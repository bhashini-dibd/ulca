package com.ulca.dataset.dao;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.ulca.dataset.model.Fileidentifier;

public interface FileIdentifierDao extends MongoRepository<Fileidentifier, String>{

}
