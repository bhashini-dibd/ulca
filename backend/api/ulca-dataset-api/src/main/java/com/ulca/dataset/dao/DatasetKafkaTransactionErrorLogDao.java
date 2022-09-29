package com.ulca.dataset.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ulca.dataset.model.DatasetKafkaTransactionErrorLog;


@Repository
public interface DatasetKafkaTransactionErrorLogDao extends MongoRepository<DatasetKafkaTransactionErrorLog, String> {


	List<DatasetKafkaTransactionErrorLog> findByFailedAndSuccess(boolean b, boolean c);

}
