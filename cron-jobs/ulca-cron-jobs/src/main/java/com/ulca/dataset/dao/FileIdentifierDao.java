package com.ulca.dataset.dao;

import com.ulca.dataset.model.Fileidentifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileIdentifierDao extends MongoRepository<Fileidentifier, String>{

	Fileidentifier findByMd5hash(String myChecksum);

}
