package com.example.modeling_tools.endpoint.dto;

import java.util.List;

/**
 * DTO for when the webpage is started.
 */
public class ModelingToolAndPropertiesDto {
    private List<ModelingToolDto> modelingTools;
    private List<String> modelingLanguages;
    private List<String> platforms;
    private List<String> programmingLanguages;

    public List<ModelingToolDto> getModelingTools() {
        return modelingTools;
    }

    public void setModelingTools(List<ModelingToolDto> modelingTools) {
        this.modelingTools = modelingTools;
    }

    public List<String> getModelingLanguages() {
        return modelingLanguages;
    }

    public void setModelingLanguages(List<String> modelingLanguages) {
        this.modelingLanguages = modelingLanguages;
    }

    public List<String> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<String> platforms) {
        this.platforms = platforms;
    }

    public List<String> getProgrammingLanguages() {
        return programmingLanguages;
    }

    public void setProgrammingLanguages(List<String> programmingLanguages) {
        this.programmingLanguages = programmingLanguages;
    }
}
