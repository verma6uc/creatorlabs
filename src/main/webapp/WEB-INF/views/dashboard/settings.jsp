<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Settings - CreatorLabs</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    <!-- Google Fonts - Inter -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/static/css/style.css" rel="stylesheet">
    
    <style>
        .settings-container {
            padding: 2rem;
            max-width: 1000px;
            margin: 0 auto;
        }

        .settings-card {
            background: var(--bg-translucent);
            border: 1px solid var(--border-light);
            border-radius: 16px;
            padding: 2rem;
            margin-bottom: 2rem;
        }

        .settings-header {
            display: flex;
            align-items: center;
            gap: 1rem;
            margin-bottom: 1.5rem;
        }

        .settings-icon {
            width: 48px;
            height: 48px;
            background: rgba(138, 111, 230, 0.1);
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .settings-icon i {
            font-size: 24px;
            color: var(--accent-color);
        }

        .form-label {
            color: rgba(255, 255, 255, 0.9);
            font-weight: 500;
            margin-bottom: 0.75rem;
        }

        .form-control {
            background-color: var(--background-darker);
            border: 1px solid var(--border-light);
            color: #fff;
            border-radius: 8px;
            padding: 0.75rem 1rem;
            transition: all 0.3s ease;
        }

        .form-control:focus {
            background-color: var(--background-darker);
            border-color: var(--accent-color);
            color: #fff;
            box-shadow: 0 0 0 2px rgba(138, 111, 230, 0.2);
        }

        .form-select {
            background-color: var(--background-darker);
            border: 1px solid var(--border-light);
            color: #fff;
            border-radius: 8px;
            padding: 0.75rem 1rem;
        }

        .form-select:focus {
            background-color: var(--background-darker);
            border-color: var(--accent-color);
            color: #fff;
            box-shadow: 0 0 0 2px rgba(138, 111, 230, 0.2);
        }

        .preference-card {
            background: rgba(138, 111, 230, 0.05);
            border: 1px solid var(--border-light);
            border-radius: 12px;
            padding: 1.25rem;
            margin-bottom: 1rem;
        }

        .preference-card h5 {
            color: var(--accent-color);
            margin-bottom: 0.5rem;
        }

        .btn-save {
            background: var(--primary-gradient);
            border: none;
            padding: 0.75rem 1.5rem;
            font-weight: 500;
            transition: all 0.3s ease;
        }

        .btn-save:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 15px rgba(138, 111, 230, 0.3);
        }

        .danger-zone {
            border-color: rgba(255, 99, 99, 0.2);
            background: rgba(255, 99, 99, 0.05);
        }

        .btn-danger-outline {
            border: 2px solid rgba(255, 99, 99, 0.5);
            color: #ff6363;
            background: transparent;
            transition: all 0.3s ease;
        }

        .btn-danger-outline:hover {
            background: rgba(255, 99, 99, 0.1);
            border-color: #ff6363;
            color: #ff6363;
        }
    </style>
</head>
<body class="bg-dark">
    <div class="settings-container">
        <h1 class="mb-4">Settings</h1>

        <!-- Profile Settings -->
        <div class="settings-card">
            <div class="settings-header">
                <div class="settings-icon">
                    <i class="bi bi-person"></i>
                </div>
                <div>
                    <h3 class="mb-1">Profile Settings</h3>
                    <p class="text-muted mb-0">Manage your personal information</p>
                </div>
            </div>

            <form id="profileForm" class="row g-4">
                <div class="col-md-6">
                    <label class="form-label">Full Name</label>
                    <input type="text" class="form-control" name="fullName" value="${settings.fullName}">
                </div>
                <div class="col-md-6">
                    <label class="form-label">Email</label>
                    <input type="email" class="form-control" name="email" value="${settings.email}" readonly>
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-save">
                        <i class="bi bi-check-lg me-2"></i>
                        Save Changes
                    </button>
                </div>
            </form>
        </div>

        <!-- Preferences -->
        <div class="settings-card">
            <div class="settings-header">
                <div class="settings-icon">
                    <i class="bi bi-sliders"></i>
                </div>
                <div>
                    <h3 class="mb-1">Preferences</h3>
                    <p class="text-muted mb-0">Your current workspace preferences</p>
                </div>
            </div>

            <div class="row g-4">
                <div class="col-md-6">
                    <div class="preference-card">
                        <h5>Brand Identity</h5>
                        <p class="mb-0">${settings.brandIdentity}</p>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="preference-card">
                        <h5>Development Focus</h5>
                        <p class="mb-0">${settings.developmentFocus}</p>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="preference-card">
                        <h5>Team Collaboration</h5>
                        <p class="mb-0">${settings.teamCollaboration}</p>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="preference-card">
                        <h5>AI Communication Style</h5>
                        <p class="mb-0">${settings.aiCommunicationStyle}</p>
                    </div>
                </div>
                <div class="col-12">
                    <a href="${pageContext.request.contextPath}/onboarding" class="btn btn-outline-light">
                        <i class="bi bi-arrow-repeat me-2"></i>
                        Reconfigure Preferences
                    </a>
                </div>
            </div>
        </div>

        <!-- Security -->
        <div class="settings-card">
            <div class="settings-header">
                <div class="settings-icon">
                    <i class="bi bi-shield-lock"></i>
                </div>
                <div>
                    <h3 class="mb-1">Security</h3>
                    <p class="text-muted mb-0">Manage your account security</p>
                </div>
            </div>

            <form id="passwordForm" class="row g-4">
                <div class="col-md-6">
                    <label class="form-label">Current Password</label>
                    <input type="password" class="form-control" name="currentPassword">
                </div>
                <div class="col-md-6">
                    <label class="form-label">New Password</label>
                    <input type="password" class="form-control" name="newPassword">
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-save">
                        <i class="bi bi-key me-2"></i>
                        Change Password
                    </button>
                </div>
            </form>
        </div>

        <!-- Danger Zone -->
        <div class="settings-card danger-zone">
            <div class="settings-header">
                <div class="settings-icon">
                    <i class="bi bi-exclamation-triangle"></i>
                </div>
                <div>
                    <h3 class="mb-1">Danger Zone</h3>
                    <p class="text-muted mb-0">Irreversible account actions</p>
                </div>
            </div>

            <div class="d-flex gap-3">
                <button class="btn btn-danger-outline" data-bs-toggle="modal" data-bs-target="#deleteAccountModal">
                    <i class="bi bi-trash me-2"></i>
                    Delete Account
                </button>
                <button class="btn btn-danger-outline">
                    <i class="bi bi-download me-2"></i>
                    Export Data
                </button>
            </div>
        </div>
    </div>

    <!-- Delete Account Modal -->
    <div class="modal fade" id="deleteAccountModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content bg-dark">
                <div class="modal-header border-light">
                    <h5 class="modal-title">Delete Account</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <p>This action cannot be undone. All your data will be permanently deleted.</p>
                    <form id="deleteAccountForm">
                        <div class="mb-3">
                            <label class="form-label">Type "DELETE" to confirm</label>
                            <input type="text" class="form-control" name="confirmDelete" required>
                        </div>
                    </form>
                </div>
                <div class="modal-footer border-light">
                    <button type="button" class="btn btn-outline-light" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" form="deleteAccountForm" class="btn btn-danger">Delete Account</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Custom JS -->
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Profile Form Submission
            document.getElementById('profileForm').addEventListener('submit', function(e) {
                e.preventDefault();
                // Add form submission logic here
            });

            // Password Form Submission
            document.getElementById('passwordForm').addEventListener('submit', function(e) {
                e.preventDefault();
                // Add password change logic here
            });

            // Delete Account Form Submission
            document.getElementById('deleteAccountForm').addEventListener('submit', function(e) {
                e.preventDefault();
                const confirmInput = this.querySelector('input[name="confirmDelete"]');
                if (confirmInput.value === 'DELETE') {
                    // Add account deletion logic here
                }
            });
        });
    </script>
</body>
</html>
