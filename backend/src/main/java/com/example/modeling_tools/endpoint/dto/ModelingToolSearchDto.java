package com.example.modeling_tools.endpoint.dto;

import java.util.List;

/**
 * DTO used for filtering modeling tools.
 */
public class ModelingToolSearchDto {
    private String name;
    private String link;
    private Boolean openSource;
    private List<String> technology;
    private Boolean webApp;
    private Boolean desktopApp;
    private String category;
    private List<String> modelingLanguages;
    private Boolean sourceCodeGeneration;
    private Boolean cloudService;
    private String license;
    private Boolean loginRequired;
    private String creator;
    private List<String> platform;
    private Boolean realTimeCollab;
    private List<String> programmingLanguage;
    private String orderBy;

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

    public List<String> getTechnology() {
        return technology;
    }

    public List<String> getTechnologyStringUpper() {
        return technology == null ? null : technology.stream().map(String::toUpperCase).toList();
    }

    public void setTechnology(List<String> technology) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getModelingLanguages() {
        return modelingLanguages;
    }

    public List<String> getModelingLanguagesUpper() {
        return modelingLanguages == null ? null : modelingLanguages.stream().map(String::toUpperCase).toList();
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

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Boolean getLoginRequired() {
        return loginRequired;
    }

    public void setLoginRequired(Boolean loginRequired) {
        this.loginRequired = loginRequired;
    }

    public String getCreator() {
        return creator;
    }

    public String getCreatorUpper() {
        return creator == null ? null : creator.toUpperCase();
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public List<String> getPlatform() {
        return platform;
    }

    public List<String> getPlatformUpper() {
        return platform == null ? null : platform.stream().map(String::toUpperCase).toList();
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

    public List<String> getProgrammingLanguageUpper() {
        return programmingLanguage == null ? null : programmingLanguage.stream().map(String::toUpperCase).toList();
    }

    public void setProgrammingLanguage(List<String> programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isEmpty() {
        return name == null
                && link == null
                && openSource == null
                && technology == null
                && webApp == null
                && desktopApp == null
                && category == null
                && modelingLanguages == null
                && sourceCodeGeneration == null
                && cloudService == null
                && license == null
                && loginRequired == null
                && creator == null
                && platform == null
                && realTimeCollab == null
                && programmingLanguage == null
                && orderBy == null;
    }

    public boolean listsEmpty() {
        return technology == null
                && creator == null
                && modelingLanguages == null
                && platform == null
                && programmingLanguage == null;
    }

    @Override
    public String toString() {
        return "ModelingToolSearchDto{" +
                "name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", openSource=" + openSource +
                ", technology=" + technology +
                ", webApp=" + webApp +
                ", desktopApp=" + desktopApp +
                ", category='" + category + '\'' +
                ", modelingLanguages=" + modelingLanguages +
                ", sourceCodeGeneration=" + sourceCodeGeneration +
                ", cloudService=" + cloudService +
                ", license='" + license + '\'' +
                ", loginRequired=" + loginRequired +
                ", creator=" + creator +
                ", platform=" + platform +
                ", realTimeCollab=" + realTimeCollab +
                ", programmingLanguage=" + programmingLanguage +
                ", orderBy='" + orderBy + '\'' +
                '}';
    }

    public String paramsAsString() {
        String result = "?";
        result += name != null ? ("name=" + name + "&") : "";
        result += link != null ? ("link=" + link + "&") : "";
        result += openSource != null ? ("openSource=" + openSource + "&") : "";
        result += technology != null ? ("technology=" + listParamAsString(technology) + "&") : "";
        result += webApp != null ? ("webApp=" + webApp + "&") : "";
        result += desktopApp != null ? ("webApp=" + desktopApp + "&") : "";
        result += category != null ? ("webApp=" + category + "&") : "";
        result += modelingLanguages != null ? ("modelingLanguages=" + listParamAsString(modelingLanguages) + "&") : "";
        result += sourceCodeGeneration != null ? ("sourceCodeGeneration=" + sourceCodeGeneration + "&") : "";
        result += cloudService != null ? ("cloudService=" + cloudService + "&") : "";
        result += license != null ? ("license=" + license + "&") : "";
        result += loginRequired != null ? ("loginRequired=" + loginRequired + "&") : "";
        result += creator != null ? ("creator=" + creator + "&") : "";
        result += platform != null ? ("platform=" + listParamAsString(platform) + "&") : "";
        result += realTimeCollab != null ? ("realTimeCollab=" + realTimeCollab + "&") : "";
        result += programmingLanguage != null ? ("programmingLanguage=" + listParamAsString(programmingLanguage) + "&") : "";
        result += orderBy != null ? ("orderBy=" + orderBy + "&") : "";
        return result.substring(0, result.length() - 1);
    }

    public String listParamAsString(Object o) {
        String listStr = o.toString();
        return listStr.length() == 2 ? "" : listStr.trim().substring(1, listStr.length() - 1).replaceAll(", ", ",");
    }
}
