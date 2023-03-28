package com.example.modeling_tools.entity;

import com.example.modeling_tools.entity.properties.ModelingLanguage;
import com.example.modeling_tools.entity.properties.Platform;
import com.example.modeling_tools.entity.properties.ProgrammingLanguage;
import com.example.modeling_tools.type.Category;
import com.example.modeling_tools.type.License;
import com.example.modeling_tools.type.Technology;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "modeling_tool_edit_proposal")
public class ModelingToolSuggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "modeling_tool_id")
    private Long modelingToolId;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "admin_token", nullable = false)
    private String adminToken;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getModelingToolId() {
        return modelingToolId;
    }

    public void setModelingToolId(Long modelingToolId) {
        this.modelingToolId = modelingToolId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getAdminToken() {
        return adminToken;
    }

    public void setAdminToken(String adminToken) {
        this.adminToken = adminToken;
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

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<ModelingLanguage> getModelingLanguages() {
        return modelingLanguages;
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

    public void setCreator(List<String> creator) {
        this.creator = creator;
    }

    public List<Platform> getPlatform() {
        return platform;
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

    public void setProgrammingLanguage(List<ProgrammingLanguage> programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }
}
