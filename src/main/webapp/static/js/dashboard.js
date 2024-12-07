// Dashboard JavaScript

class Dashboard {
    constructor() {
        this.initializeEventListeners();
        this.initializeCharts();
        this.setupWebSocket();
    }

    // Initialize Event Listeners
    initializeEventListeners() {
        // Profile Form
        const profileForm = document.getElementById('profileForm');
        if (profileForm) {
            profileForm.addEventListener('submit', (e) => this.handleProfileUpdate(e));
        }

        // Password Form
        const passwordForm = document.getElementById('passwordForm');
        if (passwordForm) {
            passwordForm.addEventListener('submit', (e) => this.handlePasswordChange(e));
        }

        // Delete Account Form
        const deleteAccountForm = document.getElementById('deleteAccountForm');
        if (deleteAccountForm) {
            deleteAccountForm.addEventListener('submit', (e) => this.handleAccountDeletion(e));
        }

        // New Project Button
        const newProjectBtn = document.querySelector('.btn-new-project');
        if (newProjectBtn) {
            newProjectBtn.addEventListener('click', () => this.showNewProjectModal());
        }

        // Project Cards
        document.querySelectorAll('.project-card').forEach(card => {
            card.addEventListener('click', (e) => this.handleProjectCardClick(e));
        });

        // Settings Navigation
        document.querySelectorAll('[data-settings-nav]').forEach(link => {
            link.addEventListener('click', (e) => this.handleSettingsNavigation(e));
        });
    }

    // Initialize Charts and Data Visualizations
    initializeCharts() {
        // Project Status Chart
        this.initializeProjectStatusChart();
        
        // Activity Timeline
        this.initializeActivityTimeline();
        
        // Progress Charts
        this.initializeProgressCharts();
    }

    // WebSocket Setup for Real-time Updates
    setupWebSocket() {
        try {
            const protocol = window.location.protocol === 'https:' ? 'wss:' : 'ws:';
            const wsUrl = `${protocol}//${window.location.host}/websocket/dashboard`;
            
            this.ws = new WebSocket(wsUrl);
            
            this.ws.onopen = () => {
                console.log('Dashboard WebSocket connected');
            };
            
            this.ws.onmessage = (event) => {
                this.handleWebSocketMessage(JSON.parse(event.data));
            };
            
            this.ws.onerror = (error) => {
                console.error('WebSocket error:', error);
            };
            
            this.ws.onclose = () => {
                console.log('WebSocket connection closed');
                // Attempt to reconnect after 5 seconds
                setTimeout(() => this.setupWebSocket(), 5000);
            };
        } catch (error) {
            console.error('WebSocket setup error:', error);
        }
    }

    // Handle Profile Update
    async handleProfileUpdate(event) {
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);

        try {
            const response = await fetch('/dashboard/settings/profile', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]').content
                },
                body: JSON.stringify(Object.fromEntries(formData))
            });

            const data = await response.json();
            if (data.success) {
                this.showNotification('Profile updated successfully', 'success');
            } else {
                this.showNotification(data.error, 'error');
            }
        } catch (error) {
            console.error('Profile update error:', error);
            this.showNotification('Failed to update profile', 'error');
        }
    }

    // Handle Password Change
    async handlePasswordChange(event) {
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);

        try {
            const response = await fetch('/dashboard/settings/password', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]').content
                },
                body: JSON.stringify(Object.fromEntries(formData))
            });

            const data = await response.json();
            if (data.success) {
                this.showNotification('Password changed successfully', 'success');
                form.reset();
            } else {
                this.showNotification(data.error, 'error');
            }
        } catch (error) {
            console.error('Password change error:', error);
            this.showNotification('Failed to change password', 'error');
        }
    }

    // Handle Account Deletion
    async handleAccountDeletion(event) {
        event.preventDefault();
        const form = event.target;
        const confirmInput = form.querySelector('input[name="confirmDelete"]');

        if (confirmInput.value !== 'DELETE') {
            this.showNotification('Please type DELETE to confirm', 'error');
            return;
        }

        try {
            const response = await fetch('/dashboard/settings/delete-account', {
                method: 'POST',
                headers: {
                    'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]').content
                }
            });

            const data = await response.json();
            if (data.success) {
                window.location.href = '/logout';
            } else {
                this.showNotification(data.error, 'error');
            }
        } catch (error) {
            console.error('Account deletion error:', error);
            this.showNotification('Failed to delete account', 'error');
        }
    }

    // Handle Project Card Click
    handleProjectCardClick(event) {
        const card = event.currentTarget;
        const projectId = card.dataset.projectId;
        window.location.href = `/dashboard/projects/${projectId}`;
    }

    // Show New Project Modal
    showNewProjectModal() {
        const modal = new bootstrap.Modal(document.getElementById('newProjectModal'));
        modal.show();
    }

    // Handle Settings Navigation
    handleSettingsNavigation(event) {
        event.preventDefault();
        const section = event.currentTarget.dataset.settingsNav;
        this.loadSettingsSection(section);
    }

    // Load Settings Section
    async loadSettingsSection(section) {
        try {
            const response = await fetch(`/dashboard/settings/${section}`);
            const data = await response.text();
            document.querySelector('.settings-content').innerHTML = data;
            this.initializeEventListeners();
        } catch (error) {
            console.error('Settings navigation error:', error);
            this.showNotification('Failed to load settings section', 'error');
        }
    }

    // Initialize Project Status Chart
    initializeProjectStatusChart() {
        const ctx = document.getElementById('projectStatusChart');
        if (!ctx) return;

        // Implementation depends on your chosen charting library
        // Example using Chart.js
        if (window.Chart) {
            new Chart(ctx, {
                // Chart configuration
            });
        }
    }

    // Initialize Activity Timeline
    initializeActivityTimeline() {
        const timeline = document.querySelector('.activity-timeline');
        if (!timeline) return;

        // Add any timeline-specific initialization
    }

    // Initialize Progress Charts
    initializeProgressCharts() {
        document.querySelectorAll('.progress-chart').forEach(chart => {
            // Initialize individual progress charts
        });
    }

    // Handle WebSocket Messages
    handleWebSocketMessage(message) {
        switch (message.type) {
            case 'project_update':
                this.updateProjectCard(message.data);
                break;
            case 'new_activity':
                this.addActivityItem(message.data);
                break;
            case 'stats_update':
                this.updateStats(message.data);
                break;
        }
    }

    // Update Project Card
    updateProjectCard(project) {
        const card = document.querySelector(`[data-project-id="${project.id}"]`);
        if (card) {
            // Update card content
            card.querySelector('.project-name').textContent = project.name;
            card.querySelector('.project-description').textContent = project.description;
            card.querySelector('.project-status').textContent = project.status;
        }
    }

    // Add Activity Item
    addActivityItem(activity) {
        const timeline = document.querySelector('.activity-list');
        if (timeline) {
            const item = this.createActivityItem(activity);
            timeline.insertBefore(item, timeline.firstChild);
            
            // Remove oldest item if more than 10
            if (timeline.children.length > 10) {
                timeline.removeChild(timeline.lastChild);
            }
        }
    }

    // Create Activity Item
    createActivityItem(activity) {
        const item = document.createElement('div');
        item.className = 'activity-item';
        item.innerHTML = `
            <div class="activity-icon">
                <i class="bi bi-${this.getActivityIcon(activity.action)}"></i>
            </div>
            <div>
                <p class="mb-1">${activity.message}</p>
                <small class="text-muted">${this.formatDate(activity.timestamp)}</small>
            </div>
        `;
        return item;
    }

    // Update Stats
    updateStats(stats) {
        Object.entries(stats).forEach(([key, value]) => {
            const element = document.querySelector(`[data-stat="${key}"]`);
            if (element) {
                element.textContent = value;
            }
        });
    }

    // Show Notification
    showNotification(message, type = 'info') {
        const toast = document.createElement('div');
        toast.className = `toast toast-${type} position-fixed bottom-0 end-0 m-3`;
        toast.innerHTML = `
            <div class="toast-header">
                <i class="bi bi-${this.getNotificationIcon(type)} me-2"></i>
                <strong class="me-auto">${type.charAt(0).toUpperCase() + type.slice(1)}</strong>
                <button type="button" class="btn-close" data-bs-dismiss="toast"></button>
            </div>
            <div class="toast-body">${message}</div>
        `;
        document.body.appendChild(toast);
        const bsToast = new bootstrap.Toast(toast);
        bsToast.show();
        
        // Remove toast after it's hidden
        toast.addEventListener('hidden.bs.toast', () => {
            document.body.removeChild(toast);
        });
    }

    // Helper Methods
    getActivityIcon(action) {
        const icons = {
            CREATE: 'plus',
            UPDATE: 'pencil',
            DELETE: 'trash',
            DEFAULT: 'arrow-right'
        };
        return icons[action] || icons.DEFAULT;
    }

    getNotificationIcon(type) {
        const icons = {
            success: 'check-circle',
            error: 'exclamation-circle',
            warning: 'exclamation-triangle',
            info: 'info-circle'
        };
        return icons[type] || icons.info;
    }

    formatDate(timestamp) {
        return new Date(timestamp).toLocaleString();
    }
}

// Initialize Dashboard
document.addEventListener('DOMContentLoaded', () => {
    window.dashboard = new Dashboard();
});
