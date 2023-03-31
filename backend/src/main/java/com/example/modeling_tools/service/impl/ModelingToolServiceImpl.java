package com.example.modeling_tools.service.impl;

import com.example.modeling_tools.endpoint.dto.ModelingToolDto;
import com.example.modeling_tools.endpoint.dto.ModelingToolSearchDto;
import com.example.modeling_tools.endpoint.dto.ModelingToolSuggestionDto;
import com.example.modeling_tools.endpoint.mapper.ModelingToolMapperAlt;
import com.example.modeling_tools.entity.ModelingToolVerified;
import com.example.modeling_tools.entity.ModelingToolSuggestion;
import com.example.modeling_tools.entity.properties.ModelingLanguage;
import com.example.modeling_tools.entity.properties.Platform;
import com.example.modeling_tools.entity.properties.ProgrammingLanguage;
import com.example.modeling_tools.exception.DuplicateException;
import com.example.modeling_tools.exception.FatalException;
import com.example.modeling_tools.exception.NotFoundException;
import com.example.modeling_tools.exception.ValidationException;
import com.example.modeling_tools.repository.ModelingToolRepository;
import com.example.modeling_tools.repository.ModelingToolSuggestionRepository;
import com.example.modeling_tools.repository.properties.ModelingLanguageRepository;
import com.example.modeling_tools.repository.properties.PlatformRepository;
import com.example.modeling_tools.repository.properties.ProgrammingLanguagesRepository;
import com.example.modeling_tools.service.EmailBuilder;
import com.example.modeling_tools.service.EmailSenderService;
import com.example.modeling_tools.service.ModelingToolService;
import com.example.modeling_tools.type.Technology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.*;

@Service
public class ModelingToolServiceImpl implements ModelingToolService, EmailBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ModelingToolRepository repository;
    private final ModelingToolSuggestionRepository suggestionRepository;
    private final ModelingLanguageRepository modelingLanguageRepository;
    private final PlatformRepository platformRepository;
    private final ProgrammingLanguagesRepository programmingLanguagesRepository;
    private final ModelingToolMapperAlt mapperAlt;
    private final EmailSenderService emailSender;


    public ModelingToolServiceImpl(ModelingToolRepository repository,
                                   ModelingToolSuggestionRepository suggestionRepository,
                                   ModelingLanguageRepository modelingLanguageRepository,
                                   PlatformRepository platformRepository,
                                   ProgrammingLanguagesRepository programmingLanguagesRepository,
                                   EmailSenderService emailSender) {
        this.repository = repository;
        this.suggestionRepository = suggestionRepository;
        this.modelingLanguageRepository = modelingLanguageRepository;
        this.platformRepository = platformRepository;
        this.programmingLanguagesRepository = programmingLanguagesRepository;
        this.emailSender = emailSender;
        this.mapperAlt = ModelingToolMapperAlt.INSTANCE;
        this.mapperAlt.setModelingLanguageRepository(modelingLanguageRepository);
        this.mapperAlt.setPlatformRepository(platformRepository);
        this.mapperAlt.setProgrammingLanguagesRepository(programmingLanguagesRepository);
    }

    @Override
    public List<ModelingToolDto> getModelingTools(ModelingToolSearchDto searchDto) {
        LOGGER.debug("Getting all modeling tools");

        List<ModelingToolVerified> repo;
        if (searchDto == null || searchDto.isEmpty()) {
            repo = repository.findAll();
        } else {
            setSearchDtoParamsAsSqlParams(searchDto);
            repo = repository.findBySearch(
                    searchDto.getName(),
                    searchDto.getLink(),
                    searchDto.getCategory(),
                    searchDto.getLicense(),
                    searchDto.getOpenSource(),
                    searchDto.getWebApp(),
                    searchDto.getDesktopApp(),
                    searchDto.getSourceCodeGeneration(),
                    searchDto.getCloudService(),
                    searchDto.getLoginRequired(),
                    searchDto.getRealTimeCollab()
            );
            if (!searchDto.listsEmpty()) {
                List<ModelingToolVerified> filteredRepo = new ArrayList<>();
                for (ModelingToolVerified tool : repo) {
                    String creator = searchDto.getCreatorUpper();
                    Boolean creatorMatch = null;
                    if (creator != null) {
                        for (String c : tool.getCreatorUpper()) {
                            if (c.contains(creator)) {
                                creatorMatch = true;
                                break;
                            }
                        }
                    }
                    if (
                            (searchDto.getPlatform() == null || (searchDto.getPlatform() != null && tool.getPlatform() != null
                            && new HashSet<>(tool.getPlatformAsStringUpper()).containsAll(searchDto.getPlatformUpper())))
                            &&
                            (searchDto.getCreator() == null || creatorMatch != null)
                            &&
                            (searchDto.getModelingLanguages() == null || (searchDto.getModelingLanguages() != null && tool.getModelingLanguages() != null
                            && new HashSet<>(tool.getModelingLanguagesAsStringUpper()).containsAll(searchDto.getModelingLanguagesUpper())))
                            &&
                            (searchDto.getTechnology() == null || (searchDto.getTechnology() != null && tool.getTechnology() != null
                            && new HashSet<>(tool.getTechnologyAsStringUpper()).containsAll(searchDto.getTechnologyStringUpper())))
                            &&
                            (searchDto.getProgrammingLanguage() == null || (searchDto.getProgrammingLanguage() != null && tool.getProgrammingLanguage() != null
                            && new HashSet<>(tool.getProgrammingLanguageAsStringUpper()).containsAll(searchDto.getProgrammingLanguageUpper())))
                    ) {
                        filteredRepo.add(tool);
                    }
                }
                repo = filteredRepo;
            }
        }

        List<ModelingToolDto> result = new ArrayList<>();

        for (ModelingToolVerified tool : repo) {
            result.add(mapperAlt.modelingToolToModelingToolDto(tool));
        }

        return result;
    }

    private void setSearchDtoParamsAsSqlParams(ModelingToolSearchDto searchDto) {
        if (searchDto.getName() == null) {
            searchDto.setName("");
        } else {
            searchDto.setName(searchDto.getName().toUpperCase());
        }
        if (searchDto.getLink() == null) {
            searchDto.setLink("");
        } else {
            searchDto.setLink(searchDto.getLink().toUpperCase());
        }
        if (searchDto.getCategory() != null) {
            searchDto.setLink(searchDto.getLink().toUpperCase());
        }
        if (searchDto.getLicense() != null) {
            searchDto.setLink(searchDto.getLink().toUpperCase());
        }
    }

    @Override
    public ModelingToolDto getModelingToolById(Long id) throws NotFoundException {
        LOGGER.debug("Getting Modeling Tool by id");

        Optional<ModelingToolVerified> tool = repository.findById(id);

        if (tool.isEmpty()) {
            throw new NotFoundException("Modeling Tool with ID number " + id + " could not be found");
        }

        return mapperAlt.modelingToolToModelingToolDto(tool.get());
    }

    @Override
    public List<ModelingToolSuggestionDto> getModelingToolSuggestions() {
        LOGGER.debug("Getting all modeling tool suggestions");

        List<ModelingToolSuggestionDto> result = new ArrayList<>();
        List<ModelingToolSuggestion> repo = suggestionRepository.findAll();

        for (ModelingToolSuggestion suggestion : repo) {
            result.add(mapperAlt.modelingToolSuggestionToModelingToolSuggestionDto(suggestion));
        }

        return result;
    }

    @Override
    public ModelingToolDto storeModelingToolSuggestion(ModelingToolSuggestionDto suggestionDto, Boolean ignoreMail) throws ValidationException {
        LOGGER.debug("Storing modeling tool suggestion");

        List<String> validationExceptions = validateModelingToolSuggestion(suggestionDto);
        List<ModelingToolVerified> storedModelingToolVerified = repository.findByName(suggestionDto.getName());

        if (!storedModelingToolVerified.isEmpty()) {
            validationExceptions.add("Modeling Tool with that name already stored");
        }
        if (!validationExceptions.isEmpty()) {
            throw new ValidationException("Invalid modeling tool suggestion", validationExceptions);
        }

        List<ModelingLanguage> modelingLanguages = storeModelingLanguageSuggestions(suggestionDto);
        List<Platform> platforms = storePlatformSuggestions(suggestionDto);
        List<ProgrammingLanguage> programmingLanguages = storeProgrammingLanguageSuggestions(suggestionDto);

        ModelingToolSuggestion suggestion = suggestionRepository.save(
                mapperAlt.modelingToolSuggestionDtoToModelingToolSuggestion(suggestionDto)
        );

        assignNewlyStoredModelingLanguage(suggestion, modelingLanguages);
        assignNewlyStoredPlatform(suggestion, platforms);
        assignNewlyStoredProgrammingLanguages(suggestion, programmingLanguages);

        ModelingToolDto toolDto = mapperAlt.modelingToolSuggestionToModelingToolDto(suggestion);

        String confirmLink = "http://localhost:8080/api/v1/modeling_tools/confirm?token=" + suggestion.getAdminToken();
        String deleteLink = "http://localhost:8080/api/v1/modeling_tools/delete?token=" + suggestion.getAdminToken();

        if (ignoreMail == null || !ignoreMail) {
            emailSender.sendEmail(
                    suggestionDto.getUserEmail(),
                    "Modeling Tools",
                    buildEmail(toolDto.toHtmlString(), buildFeedback(suggestionDto.getFeedback()))
            );
            emailSender.sendEmail(
                    "web.modeling.tools@gmail.com",
                    "[CREATE] Confirm new Modeling Tool",
                    buildAdminEmail(
                            confirmLink,
                            toolDto.toHtmlString(),
                            suggestionDto.getUserEmail(),
                            buildFeedback(suggestionDto.getFeedback()),
                            deleteLink
                    )
            );
        } else {
            LOGGER.info("ignoreMail status=true");
        }

        return toolDto;
    }

    @Override
    public ModelingToolDto updateModelingToolSuggestion(
            ModelingToolSuggestionDto suggestionDto,
            Long id,
            Boolean ignoreMail
    ) throws DuplicateException, NotFoundException, ValidationException {
        LOGGER.debug("Storing a modeling tool update suggestion");
        Optional<ModelingToolVerified> modelingTool = repository.findById(id);

        if (modelingTool.isEmpty()) {
            throw new NotFoundException("Modeling tool with id " + id + " does not exist!");
        }
        List<String> validationExceptions = validateModelingToolSuggestion(suggestionDto);
        if (!validationExceptions.isEmpty()) {
            throw new ValidationException("Invalid modeling tool suggestion", validationExceptions);
        }
        if (areSuggestionsEqual(modelingTool.get(), suggestionDto)) {
            if (suggestionDto.getFeedback() != null) {
                if (ignoreMail == null || !ignoreMail) {
                    ModelingToolDto tool = mapperAlt.modelingToolToModelingToolDto(modelingTool.get());
                    emailSender.sendEmail(
                            "web.modeling.tools@gmail.com",
                            "Feedback",
                            buildFeedbackEmail(
                                    tool.toHtmlString(),
                                    suggestionDto.getUserEmail(),
                                    buildFeedback(suggestionDto.getFeedback())
                            )
                    );
                }
                return mapperAlt.modelingToolToModelingToolDto(modelingTool.get());
            } else {
                throw new DuplicateException("Invalid modeling tool suggestion", List.of(
                        "Modeling Tool and Modeling Tool Suggestion have the same values"));
            }
        }

        suggestionDto.setModelingToolId(id);
        List<ModelingLanguage> storedModelingLanguages = storeModelingLanguageSuggestions(suggestionDto);
        List<Platform> storedPlatforms = storePlatformSuggestions(suggestionDto);
        List<ProgrammingLanguage> storedProgrammingLanguages = storeProgrammingLanguageSuggestions(suggestionDto);

        ModelingToolSuggestion suggestion = suggestionRepository.save(
                mapperAlt.modelingToolSuggestionDtoToModelingToolSuggestion(suggestionDto)
        );

        assignNewlyStoredModelingLanguage(suggestion, storedModelingLanguages);
        assignNewlyStoredPlatform(suggestion, storedPlatforms);
        assignNewlyStoredProgrammingLanguages(suggestion, storedProgrammingLanguages);

        ModelingToolDto toolDto = mapperAlt.modelingToolSuggestionToModelingToolDto(suggestion);
        ModelingToolDto storedModelingTool = mapperAlt.modelingToolToModelingToolDto(modelingTool.get());


        String toolHtmlOriginal = storedModelingTool.toHtmlStringComparison(toolDto, "#A3E5FC");
        String toolHtmlNew = toolDto.toHtmlStringComparison(storedModelingTool, "#FFFF33");
        String toolHtml = buildComparisonEmail(toolHtmlOriginal, toolHtmlNew);

        String confirmLink = "http://localhost:8080/api/v1/modeling_tools/confirm?token=" + suggestion.getAdminToken();
        String deleteLink = "http://localhost:8080/api/v1/modeling_tools/delete?token=" + suggestion.getAdminToken();

        if (ignoreMail == null || !ignoreMail) {
            emailSender.sendEmail(
                    suggestionDto.getUserEmail(),
                    "Modeling Tools",
                    buildEmail(toolHtml, buildFeedback(suggestionDto.getFeedback()))
            );
            emailSender.sendEmail(
                    "web.modeling.tools@gmail.com",
                    "[EDIT] Confirm Modeling Tool Edit",
                    buildAdminEmail(
                            confirmLink,
                            toolHtml,
                            suggestionDto.getUserEmail(),
                            buildFeedback(suggestionDto.getFeedback()),
                            deleteLink
                    )
            );
        } else {
            LOGGER.info("ignoreMail status=true");
        }

        return toolDto;
    }

    @Override
    public String postConfirmModelingToolSuggestion(String token) throws FatalException, NotFoundException {
        LOGGER.debug("Confirming modeling tool suggestion");

        List<ModelingToolSuggestion> suggestions = suggestionRepository.findByAdminToken(token);
        if (suggestions.size() > 1) {
            throw new FatalException("More than one Modeling Tool Suggestion stored with the same token!");
        }
        if (!suggestions.isEmpty()) {
            ModelingToolSuggestion suggestion = suggestions.get(0);
            if (suggestion.getAdminToken().equals(token)) {
                // Removing modeling tool suggestion from each programming language
                List<ModelingLanguage> suggestionModelingLanguages =
                        modelingLanguageRepository.findModelingLanguageByModelingToolSuggestionId(suggestion.getId());
                List<Platform> suggestionPlatforms =
                        platformRepository.findPlatformByModelingToolSuggestionId(suggestion.getId());
                List<ProgrammingLanguage> suggestionProgrammingLanguages =
                        programmingLanguagesRepository.findProgrammingLanguageByModelingToolSuggestionId(suggestion.getId());

                // TODO: Three for-loops below potentially deletable? Do not appear to be doing anything
                for (ModelingLanguage language : suggestionModelingLanguages) {
                    List<ModelingToolSuggestion> suggestionsByPlatform =
                            suggestionRepository.findModelingToolSuggestionByModelingLanguageId(language.getId());
                    List<ModelingToolSuggestion> suggestionsNew = new ArrayList<>();
                    for (ModelingToolSuggestion s : suggestionsByPlatform) {
                        if (!s.getId().equals(suggestion.getId())) {
                            suggestionsNew.add(s);
                        }
                    }
                    language.setModelingToolSuggestions(suggestionsNew);
                }
                for (Platform platform : suggestionPlatforms) {
                    List<ModelingToolSuggestion> suggestionsByPlatform =
                            suggestionRepository.findModelingToolSuggestionByPlatformId(platform.getId());
                    List<ModelingToolSuggestion> suggestionsNew = new ArrayList<>();
                    for (ModelingToolSuggestion s : suggestionsByPlatform) {
                        if (!s.getId().equals(suggestion.getId())) {
                            suggestionsNew.add(s);
                        }
                    }
                    platform.setModelingToolSuggestions(suggestionsNew);
                }
                for (ProgrammingLanguage language : suggestionProgrammingLanguages) {
                    List<ModelingToolSuggestion> suggestionsByLanguage =
                            suggestionRepository.findModelingToolSuggestionByProgrammingLanguageId(language.getId());
                    List<ModelingToolSuggestion> suggestionsNew = new ArrayList<>();
                    for (ModelingToolSuggestion s : suggestionsByLanguage) {
                        if (!s.getId().equals(suggestion.getId())) {
                            suggestionsNew.add(s);
                        }
                    }
                    language.setModelingToolSuggestions(suggestionsNew);
                }

                // Removing modeling tool suggestion
                suggestionRepository.deleteById(suggestion.getId());

                ModelingToolVerified toolMapped = new ModelingToolVerified();
                if (suggestion.getModelingToolId() != null) {
                    toolMapped.setId(suggestion.getModelingToolId());
                }
                toolMapped.setName(suggestion.getName());
                toolMapped.setLink(suggestion.getLink());
                toolMapped.setOpenSource(suggestion.getOpenSource());
                toolMapped.setTechnology(null);
                toolMapped.setWebApp(suggestion.getWebApp());
                toolMapped.setDesktopApp(suggestion.getDesktopApp());
                toolMapped.setCategory(suggestion.getCategory());
                toolMapped.setModelingLanguages(suggestionModelingLanguages);
                toolMapped.setSourceCodeGeneration(suggestion.getSourceCodeGeneration());
                toolMapped.setCloudService(suggestion.getCloudService());
                toolMapped.setLicense(suggestion.getLicense());
                toolMapped.setLoginRequired(suggestion.getLoginRequired());
                toolMapped.setCreator(null);
                toolMapped.setPlatform(suggestionPlatforms);
                toolMapped.setRealTimeCollab(suggestion.getRealTimeCollab());
                toolMapped.setProgrammingLanguage(suggestionProgrammingLanguages);

                ModelingToolVerified verifiedTool = repository.save(toolMapped);

                // Jpa ElementCollections do not seem to be updated if they are changed with setters or the lists
                // themselves are altered. Therefore, both Technology and Creator are first stored with null values,
                // then overridden with suggestion values
                verifiedTool.setTechnology(suggestion.getTechnology());
                verifiedTool.setCreator(suggestion.getCreator());
                verifiedTool = repository.save(verifiedTool);

                for (ModelingLanguage language : suggestionModelingLanguages) {
                    List<ModelingToolVerified> suggestionsByLanguage = repository.findModelingToolByModelingLanguageId(language.getId());
                    if (suggestionsByLanguage != null) {
                        suggestionsByLanguage.add(verifiedTool);
                        language.setModelingTools(suggestionsByLanguage);
                        modelingLanguageRepository.save(language);
                    } else {
                        language.setModelingTools(List.of(verifiedTool));
                        modelingLanguageRepository.save(language);
                    }
                }
                for (Platform platform : suggestionPlatforms) {
                    List<ModelingToolVerified> suggestionsByPlatform = repository.findModelingToolVerifiedByPlatformId(platform.getId());
                    if (suggestionsByPlatform != null) {
                        suggestionsByPlatform.add(verifiedTool);
                        platform.setModelingTools(suggestionsByPlatform);
                        platformRepository.save(platform);
                    } else {
                        platform.setModelingTools(List.of(verifiedTool));
                        platformRepository.save(platform);
                    }
                }
                for (ProgrammingLanguage language : suggestionProgrammingLanguages) {
                    List<ModelingToolVerified> suggestionsByLanguage = repository.findModelingToolVerifiedByProgrammingLanguageId(language.getId());
                    if (suggestionsByLanguage != null) {
                        suggestionsByLanguage.add(verifiedTool);
                        language.setModelingTools(suggestionsByLanguage);
                        programmingLanguagesRepository.save(language);
                    } else {
                        language.setModelingTools(List.of(verifiedTool));
                        programmingLanguagesRepository.save(language);
                    }
                }

                ModelingToolDto toolDto = mapperAlt.modelingToolToModelingToolDto(verifiedTool);
                return build201ResponsePage(verifiedTool.getId(), toolDto.getName(), toolDto.toHtmlString());
            }
        }
        throw new NotFoundException(build401ResponsePage(Crud.stored));
    }

    @Override
    public String removeModelingToolSuggestion(String token) throws NotFoundException {
        List<ModelingToolSuggestion> suggestionList = suggestionRepository.findByAdminToken(token);
        if (!suggestionList.isEmpty()) {
            if (suggestionList.size() > 1) {
                throw new FatalException("More than one Modeling Tool Suggestion stored with the same token!");
            }
            ModelingToolSuggestion suggestion = suggestionList.get(0);
            detachModelingToolSuggestion(suggestion);

            return build200ResponsePage(suggestion.getName());
        } else {
            throw new NotFoundException(build401ResponsePage(Crud.deleted));
        }
    }

    private List<String> validateModelingToolSuggestion(ModelingToolSuggestionDto suggestionDto) {
        List<String> validationExceptions = new ArrayList<>();
        String nameEval = validateStringValue(suggestionDto.getName());
        if (nameEval != null) {
            validationExceptions.add("Name " + nameEval);
        }
        String linkEval = validateStringValue(suggestionDto.getLink());
        if (linkEval != null) {
            validationExceptions.add("Link " + linkEval);
        }
        String emailEval = validateStringValue(suggestionDto.getUserEmail());
        if (emailEval != null) {
            validationExceptions.add("Email " + linkEval);
        }
        return validationExceptions;
    }

    private String validateStringValue(String value) {
        if (value == null) {
            return "needs to be specified!";
        } else {
            if (value.trim().isEmpty()) {
                return "should not contain just of whitespaces!";
            }
            if (value.length() > 255) {
                return "needs to be at most 255 characters long!";
            }
        }
        return null;
    }

    private boolean areSuggestionsEqual(ModelingToolVerified tool, ModelingToolSuggestionDto suggestion) {
        return
                tool.getName().trim().equalsIgnoreCase(suggestion.getName().trim()) &&
                tool.getLink().trim().equalsIgnoreCase(suggestion.getLink().trim()) &&
                tool.getOpenSource().equals(suggestion.getOpenSource()) &&
                areTechnologiesEqual(tool.getTechnology(), suggestion.getTechnology()) &&
                tool.getWebApp().equals(suggestion.getWebApp()) &&
                tool.getDesktopApp().equals(suggestion.getDesktopApp()) &&
                tool.getCategory().equals(suggestion.getCategory()) &&
                areModelingLanguagesEqual(modelingLanguageRepository.findModelingLanguageByModelingToolId(tool.getId()), suggestion.getModelingLanguages()) &&
                tool.getSourceCodeGeneration().equals(suggestion.getSourceCodeGeneration()) &&
                tool.getCloudService().equals(suggestion.getCloudService()) &&
                tool.getLicense().equals(suggestion.getLicense()) &&
                tool.getLoginRequired().equals(suggestion.getLoginRequired()) &&
                tool.getRealTimeCollab().equals(suggestion.getRealTimeCollab()) &&
                areCreatorsEqual(tool.getCreator(), suggestion.getCreator()) &&
                arePlatformsEqual(tool.getPlatform(), suggestion.getPlatform()) &&
                areProgrammingLanguagesEqual(tool.getProgrammingLanguage(), suggestion.getProgrammingLanguage());
    }

    private Boolean areListStructuresEqual(List<?> list1, List<?> list2) {
        if (list1 == null && list2 == null) {
            return true;
        }
        if ((list1 == null && list2.isEmpty()) || (list2 == null && list1.isEmpty())) {
            return true;
        }
        if (list1 == null || list2 == null) {
            return false;
        }
        if (list1.isEmpty() && list2.isEmpty()) {
            return true;
        }
        return null;
    }

    private boolean areCreatorsEqual(List<String> toolValue1, List<String> toolValue2) {
        Boolean listStructures = areListStructuresEqual(toolValue1, toolValue2);
        if ((listStructures == null || listStructures) && (toolValue1 == null || toolValue2 == null)) {
            return true;
        }
        return new HashSet<>(toolValue1).containsAll(toolValue2) && new HashSet<>(toolValue2).containsAll(toolValue1);
    }

    private boolean areTechnologiesEqual(List<Technology> toolValue1, List<Technology> toolValue2) {
        Boolean listStructures = areListStructuresEqual(toolValue1, toolValue2);
        if ((listStructures == null || listStructures) && (toolValue1 == null || toolValue2 == null)) {
            return true;
        }
        return new HashSet<>(toolValue1).containsAll(toolValue2) && new HashSet<>(toolValue2).containsAll(toolValue1);
    }

    private boolean areModelingLanguagesEqual(List<ModelingLanguage> toolProperties, List<String> suggestionProperties) {
        Boolean listStructures = areListStructuresEqual(toolProperties, suggestionProperties);
        if (listStructures == null || listStructures) {
            if (toolProperties == null || suggestionProperties == null) {
                return true;
            }
            List<String> toolStrings = toolProperties.stream().map(ml -> ml.getName().trim().toUpperCase()).toList();
            List<String> suggestionStrings = suggestionProperties.stream().map(name -> name.trim().toUpperCase()).toList();
            return new HashSet<>(toolStrings).containsAll(suggestionStrings) && new HashSet<>(suggestionStrings).containsAll(toolStrings);
        }
        return false;
    }

    private boolean arePlatformsEqual(List<Platform> toolProperties, List<String> suggestionProperties) {
        Boolean listStructures = areListStructuresEqual(toolProperties, suggestionProperties);
        if (listStructures == null || listStructures) {
            if (toolProperties == null || suggestionProperties == null) {
                return true;
            }
            List<String> toolStrings = toolProperties.stream().map(platform -> platform.getName().trim().toUpperCase()).toList();
            List<String> suggestionStrings = suggestionProperties.stream().map(name -> name.trim().toUpperCase()).toList();
            return new HashSet<>(toolStrings).containsAll(suggestionStrings) && new HashSet<>(suggestionStrings).containsAll(toolStrings);
        }
        return false;
    }

    private boolean areProgrammingLanguagesEqual(List<ProgrammingLanguage> toolProperties, List<String> suggestionProperties) {
        Boolean listStructures = areListStructuresEqual(toolProperties, suggestionProperties);
        if (listStructures == null || listStructures) {
            if (toolProperties == null || suggestionProperties == null) {
                return true;
            }
            List<String> toolStrings = toolProperties.stream().map(platform -> platform.getName().trim().toUpperCase()).toList();
            List<String> suggestionStrings = suggestionProperties.stream().map(name -> name.trim().toUpperCase()).toList();
            return new HashSet<>(toolStrings).containsAll(suggestionStrings) && new HashSet<>(suggestionStrings).containsAll(toolStrings);
        }
        return false;
    }

    private List<ModelingLanguage> storeModelingLanguageSuggestions(ModelingToolSuggestionDto suggestionDto) {
        List<ModelingLanguage> storedModelingLanguages = new ArrayList<>();
        if (suggestionDto.getModelingLanguages() != null) {
            Set<String> uniqueModelingLanguages = new HashSet<>(suggestionDto.getModelingLanguages());
            for (String modelingLanguage : uniqueModelingLanguages) {
                List<ModelingLanguage> platformRepo = modelingLanguageRepository.findByNameIgnoreCase(modelingLanguage);
                if (modelingLanguageRepository.findByNameIgnoreCase(modelingLanguage).isEmpty()) {
                    LOGGER.info("Storing a new modeling language suggestion: " + modelingLanguage);
                    storedModelingLanguages.add(
                            modelingLanguageRepository.save(new ModelingLanguage(modelingLanguage, true))
                    );
                } else {
                    storedModelingLanguages.addAll(platformRepo);
                }
            }
        }

        return storedModelingLanguages;
    }

    private List<Platform> storePlatformSuggestions(ModelingToolSuggestionDto suggestionDto) {
        List<Platform> storedPlatforms = new ArrayList<>();
        if (suggestionDto.getPlatform() != null) {
            Set<String> uniquePlatforms = new HashSet<>(suggestionDto.getPlatform());
            for (String platform : uniquePlatforms) {
                List<Platform> platformRepo = platformRepository.findByNameIgnoreCase(platform);
                if (platformRepository.findByNameIgnoreCase(platform).isEmpty()) {
                    LOGGER.info("Storing a new platform suggestion: " + platform);
                    storedPlatforms.add(
                            platformRepository.save(new Platform(platform, true))
                    );
                } else {
                    storedPlatforms.addAll(platformRepo);
                }
            }
        }

        return storedPlatforms;
    }

    private List<ProgrammingLanguage> storeProgrammingLanguageSuggestions(ModelingToolSuggestionDto suggestionDto) {
        List<ProgrammingLanguage> storedProgrammingLanguages = new ArrayList<>();
        if (suggestionDto.getProgrammingLanguage() != null) {
            Set<String> uniqueLanguages = new HashSet<>(suggestionDto.getProgrammingLanguage());
            for (String programmingLanguage : uniqueLanguages) {
                List<ProgrammingLanguage> languageRepo = programmingLanguagesRepository.findByNameIgnoreCase(programmingLanguage);
                if (languageRepo.isEmpty()) {
                    LOGGER.info("Storing a new programming language suggestion: " + programmingLanguage);
                    storedProgrammingLanguages.add(
                            programmingLanguagesRepository.save(new ProgrammingLanguage(programmingLanguage, true))
                    );
                } else {
                    storedProgrammingLanguages.addAll(languageRepo);
                }
            }
        }

        return storedProgrammingLanguages;
    }

    private void assignNewlyStoredModelingLanguage(ModelingToolSuggestion suggestion, List<ModelingLanguage> storedModelingLanguages) {
        for (ModelingLanguage language : storedModelingLanguages) {
            List<ModelingLanguage> languages =
                    modelingLanguageRepository.findByNameIgnoreCase(language.getName());
            if (languages.isEmpty()) {
                language.setModelingToolSuggestions(List.of(suggestion));
                modelingLanguageRepository.save(language);
            } else if (languages.size() == 1) {
                ModelingLanguage repoLanguage = languages.get(0);
                List<ModelingToolSuggestion> suggestions = repoLanguage.getModelingToolSuggestions();
                if (suggestions != null) {
                    suggestions.add(suggestion);
                    language.setModelingToolSuggestions(suggestions);
                    modelingLanguageRepository.save(language);
                } else {
                    List<ModelingToolSuggestion> sList = new ArrayList<>();
                    sList.add(suggestion);
                    language.setModelingToolSuggestions(sList);
                    modelingLanguageRepository.save(language);
                }
                // modelingLanguageRepository.save(repoLanguage);
            } else {
                throw new FatalException("Too many modeling languages stored with the same name!");
            }
        }
    }

    public void detachModelingToolSuggestion(ModelingToolSuggestion suggestion) {
        // Modeling Languages
        List<ModelingLanguage> languages = modelingLanguageRepository.findModelingLanguageByModelingToolSuggestionId(
                suggestion.getId()
        );
        for (ModelingLanguage language : languages) {
            List<ModelingToolSuggestion> suggestions = suggestionRepository.findModelingToolSuggestionByModelingLanguageId(
                    language.getId()
            );
            List<ModelingToolSuggestion> suggestionsUpdated = new ArrayList<>();
            for (ModelingToolSuggestion s : suggestions) {
                if (!s.getId().equals(suggestion.getId())) {
                    suggestionsUpdated.add(s);
                }
            }
            language.setModelingToolSuggestions(suggestionsUpdated);
        }

        // Platforms
        List<Platform> platforms = platformRepository.findPlatformByModelingToolSuggestionId(
                suggestion.getId()
        );
        for (Platform platform : platforms) {
            List<ModelingToolSuggestion> suggestions = suggestionRepository.findModelingToolSuggestionByPlatformId(
                    platform.getId()
            );
            List<ModelingToolSuggestion> suggestionsUpdated = new ArrayList<>();
            for (ModelingToolSuggestion s : suggestions) {
                if (!s.getId().equals(suggestion.getId())) {
                    suggestionsUpdated.add(s);
                }
            }
            platform.setModelingToolSuggestions(suggestionsUpdated);
        }

        // Programming Languages
        List<ProgrammingLanguage> pLanguages = programmingLanguagesRepository.findProgrammingLanguageByModelingToolSuggestionId(
                suggestion.getId()
        );
        for (ProgrammingLanguage language : pLanguages) {
            List<ModelingToolSuggestion> suggestions = suggestionRepository.findModelingToolSuggestionByProgrammingLanguageId(
                    language.getId()
            );
            List<ModelingToolSuggestion> suggestionsUpdated = new ArrayList<>();
            for (ModelingToolSuggestion s : suggestions) {
                if (!s.getId().equals(suggestion.getId())) {
                    suggestionsUpdated.add(s);
                }
            }
            language.setModelingToolSuggestions(suggestionsUpdated);
        }

        suggestionRepository.deleteById(suggestion.getId());

        List<ModelingLanguage> modelingLanguageMlNull = modelingLanguageRepository.findByModelingToolsVerifiedIsNullAndDeletableIsTrue();
        List<Platform> platformMlNull = platformRepository.findByModelingToolsVerifiedIsNullAndDeletableIsTrue();
        List<ProgrammingLanguage> programmingLanguageMlNull = programmingLanguagesRepository.findByModelingToolsVerifiedIsNullAndDeletableIsTrue();

        for (ModelingLanguage language : modelingLanguageMlNull) {
            modelingLanguageRepository.deleteById(language.getId());
        }
        for (Platform platform : platformMlNull) {
            platformRepository.deleteById(platform.getId());
        }
        for (ProgrammingLanguage language : programmingLanguageMlNull) {
            programmingLanguagesRepository.deleteById(language.getId());
        }
    }

    private void assignNewlyStoredPlatform(ModelingToolSuggestion suggestion, List<Platform> storedPlatforms) {
        for (Platform platform : storedPlatforms) {
            List<Platform> platforms =
                    platformRepository.findByNameIgnoreCase(platform.getName());
            if (platforms.isEmpty()) {
                platform.setModelingToolSuggestions(List.of(suggestion));
                platformRepository.save(platform);
            } else if (platforms.size() == 1) {
                Platform repoPlatform = platforms.get(0);
                List<ModelingToolSuggestion> suggestions = repoPlatform.getModelingToolSuggestions();
                if (suggestions != null) {
                    suggestions.add(suggestion);
                    platform.setModelingToolSuggestions(suggestions);
                    platformRepository.save(platform);
                } else {
                    List<ModelingToolSuggestion> sList = new ArrayList<>();
                    sList.add(suggestion);
                    platform.setModelingToolSuggestions(sList);
                    platformRepository.save(platform);
                }
                // platformRepository.save(repoPlatform);
            } else {
                throw new FatalException("Too many platforms stored with the same name!");
            }
        }
    }

    private void assignNewlyStoredProgrammingLanguages(ModelingToolSuggestion suggestion, List<ProgrammingLanguage> storedProgrammingLanguages) {
        for (ProgrammingLanguage language : storedProgrammingLanguages) {
            List<ProgrammingLanguage> programmingLanguages =
                    programmingLanguagesRepository.findByNameIgnoreCase(language.getName());
            if (programmingLanguages.isEmpty()) {
                language.setModelingToolSuggestions(List.of(suggestion));
                programmingLanguagesRepository.save(language);
            } else if (programmingLanguages.size() == 1) {
                ProgrammingLanguage repoLanguage = programmingLanguages.get(0);
                List<ModelingToolSuggestion> suggestions = repoLanguage.getModelingToolSuggestions();
                if (suggestions != null) {
                    suggestions.add(suggestion);
                    language.setModelingToolSuggestions(suggestions);
                    programmingLanguagesRepository.save(language);
                } else {
                    List<ModelingToolSuggestion> sList = new ArrayList<>();
                    sList.add(suggestion);
                    language.setModelingToolSuggestions(sList);
                    programmingLanguagesRepository.save(language);
                }
                // programmingLanguagesRepository.save(repoLanguage);
            } else {
                throw new FatalException("Too many programming languages stored with the same name!");
            }
        }
    }
}
