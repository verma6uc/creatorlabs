<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- SEO Meta Tags -->
    <title>Creator Labs | Build Products Through AI Conversations</title>
    <meta name="description" content="Transform your product ideas into reality through AI-powered conversations. Our AI team works together like interconnected Lego pieces to bring your vision to life.">
    <meta name="keywords" content="AI product development, conversational AI, prototype builder, MVP development, AI-powered development, product design">
    <meta name="author" content="Creator Labs Inc.">
    <meta name="robots" content="index, follow">
    
    <!-- CSS Dependencies -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/aos@2.3.4/dist/aos.css" rel="stylesheet">
    
    <!-- Critical CSS -->
    <style>
        :root {
            --primary-gradient: linear-gradient(135deg, #8A6FE6 0%, #4D55E8 100%);
            --background-dark: #1C1F2E;
            --background-darker: #252837;
            --accent-color: #8A6FE6;
            --border-light: rgba(255, 255, 255, 0.1);
            --bg-translucent: rgba(255, 255, 255, 0.05);
        }

        html {
            scroll-behavior: smooth;
            scroll-padding-top: 80px;
        }

        body {
            font-family: 'Inter', sans-serif;
            background-color: var(--background-dark);
            color: #fff;
            line-height: 1.6;
            overflow-x: hidden;
        }

        section {
            position: relative;
            overflow: hidden;
        }

        .gradient-text {
            background: var(--primary-gradient);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
        }

        .btn-primary {
            background: var(--primary-gradient);
            border: none;
            padding: 0.75rem 1.5rem;
            transition: all 0.3s ease;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 15px rgba(138, 111, 230, 0.3);
        }

        /* Smooth Scrolling */
        html {
            scroll-behavior: smooth;
        }

        /* Loading Animation */
        .loading {
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: var(--background-dark);
            z-index: 9999;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .loading::after {
            content: '';
            width: 50px;
            height: 50px;
            border: 3px solid var(--border-light);
            border-top-color: var(--accent-color);
            border-radius: 50%;
            animation: spin 1s linear infinite;
        }

        @keyframes spin {
            to { transform: rotate(360deg); }
        }

        /* Scrollbar Styling */
        ::-webkit-scrollbar {
            width: 10px;
        }

        ::-webkit-scrollbar-track {
            background: var(--background-dark);
        }

        ::-webkit-scrollbar-thumb {
            background: var(--accent-color);
            border-radius: 5px;
        }

        ::-webkit-scrollbar-thumb:hover {
            background: #4D55E8;
        }
    </style>
</head>
<body class="bg-dark">
    <!-- Loading Screen -->
    <div class="loading" id="loading"></div>

    <!-- Header -->
    <jsp:include page="components/header.jsp" />

    <main class="main-content">
        <!-- Hero Section -->
        <jsp:include page="components/hero.jsp" />

        <!-- Team Section -->
        <jsp:include page="components/team.jsp" />

        <!-- Process Section -->
        <jsp:include page="components/process.jsp" />

        <!-- Products Section -->
        <jsp:include page="components/products.jsp" />

        <!-- Showcase Section -->
        <jsp:include page="components/showcase.jsp" />

        <!-- Contact Section -->
        <jsp:include page="components/contact.jsp" />
    </main>

    <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/aos@2.3.4/dist/aos.js"></script>
    <script>
        // Remove loading screen when page is loaded
        window.addEventListener('load', function() {
            document.getElementById('loading').style.display = 'none';
        });

        // Initialize AOS
        document.addEventListener('DOMContentLoaded', function() {
            AOS.init({
                duration: 800,
                easing: 'ease-out',
                once: true,
                offset: 100,
                delay: 100
            });

            // Scroll Progress Indicator
            window.addEventListener('scroll', () => {
                const winScroll = document.body.scrollTop || document.documentElement.scrollTop;
                const height = document.documentElement.scrollHeight - document.documentElement.clientHeight;
                const scrolled = (winScroll / height) * 100;
                document.documentElement.style.setProperty('--scroll', `${scrolled}%`);
            });

            // Section Visibility
            const observer = new IntersectionObserver((entries) => {
                entries.forEach(entry => {
                    if (entry.isIntersecting) {
                        entry.target.classList.add('visible');
                    }
                });
            }, { threshold: 0.1 });

            document.querySelectorAll('section').forEach(section => {
                observer.observe(section);
            });
        });

        // Smooth Scroll for Anchor Links
        document.querySelectorAll('a[href^="#"]').forEach(anchor => {
            anchor.addEventListener('click', function(e) {
                e.preventDefault();
                const target = document.querySelector(this.getAttribute('href'));
                if (target) {
                    target.scrollIntoView({
                        behavior: 'smooth',
                        block: 'start'
                    });
                }
            });
        });
    </script>
    <script src="static/js/main.js"></script>
</body>
</html>
