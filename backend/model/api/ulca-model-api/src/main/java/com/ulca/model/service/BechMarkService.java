package com.ulca.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ulca.model.dao.BenchMarkDao;
import com.ulca.model.dao.BenchMarkModel;
import com.ulca.model.dao.ModelExtended;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class BechMarkService {


	@Autowired
	BenchMarkDao benchmarkdao;

	

	public BenchMarkModel Submitmodel(BenchMarkModel model) {

		benchmarkdao.save(model);
		return model;
	}
}
