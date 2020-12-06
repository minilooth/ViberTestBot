package by.testbot.models.viber;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Frame {
    @JsonProperty("BorderWidth")
    private Integer borderWidth;

    @JsonProperty("BorderColor")
    private String borderColor;

    @JsonProperty("CornerRadius")
    private Integer cornerRadius;
}
