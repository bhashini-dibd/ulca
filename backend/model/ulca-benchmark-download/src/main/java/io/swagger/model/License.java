package io.swagger.model;

import java.util.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * license under which information is published.
 */
public enum License {
  CC_BY_4_0("cc-by-4.0"),
    CC_BY_SA_4_0("cc-by-sa-4.0"),
    CC_BY_ND_2_0("cc-by-nd-2.0"),
    CC_BY_ND_4_0("cc-by-nd-4.0"),
    CC_BY_NC_3_0("cc-by-nc-3.0"),
    CC_BY_NC_4_0("cc-by-nc-4.0"),
    CC_BY_NC_SA_4_0("cc-by-nc-sa-4.0"),
    MIT("mit"),
    GPL_3_0("gpl-3.0"),
    BSD_3_CLAUSE("bsd-3-clause"),
    PRIVATE_COMMERCIAL("private-commercial"),
    UNKNOWN_LICENSE("unknown-license"),
    CUSTOM_LICENSE("custom-license");

  private String value;

  License(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static License fromValue(String text) {
    for (License b : License.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
