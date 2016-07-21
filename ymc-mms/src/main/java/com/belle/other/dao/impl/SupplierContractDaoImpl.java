package com.belle.other.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.infrastructure.util.DateUtil;
import com.belle.infrastructure.util.StringUtil;
import com.belle.other.dao.ISupplierContractDao;
import com.belle.other.model.pojo.SupplierContract;
import com.belle.other.model.vo.SupplierContractVo;

/**
 * 合同信息DAO实现类
 * 
 * @author zhuangruibo
 * 
 */
@Repository
public class SupplierContractDaoImpl extends
		HibernateEntityDao<SupplierContract> implements ISupplierContractDao {

	public PageFinder<SupplierContractVo> querySupplierContract(SupplierContractVo vo, Query query) throws Exception {
		StringBuffer sql = new StringBuffer("SELECT c.contract_no,s.supplier_code,s.supplier,s.supplier_type,");
			sql.append("c.clearing_form,c.effective_date,c.failure_date,c.update_time,c.update_user,c.id,c.attachment");
			sql.append(" FROM tbl_sp_supplier s INNER JOIN tbl_sp_supplier_contract c ON s.id = c.supplier_id WHERE 1=1 ");
		StringBuffer whereSql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		if (StringUtil.isExist(vo.getContractNo())) {
			whereSql.append(" AND c.contract_no = ?");
			params.add(vo.getContractNo());
		}
		if (vo.getContractState() != null){
			if (vo.getContractState() == 0){
//				whereSql.append(" AND (c.effective_date > DATE_FORMAT(NOW(),'%y-%m-%d') OR c.failure_date < DATE_FORMAT(NOW(),'%y-%m-%d')) ");
				whereSql.append(" AND DATE_FORMAT(NOW(),'%y-%m-%d') > c.failure_date ");
			}
			else if (vo.getContractState() == 1){
//				whereSql.append(" AND c.effective_date <= DATE_FORMAT(NOW(),'%y-%m-%d') AND c.failure_date >= DATE_FORMAT(NOW(),'%y-%m-%d') ");
				whereSql.append(" AND DATE_FORMAT(NOW(),'%y-%m-%d') <= c.failure_date ");
			}
		}
		if (StringUtil.isExist(vo.getSupplierType())){
			whereSql.append(" AND s.supplier_type = ?");
			params.add(vo.getSupplierType());
		}
		if (vo.getClearingForm() != null){
			whereSql.append(" AND c.clearing_form = ?");
			params.add(vo.getClearingForm());
		}
		if (StringUtil.isExist(vo.getSupplierCode())){
			whereSql.append(" AND s.supplier_code = ?");
			params.add(vo.getSupplierCode());
		}
		if (StringUtil.isExist(vo.getSupplier())){
			whereSql.append(" AND s.supplier = ?");
			params.add(vo.getSupplier());
		}
		
		String countSql = "SELECT COUNT(*) as sumNum FROM tbl_sp_supplier s INNER JOIN tbl_sp_supplier_contract c ON s.id = c.supplier_id where 1=1 ";
		countSql += whereSql.toString();
		
		Session session = getHibernateSession();
		/** 得到当前结果集 start **/
		PageFinder<SupplierContractVo> pageFinder = null;
		Connection conn = null;
		ResultSet rs = null;
		try{
			conn = session.connection();
			PreparedStatement ps = conn.prepareStatement(countSql);
	
			setSqlParams(ps, params.toArray());
	
			rs = ps.executeQuery();
			int total = 0;
			while (rs.next()) {
				total = rs.getInt("sumNum");
			}
			
			/** 得到总的记录数 end **/
	
			pageFinder = new PageFinder<SupplierContractVo>(query.getPage(), query.getPageSize(), total);
			// 排序的加上
			sql.append(whereSql.toString());
			sql.append(" order by c.effective_date desc limit " + (pageFinder.getStartOfPage()) + "," + pageFinder.getPageSize());
	
			ps = conn.prepareStatement(sql.toString());
	
			setSqlParams(ps, params.toArray());
			
			ResultSet rs2 = ps.executeQuery();
			List<SupplierContractVo> data = getSupplierContract(rs2);
			if (data.size() > 0) {
				pageFinder.setData(data);
			} else {
				pageFinder.setData(null);
			}
			if (null == pageFinder.getData() || pageFinder.getData().size() == 0) {
				return pageFinder;
			}
		}catch(Exception e){
			e.printStackTrace(); 
		}finally{
			if(rs!=null){
				rs.close();
			}
			if(conn!=null){
				conn.close();
			}
		}
		return pageFinder;
	}

	protected void setSqlParams(PreparedStatement ps, Object[] queryParams) throws SQLException {
		if (queryParams != null && queryParams.length > 0) {
			for (int i = 0; i < queryParams.length; i++) {
				Object code = queryParams[i];
				ps.setObject(i + 1, code);
			}
		}
	}

	private List<SupplierContractVo> getSupplierContract(ResultSet rs) throws SQLException{
		List<SupplierContractVo> result = new ArrayList<SupplierContractVo>();
		SupplierContractVo vo = null;
		String now = DateUtil.getDate(new Date(), "yyyy-MM-dd");
		while (rs.next()){
			vo = new SupplierContractVo();
			vo.setContractNo(rs.getString(1));
			vo.setSupplierCode(rs.getString(2));
			vo.setSupplier(rs.getString(3));
			vo.setSupplierType(rs.getString(4));
			vo.setClearingForm(rs.getInt(5));
			vo.setEffectiveDate(rs.getDate(6));
			vo.setFailureDate(rs.getDate(7));
			vo.setUpdateTime(rs.getString(8));
			vo.setUpdateUser(rs.getString(9));
			vo.setId(rs.getString(10));
			vo.setAttachment(rs.getString(11));
			//(now.compareTo(DateUtil.getDate(vo.getEffectiveDate(), "yyyy-MM-dd")) >= 0) && 
			if(now.compareTo(DateUtil.getDate(vo.getFailureDate(), "yyyy-MM-dd")) <= 0){
				vo.setContractState(1);
			}
			else {
				vo.setContractState(0);
			}
			result.add(vo);
			vo = null;
		}
		return result;
	}
}
