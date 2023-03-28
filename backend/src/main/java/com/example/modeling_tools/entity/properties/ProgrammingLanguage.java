package com.example.modeling_tools.entity.properties;

import com.example.modeling_tools.entity.ModelingTool;
import com.example.modeling_tools.entity.ModelingToolSuggestion;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "programming_language")
public class ProgrammingLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean deletable;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<ModelingTool> modelingTools;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<ModelingToolSuggestion> modelingToolSuggestions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDeletable() {
        return deletable;
    }

    public void setDeletable(Boolean deletable) {
        this.deletable = deletable;
    }

    public List<ModelingTool> getModelingTools() {
        return modelingTools;
    }

    public void setModelingTools(List<ModelingTool> modelingTools) {
        this.modelingTools = modelingTools;
    }

    public List<ModelingToolSuggestion> getModelingToolSuggestions() {
        return modelingToolSuggestions;
    }

    public void setModelingToolSuggestions(List<ModelingToolSuggestion> modelingToolSuggestions) {
        this.modelingToolSuggestions = modelingToolSuggestions;
    }

    public ProgrammingLanguage() {
    }

    public ProgrammingLanguage(String name, Boolean deletable) {
        this.name = name;
        this.deletable = deletable;
    }

    @Override
    public String toString() {
        return "ProgrammingLanguage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deletable=" + deletable +
                '}';
    }
}
