package com.example.modeling_tools.repository;

import com.example.modeling_tools.entity.ModelingTool;
import com.example.modeling_tools.entity.ModelingToolSuggestion;
import com.example.modeling_tools.entity.properties.ModelingLanguage;
import com.example.modeling_tools.entity.properties.ProgrammingLanguage;
import com.example.modeling_tools.type.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelingToolRepository extends JpaRepository<ModelingTool, Long> {
    /**
     * Retrieves a list of modeling tools with a matching {@code name}.
     *
     * @param name name of the searched modeling tool
     * @return a list of modeling tools with a corresponding name
     */
    List<ModelingTool> findByName(String name);

    /**
     * Retrieves a list of modeling tools matching the query parameters. Parameters such as technology, modelingLanguages,
     * creator, platform and programmingLanguages have been left out due to not being able to check in SQL if a list is
     * a subset of another list.
     *
     * @param name name of the modeling tool
     * @param link website link of the modeling tool
     * @param category category in which the modeling tool belongs
     * @param license license of the modeling tool
     * @param openSource informs whether the modeling tool is open source or not
     * @param webApp informs whether the modeling tool is available as a web app
     * @param desktopApp informs whether the modeling tool is available as a desktop app or not
     * @param sourceCodeGeneration informs whether the modeling tool generates source code based on the model
     * @param cloudService informs whether the modeling tool provides cloud services
     * @param loginRequired informs whether a user needs to log-in on the app before the modeling tool can be used
     * @param realTimeCollab informs whether a user can collaborate with others in real time
     *
     * @return a list of modeling tools that matches the query parameters
     */
    @Query("""
    SELECT tool
    FROM modeling_tool tool
    WHERE UPPER(tool.name) LIKE %?1%
    AND UPPER(tool.link) LIKE %?2%
    AND (
        (?3 IS NOT NULL AND UPPER(tool.category) = ?3)
        OR
        (?3 IS NULL)
    )
    AND (
        (?4 IS NOT NULL AND UPPER(tool.license) = ?4)
        OR
        (?4 IS NULL)
    )
    AND (
        (?5 IS NOT NULL AND tool.openSource = ?5)
        OR
        (?5 IS NULL AND (tool.openSource = TRUE OR tool.openSource = FALSE OR tool.openSource IS NULL))
    )
    AND (
        (?6 IS NOT NULL AND tool.webApp = ?6)
        OR
        (?6 IS NULL AND (tool.webApp = TRUE OR tool.webApp = FALSE OR tool.webApp IS NULL))
    )
    AND (
        (?7 IS NOT NULL AND tool.desktopApp = ?7)
        OR
        (?7 IS NULL AND (tool.desktopApp = TRUE OR tool.desktopApp = FALSE OR tool.desktopApp IS NULL))
    )
    AND (
        (?8 IS NOT NULL AND tool.sourceCodeGeneration = ?8)
        OR
        (?8 IS NULL AND (tool.sourceCodeGeneration = TRUE OR tool.sourceCodeGeneration = FALSE OR tool.sourceCodeGeneration IS NULL))
    )
    AND (
        (?9 IS NOT NULL AND tool.cloudService = ?9)
        OR
        (?9 IS NULL AND (tool.cloudService = TRUE OR tool.cloudService = FALSE OR tool.cloudService IS NULL))
    )
    AND (
        (?10 IS NOT NULL AND tool.loginRequired = ?10)
        OR
        (?10 IS NULL AND (tool.loginRequired = TRUE OR tool.loginRequired = FALSE OR tool.loginRequired IS NULL))
    )
    AND (
        (?11 IS NOT NULL AND tool.realTimeCollab = ?11)
        OR
        (?11 IS NULL AND (tool.realTimeCollab = TRUE OR tool.realTimeCollab = FALSE OR tool.realTimeCollab IS NULL))
    )
    """)
    List<ModelingTool> findBySearch(
            String name,
            String link,
            String category,
            String license,
            Boolean openSource,
            Boolean webApp,
            Boolean desktopApp,
            Boolean sourceCodeGeneration,
            Boolean cloudService,
            Boolean loginRequired,
            Boolean realTimeCollab
    );

    /**
     * Retrieves a list of modeling tools which contain programming languages with a certain id.
     *
     * @param programmingLanguageId id of the programming language
     *
     * @return a list of modeling tools that contain programming languages with a certain id
     */
    @Query("""
    SELECT DISTINCT tool
    FROM modeling_tool tool
    LEFT JOIN tool.programmingLanguage language
    WHERE language.id = ?1
    """)
    List<ModelingTool> findModelingToolByProgrammingLanguageId(Long programmingLanguageId);

    /**
     * Retrieves a list of modeling tool suggestions which contain platforms with a certain id.
     *
     * @param platformId id of the platform
     *
     * @return a list of modeling tool suggestions that contain platforms with a certain id
     */
    @Query("""
    SELECT DISTINCT tool
    FROM modeling_tool tool
    LEFT JOIN tool.platform p
    WHERE p.id = ?1
    """)
    List<ModelingTool> findModelingToolByPlatformId(Long platformId);

    /**
     * Retrieves a list of modeling tool suggestions which contain modeling languages with a certain id.
     *
     * @param modelingLanguageId id of the modeling language
     *
     * @return a list of modeling tool suggestions that contain modeling languages with a certain id
     */
    @Query("""
    SELECT DISTINCT tool
    FROM modeling_tool tool
    LEFT JOIN tool.modelingLanguages language
    WHERE language.id = ?1
    """)
    List<ModelingTool> findModelingToolByModelingLanguageId(Long modelingLanguageId);
}
