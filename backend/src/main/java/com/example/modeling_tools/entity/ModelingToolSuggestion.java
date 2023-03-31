package com.example.modeling_tools.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;

@Entity(name = "modeling_tool_edit_proposal")
public class ModelingToolSuggestion extends ModelingTool {
    @Column(name = "modeling_tool_id")
    private Long modelingToolId;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "admin_token", nullable = false)
    private String adminToken;


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

}
