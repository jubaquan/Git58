package cn.itcast.solar.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

public class SolarTest {
	
	private HttpSolrServer httpSolrServer;

	// 提取HttpSolrServer创建
	@Before
	public void init() {
		// 1. 创建HttpSolrServer对象
		// 设置solr服务接口,浏览器客户端地址http://127.0.0.1:8081/solr/#/
		String baseURL = "http://127.0.0.1:8081/solr/";
		this.httpSolrServer = new HttpSolrServer(baseURL);
	}
	
	
	@Test
	public void testCreateAndUpdateIndex() throws SolrServerException, IOException{
		//1、创建HttpSolrServer对象，通过它和Solr服务器建立连接。
		// 设置solr服务接口,浏览器客户端地址http://127.0.0.1:8081/solr/#/
		//2、创建SolrInputDocument对象，然后通过它来添加域。
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "c1001");
		document.addField("content", "hello world");
		//3、通过HttpSolrServer对象将SolrInputDocument添加到索引库。
		httpSolrServer.add(document);
		//4、提交。
		httpSolrServer.commit();
	}
	
	@Test
	public void deleteIndex() throws SolrServerException, IOException{
		httpSolrServer.deleteById("c1001");
		httpSolrServer.commit();
	}
	
	
	@Test
	public void testSearchIndex() throws SolrServerException{
		//创建搜索对象
		SolrQuery solrQuery = new SolrQuery();
		//设置搜索条件
		solrQuery.setQuery("*:*");
		//发起搜索请求
		QueryResponse reponse = this.httpSolrServer.query(solrQuery);
		//处理搜索结果
		SolrDocumentList results = reponse.getResults();
		System.out.println("搜索到的结果总数：" + results.getNumFound());
		//遍历搜索结果
		for (SolrDocument solrDocument : results) {
			System.out.println("----------------------------------------------------");

			System.out.println("id：" + solrDocument.get("id"));
			System.out.println("content" + solrDocument.get("content"));
		}
	}
	
}
