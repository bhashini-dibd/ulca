package com.ulca.dataset.model.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.model.OneOfAudioQualityEvaluationMethodDetails;
import io.swagger.model.WadaSnr;

public class OneOfAudioQualityEvaluationMethodDetailsDeserializer extends JsonDeserializer<OneOfAudioQualityEvaluationMethodDetails> {

    @Override
    public OneOfAudioQualityEvaluationMethodDetails deserialize(JsonParser parser, DeserializationContext ctxt)
        throws IOException, JsonMappingException {

    	OneOfAudioQualityEvaluationMethodDetails collectionMethod = null;
    	JsonNode node = parser.readValueAsTree();
    	
    	System.out.println(node.toPrettyString());
    	
    	
    		String val = node.toPrettyString();
    		if(val.contains("snr")) {
    			
    			System.out.println("creating object for WadaSnr.class");
    			WadaSnr wadaSnr = new WadaSnr();
    			
    			collectionMethod = wadaSnr;
    			
    		}
    		
    	
    		return collectionMethod;
    }

	
}