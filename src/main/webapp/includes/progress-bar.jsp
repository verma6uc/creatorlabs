<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String currentStep = request.getParameter("step");
    if (currentStep == null) currentStep = "1";
    int step = Integer.parseInt(currentStep);
    
    String[] steps = {
        "Company Details",
        "Brand Personality",
        "Team & Goals",
        "Communication",
        "Review & Setup"
    };
    
    int progressValue = (step * 100) / steps.length;
%>

<div class="progress-section mb-8">
    <div class="steps-indicator d-flex justify-content-between mb-3">
        <% for (int i = 0; i < steps.length; i++) { %>
            <div class="step-item text-center">
                <div class="step-circle <%= i < step ? "completed" : i == step - 1 ? "active" : "" %>">
                    <% if (i < step - 1) { %>
                        <i class="bi bi-check-lg"></i>
                    <% } else { %>
                        <%= i + 1 %>
                    <% } %>
                </div>
                <div class="step-label d-none d-md-block mt-2">
                    <small class="text-muted"><%= steps[i] %></small>
                </div>
            </div>
        <% } %>
    </div>
    
    <div class="progress">
        <div class="progress-bar" role="progressbar" 
             style="width: <%= progressValue %>%"
             aria-valuenow="<%= progressValue %>" 
             aria-valuemin="0" 
             aria-valuemax="100">
        </div>
    </div>
</div>

<style>
    .step-circle {
        width: 32px;
        height: 32px;
        border-radius: 50%;
        background: rgba(255, 255, 255, 0.1);
        border: 2px solid rgba(255, 255, 255, 0.2);
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 14px;
        color: rgba(255, 255, 255, 0.7);
        transition: all 0.3s ease;
    }
    
    .step-circle.active {
        border-color: var(--color-purple);
        background: rgba(138, 111, 230, 0.1);
        color: var(--color-purple);
    }
    
    .step-circle.completed {
        background: var(--color-purple);
        border-color: var(--color-purple);
        color: white;
    }
    
    .step-label {
        font-size: 12px;
        max-width: 100px;
        margin: 0 auto;
    }
</style>
