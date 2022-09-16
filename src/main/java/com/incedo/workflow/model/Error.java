
package com.incedo.workflow.model;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "errorCode",
    "errorMessage",
    "developersText"
})
@Generated("jsonschema2pojo")
public class Error {

    @JsonProperty("errorCode")
    private Integer errorCode;
    @JsonProperty("errorMessage")
    private String errorMessage;
    @JsonProperty("developersText")
    private String developersText;

    @JsonProperty("errorCode")
    public Integer getErrorCode() {
        return errorCode;
    }

    @JsonProperty("errorCode")
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    @JsonProperty("errorMessage")
    public String getErrorMessage() {
        return errorMessage;
    }

    @JsonProperty("errorMessage")
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @JsonProperty("developersText")
    public String getDevelopersText() {
        return developersText;
    }

    @JsonProperty("developersText")
    public void setDevelopersText(String developersText) {
        this.developersText = developersText;
    }

}
