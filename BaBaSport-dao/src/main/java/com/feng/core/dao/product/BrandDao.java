package com.feng.core.dao.product;

import java.util.List;

import com.feng.core.bean.product.Brand;
import com.feng.core.bean.product.BrandQuery;

public interface BrandDao {
	/**
	 * 查询结果集
	 */
	public List<Brand> selectBrandListByQuery(BrandQuery brandQuery); 
	/**
	 * 查询符合条件的总条数
	 */
	public Integer selectCount(BrandQuery brandQuery); 
	/**
	 * 通过ID查询
	 */
	public Brand selectBrandById(Long id);
	/**
	 * 修改
	 */
	public void updateBrandById(Brand brand);
}
