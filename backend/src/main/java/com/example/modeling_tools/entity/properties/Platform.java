package com.example.modeling_tools.entity.properties;

import com.example.modeling_tools.entity.ModelingToolVerified;
import com.example.modeling_tools.entity.ModelingToolSuggestion;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Platform extends Property{
    public Platform() {
    }

    public Platform(String name, Boolean deletable) {
        super(name, deletable);
    }
}
