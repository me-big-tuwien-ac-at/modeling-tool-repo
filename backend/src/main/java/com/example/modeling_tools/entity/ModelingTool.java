package com.example.modeling_tools.entity;

import com.example.modeling_tools.entity.properties.ModelingLanguage;
import com.example.modeling_tools.entity.properties.Platform;
import com.example.modeling_tools.entity.properties.ProgrammingLanguage;
import com.example.modeling_tools.type.Category;
import com.example.modeling_tools.type.License;
import com.example.modeling_tools.type.Technology;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;

import java.util.List;

@MappedSuperclass
public abstract class ModelingTool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String link;

    @Column(name = "open_source")
    private Boolean openSource;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Technology> technology;

    @Column(name = "web_app")
    private Boolean webApp;

    @Column(name = "desktop_app")
    private Boolean desktopApp;

    @Enumerated(EnumType.STRING)
    @Column
    private Category category;

    @ManyToMany
    private List<ModelingLanguage> modelingLanguages;

    @Column(name = "source_code_generation")
    private Boolean sourceCodeGeneration;

    @Column(name = "cloud_service")
    private Boolean cloudService;

    @Enumerated(EnumType.STRING)
    @Column
    private License license;

    @Column(name = "login_required")
    private Boolean loginRequired;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> creator;

    @ManyToMany
    private List<Platform> platform;

    @Column(name = "real_time_collab")
    private Boolean realTimeCollab;

    @ManyToMany
    private List<ProgrammingLanguage> programmingLanguage;

    public ModelingTool() {
    }

    public ModelingTool(
            String name,
            String link,
            Boolean openSource,
            List<Technology> technology,
            Boolean webApp,
            Boolean desktopApp,
            Category category,
            List<ModelingLanguage> modelingLanguages,
            Boolean sourceCodeGeneration,
            Boolean cloudService,
            License license,
            Boolean loginRequired,
            List<String> creator,
            List<Platform> platform,
            Boolean realTimeCollab,
            List<ProgrammingLanguage> programmingLanguage
    ) {
        this.name = name;
        this.link = link;
        this.openSource = openSource;
        this.technology = technology;
        this.webApp = webApp;
        this.desktopApp = desktopApp;
        this.category = category;
        this.modelingLanguages = modelingLanguages;
        this.sourceCodeGeneration = sourceCodeGeneration;
        this.cloudService = cloudService;
        this.license = license;
        this.loginRequired = loginRequired;
        this.creator = creator;
        this.platform = platform;
        this.realTimeCollab = realTimeCollab;
        this.programmingLanguage = programmingLanguage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getOpenSource() {
        return openSource;
    }

    public void setOpenSource(Boolean openSource) {
        this.openSource = openSource;
    }

    public List<Technology> getTechnology() {
        return technology;
    }

    public List<String> getTechnologyAsStringUpper() {
        return technology == null
                ? null
                : technology.stream().map(technology -> technology.name().toUpperCase()).toList();
    }

    public void setTechnology(List<Technology> technology) {
        this.technology = technology;
    }

    public Boolean getWebApp() {
        return webApp;
    }

    public void setWebApp(Boolean webApp) {
        this.webApp = webApp;
    }

    public Boolean getDesktopApp() {
        return desktopApp;
    }

    public void setDesktopApp(Boolean desktopApp) {
        this.desktopApp = desktopApp;
    }

    public Category getCategory() {
        return category;
    }

    public String getCategoryString() {
        return category == null ? null : category.toString();
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<ModelingLanguage> getModelingLanguages() {
        return modelingLanguages;
    }

    public List<String> getModelingLanguagesAsStringUpper() {
        return modelingLanguages == null
                ? null
                : modelingLanguages.stream().map(modelingLanguage -> modelingLanguage.getName().toUpperCase()).toList();
    }

    public void setModelingLanguages(List<ModelingLanguage> modelingLanguages) {
        this.modelingLanguages = modelingLanguages;
    }

    public Boolean getSourceCodeGeneration() {
        return sourceCodeGeneration;
    }

    public void setSourceCodeGeneration(Boolean sourceCodeGeneration) {
        this.sourceCodeGeneration = sourceCodeGeneration;
    }

    public Boolean getCloudService() {
        return cloudService;
    }

    public void setCloudService(Boolean cloudService) {
        this.cloudService = cloudService;
    }

    public License getLicense() {
        return license;
    }

    public String getLicenseString() {
        return license == null ? null : license.toString();
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public Boolean getLoginRequired() {
        return loginRequired;
    }

    public void setLoginRequired(Boolean loginRequired) {
        this.loginRequired = loginRequired;
    }

    public List<String> getCreator() {
        return creator;
    }

    public List<String> getCreatorUpper() {
        return creator == null ? null : creator.stream().map(String::toUpperCase).toList();
    }

    public void setCreator(List<String> creator) {
        this.creator = creator;
    }

    public List<Platform> getPlatform() {
        return platform;
    }

    public List<String> getPlatformAsStringUpper() {
        return platform == null ? null : platform.stream().map(platform -> platform.getName().toUpperCase()).toList();
    }

    public void setPlatform(List<Platform> platform) {
        this.platform = platform;
    }

    public Boolean getRealTimeCollab() {
        return realTimeCollab;
    }

    public void setRealTimeCollab(Boolean realTimeCollab) {
        this.realTimeCollab = realTimeCollab;
    }

    public List<ProgrammingLanguage> getProgrammingLanguage() {
        return programmingLanguage;
    }

    public List<String> getProgrammingLanguageAsStringUpper() {
        return programmingLanguage == null
                ? null
                : programmingLanguage.stream().map(language -> language.getName().toUpperCase()).toList();
    }

    public void setProgrammingLanguage(List<ProgrammingLanguage> programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    @Override
    public String toString() {
        return "ModelingTool{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", openSource=" + openSource +
                ", technology=" + technology +
                ", webApp=" + webApp +
                ", desktopApp=" + desktopApp +
                ", category='" + category + '\'' +
                ", sourceCodeGeneration=" + sourceCodeGeneration +
                ", cloudService=" + cloudService +
                ", license=" + license +
                ", loginRequired=" + loginRequired +
                ", creator=" + creator +
                ", realTimeCollab=" + realTimeCollab +
                '}';
    }
}
