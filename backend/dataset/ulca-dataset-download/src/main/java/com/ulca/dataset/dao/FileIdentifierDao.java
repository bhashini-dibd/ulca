package com.ulca.dataset.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ulca.dataset.model.Fileidentifier;

@Repository
public interface FileIdentifierDao extends MongoRepository<Fileidentifier, String>{

	Fileidentifier findByMd5hash(String myChecksum);

}
