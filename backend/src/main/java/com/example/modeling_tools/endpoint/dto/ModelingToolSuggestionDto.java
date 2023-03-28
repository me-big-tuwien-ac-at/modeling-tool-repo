package com.example.modeling_tools.endpoint.dto;

import com.example.modeling_tools.type.Category;
import com.example.modeling_tools.type.License;
import com.example.modeling_tools.type.Technology;

import java.util.ArrayList;
import java.util.List;

public class ModelingToolSuggestionDto {
    private Long id;
    private Long modelingToolId;
    private String userEmail;
    private String adminToken;
    private String name;
    private String link;
    private Boolean openSource;
    private List<Technology> technology;
    private Boolean webApp;
    private Boolean desktopApp;
    private Category category;
    private List<String> modelingLanguages;
    private Boolean sourceCodeGeneration;
    private Boolean cloudService;
    private License license;
    private Boolean loginRequired;
    private List<String> creator;
    private List<String> platform;
    private Boolean realTimeCollab;
    private List<String> programmingLanguage;
    private String feedback;

    public ModelingToolSuggestionDto() {
    }

    public ModelingToolSuggestionDto(
            Long modelingToolId,
            String userEmail,
            String name,
            String link,
            Boolean openSource,
            List<Technology> technology,
            Boolean webApp,
            Boolean desktopApp,
            Category category,
            List<String> modelingLanguages,
            Boolean sourceCodeGeneration,
            Boolean cloudService,
            License license,
            Boolean loginRequired,
            List<String> creator,
            List<String> platform,
            Boolean realTimeCollab,
            List<String> programmingLanguage,
            String feedback
    ) {
        this.modelingToolId = modelingToolId;
        this.userEmail = userEmail;
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
        this.feedback = feedback;
    }

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

    public String getCategoryString() {
        return category == null ? null : category.toString();
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<String> getModelingLanguages() {
        return modelingLanguages;
    }

    public List<String> getModelingLanguagesOrEmptyList() {
        return modelingLanguages != null ? modelingLanguages : new ArrayList<>();
    }

    public void setModelingLanguages(List<String> modelingLanguages) {
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

    public void setCreator(List<String> creator) {
        this.creator = creator;
    }

    public List<String> getPlatform() {
        return platform;
    }

    public List<String> getPlatformOrEmptyList() {
        return platform != null ? platform : new ArrayList<>();
    }

    public void setPlatform(List<String> platform) {
        this.platform = platform;
    }

    public Boolean getRealTimeCollab() {
        return realTimeCollab;
    }

    public void setRealTimeCollab(Boolean realTimeCollab) {
        this.realTimeCollab = realTimeCollab;
    }

    public List<String> getProgrammingLanguage() {
        return programmingLanguage;
    }

    public List<String> getProgrammingLanguageOrEmptyList() {
        return programmingLanguage != null ? programmingLanguage : new ArrayList<>();
    }

    public void setProgrammingLanguage(List<String> programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
