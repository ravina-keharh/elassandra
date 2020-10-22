package com.example.elassandra;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ElassandraApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElassandraApplication.class, args);
	}

	@Bean
	public RestHighLevelClient client(){   
		//final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		//credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials("elastic", "ravina"));
		//RestClientBuilder builder =RestClient.builder(new HttpHost("localhost", 9200, "http")).setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
		 RestClientBuilder builder =RestClient.builder(new HttpHost("localhost", 9200, "http"));
		 RestHighLevelClient client = new RestHighLevelClient(builder);
		    return client;
	}
	
	@GetMapping("/list-records")
	public SearchResponse fetchRecords() throws IOException {
		SearchRequest searchRequest = new SearchRequest("lab_form_records");
		//QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("identification_number", identificationNo);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		//sourceBuilder.query(QueryBuilders.matchAllQuery());
		sourceBuilder.query(new BoolQueryBuilder().must(QueryBuilders.matchQuery("gender", "Female")))
				/*.must(QueryBuilders.matchQuery("identification_number", "PL1234")))*/.from(0).size(2).sort("age", SortOrder.ASC);
		//sourceBuilder.query(new BoolQueryBuilder().must(QueryBuilders.matchQuery("identification_number", "PL1234")));
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse = client().search(searchRequest,RequestOptions.DEFAULT);
		return searchResponse;
	}
	
}


