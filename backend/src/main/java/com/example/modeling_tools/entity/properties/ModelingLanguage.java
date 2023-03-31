package com.example.modeling_tools.entity.properties;

import com.example.modeling_tools.entity.ModelingToolVerified;
import com.example.modeling_tools.entity.ModelingToolSuggestion;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "modeling_language")
public class ModelingLanguage extends Property {
    public ModelingLanguage() {
    }

    public ModelingLanguage(String name, Boolean deletable) {
        super(name, deletable);
    }
}
