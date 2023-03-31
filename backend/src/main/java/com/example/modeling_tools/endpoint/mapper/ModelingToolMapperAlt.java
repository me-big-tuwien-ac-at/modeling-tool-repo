package com.example.modeling_tools.endpoint.mapper;

import com.example.modeling_tools.endpoint.dto.ModelingToolDto;
import com.example.modeling_tools.endpoint.dto.ModelingToolSuggestionDto;
import com.example.modeling_tools.entity.ModelingToolVerified;
import com.example.modeling_tools.entity.ModelingToolSuggestion;
import com.example.modeling_tools.entity.properties.ModelingLanguage;
import com.example.modeling_tools.entity.properties.Platform;
import com.example.modeling_tools.entity.properties.ProgrammingLanguage;
import com.example.modeling_tools.exception.FatalException;
import com.example.modeling_tools.repository.properties.ModelingLanguageRepository;
import com.example.modeling_tools.repository.properties.PlatformRepository;
import com.example.modeling_tools.repository.properties.ProgrammingLanguagesRepository;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Mapper
public abstract class ModelingToolMapperAlt {
    public static final ModelingToolMapperAlt INSTANCE = Mappers.getMapper(ModelingToolMapperAlt.class);
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private ModelingLanguageRepository modelingLanguageRepository;
    private PlatformRepository platformRepository;
    private ProgrammingLanguagesRepository programmingLanguagesRepository;


    public ModelingToolVerified modelingToolDtoToModelingTool(ModelingToolDto modelingToolDto) {
        LOGGER.debug("modelingToolDtoToModelingTool({})", modelingToolDto);

        ModelingToolVerified modelingToolVerified = new ModelingToolVerified();
        modelingToolVerified.setId(modelingToolDto.getId());
        modelingToolVerified.setName(modelingToolDto.getName());
        modelingToolVerified.setLink(modelingToolDto.getLink());
        modelingToolVerified.setOpenSource(modelingToolDto.getOpenSource());
        modelingToolVerified.setTechnology(modelingToolDto.getTechnology());
        modelingToolVerified.setWebApp(modelingToolDto.getWebApp());
        modelingToolVerified.setDesktopApp(modelingToolDto.getDesktopApp());
        modelingToolVerified.setCategory(modelingToolDto.getCategory());
        modelingToolVerified.setModelingLanguages(getModelingLanguagesByName(modelingToolDto.getModelingLanguages()));
        modelingToolVerified.setSourceCodeGeneration(modelingToolDto.getSourceCodeGeneration());
        modelingToolVerified.setCloudService(modelingToolDto.getCloudService());
        modelingToolVerified.setLicense(modelingToolDto.getLicense());
        modelingToolVerified.setLoginRequired(modelingToolDto.getLoginRequired());
        modelingToolVerified.setCreator(modelingToolDto.getCreator());
        modelingToolVerified.setPlatform(getPlatformsByName(modelingToolDto.getPlatform()));
        modelingToolVerified.setRealTimeCollab(modelingToolDto.getRealTimeCollab());
        modelingToolVerified.setProgrammingLanguage(getProgrammingLanguagesByName(modelingToolDto.getProgrammingLanguage()));
        return modelingToolVerified;
    }

    public ModelingToolDto modelingToolToModelingToolDto(ModelingToolVerified modelingToolVerified) {
        LOGGER.debug("modelingToolToModelingToolDto({})", modelingToolVerified);

        return new ModelingToolDto(
                modelingToolVerified.getId(),
                modelingToolVerified.getName(),
                modelingToolVerified.getLink(),
                modelingToolVerified.getOpenSource(),
                modelingToolVerified.getTechnology(),
                modelingToolVerified.getWebApp(),
                modelingToolVerified.getDesktopApp(),
                modelingToolVerified.getCategory(),
                getModelingLanguageNames(modelingToolVerified.getModelingLanguages()),
                modelingToolVerified.getSourceCodeGeneration(),
                modelingToolVerified.getCloudService(),
                modelingToolVerified.getLicense(),
                modelingToolVerified.getLoginRequired(),
                modelingToolVerified.getCreator(),
                getPlatformNames(modelingToolVerified.getPlatform()),
                modelingToolVerified.getRealTimeCollab(),
                getProgrammingLanguageNames(modelingToolVerified.getProgrammingLanguage())
        );
    }

    public ModelingToolSuggestionDto modelingToolSuggestionToModelingToolSuggestionDto(ModelingToolSuggestion suggestion) {
        LOGGER.debug("modelingToolSuggestionToModelingToolSuggestionDto({})", suggestion);

        ModelingToolSuggestionDto suggestionDto = new ModelingToolSuggestionDto();
        suggestionDto.setId(suggestion.getId());
        suggestionDto.setName(suggestion.getName());
        suggestionDto.setLink(suggestion.getLink());
        suggestionDto.setOpenSource(suggestion.getOpenSource());
        suggestionDto.setTechnology(suggestion.getTechnology());
        suggestionDto.setWebApp(suggestion.getWebApp());
        suggestionDto.setDesktopApp(suggestion.getDesktopApp());
        suggestionDto.setCategory(suggestion.getCategory());
        suggestionDto.setModelingLanguages(getModelingLanguageNames(suggestion.getModelingLanguages()));
        suggestionDto.setSourceCodeGeneration(suggestion.getSourceCodeGeneration());
        suggestionDto.setCloudService(suggestion.getCloudService());
        suggestionDto.setLicense(suggestion.getLicense());
        suggestionDto.setLoginRequired(suggestion.getLoginRequired());
        suggestionDto.setCreator(suggestion.getCreator());
        suggestionDto.setPlatform(getPlatformNames(suggestion.getPlatform()));
        suggestionDto.setRealTimeCollab(suggestion.getRealTimeCollab());
        suggestionDto.setProgrammingLanguage(getProgrammingLanguageNames(suggestion.getProgrammingLanguage()));
        return suggestionDto;
    }

    public ModelingToolSuggestion modelingToolSuggestionDtoToModelingToolSuggestion(ModelingToolSuggestionDto suggestionDto) {
        LOGGER.debug("modelingToolSuggestionDtoToModelingToolSuggestion({})", suggestionDto);

        ModelingToolSuggestion suggestion = new ModelingToolSuggestion();
        suggestion.setId(null);
        suggestion.setModelingToolId(suggestionDto.getModelingToolId());
        suggestion.setUserEmail(suggestionDto.getUserEmail());
        suggestion.setAdminToken(UUID.randomUUID().toString());
        suggestion.setName(suggestionDto.getName());
        suggestion.setLink(suggestionDto.getLink());
        suggestion.setOpenSource(suggestionDto.getOpenSource());
        suggestion.setTechnology(suggestionDto.getTechnology() != null ? new ArrayList<>(new HashSet<>(suggestionDto.getTechnology())) : null);
        suggestion.setWebApp(suggestionDto.getWebApp());
        suggestion.setDesktopApp(suggestionDto.getDesktopApp());
        suggestion.setCategory(suggestionDto.getCategory());
        suggestion.setModelingLanguages(getModelingLanguagesByName(suggestionDto.getModelingLanguages()));
        suggestion.setSourceCodeGeneration(suggestionDto.getSourceCodeGeneration());
        suggestion.setCloudService(suggestionDto.getCloudService());
        suggestion.setLicense(suggestionDto.getLicense());
        suggestion.setLoginRequired(suggestionDto.getLoginRequired());
        suggestion.setCreator(filterCreators(suggestionDto.getCreator()));
        suggestion.setPlatform(getPlatformsByName(suggestionDto.getPlatform()));
        suggestion.setRealTimeCollab(suggestionDto.getRealTimeCollab());
        suggestion.setProgrammingLanguage(getProgrammingLanguagesByName(suggestionDto.getProgrammingLanguage()));
        return suggestion;
    }

    public ModelingToolDto modelingToolSuggestionToModelingToolDto(ModelingToolSuggestion suggestion) {
        LOGGER.debug("modelingToolSuggestionToModelingToolDto({})", suggestion);

        ModelingToolDto toolDto = new ModelingToolDto();
        toolDto.setId(suggestion.getModelingToolId());
        toolDto.setName(suggestion.getName());
        toolDto.setLink(suggestion.getLink());
        toolDto.setOpenSource(suggestion.getOpenSource());
        toolDto.setTechnology(suggestion.getTechnology());
        toolDto.setWebApp(suggestion.getWebApp());
        toolDto.setDesktopApp(suggestion.getDesktopApp());
        toolDto.setCategory(suggestion.getCategory());
        toolDto.setModelingLanguages(getModelingLanguageNames(suggestion.getModelingLanguages()));
        toolDto.setSourceCodeGeneration(suggestion.getSourceCodeGeneration());
        toolDto.setCloudService(suggestion.getCloudService());
        toolDto.setLicense(suggestion.getLicense());
        toolDto.setLoginRequired(suggestion.getLoginRequired());
        toolDto.setCreator(suggestion.getCreator());
        toolDto.setPlatform(getPlatformNames(suggestion.getPlatform()));
        toolDto.setRealTimeCollab(suggestion.getRealTimeCollab());
        toolDto.setProgrammingLanguage(getProgrammingLanguageNames(suggestion.getProgrammingLanguage()));
        return toolDto;
    }

    public ModelingToolVerified modelingToolSuggestionToModelingTool(ModelingToolSuggestion suggestion) {
        LOGGER.debug("modelingToolSuggestionToModelingTool({})", suggestion);

        ModelingToolVerified modelingToolVerified = new ModelingToolVerified();
        modelingToolVerified.setName(suggestion.getName());
        modelingToolVerified.setLink(suggestion.getLink());
        modelingToolVerified.setOpenSource(suggestion.getOpenSource());
        modelingToolVerified.setTechnology(suggestion.getTechnology());
        modelingToolVerified.setWebApp(suggestion.getWebApp());
        modelingToolVerified.setDesktopApp(suggestion.getDesktopApp());
        modelingToolVerified.setCategory(suggestion.getCategory());
        modelingToolVerified.setModelingLanguages(suggestion.getModelingLanguages());
        modelingToolVerified.setSourceCodeGeneration(suggestion.getSourceCodeGeneration());
        modelingToolVerified.setCloudService(suggestion.getCloudService());
        modelingToolVerified.setLicense(suggestion.getLicense());
        modelingToolVerified.setLoginRequired(suggestion.getLoginRequired());
        modelingToolVerified.setCreator(suggestion.getCreator());
        modelingToolVerified.setPlatform(suggestion.getPlatform());
        modelingToolVerified.setRealTimeCollab(suggestion.getRealTimeCollab());
        modelingToolVerified.setProgrammingLanguage(suggestion.getProgrammingLanguage());
        return modelingToolVerified;
    }

    private List<String> getModelingLanguageNames(List<ModelingLanguage> modelingLanguages) {
        if (modelingLanguages == null || modelingLanguages.isEmpty()) {
            return null;
        }
        return modelingLanguages.stream().map(ModelingLanguage::getName).toList();
    }

    private List<ModelingLanguage> getModelingLanguagesByName(List<String> modelingLanguageNames) {
        if (modelingLanguageNames == null || modelingLanguageNames.isEmpty()) {
            return null;
        }
        List<ModelingLanguage> modelingLanguages = new ArrayList<>();
        for (String name : modelingLanguageNames) {
            List<ModelingLanguage> language = modelingLanguageRepository.findByNameIgnoreCase(name);
            if (language.size() > 1) {
                throw new FatalException("More than one modeling language with the same name stored in the system");
            }
            // TODO: What happens if no matches?
            modelingLanguages.add(language.get(0));
        }
        return modelingLanguages;
    }

    private List<String> getPlatformNames(List<Platform> platforms) {
        if (platforms == null || platforms.isEmpty()) {
            return null;
        }
        return platforms.stream().map(Platform::getName).toList();
    }

    private List<Platform> getPlatformsByName(List<String> platformNames) {
        if (platformNames == null || platformNames.isEmpty()) {
            return null;
        }
        List<Platform> platforms = new ArrayList<>();
        for (String name : platformNames) {
            List<Platform> platform = platformRepository.findByNameIgnoreCase(name);
            if (platform.size() > 1) {
                throw new FatalException("More than one platform with the same name stored in the system");
            }
            // TODO: What happens if no matches?
            platforms.add(platform.get(0));
        }
        return platforms;
    }

    private List<String> getProgrammingLanguageNames(List<ProgrammingLanguage> programmingLanguages) {
        if (programmingLanguages == null || programmingLanguages.isEmpty()) {
            return null;
        }
        return programmingLanguages.stream().map(ProgrammingLanguage::getName).toList();
    }

    private List<ProgrammingLanguage> getProgrammingLanguagesByName(List<String> programmingLanguageNames) {
        if (programmingLanguageNames == null || programmingLanguageNames.isEmpty()) {
            return null;
        }
        List<ProgrammingLanguage> programmingLanguages = new ArrayList<>();
        for (String name : programmingLanguageNames) {
            List<ProgrammingLanguage> languages = programmingLanguagesRepository.findByNameIgnoreCase(name);
            if (languages.size() > 1) {
                throw new FatalException("More than one programming language with the same name stored in the system");
            }
            // TODO: What happens if no matches?
            programmingLanguages.add(languages.get(0));
        }
        return programmingLanguages;
    }

    private List<String> filterCreators(List<String> creators) {
        if (creators == null) {
            return null;
        }
        List<String> result = new ArrayList<>();
        for (String creator : creators) {
            boolean containsCreator = false;
            for (String resCreator : result) {
                if (resCreator.trim().equalsIgnoreCase(creator.trim())) {
                    containsCreator = true;
                    break;
                }
            }
            if (!containsCreator) {
                result.add(creator);
            }
        }
        return result;
    }

    public void setModelingLanguageRepository(ModelingLanguageRepository modelingLanguageRepository) {
        this.modelingLanguageRepository = modelingLanguageRepository;
    }

    public void setPlatformRepository(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

    public void setProgrammingLanguagesRepository(ProgrammingLanguagesRepository programmingLanguagesRepository) {
        this.programmingLanguagesRepository = programmingLanguagesRepository;
    }
}
