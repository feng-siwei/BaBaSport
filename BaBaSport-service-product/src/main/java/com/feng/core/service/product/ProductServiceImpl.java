package com.feng.core.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.feng.core.bean.product.Color;
import com.feng.core.bean.product.ColorQuery;
import com.feng.core.bean.product.Product;
import com.feng.core.bean.product.ProductQuery;
import com.feng.core.bean.product.ProductQuery.Criteria;
import com.feng.core.dao.product.ColorDao;
import com.feng.core.dao.product.ProductDao;

import cn.itcast.common.page.Pagination;

@Service("productService")
@Transactional
@RequestMapping(value="/product")
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductDao productDao;
	@Autowired
	private ColorDao colorDao;
	
	//分页
	@Override
	@RequestMapping(value="/list.do")
	public Pagination selectPaginationByQuery(Integer pageNo, String name, Long brandId, Boolean isShow) {
		ProductQuery productQuery = new ProductQuery();
		//分页
		productQuery.setPageNo(Pagination.cpn(pageNo));
		//参数绑定
		Criteria criteria = productQuery.createCriteria();
		//分页URL帮参
		StringBuilder params = new StringBuilder();
		if (null != name) {
			criteria.andNameLike("%"+name+"%");
			params.append("name=").append(name);
		}
		if (null != brandId) {
			criteria.andBrandIdEqualTo(brandId);
			params.append("&brandId=").append(brandId);
		}
		if (null != isShow) {
			criteria.andIsShowEqualTo(isShow);
			params.append("&isShow=").append(isShow);
		}else {
			criteria.andIsShowEqualTo(false);
			params.append("&isShow=").append(false);
		}
		//倒序
		productQuery.setOrderByClause("id DESC");
		
		List<Product> products = productDao.selectByExample(productQuery);
		int count = productDao.countByExample(productQuery);
		Pagination pagination = new Pagination(
				productQuery.getPageNo(),
				productQuery.getPageSize(), 
				count,
				products);
		//分页展示
		String url = "/product/list.do";
		
		pagination.pageView(url, params.toString());
		
		return pagination;
	}
	
	//加载颜色集合
	public List<Color> selectColorList() {
		ColorQuery colorQuery = new ColorQuery();
		colorQuery.createCriteria().andParentIdNotEqualTo(0L);		
		return colorDao.selectByExample(colorQuery);
	}
	
}
