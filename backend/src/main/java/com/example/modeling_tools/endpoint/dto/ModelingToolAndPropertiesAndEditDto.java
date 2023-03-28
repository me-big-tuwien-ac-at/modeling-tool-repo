package com.example.modeling_tools.endpoint.dto;

import java.util.List;

/**
 * DTO for when the user wants to suggest an edit.
 */
public class ModelingToolAndPropertiesAndEditDto {
    private List<ModelingToolDto> modelingTools;
    private ModelingToolDto modelingTool;
    private List<String> modelingLanguages;
    private List<String> platforms;
    private List<String> programmingLanguages;

    public ModelingToolAndPropertiesAndEditDto() {
    }

    public ModelingToolAndPropertiesAndEditDto(
            List<ModelingToolDto> modelingTools,
            ModelingToolDto modelingTool,
            List<String> modelingLanguages,
            List<String> platforms,
            List<String> programmingLanguages
    ) {
        this.modelingTools = modelingTools;
        this.modelingTool = modelingTool;
        this.modelingLanguages = modelingLanguages;
        this.platforms = platforms;
        this.programmingLanguages = programmingLanguages;
    }

    public List<ModelingToolDto> getModelingTools() {
        return modelingTools;
    }

    public void setModelingTools(List<ModelingToolDto> modelingTools) {
        this.modelingTools = modelingTools;
    }

    public ModelingToolDto getModelingTool() {
        return modelingTool;
    }

    public void setModelingTool(ModelingToolDto modelingTool) {
        this.modelingTool = modelingTool;
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
