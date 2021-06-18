package com.ulca.dataset.model.deserializer;

public enum AsrRowDataSchemaKeys {

	//required
		audioFilename,
		text,
		endTime,
		
		//optional
		channel,
		samplingRate,
		bitsPerSample,
		gender,
		age,
		dialect,
		snr,
		startTime,
		collectionMethod
	
}
