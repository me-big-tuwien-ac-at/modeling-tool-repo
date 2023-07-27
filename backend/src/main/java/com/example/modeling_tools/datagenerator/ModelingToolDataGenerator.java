package com.example.modeling_tools.datagenerator;

import com.example.modeling_tools.entity.ModelingToolVerified;
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
    private void initializeData() {
        addModelingLanguages();
        addPlatforms();
        addProgrammingLanguages();
        // readModelingToolsFromJson();
        createModelingTools();
    }

    private void addModelingLanguages() {
        LOGGER.info("Generating Modeling Languages");
        if (modelingLanguageRepository.findAll().isEmpty()) {
            ModelingLanguage archiMate = new ModelingLanguage("ArchiMate", false);
            ModelingLanguage bpmn = new ModelingLanguage("BPMN", false);
            ModelingLanguage circuitDiagram = new ModelingLanguage("Circuit Diagrams", false);
            ModelingLanguage er = new ModelingLanguage("ER", false);
            ModelingLanguage flowchart = new ModelingLanguage("Flowchart", false);
            ModelingLanguage sequenceDiagram = new ModelingLanguage("Sequence Diagram", false);
            ModelingLanguage sysml = new ModelingLanguage("SysML", false);
            ModelingLanguage uml = new ModelingLanguage("UML", false);
            modelingLanguageRepository.save(archiMate);
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

    public void createModelingTools() {
        LOGGER.info("Creating and storing modeling Tools");
        if (!repository.findAll().isEmpty()) {
            LOGGER.info("Modeling Tools already stored");
            return;
        }

        List<ModelingToolVerified> tools = new ArrayList<>();
        tools.add(
                new ModelingToolVerified("Adonis", "https://www.boc-group.com/en/", false, List.of(Technology.APP),
                        true, true, Category.GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("BPMN")), false,
                        true, License.RESTRICTED_FREE_AND_COMMERCIAL, true, List.of("BOC Group"),
                        getPlatforms(List.of("Windows")), false, null)
        );
        tools.add(
                new ModelingToolVerified("Apache OpenOffice Draw", "https://www.openoffice.org/product/draw.html", true, List.of(Technology.APP),
                        true, true, Category.DRAWING_TOOL, getModelingLanguages(List.of("UML")), false,
                        false, License.FREE, true, List.of("Sun Microsystems", "Oracle Corporation"),
                        getPlatforms(List.of("Windows")), false, getProgrammingLanguages(List.of("C++", "Java")))
                );
        tools.add(
                new ModelingToolVerified("Archi", "https://www.archimatetool.com/", true, List.of(Technology.APP),
                        false, true, Category.METAMODELING_TOOL, getModelingLanguages(List.of("ArchiMate")), false,
                        false, License.FREE, false, List.of("Phil Beauvoir", "Jean-Baptiste Sarrodie"),
                        getPlatforms(List.of("Windows", "macOS", "Linux")), false, getProgrammingLanguages(List.of("Java", "HTML", "JavaScript", "CSS")))
        );
        tools.add(
                new ModelingToolVerified("ARGOuml", "https://argouml-tigris-org.github.io/", true, List.of(Technology.APP),
                        false, true, Category.GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("UML")), true,
                        false,  License.FREE, null, List.of("Volunteer developers"),
                        null, false, null)
        );
        tools.add(
                new ModelingToolVerified("Astah", "https://astah.net/", false, List.of(Technology.APP),
                        false, true, Category.GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("UML", "ER")), false,
                        true, License.RESTRICTED_FREE_AND_COMMERCIAL, true, List.of("Change Vision"),
                        null, null, getProgrammingLanguages(List.of("Java")))
        );
        tools.add(
                new ModelingToolVerified("bigER Modeling Tool", "https://marketplace.visualstudio.com/items?itemName=BIGModelingTools.erdiagram", true, List.of(Technology.FRAMEWORK),
                        false, false, Category.MIXED_TEXTUAL_AND_GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("ER")), true,
                        true, License.FREE, false, List.of("Volunteer developers"),
                        null, false, getProgrammingLanguages(List.of("Java", "JavaScript", "TypeScript")))
        );
        tools.add(
                new ModelingToolVerified("bigUML Modeling Tool", "https://marketplace.visualstudio.com/items?itemName=BIGModelingTools.umldiagram", true, List.of(Technology.FRAMEWORK),
                        false, false, Category.MIXED_TEXTUAL_AND_GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("UML")), true,
                        true, License.FREE, false, List.of("Volunteer developers"),
                        null, false, getProgrammingLanguages(List.of("Java", "TypeScript", "CSS", "JavaScript")))
        );
        tools.add(
                new ModelingToolVerified("BPMN.io", "https://bpmn.io", true, List.of(Technology.APP),
                        true, true, Category.GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("BPMN")), false,
                        true, License.FREE, false, null,
                        null, false, getProgrammingLanguages(List.of("JavaScript")))
        );
        tools.add(
                new ModelingToolVerified("Cacoo", "https://nulab.com/cacoo/", false, List.of(Technology.APP),
                        true, false, Category.BUSINESS_TOOL, getModelingLanguages(List.of("BPMN", "Flowchart")), false,
                        true, License.RESTRICTED_FREE_AND_COMMERCIAL, true, List.of("Nulab Inc."),
                        null, true, getProgrammingLanguages(List.of("HTML")))
        );
        tools.add(
                new ModelingToolVerified("Camunda BPM", "https://camunda.com/", true, List.of(Technology.APP),
                        true, true, Category.MIXED_TEXTUAL_AND_GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("BPMN")), false,
                        true, License.RESTRICTED_FREE_AND_COMMERCIAL, true, List.of("Camunda BPM"),
                        getPlatforms(List.of("LINUX", "MACOS", "WINDOWS")), true, getProgrammingLanguages(List.of("Java")))
        );
        tools.add(
                new ModelingToolVerified("Chartmage", "https://chartmage.com/intro.html", true, List.of(Technology.APP),
                        true, false, Category.TEXT_BASED_MODELING_TOOL, getModelingLanguages(List.of("Sequence Diagram")), false,
                        true, License.FREE, false, List.of("Junlin Shang"),
                        null, false, getProgrammingLanguages(List.of("JAVASCRIPT", "HTML", "CSS")))
        );
        tools.add(
                new ModelingToolVerified("Circuit Diagram", "https://www.circuit-diagram.org/editor/", true, List.of(Technology.APP),
                        true, true, Category.GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("Circuit Diagrams")), false,
                        false, License.FREE, false, null,
                        null, false, getProgrammingLanguages(List.of("C#", "POWERSHELL", "TYPESCRIPT")))
        );
        tools.add(
                new ModelingToolVerified("ConceptDraw Diagram", "https://www.conceptdraw.com/products/drawing-tool", false, List.of(Technology.APP),
                        false, true, Category.DRAWING_TOOL, getModelingLanguages(List.of("UML", "BPMN")), false,
                        false, License.COMMERCIAL, true, List.of("CS Odessa"),
                        null, false, null)
        );
        tools.add(
                new ModelingToolVerified("Creately", "https://creately.com/", false, List.of(Technology.APP),
                        true, false, Category.BUSINESS_TOOL, getModelingLanguages(List.of("UML", "BPMN", "ER")), false,
                        true, License.RESTRICTED_FREE_AND_COMMERCIAL, true, null,
                        null, false, null)
        );
        tools.add(
                new ModelingToolVerified("dbdiagram.io", "https://dbdiagram.io/home", false, List.of(Technology.APP),
                        true, false, Category.MIXED_TEXTUAL_AND_GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("ER")), true,
                        true, License.RESTRICTED_FREE_AND_COMMERCIAL, false, null,
                        null, true, null)
        );
        tools.add(
                new ModelingToolVerified("Dia", "https://nulab.com/cacoo/", false, List.of(Technology.APP),
                        true, false, Category.GRAPHICAL_MODELING_TOOL, null, false,
                        true, License.FREE, true, List.of("Bartłomiej Piotrowski (git author)"),
                        null, false, null)
        );
        tools.add(
                new ModelingToolVerified("Diagram Designer", "https://logicnet.dk/DiagramDesigner/", true, List.of(Technology.APP),
                        false, true, Category.DRAWING_TOOL, getModelingLanguages(List.of("UML")), false,
                        false, License.FREE, false, List.of("Volunteer developers"),
                        null, false, null)
        );
        tools.add(
                new ModelingToolVerified("Diagramo", "http://diagramo.com/", true, List.of(Technology.APP),
                        true, true, Category.DRAWING_TOOL, null, false,
                        false, License.FREE, false, null,
                        null, false, getProgrammingLanguages(List.of("HTML", "JavaScript")))
        );
        tools.add(
                new ModelingToolVerified("Diagrams.net", "https://www.diagrams.net/", true, List.of(Technology.APP),
                        true, true, Category.DRAWING_TOOL, getModelingLanguages(List.of("UML", "BPMN", "ER")), false,
                        true, License.FREE, false, null,
                        null, false, getProgrammingLanguages(List.of("Java", "JavaScript")))
        );
        tools.add(
                new ModelingToolVerified("DotUML", "https://dotuml.com/", null, List.of(Technology.APP),
                        true, false, Category.GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("UML")), false,
                        true, License.FREE, false, null,
                        null, false, getProgrammingLanguages(List.of("JavaScript")))
        );
        tools.add(
                new ModelingToolVerified("Eclipse GLSP", "https://www.eclipse.org/glsp/", true, List.of(Technology.APP),
                        false, false, Category.METAMODELING_TOOL, null, false,
                        true, License.FREE, false, List.of("Eclipse"),
                        null, false, null)
        );
        tools.add(
                new ModelingToolVerified("Eclipse Papyrus", "https://www.eclipse.org/papyrus/", true, List.of(Technology.FRAMEWORK),
                        false, true, Category.GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("UML", "BPMN")), false,
                        true, License.FREE, false, List.of("Commissariat à l'Énergie Atomique, Atos Origin"),
                        null, false, getProgrammingLanguages(List.of("Java")))
        );
        tools.add(
                new ModelingToolVerified("Eclipse Modeling Framework", "https://www.eclipse.org/modeling/emf/", true, List.of(Technology.FRAMEWORK),
                        false, false, Category.GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("UML", "BPMN")), false,
                        true, License.FREE, false, List.of("Eclipse Foundation"),
                        null, false, null)
        );
        tools.add(
                new ModelingToolVerified("Edraw Max", "https://www.edrawsoft.com/de/", false, List.of(Technology.APP),
                        true, true, Category.BUSINESS_TOOL, getModelingLanguages(List.of("UML", "BPMN", "ER")), false,
                        true, License.RESTRICTED_FREE_AND_COMMERCIAL, true, List.of("EdrawSoft"),
                        null, false, null)
        );
        tools.add(
                new ModelingToolVerified("ER/Studio", "https://go.idera.com/data-architecture-for-applications/?utm_source=guru99&utm_medium=Online%20Display&utm_campaign=trial-download&utm_content=ers&utm_term=q1y23", false, List.of(Technology.APP),
                        false, true, Category.TEXT_BASED_MODELING_TOOL, getModelingLanguages(List.of("BPMN", "ER")), false,
                        true, License.RESTRICTED_FREE_AND_COMMERCIAL, true, List.of("Idera"),
                        null, false, null)
        );
        tools.add(
                new ModelingToolVerified("FXDiagram", "https://jankoehnlein.github.io/FXDiagram/", true, List.of(Technology.FRAMEWORK),
                        false, false, Category.TEXT_BASED_MODELING_TOOL, getModelingLanguages(List.of("UML", "BPMN", "ER")), false,
                        true, License.RESTRICTED_FREE_AND_COMMERCIAL, true, null,
                        null, false, getProgrammingLanguages(List.of("J2EE", "GWT", "HTML")))
        );
        tools.add(
                new ModelingToolVerified("GenMyModel", "https://www.genmymodel.com/", false, List.of(Technology.APP),
                        true, false, Category.GRAPHICAL_MODELING_TOOL, null, false,
                        null, License.FREE, false, List.of("Jan Koehnlein"),
                        null, false, getProgrammingLanguages(List.of("Java", "Xtend")))
        );
        tools.add(
                new ModelingToolVerified("GitMind", "https://gitmind.com/", false, List.of(Technology.APP),
                        true, true, Category.DRAWING_TOOL, null, false,
                        true, License.RESTRICTED_FREE_AND_COMMERCIAL, false, List.of("Wangxu Technology Co., Ltd"),
                        null, true, null)
        );
        tools.add(
                new ModelingToolVerified("Gliffy", "https://www.gliffy.com/", false, List.of(Technology.APP),
                        true, false, Category.BUSINESS_TOOL, getModelingLanguages(List.of("UML", "BPMN")), false,
                        true, License.COMMERCIAL, true, List.of("Chris Kohlhardt", "Clint Dickson"),
                        null, true, null)
        );
        tools.add(
                new ModelingToolVerified("GoJS", "https://gojs.net/latest/index.html", true, List.of(Technology.FRAMEWORK),
                        false, false, Category.MIXED_TEXTUAL_AND_GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("UML", "BPMN", "ER", "Flowchart")), false,
                        true, License.RESTRICTED_FREE_AND_COMMERCIAL, false, List.of("Volunteer developers"),
                        null, false, getProgrammingLanguages(List.of("HTML", "JAVASCRIPT", "TYPESCRIPT")))
        );
        tools.add(
                new ModelingToolVerified("Graphity", "https://www.graphity.com/", false, List.of(Technology.APP),
                        false, true, Category.GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("UML", "BPMN", "ER")), false,
                        true, License.COMMERCIAL, true, List.of("yWorks"),
                        null, false, null)
        );
        tools.add(
                new ModelingToolVerified("Graphiti", "https://www.eclipse.org/graphiti/", true, List.of(Technology.FRAMEWORK),
                        false, false, Category.GRAPHICAL_MODELING_TOOL, null, false,
                        true, License.FREE, false, List.of("Eclipse Foundation"),
                        null, false, getProgrammingLanguages(List.of("Java")))
        );
        tools.add(
                new ModelingToolVerified("iGrafx", "https://www.igrafx.com/", false, List.of(Technology.APP),
                        true, true, Category.BUSINESS_TOOL, getModelingLanguages(List.of("BPMN")), false,
                        true, License.RESTRICTED_FREE_AND_COMMERCIAL, true, null,
                        null, true, null)
        );
        tools.add(
                new ModelingToolVerified("JetBrains MPS", "https://www.jetbrains.com/mps/", false, List.of(Technology.APP),
                        false, true, Category.METAMODELING_TOOL, null, false,
                        true, License.RESTRICTED_FREE_AND_COMMERCIAL, true, List.of("JetBrains"),
                        null, false, getProgrammingLanguages(List.of("Java")))
        );
        tools.add(
                new ModelingToolVerified("JointJS", "https://www.jointjs.com/", true, List.of(Technology.FRAMEWORK),
                        false, false, Category.MIXED_TEXTUAL_AND_GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("UML", "BPMN", "ER")), false,
                        true, License.RESTRICTED_FREE_AND_COMMERCIAL, true, null,
                        null, false, getProgrammingLanguages(List.of("JavaScript", "HTML")))
        );
        tools.add(
                new ModelingToolVerified("jslumb", "https://jsplumbtoolkit.com/", true, List.of(Technology.FRAMEWORK),
                        true, false, Category.MIXED_TEXTUAL_AND_GRAPHICAL_MODELING_TOOL, null, false,
                        true, License.FREE, false, null,
                        null, false, getProgrammingLanguages(List.of("JAVASCRIPT", "HTML", "TYPESCRIPT", "CSS")))
        );
        tools.add(
                new ModelingToolVerified("jsUML2 Editor", "http://www.jrromero.net/tools/jsUML2", true, List.of(Technology.APP),
                        true, true, Category.GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("UML")), false,
                        false, License.FREE, false, null,
                        null, false, getProgrammingLanguages(List.of("JAVASCRIPT", "HTML")))
        );
        tools.add(
                new ModelingToolVerified("Lucidchart", "https://www.lucidchart.com", false, List.of(Technology.APP),
                        true, false, Category.DRAWING_TOOL, getModelingLanguages(List.of("UML", "BPMN", "ER")), false,
                        true, License.COMMERCIAL, true, null,
                        null, true, getProgrammingLanguages(List.of("Java", "Scala", "Ruby", "PHP", "JavaScript")))
        );
        tools.add(
                new ModelingToolVerified("MagicDraw", "https://www.3ds.com/products-services/catia/products/no-magic/", false, List.of(Technology.APP),
                        false, true, Category.GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("UML", "SysML", "BPMN")), false,
                        true, License.RESTRICTED_FREE_AND_COMMERCIAL, true, List.of("No Magic, Inc."),
                        getPlatforms(List.of("LINUX", "MACOS", "WINDOWS")), false, null)
        );
        tools.add(
                new ModelingToolVerified("MelanEE", "http://www.melanee.org/", false, List.of(Technology.APP),
                        false, true, Category.METAMODELING_TOOL, null, false,
                        false, License.FREE, false, List.of("University of Mannheim - Software Engineering Group"),
                        null, false, null)
        );
        tools.add(
                new ModelingToolVerified("Mermaid.js", "https://mermaid-js.github.io/mermaid/#/", true, List.of(Technology.LIBRARY),
                        false, true, Category.METAMODELING_TOOL, null, false,
                        false, License.FREE, false, List.of("University of Mannheim - Software Engineering Group"),
                        null, false, null)
        );
        tools.add(
                new ModelingToolVerified("MetaEdit+", "https://www.metacase.com/products.html", false, List.of(Technology.APP),
                        false, true, Category.METAMODELING_TOOL, null, false,
                        false, License.RESTRICTED_FREE_AND_COMMERCIAL, false, List.of("MetaCase"),
                        null, false, null)
        );
        tools.add(
                new ModelingToolVerified("MetaUML", "https://github.com/ogheorghies/MetaUML", false, List.of(Technology.APP),
                        false, true, Category.METAMODELING_TOOL, null, false,
                        false, License.RESTRICTED_FREE_AND_COMMERCIAL, false, List.of("Volunteer developers"),
                        null, false, null)
        );
        tools.add(
                new ModelingToolVerified("Microsoft Visio", "https://www.microsoft.com/en-us/microsoft-365/visio/flowchart-software", false, List.of(Technology.APP),
                        true, true, Category.BUSINESS_TOOL, getModelingLanguages(List.of("UML", "BPMN")), false,
                        true, License.COMMERCIAL, true, List.of("Microsoft Corporation"),
                        null, true, getProgrammingLanguages(List.of("VBA")))
        );
        tools.add(
                new ModelingToolVerified("Mindfusion", "https://mindfusion.eu/index.html", true, List.of(Technology.LIBRARY),
                        true, false, Category.DRAWING_TOOL, getModelingLanguages(List.of("UML")), false,
                        true, License.RESTRICTED_FREE_AND_COMMERCIAL, false, null,
                        null, false, getProgrammingLanguages(List.of("JAVASCRIPT", "C#", "TYPESCRIPT", "CSS")))
        );
        tools.add(
                new ModelingToolVerified("Miro", "https://miro.com/de/", false, List.of(Technology.APP),
                        true, true, Category.BUSINESS_TOOL, getModelingLanguages(List.of("UML", "BPMN", "ER")), false,
                        true, License.RESTRICTED_FREE_AND_COMMERCIAL, true, List.of("Andrey Khusid"),
                        null, true, null)
        );
        tools.add(
                new ModelingToolVerified("Modelio", "https://www.modelio.org/", true, List.of(Technology.APP),
                        false, true, Category.GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("UML", "SysML", "BPMN")), true,
                        true, License.FREE, false, null,
                        null, false, getProgrammingLanguages(List.of("JAVA", "C++")))
        );
        tools.add(
                new ModelingToolVerified("Moqups", "https://moqups.com", false, List.of(Technology.APP),
                        true, false, Category.BUSINESS_TOOL, getModelingLanguages(List.of("UML", "SysML", "BPMN")), false,
                        true, License.RESTRICTED_FREE_AND_COMMERCIAL, true, List.of("Emil Tamas"),
                        null, true, null)
        );
        tools.add(
                new ModelingToolVerified("mxGraph", "https://github.com/jgraph/mxgraph", true, List.of(Technology.LIBRARY),
                        false, false, Category.DRAWING_TOOL, null, false,
                        true, License.FREE, false, null,
                        null, false, getProgrammingLanguages(List.of("HTML", "JAVASCRIPT", "JAVA", "C#")))
        );
        tools.add(
                new ModelingToolVerified("Nomnoml", "https://www.nomnoml.com/", true, List.of(Technology.LIBRARY),
                        true, false, Category.TEXT_BASED_MODELING_TOOL, getModelingLanguages(List.of("UML")), true,
                        true, License.FREE, false, null,
                        null, false, getProgrammingLanguages(List.of("TYPESCRIPT", "JAVASCRIPT", "HTML", "CSS", "YACC")))
        );
        tools.add(
                new ModelingToolVerified("ObeoDesigner", "https://www.obeodesigner.com/en", true, List.of(Technology.APP),
                        false, true, Category.MIXED_TEXTUAL_AND_GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("UML", "SysML", "BPMN")), false,
                        true, License.RESTRICTED_FREE_AND_COMMERCIAL, false, null,
                        null, true, getProgrammingLanguages(List.of("Java")))
        );
        tools.add(
                new ModelingToolVerified("OpenPonk Modeling Platform", "https://openponk.org/", true, List.of(Technology.APP),
                        false, true, Category.GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("UML", "BPMN")), true,
                        true, License.FREE, false, null,
                        null, false, getProgrammingLanguages(List.of("SMALLTALK")))
        );
        tools.add(
                new ModelingToolVerified("Pencil Project", "https://pencil.evolus.vn/", true, List.of(Technology.APP),
                        false, true, Category.DRAWING_TOOL, null, false,
                        false, License.FREE, false, List.of("Volunteer developers"),
                        null, false, getProgrammingLanguages(List.of("JavaScript")))
        );
        tools.add(
                new ModelingToolVerified("pgModeler", "https://pgmodeler.io/", true, List.of(Technology.APP),
                        false, true, Category.GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("ER")), false,
                        false, License.COMMERCIAL, false, List.of("Raphael Araújo e Silva"),
                        null, false, getProgrammingLanguages(List.of("C++")))
        );
        tools.add(
                new ModelingToolVerified("PlantUML", "https://plantuml.com/de/", true, List.of(Technology.LIBRARY),
                        true, false, Category.TEXT_BASED_MODELING_TOOL, getModelingLanguages(List.of("UML")), true,
                        true, License.FREE, false, List.of("Arnaud Roques"),
                        null, false, getProgrammingLanguages(List.of("Java")))
        );
        tools.add(
                new ModelingToolVerified("ProcessOn", "https://www.processon.io/", false, List.of(Technology.APP),
                        true, false, Category.DRAWING_TOOL, getModelingLanguages(List.of("UML", "BPMN")), false,
                        true, null, false, null,
                        null, true, null)
        );
        tools.add(
                new ModelingToolVerified("Rational Rose (IBM)", "https://www.ibm.com/support/pages/ibm-rational-rose-enterprise-7004-ifix001", false, List.of(Technology.APP),
                        false, true, Category.GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("UML")), false,
                        false, License.COMMERCIAL, true, List.of("IBM"),
                        getPlatforms(List.of("LINUX", "SOLARIS", "UNIX", "WINDOWS")), false, null)
        );
        tools.add(
                new ModelingToolVerified("SCADE", "https://www.ansys.com/products/embedded-software/ansys-scade-suite", false, List.of(Technology.APP),
                        false, true, Category.GRAPHICAL_MODELING_TOOL, null, false,
                        true, License.RESTRICTED_FREE_AND_COMMERCIAL, true, List.of("Ajei Gopal"),
                        null, false, null)
        );
        tools.add(
                new ModelingToolVerified("Simulink", "https://de.mathworks.com/products/simulink.html", false, List.of(Technology.APP),
                        true, false, Category.GRAPHICAL_MODELING_TOOL, null, false,
                        true, License.COMMERCIAL, true, List.of("MathWorks"),
                        null, true, null)
        );
        tools.add(
                new ModelingToolVerified("sketchboard", "https://sketchboard.me/home", false, List.of(Technology.APP),
                        true, false, Category.BUSINESS_TOOL, getModelingLanguages(List.of("UML")), false,
                        true, License.COMMERCIAL, true, List.of("Saiki Tanabe"),
                        null, false, null)
        );
        tools.add(
                new ModelingToolVerified("Slickplan", "https://slickplan.com/", false, List.of(Technology.APP),
                        true, false, Category.BUSINESS_TOOL, getModelingLanguages(List.of("UML", "BPMN", "ER")), false,
                        true, License.RESTRICTED_FREE_AND_COMMERCIAL, true, List.of("Ian Lawson"),
                        null, true, null)
        );
        tools.add(
                new ModelingToolVerified("Software Ideas Modeler", "https://www.softwareideas.net/", false, List.of(Technology.APP),
                        false, true, Category.GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("UML", "BPMN", "ER", "SysML")), true,
                        false, License.RESTRICTED_FREE_AND_COMMERCIAL, false, List.of("Dusan Rodina"),
                        null, false, getProgrammingLanguages(List.of("C#")))
        );
        tools.add(
                new ModelingToolVerified("Enterprise Architect", "https://sparxsystems.com/", false, List.of(Technology.APP),
                        false, true, Category.GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("UML", "BPMN", "ER", "SysML")), false,
                        true, License.COMMERCIAL, false, List.of("Sparx"),
                        getPlatforms(List.of("LINUX", "MACOS", "WINDOWS")), true, getProgrammingLanguages(List.of("C++")))
        );
        tools.add(
                new ModelingToolVerified("StarUML", "https://staruml.io/", true, List.of(Technology.APP),
                        false, true, Category.MIXED_TEXTUAL_AND_GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("UML", "ER", "SysML")), true,
                        true, License.RESTRICTED_FREE_AND_COMMERCIAL, false, List.of("MKLabs Co. Ltd."),
                        null, false, null)
        );
        tools.add(
                new ModelingToolVerified("Swimlanes", "https://swimlanes.io/", true, List.of(Technology.APP),
                        true, false, Category.TEXT_BASED_MODELING_TOOL, getModelingLanguages(List.of("UML")), true,
                        false, License.FREE, false, List.of("Volunteer developers"),
                        getPlatforms(List.of("Chrome", "Edge", "Firefox")), false, getProgrammingLanguages(List.of("HTML", "JavaScript")))
        );
        tools.add(
                new ModelingToolVerified("UMLetino", "https://www.umletino.com/", true, List.of(Technology.APP),
                        true, true, Category.DRAWING_TOOL, getModelingLanguages(List.of("UML")), false,
                        false, License.FREE, false, null,
                        null, false, null)
        );
        tools.add(
                new ModelingToolVerified("Umple", "https://cruise.umple.org/umple/", true, List.of(Technology.FRAMEWORK),
                        true, true, Category.MIXED_TEXTUAL_AND_GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("UML")), false,
                        true, License.FREE, false, List.of("Cruise Group in the Department of Electrical Engineering and Computer Science, Led by Timothy Lethbridge at University of Ottawa"),
                        null, false, getProgrammingLanguages(List.of("Java")))
        );
        tools.add(
                new ModelingToolVerified("Visual Paradigm", "https://online.visual-paradigm.com/diagrams/solutions/free-online-diagram-editor/", false, List.of(Technology.APP),
                        true, true, Category.GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("UML", "BPMN")), false,
                        true, License.COMMERCIAL, false, List.of("Visual Paradigm International"),
                        null, true, getProgrammingLanguages(List.of("Java")))
        );
        tools.add(
                new ModelingToolVerified("WebGME", "https://webgme.org/", true, List.of(Technology.APP),
                        true, false, Category.METAMODELING_TOOL, null, false,
                        true, License.FREE, false, null,
                        null, false, null)
        );
        tools.add(
                new ModelingToolVerified("Xtext", "https://www.eclipse.org/Xtext/", true, List.of(Technology.FRAMEWORK),
                        false, false, Category.TEXT_BASED_MODELING_TOOL, null, false,
                        true, License.FREE, false, List.of("Eclipse Foundation"),
                        null, false, getProgrammingLanguages(List.of("Java", "GAP", "XTEND")))
        );
        tools.add(
                new ModelingToolVerified("yEd", "https://www.yworks.com/products/yed", false, List.of(Technology.APP),
                        true, true, Category.GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("UML", "BPMN", "ER")), false,
                        true, License.RESTRICTED_FREE_AND_COMMERCIAL, true, List.of("yWorks"),
                        null, false, null)
        );
        tools.add(
                new ModelingToolVerified("yFiles", "https://www.yworks.com/products/yfiles", false, List.of(Technology.LIBRARY),
                        true, true, Category.GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("UML", "BPMN")), false,
                        true, License.RESTRICTED_FREE_AND_COMMERCIAL, false, List.of("yWorks"),
                        null, false, null)
        );
        tools.add(
                new ModelingToolVerified("ZenUML", "https://www.zenuml.com/", true, List.of(Technology.APP),
                        true, true, Category.MIXED_TEXTUAL_AND_GRAPHICAL_MODELING_TOOL, getModelingLanguages(List.of("UML")), false,
                        true, License.RESTRICTED_FREE_AND_COMMERCIAL, false, null,
                        null, false, getProgrammingLanguages(List.of("JAVASCRIPT", "TYPESCRIPT", "JAVA", "VUE", "HTML")))
        );

        repository.saveAll(tools);
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
                    new ModelingToolVerified(
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
                List<ModelingToolVerified> plTools = modelingLanguageRepository.findModelingToolByModelingLanguageId(language.getId());
                language.setModelingTools(plTools);
                modelingLanguageRepository.save(language);
            }
            for (Platform p : platform) {
                List<ModelingToolVerified> plTools = platformRepository.findModelingToolVerifiedByPlatformId(p.getId());
                p.setModelingTools(plTools);
                platformRepository.save(p);
            }
            for (ProgrammingLanguage language : programmingLanguages) {
                List<ModelingToolVerified> plTools = repository.findModelingToolVerifiedByProgrammingLanguageId(language.getId());
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
