<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - CreatorLabs</title>
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
                    <li class="active"><a href="${pageContext.request.contextPath}/dashboard">Overview</a></li>
                    <li><a href="${pageContext.request.contextPath}/dashboard/projects">Projects</a></li>
                    <li><a href="${pageContext.request.contextPath}/dashboard/analytics">Analytics</a></li>
                    <li><a href="${pageContext.request.contextPath}/dashboard/settings">Settings</a></li>
                </ul>
            </nav>
        </aside>

        <!-- Main Content -->
        <main class="dashboard-main">
            <!-- Header -->
            <header class="dashboard-header">
                <div class="header-search">
                    <input type="search" placeholder="Search...">
                </div>
                <div class="header-actions">
                    <button class="notifications-btn">
                        <span class="icon">🔔</span>
                        <span class="badge">3</span>
                    </button>
                    <div class="user-menu">
                        <img src="${pageContext.request.contextPath}/static/images/avatar.png" alt="User avatar">
                        <span>${dashboardData.username}</span>
                    </div>
                </div>
            </header>

            <!-- Dashboard Content -->
            <div class="dashboard-content">
                <!-- Welcome Section -->
                <section class="welcome-section">
                    <h2>Welcome back, ${dashboardData.firstName}!</h2>
                    <p>Here's what's happening with your projects today.</p>
                </section>

                <!-- Stats Grid -->
                <div class="stats-grid">
                    <div class="stat-card">
                        <h3>Active Projects</h3>
                        <p class="stat-number">12</p>
                        <p class="stat-trend positive">↑ 3 from last week</p>
                    </div>
                    <div class="stat-card">
                        <h3>Completed Tasks</h3>
                        <p class="stat-number">48</p>
                        <p class="stat-trend positive">↑ 12 from last week</p>
                    </div>
                    <div class="stat-card">
                        <h3>Team Members</h3>
                        <p class="stat-number">8</p>
                        <p class="stat-trend neutral">No change</p>
                    </div>
                    <div class="stat-card">
                        <h3>Hours Logged</h3>
                        <p class="stat-number">164</p>
                        <p class="stat-trend negative">↓ 8 from last week</p>
                    </div>
                </div>

                <!-- Recent Activity -->
                <section class="recent-activity">
                    <h3>Recent Activity</h3>
                    <div class="activity-list">
                        <c:forEach var="activity" items="${dashboardData.activities}">
                            <div class="activity-item">
                                <div class="activity-icon">${activity.icon}</div>
                                <div class="activity-details">
                                    <p class="activity-text">${activity.text}</p>
                                    <p class="activity-time">${activity.time}</p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </section>

                <!-- Quick Actions -->
                <section class="quick-actions">
                    <h3>Quick Actions</h3>
                    <div class="actions-grid">
                        <button class="action-btn">
                            <span class="icon">📝</span>
                            New Project
                        </button>
                        <button class="action-btn">
                            <span class="icon">👥</span>
                            Invite Team Member
                        </button>
                        <button class="action-btn">
                            <span class="icon">📊</span>
                            Generate Report
                        </button>
                        <button class="action-btn">
                            <span class="icon">⚙️</span>
                            Configure Settings
                        </button>
                    </div>
                </section>
            </div>
        </main>
    </div>

    <!-- Scripts -->
    <script src="${pageContext.request.contextPath}/static/js/dashboard.js"></script>
    <script>
        // Initialize WebSocket connection
        const wsProtocol = window.location.protocol === 'https:' ? 'wss:' : 'ws:';
        const wsUrl = `${wsProtocol}//${window.location.host}${pageContext.request.contextPath}/ws/dashboard?token=${sessionScope.token}`;
        const socket = new WebSocket(wsUrl);

        socket.onmessage = function(event) {
            const data = JSON.parse(event.data);
            // Handle real-time updates
            console.log('Received update:', data);
        };

        socket.onerror = function(error) {
            console.error('WebSocket error:', error);
        };

        // Clean up on page unload
        window.onunload = function() {
            socket.close();
        };
    </script>
</body>
</html>
