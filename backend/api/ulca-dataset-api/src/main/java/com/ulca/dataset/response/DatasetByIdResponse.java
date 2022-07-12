package com.ulca.dataset.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import com.ulca.dataset.model.TaskTracker;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DatasetByIdResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;
	private Map<String, ArrayList<TaskTracker>> data;

}
