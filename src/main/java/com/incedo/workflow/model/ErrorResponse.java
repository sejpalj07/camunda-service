
package com.incedo.workflow.model;

import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "httpStatusCode",
    "httpStatuswMessage",
    "errorList"
})
@Generated("jsonschema2pojo")
public class ErrorResponse {

    @JsonProperty("httpStatusCode")
    private Integer httpStatusCode;
    @JsonProperty("httpStatuswMessage")
    private String httpStatuswMessage;
    @JsonProperty("errorList")
    private List<Error> errorList = null;

    @JsonProperty("httpStatusCode")
    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    @JsonProperty("httpStatusCode")
    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    @JsonProperty("httpStatuswMessage")
    public String getHttpStatuswMessage() {
        return httpStatuswMessage;
    }

    @JsonProperty("httpStatuswMessage")
    public void setHttpStatuswMessage(String httpStatuswMessage) {
        this.httpStatuswMessage = httpStatuswMessage;
    }

    @JsonProperty("errorList")
    public List<Error> getErrorList() {
        return errorList;
    }

    @JsonProperty("errorList")
    public void setErrorList(List<Error> errorList) {
        this.errorList = errorList;
    }

}
