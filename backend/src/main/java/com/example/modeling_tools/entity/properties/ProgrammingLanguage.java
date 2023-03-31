package com.example.modeling_tools.entity.properties;

import com.example.modeling_tools.entity.ModelingToolVerified;
import com.example.modeling_tools.entity.ModelingToolSuggestion;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "programming_language")
public class ProgrammingLanguage extends Property{
    public ProgrammingLanguage() {
    }

    public ProgrammingLanguage(String name, Boolean deletable) {
        super(name, deletable);
    }
}
