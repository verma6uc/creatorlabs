<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - CreatorLabs</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    <!-- Google Fonts - Inter -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/static/css/style.css" rel="stylesheet">
    
    <style>
        .dashboard-container {
            padding: 2rem;
            max-width: 1400px;
            margin: 0 auto;
        }

        .stats-card {
            background: var(--bg-translucent);
            border: 1px solid var(--border-light);
            border-radius: 16px;
            padding: 1.5rem;
            transition: all 0.3s ease;
        }

        .stats-card:hover {
            transform: translateY(-5px);
            border-color: var(--accent-color);
        }

        .stats-icon {
            width: 48px;
            height: 48px;
            background: rgba(138, 111, 230, 0.1);
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-bottom: 1rem;
        }

        .stats-icon i {
            font-size: 24px;
            color: var(--accent-color);
        }

        .activity-list {
            background: var(--bg-translucent);
            border: 1px solid var(--border-light);
            border-radius: 16px;
            padding: 1.5rem;
        }

        .activity-item {
            display: flex;
            align-items: flex-start;
            gap: 1rem;
            padding: 1rem 0;
            border-bottom: 1px solid var(--border-light);
        }

        .activity-item:last-child {
            border-bottom: none;
        }

        .activity-icon {
            width: 36px;
            height: 36px;
            background: rgba(138, 111, 230, 0.1);
            border-radius: 8px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .activity-icon i {
            font-size: 18px;
            color: var(--accent-color);
        }

        .project-card {
            background: var(--bg-translucent);
            border: 1px solid var(--border-light);
            border-radius: 16px;
            padding: 1.5rem;
            height: 100%;
            transition: all 0.3s ease;
        }

        .project-card:hover {
            transform: translateY(-5px);
            border-color: var(--accent-color);
        }

        .status-badge {
            padding: 0.25rem 0.75rem;
            border-radius: 12px;
            font-size: 0.875rem;
            font-weight: 500;
        }

        .status-active {
            background: rgba(25, 135, 84, 0.1);
            color: #198754;
        }

        .status-completed {
            background: rgba(13, 110, 253, 0.1);
            color: #0d6efd;
        }

        .status-archived {
            background: rgba(108, 117, 125, 0.1);
            color: #6c757d;
        }
    </style>
</head>
<body class="bg-dark">
    <div class="dashboard-container">
        <!-- Welcome Section -->
        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h1 class="mb-0">Welcome Back!</h1>
                <p class="text-muted">Here's what's happening with your projects</p>
            </div>
            <div>
                <button class="btn btn-primary">
                    <i class="bi bi-plus-lg me-2"></i>
                    New Project
                </button>
            </div>
        </div>

        <!-- Stats Section -->
        <div class="row g-4 mb-5">
            <div class="col-md-4">
                <div class="stats-card">
                    <div class="stats-icon">
                        <i class="bi bi-folder"></i>
                    </div>
                    <h3>${dashboardData.projectSummary.total}</h3>
                    <p class="text-muted mb-0">Total Projects</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="stats-card">
                    <div class="stats-icon">
                        <i class="bi bi-lightning"></i>
                    </div>
                    <h3>${dashboardData.projectSummary.active}</h3>
                    <p class="text-muted mb-0">Active Projects</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="stats-card">
                    <div class="stats-icon">
                        <i class="bi bi-check-circle"></i>
                    </div>
                    <h3>${dashboardData.projectSummary.completed}</h3>
                    <p class="text-muted mb-0">Completed Projects</p>
                </div>
            </div>
        </div>

        <!-- Main Content -->
        <div class="row g-4">
            <!-- Projects Section -->
            <div class="col-lg-8">
                <h2 class="mb-4">Recent Projects</h2>
                <div class="row g-4">
                    <c:forEach items="${dashboardData.projects}" var="project">
                        <div class="col-md-6">
                            <div class="project-card">
                                <div class="d-flex justify-content-between align-items-start mb-3">
                                    <h5 class="mb-0">${project.name}</h5>
                                    <span class="status-badge status-${project.status.toLowerCase()}">
                                        ${project.status}
                                    </span>
                                </div>
                                <p class="text-muted mb-3">${project.description}</p>
                                <div class="d-flex justify-content-between align-items-center">
                                    <small class="text-muted">
                                        Updated <fmt:formatDate value="${project.updatedAt}" pattern="MMM d, yyyy"/>
                                    </small>
                                    <a href="#" class="btn btn-sm btn-outline-light">View Details</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <!-- Activity Section -->
            <div class="col-lg-4">
                <h2 class="mb-4">Recent Activity</h2>
                <div class="activity-list">
                    <c:forEach items="${dashboardData.recentActivity}" var="activity">
                        <div class="activity-item">
                            <div class="activity-icon">
                                <i class="bi bi-${activity.action == 'CREATE' ? 'plus' : 
                                                  activity.action == 'UPDATE' ? 'pencil' : 
                                                  activity.action == 'DELETE' ? 'trash' : 'arrow-right'}"></i>
                            </div>
                            <div>
                                <p class="mb-1">${activity.details.message}</p>
                                <small class="text-muted">
                                    <fmt:formatDate value="${activity.createdAt}" pattern="MMM d, yyyy h:mm a"/>
                                </small>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Custom JS -->
    <script>
        // Add any dashboard-specific JavaScript here
        document.addEventListener('DOMContentLoaded', function() {
            // Initialize any components or add event listeners
        });
    </script>
</body>
</html>
