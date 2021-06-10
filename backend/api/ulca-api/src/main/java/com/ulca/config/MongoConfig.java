//package com.ulca.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.MongoDatabaseFactory;
//import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
//
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//
//@Configuration
//public class MongoConfig {
//	
//	
//
//	@Value(value = "${ULCA_MONGO_CLUSTER}")
//    private String mongodbUri;
//	
//	
//	@Value(value = "${ULCA_PROC_TRACKER_DB}")
//    private String database;
//
//	 public @Bean MongoClient mongoClient() {
//	       return MongoClients.create(mongodbUri);
//	   }
//
//	 public @Bean MongoDatabaseFactory mongoDatabaseFactory() {
//		    return new SimpleMongoClientDatabaseFactory(MongoClients.create(), database);
//		  }
//
//}
