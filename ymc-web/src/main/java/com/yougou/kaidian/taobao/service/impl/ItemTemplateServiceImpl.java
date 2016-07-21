package com.yougou.kaidian.taobao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.taobao.dao.ItemTemplateMapper;
import com.yougou.kaidian.taobao.exception.BusinessException;
import com.yougou.kaidian.taobao.model.pojo.ItemPropTemplate;
import com.yougou.kaidian.taobao.model.pojo.ItemTemplate;
import com.yougou.kaidian.taobao.service.IItemTemplateService;
import com.yougou.tools.common.utils.DateUtil;

@Service
public class ItemTemplateServiceImpl implements IItemTemplateService {
	
	private static final Logger log = LoggerFactory.getLogger(ItemTemplateServiceImpl.class);

	@Resource
	private ItemTemplateMapper itemTemplateMapper;

	@Override
	@Transactional
	public void addItemTemplate(String[] propItemNo, String[] propItemName, String[] propValueNo,
			String[] propValueName, String commodityName, String cateNo, String merchantCode) throws BusinessException {
		log.info("[淘宝导入]商家编码:{}-添加模板.propItemNo:{},propItemName:{},propValueNo:{},propValueName:{},commodityName:{},cateNo:{}.",
				merchantCode, propItemNo, propItemName, propValueNo, propValueName, commodityName, cateNo);
		if (propItemNo == null || propItemName == null || propValueNo == null || propValueName == null
				|| StringUtils.isEmpty(commodityName) || StringUtils.isEmpty(cateNo)
				|| StringUtils.isEmpty(merchantCode)) {
			throw new BusinessException("参数错误");
		}
		if (propItemNo.length != propItemName.length || propItemNo.length != propValueNo.length
				|| propItemNo.length != propValueName.length) {
			throw new BusinessException("参数错误");
		}

		// 校验是否已经保存过模板
		int count = itemTemplateMapper.selectTemplateCount(merchantCode, cateNo, commodityName, "");
		if (count > 0) {
			throw new BusinessException("此模板已经存在");
		}
		String templateId = UUIDGenerator.getUUID();

		List<ItemPropTemplate> list = new ArrayList<ItemPropTemplate>();
		int _len = propItemNo.length;
		ItemPropTemplate propTem = null;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < _len; i++) {
			propTem = new ItemPropTemplate();
			propTem.setId(UUIDGenerator.getUUID());
			propTem.setMerchantCode(merchantCode);
			propTem.setTemplateId(templateId);
			propTem.setPropItemName(propItemName[i]);
			propTem.setPropItemNo(propItemNo[i]);
			propTem.setPropValueNo(propValueNo[i]);
			propTem.setPropValueName(propValueName[i]);
			list.add(propTem);
			sb.append(propItemName[i]).append("：").append(propValueName[i]);
			if (i < _len - 1) {
				sb.append(" / ");
			}
		}
		ItemTemplate template = new ItemTemplate();
		template.setId(templateId);
		template.setCateNo(cateNo);
		template.setMerchantCode(merchantCode);
		template.setTitle(commodityName);
		template.setOperated(DateUtil.getDateTime(new Date()));
		template.setPropNames(sb.toString());
		itemTemplateMapper.insertItemTemplate(template);
		if (!list.isEmpty()) {
			itemTemplateMapper.insertItemPropTemplateBatch(list);
		}
	}

	@Override
	@Transactional
	public void deleteItemTemplate(String id, String merchantCode) throws BusinessException {
		if (StringUtils.isEmpty(id)) {
			throw new BusinessException("参数错误");
		}
		itemTemplateMapper.deleteItemTemplate(id, merchantCode);
		itemTemplateMapper.deleteItemPropTemplate(id, merchantCode);
	}

	@Override
	public PageFinder<ItemTemplate> findItemTemplateList(String merchantCode, String cateNo, String key, Query query) {
		RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
		List<ItemTemplate> list = itemTemplateMapper.selectTemplate(merchantCode, cateNo, "", key, rowBounds);
		int count = itemTemplateMapper.selectTemplateCount(merchantCode, cateNo, "", key);
		PageFinder<ItemTemplate> pageFinder = new PageFinder<ItemTemplate>(query.getPage(), query.getPageSize(), count,
				list);
		return pageFinder;
	}

	@Override
	public int selectTemplateCount(String merchantCode, String cateNo, String title, String key) {
		return itemTemplateMapper.selectTemplateCount(merchantCode, cateNo, "", key);
	}

	@Override
	public List<ItemPropTemplate> findItemPropTemplateList(String merchantCode, String templateId) {
		return itemTemplateMapper.selectPropTemplate(merchantCode, templateId);
	}

}
