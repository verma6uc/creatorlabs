<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CreatorLabs Onboarding</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    <!-- Google Fonts - Inter -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/static/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/onboarding/css/onboarding.css" rel="stylesheet">
</head>
<body class="bg-dark">
    <div class="container-fluid">
        <div class="onboarding-container">
            <form id="onboardingForm" action="onboarding-submit" method="POST" novalidate>
                <!-- Progress Bar -->
                <div class="progress-container">
                    <div class="progress">
                        <div class="progress-bar" role="progressbar" style="width: 0%"></div>
                    </div>
                    <div class="steps">
                        <div class="step active" data-step="1">
                            <div class="step-number">1</div>
                            <span>Brand Identity</span>
                        </div>
                        <div class="step" data-step="2">
                            <div class="step-number">2</div>
                            <span>Workspace Setup</span>
                        </div>
                        <div class="step" data-step="3">
                            <div class="step-number">3</div>
                            <span>AI Team</span>
                        </div>
                    </div>
                </div>

                <!-- Brand Identity Section -->
                <div class="section-content active" data-step="1">
                    <div class="section-title">
                        <i class="bi bi-palette"></i>
                        Choose Your Brand Identity
                    </div>
                    <p class="text-muted mb-4">Select the archetype that best represents your brand's personality</p>

                    <div class="row g-4">
                        <div class="col-md-6">
                            <div class="card-option" data-value="guide">
                                <div class="d-flex justify-content-between mb-3">
                                    <div class="ai-agent-icon">
                                        <i class="bi bi-compass"></i>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="brandIdentity" id="guide" value="guide" required>
                                    </div>
                                </div>
                                <h4>The Guide</h4>
                                <p class="text-muted mb-0">We help others navigate and achieve their goals</p>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div class="card-option" data-value="expert">
                                <div class="d-flex justify-content-between mb-3">
                                    <div class="ai-agent-icon">
                                        <i class="bi bi-book"></i>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="brandIdentity" id="expert" value="expert" required>
                                    </div>
                                </div>
                                <h4>The Expert</h4>
                                <p class="text-muted mb-0">We provide deep knowledge and trusted solutions</p>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div class="card-option" data-value="enabler">
                                <div class="d-flex justify-content-between mb-3">
                                    <div class="ai-agent-icon">
                                        <i class="bi bi-rocket"></i>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="brandIdentity" id="enabler" value="enabler" required>
                                    </div>
                                </div>
                                <h4>The Enabler</h4>
                                <p class="text-muted mb-0">We empower others to reach their full potential</p>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Workspace Setup Section -->
                <div class="section-content" data-step="2" style="display: none;">
                    <div class="section-title">
                        <i class="bi bi-gear"></i>
                        Workspace Preferences
                    </div>

                    <div class="row g-4 mb-5">
                        <div class="col-md-6">
                            <label class="form-label" for="developmentFocus">Development Focus</label>
                            <select class="form-select" id="developmentFocus" name="developmentFocus" required>
                                <option value="" selected disabled>Select primary focus</option>
                                <option value="web">Web Applications</option>
                                <option value="mobile">Mobile Apps</option>
                                <option value="desktop">Desktop Software</option>
                                <option value="api">APIs & Services</option>
                            </select>
                            <div class="invalid-feedback">Please select your development focus</div>
                        </div>

                        <div class="col-md-6">
                            <label class="form-label" for="teamCollaboration">Team Collaboration</label>
                            <select class="form-select" id="teamCollaboration" name="teamCollaboration" required>
                                <option value="" selected disabled>Select collaboration style</option>
                                <option value="agile">Agile/Scrum</option>
                                <option value="kanban">Kanban</option>
                                <option value="waterfall">Traditional/Waterfall</option>
                            </select>
                            <div class="invalid-feedback">Please select your collaboration style</div>
                        </div>
                    </div>
                </div>

                <!-- AI Team Section -->
                <div class="section-content" data-step="3" style="display: none;">
                    <div class="section-title">
                        <i class="bi bi-cpu"></i>
                        Meet Your AI Team
                    </div>

                    <p class="text-muted mb-4">These AI agents will help build and manage your products</p>

                    <div class="ai-agents mb-5">
                        <div class="ai-agent">
                            <div class="ai-agent-icon">
                                <i class="bi bi-stars"></i>
                            </div>
                            <div>
                                <h5 class="mb-1">Seldon & Baley</h5>
                                <p class="text-muted mb-0">Strategic planning and research specialists. They'll help shape your product vision and market approach.</p>
                            </div>
                        </div>

                        <div class="ai-agent">
                            <div class="ai-agent-icon">
                                <i class="bi bi-layout-text-window"></i>
                            </div>
                            <div>
                                <h5 class="mb-1">Dors</h5>
                                <p class="text-muted mb-0">Frontend experience and UI/UX expert. Creates intuitive, beautiful interfaces your users will love.</p>
                            </div>
                        </div>

                        <div class="ai-agent">
                            <div class="ai-agent-icon">
                                <i class="bi bi-code-square"></i>
                            </div>
                            <div>
                                <h5 class="mb-1">Daneel & Giskard</h5>
                                <p class="text-muted mb-0">Backend logic and integration specialists. They handle all the complex technical implementations.</p>
                            </div>
                        </div>

                        <div class="ai-agent">
                            <div class="ai-agent-icon">
                                <i class="bi bi-shield-check"></i>
                            </div>
                            <div>
                                <h5 class="mb-1">Calvin</h5>
                                <p class="text-muted mb-0">Quality assurance and testing expert. Ensures everything works perfectly before launch.</p>
                            </div>
                        </div>
                    </div>

                    <div class="communication-preferences mt-5">
                        <label class="form-label">How would you like the AI team to communicate?</label>
                        <div class="card-option mb-3" data-value="detailed">
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="aiComm" id="detailed" value="detailed" required>
                                <label class="form-check-label" for="detailed">
                                    <h5>Detailed & Comprehensive</h5>
                                    <p class="text-muted mb-0">In-depth explanations and thorough documentation</p>
                                </label>
                            </div>
                        </div>
                        <div class="card-option mb-3" data-value="concise">
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="aiComm" id="concise" value="concise" required>
                                <label class="form-check-label" for="concise">
                                    <h5>Quick & Concise</h5>
                                    <p class="text-muted mb-0">Brief, to-the-point updates and key information</p>
                                </label>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Navigation Buttons -->
                <div class="d-flex justify-content-between mt-5">
                    <button type="button" class="btn btn-outline" id="prevBtn" style="display: none;">
                        <i class="bi bi-arrow-left me-2"></i>
                        Back
                    </button>
                    <button type="button" class="btn btn-primary" id="nextBtn">
                        Continue
                        <i class="bi bi-arrow-right ms-2"></i>
                    </button>
                </div>
            </form>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Custom JS -->
    <script src="${pageContext.request.contextPath}/static/onboarding/js/onboarding.js"></script>
</body>
</html>
