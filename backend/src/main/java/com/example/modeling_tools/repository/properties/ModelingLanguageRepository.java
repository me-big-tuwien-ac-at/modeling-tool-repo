package com.example.modeling_tools.repository.properties;

import com.example.modeling_tools.entity.ModelingTool;
import com.example.modeling_tools.entity.properties.ModelingLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelingLanguageRepository extends JpaRepository<ModelingLanguage, Long> {
    /**
     * Retrieves a list of modeling languages matching {@code name}.
     *
     * @param name name of the modeling tool
     * @return all modeling tools with a corresponding name
     */
    List<ModelingLanguage> findByNameIgnoreCase(String name);

    /**
     * Retrieves a list of modeling languages that are assigned to a modeling tool or are deletable.
     *
     * @return a list of modeling languages where the modeling tool is not null or deletable is false
     */
    List<ModelingLanguage> findByModelingToolsIsNotNullOrDeletableIsFalse();

    /**
     * Retrieves a list of modeling languages that are yet to be assigned to a modeling tool or are deletable.
     *
     * @return a list of modeling languages where the modeling tool is null or deletable is true
     */
    List<ModelingLanguage> findByModelingToolsIsNullAndDeletableIsTrue();

    /**
     * Retrieves a list of modeling tools which contain modeling languages with a certain id.
     *
     * @param modelingLanguageId id of the modeling language
     *
     * @return a list of modeling tools that contain modeling languages with a certain id
     */
    @Query("""
    SELECT DISTINCT tool
    FROM modeling_tool tool
    LEFT JOIN tool.modelingLanguages language
    WHERE language.id = ?1
    """)
    List<ModelingTool> findModelingToolByModelingLanguageId(Long modelingLanguageId);

    /**
     * Retrieves a list of modeling languages which are assigned to a certain modeling tool suggestion.
     *
     * @param modelingToolSuggestionId id of the modeling tool suggestion
     *
     * @return a list of modeling languages which are assigned to a modeling tool suggestion
     */
    @Query("""
    SELECT DISTINCT language
    FROM modeling_language language
    LEFT JOIN language.modelingToolSuggestions suggestion
    WHERE suggestion.id = ?1
    """)
    List<ModelingLanguage> findModelingLanguageByModelingToolSuggestionId(Long modelingToolSuggestionId);

    /**
     * Retrieves a list of modeling languages which are assigned to a certain modeling tool.
     *
     * @param modelingToolId id of the modeling tool
     *
     * @return a list of modeling languages which are assigned to a modeling tool
     */
    @Query("""
    SELECT DISTINCT language
    FROM modeling_language language
    LEFT JOIN language.modelingTools tool
    WHERE tool.id = ?1
    """)
    List<ModelingLanguage> findModelingLanguageByModelingToolId(Long modelingToolId);
}
