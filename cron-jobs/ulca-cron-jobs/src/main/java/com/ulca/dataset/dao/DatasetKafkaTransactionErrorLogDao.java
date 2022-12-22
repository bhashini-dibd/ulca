package com.ulca.dataset.dao;

import com.ulca.dataset.model.DatasetKafkaTransactionErrorLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DatasetKafkaTransactionErrorLogDao extends MongoRepository<DatasetKafkaTransactionErrorLog, String> {


	List<DatasetKafkaTransactionErrorLog> findByFailedAndSuccess(boolean b, boolean c);

}
