package com.ulca.dataset.response;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ulca.dataset.model.TaskTracker;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
public class DatasetByServiceReqNrResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;
	private List<TaskTracker> data;

}
