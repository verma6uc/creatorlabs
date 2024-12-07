<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 - Page Not Found | CreatorLabs</title>
    
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
            background: var(--primary-gradient);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            line-height: 1;
            margin-bottom: 1rem;
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

        .error-image {
            max-width: 300px;
            margin-bottom: 2rem;
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

        .suggestions {
            margin-top: 2rem;
            text-align: left;
        }

        .suggestion-item {
            display: flex;
            align-items: center;
            gap: 0.5rem;
            color: rgba(255, 255, 255, 0.8);
            margin-bottom: 0.5rem;
        }

        .suggestion-item i {
            color: var(--accent-color);
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-code">404</div>
        <h1 class="error-title">Page Not Found</h1>
        <p class="error-message">
            The page you're looking for doesn't exist or has been moved.
        </p>
        
        <div class="suggestions mb-4">
            <div class="suggestion-item">
                <i class="bi bi-check-circle"></i>
                <span>Check if the URL was typed correctly</span>
            </div>
            <div class="suggestion-item">
                <i class="bi bi-check-circle"></i>
                <span>Make sure you have the necessary permissions</span>
            </div>
            <div class="suggestion-item">
                <i class="bi bi-check-circle"></i>
                <span>Try navigating from the homepage</span>
            </div>
        </div>

        <a href="${pageContext.request.contextPath}/" class="btn btn-primary">
            <i class="bi bi-house-door me-2"></i>
            Return Home
        </a>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
