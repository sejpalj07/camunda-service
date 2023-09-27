package com.incedo.workflow.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BpmXMlResponse {
    private String processDefinitionId;
    private String xmlResponse;
}
