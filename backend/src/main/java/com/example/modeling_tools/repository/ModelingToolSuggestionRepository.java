package com.example.modeling_tools.repository;

import com.example.modeling_tools.entity.ModelingToolSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelingToolSuggestionRepository extends JpaRepository<ModelingToolSuggestion, Long> {

    /**
     * Finds all modeling tool suggestions with corresponding user tokens or admin tokens.
     *
     * @param adminToken Admin token of a  Modeling tool suggestion
     *
     * @return a list of modeling tool suggestions with corresponding a corresponding user token or admin token
     */
    List<ModelingToolSuggestion> findByAdminToken(String adminToken);

    /**
     * Retrieves a list of modeling tool suggestion which contain programming languages with a certain id.
     *
     * @param programmingLanguageId id of the programming language
     *
     * @return a list of modeling tool suggestions that contain programming languages with a certain id
     */
    @Query("""
    SELECT DISTINCT suggestion
    FROM modeling_tool_edit_proposal suggestion
    LEFT JOIN suggestion.programmingLanguage language
    WHERE language.id = ?1
    """)
    List<ModelingToolSuggestion> findModelingToolSuggestionByProgrammingLanguageId(Long programmingLanguageId);

    /**
     * Retrieves a list of modeling tool suggestion which contain platforms with a certain id.
     *
     * @param platformId id of the programming language
     *
     * @return a list of modeling tool suggestions that contain platforms with a certain id
     */
    @Query("""
    SELECT DISTINCT suggestion
    FROM modeling_tool_edit_proposal suggestion
    LEFT JOIN suggestion.platform p
    WHERE p.id = ?1
    """)
    List<ModelingToolSuggestion> findModelingToolSuggestionByPlatformId(Long platformId);

    /**
     * Retrieves a list of modeling tool suggestion which contain modeling languages with a certain id.
     *
     * @param modelingLanguageId id of the programming language
     *
     * @return a list of modeling tool suggestions that contain modeling languages with a certain id
     */
    @Query("""
    SELECT DISTINCT suggestion
    FROM modeling_tool_edit_proposal suggestion
    LEFT JOIN suggestion.modelingLanguages language
    WHERE language.id = ?1
    """)
    List<ModelingToolSuggestion> findModelingToolSuggestionByModelingLanguageId(Long modelingLanguageId);
}
