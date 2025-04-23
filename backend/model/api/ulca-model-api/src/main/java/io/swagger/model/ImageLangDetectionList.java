package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.ImageFile;
import io.swagger.model.LangDetectionPrediction;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ImageLangDetectionList
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-04-16T09:37:42.477810161Z[GMT]")


public class ImageLangDetectionList   {
  @JsonProperty("image")

  private ImageFile image = null;

  @JsonProperty("langPrediction")
  @Valid
  private List<LangDetectionPrediction> langPrediction = new ArrayList<LangDetectionPrediction>();

  public ImageLangDetectionList image(ImageFile image) { 

    this.image = image;
    return this;
  }

  /**
   * Get image
   * @return image
   **/
  
  @Schema(required = true, description = "")
  
@Valid
  @NotNull
  public ImageFile getImage() {  
    return image;
  }



  public void setImage(ImageFile image) { 

    this.image = image;
  }

  public ImageLangDetectionList langPrediction(List<LangDetectionPrediction> langPrediction) { 

    this.langPrediction = langPrediction;
    return this;
  }

  public ImageLangDetectionList addLangPredictionItem(LangDetectionPrediction langPredictionItem) {
    this.langPrediction.add(langPredictionItem);
    return this;
  }

  /**
   * list of
   * @return langPrediction
   **/
  
  @Schema(required = true, description = "list of")
  
@Valid
  @NotNull
  public List<LangDetectionPrediction> getLangPrediction() {  
    return langPrediction;
  }



  public void setLangPrediction(List<LangDetectionPrediction> langPrediction) { 

    this.langPrediction = langPrediction;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ImageLangDetectionList imageLangDetectionList = (ImageLangDetectionList) o;
    return Objects.equals(this.image, imageLangDetectionList.image) &&
        Objects.equals(this.langPrediction, imageLangDetectionList.langPrediction);
  }

  @Override
  public int hashCode() {
    return Objects.hash(image, langPrediction);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ImageLangDetectionList {\n");
    
    sb.append("    image: ").append(toIndentedString(image)).append("\n");
    sb.append("    langPrediction: ").append(toIndentedString(langPrediction)).append("\n");
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
