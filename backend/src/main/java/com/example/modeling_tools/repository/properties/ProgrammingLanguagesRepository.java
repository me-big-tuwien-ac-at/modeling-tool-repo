package com.example.modeling_tools.repository.properties;

import com.example.modeling_tools.entity.properties.ProgrammingLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgrammingLanguagesRepository extends JpaRepository<ProgrammingLanguage, Long> {
    /**
     * Retrieves a list of programming languages matching {@code name}.
     *
     * @param name name of the programming language
     *
     * @return all programming languages with a corresponding name
     */
    List<ProgrammingLanguage> findByNameIgnoreCase(String name);

    /**
     * Retrieves a list of programming languages that are assigned to a modeling tool or are not deletable.
     *
     * @return a list of programming languages where the modeling tool is not null or deletable is false
     */
    List<ProgrammingLanguage> findByModelingToolsVerifiedIsNotNullOrDeletableIsFalse();

    /**
     * Retrieves a list of programming languages that are yet to be assigned to a modeling tool or are deletable.
     *
     * @return a list of programming languages where the modeling tool is null or deletable is true
     */
    List<ProgrammingLanguage> findByModelingToolsVerifiedIsNullAndDeletableIsTrue();

    /**
     * Retrieves a list of programming languages which are assigned to a certain modeling tool.
     *
     * @param modelingToolId id of the modeling tool
     *
     * @return a list of programming languages which are used by a modeling tool
     */
    @Query("""
    SELECT DISTINCT language
    FROM programming_language language
    LEFT JOIN language.modelingToolsVerified tools
    WHERE tools.id = ?1
    """)
    List<ProgrammingLanguage> findProgrammingLanguageByModelingToolId(Long modelingToolId);

    /**
     * Retrieves a list of programming languages which are assigned to a certain modeling tool suggestion.
     *
     * @param modelingToolSuggestionId id of the modeling tool suggestion
     *
     * @return a list of programming languages which are assigned to a modeling tool suggestion
     */
    @Query("""
    SELECT DISTINCT language
    FROM programming_language language
    LEFT JOIN language.modelingToolSuggestions suggestion
    WHERE suggestion.id = ?1
    """)
    List<ProgrammingLanguage> findProgrammingLanguageByModelingToolSuggestionId(Long modelingToolSuggestionId);
}
