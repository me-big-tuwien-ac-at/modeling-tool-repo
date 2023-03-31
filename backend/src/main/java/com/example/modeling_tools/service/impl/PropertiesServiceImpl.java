package com.example.modeling_tools.service.impl;

import com.example.modeling_tools.entity.properties.ModelingLanguage;
import com.example.modeling_tools.entity.properties.Platform;
import com.example.modeling_tools.entity.properties.ProgrammingLanguage;
import com.example.modeling_tools.repository.properties.ModelingLanguageRepository;
import com.example.modeling_tools.repository.properties.PlatformRepository;
import com.example.modeling_tools.repository.properties.ProgrammingLanguagesRepository;
import com.example.modeling_tools.service.PropertiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.stream.Stream;

@Service
public class PropertiesServiceImpl implements PropertiesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ModelingLanguageRepository modelingLanguageRepository;
    private final PlatformRepository platformRepository;
    private final ProgrammingLanguagesRepository programmingLanguagesRepository;

    public PropertiesServiceImpl(ModelingLanguageRepository modelingLanguageRepository,
                                 PlatformRepository platformRepository,
                                 ProgrammingLanguagesRepository programmingLanguagesRepository) {
        this.modelingLanguageRepository = modelingLanguageRepository;
        this.platformRepository = platformRepository;
        this.programmingLanguagesRepository = programmingLanguagesRepository;
    }

    @Override
    public Stream<String> getModelingLanguages(Boolean confirmed) {
        LOGGER.debug("Retrieving modeling languages " + (confirmed == null
                ? "where confirmation status is not relevant"
                : (confirmed ? "which have been approved" : "which have yet to be approved")
        ));
        if (confirmed == null) {
            return modelingLanguageRepository.findAll().stream().map(ModelingLanguage::getName);
        } else if (confirmed) {
            return modelingLanguageRepository.findByModelingToolsVerifiedIsNotNullOrDeletableIsFalse().stream().map(ModelingLanguage::getName);
        } else {
            return modelingLanguageRepository.findByModelingToolsVerifiedIsNullAndDeletableIsTrue().stream().map(ModelingLanguage::getName);

        }
    }

    @Override
    public Stream<String> getPlatforms(Boolean confirmed) {
        LOGGER.debug("Retrieving platforms " + (confirmed == null
                ? "where confirmation status is not relevant"
                : (confirmed ? "which have been approved" : "which have yet to be approved")
        ));
        if (confirmed == null) {
            return platformRepository.findAll().stream().map(Platform::getName);
        } else if (confirmed) {
            return platformRepository.findByModelingToolsVerifiedIsNotNullOrDeletableIsFalse().stream().map(Platform::getName);
        } else {
            return platformRepository.findByModelingToolsVerifiedIsNullAndDeletableIsTrue().stream().map(Platform::getName);
        }
    }

    @Override
    public Stream<String> getProgrammingLanguages(Boolean confirmed) {
        LOGGER.debug("Retrieving programming languages " + (confirmed == null
                        ? "where confirmation status is not relevant"
                        : (confirmed ? "which have been approved" : "which have yet to be approved")
        ));
        if (confirmed == null) {
            return programmingLanguagesRepository.findAll().stream().map(ProgrammingLanguage::getName);
        } else if (confirmed) {
            return programmingLanguagesRepository.findByModelingToolsVerifiedIsNotNullOrDeletableIsFalse().stream().map(ProgrammingLanguage::getName);
        } else {
            return programmingLanguagesRepository.findByModelingToolsVerifiedIsNullAndDeletableIsTrue().stream().map(ProgrammingLanguage::getName);
        }
    }
}
