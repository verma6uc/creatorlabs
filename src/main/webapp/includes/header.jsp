<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Creator Labs - Company Onboarding</title>
    
    <!-- CSS Dependencies -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    
    <style>
        :root {
            --color-purple: #8A6FE6;
            --color-indigo: #4D55E8;
            --color-dark-gray: #1C1F2E;
            --color-darker-gray: #141927;
            --color-light-gray: #2A2F40;
        }
        
        body {
            font-family: 'Inter', sans-serif;
            background: linear-gradient(180deg, var(--color-dark-gray) 0%, #252837 100%);
            color: #fff;
            min-height: 100vh;
        }

        .dark-card {
            background: rgba(255, 255, 255, 0.03);
            border: 1px solid rgba(255, 255, 255, 0.1);
            border-radius: 16px;
            backdrop-filter: blur(10px);
        }

        .form-control {
            background: rgba(255, 255, 255, 0.05);
            border: 1px solid rgba(255, 255, 255, 0.1);
            color: #fff;
            border-radius: 8px;
            padding: 0.75rem 1rem;
        }

        .form-control:focus {
            background: rgba(255, 255, 255, 0.08);
            border-color: var(--color-purple);
            color: #fff;
            box-shadow: 0 0 0 2px rgba(138, 111, 230, 0.25);
        }

        .btn-primary {
            background: linear-gradient(135deg, var(--color-purple), var(--color-indigo));
            border: none;
            padding: 0.75rem 1.5rem;
            border-radius: 8px;
            font-weight: 500;
            transition: all 0.3s ease;
        }

        .btn-primary:hover {
            transform: translateY(-1px);
            box-shadow: 0 4px 12px rgba(138, 111, 230, 0.3);
        }

        .progress {
            height: 6px;
            background: rgba(255, 255, 255, 0.1);
            border-radius: 3px;
            overflow: hidden;
        }

        .progress-bar {
            background: linear-gradient(135deg, var(--color-purple), var(--color-indigo));
            transition: width 0.4s ease;
        }
    </style>
</head>
<body class="py-5">
