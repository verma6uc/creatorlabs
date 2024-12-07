<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>500 - Server Error | CreatorLabs</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    <!-- Google Fonts - Inter -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <!-- Custom CSS -->
    <style>
        :root {
            --primary-gradient: linear-gradient(135deg, #8A6FE6 0%, #4D55E8 100%);
            --background-dark: #1C1F2E;
            --accent-color: #8A6FE6;
            --error-color: #FF6B6B;
        }

        body {
            background-color: var(--background-dark);
            color: #fff;
            font-family: 'Inter', sans-serif;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0;
            padding: 2rem;
        }

        .error-container {
            text-align: center;
            max-width: 600px;
        }

        .error-code {
            font-size: 8rem;
            font-weight: 700;
            color: var(--error-color);
            line-height: 1;
            margin-bottom: 1rem;
            text-shadow: 0 0 20px rgba(255, 107, 107, 0.3);
        }

        .error-title {
            font-size: 2rem;
            font-weight: 600;
            margin-bottom: 1rem;
        }

        .error-message {
            color: rgba(255, 255, 255, 0.8);
            margin-bottom: 2rem;
            font-size: 1.1rem;
        }

        .error-details {
            background: rgba(255, 107, 107, 0.1);
            border: 1px solid rgba(255, 107, 107, 0.2);
            border-radius: 8px;
            padding: 1rem;
            margin-bottom: 2rem;
            text-align: left;
        }

        .error-details pre {
            color: rgba(255, 255, 255, 0.8);
            margin: 0;
            white-space: pre-wrap;
            word-wrap: break-word;
        }

        .btn-primary {
            background: var(--primary-gradient);
            border: none;
            padding: 0.75rem 1.5rem;
            font-weight: 500;
            transition: all 0.3s ease;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 15px rgba(138, 111, 230, 0.3);
        }

        .btn-outline {
            border: 2px solid var(--border-light);
            color: #fff;
            background: transparent;
            padding: 0.75rem 1.5rem;
            font-weight: 500;
            transition: all 0.3s ease;
            margin-left: 1rem;
        }

        .btn-outline:hover {
            border-color: var(--accent-color);
            color: var(--accent-color);
        }

        .actions {
            display: flex;
            justify-content: center;
            gap: 1rem;
            margin-top: 2rem;
        }

        .support-info {
            margin-top: 2rem;
            padding-top: 2rem;
            border-top: 1px solid rgba(255, 255, 255, 0.1);
        }

        .support-info h3 {
            font-size: 1.2rem;
            margin-bottom: 1rem;
        }

        .support-item {
            display: flex;
            align-items: center;
            gap: 0.5rem;
            color: rgba(255, 255, 255, 0.8);
            margin-bottom: 0.5rem;
        }

        .support-item i {
            color: var(--accent-color);
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-code">500</div>
        <h1 class="error-title">Internal Server Error</h1>
        <p class="error-message">
            We're experiencing some technical difficulties.<br>
            Our team has been notified and is working on the issue.
        </p>
        
        <%
            if (request.getParameter("debug") != null && exception != null) {
        %>
        <div class="error-details">
            <pre><%= exception.getMessage() %></pre>
        </div>
        <%
            }
        %>

        <div class="actions">
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary">
                <i class="bi bi-house-door me-2"></i>
                Return Home
            </a>
            <button onclick="location.reload()" class="btn btn-outline">
                <i class="bi bi-arrow-clockwise me-2"></i>
                Try Again
            </button>
        </div>

        <div class="support-info">
            <h3>Need Help?</h3>
            <div class="support-item">
                <i class="bi bi-envelope"></i>
                <span>Contact support at support@creatorlabs.com</span>
            </div>
            <div class="support-item">
                <i class="bi bi-clock"></i>
                <span>Our team is available 24/7</span>
            </div>
            <div class="support-item">
                <i class="bi bi-chat-dots"></i>
                <span>Live chat support available</span>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Error Tracking Script -->
    <script>
        // Send error details to monitoring service
        window.addEventListener('load', function() {
            if (typeof errorDetails !== 'undefined') {
                // Implementation for error tracking service
                console.error('Error tracked:', errorDetails);
            }
        });
    </script>
</body>
</html>
