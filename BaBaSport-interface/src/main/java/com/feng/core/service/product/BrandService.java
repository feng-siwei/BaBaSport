package com.feng.core.service.product;

import com.feng.core.bean.product.Brand;

import cn.itcast.common.page.Pagination;

public interface BrandService {
	/**
	 * 查询品牌分页对象
	 * @param name 品牌名称 支持模糊查询
	 * @param isDisplay 是否可用
	 * @param pageNo 当前页数
	 * @return 分页集合
	 */
	public Pagination selectPaginationByquery(String name , Integer isDisplay,Integer pageNo); 
	
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
}
