<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Creator Labs</title>
    
    <!-- CSS Dependencies -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    
    <style>
        body {
            min-height: 100vh;
            background: linear-gradient(180deg, #252837 0%, #1C1F2E 100%);
            display: flex;
            align-items: center;
            font-family: 'Inter', sans-serif;
            color: #fff;
            padding: 20px;
        }

        .login-card {
            background: rgba(255, 255, 255, 0.03);
            border: 1px solid rgba(255, 255, 255, 0.1);
            border-radius: 16px;
            padding: 2rem;
            backdrop-filter: blur(10px);
            max-width: 360px;
            margin: 0 auto;
            position: relative;
            overflow: hidden;
        }

        .login-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 3px;
            background: linear-gradient(90deg, #8A6FE6, #4D55E8);
        }

        .login-header {
            text-align: center;
            margin-bottom: 1.5rem;
        }

        .login-title {
            font-size: 1.75rem;
            font-weight: 700;
            margin-bottom: 0.5rem;
            background: linear-gradient(135deg, #8A6FE6, #4D55E8);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
        }

        .login-subtitle {
            color: rgba(255, 255, 255, 0.7);
            font-size: 0.875rem;
            margin-bottom: 0;
        }

        .form-group {
            margin-bottom: 1rem;
            position: relative;
        }

        .form-label {
            color: rgba(255, 255, 255, 0.8);
            font-size: 0.875rem;
            font-weight: 500;
            margin-bottom: 0.25rem;
        }

        .form-control {
            background: rgba(255, 255, 255, 0.05);
            border: 1px solid rgba(255, 255, 255, 0.1);
            border-radius: 8px;
            color: #fff;
            padding: 0.625rem 0.875rem;
            font-size: 0.875rem;
            transition: all 0.2s ease;
        }

        .form-control:focus {
            background: rgba(255, 255, 255, 0.08);
            border-color: #8A6FE6;
            box-shadow: 0 0 0 2px rgba(138, 111, 230, 0.25);
        }

        .password-toggle {
            position: absolute;
            right: 0.75rem;
            top: 2.125rem;
            color: rgba(255, 255, 255, 0.5);
            cursor: pointer;
            transition: color 0.2s ease;
            font-size: 0.875rem;
        }

        .password-toggle:hover {
            color: #8A6FE6;
        }

        .form-check {
            margin: 0.75rem 0;
        }

        .form-check-label {
            font-size: 0.8125rem;
            color: rgba(255, 255, 255, 0.7);
            cursor: pointer;
        }

        .form-check-input {
            width: 1rem;
            height: 1rem;
            margin-top: 0.2rem;
            cursor: pointer;
        }

        .btn-login {
            background: linear-gradient(135deg, #8A6FE6, #4D55E8);
            border: none;
            border-radius: 8px;
            color: #fff;
            font-weight: 500;
            padding: 0.625rem;
            width: 100%;
            font-size: 0.875rem;
            transition: all 0.2s ease;
        }

        .btn-login:hover {
            transform: translateY(-1px);
            box-shadow: 0 4px 12px rgba(138, 111, 230, 0.3);
        }

        .login-footer {
            text-align: center;
            margin-top: 1.25rem;
            padding-top: 1.25rem;
            border-top: 1px solid rgba(255, 255, 255, 0.1);
        }

        .social-login {
            display: flex;
            gap: 0.75rem;
            justify-content: center;
            margin-bottom: 1rem;
        }

        .social-btn {
            width: 32px;
            height: 32px;
            background: rgba(255, 255, 255, 0.05);
            border: 1px solid rgba(255, 255, 255, 0.1);
            border-radius: 8px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: rgba(255, 255, 255, 0.8);
            font-size: 0.875rem;
            transition: all 0.2s ease;
        }

        .social-btn:hover {
            background: rgba(255, 255, 255, 0.1);
            transform: translateY(-1px);
            color: #fff;
        }

        .login-links {
            display: flex;
            justify-content: space-between;
            margin-top: 0.75rem;
        }

        .login-link {
            color: rgba(255, 255, 255, 0.6);
            text-decoration: none;
            font-size: 0.75rem;
            transition: color 0.2s ease;
        }

        .login-link:hover {
            color: #8A6FE6;
        }

        .back-to-home {
            position: fixed;
            top: 1rem;
            left: 1rem;
            color: rgba(255, 255, 255, 0.6);
            text-decoration: none;
            font-size: 0.875rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
            transition: color 0.2s ease;
        }

        .back-to-home:hover {
            color: #fff;
        }
    </style>
</head>
<body>
    <a href="index.jsp" class="back-to-home">
        <i class="bi bi-arrow-left"></i>
        Back to Home
    </a>

    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-5">
                <div class="login-card">
                    <div class="login-header">
                        <h2 class="login-title">Welcome Back</h2>
                        <p class="login-subtitle">Sign in to continue building with AI</p>
                    </div>

                    <form id="loginForm">
                        <div class="form-group">
                            <label class="form-label" for="email">Email Address</label>
                            <input type="email" class="form-control" id="email" 
                                   placeholder="Enter your email" 
                                   autocomplete="email" required>
                        </div>

                        <div class="form-group">
                            <label class="form-label" for="password">Password</label>
                            <input type="password" class="form-control" id="password" 
                                   placeholder="Enter your password" 
                                   autocomplete="current-password" required>
                            <i class="bi bi-eye password-toggle" onclick="togglePassword()"></i>
                        </div>

                        <div class="form-check">
                            <input type="checkbox" class="form-check-input" id="rememberMe">
                            <label class="form-check-label" for="rememberMe">Remember me</label>
                        </div>

                        <button type="submit" class="btn btn-login">
                            <i class="bi bi-box-arrow-in-right me-2"></i>
                            Sign In
                        </button>

                        <div class="login-footer">
                            <div class="social-login">
                                <a href="#" class="social-btn" title="Sign in with Google">
                                    <i class="bi bi-google"></i>
                                </a>
                                <a href="#" class="social-btn" title="Sign in with GitHub">
                                    <i class="bi bi-github"></i>
                                </a>
                                <a href="#" class="social-btn" title="Sign in with LinkedIn">
                                    <i class="bi bi-linkedin"></i>
                                </a>
                            </div>

                            <div class="login-links">
                                <a href="forgot-password.jsp" class="login-link">Forgot Password?</a>
                                <a href="register.jsp" class="login-link">Create Account</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script>
        function togglePassword() {
            const passwordInput = document.getElementById('password');
            const toggleIcon = document.querySelector('.password-toggle');
            
            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                toggleIcon.classList.remove('bi-eye');
                toggleIcon.classList.add('bi-eye-slash');
            } else {
                passwordInput.type = 'password';
                toggleIcon.classList.remove('bi-eye-slash');
                toggleIcon.classList.add('bi-eye');
            }
        }

        document.getElementById('loginForm').addEventListener('submit', function(event) {
            event.preventDefault();
            
            const button = event.target.querySelector('button');
            button.disabled = true;
            button.innerHTML = '<i class="bi bi-arrow-repeat spin me-2"></i>Signing In...';
            
            // Simulate login
            setTimeout(() => {
                window.location.href = '/dashboard';
            }, 1500);
        });
    </script>
</body>
</html>
