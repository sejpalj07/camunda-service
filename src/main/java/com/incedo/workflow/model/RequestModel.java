package com.incedo.workflow.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestModel {
    String correlationId;
    RequestType httpRquestType;
    String httpRequestUrl;
    HashMap httpRequestHeader;
    Object body;
    ResponseEntity responseEntity;
}
