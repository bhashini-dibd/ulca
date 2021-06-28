//package io.swagger.model;
//
//import java.io.IOException;
//
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.JsonNode;
//
//public class ModelDeserializer extends JsonDeserializer<OneOfParallelDatasetCollectionMethodCollectionDetails> {
//
//	@Override
//	public OneOfParallelDatasetCollectionMethodCollectionDetails deserialize(JsonParser parser,
//			DeserializationContext ctxt) throws IOException, JsonMappingException {
//
//		OneOfParallelDatasetCollectionMethodCollectionDetails collectionMethod = null;
//		JsonNode node = parser.readValueAsTree();
//
//		String val = node.toPrettyString();
//		if (val.contains("auto-aligned")) {
//			collectionMethod = new CollectionDetailsAutoAligned();
//		} else if (val.contains("machine-translated")) {
//
//			collectionMethod = new CollectionDetailsMachineTranslated();
//
//		} else if (val.contains("machine-translated-post-edited")) {
//			collectionMethod = new CollectionDetailsMachineTranslatedPostEdited();
//		} else if (val.contains("manual-translated")) {
//			collectionMethod = new CollectionDetailsManualTranslated();
//		}
//
//		return collectionMethod;
//	}
//
//}