package com.ulca.model.response.pipeline.script;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.model.SupportedLanguages;
import io.swagger.model.SupportedScripts;
import io.swagger.model.LanguagePair;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * LanguageSchema
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T08:00:21.046011704Z[GMT]")

public class PipelineResponseLanguageSchemaWithScript {
	@JsonProperty("sourceLanguage")
	private SupportedLanguages sourceLanguage = null;

	@JsonProperty("sourceScriptCode")
	private SupportedScripts sourceScriptCode = null;

	@JsonProperty("targetLanguageList")
	@Valid
	private List<LanguagePair> targetLanguageList = null;
	// List of Language Pairs

	public PipelineResponseLanguageSchemaWithScript sourceLanguage(SupportedLanguages sourceLanguage) {
		this.sourceLanguage = sourceLanguage;
		return this;
	}

	/**
	 * Get sourceLanguage
	 * 
	 * @return sourceLanguage
	 **/
	@Schema(description = "")

	@Valid
	public SupportedLanguages getSourceLanguage() {
		return sourceLanguage;
	}

	public void setSourceLanguage(SupportedLanguages sourceLanguage) {
		this.sourceLanguage = sourceLanguage;
	}

	public SupportedScripts getSourceScriptCode() {
		return sourceScriptCode;
	}

	public void setSourceScriptCode(SupportedScripts sourceScriptCode) {
		this.sourceScriptCode = sourceScriptCode;
	}

	public PipelineResponseLanguageSchemaWithScript targetLanguageList(List<LanguagePair> targetLanguageList) {
		this.targetLanguageList = targetLanguageList;
		return this;
	}

	public PipelineResponseLanguageSchemaWithScript addTargetLanguageListItem(LanguagePair targetLanguageListItem) {
		if (this.targetLanguageList == null) {
			this.targetLanguageList = new ArrayList<LanguagePair>();
		}
		this.targetLanguageList.add(targetLanguageListItem);
		return this;
	}

	/**
	 * list of
	 * 
	 * @return targetLanguageList
	 **/
	@Schema(description = "list of")
	@Valid
	public List<LanguagePair> getTargetLanguageList() {
		return targetLanguageList;
	}

	public void setTargetLanguageList(List<LanguagePair> targetLanguageList) {
		this.targetLanguageList = targetLanguageList;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PipelineResponseLanguageSchemaWithScript languageSchema = (PipelineResponseLanguageSchemaWithScript) o;
		return Objects.equals(this.sourceLanguage, languageSchema.sourceLanguage)
				&& Objects.equals(this.sourceScriptCode, languageSchema.sourceScriptCode)
				&& Objects.equals(this.targetLanguageList, languageSchema.targetLanguageList);
	}

	@Override
	public int hashCode() {
		return Objects.hash(sourceLanguage,sourceScriptCode, targetLanguageList);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class LanguageSchema {\n");

		sb.append("    sourceLanguage: ").append(toIndentedString(sourceLanguage)).append("\n");
		sb.append("    sourceScriptCode: ").append(toIndentedString(sourceScriptCode)).append("\n");
		sb.append("    targetLanguageList: ").append(toIndentedString(targetLanguageList)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
