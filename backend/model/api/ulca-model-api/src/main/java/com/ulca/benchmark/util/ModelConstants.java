package com.ulca.benchmark.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public final class ModelConstants {

	public List<String> getMetricListByModelTask(String task) {
		List<String> list = null;
		if (task.equalsIgnoreCase("translation")) {


			/*
			 * rouge has been temporarily removed
			 */
			String[] metric = { "bleu", "meteor", "ribes", "gleu", "bert", "chrf" };

			list = new ArrayList<>(Arrays.asList(metric));
			return list;
		}
		if (task.equalsIgnoreCase("transliteration")) {
			String[] metric = { "cer", "top-1 accuracy", "top-5 accuracy"};
			list = new ArrayList<>(Arrays.asList(metric));
			return list;
		}
		if (task.equalsIgnoreCase("asr")) {
			String[] metric = { "wer", "cer" };
			list = new ArrayList<>(Arrays.asList(metric));
			return list;
		}
		if (task.equalsIgnoreCase("ocr")) {

			String[] metric = { "wer", "cer" };
			list = new ArrayList<>(Arrays.asList(metric));
			return list;
		}
		if (task.equalsIgnoreCase("tts")) {

			String[] metric = { "wer" };
			list = new ArrayList<>(Arrays.asList(metric));
			return list;
		}
		if (task.equalsIgnoreCase("document-layout")) {
			String[] metric = { "precision", "recall", "h1-mean" };
			list = new ArrayList<>(Arrays.asList(metric));
			return list;
		}
		return list;
	}

}
