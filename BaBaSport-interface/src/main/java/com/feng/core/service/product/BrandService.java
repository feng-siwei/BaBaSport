package com.feng.core.service.product;

import java.util.List;

import com.feng.core.bean.product.Brand;
import com.feng.core.bean.product.BrandQuery;

import cn.itcast.common.page.Pagination;

public interface BrandService {
	/**
	 * 查询品牌分页对象
	 * @param name 品牌名称 支持模糊查询
	 * @param isDisplay 是否可用
	 * @param pageNo 当前页数
	 * @return 分页集合
	 */
	public Pagination selectPaginationByquery(String name , Boolean isDisplay,Integer pageNo); 
	
	/**
	 * 根据条件 查找品牌
	 * @param id
	 * @return
	 */
	public List<Brand> selectBrandByQuery(BrandQuery brandQuery);
	
	/**
	 * 查询可用品牌
	 * @return
	 */
	public List<Brand> selectDisplayBrand();
	
	/**
	 * 更具ID查找品牌
	 * @param id
	 * @return
	 */
	public Brand selectBrandById(Long id);
	
	/**
	 * 修改
	 * @param brand
	 * @return
	 */
	public void updateBrandById(Brand brand);
	
	/**
	 * 批量删除
	 * @param ids 批量删除的id list
	 * 
	 */
	public void deletes(List<Long> ids);

	/**
	 * 从Redis数据库中查询可用品牌id与名称
	 * @return
	 */
	public List<Brand> selectBrandListFromRedis();
}
