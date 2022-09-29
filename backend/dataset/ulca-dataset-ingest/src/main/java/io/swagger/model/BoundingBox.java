package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.Vertex;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * array of vertex with clockwise coordinate arrangement
 */
@Schema(description = "array of vertex with clockwise coordinate arrangement")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-06-09T08:20:20.072Z[GMT]")


public class BoundingBox   {
  @JsonProperty("vertices")
  @Valid
  private List<Vertex> vertices = null;

  public BoundingBox vertices(List<Vertex> vertices) {
    this.vertices = vertices;
    return this;
  }

  public BoundingBox addVerticesItem(Vertex verticesItem) {
    if (this.vertices == null) {
      this.vertices = new ArrayList<Vertex>();
    }
    this.vertices.add(verticesItem);
    return this;
  }

  /**
   * Get vertices
   * @return vertices
   **/
  @Schema(description = "")
      @Valid
    public List<Vertex> getVertices() {
    return vertices;
  }

  public void setVertices(List<Vertex> vertices) {
    this.vertices = vertices;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BoundingBox boundingBox = (BoundingBox) o;
    return Objects.equals(this.vertices, boundingBox.vertices);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vertices);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BoundingBox {\n");
    
    sb.append("    vertices: ").append(toIndentedString(vertices)).append("\n");
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
