package com.belle.other.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.RowRecord;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.belle.infrastructure.util.UUIDUtil;

/**
 * 
 * @descript：高效操作XLS的工具类，采用事件机制
 */
public class EventXLSUtil implements HSSFListener {

    // 保存读取XLS后的数据集合
    private static StringBuffer buf = new StringBuffer();
    // 记录列大小
    private static int columnSize = 0;
    // 多批次记录
    private SSTRecord sstrec;
    //总字段数（用于行计算）
    private static int totalSize = 0;

    /**
     * 侦听传入的记录并处理.
     * @param record    记录.
     */
    @SuppressWarnings("static-access")
	public void processRecord(Record record) {

        switch (record.getSid()) {
            case BOFRecord.sid:
                BOFRecord bof = (BOFRecord) record;
                if (bof.getType() == bof.TYPE_WORKSHEET) {
                    buf.append("-sheet-");
                }
                break;
            case BoundSheetRecord.sid:
                //BoundSheetRecord bsr = (BoundSheetRecord) record;
                //buf.append(bsr.getSheetname() + ",");
                break;
            case RowRecord.sid:
                if (columnSize == 0) {
                    RowRecord rowrec = (RowRecord) record;
                    columnSize = rowrec.getLastCol();
                    buf.append("-row-");
                } 
                break;
            case NumberRecord.sid:
                NumberRecord numrec = (NumberRecord) record;
                totalSize++;
                if (totalSize%columnSize==0) {
                	buf.append(numrec.getValue() + "-row-");
                } else {
                	buf.append(numrec.getValue() + ",");
                }
                break;
            case SSTRecord.sid:
                sstrec = (SSTRecord) record;
                break;
            case LabelSSTRecord.sid:
                LabelSSTRecord lrec = (LabelSSTRecord) record;
                totalSize++;
                //去掉第一行记录(标题)
                if (totalSize<=columnSize) break;
                //区分行
                if (totalSize%columnSize==0) {
                	buf.append(sstrec.getString(lrec.getSSTIndex()) + "-row-");
                } else {
                	buf.append(sstrec.getString(lrec.getSSTIndex()) + ",");
                }
                break;
        }
    }

    /**
     * 转换为参数集合,便于批量插入.
     */
    public static List<Object[]> toJdbcParamList() {
        
        // 输出数据
        List<Object[]> listObjects = new ArrayList<Object[]>();
        // 输入数据
        String xlsString = buf.toString();
        // 分割sheet
        String[] tableStrs = xlsString.split("-sheet-");
        // 记录sheet名称
        //String[] sheets = tableStrs[0].split(",");
        // 记录sheet数据
        for (int i = 1; i < tableStrs.length; i++) {
            // 获取xls行数据
        	String[] rows = tableStrs[i].split("-row-"); 
        	
        	for (String row : rows) {
        		//去除空行
        		if(StringUtils.isBlank(row)) continue;
        		
        		Object[] objs = new Object[columnSize + 1];
        		objs[0] = UUIDUtil.getUUID();
        		//获取xls列数据
				Object[] columns = row.split(",");
				for (int j = 0; j < columns.length; j++) {
					objs[j+1] = columns[j];
				}
				listObjects.add(objs);
			}
        }
        columnSize = 0;
        buf.delete(0, buf.length());
        totalSize = 0;
        return listObjects;
    }
    
    /**
     * 执行监听事件并将结果转换为参数集合,便于批量插入
     * @return List<参数数组]>
     * @throws IOException
     * @throws URISyntaxException
     */
    public static List<Object[]> execute(String xlsPath) throws IOException, URISyntaxException {
        //String xlsPath = Thread.currentThread().getContextClassLoader().getResource("com/belle/finance/upload/files/" + xls).toURI().getPath();
        // 创建输入流
        FileInputStream fin = new FileInputStream(xlsPath);
        // 创建org.apache.poi.poifs.filesystem.Filesystem
        POIFSFileSystem poifs = new POIFSFileSystem(fin);
        
       /* HSSFWorkbook workbook = new HSSFWorkbook(pss);  
        //读取Sheet  
        HSSFSheet sheet = workbook.getSheetAt(0);*/
        
        
        //获取InputStream中的部分Workbook (excel part)流
        InputStream din = poifs.createDocumentInputStream("Workbook");
        //构建HSSFRequest对象
        HSSFRequest req = new HSSFRequest();
        //使用监听器逐步获取所有记录
        req.addListenerForAllRecords(new EventXLSUtil());
        //创建事件工厂
        HSSFEventFactory factory = new HSSFEventFactory();
        // 执行
        factory.processEvents(req, din);
        
        // 监听完成后关闭输入流
        fin.close();
        //关闭流
        din.close();
       
        return toJdbcParamList();

    }
}
