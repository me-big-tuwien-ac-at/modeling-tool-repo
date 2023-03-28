package com.example.modeling_tools.base_test;

import com.example.modeling_tools.endpoint.dto.ModelingToolSuggestionDto;
import com.example.modeling_tools.type.Category;
import com.example.modeling_tools.type.License;
import com.example.modeling_tools.type.Technology;

import java.util.List;

/**
 * Types of suggestions:
 * MIN_INFO_CREATE - Modeling Tool Suggestion, where only mandatory fields are filled
 * MAX_INFO_CREATE - Modeling Tool Suggestion, where every field is filled
 * MIN_INFO_EDIT - Modeling Tool Suggestion, where only mandatory fields are filled and references a Modeling Tool by
 *                 specifying the modelingToolId
 * MAX_INFO_EDIT - Modeling Tool Suggestion, where every field is filled and references a Modeling Tool by specifying
 *                 the modelingToolId
 * SUGGESTION_EXISTING_PROPERTIES - Modeling Tool Suggestion with properties already stored in the persistent data
 *                                  store
 * SUGGESTION_NEW_PROPERTIES - Modeling Tool Suggestion with newly suggested properties
 * SUGGESTION_MIXED_PROPERTIES - Modeling Tool Suggested with mixed existing and newly suggested properties
 * CREATELY, MERMAID_JS, UMPLE - Real life Modeling Tools used as Modeling Tool Suggestions
 */
public interface TestData {
    /**
     * Modeling Tool Suggestion, where only mandatory fields are filled.
     */
    ModelingToolSuggestionDto MIN_INFO_CREATE = new ModelingToolSuggestionDto(
            null,
            "web.modeling.tools@gmail.com",
            "Minimal Info Modeling Tool Create",
            "https://minimal_info_create.de",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
    );

    /**
     * Modeling Tool Suggestion, where every field is filled.
     */
    ModelingToolSuggestionDto  MAX_INFO_CREATE = new ModelingToolSuggestionDto(
            null,
            "web.modeling.tools@gmail.com",
            "Maximal Info Modeling Tool Create",
            "https://max_info_create.ac.at",
            true,
            List.of(Technology.APP),
            true,
            false,
            Category.GRAPHICAL_MODELING_TOOL,
            List.of("UML"),
            false,
            true,
            License.FREE,
            true,
            List.of("Mister Minimal"),
            List.of("Windows"),
            true,
            List.of("C"),
            null
    );

    /**
     * Modeling Tool Suggestion, where only mandatory fields are filled and references a Modeling Tool by specifying the
     * modelingToolId.
     */
    ModelingToolSuggestionDto MIN_INFO_EDIT = new ModelingToolSuggestionDto(
            1L,
            "web.modeling.tools@gmail.com",
            "Minimal Info Modeling Tool Edit",
            "https://min_info_edit.org",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
    );

    /**
     * Modeling Tool Suggestion, where every field is filled and references a Modeling Tool by specifying the
     * modelingToolId.
     */
    ModelingToolSuggestionDto MAX_INFO_EDIT = new ModelingToolSuggestionDto(
            1L,
            "web.modeling.tools@gmail.com",
            "Maximal Info Modeling Tool Edit",
            "https://max_info_edit.it",
            true,
            List.of(Technology.APP),
            true,
            false,
            Category.GRAPHICAL_MODELING_TOOL,
            List.of("UML"),
            false,
            true,
            License.FREE,
            true,
            List.of("Mister Minimal"),
            List.of("Windows"),
            true,
            List.of("C"),
            "Modeling Tool is still in beta"
    );

    /**
     * Modeling Tool Suggestion with properties already stored in the persistent data store.
     */
    ModelingToolSuggestionDto SUGGESTION_EXISTING_PROPERTIES = new ModelingToolSuggestionDto(
            null,
            "web.modeling.tools@gmail.com",
            "Suggestion Existing Properties",
            "https://suggestion_existing_properties.de",
            true,
            List.of(Technology.APP, Technology.LIBRARY, Technology.FRAMEWORK),
            null,
            false,
            Category.BUSINESS_TOOL,
            List.of("UML", "BPMN", "ER", "SysML"),
            null,
            false,
            License.FREE,
            true,
            List.of("Mister Minimal"),
            List.of("LINUX", "MACOS", "WINDOWS", "SOLARIS", "UNIX"),
            null,
            List.of("C", "JAVA", "JAVASCRIPT", "HTML", "CSS", "POWERSHELL", "TYPESCRIPT"),
            null
    );

    /**
     * Modeling Tool Suggestion with newly suggested properties,
     */
    ModelingToolSuggestionDto SUGGESTION_NEW_PROPERTIES = new ModelingToolSuggestionDto(
            null,
            "web.modeling.tools@gmail.com",
            "Suggestion New Properties",
            "https://suggestion_new_properties.de",
            false,
            List.of(Technology.LIBRARY, Technology.FRAMEWORK),
            false,
            null,
            Category.BUSINESS_TOOL,
            List.of("AlfaML", "BravoML", "CharlieML"),
            null,
            true,
            License.FREE,
            null,
            List.of("Mister Minimal"),
            List.of("AlfaOS", "BravoOS", "CharlieOS"),
            null,
            List.of("AlfaPL", "BravoPL", "CharliePL"),
            null
    );

    /**
     * Modeling Tool Suggested with mixed existing and newly suggested properties.
     */
    ModelingToolSuggestionDto SUGGESTION_MIXED_PROPERTIES = new ModelingToolSuggestionDto(
            null,
            "web.modeling.tools@gmail.com",
            "Suggestion Mixed Properties",
            "https://suggestion_mixed_properties.de",
            false,
            List.of(Technology.APP, Technology.FRAMEWORK),
            false,
            null,
            Category.BUSINESS_TOOL,
            List.of("AlfaML", "UML", "BPMN", "BravoML", "ER", "CharlieML"),
            null,
            true,
            License.FREE,
            null,
            List.of("Mister Minimal"),
            List.of("AlfaOS", "WINDOWS", "BravoOS", "LINUX", "UNIX", "CharlieOS"),
            null,
            List.of("JAVA", "AlfaPL", "C", "C#", "BravoPL", "POWERSHELL", "CharliePL", "TYPESCRIPT"),
            null
    );

    /**
     * Real life Modeling Tool used as Modeling Tool Suggestion.
     */
    ModelingToolSuggestionDto CREATELY = new ModelingToolSuggestionDto(
            null,
            "web.modeling.tools@gmail.com",
            "Creately",
            "https://creately.com/",
            false,
            List.of(Technology.APP),
            true,
            false,
            Category.BUSINESS_TOOL,
            List.of("UML", "BPMN", "ER"),
            false,
            true,
            License.RESTRICTED_FREE_AND_COMMERCIAL,
            true,
            null,
            null,
            false,
            null,
            null
    );

    /**
     * Real life Modeling Tool used as Modeling Tool Suggestion.
     */
    ModelingToolSuggestionDto MERMAID_JS = new ModelingToolSuggestionDto(
            null,
            "web.modeling.tools@gmail.com",
            "Mermaid.js",
            "https://mermaid-js.github.io/mermaid/#/",
            true,
            List.of(Technology.LIBRARY),
            false,
            false,
            Category.TEXT_BASED_MODELING_TOOL,
            List.of("UML"),
            false,
            true,
            License.FREE,
            false,
            null,
            null,
            false,
            List.of("JAVASCRIPT", "HTML", "TYPESCRIPT", "YACC", "CSS", "PYTHON"),
            null
    );

    /**
     * Real life Modeling Tool used as Modeling Tool Suggestion.
     */
    ModelingToolSuggestionDto UMPLE = new ModelingToolSuggestionDto(
            null,
            "web.modeling.tools@gmail.com",
            "Umple",
            "https://cruise.umple.org/umple/",
            true,
            List.of(Technology.FRAMEWORK),
            true,
            true,
            Category.MIXED_TEXTUAL_AND_GRAPHICAL_MODElING_TOOL,
            List.of("UML"),
            false,
            true,
            License.FREE,
            false,
            List.of("Cruise Group in the Department of Electrical Engineering and Computer Science, Led by Timothy Lethbridge at University of Ottawa"),
            null,
            false,
            List.of("JAVA"),
            null
    );

    List<ModelingToolSuggestionDto> suggestions = List.of(
            MIN_INFO_CREATE,
            MAX_INFO_CREATE,
            MIN_INFO_EDIT,
            MAX_INFO_EDIT,
            SUGGESTION_EXISTING_PROPERTIES,
            SUGGESTION_NEW_PROPERTIES,
            SUGGESTION_MIXED_PROPERTIES,
            CREATELY,
            MERMAID_JS,
            UMPLE
    );

    ModelingToolSuggestionDto DUPLICATE_PROPERTIES = new ModelingToolSuggestionDto(
            null,
            "web.modeling.tools@gmail.com",
            "Modeling Tool containing property duplicates",
            "https://duplicates.ac.at",
            true,
            List.of(Technology.APP, Technology.APP),
            null,
            null,
            null,
            List.of("ml1", "Ml1", "ML1"),
            null,
            null,
            null,
            null,
            List.of("Alfa", "alfa", "aLFA", "Bravo", "brAvo", "brAVO"),
            List.of("platform1", "PLATForm1", "platForm1", "plAtForM1"),
            null,
            List.of("JAVA", "java"),
            null
    );
}
