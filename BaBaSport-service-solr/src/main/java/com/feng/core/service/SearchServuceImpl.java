package com.feng.core.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feng.core.bean.product.Product;
import com.feng.core.bean.product.ProductQuery;
import com.feng.core.bean.product.Sku;
import com.feng.core.bean.product.SkuQuery;
import com.feng.core.dao.product.ProductDao;
import com.feng.core.dao.product.SkuDao;

import cn.itcast.common.page.Pagination;

/**
 * 全文检索 使用SOLR
 * @author 冯思伟
 *
 */
@Service("searchServuce")
public class SearchServuceImpl implements SearchServuce {
	@Autowired
	private SolrServer solrServer;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private SkuDao skuDao;
	
	@Override
	public Pagination selectPaginationByQuery(String keyword,Integer pageNo, Long brandId, String price) throws Exception{
		//创建分页包装类
		ProductQuery productQuery =new ProductQuery();
		//当前页
		productQuery.setPageNo(Pagination.cpn(pageNo));
		//每页显示12条
		productQuery.setPageSize(12);
		//拼接条件
		StringBuilder params = new StringBuilder();
		params.append("keyword=").append(keyword);
		//查询集合
		List<Product> products = new ArrayList<>();
		
		//solr查询
		SolrQuery solrQuery = new SolrQuery();
		//关键词
		solrQuery.set("q", "name_ik:"+keyword);
		//过滤条件
		//商品品牌
		if (null != brandId) {
			solrQuery.addFilterQuery("brandId:"+brandId);
			
		}
		//价格
		if (null != price) {
			String[] p = price.split("-");
			if (p.length == 2) {
				solrQuery.addFilterQuery("price:["+ p[0] +" TO "+ p[1] +"]");
			}else {
				solrQuery.addFilterQuery("price:["+ p[0] +" TO *]");				
				
			}
			
		}
		
		//分页
		solrQuery.setStart(productQuery.getStartRow());
		solrQuery.setRows(productQuery.getPageSize());
		//高亮
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("name_ik");
		solrQuery.setHighlightSimplePre("<span style='color:red'>");
		solrQuery.setHighlightSimplePost("</span>");
		//排序
		solrQuery.addSort("price", ORDER.asc);
		
		
		//执行查询
		QueryResponse response =  solrServer.query(solrQuery);
		
		//取高亮集合
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting(); 
		/*
		 * 第一个map k:v 商品ID : 第二个map
		 *                       第二个map    k:v      name_ik :商品名称 
		 */
		
		//结果集
		SolrDocumentList docs = response.getResults();
		//总条数
		long numFpimd = docs.getNumFound();
		for (SolrDocument doc : docs) {
			//创建商品对象
			Product product = new Product();
			//商品ID
			String id = (String) doc.get("id");
			product.setId(Long.parseLong(id));
			//商品名称
//			String name = (String) doc.get("name_ik");
//			product.setName(name);
//			高亮版
			Map<String, List<String>> map = highlighting.get(id);
			List<String> list = map.get("name_ik");
			product.setName(list.get(0));
			//图片
			String url = (String) doc.get("url");
			product.setImgUrl(url);
			//价格销售
			Float minPrice = (Float) doc.get("price");
			product.setMinPrice(minPrice);
			//品牌ID long
			Integer brandIntId  = (Integer) doc.get("brandId");
			if (brandIntId != null) {
				product.setBrandId(brandIntId.longValue());
			}
				
			
			products.add(product);
		}
		//构建分页对象
		Pagination pagination = new Pagination(
				productQuery.getPageNo(),
				productQuery.getPageSize(),
				(int) numFpimd,
				products
				);
		//页面展示
		String url = "/search";
		pagination.pageView(url, params.toString());
		
		return pagination;
	}
	
	//保存商品信息到Solr服务器
	public void insertProductToSolr(Long id) {
		
		SolrInputDocument doc = new SolrInputDocument();
		Product p = productDao.selectByPrimaryKey(id);
		//商品ID
		doc.setField("id", id);
		//图片
		doc.setField("url", p.getImages()[0]);
		//商品名称
		doc.setField("name_ik", p.getName());
		//价格
		 SkuQuery skuQuery = new SkuQuery();
		 skuQuery.createCriteria().andProductIdEqualTo(id);
		 skuQuery.setOrderByClause("price asc");
		 skuQuery.setPageNo(1);
		 skuQuery.setPageSize(1);
		 skuQuery.setFields("price");
		 List<Sku> skus = skuDao.selectByExample(skuQuery);
		 doc.setField("price", skus.get(0).getPrice());
		 //品牌ID
		 doc.setField("brandId", p.getBrandId());
		 
		 
		try {
			solrServer.add(doc);
			solrServer.commit();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}
	
	
	

}
