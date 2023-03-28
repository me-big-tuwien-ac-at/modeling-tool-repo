package com.example.modeling_tools.repository.properties;

import com.example.modeling_tools.entity.ModelingTool;
import com.example.modeling_tools.entity.properties.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Long> {
    /**
     * Retrieves a list of platforms matching {@code name}.
     *
     * @param name name of the platform
     * @return all platforms with a corresponding name
     */
    List<Platform> findByNameIgnoreCase(String name);

    /**
     * Retrieves a list of platforms that are assigned to a modeling tool or are not deletable.
     *
     * @return a list of platforms where the modeling tool is not null or deletable is false
     */
    List<Platform> findByModelingToolsIsNotNullOrDeletableIsFalse();

    /**
     * Retrieves a list of platforms that are yet to be assigned to a modeling tool or are deletable.
     *
     * @return a list of platforms where the modeling tool is null or deletable is true
     */
    List<Platform> findByModelingToolsIsNullAndDeletableIsTrue();

    /**
     * Retrieves a list of modeling tools which contain platforms with a certain id.
     *
     * @param platformId id of the platform
     *
     * @return a list of modeling tools that contain platforms with a certain id
     */
    @Query("""
    SELECT DISTINCT tool
    FROM modeling_tool tool
    LEFT JOIN tool.platform p
    WHERE p.id = ?1
    """)
    List<ModelingTool> findModelingToolByPlatformId(Long platformId);

    /**
     * Retrieves a list of platforms which are assigned to a certain modeling tool suggestion.
     *
     * @param modelingToolSuggestionId id of the modeling tool suggestion
     *
     * @return a list of platforms which are assigned to a modeling tool suggestion
     */
    @Query("""
    SELECT DISTINCT p
    FROM Platform p
    LEFT JOIN p.modelingToolSuggestions suggestion
    WHERE suggestion.id = ?1
    """)
    List<Platform> findPlatformByModelingToolSuggestionId(Long modelingToolSuggestionId);

    /**
     * Retrieves a list of platforms which are assigned to a certain modeling tool.
     *
     * @param modelingToolId id of the modeling tool
     *
     * @return a list of platforms which are assigned to a modeling tool
     */
    @Query("""
    SELECT DISTINCT p
    FROM Platform p
    LEFT JOIN p.modelingTools tool
    WHERE tool.id = ?1
    """)
    List<Platform> findPlatformByModelingToolId(Long modelingToolId);

}
