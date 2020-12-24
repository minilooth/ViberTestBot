package by.autocapital.models.enums.button;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ImageScaleType {
    @JsonProperty("crop")
    CROP,

    @JsonProperty("fill")
    FILL,

    @JsonProperty("fit")
    FIT;
}
