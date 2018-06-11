package com.feng.core.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feng.core.bean.product.Product;

/**
 * 全文检索 使用SOLR
 * @author 冯思伟
 *
 */
@Service("searchServuce")
public class SearchServuceImpl implements SearchServuce {
	@Autowired
	private SolrServer solrServer;
	
	public List<Product> selectProducListByQuery(String keyword) throws Exception{
		List<Product> products = new ArrayList<>();
		
		SolrQuery solrQuery = new SolrQuery();
		//关键词
		solrQuery.set("q", "name_ik:"+keyword);
		
		//执行查询
		QueryResponse response =  solrServer.query(solrQuery);
		
		//结果集
		SolrDocumentList docs = response.getResults();
		for (SolrDocument doc : docs) {
			//创建商品对象
			Product product = new Product();
			//商品ID
			String id = (String) doc.get("id");
			product.setId(Long.parseLong(id));
			//商品名称
			String name = (String) doc.get("name_ik");
			product.setName(name);
			//图片
			String url = (String) doc.get("url");
			product.setImgUrl(url);
			//价格销售
			Float price = (Float) doc.get("price");
			product.setMinPrice(price);
			//品牌ID long
			Integer brandId  = (Integer) doc.get("brandId");
			product.setBrandId(brandId.longValue());
			
			products.add(product);
		}
		
		 
		return products;
	}
}
