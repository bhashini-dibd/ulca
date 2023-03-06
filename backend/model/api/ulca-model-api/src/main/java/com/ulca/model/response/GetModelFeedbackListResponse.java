package com.ulca.model.response;

import java.util.List;

import com.ulca.model.dao.ModelFeedback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetModelFeedbackListResponse extends ModelFeedback {
	
	
	List<ModelFeedback> detailedFeedback;
	
}
