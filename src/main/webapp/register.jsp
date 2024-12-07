<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Account - Creator Labs</title>
    
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

        .register-card {
            background: rgba(255, 255, 255, 0.03);
            border: 1px solid rgba(255, 255, 255, 0.1);
            border-radius: 16px;
            padding: 2rem;
            backdrop-filter: blur(10px);
            max-width: 400px;
            margin: 0 auto;
            position: relative;
            overflow: hidden;
        }

        .register-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 3px;
            background: linear-gradient(90deg, #8A6FE6, #4D55E8);
        }

        .register-header {
            text-align: center;
            margin-bottom: 1.5rem;
        }

        .register-title {
            font-size: 1.75rem;
            font-weight: 700;
            margin-bottom: 0.5rem;
            background: linear-gradient(135deg, #8A6FE6, #4D55E8);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
        }

        .register-subtitle {
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

        .password-requirements {
            font-size: 0.75rem;
            color: rgba(255, 255, 255, 0.5);
            margin-top: 0.25rem;
        }

        .requirement {
            display: flex;
            align-items: center;
            gap: 0.25rem;
            margin-bottom: 0.125rem;
        }

        .requirement i {
            font-size: 0.75rem;
        }

        .requirement.met {
            color: #8A6FE6;
        }

        .btn-register {
            background: linear-gradient(135deg, #8A6FE6, #4D55E8);
            border: none;
            border-radius: 8px;
            color: #fff;
            font-weight: 500;
            padding: 0.625rem;
            width: 100%;
            font-size: 0.875rem;
            margin-top: 1rem;
            transition: all 0.2s ease;
        }

        .btn-register:hover {
            transform: translateY(-1px);
            box-shadow: 0 4px 12px rgba(138, 111, 230, 0.3);
        }

        .terms {
            font-size: 0.75rem;
            color: rgba(255, 255, 255, 0.6);
            text-align: center;
            margin-top: 1rem;
        }

        .terms a {
            color: #8A6FE6;
            text-decoration: none;
        }

        .terms a:hover {
            text-decoration: underline;
        }

        .login-link {
            text-align: center;
            margin-top: 1.25rem;
            padding-top: 1.25rem;
            border-top: 1px solid rgba(255, 255, 255, 0.1);
        }

        .login-text {
            color: rgba(255, 255, 255, 0.6);
            text-decoration: none;
            font-size: 0.875rem;
            transition: color 0.2s ease;
        }

        .login-text a {
            color: #8A6FE6;
            text-decoration: none;
            font-weight: 500;
            margin-left: 0.25rem;
        }

        .login-text a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-5">
                <div class="register-card">
                    <div class="register-header">
                        <h2 class="register-title">Create Account</h2>
                        <p class="register-subtitle">Join Creator Labs and start building with AI</p>
                    </div>

                    <form id="registerForm">
                        <div class="form-group">
                            <label class="form-label" for="name">Full Name</label>
                            <input type="text" class="form-control" id="name" 
                                   placeholder="Enter your full name" 
                                   autocomplete="name" required>
                        </div>

                        <div class="form-group">
                            <label class="form-label" for="email">Email Address</label>
                            <input type="email" class="form-control" id="email" 
                                   placeholder="Enter your email" 
                                   autocomplete="email" required>
                        </div>

                        <div class="form-group">
                            <label class="form-label" for="password">Password</label>
                            <input type="password" class="form-control" id="password" 
                                   placeholder="Create a password" 
                                   autocomplete="new-password" required>
                            <i class="bi bi-eye password-toggle" onclick="togglePassword('password')"></i>
                            <div class="password-requirements" id="passwordRequirements">
                                <div class="requirement" data-requirement="length">
                                    <i class="bi bi-circle"></i>
                                    At least 8 characters
                                </div>
                                <div class="requirement" data-requirement="uppercase">
                                    <i class="bi bi-circle"></i>
                                    One uppercase letter
                                </div>
                                <div class="requirement" data-requirement="number">
                                    <i class="bi bi-circle"></i>
                                    One number
                                </div>
                                <div class="requirement" data-requirement="special">
                                    <i class="bi bi-circle"></i>
                                    One special character
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="form-label" for="confirmPassword">Confirm Password</label>
                            <input type="password" class="form-control" id="confirmPassword" 
                                   placeholder="Confirm your password" 
                                   autocomplete="new-password" required>
                            <i class="bi bi-eye password-toggle" onclick="togglePassword('confirmPassword')"></i>
                        </div>

                        <button type="submit" class="btn btn-register">
                            <i class="bi bi-person-plus me-2"></i>
                            Create Account
                        </button>

                        <p class="terms">
                            By creating an account, you agree to our 
                            <a href="#">Terms of Service</a> and 
                            <a href="#">Privacy Policy</a>
                        </p>

                        <div class="login-link">
                            <span class="login-text">
                                Already have an account?
                                <a href="login.jsp">Sign In</a>
                            </span>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script>
        function togglePassword(inputId) {
            const input = document.getElementById(inputId);
            const icon = input.nextElementSibling;
            
            if (input.type === 'password') {
                input.type = 'text';
                icon.classList.remove('bi-eye');
                icon.classList.add('bi-eye-slash');
            } else {
                input.type = 'password';
                icon.classList.remove('bi-eye-slash');
                icon.classList.add('bi-eye');
            }
        }

        function validatePassword(password) {
            const requirements = {
                length: password.length >= 8,
                uppercase: /[A-Z]/.test(password),
                number: /[0-9]/.test(password),
                special: /[!@#$%^&*]/.test(password)
            };

            Object.entries(requirements).forEach(([key, met]) => {
                const requirement = document.querySelector(`[data-requirement="${key}"]`);
                const icon = requirement.querySelector('i');
                
                if (met) {
                    requirement.classList.add('met');
                    icon.classList.remove('bi-circle');
                    icon.classList.add('bi-check-circle-fill');
                } else {
                    requirement.classList.remove('met');
                    icon.classList.remove('bi-check-circle-fill');
                    icon.classList.add('bi-circle');
                }
            });

            return Object.values(requirements).every(Boolean);
        }

        document.getElementById('password').addEventListener('input', function() {
            validatePassword(this.value);
        });

        document.getElementById('registerForm').addEventListener('submit', function(event) {
            event.preventDefault();
            
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            
            if (!validatePassword(password)) {
                alert('Please meet all password requirements');
                return;
            }
            
            if (password !== confirmPassword) {
                alert('Passwords do not match');
                return;
            }
            
            const button = event.target.querySelector('button');
            button.disabled = true;
            button.innerHTML = '<i class="bi bi-arrow-repeat spin me-2"></i>Creating Account...';
            
            // Simulate account creation
            setTimeout(() => {
                window.location.href = 'login.jsp';
            }, 1500);
        });
    </script>
</body>
</html>
