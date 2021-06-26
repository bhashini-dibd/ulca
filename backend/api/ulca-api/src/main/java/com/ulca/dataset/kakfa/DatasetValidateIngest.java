package com.ulca.dataset.kakfa;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.ulca.dataset.model.Error;

@Service
public interface DatasetValidateIngest {

	default Error validateFileExistence(Map<String, String> fileMap) {

		Error error = null;

		if (!fileMap.containsKey("params.json") || fileMap.get("params.json").isBlank()) {

			error = new Error();
			error.setCause("params.json file not available");
			error.setMessage("params validation failed");
			error.setCode("1000_PARAMS_JSON_FILE_NOT_AVAILABLE");
			return error;

		}

		if (!fileMap.containsKey("data.json") || fileMap.get("data.json").isBlank()) {
			error = new Error();
			error.setCause("data.json file not available");
			error.setMessage("params validation failed");
			error.setCode("1000_DATA_JSON_FILE_NOT_AVAILABLE");

			return error;

		}

		return error;
	}

	public default JSONObject deepMerge(JSONObject source, JSONObject target) throws JSONException {
		for (String key : JSONObject.getNames(source)) {
			Object value = source.get(key);
			if (!target.has(key)) {
				// new value for "key":
				target.put(key, value);
			} else {
				// existing value for "key" - recursively deep merge:
				if (value instanceof JSONObject) {
					JSONObject valueJson = (JSONObject) value;
					deepMerge(valueJson, target.getJSONObject(key));
				} else {
					target.put(key, value);
				}
			}
		}
		return target;
	}

}
