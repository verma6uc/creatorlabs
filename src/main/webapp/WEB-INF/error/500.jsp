<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>500 - Internal Server Error | CreatorLabs</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
    <style>
        .error-container {
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            text-align: center;
            padding: 2rem;
        }

        .error-code {
            font-size: 8rem;
            font-weight: 700;
            color: var(--danger-color);
            margin-bottom: 1rem;
            line-height: 1;
        }

        .error-message {
            font-size: 2rem;
            margin-bottom: 2rem;
            color: var(--text-color);
        }

        .error-description {
            font-size: 1.1rem;
            color: var(--text-muted);
            max-width: 600px;
            margin-bottom: 2rem;
        }

        .error-actions {
            display: flex;
            gap: 1rem;
        }

        .error-details {
            margin-top: 2rem;
            padding: 1rem;
            background-color: var(--card-bg);
            border-radius: 0.5rem;
            max-width: 600px;
            text-align: left;
        }

        .error-details pre {
            margin: 0;
            padding: 1rem;
            background-color: var(--bg-color);
            border-radius: 0.25rem;
            overflow-x: auto;
            font-size: 0.875rem;
        }

        @media (max-width: 768px) {
            .error-code {
                font-size: 6rem;
            }

            .error-message {
                font-size: 1.5rem;
            }
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-code">500</div>
        <h1 class="error-message">Internal Server Error</h1>
        <p class="error-description">
            Something went wrong on our end. Our team has been notified and is working to fix the issue.
            Please try again later or contact support if the problem persists.
        </p>
        <div class="error-actions">
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Go to Homepage</a>
            <button onclick="window.history.back()" class="btn btn-secondary">Go Back</button>
            <a href="${pageContext.request.contextPath}/support" class="btn btn-outline-primary">Contact Support</a>
        </div>
        
        <%-- Show error details in development environment --%>
        <% if (request.getAttribute("javax.servlet.error.exception") != null 
              && System.getProperty("app.environment", "development").equals("development")) { %>
            <div class="error-details">
                <h3>Error Details</h3>
                <pre>${pageContext.errorData.throwable}</pre>
            </div>
        <% } %>
    </div>

    <%-- Report error to monitoring service in production --%>
    <% if (System.getProperty("app.environment", "development").equals("production")) { %>
        <script>
            // Example error reporting
            window.addEventListener('load', function() {
                const errorData = {
                    url: window.location.href,
                    timestamp: new Date().toISOString(),
                    userAgent: navigator.userAgent,
                    errorCode: 500
                };
                
                // Send error data to monitoring service
                fetch('/api/error-reporting', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(errorData)
                }).catch(console.error);
            });
        </script>
    <% } %>
</body>
</html>
