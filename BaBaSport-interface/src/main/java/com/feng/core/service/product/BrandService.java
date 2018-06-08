package com.feng.core.service.product;

import cn.itcast.common.page.Pagination;

public interface BrandService {
	public Pagination selectPaginationByquery(String name , Integer isDisplay,Integer pageNo); 
}
