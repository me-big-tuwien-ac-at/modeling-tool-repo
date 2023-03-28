package com.example.modeling_tools.endpoint.dto;

import com.example.modeling_tools.type.*;

import java.util.HashSet;
import java.util.List;

public class ModelingToolDto {
    private Long id;
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

    // HTML values
    private final String COLOR_NONE = "none";
    private final String COLOR_BOOLEAN = "#B22222";
    private final String COLOR_STRING = "#008000";
    private final String COLOR_NULL = "#00008B";
    private String changedColor;
    private final String htmlJsonString =
            """
            <div style="font: 14px monospace; margin: 1em 0">
              {
              <ul style="margin: 0 0 0 2em; list-style-type: none; padding: 0;">
                %s
                %s
                %s
                %s
                %s
                %s
                %s
                %s
                %s
                %s
                %s
                %s
                %s
                %s
                %s
                %s
              </ul>
              }
            </div>
            """;

    public ModelingToolDto() {
    }

    public ModelingToolDto(
            Long id,
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
            List<String> programmingLanguage) {
        this.id = id;
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

    public List<String> getModelingLanguages() {
        return modelingLanguages;
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

    public void setProgrammingLanguage(List<String> programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    public String toHtmlString() {
        String categoryString = category != null ? category.toString() : null;
        String licenseString = license != null ? license.toString() : null;
        return String.format(htmlJsonString,
                htmlKeyValuePairString(COLOR_NONE, "name", name),
                htmlKeyValuePairString(COLOR_NONE, "link", link),
                htmlKeyValuePairString(COLOR_NONE, "openSource", openSource),
                htmlKeyValuePairArray(COLOR_NONE, "technology", technology),
                htmlKeyValuePairString(COLOR_NONE, "webApp", webApp),
                htmlKeyValuePairString(COLOR_NONE, "desktopApp", desktopApp),
                htmlKeyValuePairString(COLOR_NONE, "category", categoryString),
                htmlKeyValuePairArray(COLOR_NONE, "modelingLanguages", modelingLanguages),
                htmlKeyValuePairString(COLOR_NONE, "sourceCodeGeneration", sourceCodeGeneration),
                htmlKeyValuePairString(COLOR_NONE, "cloudService", cloudService),
                htmlKeyValuePairString(COLOR_NONE, "license", licenseString),
                htmlKeyValuePairString(COLOR_NONE, "loginRequired", loginRequired),
                htmlKeyValuePairArray(COLOR_NONE, "creator", creator),
                htmlKeyValuePairArray(COLOR_NONE, "platform", platform),
                htmlKeyValuePairString(COLOR_NONE, "realTimeCollab", realTimeCollab),
                htmlKeyValuePairArray(COLOR_NONE, "programmingLanguage", programmingLanguage)
        );
    }

    public String toHtmlStringComparison(ModelingToolDto other, String changedColor) {
        this.changedColor = changedColor;
        String categoryString = category != null ? category.toString() : null;
        String otherCategoryString = other.getCategory() != null ? other.getCategory().toString() : null;
        String licenseString = license != null ? license.toString() : null;
        String otherLicenseString = other.getLicense() != null ? other.getLicense().toString() : null;
        return String.format(htmlJsonString,
                htmlKeyValuePairString(getBackgroundColor(name, other.getName()), "name", name),
                htmlKeyValuePairString(getBackgroundColor(link, other.getLink()), "link", link),
                htmlKeyValuePairString(getBackgroundColor(openSource, other.getOpenSource()), "openSource", openSource),
                htmlKeyValuePairArray(getBackgroundColorTechnology(technology, other.getTechnology()), "technology", technology),
                htmlKeyValuePairString(getBackgroundColor(webApp, other.getWebApp()), "webApp", webApp),
                htmlKeyValuePairString(getBackgroundColor(desktopApp, other.getDesktopApp()), "desktopApp", desktopApp),
                htmlKeyValuePairString(getBackgroundColor(categoryString, otherCategoryString), "category", categoryString),
                htmlKeyValuePairArray(getBackgroundColor(modelingLanguages, other.getModelingLanguages()), "modelingLanguages", modelingLanguages),
                htmlKeyValuePairString(getBackgroundColor(sourceCodeGeneration, other.getSourceCodeGeneration()), "sourceCodeGeneration", sourceCodeGeneration),
                htmlKeyValuePairString(getBackgroundColor(cloudService, other.getCloudService()), "cloudService", cloudService),
                htmlKeyValuePairString(getBackgroundColor(licenseString, otherLicenseString), "license", licenseString),
                htmlKeyValuePairString(getBackgroundColor(loginRequired, other.getLoginRequired()), "loginRequired", loginRequired),
                htmlKeyValuePairArray(getBackgroundColor(creator, other.getCreator()), "creator", creator),
                htmlKeyValuePairArray(getBackgroundColor(platform, other.getPlatform()), "platform", platform),
                htmlKeyValuePairString(getBackgroundColor(realTimeCollab, other.getRealTimeCollab()), "realTimeCollab", realTimeCollab),
                htmlKeyValuePairArray(getBackgroundColor(programmingLanguage, other.getProgrammingLanguage()), "programmingLanguage", programmingLanguage)
        );
    }

    private String htmlKeyValuePairString(String color, String key, Object value) {
        return String.format(
                """
                <li style="background: %s;">
                  <div style="padding: 0.15em 0; transition: background-color .2s ease-out 0s;">
                    <span style="font-weight: bold;">%s: </span>
                    <span style="color: %s;">%s</span>,
                  </div>
                </li>
                """,
                color,
                key,
                evaluateColor(value),
                valueToString(value)
        );
    }

    private String htmlKeyValuePairArray(String color, String key, List<?> value) {
        return String.format(
                """
                <li style="background: %s;">
                  <div style="padding: 0.15em 0; transition: background-color .2s ease-out 0s;">
                    <span style="font-weight: bold;">%s: </span>
                    %s%s
                  </div>
                </li>
                """,
                color,
                key,
                htmlArrayEntry(value),
                key.equals("programmingLanguage") ? "" : ","
        );
    }

    private String htmlArrayEntry(List<?> array) {
        StringBuilder html = new StringBuilder();
        if (array == null || array.isEmpty()) {
            return "<span style=\"color: #00008B;\">null</span>";
        }
        for (var entry : array) {
            if (html.toString().isBlank()) {
                html.append("<span style=\"color: #008000;\">\"").append(entry).append("\"</span>");
            } else {
                html.append(", <span style=\"color: #008000;\">\"").append(entry).append("\"</span>");
            }
        }
        return "[" + html + "]";
    }


    private String getBackgroundColor(String toolValue1, String toolValue2) {
        return (toolValue1 == null && toolValue2 == null) || (toolValue1 != null && toolValue1.equals(toolValue2)) ? COLOR_NONE : changedColor;
    }

    private String getBackgroundColor(Boolean toolValue1, Boolean toolValue2) {
        return (toolValue1 == null && toolValue2 == null) || (toolValue1 != null && toolValue1.equals(toolValue2)) ? COLOR_NONE : changedColor;
    }

    private String getBackgroundColor(List<String> toolValue1, List<String> toolValue2) {
        if (toolValue1 == null && toolValue2 == null) {
            return COLOR_NONE;
        }
        if ((toolValue1 == null && toolValue2.isEmpty()) || (toolValue2 == null && toolValue1.isEmpty())) {
            return COLOR_NONE;
        }
        if (toolValue1 == null || toolValue2 == null) {
            return changedColor;
        }
        if (toolValue1.isEmpty() && toolValue2.isEmpty()) {
            return COLOR_NONE;
        }
        return new HashSet<>(toolValue1).containsAll(toolValue2) && new HashSet<>(toolValue2).containsAll(toolValue1) ? COLOR_NONE : changedColor;
    }

    private String getBackgroundColorTechnology(List<Technology> toolValue1, List<Technology> toolValue2) {
        if (toolValue1 == null && toolValue2 == null) {
            return COLOR_NONE;
        }
        if ((toolValue1 == null && toolValue2.isEmpty()) || (toolValue2 == null && toolValue1.isEmpty())) {
            return COLOR_NONE;
        }
        if (toolValue1 == null || toolValue2 == null) {
            return changedColor;
        }
        if (toolValue1.isEmpty() && toolValue2.isEmpty()) {
            return COLOR_NONE;
        }
        return new HashSet<>(toolValue1).containsAll(toolValue2) && new HashSet<>(toolValue2).containsAll(toolValue1) ? COLOR_NONE : changedColor;
    }

    private String valueToString(Object o) {
        return o == null ? "null" : (o instanceof String ? ("\"" + o + "\"") : o.toString());
    }

    private String evaluateColor(Object o) {
        return o == null ? COLOR_NULL : (o instanceof String ? COLOR_STRING : COLOR_BOOLEAN);
    }
}
