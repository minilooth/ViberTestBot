package by.autocapital.models.viber;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import by.autocapital.models.enums.button.ActionType;
import by.autocapital.models.enums.button.BackgroundMediaScaleType;
import by.autocapital.models.enums.button.BackgroundMediaType;
import by.autocapital.models.enums.button.ImageScaleType;
import by.autocapital.models.enums.button.TextHorizontalAlign;
import by.autocapital.models.enums.button.TextSize;
import by.autocapital.models.enums.button.TextVerticalAlign;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Button {
    @JsonProperty("Columns")
    private Integer columns;

    @JsonProperty("Rows")
    private Integer rows;

    @JsonProperty("BgColor")
    private String backgroundColor;

    @JsonProperty("Silent")
    private Boolean silent;

    @JsonProperty("BgMediaType")
    private BackgroundMediaType backgroundMediaType;

    @JsonProperty("BgMedia")
    private String backgroundMedia;

    @JsonProperty("BgMediaScaleType")
    private BackgroundMediaScaleType backgroundMediaScaleType;

    @JsonProperty("ImageScaleType")
    private ImageScaleType imageScaleType;

    @JsonProperty("BgLoop")
    private Boolean backgroundLoop;

    @JsonProperty("ActionType")
    private ActionType actionType;

    @JsonProperty(value = "ActionBody", required = true)
    private String actionBody;

    @JsonProperty("Image")
    private String imageUrl;

    @JsonProperty("Text")
    private String text;

    @JsonProperty("TextVAlign")
    private TextVerticalAlign textVerticalAlign;

    @JsonProperty("TextHAlign")
    private TextHorizontalAlign textHorizontalAlign;

    @JsonProperty("TextPaddings")
    private List<Integer> textPaddings;

    @JsonProperty("TextOpacity")
    private Integer textOpacity;

    @JsonProperty("TextSize")
    private TextSize textSize;

    @JsonProperty("TextShouldFit")
    private Boolean textShouldFit;

    @JsonProperty("Frame")
    private Frame frame;


}
