/**
 * Dashboard functionality
 */
class Dashboard {
    constructor() {
        this.initializeWebSocket();
        this.setupEventListeners();
        this.setupTheme();
    }

    /**
     * Initialize WebSocket connection
     */
    initializeWebSocket() {
        const wsProtocol = window.location.protocol === 'https:' ? 'wss:' : 'ws:';
        const wsUrl = `${wsProtocol}//${window.location.host}${window.contextPath}/ws/dashboard`;
        
        this.socket = new WebSocket(wsUrl);
        
        this.socket.onopen = () => {
            console.log('WebSocket connection established');
            this.sendMessage({ type: 'init' });
        };

        this.socket.onmessage = (event) => {
            const data = JSON.parse(event.data);
            this.handleWebSocketMessage(data);
        };

        this.socket.onerror = (error) => {
            console.error('WebSocket error:', error);
            this.showNotification('Connection error', 'error');
        };

        this.socket.onclose = () => {
            console.log('WebSocket connection closed');
            // Attempt to reconnect after 5 seconds
            setTimeout(() => this.initializeWebSocket(), 5000);
        };
    }

    /**
     * Set up event listeners
     */
    setupEventListeners() {
        // Notifications
        const notificationsBtn = document.querySelector('.notifications-btn');
        if (notificationsBtn) {
            notificationsBtn.addEventListener('click', () => this.toggleNotifications());
        }

        // User menu
        const userMenu = document.querySelector('.user-menu');
        if (userMenu) {
            userMenu.addEventListener('click', () => this.toggleUserMenu());
        }

        // Search
        const searchInput = document.querySelector('.header-search input');
        if (searchInput) {
            searchInput.addEventListener('input', (e) => this.handleSearch(e.target.value));
        }

        // Quick action buttons
        document.querySelectorAll('.action-btn').forEach(btn => {
            btn.addEventListener('click', (e) => this.handleQuickAction(e.target.textContent.trim()));
        });

        // Theme toggle
        const themeSelect = document.getElementById('theme');
        if (themeSelect) {
            themeSelect.addEventListener('change', (e) => this.handleThemeChange(e.target.value));
        }

        // Settings forms
        const forms = document.querySelectorAll('.settings-form');
        forms.forEach(form => {
            form.addEventListener('change', () => this.markSettingsAsChanged());
        });

        // Handle page unload
        window.addEventListener('beforeunload', (e) => {
            if (this.hasUnsavedChanges) {
                e.preventDefault();
                e.returnValue = '';
            }
        });
    }

    /**
     * Set up theme handling
     */
    setupTheme() {
        const theme = localStorage.getItem('theme') || 'light';
        document.documentElement.setAttribute('data-theme', theme);
        
        const themeSelect = document.getElementById('theme');
        if (themeSelect) {
            themeSelect.value = theme;
        }
    }

    /**
     * Handle WebSocket messages
     */
    handleWebSocketMessage(data) {
        switch (data.type) {
            case 'notification':
                this.showNotification(data.message, data.level);
                break;
            case 'stats_update':
                this.updateStats(data.stats);
                break;
            case 'activity_update':
                this.updateActivity(data.activity);
                break;
            default:
                console.log('Unknown message type:', data.type);
        }
    }

    /**
     * Send WebSocket message
     */
    sendMessage(message) {
        if (this.socket.readyState === WebSocket.OPEN) {
            this.socket.send(JSON.stringify(message));
        }
    }

    /**
     * Show notification
     */
    showNotification(message, type = 'info') {
        const notification = document.createElement('div');
        notification.className = `notification notification-${type}`;
        notification.textContent = message;

        const container = document.querySelector('.notifications-container') || 
            this.createNotificationContainer();

        container.appendChild(notification);

        setTimeout(() => {
            notification.classList.add('fade-out');
            setTimeout(() => notification.remove(), 300);
        }, 5000);
    }

    /**
     * Create notification container
     */
    createNotificationContainer() {
        const container = document.createElement('div');
        container.className = 'notifications-container';
        document.body.appendChild(container);
        return container;
    }

    /**
     * Update statistics display
     */
    updateStats(stats) {
        Object.entries(stats).forEach(([key, value]) => {
            const element = document.querySelector(`[data-stat="${key}"]`);
            if (element) {
                element.textContent = value;
            }
        });
    }

    /**
     * Update activity feed
     */
    updateActivity(activity) {
        const activityList = document.querySelector('.activity-list');
        if (!activityList) return;

        const activityItem = document.createElement('div');
        activityItem.className = 'activity-item';
        activityItem.innerHTML = `
            <div class="activity-icon">${activity.icon}</div>
            <div class="activity-details">
                <p class="activity-text">${activity.text}</p>
                <p class="activity-time">${activity.time}</p>
            </div>
        `;

        activityList.insertBefore(activityItem, activityList.firstChild);

        // Remove oldest activity if more than 10
        if (activityList.children.length > 10) {
            activityList.lastChild.remove();
        }
    }

    /**
     * Handle search input
     */
    handleSearch(query) {
        // Debounce search requests
        clearTimeout(this.searchTimeout);
        this.searchTimeout = setTimeout(() => {
            this.sendMessage({
                type: 'search',
                query: query
            });
        }, 300);
    }

    /**
     * Handle quick action button clicks
     */
    handleQuickAction(action) {
        switch (action) {
            case 'New Project':
                window.location.href = `${window.contextPath}/projects/new`;
                break;
            case 'Invite Team Member':
                this.showInviteModal();
                break;
            case 'Generate Report':
                this.generateReport();
                break;
            case 'Configure Settings':
                window.location.href = `${window.contextPath}/dashboard/settings`;
                break;
        }
    }

    /**
     * Handle theme changes
     */
    handleThemeChange(theme) {
        document.documentElement.setAttribute('data-theme', theme);
        localStorage.setItem('theme', theme);
        this.sendMessage({
            type: 'update_preference',
            preference: 'theme',
            value: theme
        });
    }

    /**
     * Mark settings as changed
     */
    markSettingsAsChanged() {
        this.hasUnsavedChanges = true;
        const saveButton = document.querySelector('.settings-actions .btn-primary');
        if (saveButton) {
            saveButton.classList.add('btn-highlight');
        }
    }

    /**
     * Clean up resources
     */
    destroy() {
        if (this.socket) {
            this.socket.close();
        }
    }
}

// Initialize dashboard when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    window.dashboard = new Dashboard();
});
