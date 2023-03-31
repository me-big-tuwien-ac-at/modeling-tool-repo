package com.example.modeling_tools.entity;

import com.example.modeling_tools.entity.properties.ModelingLanguage;
import com.example.modeling_tools.entity.properties.Platform;
import com.example.modeling_tools.entity.properties.ProgrammingLanguage;
import com.example.modeling_tools.type.Category;
import com.example.modeling_tools.type.License;
import com.example.modeling_tools.type.Technology;
import jakarta.persistence.Entity;

import java.util.List;

@Entity(name = "modeling_tool")
public class ModelingToolVerified extends ModelingTool {
    public ModelingToolVerified() {
    }

    public ModelingToolVerified(
            String name,
            String link,
            Boolean openSource,
            List<Technology> technology,
            Boolean webApp,
            Boolean desktopApp,
            Category category,
            List<ModelingLanguage> modelingLanguages,
            Boolean sourceCodeGeneration,
            Boolean cloudService,
            License license,
            Boolean loginRequired,
            List<String> creator,
            List<Platform> platform,
            Boolean realTimeCollab,
            List<ProgrammingLanguage> programmingLanguage
    ) {
        super(name, link, openSource, technology, webApp, desktopApp, category, modelingLanguages, sourceCodeGeneration,
                cloudService, license, loginRequired, creator, platform, realTimeCollab, programmingLanguage);
    }
}
