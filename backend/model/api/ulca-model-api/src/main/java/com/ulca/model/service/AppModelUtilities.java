package com.ulca.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ulca.model.dao.ModelDao;
import com.ulca.model.dao.ModelExtended;
import com.ulca.model.dao.PipelineModel;
import com.ulca.model.response.AppModelService;

import io.swagger.pipelinemodel.ConfigSchema;
import io.swagger.pipelinemodel.TaskSpecification;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AppModelUtilities {
	@Autowired
	ModelService modelService;

	public List<AppModelService> getAllModelServicesOfIndividualTaskType(String taskType,
			List<PipelineModel> pipelineModels) {
		log.info("task type to be search :: " + taskType);
		List<AppModelService> services = new ArrayList<AppModelService>();

		Map<String, AppModelService> modelsMap = new HashMap<String, AppModelService>();
		for (PipelineModel pipelineModel : pipelineModels) {
			for (TaskSpecification taskSpecification : pipelineModel.getTaskSpecifications()) {
				log.info("task type of pipeline :: " + taskSpecification.getTaskType().name());
				if (taskSpecification.getTaskType().name().toLowerCase().equals(taskType)) {
					for (ConfigSchema configSchema : taskSpecification.getTaskConfig()) {
						AppModelService appModelService = new AppModelService();
						appModelService.setServiceId(configSchema.getServiceId());
						appModelService.setSourceLanguage(configSchema.getSourceLanguage());
						appModelService.setSourceScriptCode(configSchema.getSourceScriptCode());
						appModelService.setTargetLanguage(configSchema.getTargetLanguage());
						appModelService.setTargetScriptCode(configSchema.getTargetScriptCode());

						modelsMap.put(configSchema.getModelId(), appModelService);
					}
				}
			}
		}
		if (modelsMap != null) {

			List<ModelExtended> models = modelService.findModelsByIds(modelsMap.keySet());
			for (ModelExtended model : models) {
				AppModelService service = modelsMap.get(model.getModelId());
				service.setName(model.getName());
				service.setDescription(model.getDescription());
				modelsMap.put(model.getModelId(), service);
			}

			services = new ArrayList<AppModelService>(modelsMap.values());
		}

		return services;

	}

}
