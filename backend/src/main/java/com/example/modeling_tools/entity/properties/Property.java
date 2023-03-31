package com.example.modeling_tools.entity.properties;

import com.example.modeling_tools.entity.ModelingToolSuggestion;
import com.example.modeling_tools.entity.ModelingToolVerified;
import jakarta.persistence.*;

import java.util.List;

@MappedSuperclass
public abstract class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean deletable;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<ModelingToolVerified> modelingToolsVerified;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<ModelingToolSuggestion> modelingToolSuggestions;

    public Property() {
    }

    public Property(String name, Boolean deletable) {
        this.name = name;
        this.deletable = deletable;
    }

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

    public List<ModelingToolVerified> getModelingTools() {
        return modelingToolsVerified;
    }

    public void setModelingTools(List<ModelingToolVerified> modelingToolsVerified) {
        this.modelingToolsVerified = modelingToolsVerified;
    }

    public List<ModelingToolSuggestion> getModelingToolSuggestions() {
        return modelingToolSuggestions;
    }

    public void setModelingToolSuggestions(List<ModelingToolSuggestion> modelingToolSuggestions) {
        this.modelingToolSuggestions = modelingToolSuggestions;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deletable=" + deletable +
                '}';
    }
}
