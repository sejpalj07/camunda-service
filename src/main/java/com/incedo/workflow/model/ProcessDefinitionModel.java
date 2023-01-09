package com.incedo.workflow.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessDefinitionModel {


    private String definitionId;

    private String definitionKey;

    private String category;

    private Object description;

    private String name;

    private Integer version;

    private String resource;

    private String deploymentId;

    private Object diagram;

    private Boolean suspended;

    private Object tenantId;

    private String versionTag;

    private Object historyTimeToLive;

    private Boolean startableInTasklist;


}
