package com.example.modeling_tools.integration_tests;

import com.example.modeling_tools.base_test.TestData;
import com.example.modeling_tools.endpoint.dto.ModelingToolSuggestionDto;
import com.example.modeling_tools.entity.ModelingTool;
import com.example.modeling_tools.entity.ModelingToolSuggestion;
import com.example.modeling_tools.entity.properties.ModelingLanguage;
import com.example.modeling_tools.entity.properties.Platform;
import com.example.modeling_tools.entity.properties.ProgrammingLanguage;
import com.example.modeling_tools.repository.ModelingToolRepository;
import com.example.modeling_tools.repository.ModelingToolSuggestionRepository;
import com.example.modeling_tools.repository.properties.ModelingLanguageRepository;
import com.example.modeling_tools.repository.properties.PlatformRepository;
import com.example.modeling_tools.repository.properties.ProgrammingLanguagesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@AutoConfigureMockMvc
public class ModelingToolEndpointTest implements TestData {
    enum Crud {
        confirm,
        delete
    }
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ModelingToolRepository repository;

    @Autowired
    ModelingToolSuggestionRepository suggestionRepository;

    @Autowired
    ModelingLanguageRepository modelingLanguageRepository;

    @Autowired
    PlatformRepository platformRepository;

    @Autowired
    ProgrammingLanguagesRepository programmingLanguagesRepository;

    @BeforeEach
    public void beforeEach() {
        emptyAllRepositories();
    }

    /**
     * Send Modeling Tool Suggestions as POST requests and tests the following criteria:
     * 1. Amount of modeling tool suggestions sent corresponds to stored modeling tool suggestions in the repositories
     * 2. Sent modeling tool suggestions correspond to suggestions within the repositories + corresponding properties
     *    are also stored
     * @throws Exception if the POST request is syntactically invalid
     */
    @Test
    public void postModelingToolSuggestion() throws Exception {
        for (ModelingToolSuggestionDto suggestion : TestData.suggestions) {
            makePostRequest(createModelingToolSuggestionJson(suggestion));
        }

        // 1. Amount of modeling tool suggestions sent corresponds to stored modeling tool suggestions in the repositories
        List<ModelingToolSuggestion> suggestionsRepo = suggestionRepository.findAll();
        assertThat(suggestionsRepo.size()).isEqualTo(10);

        // 2. Sent modeling tool suggestions correspond to suggestions within the repositories + including properties
        assertThatSuggestionsAndPropertiesAreStored(TestData.suggestions);

        emptyAllRepositories();
    }

    /**
     * Sending multiple POST requests containing Modeling Tool Suggestions, followed by GET requests attempting to
     * confirm the Modeling Tool Suggestion whereupon they are expected to be stored as Modeling Tools.
     * @throws Exception if the GET request is syntactically invalid
     */
    @Test
    public void confirmModelingToolSuggestion() throws Exception {
        // 1. Send POST requests containing Modeling Tool Suggestions
        for (ModelingToolSuggestionDto suggestion : TestData.suggestions) {
            makePostRequest(createModelingToolSuggestionJson(suggestion));
        }

        // 2. Send GET requests to confirm Modeling Tool Suggestions
        confirmAllSuggestions();

        // 3. Assert that confirmed modeling tools and corresponding properties are stored
        assertThat(repository.findAll().size()).isEqualTo(10);
        assertThatModelingToolsAndPropertiesAreStored(TestData.suggestions);

        emptyAllRepositories();
    }

    /**
     * Perform a GET request acting as a confirmation modeling tool suggestion, expected to fail and return a 401
     * Unauthorized response status.
     * @throws Exception if the GET request is syntactically invalid
     */
    @Test
    public void invalidConfirmModelingToolSuggestion() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/v1/modeling_tools/confirm?token=" + "abc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnauthorized()).andReturn().getResponse().getContentAsByteArray();
        assertThat(repository.findAll().size()).isEqualTo(0);
    }

    @Test
    public void putModelingToolSuggestion() throws Exception {
        // 1. Send POST request containing a Modeling Tool
        makePostRequest(createModelingToolSuggestionJson(TestData.MIN_INFO_CREATE));

        // 2. Send GET requests to confirm Modeling Tool Suggestions
        confirmAllSuggestions();
        assertThat(suggestionRepository.findAll().size()).isEqualTo(0);

        // 3. Send PUT request to edit a Modeling Tool
        Long id = repository.findAll().get(0).getId();
        makePutRequest(createModelingToolSuggestionJson(TestData.MAX_INFO_CREATE), id);
        assertThatSuggestionsAndPropertiesAreStored(List.of(TestData.MAX_INFO_CREATE));

        // 4. Send GET request to confirm Modeling Tool Edit suggestion
        confirmAllSuggestions();
        assertThatModelingToolsAndPropertiesAreStored(List.of(TestData.MAX_INFO_CREATE));

        emptyAllRepositories();
    }

    /**
     * Testing following scenarios:
     * 1. Sending a Modeling Tool Suggestion Edit without specifying the id within the PUT request throws
     *    an appropriate error exception.
     * 2. Sending a Modeling Tool Edit suggestion with precisely the same values, expecting an error exception.
     * 3. Sending a Modeling Tool Edit suggestion with precisely the same values but also containing feedback, expecting
     *    success.
     */
    @Test
    public void invalidPutSuggestion() throws Exception {
        // 1. Expecting 404 Not Found response when sending a PUT request with an ID which no modeling tool contains
        makeInvalidPutRequest(createModelingToolSuggestionJson(TestData.MIN_INFO_CREATE), 1L, status().isNotFound());

        // 2. Expecting 422 Unprocessable Content response when sending a PUT request with the exact same properties
        makePostRequest(createModelingToolSuggestionJson(TestData.MAX_INFO_CREATE));
        confirmAllSuggestions();

        ModelingTool toolInRepository = repository.findAll().get(0);
        makeInvalidPutRequest(createModelingToolSuggestionJson(TestData.MAX_INFO_CREATE), toolInRepository.getId(), status().isUnprocessableEntity());

        // 3. Expecting successfully processed PUT request
        TestData.MAX_INFO_CREATE.setFeedback("You could show some more pictures.");
        makePutRequest(createModelingToolSuggestionJson(TestData.MAX_INFO_CREATE), toolInRepository.getId());

        TestData.MAX_INFO_CREATE.setFeedback(null);
        emptyAllRepositories();
    }

    /**
     * Removing a Modeling Tool Suggestion from the persistent data store. Expecting the modeling tool suggestion and
     * unverified properties to be deleted.
     */
    @Test
    public void deleteModelingToolSuggestion() throws Exception {
        makePostRequest(createModelingToolSuggestionJson(TestData.MAX_INFO_CREATE));
        assertThat(suggestionRepository.findAll().size()).isGreaterThan(0);
        assertThat(modelingLanguageRepository.findAll().size()).isGreaterThan(0);
        assertThat(platformRepository.findAll().size()).isGreaterThan(0);
        assertThat(programmingLanguagesRepository.findAll().size()).isGreaterThan(0);

        String token = suggestionRepository.findAll().get(0).getAdminToken();
        makeConfirmOrDeleteRequest(token, Crud.delete);
        assertThat(suggestionRepository.findAll().size()).isEqualTo(0);
        assertThat(modelingLanguageRepository.findAll().size()).isEqualTo(0);
        assertThat(platformRepository.findAll().size()).isEqualTo(0);
        assertThat(programmingLanguagesRepository.findAll().size()).isEqualTo(0);
    }

    /**
     * Check whether a Modeling Tool Suggestion which contains property (Modeling Language, Platform, Programming Language)
     * duplicates does NOT get stores more than once within the persistent data store.
     */
    @Test
    public void duplicatePropertiesCheck() throws Exception {
        // 1. Test if modeling tools with property duplicated are filtered with a POST request
        makePostRequest(createModelingToolSuggestionJson(TestData.DUPLICATE_PROPERTIES));
        ModelingToolSuggestion storedSuggestion = suggestionRepository.findAll().get(0);
        List<ModelingLanguage> mlList = modelingLanguageRepository.findModelingLanguageByModelingToolSuggestionId(storedSuggestion.getId());
        List<Platform> platformList = platformRepository.findPlatformByModelingToolSuggestionId(storedSuggestion.getId());
        List<ProgrammingLanguage> plList = programmingLanguagesRepository.findProgrammingLanguageByModelingToolSuggestionId(storedSuggestion.getId());

        assertThat(storedSuggestion.getTechnology().size()).isEqualTo(1);
        assertThat(mlList.size()).isEqualTo(1);
        assertThat(storedSuggestion.getCreator().size()).isEqualTo(2);
        assertThat(platformList.size()).isEqualTo(1);
        assertThat(plList.size()).isEqualTo(1);

        // 2. Test if modeling tools with property duplicated are filtered with a PUT request
        confirmAllSuggestions();
        Long id = repository.findAll().get(0).getId();
        ModelingToolSuggestionDto suggestionEdit = TestData.DUPLICATE_PROPERTIES;
        suggestionEdit.setOpenSource(false);

        makePutRequest(createModelingToolSuggestionJson(suggestionEdit), id);

        storedSuggestion = suggestionRepository.findAll().get(0);
        mlList = modelingLanguageRepository.findModelingLanguageByModelingToolSuggestionId(storedSuggestion.getId());
        platformRepository.findPlatformByModelingToolSuggestionId(storedSuggestion.getId());
        plList = programmingLanguagesRepository.findProgrammingLanguageByModelingToolSuggestionId(storedSuggestion.getId());

        assertThat(storedSuggestion.getTechnology().size()).isEqualTo(1);
        assertThat(mlList.size()).isEqualTo(1);
        assertThat(storedSuggestion.getCreator().size()).isEqualTo(2);
        assertThat(platformList.size()).isEqualTo(1);
        assertThat(plList.size()).isEqualTo(1);

        TestData.DUPLICATE_PROPERTIES.setOpenSource(true);
        emptyAllRepositories();
    }

    /**
     * Precondition: Modeling Tool Suggestions recently stored through Http-requests are the very same that are expected
     *               within the persistent data store
     */
    private void assertThatSuggestionsAndPropertiesAreStored(List<ModelingToolSuggestionDto> suggestions) {
        List<ModelingToolSuggestion> suggestionsRepo = suggestionRepository.findAll();

        assertEquals(suggestions.size(), suggestionsRepo.size());
        assertThat(suggestionsRepo)
                .extracting(
                        ModelingToolSuggestion::getName,
                        ModelingToolSuggestion::getLink,
                        ModelingToolSuggestion::getOpenSource,
                        ModelingToolSuggestion::getWebApp,
                        ModelingToolSuggestion::getDesktopApp,
                        ModelingToolSuggestion::getCategory,
                        ModelingToolSuggestion::getSourceCodeGeneration,
                        ModelingToolSuggestion::getCloudService,
                        ModelingToolSuggestion::getLicense,
                        ModelingToolSuggestion::getLoginRequired,
                        ModelingToolSuggestion::getRealTimeCollab
                )
                .containsAll(
                        getSuggestionTupleList(suggestions)
                );

        for (int i = 0; i < suggestions.size() ; i++) {
            ModelingToolSuggestion suggestion = suggestionsRepo.get(i);

            // Test Modeling Languages
            List<ModelingLanguage> mlList = modelingLanguageRepository.findModelingLanguageByModelingToolSuggestionId(
                    suggestion.getId()
            );
            List<String> mlNames = mlList.stream().map(modelingLanguage -> modelingLanguage.getName().trim().toUpperCase()).toList();
            List<String> suggestionMl = suggestions.get(i).getModelingLanguagesOrEmptyList().stream().map(modelingLanguage -> modelingLanguage.trim().toUpperCase()).toList();
            assertTrue(mlNames.containsAll(suggestionMl));
            assertThat(mlList.size()).isEqualTo(suggestions.get(i).getModelingLanguagesOrEmptyList().size());

            // Test Platforms
            List<Platform> platformList = platformRepository.findPlatformByModelingToolSuggestionId(
                    suggestion.getId()
            );
            List<String> platformNames = platformList.stream().map(platform -> platform.getName().trim().toUpperCase()).toList();
            List<String> suggestionPlatform = suggestions.get(i).getPlatformOrEmptyList().stream().map(platform -> platform.trim().toUpperCase()).toList();
            assertTrue(platformNames.containsAll(suggestionPlatform));
            assertThat(platformList.size()).isEqualTo(suggestions.get(i).getPlatformOrEmptyList().size());

            // Test Programming Languages
            List<ProgrammingLanguage> plList = programmingLanguagesRepository.findProgrammingLanguageByModelingToolSuggestionId(
                    suggestion.getId()
            );
            List<String> plNames = plList.stream().map(programmingLanguage -> programmingLanguage.getName().trim().toUpperCase()).toList();
            List<String> suggestionProgrammingLanguages = suggestions.get(i).getProgrammingLanguageOrEmptyList().stream().map(programmingLanguage -> programmingLanguage.trim().toUpperCase()).toList();
            assertTrue(plNames.containsAll(suggestionProgrammingLanguages));
            assertThat(plList.size()).isEqualTo(suggestions.get(i).getProgrammingLanguageOrEmptyList().size());
        }
    }

    /**
     * Precondition: Modeling Tools recently confirmed and stored through Http-requests are the very same that are expected
     *               within the persistent data store
     */
    private void assertThatModelingToolsAndPropertiesAreStored(List<ModelingToolSuggestionDto> suggestions) {
        List<ModelingTool> modelingToolRepo = repository.findAll();

        System.out.println();
        System.out.println("REPO SIZE CHECK");
        for (ModelingTool mt : modelingToolRepo) {
            System.out.println(mt.getName());
        }
        System.out.println();

        // 1. Assert that all Modeling Tools that have been initially suggested are now also stored as Modeling Tools
        assertEquals(suggestions.size(), modelingToolRepo.size());
        assertThat(modelingToolRepo)
                .extracting(
                        ModelingTool::getName,
                        ModelingTool::getLink,
                        ModelingTool::getOpenSource,
                        ModelingTool::getWebApp,
                        ModelingTool::getDesktopApp,
                        ModelingTool::getCategory,
                        ModelingTool::getSourceCodeGeneration,
                        ModelingTool::getCloudService,
                        ModelingTool::getLicense,
                        ModelingTool::getLoginRequired,
                        ModelingTool::getRealTimeCollab
                )
                .containsAll(
                        getSuggestionTupleList(suggestions)
                );

        // 2. Assert that all properties contained within the suggestions are now stored as verified properties
        for (int i = 0; i < suggestions.size() ; i++) {
            ModelingTool tool = modelingToolRepo.get(i);

            // Test Modeling Languages
            List<ModelingLanguage> mlList = modelingLanguageRepository.findModelingLanguageByModelingToolId(
                    tool.getId()
            );
            List<String> mlNames = mlList.stream().map(modelingLanguage -> modelingLanguage.getName().trim().toUpperCase()).toList();
            List<String> suggestionMl = suggestions.get(i).getModelingLanguagesOrEmptyList().stream().map(modelingLanguage -> modelingLanguage.trim().toUpperCase()).toList();
            assertTrue(mlNames.containsAll(suggestionMl));

            // Test Platforms
            List<Platform> platformList = platformRepository.findPlatformByModelingToolId(
                    tool.getId()
            );
            List<String> platformNames = platformList.stream().map(platform -> platform.getName().trim().toUpperCase()).toList();
            List<String> suggestionPlatform = suggestions.get(i).getPlatformOrEmptyList().stream().map(platform -> platform.trim().toUpperCase()).toList();
            assertTrue(platformNames.containsAll(suggestionPlatform));

            // Test Programming Languages
            List<ProgrammingLanguage> plList = programmingLanguagesRepository.findProgrammingLanguageByModelingToolId(
                    tool.getId()
            );
            List<String> plNames = plList.stream().map(programmingLanguage -> programmingLanguage.getName().trim().toUpperCase()).toList();
            List<String> suggestionProgrammingLanguages = suggestions.get(i).getProgrammingLanguageOrEmptyList().stream().map(programmingLanguage -> programmingLanguage.trim().toUpperCase()).toList();
            assertTrue(plNames.containsAll(suggestionProgrammingLanguages));
        }
    }

    private void confirmAllSuggestions() throws Exception {
        List<ModelingToolSuggestion> repoSuggestions =  suggestionRepository.findAll();
        for (ModelingToolSuggestion suggestion : repoSuggestions) {
            makeConfirmOrDeleteRequest(suggestion.getAdminToken(), Crud.confirm);
        }
    }

    private List<Tuple> getSuggestionTupleList(List<ModelingToolSuggestionDto> suggestions) {
        List<Tuple> tuples = new ArrayList<>();
        // Ignoring ID's as generated ID's by the database do not reset
        for (ModelingToolSuggestionDto suggestion : suggestions) {
            tuples.add(
                    tuple(
                            suggestion.getName(),
                            suggestion.getLink(),
                            suggestion.getOpenSource(),
                            suggestion.getWebApp(),
                            suggestion.getDesktopApp(),
                            suggestion.getCategory(),
                            suggestion.getSourceCodeGeneration(),
                            suggestion.getCloudService(),
                            suggestion.getLicense(),
                            suggestion.getLoginRequired(),
                            suggestion.getRealTimeCollab()
                    )
            );
        }
        return tuples;
    }

    private void makePostRequest(String json) throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/modeling_tools?ignoreMail=true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated()).andReturn().getResponse().getContentAsByteArray();
    }

    private void makePutRequest(String json, Long id) throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/api/v1/modeling_tools/" + id + "?ignoreMail=true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn().getResponse().getContentAsByteArray();
    }

    private void makeInvalidPutRequest(String json, Long id, ResultMatcher status) throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/api/v1/modeling_tools/" + id + "?ignoreMail=true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status).andReturn().getResponse().getContentAsByteArray();
    }

    private void makeConfirmOrDeleteRequest(String token, Crud request) throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/v1/modeling_tools/" + request + "?token=" + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(request.equals(Crud.confirm) ? status().isCreated() : status().isOk()).andReturn().getResponse().getContentAsByteArray();
    }

    private void emptyAllRepositories() {
        // Modeling Languages
        List<ModelingLanguage> languages = modelingLanguageRepository.findAll();
        for (ModelingLanguage language : languages) {
            language.setModelingTools(null);
            language.setModelingToolSuggestions(null);
            modelingLanguageRepository.save(language);
        }

        // Platforms
        List<Platform> platforms = platformRepository.findAll();
        for (Platform platform : platforms) {
            platform.setModelingTools(null);
            platform.setModelingToolSuggestions(null);
            platformRepository.save(platform);
        }

        // Programming Languages
        List<ProgrammingLanguage> pLanguages = programmingLanguagesRepository.findAll();
        for (ProgrammingLanguage language : pLanguages) {
            language.setModelingTools(null);
            language.setModelingToolSuggestions(null);
            programmingLanguagesRepository.save(language);
        }

        // Modeling Tools
        List<ModelingTool> modelingTools = repository.findAll();
        for (ModelingTool tool : modelingTools) {
            tool.setModelingLanguages(null);
            tool.setPlatform(null);
            tool.setProgrammingLanguage(null);
            repository.save(tool);
        }

        List<ModelingToolSuggestion> suggestions = suggestionRepository.findAll();
        for (ModelingToolSuggestion suggestion : suggestions) {
            suggestion.setModelingLanguages(null);
            suggestion.setPlatform(null);
            suggestion.setProgrammingLanguage(null);
            suggestionRepository.save(suggestion);
        }

        modelingLanguageRepository.deleteAll();
        platformRepository.deleteAll();
        programmingLanguagesRepository.deleteAll();
        repository.deleteAll();
        suggestionRepository.deleteAll();
    }

    private String createModelingToolSuggestionJson(ModelingToolSuggestionDto suggestion) {
        return String.format(
                """
                {
                    "userEmail": %s,
                    "name": %s,
                    "link": %s,
                    "openSource": %s,
                    "technology": %s,
                    "webApp": %s,
                    "desktopApp": %s,
                    "category": %s,
                    "modelingLanguages": %s,
                    "sourceCodeGeneration": %s,
                    "cloudService": %s,
                    "license": %s,
                    "loginRequired": %s,
                    "creator": %s,
                    "platform": %s,
                    "realTimeCollab": %s,
                    "programmingLanguage": %s,
                    "feedback": %s
                }
                """,
                constructJsonEntry(suggestion.getUserEmail()),
                constructJsonEntry(suggestion.getName()),
                constructJsonEntry(suggestion.getLink()),
                constructJsonEntry(suggestion.getOpenSource()),
                constructJsonEntry(suggestion.getTechnology()),
                constructJsonEntry(suggestion.getWebApp()),
                constructJsonEntry(suggestion.getDesktopApp()),
                constructJsonEntry(suggestion.getCategoryString()),
                constructJsonEntry(suggestion.getModelingLanguages()),
                constructJsonEntry(suggestion.getSourceCodeGeneration()),
                constructJsonEntry(suggestion.getCloudService()),
                constructJsonEntry(suggestion.getLicenseString()),
                constructJsonEntry(suggestion.getLoginRequired()),
                constructJsonEntry(suggestion.getCreator()),
                constructJsonEntry(suggestion.getPlatform()),
                constructJsonEntry(suggestion.getRealTimeCollab()),
                constructJsonEntry(suggestion.getProgrammingLanguage()),
                constructJsonEntry(suggestion.getFeedback())
        );
    }

    private String constructJsonEntry(String entry) {
        return entry == null ? "null" : "\"" + entry + "\"";
    }

    private String constructJsonEntry(Boolean entry) {
        return entry == null ? "null" : "\"" + entry + "\"";
    }

    private String constructJsonEntry(List<?> list) {
        if (list == null || list.isEmpty()) {
            return "null";
        }
        String result = "[";
        for (Object entry : list) {
            result += ("\"" + entry.toString() + "\", ");
        }
        return result.substring(0, result.length() - 2) + "]";
    }
}
