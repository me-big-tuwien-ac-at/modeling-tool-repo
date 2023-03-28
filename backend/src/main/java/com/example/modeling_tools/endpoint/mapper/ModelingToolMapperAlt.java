package com.example.modeling_tools.endpoint.mapper;

import com.example.modeling_tools.endpoint.dto.ModelingToolDto;
import com.example.modeling_tools.endpoint.dto.ModelingToolSuggestionDto;
import com.example.modeling_tools.entity.ModelingTool;
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


    public ModelingTool modelingToolDtoToModelingTool(ModelingToolDto modelingToolDto) {
        LOGGER.debug("modelingToolDtoToModelingTool({})", modelingToolDto);

        ModelingTool modelingTool = new ModelingTool();
        modelingTool.setId(modelingToolDto.getId());
        modelingTool.setName(modelingToolDto.getName());
        modelingTool.setLink(modelingToolDto.getLink());
        modelingTool.setOpenSource(modelingToolDto.getOpenSource());
        modelingTool.setTechnology(modelingToolDto.getTechnology());
        modelingTool.setWebApp(modelingToolDto.getWebApp());
        modelingTool.setDesktopApp(modelingToolDto.getDesktopApp());
        modelingTool.setCategory(modelingToolDto.getCategory());
        modelingTool.setModelingLanguages(getModelingLanguagesByName(modelingToolDto.getModelingLanguages()));
        modelingTool.setSourceCodeGeneration(modelingToolDto.getSourceCodeGeneration());
        modelingTool.setCloudService(modelingToolDto.getCloudService());
        modelingTool.setLicense(modelingToolDto.getLicense());
        modelingTool.setLoginRequired(modelingToolDto.getLoginRequired());
        modelingTool.setCreator(modelingToolDto.getCreator());
        modelingTool.setPlatform(getPlatformsByName(modelingToolDto.getPlatform()));
        modelingTool.setRealTimeCollab(modelingToolDto.getRealTimeCollab());
        modelingTool.setProgrammingLanguage(getProgrammingLanguagesByName(modelingToolDto.getProgrammingLanguage()));
        return modelingTool;
    }

    public ModelingToolDto modelingToolToModelingToolDto(ModelingTool modelingTool) {
        LOGGER.debug("modelingToolToModelingToolDto({})", modelingTool);

        return new ModelingToolDto(
                modelingTool.getId(),
                modelingTool.getName(),
                modelingTool.getLink(),
                modelingTool.getOpenSource(),
                modelingTool.getTechnology(),
                modelingTool.getWebApp(),
                modelingTool.getDesktopApp(),
                modelingTool.getCategory(),
                getModelingLanguageNames(modelingTool.getModelingLanguages()),
                modelingTool.getSourceCodeGeneration(),
                modelingTool.getCloudService(),
                modelingTool.getLicense(),
                modelingTool.getLoginRequired(),
                modelingTool.getCreator(),
                getPlatformNames(modelingTool.getPlatform()),
                modelingTool.getRealTimeCollab(),
                getProgrammingLanguageNames(modelingTool.getProgrammingLanguage())
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

    public ModelingTool modelingToolSuggestionToModelingTool(ModelingToolSuggestion suggestion) {
        LOGGER.debug("modelingToolSuggestionToModelingTool({})", suggestion);

        ModelingTool modelingTool = new ModelingTool();
        modelingTool.setName(suggestion.getName());
        modelingTool.setLink(suggestion.getLink());
        modelingTool.setOpenSource(suggestion.getOpenSource());
        modelingTool.setTechnology(suggestion.getTechnology());
        modelingTool.setWebApp(suggestion.getWebApp());
        modelingTool.setDesktopApp(suggestion.getDesktopApp());
        modelingTool.setCategory(suggestion.getCategory());
        modelingTool.setModelingLanguages(suggestion.getModelingLanguages());
        modelingTool.setSourceCodeGeneration(suggestion.getSourceCodeGeneration());
        modelingTool.setCloudService(suggestion.getCloudService());
        modelingTool.setLicense(suggestion.getLicense());
        modelingTool.setLoginRequired(suggestion.getLoginRequired());
        modelingTool.setCreator(suggestion.getCreator());
        modelingTool.setPlatform(suggestion.getPlatform());
        modelingTool.setRealTimeCollab(suggestion.getRealTimeCollab());
        modelingTool.setProgrammingLanguage(suggestion.getProgrammingLanguage());
        return modelingTool;
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
