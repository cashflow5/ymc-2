package com.belle.other.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.belle.finance.costsettlement.costsetofbooks.model.vo.CostSetofBooks;

@Repository
public interface ICostSetOfBooksDao {
	
	/**
	 * 查询所有成本帐套信息【未删除】
	 * @return
	 * @throws Exception
	 */
	public List<CostSetofBooks> queryAllCostSetOfBooks() throws Exception ;
	
	/**
	 * 查询所有的成本帐套
	 * 
	 * isTemp = true 排除临时帐套的
	 * @param isTemp
	 * @return
	 * @throws Exception
	 */
	public List<CostSetofBooks> queryAllCostSetOfBooks(boolean isTemp) throws Exception ;
	/**
	 * 查询所有买断的成本帐套
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<CostSetofBooks> queryAllBuyOutCostSetOfBooks() throws Exception ;
	
	/**
	 * 按Id查询
	 * @param id
	 * @return
	 */
	public CostSetofBooks queryCostSetOfBooksById(String id) throws Exception ;
	
	/**
	 * 按帐套编码查询帐套信息
	 * @param setOfBooksCode
	 * @return
	 * @throws Exception
	 */
	public CostSetofBooks queryCostSetofBooksByCode(String setOfBooksCode) throws Exception;
	
}
