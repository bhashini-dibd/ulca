package com.ulca.benchmark.dao;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ulca.model.dao.ModelExtended;

import io.swagger.model.Benchmark;
import io.swagger.model.LanguagePair;
import io.swagger.model.ModelTask;

@Repository
public interface BenchmarkDao extends MongoRepository<Benchmark, String> {

	Benchmark findByBenchmarkId(String benchmarkId);
	List<Benchmark> findByTask(ModelTask task);
	Page<Benchmark> findByUserId(String userId, Pageable paging);
	List<Benchmark> findByUserId(String userId);
	
	Benchmark findByName(String name);
	List<Benchmark> findByTaskAndLanguages(@NotNull @Valid ModelTask task, LanguagePair lp);
	List<Benchmark> findByTaskAndLanguages(ModelTask modelTask, LanguagePair lp, Pageable paging);
	List<Benchmark> findByTask(ModelTask modelTask, Pageable paging);
	List<Benchmark> findByLanguages(LanguagePair lp, Pageable paging);
	List<Benchmark> findByLanguages(LanguagePair lp);

	Integer countByUserId(String userId);

	Integer countByUserIdAndName(String userId,String name);
	
}
