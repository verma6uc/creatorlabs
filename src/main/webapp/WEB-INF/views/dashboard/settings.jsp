<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Settings - CreatorLabs</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/dashboard.css">
</head>
<body>
    <div class="dashboard-container">
        <!-- Sidebar -->
        <aside class="dashboard-sidebar">
            <div class="sidebar-header">
                <h1>CreatorLabs</h1>
            </div>
            <nav class="sidebar-nav">
                <ul>
                    <li><a href="${pageContext.request.contextPath}/dashboard">Overview</a></li>
                    <li><a href="${pageContext.request.contextPath}/dashboard/projects">Projects</a></li>
                    <li><a href="${pageContext.request.contextPath}/dashboard/analytics">Analytics</a></li>
                    <li class="active"><a href="${pageContext.request.contextPath}/dashboard/settings">Settings</a></li>
                </ul>
            </nav>
        </aside>

        <!-- Main Content -->
        <main class="dashboard-main">
            <!-- Header -->
            <header class="dashboard-header">
                <h2>Settings</h2>
                <div class="header-actions">
                    <button class="notifications-btn">
                        <span class="icon">🔔</span>
                        <span class="badge">3</span>
                    </button>
                    <div class="user-menu">
                        <img src="${pageContext.request.contextPath}/static/images/avatar.png" alt="User avatar">
                        <span>${settings.username}</span>
                    </div>
                </div>
            </header>

            <!-- Settings Content -->
            <div class="settings-content">
                <!-- Profile Settings -->
                <section class="settings-section">
                    <h3>Profile Settings</h3>
                    <form id="profileForm" class="settings-form">
                        <div class="form-group">
                            <label for="firstName">First Name</label>
                            <input type="text" id="firstName" name="firstName" value="${settings.firstName}">
                        </div>
                        <div class="form-group">
                            <label for="lastName">Last Name</label>
                            <input type="text" id="lastName" name="lastName" value="${settings.lastName}">
                        </div>
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" id="email" name="email" value="${settings.email}">
                        </div>
                    </form>
                </section>

                <!-- Preferences -->
                <section class="settings-section">
                    <h3>Preferences</h3>
                    <form id="preferencesForm" class="settings-form">
                        <div class="form-group">
                            <label for="theme">Theme</label>
                            <select id="theme" name="theme">
                                <option value="light" ${settings.theme == 'light' ? 'selected' : ''}>Light</option>
                                <option value="dark" ${settings.theme == 'dark' ? 'selected' : ''}>Dark</option>
                                <option value="system" ${settings.theme == 'system' ? 'selected' : ''}>System Default</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="brandIdentity">Brand Identity</label>
                            <select id="brandIdentity" name="brandIdentity">
                                <c:forEach var="identity" items="${Constants.Onboarding.BRAND_IDENTITIES}">
                                    <option value="${identity}" ${settings.brandIdentity == identity ? 'selected' : ''}>${identity}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="developmentFocus">Development Focus</label>
                            <select id="developmentFocus" name="developmentFocus">
                                <c:forEach var="focus" items="${Constants.Onboarding.DEVELOPMENT_FOCUSES}">
                                    <option value="${focus}" ${settings.developmentFocus == focus ? 'selected' : ''}>${focus}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="teamCollaboration">Team Collaboration</label>
                            <select id="teamCollaboration" name="teamCollaboration">
                                <c:forEach var="collab" items="${Constants.Onboarding.TEAM_COLLABORATIONS}">
                                    <option value="${collab}" ${settings.teamCollaboration == collab ? 'selected' : ''}>${collab}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="aiCommunicationStyle">AI Communication Style</label>
                            <select id="aiCommunicationStyle" name="aiCommunicationStyle">
                                <c:forEach var="style" items="${Constants.Onboarding.AI_COMMUNICATION_STYLES}">
                                    <option value="${style}" ${settings.aiCommunicationStyle == style ? 'selected' : ''}>${style}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label class="checkbox-label">
                                <input type="checkbox" name="notificationsEnabled" 
                                    ${settings.notificationsEnabled ? 'checked' : ''}>
                                Enable Notifications
                            </label>
                        </div>
                    </form>
                </section>

                <!-- Security Settings -->
                <section class="settings-section">
                    <h3>Security</h3>
                    <form id="securityForm" class="settings-form">
                        <div class="form-group">
                            <button type="button" class="btn" onclick="showChangePasswordModal()">
                                Change Password
                            </button>
                        </div>
                        <div class="form-group">
                            <button type="button" class="btn" onclick="showTwoFactorSetup()">
                                Setup Two-Factor Authentication
                            </button>
                        </div>
                    </form>
                </section>

                <!-- Save Changes -->
                <div class="settings-actions">
                    <button type="button" class="btn btn-primary" onclick="saveAllSettings()">
                        Save Changes
                    </button>
                </div>
            </div>
        </main>
    </div>

    <!-- Scripts -->
    <script src="${pageContext.request.contextPath}/static/js/dashboard.js"></script>
    <script>
        function saveAllSettings() {
            const profileData = new FormData(document.getElementById('profileForm'));
            const preferencesData = new FormData(document.getElementById('preferencesForm'));
            
            const data = {
                profile: Object.fromEntries(profileData),
                preferences: Object.fromEntries(preferencesData)
            };

            fetch('${pageContext.request.contextPath}/dashboard/settings', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': '${sessionScope.csrfToken}'
                },
                body: JSON.stringify(data)
            })
            .then(response => response.json())
            .then(result => {
                if (result.success) {
                    showNotification('Settings saved successfully');
                } else {
                    showNotification('Error saving settings', 'error');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                showNotification('Error saving settings', 'error');
            });
        }

        function showNotification(message, type = 'success') {
            // TODO: Implement notification system
            alert(message);
        }

        function showChangePasswordModal() {
            // TODO: Implement password change modal
            alert('Change password functionality coming soon');
        }

        function showTwoFactorSetup() {
            // TODO: Implement 2FA setup
            alert('Two-factor authentication setup coming soon');
        }
    </script>
</body>
</html>
