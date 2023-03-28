package com.example.modeling_tools.service;

import java.util.stream.Stream;

/**
 * Service responsible for retrieval of Modeling Languages, Platforms and Programming Languages.
 */
public interface PropertiesService {
    /**
     * Retrieves a list of modeling languages that are either not deletable or are assigned to a modeling tool.
     *
     * @param confirmed specifies whether the modeling language has been approved
     *
     * @return a list of modeling languages
     */
    Stream<String> getModelingLanguages(Boolean confirmed);

    /**
     * Retrieves a list of platforms that are either not deletable or are assigned to a modeling tool.
     *
     * @param confirmed specifies whether the platform has been approved
     *
     * @return a list of platforms
     */
    Stream<String> getPlatforms(Boolean confirmed);

    /**
     * Retrieves a list of programming languages that are either not deletable or are assigned to a modeling tool.
     *
     * @param confirmed specifies whether the programming language has been approved
     *
     * @return a list of programming languages
     */
    Stream<String> getProgrammingLanguages(Boolean confirmed);
}
