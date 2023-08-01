/*
 * package com.ulca.benchmark.util;
 * 
 * import org.junit.jupiter.params.ParameterizedTest; import
 * org.junit.jupiter.params.provider.Arguments; import
 * org.junit.jupiter.params.provider.MethodSource;
 * 
 * import java.util.ArrayList; import java.util.Arrays; import java.util.List;
 * import java.util.stream.Stream;
 * 
 * import static org.junit.jupiter.api.Assertions.*;
 * 
 * class ModelConstantsTest {
 * 
 * 
 * private static Stream<Arguments> getMetricListByModelTaskParam(){
 * 
 * String[] metric1 = { "bleu", "meteor", "ribes", "gleu", "bert","chrf" };
 * String[] metric2 = { "cer", "top-1 accuracy", "top-5 accuracy"}; String[]
 * metric3 = { "wer", "cer" }; String[] metric4 = { "wer", "cer" }; String[]
 * metric5 = { "wer"}; String[] metric6 = { "precision", "recall", "h1-mean" };
 * 
 * return Stream.of( Arguments.of("translation",new
 * ArrayList<>(Arrays.asList(metric1))), Arguments.of("transliteration",new
 * ArrayList<>(Arrays.asList(metric2))), Arguments.of("asr",new
 * ArrayList<>(Arrays.asList(metric3))), Arguments.of("ocr",new
 * ArrayList<>(Arrays.asList(metric4))), Arguments.of("tts",new
 * ArrayList<>(Arrays.asList(metric5))), Arguments.of("document-layout",new
 * ArrayList<>(Arrays.asList(metric6))), Arguments.of("",null)
 * 
 * 
 * ); }
 * 
 * @ParameterizedTest
 * 
 * @MethodSource("getMetricListByModelTaskParam") void
 * getMetricListByModelTask(String task, List<String> list) { ModelConstants
 * modelConstants = new ModelConstants();
 * assertEquals(list,modelConstants.getMetricListByModelTask(task)); } }
 */