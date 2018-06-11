package com.feng.core.service;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application-context.xml"})
public class TestRedis {
	
	
	@Test
	public void testSolr() throws Exception {
		String baseURL = "http://192.168.200.128:8080/solr";
		SolrServer solrServer = new HttpSolrServer(baseURL);
		SolrInputDocument doc = new SolrInputDocument();
		doc.setField("id", 3);
		doc.setField("name","防水袋");
		
		solrServer.add(doc);
		
		solrServer.commit();
		
	}
	
	@Autowired
	private SolrServer solrServer2;
	
	@Test
	public void testSolSpringr() throws Exception {
		
		SolrInputDocument doc = new SolrInputDocument();
		doc.setField("id", 4);
		doc.setField("name","彻夜抹抹");
		
		solrServer2.add(doc);
		
		solrServer2.commit();
		
	}
}
