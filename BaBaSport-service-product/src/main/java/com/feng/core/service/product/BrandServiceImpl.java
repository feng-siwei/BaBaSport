package com.feng.core.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feng.core.bean.product.Brand;
import com.feng.core.bean.product.BrandQuery;
import com.feng.core.dao.product.BrandDao;

import cn.itcast.common.page.Pagination;

@Service("brandService")
@Transactional
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandDao brandDao;
	//查询品牌分页对象
	@Override
	public Pagination selectPaginationByquery(String name, Boolean isDisplay, Integer pageNo) {
		BrandQuery brandQuery = new BrandQuery();
		
		//分页
		//将null或者负值转换1
		brandQuery.setPageNo(Pagination.cpn(pageNo));
		brandQuery.setPageSize(3);
		
		//分页显示条件
		StringBuilder params = new StringBuilder();
				
		//数据绑定
		if(null != name){
			brandQuery.createCriteria().andNameLike("%"+name+"%");
			params.append("name=").append(name);
		}
		if(null != isDisplay){
			brandQuery.createCriteria().andIsDisplayEqualTo(isDisplay);
			params.append("&isDisplay=").append(isDisplay);
		}else{
			params.append("&isDisplay=").append(true);
		}
		
		
		//新建分页结果集
		Pagination pagination = new Pagination(brandQuery.getPageNo(),brandQuery.getPageSize(),brandDao.countByExample(brandQuery));
		//设定结果集
		pagination.setList(brandDao.selectByExample(brandQuery));
		//分页展示
		String url = "/brand/list.do";

		pagination.pageView(url, params.toString());

		
		return pagination;
	}
	//id→品牌
	@Override
	public Brand selectBrandById(Long id) {
		return brandDao.selectByPrimaryKey(id);
	}
	//修改
	@Override
	public void updateBrandById(Brand brand) {
		
		brandDao.updateByPrimaryKey(brand);
		
	}
	//批量删除
	@Override
	public void deletes(List<Long> ids) {
		BrandQuery brandQuery = new BrandQuery();
		brandQuery.createCriteria().andIdIn(ids);
		brandDao.deleteByExample(brandQuery);
	}
	
	//根据条件查找
	@Override
	public List<Brand> selectBrandByQuery(BrandQuery brandQuery) {
		return brandDao.selectByExample(brandQuery);
	}
	@Override
	public List<Brand> selectDisplayBrand() {
		BrandQuery brandQuery = new BrandQuery();
		brandQuery.createCriteria().andIsDisplayEqualTo(true);
		return brandDao.selectByExample(brandQuery);
	}

}
