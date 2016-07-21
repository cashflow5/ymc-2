package com.yougou.kaidian.taobao.web;

import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import au.com.bytecode.opencsv.CSVReader;

import com.sun.istack.NotNull;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.ItemImg;
import com.taobao.api.domain.Location;
import com.taobao.api.domain.Sku;
import com.yougou.kaidian.framework.exception.YMCException;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.taobao.common.TaobaoImportUtils;
import com.yougou.kaidian.taobao.constant.TaobaoImportConstants;
import com.yougou.kaidian.taobao.enums.ResultCode;
import com.yougou.kaidian.taobao.model.TaobaoItemProp;
import com.yougou.kaidian.taobao.model.TaobaoItemPropValue;
import com.yougou.kaidian.taobao.service.ITaobaoDataImportService;
import com.yougou.kaidian.taobao.service.ITaobaoItemService;
import com.yougou.kaidian.taobao.service.impl.TaobaoThread;
import com.yougou.kaidian.taobao.vo.ErrorVo;
import com.yougou.kaidian.taobao.vo.TaobaoCsvItemVO;
import com.yougou.kaidian.taobao.vo.TaobaoImportVo;

/**
 * 多线程处理淘宝导入
 * 
 * @author le.sm
 *
 */
@Controller
@RequestMapping("/taobao")
public class TaobaoImportController {
	private static final Logger log = LoggerFactory.getLogger(TaobaoImportController.class);

	@Resource
	private ITaobaoDataImportService taobaoDataImportService;
	@Resource
	private ITaobaoItemService taobaoItemService;

	/**
	 * 上传CSV格式淘宝商品数据文件，解析数据，保存到数据库中，发送JMS消息下载上传图片。
	 * CSV淘宝商品数据量超过20行的时候，使用多线程解析、处理数据。
	 * 
	 * 功能分解 1.CVS数据装载解析 2.数据格式验证封装 3.验证品牌和分类信息 4.数据保存 5.图片处理
	 * 
	 * @param request MultipartHttpServletRequest
	 * @param response HttpServletResponse
	 * @return 
	 *         JSON格式字符串，格式如：{"total":1,"extendTotal":1,"existTotal":0,"resultCode"
	 *         :"200","failTotal":0,"msg":[]}
	 */
	@ResponseBody
	@RequestMapping(value = "/csvImport", method = RequestMethod.POST)
	public String csvImport(MultipartHttpServletRequest request, HttpServletResponse response) {
		long timeBegin = System.currentTimeMillis();

		TaobaoImportVo importVo = new TaobaoImportVo();
		JSONObject jsonObject = new JSONObject();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		String loginName = YmcThreadLocalHolder.getOperater();

		Iterator<String> itr = request.getFileNames();
		if (itr == null) {
			jsonObject.put("resultCode", ResultCode.ERROR.getCode());
			jsonObject.put("msg", "文件为空或者错误,请重新选择!");
			log.error("[淘宝导入]商家编码:{}-文件为空或者错误.", merchantCode);
			return jsonObject.toString();
		}
		if (itr.hasNext()) {
			try {
				MultipartFile mpf = request.getFile(itr.next());
				// 构建csvReader对象
				CSVReader csvReader = new CSVReader(new InputStreamReader(mpf.getInputStream(), "unicode"), '\t', '"',
						0);
				// 解析csv文件
				/****************************
				 * 多线程改造，如果数据不超过20，不启用多线程
				 ***************************/
				try {
					csvReader.readNext();
					// 英文题头
					String[] enName = csvReader.readNext();
					// 校验英文题头的第一列是否为"title"，检查文件格式
					for (int e = 0; e < enName.length; e++) {
						if (e == 0 && !enName[e].equalsIgnoreCase("title")) {
							throw new YMCException("csv文件格式被修改不能解析.第二行的第一列应该是'title'", "taobao");
						}
						importVo.getMap().put(enName[e], e);
					}
					// 中文题头
					csvReader.readNext();
					// 所有内容
					List<String[]> list = csvReader.readAll();

					if (list.size() < 1) {
						throw new YMCException("导入的csv文件中没有数据", "taobao");
					}

					int threads = TaobaoImportUtils.getThreadSum(list.size());
					if (threads > 1) {
						log.info("[淘宝导入]商家编码:{}-多线程校验文件,校验内容:{}",merchantCode, list);
						executeThread(list, merchantCode, importVo);
					} else {
						log.info("[淘宝导入]商家编码:{}-校验文件,未使用多线程,校验内容:{}",merchantCode, list);
						validateCsvFile(list, merchantCode, importVo);
					}
				} catch (Exception e) {
					log.error("[淘宝导入]商家编码:{}-校验CSV文件出现异常.", merchantCode, e);
					jsonObject.put("resultCode", ResultCode.ERROR.getCode());
					jsonObject.put("msg", StringUtils.isBlank(e.getMessage()) ? "CSV文件格式有问题，校验不通过。" : e.getMessage());
					return jsonObject.toString();
				} finally {
					csvReader.close();
				}
				// 验证品牌和分类 对应信息
				log.info("[淘宝导入]商家编码:{}-验证品牌和分类对应信息,数据:{}",merchantCode, importVo);
				taobaoDataImportService.checkBrandAndCat(importVo, merchantCode);

				if (importVo.getErrorList().size() < 1) {

					// 如果voList的数量>20 ,采用多线程
					int itemThreads = TaobaoImportUtils.getThreadSum(importVo.getVoList().size());
					if (itemThreads > 1) {
						log.info("[淘宝导入]商家编码:{}-多线程 导入数据库",merchantCode);
						executeThreadTOImportCSVData(importVo, merchantCode);
						// 排序
						Collections.sort(importVo.getErrorList());
						buildJsonResult(jsonObject, importVo);
					} else {
						log.info("[淘宝导入]商家编码:{}-导入数据库，未使用多线程",merchantCode);
						taobaoDataImportService.importTaobaoItemData4CSV(merchantCode, loginName, importVo);
						buildJsonResult(jsonObject, importVo);
					}
				} else {
					jsonObject.put("msg", importVo.getErrorList());
					jsonObject.put("resultCode", ResultCode.SUCCESS.getCode());
					jsonObject.put("total", 0);
					jsonObject.put("extendTotal", 0);
					jsonObject.put("existTotal", 0);
					jsonObject.put("failTotal", importVo.getErrorList().size());
				}

			} catch (Exception e) {
				log.error("[淘宝导入]商家编码:{}-导入淘宝商品异常.", merchantCode, e);
				jsonObject.put("resultCode", ResultCode.ERROR.getCode());
				jsonObject.put("msg", "导入淘宝商品资料异常  " + (StringUtils.isEmpty(e.getMessage()) ? "" : e.getMessage()));
			}
		}
		log.info("[淘宝导入]商家编码:{}-淘宝导入耗时：=============== {}s ", merchantCode, (System.currentTimeMillis() - timeBegin) / 1000);
		return jsonObject.toString();

	}

	/**
	 * 组装返回Json对象
	 * 
	 * @param jsonObject Json对象
	 * @param importVo 淘宝导入VO
	 */
	public void buildJsonResult(JSONObject jsonObject, TaobaoImportVo importVo) {
		// 将errorList 的值取出来
		jsonObject.put("msg", importVo.getErrorList());
		jsonObject.put("resultCode", ResultCode.SUCCESS.getCode());
		jsonObject.put("total",
				importVo.getResultTotal().get("itemTotal") == null ? 0 : importVo.getResultTotal().get("itemTotal"));
		jsonObject.put("extendTotal", importVo.getResultTotal().get("extendTotal") == null ? 0 : importVo
				.getResultTotal().get("extendTotal"));
		jsonObject.put("existTotal", (importVo.getResultTotal().get("existTotal") == null ? 0 : importVo
				.getResultTotal().get("existTotal")));
		jsonObject.put("failTotal", (importVo.getResultTotal().get("failCount") == null ? 0 : importVo.getResultTotal()
				.get("failCount")) + importVo.getPackagFailTotal());
	}

	/**
	 * 解析数据代码的多线程
	 * 
	 * @param list 淘宝商品数据列表
	 * @param merchantCode 商家编码
	 * @param importVo 淘宝导入VO
	 */
	public void executeThread(List<String[]> list, final String merchantCode, final TaobaoImportVo importVo) {
		// 线程池中线程数
		final int threadCount = TaobaoImportUtils.getThreadSum(list.size()) * TaobaoImportConstants.cpuNums;
		final CountDownLatch countdownLatch = new CountDownLatch(list.size());

		final BlockingQueue<TaobaoThread> queue = new SynchronousQueue<TaobaoThread>();// 阻塞队列
		ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);// 线程池

		for (int i = 0; i < threadCount; i++) {// 依次加入线程
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							if (countdownLatch.getCount() == 0) {
								log.info("[淘宝导入]商家编码:{}--------计数为零，线程退出---------- ", merchantCode);
								break;
							}
							TaobaoThread thread = queue.poll(TaobaoImportConstants.WAIT_TIME, TimeUnit.SECONDS);// 从队列中取出数据，1秒后仍取不出，则返回null
							if (thread == null) {
								log.info("[淘宝导入]商家编码:{}--------取不到数据，线程退出---------- ", merchantCode);
								break;
							}
							String[] csvItem = thread.getCsvArray();
							log.info("[淘宝导入]商家编码:{} --------csvItem----------{} ", merchantCode, Arrays.toString(csvItem));
							// 解析数据
							importCsvData(csvItem, merchantCode, thread.getIndex(), importVo);
							csvItem = null;
							countdownLatch.countDown();
							Thread.sleep(20);
						} catch (InterruptedException e) {
							log.error("[淘宝导入]商家编码:{}-批量解析csv文件异常.", merchantCode, e);
						}
					}
				}
			});
		}
		try {
			for (int i = 0; i < list.size(); i++) {
				queue.put(new TaobaoThread(i, list.get(i)));
			}
		} catch (InterruptedException e) {
			log.error("[淘宝导入]商家编码:{}-批量解析csv文件异常.", merchantCode, e);
		}

		try {// 等待子线程发布结束
			countdownLatch.await(1, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			log.error("[淘宝导入]商家编码:{}-批量解析csv文件异常.", merchantCode, e);
		}

		if (threadPool != null) {
			threadPool.shutdown();
			threadPool = null;
		}
	}

	/**
	 * 多线程导入csv，初始化数据
	 * 
	 * @param csvStr csv一行数据数组
	 * @param merchantCode 商家编码
	 * @param arrayIndex
	 *            数据行，从0开始计算，数据实际在csv所在行，需加上4（TaobaoImportConstants.CSV_INDEX）
	 * @param importVo 淘宝导入VO
	 */
	private void importCsvData(String[] csvStr, String merchantCode, int arrayIndex, TaobaoImportVo importVo) {
		String title = csvStr[importVo.getMap().get("title")];
		if (StringUtils.isNotBlank(title)) {
			try {
				initData(title, csvStr, merchantCode, arrayIndex, importVo);
			} catch (Exception e) {
				importVo.setPackagFailTotalAdd();
				importVo.getErrorList().add(
						(new ErrorVo(arrayIndex, MessageFormat.format("第 {0} 行:解析失败,格式不符合规范.{1}", new Object[] {
								(arrayIndex + TaobaoImportConstants.CSV_INDEX),
								(StringUtils.isEmpty(e.getMessage()) ? "解析数据失败" : e.getMessage()) }))));
				log.error("[淘宝导入]商家编码:{}-导入淘宝商品{} 第{}行解析数据失败.", merchantCode, title, arrayIndex
						+ TaobaoImportConstants.CSV_INDEX, e);
			}
		} else {
			importVo.setPackagFailTotalAdd();
			importVo.getErrorList().add(
					new ErrorVo(arrayIndex, MessageFormat.format("第 {0}行 {1}列:解析失败,格式不符合规范.{2}", new Object[] {
							(arrayIndex + TaobaoImportConstants.CSV_INDEX), "title", "宝贝名称为空" })));
			log.error("[淘宝导入]商家编码:{}-导入淘宝商品-第{}行 {}列:解析失败,格式不符合规范.{}", merchantCode, arrayIndex
					+ TaobaoImportConstants.CSV_INDEX, "title", "宝贝名称为空");
		}
	}

	/**
	 * 验证，组装一行csv数据
	 */
	private void initData(String title, String[] data, String merchantCode, int arrayIndex, TaobaoImportVo importVo)
			throws Exception {

		Map<String, Integer> columnMap = importVo.getMap();
		Item vo = new Item();
		vo.setTitle(title);
		String numid = data[columnMap.get("num_id")];
		if ("0".equals(numid)) {
			addErrorVoToList("不能为0 ", merchantCode, importVo, "num_id", arrayIndex, null);
		}
		// CID 类目ID 判断是否是数字
		vo.setCid(isNumErrorCatch(data[columnMap.get("cid")], "cid", importVo, arrayIndex, merchantCode));
		// 数字ID
		vo.setNumIid(isNumErrorCatch(numid, "num_id", importVo, arrayIndex, merchantCode));
		// 宝贝数量
		vo.setNum(isNumErrorCatch(data[columnMap.get("num")], "num", importVo, arrayIndex, merchantCode));
		vo.setSellerCids(data[columnMap.get("seller_cids")]);// 店铺类目ID
		vo.setStuffStatus(data[columnMap.get("stuff_status")]);// 新旧程度
		Location location = new Location();
		location.setState(data[columnMap.get("location_state")]);// 省
		location.setCity(data[columnMap.get("location_city")]);// 市
		vo.setLocation(location);
		vo.setType(data[columnMap.get("item_type")]);// 出售方式
		vo.setPrice(data[columnMap.get("price")]);// 价格
		vo.setFreightPayer(data[columnMap.get("freight_payer")]);// 运费承担
		vo.setPostFee(data[columnMap.get("post_fee")]);// 平邮
		vo.setEmsFee(data[columnMap.get("ems_fee")]);// EMS
		vo.setExpressFee(data[columnMap.get("express_fee")]);// 快递
		vo.setHasInvoice("1".equals(data[columnMap.get("has_invoice")]) ? true : false);// 发票
		vo.setHasWarranty("1".equals(data[columnMap.get("has_warranty")]) ? true : false);// 保修
		vo.setApproveStatus(data[columnMap.get("approve_status")]);// 放入仓库
		vo.setHasShowcase("1".equals(data[columnMap.get("has_showcase")]) ? true : false);// 橱窗推荐
		String modified = data[columnMap.get("modified")];// 修改时间
		try {
			vo.setModified(TaobaoImportUtils.formatStrToDate(modified));
		} catch (Exception e) {
			vo.setModified(new Date(System.currentTimeMillis()));
		}
		vo.setCreated(vo.getModified());
		vo.setListTime(vo.getModified());
		vo.setDesc(data[columnMap.get("description")]);// 描述
		vo.setHasDiscount("1".equals(data[columnMap.get("has_discount")]) ? true : false);// 会员打折
		String cateProps = data[columnMap.get("cateProps")];
		String brandPid = TaobaoImportConstants.P_ID_SEARCH + ":";
		if (StringUtils.isBlank(cateProps)) {
			addErrorVoToList("该商品属性不能为空", merchantCode, importVo, "cateProps", arrayIndex, null);
		} else if (cateProps.indexOf(brandPid) == -1) {
			addErrorVoToList("不能从该商品属性获取品牌信息", merchantCode, importVo, "cateProps", arrayIndex, null);
		} else {
			vo.setProps(cateProps);// 商品属性
			// 处理属性
			vo.setPropsName(this.getPropsName(cateProps));
			String brand_v_Str = StringUtils.substringBetween(cateProps, brandPid, ";");
			if (StringUtils.isBlank(brand_v_Str)) {
				brand_v_Str = StringUtils.substringAfter(cateProps, brandPid);
			}
			if (StringUtils.isEmpty(importVo.getTaobaoBrandMap().get(Long.parseLong(brand_v_Str)))) {
				importVo.getTaobaoBrandMap().put(Long.parseLong(brand_v_Str), title);
			}
			if (vo.getCid() != 0L) {
				if (importVo.getTaobaoCatMap().get(vo.getCid()) == null) {
					importVo.getTaobaoCatMap().put(vo.getCid(), new String[] { title, brand_v_Str });
				}
			}
		}

		// picture
		// 处理图片
		String picStr = data[columnMap.get("picture")];
		Long index = System.nanoTime() + new Random().nextInt(9999999);
		if (StringUtils.isNotEmpty(picStr)) {
			vo.setItemImgs(addPicList(picStr, vo, index, importVo, arrayIndex, merchantCode));
		}
		// 商品图片
		vo.setVideos(null);// video
		String skuStr = data[columnMap.get("skuProps")];// SKU 销售属性组合
		if (StringUtils.isBlank(skuStr)) {
			addErrorVoToList("skuProps不能为空", merchantCode, importVo, "skuProps", arrayIndex, null);
		}

		boolean colorFlag = false;
		// 判断skuProps列是否已经包含已知的颜色代码
		for (String color : TaobaoImportConstants.COLOR_PIDS) {
			if (skuStr.indexOf(color) > -1) {
				colorFlag = true;
			}
		}
		if (!colorFlag) {
			addErrorVoToList("skuProp解析数据失败，缺少颜色属性", merchantCode, importVo, "skuProps", arrayIndex, null);
		}
		// skuProps 销售属性组合
		vo.setSkus(addPropList(skuStr, vo, index, importVo, arrayIndex, merchantCode));
		vo.setInputPids(data[columnMap.get("inputPids")]);// 用户输入ID串
		vo.setInputStr(data[columnMap.get("inputValues")]);// 用户输入名-值对
		String outId = data[columnMap.get("outer_id")];// 商家编码
		if (StringUtils.isBlank(outId)) {
			outId = this.getOutIdByInput(vo.getInputPids(), vo.getInputStr());
			if ("outIdIsNull".equals(outId)) {
				addErrorVoToList("获取商品货号失败,请将货号(款号)赋值给outer_id字段", merchantCode, importVo, "inputPids，inputValues",
						arrayIndex, null);
			} else if ("inputIsNull".equals(outId)) {
				addErrorVoToList("这两字段均不能为空", merchantCode, importVo, "inputPids，inputValues", arrayIndex, null);
			}
		}
		vo.setOuterId(outId);
		vo.setPropertyAlias(data[columnMap.get("propAlias")]);// 销售属性别名
		vo.setAutoFill(data[columnMap.get("auto_fill")]);// 代充类型
		// 闪电发货
		vo.setIsLightningConsignment("1".equals(data[columnMap.get("is_lighting_consigment")]) ? true : false);
		// 新品
		vo.setIsXinpin("1".equals(data[columnMap.get("is_xinpin")]) ? true : false);
		// 宝贝卖点
		vo.setSellPoint(columnMap.get("subtitle") == null ? "" : data[columnMap.get("subtitle")]);

		TaobaoCsvItemVO taobaoCsvItemVO = new TaobaoCsvItemVO();
		taobaoCsvItemVO.setIndex(arrayIndex);
		taobaoCsvItemVO.setItem(vo);
		taobaoCsvItemVO.setArrayIndex(arrayIndex);
		importVo.getVoList().add(taobaoCsvItemVO);
	}

	/**
	 * 解析图片
	 * 
	 * @param picStr
	 * @param vo
	 * @param index
	 * @param importVo
	 * @param arrayIndex
	 * @param merchantCode
	 * @return
	 */
	private List<ItemImg> addPicList(String picStr, Item vo, long index, TaobaoImportVo importVo, int arrayIndex,
			String merchantCode) {
		log.info("[淘宝导入]商家编码:{}-解析图片.picStr:{},Item:{},index:{},arrayIndex:{}", merchantCode, picStr, vo, index , arrayIndex);
		String[] picArry = StringUtils.split(picStr, ";");
		List<ItemImg> itemImgs = new ArrayList<ItemImg>();
		String[] picParmArry;
		String[] picParmArryURL;
		ItemImg itemImg;
		for (String img : picArry) {
			picParmArry = StringUtils.split(img, ":");
			picParmArryURL = StringUtils.split(img, "|");
			if (picParmArry.length > 2) {
				if ("1".equals(picParmArry[1])) {
					itemImg = new ItemImg();
					if ("0".equals(picParmArry[2])) {
						itemImg.setId(0L);
						if (picParmArryURL.length > 1) {
							vo.setPicUrl(picParmArryURL[1]);
						}
					} else {
						itemImg.setId(index++);
					}
					itemImg.setPosition(isNumErrorCatch(picParmArry[2], "picture里 ，图片的绝对位置 ", importVo, arrayIndex,
							merchantCode));
					if (picParmArryURL.length > 1) {
						itemImg.setUrl(picParmArryURL[1]);
					}
					itemImg.setCreated(new Date());
					itemImgs.add(itemImg);
				}
			}
		}
		return itemImgs;
	}

	/**
	 * sku 属性解析
	 * 
	 * @param skuArry
	 * @param vo
	 * @param index
	 * @param importVo
	 * @param arrayIndex
	 * @param merchantCode
	 * @return
	 * @throws Exception
	 */
	private List<Sku> addPropList(String skuStr, Item vo, long index, TaobaoImportVo importVo, int arrayIndex,
			String merchantCode) throws Exception {
		log.info("[淘宝导入]商家编码:{}-属性解析.skuStr:{},Item:{},index:{},arrayIndex:{}", merchantCode, skuStr, vo, index , arrayIndex);
		String[] skuArry = StringUtils.split(skuStr, ";");
		boolean hasSize = true;
		String[] skuParmArry = null;
		Sku sku = null;
		List<Sku> skuList = new ArrayList<Sku>();
		for (int i = 0; i < skuArry.length; i++) {
			skuParmArry = StringUtils.splitByWholeSeparatorPreserveAllTokens(skuArry[i], ":");
			if (skuParmArry.length == 5) {
				sku = new Sku();
				if (TaobaoImportUtils.isNum(skuParmArry[1])) {
					sku.setQuantity(isNumErrorCatch(skuParmArry[1], "skuProps中Quantity属性", importVo, arrayIndex,
							merchantCode));
				}
				sku.setOuterId(skuParmArry[2]);
				sku.setProperties(skuParmArry[3] + ":" + skuParmArry[4]);
				sku.setNumIid(vo.getNumIid());
				sku.setPropertiesName(this.getPropsName(sku.getProperties()));
				sku.setSkuId(index++);
				skuList.add(sku);
				hasSize = true;
			} else if (hasSize && skuParmArry.length == 2 && sku != null) {
				sku.setProperties(sku.getProperties() + ";" + skuArry[i]);
				sku.setPropertiesName(this.getPropsName(sku.getProperties()));
				hasSize = false;
			}
		}
		return skuList;
	}

	/**
	 * 判断字符串转换long
	 * 
	 * @param str 需要转换的字符串
	 * @param columnName 列名
	 * @param importVo
	 * @param arrayIndex 表示的下标
	 * @param merchantCode 商家编码
	 * @param title 宝贝名称
	 * @return
	 */
	private long isNumErrorCatch(String value, String columnName, TaobaoImportVo importVo, int arrayIndex,
			String merchantCode) {
		Long num = 0L;
		try {
			num = Long.parseLong(value);// 数量
		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-{}不能为空且必须是数字,columnName:{},arrayIndex:{}",merchantCode, value, columnName, arrayIndex);
			addErrorVoToList("不能为空且必须是数字 ", merchantCode, importVo, columnName, arrayIndex, e);
		}
		return num;
	}

	/**
	 * CSV文件验证
	 * 
	 * @param request
	 */
	private void validateCsvFile(List<String[]> list, String merchantCode, TaobaoImportVo importVo) throws Exception {
		String title = "";
		for (int j = 0; j < list.size(); j++) {
			title = list.get(j)[importVo.getMap().get("title")];
			if (StringUtils.isNotBlank(title)) {
				try {
					initData(title, list.get(j), merchantCode, j, importVo);
				} catch (Exception e) {
					importVo.setPackagFailTotalAdd();
					importVo.getErrorList().add(
							new ErrorVo(j, MessageFormat.format("第 {0} 行:解析失败,格式不符合规范.{1}", new Object[] {
									(j + TaobaoImportConstants.CSV_INDEX),
									(StringUtils.isEmpty(e.getMessage()) ? "解析数据失败" : e.getMessage()) })));
					log.error("[淘宝导入]商家编码:{}-导入淘宝商品{} 第{}解析数据失败.", merchantCode, title, j
							+ TaobaoImportConstants.CSV_INDEX, e);
				}
			} else {
				importVo.setPackagFailTotalAdd();
				importVo.getErrorList().add(
						new ErrorVo(j, MessageFormat.format("第 {0}行 {1}列:解析失败,格式不符合规范.{2}", new Object[] {
								(j + TaobaoImportConstants.CSV_INDEX), "title", "宝贝名称为空" })));
				log.error("[淘宝导入]商家编码:{}-导入淘宝商品-第{}行 {}列:解析失败,格式不符合规范.{3}", merchantCode, j
						+ TaobaoImportConstants.CSV_INDEX, "title", "宝贝名称为空");
			}
		}
	}

	/**
	 * 导入到数据库 多线程
	 * 
	 * @param itemList
	 * @param merchantCode
	 * @param operter
	 * @throws IllegalAccessException
	 */
	public void executeThreadTOImportCSVData(@NotNull final TaobaoImportVo importVo, final String merchantCode)
			throws IllegalAccessException {
		ExecutorService threadPool = null;
		List<TaobaoCsvItemVO> itemList = importVo.getVoList();

		Map<String, Integer> resultTotal = importVo.getResultTotal();
		resultTotal.put("itemTotal", 0);
		resultTotal.put("extendTotal", 0);
		resultTotal.put("existTotal", 0);
		// 线程池中线程数
		final int threadCount = TaobaoImportUtils.getThreadSum(importVo.getVoList().size()) * TaobaoImportConstants.cpuNums;
		final CountDownLatch countdownLatch = new CountDownLatch(importVo.getVoList().size());

		final BlockingQueue<TaobaoCsvItemVO> queue = new SynchronousQueue<TaobaoCsvItemVO>();// 阻塞队列
		threadPool = Executors.newFixedThreadPool(threadCount);// 线程池

		for (int i = 0; i < threadCount; i++) {// 依次加入线程
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							if (countdownLatch.getCount() == 0) {
								log.info("[淘宝导入]商家编码:{} ----批量验证和导入 TaobaoCsvItemVO list报错----计数为零，线程退出---------- ", merchantCode);
								break;
							}
							TaobaoCsvItemVO item = queue.poll(TaobaoImportConstants.WAIT_TIME, TimeUnit.SECONDS);// 从队列中取出数据，3秒后仍取不出，则返回null
							if (item == null) {
								log.info("[淘宝导入]商家编码:{}----批量验证和导入 TaobaoCsvItemVO list报错----取不到数据，线程退出---------- ", merchantCode);
								break;
							}
							log.info("[淘宝导入]商家编码:{} --------TaobaoCsvItemVO 开始导入数据库---------- ", merchantCode);
							taobaoDataImportService.importTaobaoItemData4CSVForSimple(item, merchantCode, importVo);
							countdownLatch.countDown();
							Thread.sleep(20);
						} catch (InterruptedException e) {
							log.error("[淘宝导入]商家编码:{}-批量验证和导入 TaobaoCsvItemVO list报错.", merchantCode, e);
						}
					}
				}
			});
		}

		try {
			for (TaobaoCsvItemVO vo : itemList) {
				queue.put(vo);
			}

		} catch (InterruptedException e) {
			log.error("[淘宝导入]商家编码:{}-批量验证和导入 TaobaoCsvItemVO list报错.", merchantCode, e);
		}
		try {// 等待子线程发布结束
			countdownLatch.await(1, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			log.error("[淘宝导入]商家编码:{}-批量验证和导入 TaobaoCsvItemVO list报错.", merchantCode, e);
		}

		if (threadPool != null) {
			threadPool.shutdown();
			threadPool = null;
		}
	}

	/**
	 * 组装错误信息
	 * 
	 * @param errorMsg 错误信息
	 * @param merchantCode 商家编码
	 * @param importVo 导入VO对象
	 * @param title 宝贝名称
	 * @param row 出错行
	 * @param e 异常信息
	 */
	private void addErrorVoToList(String errorMsg, String merchantCode, TaobaoImportVo importVo, String columnName,
			int row, Exception e) {
		importVo.setPackagFailTotalAdd();
		importVo.getErrorList().add(
				new ErrorVo(row, MessageFormat.format("第 {0}行 {1}列:解析失败,格式不符合规范.{2}", new Object[] {
						(row + TaobaoImportConstants.CSV_INDEX), columnName, errorMsg })));
		log.error("[淘宝导入]商家编码:{}-导入淘宝商品-第{}行 {}列:解析失败,格式不符合规范.{}", merchantCode,
				(row + TaobaoImportConstants.CSV_INDEX), columnName, errorMsg, e);
	}

	/**
	 * 根据pid,vid获取相应的name
	 * 
	 * @param props
	 * @return
	 * @throws Exception
	 */
	private String getPropsName(String props) throws Exception {
		StringBuffer propsName = new StringBuffer();
		String[] propsArry = StringUtils.split(props, ";");
		String[] p_vid;
		TaobaoItemProp taobaoItemProp = null;
		TaobaoItemPropValue taobaoItemPropValue = null;
		String taobaoItemPropName = "";
		String taobaoItemPropValueName;
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		for (String a_prop : propsArry) {
			p_vid = StringUtils.split(a_prop, ":");
			if (TaobaoImportUtils.isNum(p_vid[0])) {
				taobaoItemProp = taobaoItemService.getTaobaoItemProp(Long.parseLong(p_vid[0]));
				taobaoItemPropValue = taobaoItemService.getTaobaoItemPropValue(Long.parseLong(p_vid[1]));
				if (taobaoItemProp != null) {
					taobaoItemPropName = taobaoItemProp.getName();
				} else {
					taobaoItemPropName = "未知属性，请去分类属性补全--pid_" + (Long.parseLong(p_vid[0]));
					log.info("[淘宝导入]商家编码:{}获取属性名称为null pid:{}", merchantCode, p_vid[0]);
				}
				if (taobaoItemPropValue != null) {
					taobaoItemPropValueName = taobaoItemPropValue.getName();
				} else {
					taobaoItemPropValueName = "未知属性值，请去分类属性补全vid--" + (Long.parseLong(p_vid[1]));
					log.info("[淘宝导入]商家编码:{}获取属性值名称为null vid:{}", merchantCode, p_vid[1]);
				}
				propsName.append(a_prop).append(":").append(taobaoItemPropName).append(":")
						.append(taobaoItemPropValueName).append(";");
			}
		}
		log.info("[淘宝导入]商家编码:{}-根据pid,vid获取相应的name.props:{},propsName:{}", merchantCode, props, propsName.toString());
		return propsName.toString();
	}

	/**
	 * 从inputid解析outid(款号)
	 * 
	 * @param inputPids
	 * @param inputValues
	 * @return
	 * @throws Exception
	 */
	private String getOutIdByInput(String inputPids, String inputValues) throws YMCException {
		if (StringUtils.isBlank(inputPids) || StringUtils.isBlank(inputValues)) {
			return "inputIsNull";
		}
		String outId = "";
		String[] aArry = StringUtils.splitByWholeSeparator(inputValues, ",");
		if (aArry.length > 0 && aArry.length <= 3) {
			String inputPidStr = StringUtils.replace(inputPids, ",", "");
			int index = inputPidStr.indexOf(TaobaoImportConstants.TAOBAO_STYLE_NO);
			if (index == 0 && aArry.length > 0) {
				outId = aArry[0];
			} else if (index == (inputPidStr.length() - TaobaoImportConstants.TAOBAO_STYLE_NO.length())
					&& aArry.length > 1) {
				outId = aArry[aArry.length - 1];
			} else if (index < (inputPidStr.length() - TaobaoImportConstants.TAOBAO_STYLE_NO.length())
					&& aArry.length > 2) {
				outId = aArry[aArry.length - 2];
			}
		}
		if (StringUtils.isBlank(outId)) {
			return "outIdIsNull";
		}
		log.info("[淘宝导入]商家编码:{}-从inputid解析outid(款号),inputPids:{},inputValues:{},outId:{}", YmcThreadLocalHolder.getMerchantCode(), inputPids, inputValues, outId);
		return outId;
	}

}