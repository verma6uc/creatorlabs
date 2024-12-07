<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Password - Creator Labs</title>
    
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

        .reset-card {
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

        .reset-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 3px;
            background: linear-gradient(90deg, #8A6FE6, #4D55E8);
        }

        .reset-header {
            text-align: center;
            margin-bottom: 1.5rem;
        }

        .reset-title {
            font-size: 1.75rem;
            font-weight: 700;
            margin-bottom: 0.5rem;
            background: linear-gradient(135deg, #8A6FE6, #4D55E8);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
        }

        .reset-subtitle {
            color: rgba(255, 255, 255, 0.7);
            font-size: 0.875rem;
            margin-bottom: 0;
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

        .btn-reset {
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

        .btn-reset:hover {
            transform: translateY(-1px);
            box-shadow: 0 4px 12px rgba(138, 111, 230, 0.3);
        }

        .back-to-login {
            text-align: center;
            margin-top: 1.25rem;
            padding-top: 1.25rem;
            border-top: 1px solid rgba(255, 255, 255, 0.1);
        }

        .back-link {
            color: rgba(255, 255, 255, 0.6);
            text-decoration: none;
            font-size: 0.875rem;
            transition: color 0.2s ease;
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
        }

        .back-link:hover {
            color: #8A6FE6;
        }

        .success-message {
            display: none;
            text-align: center;
            padding: 1rem;
            background: rgba(138, 111, 230, 0.1);
            border-radius: 8px;
            margin-top: 1rem;
        }

        .success-icon {
            font-size: 2rem;
            color: #8A6FE6;
            margin-bottom: 0.5rem;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-5">
                <div class="reset-card">
                    <div class="reset-header">
                        <h2 class="reset-title">Reset Password</h2>
                        <p class="reset-subtitle">Enter your email to receive reset instructions</p>
                    </div>

                    <form id="resetForm">
                        <div class="mb-3">
                            <label class="form-label" for="email">Email Address</label>
                            <input type="email" class="form-control" id="email" 
                                   placeholder="Enter your email" 
                                   autocomplete="email" required>
                        </div>

                        <button type="submit" class="btn btn-reset">
                            <i class="bi bi-envelope me-2"></i>
                            Send Reset Link
                        </button>

                        <div class="success-message" id="successMessage">
                            <i class="bi bi-check-circle success-icon"></i>
                            <p class="mb-0">Reset instructions have been sent to your email.</p>
                            <small class="text-muted">Please check your inbox and spam folder.</small>
                        </div>

                        <div class="back-to-login">
                            <a href="login.jsp" class="back-link">
                                <i class="bi bi-arrow-left"></i>
                                Back to Login
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.getElementById('resetForm').addEventListener('submit', function(event) {
            event.preventDefault();
            
            const button = event.target.querySelector('button');
            const successMessage = document.getElementById('successMessage');
            
            // Disable button and show loading state
            button.disabled = true;
            button.innerHTML = '<i class="bi bi-arrow-repeat spin me-2"></i>Sending...';
            
            // Simulate API call
            setTimeout(() => {
                // Hide form and show success message
                button.style.display = 'none';
                successMessage.style.display = 'block';
                
                // Redirect to login after 3 seconds
                setTimeout(() => {
                    window.location.href = 'login.jsp';
                }, 3000);
            }, 1500);
        });
    </script>
</body>
</html>
