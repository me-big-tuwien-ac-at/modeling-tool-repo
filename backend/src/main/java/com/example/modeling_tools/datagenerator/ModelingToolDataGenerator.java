package com.example.modeling_tools.datagenerator;

import com.example.modeling_tools.entity.ModelingTool;
import com.example.modeling_tools.entity.properties.ModelingLanguage;
import com.example.modeling_tools.entity.properties.Platform;
import com.example.modeling_tools.entity.properties.ProgrammingLanguage;
import com.example.modeling_tools.exception.FatalException;
import com.example.modeling_tools.repository.ModelingToolRepository;
import com.example.modeling_tools.repository.properties.ModelingLanguageRepository;
import com.example.modeling_tools.repository.properties.PlatformRepository;
import com.example.modeling_tools.repository.properties.ProgrammingLanguagesRepository;
import com.example.modeling_tools.type.Category;
import com.example.modeling_tools.type.License;
import com.example.modeling_tools.type.Technology;
import jakarta.annotation.PostConstruct;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class ModelingToolDataGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private ModelingToolRepository repository;

    @Autowired
    private ModelingLanguageRepository modelingLanguageRepository;

    @Autowired
    private PlatformRepository platformRepository;

    @Autowired
    ProgrammingLanguagesRepository programmingLanguagesRepository;

    @PostConstruct
    private void initializeData() throws IOException {
        addModelingLanguages();
        addPlatforms();
        addProgrammingLanguages();
        readModelingToolsFromJson();
    }

    private void addModelingLanguages() {
        LOGGER.info("Generating Modeling Languages");
        if (modelingLanguageRepository.findAll().isEmpty()) {
            ModelingLanguage bpmn = new ModelingLanguage("BPMN", false);
            ModelingLanguage circuitDiagram = new ModelingLanguage("Circuit Diagrams", false);
            ModelingLanguage er = new ModelingLanguage("ER", false);
            ModelingLanguage flowchart = new ModelingLanguage("Flowchart", false);
            ModelingLanguage sequenceDiagram = new ModelingLanguage("Sequence Diagram", false);
            ModelingLanguage sysml = new ModelingLanguage("SysML", false);
            ModelingLanguage uml = new ModelingLanguage("UML", false);
            modelingLanguageRepository.save(bpmn);
            modelingLanguageRepository.save(circuitDiagram);
            modelingLanguageRepository.save(er);
            modelingLanguageRepository.save(flowchart);
            modelingLanguageRepository.save(sequenceDiagram);
            modelingLanguageRepository.save(sysml);
            modelingLanguageRepository.save(uml);
        }
    }

    private void addPlatforms() {
        LOGGER.info("Generating Platforms");
        if (platformRepository.findAll().isEmpty()) {
            Platform chrome = new Platform("Chrome", false);
            Platform edge = new Platform("Edge", false);
            Platform firefox = new Platform("Firefox", false);
            Platform linux = new Platform("Linux", false);
            Platform macos = new Platform("macOS", false);
            Platform solaris = new Platform("Solaris", false);
            Platform unix = new Platform("Unix", false);
            Platform windows = new Platform("Windows", false);
            platformRepository.save(chrome);
            platformRepository.save(edge);
            platformRepository.save(firefox);
            platformRepository.save(linux);
            platformRepository.save(macos);
            platformRepository.save(solaris);
            platformRepository.save(unix);
            platformRepository.save(windows);
        }
    }

    private void addProgrammingLanguages() {
        LOGGER.info("Generating Programming Languages");
        if (programmingLanguagesRepository.findAll().isEmpty()) {
            ProgrammingLanguage angular = new ProgrammingLanguage("Angular", false);
            ProgrammingLanguage c = new ProgrammingLanguage("C", false);
            ProgrammingLanguage cs = new ProgrammingLanguage("C#", false);
            ProgrammingLanguage css = new ProgrammingLanguage("CSS", false);
            ProgrammingLanguage cpp = new ProgrammingLanguage("C++", false);
            ProgrammingLanguage go = new ProgrammingLanguage("Go", false);
            ProgrammingLanguage gwt = new ProgrammingLanguage("GWT", false);
            ProgrammingLanguage j2ee = new ProgrammingLanguage("J2EE", false);
            ProgrammingLanguage java = new ProgrammingLanguage("Java", false);
            ProgrammingLanguage javascript = new ProgrammingLanguage("JavaScript", false);
            ProgrammingLanguage kotlin = new ProgrammingLanguage("Kotlin", false);
            ProgrammingLanguage matlab = new ProgrammingLanguage("MATLAB", false);
            ProgrammingLanguage perl = new ProgrammingLanguage("Perl", false);
            ProgrammingLanguage php = new ProgrammingLanguage("PHP", false);
            ProgrammingLanguage python = new ProgrammingLanguage("Python", false);
            ProgrammingLanguage ruby = new ProgrammingLanguage("Ruby", false);
            ProgrammingLanguage rust = new ProgrammingLanguage("Rust", false);
            ProgrammingLanguage scala = new ProgrammingLanguage("Scala", false);
            ProgrammingLanguage smalltalk = new ProgrammingLanguage("Smalltalk", false);
            ProgrammingLanguage typescript = new ProgrammingLanguage("TypeScript", false);
            ProgrammingLanguage html = new ProgrammingLanguage("HTML", false);
            ProgrammingLanguage powershell = new ProgrammingLanguage("PowerShell", false);
            ProgrammingLanguage gap = new ProgrammingLanguage("GAP", false);
            ProgrammingLanguage xtend = new ProgrammingLanguage("Xtend", false);
            ProgrammingLanguage vba = new ProgrammingLanguage("VBA", false);
            ProgrammingLanguage vue = new ProgrammingLanguage("Vue", false);
            ProgrammingLanguage yacc = new ProgrammingLanguage("Yacc", false);
            programmingLanguagesRepository.save(angular);
            programmingLanguagesRepository.save(c);
            programmingLanguagesRepository.save(cs);
            programmingLanguagesRepository.save(css);
            programmingLanguagesRepository.save(cpp);
            programmingLanguagesRepository.save(go);
            programmingLanguagesRepository.save(gwt);
            programmingLanguagesRepository.save(j2ee);
            programmingLanguagesRepository.save(java);
            programmingLanguagesRepository.save(javascript);
            programmingLanguagesRepository.save(kotlin);
            programmingLanguagesRepository.save(matlab);
            programmingLanguagesRepository.save(perl);
            programmingLanguagesRepository.save(php);
            programmingLanguagesRepository.save(python);
            programmingLanguagesRepository.save(ruby);
            programmingLanguagesRepository.save(rust);
            programmingLanguagesRepository.save(scala);
            programmingLanguagesRepository.save(smalltalk);
            programmingLanguagesRepository.save(typescript);
            programmingLanguagesRepository.save(html);
            programmingLanguagesRepository.save(powershell);
            programmingLanguagesRepository.save(gap);
            programmingLanguagesRepository.save(xtend);
            programmingLanguagesRepository.save(vba);
            programmingLanguagesRepository.save(vue);
            programmingLanguagesRepository.save(yacc);
        }
    }

    private void readModelingToolsFromJson() throws IOException {
        LOGGER.info("Reading Modeling Tool JSON collection");
        if (repository.findAll().isEmpty()) {
            readJsonCollection();
        }
    }

    public void readJsonCollection() throws IOException {
        LOGGER.info("Creating and storing modeling Tools");
        String data = new String(Files.readAllBytes(Paths.get("src/main/resources/web-modeling-tools.json")));
        JSONArray jsonArray = new JSONArray(data);


        for (int i = 0; i < jsonArray.length(); i++) {
            String str = jsonArray.get(i).toString();
            JSONObject object = new JSONObject(str);

            String name = getJsonString(object, "name");
            String link = getJsonString(object, "link");
            Boolean openSource = getJsonBoolean(object, "openSource");
            List<Technology> technology = getTechnologyList(object, "technology");
            Boolean webApp = getJsonBoolean(object, "webApp");
            Boolean desktopApp = getJsonBoolean(object, "desktopApp");
            Category category = getCategoryByJsonString(object, "category");
            List<ModelingLanguage> modelingLanguages = getModelingLanguages(getJsonArray(object, "modelingLanguages"));
            Boolean sourceCodeGeneration = getJsonBoolean(object, "sourceCodeGeneration");
            Boolean cloudService = getJsonBoolean(object, "cloudService");
            License license = getLicenseByJsonString(object, "license");
            Boolean loginRequired = getJsonBoolean(object, "loginRequired");
            List<String> creator = getJsonArray(object, "creator");
            List<Platform> platform = getPlatforms(getJsonArray(object, "platform"));
            Boolean realTimeCollab = getJsonBoolean(object, "realTimeCollab");
            List<ProgrammingLanguage> programmingLanguages = getProgrammingLanguages(getJsonArray(object, "programmingLanguages"));

            repository.save(
                    new ModelingTool(
                            name,
                            link,
                            openSource,
                            technology,
                            webApp,
                            desktopApp,
                            category,
                            modelingLanguages,
                            sourceCodeGeneration,
                            cloudService,
                            license,
                            loginRequired,
                            creator,
                            platform,
                            realTimeCollab,
                            programmingLanguages
                    )
            );

            // Assign to each modeling tool, platform and programming language the modeling tools they are referencing
            for (ModelingLanguage language : modelingLanguages) {
                List<ModelingTool> plTools = modelingLanguageRepository.findModelingToolByModelingLanguageId(language.getId());
                language.setModelingTools(plTools);
                modelingLanguageRepository.save(language);
            }
            for (Platform p : platform) {
                List<ModelingTool> plTools = platformRepository.findModelingToolByPlatformId(p.getId());
                p.setModelingTools(plTools);
                platformRepository.save(p);
            }
            for (ProgrammingLanguage language : programmingLanguages) {
                List<ModelingTool> plTools = repository.findModelingToolByProgrammingLanguageId(language.getId());
                language.setModelingTools(plTools);
                programmingLanguagesRepository.save(language);
            }
        }
    }

    public String getJsonString(JSONObject object, String key) {
        return object.isNull(key) ? null : object.getString(key);
    }

    public Category getCategoryByJsonString(JSONObject object, String key) {
        return object.isNull(key) ? null : Category.valueOf(object.getString(key));
    }

    public License getLicenseByJsonString(JSONObject object, String key) {
        return object.isNull(key) ? null : License.valueOf(object.getString(key));
    }

    public Boolean getJsonBoolean(JSONObject object, String key) {
        return object.isNull(key) ? null : object.getBoolean(key);
    }

    public List<String> getJsonArray(JSONObject object, String key) {
        JSONArray jsonArray;
        if (object == null || key == null || (jsonArray = object.optJSONArray(key)) == null) {
            return new ArrayList<>();
        }
        List<String> result = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            result.add((String) jsonArray.get(i));
        }
        return result;
    }

    public List<Technology> getTechnologyList(JSONObject object, String key) {
        if (object == null || key == null) {
            return new ArrayList<>();
        }
        JSONArray jsonArray = object.getJSONArray(key);
        ArrayList<Technology> result = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            result.add(Technology.valueOf(((String) jsonArray.get(i)).toUpperCase()));
        }
        return result;
    }

    public List<ModelingLanguage> getModelingLanguages(List<String> modelingLanguageStrings) {
        List<ModelingLanguage> modelingLanguages = new ArrayList<>();

        for (String languageString : modelingLanguageStrings) {
            List<ModelingLanguage> languages = modelingLanguageRepository.findByNameIgnoreCase(languageString);
            if (languages.isEmpty()) {
                ModelingLanguage language = modelingLanguageRepository.save(new ModelingLanguage(languageString, true));
                modelingLanguages.add(language);
            } else if (languages.size() == 1) {
                modelingLanguages.add(languages.get(0));
            } else {
                throw new FatalException("More than one modeling language with the same name stored in the persistent data store");
            }
        }
        return modelingLanguages;
    }

    public List<Platform> getPlatforms(List<String> platformStrings) {
        List<Platform> platforms = new ArrayList<>();

        for (String platformString : platformStrings) {
            List<Platform> platformList = platformRepository.findByNameIgnoreCase(platformString);
            if (platformList.isEmpty()) {
                Platform language = platformRepository.save(new Platform(platformString, true));
                platforms.add(language);
            } else if (platformList.size() == 1) {
                platforms.add(platformList.get(0));
            } else {
                throw new FatalException("More than one platform with the same name stored in the persistent data store");
            }
        }
        return platforms;
    }

    public List<ProgrammingLanguage> getProgrammingLanguages(List<String> programmingLanguageStrings) {
        List<ProgrammingLanguage> programmingLanguages = new ArrayList<>();

        for (String languageString : programmingLanguageStrings) {
            List<ProgrammingLanguage> languages = programmingLanguagesRepository.findByNameIgnoreCase(languageString);
            if (languages.isEmpty()) {
                ProgrammingLanguage language = programmingLanguagesRepository.save(new ProgrammingLanguage(languageString, true));
                programmingLanguages.add(language);
            } else if (languages.size() == 1) {
                programmingLanguages.add(languages.get(0));
            } else {
                throw new FatalException("More than one programming language with the same name stored in the persistent data store");
            }
        }
        return programmingLanguages;
    }
}
