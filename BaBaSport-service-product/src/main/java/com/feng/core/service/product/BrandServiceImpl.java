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
		//数据绑定
		brandQuery.setName(name);
		brandQuery.setIsDisplay(isDisplay);
		//分页
		//将null或者负值转换1
		brandQuery.setPageNo(Pagination.cpn(pageNo));
		brandQuery.setPageSize(3);

		StringBuilder params = new StringBuilder();

		//条件
		if(null != name){
			brandQuery.setName(name);
			params.append("name=").append(name);
		}
		if(null != isDisplay){
			brandQuery.setIsDisplay(isDisplay);
			params.append("&isDisplay=").append(isDisplay);
		}else{
			brandQuery.setIsDisplay(1);
			params.append("&isDisplay=").append(1);
		}

		
		//新建分页结果集
		Pagination pagination = new Pagination(brandQuery.getPageNo(),brandQuery.getPageSize(),brandDao.selectCount(brandQuery));
		//设定结果集
		pagination.setList(brandDao.selectBrandListByQuery(brandQuery));
		//分页展示
		String url = "/brand/list.do";

		pagination.pageView(url, params.toString());

		
		return pagination;
	}

}
