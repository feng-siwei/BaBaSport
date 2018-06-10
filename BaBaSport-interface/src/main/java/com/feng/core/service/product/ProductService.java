package com.feng.core.service.product;

import java.util.List;

import com.feng.core.bean.product.Color;
import com.feng.core.bean.product.Product;

import cn.itcast.common.page.Pagination;

public interface ProductService {
	/**
	 * 分页查询
	 * @param pageNo 当前页
	 * @param name 查询名称
	 * @param brandId 品牌ID
	 * @param isShow 是否上架
	 * @return
	 */
	public Pagination selectPaginationByQuery(Integer pageNo,String name ,Long brandId,Boolean isShow);
	/**
	 * 加载颜色集合
	 * @return
	 */
	public List<Color> selectColorList() ;
	
	/**
	 * 添加商品
	 * @param product
	 */
	public void insertProduct(Product product);
}
