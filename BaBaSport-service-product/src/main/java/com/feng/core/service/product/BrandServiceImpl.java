package com.feng.core.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feng.core.bean.product.BrandQuery;
import com.feng.core.dao.product.BrandDao;

import cn.itcast.common.page.Pagination;

@Service("brandService")
@Transactional
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandDao brandDao;
	@Override
	public Pagination selectPaginationByquery(String name, Integer isDisplay, Integer pageNo) {
		BrandQuery brandQuery = new BrandQuery();
		//将null或者负值转换1
		brandQuery.setPageNo(Pagination.cpn(pageNo));
		brandQuery.setPageSize(3);
		//新建分页结果集
		Pagination pagination = new Pagination(brandQuery.getPageNo(),brandQuery.getPageSize(),brandDao.selectCount(brandQuery));
		//设定结果集
		pagination.setList(brandDao.selectBrandListByQuery(brandQuery));
		return pagination;
	}

}
