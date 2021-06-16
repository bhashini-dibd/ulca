package com.ulca.dataset.model.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.model.CollectionDetailsAudioAutoAligned;
import io.swagger.model.CollectionDetailsMachineGeneratedTranscript;
import io.swagger.model.CollectionDetailsManualTranscribed;
import io.swagger.model.OneOfCollectionMethodAudioCollectionDetails;

public class OneOfCollectionMethodAudioCollectionDetailsDeserializer extends JsonDeserializer<OneOfCollectionMethodAudioCollectionDetails> {

    @Override
    public OneOfCollectionMethodAudioCollectionDetails deserialize(JsonParser parser, DeserializationContext ctxt)
        throws IOException, JsonMappingException {

    	OneOfCollectionMethodAudioCollectionDetails collectionMethod = null;
    	JsonNode node = parser.readValueAsTree();
    	
    	System.out.println(node.toPrettyString());
    	
    	
    		String val = node.toPrettyString();
    		if(val.contains("alignmentTool")) {
    			CollectionDetailsAudioAutoAligned collectionDetailsAudioAutoAligned = new CollectionDetailsAudioAutoAligned();
    			
    			collectionMethod = collectionDetailsAudioAutoAligned;
    			
    		}else if(val.contains("asrModel")) {
    			
    			System.out.println("creating CollectionDetailsMachineGeneratedTranscript object");
    			CollectionDetailsMachineGeneratedTranscript collectionDetailsMachineGeneratedTranscript = new CollectionDetailsMachineGeneratedTranscript();
    			collectionDetailsMachineGeneratedTranscript.setAsrModel(node.get("asrModel").asText());
    			System.out.println(node.get("asrModel").asText());
    			collectionMethod = collectionDetailsMachineGeneratedTranscript;
    			
    		}else if(val.contains("contributor")) {
    			collectionMethod = new CollectionDetailsManualTranscribed();
    		}
    		
    	
    		return collectionMethod;
    }

	
}