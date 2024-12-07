<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Test Page | AI Agency</title>
    
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- Custom Styles -->
    <link rel="stylesheet" href="incudes/static/style.css">
    
    <!-- Material Icons -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>
    <div class="container py-5">
        <h1 class="mb-5">Test Page</h1>

        <!-- Navigation Links Test -->
        <section class="mb-5">
            <h2 class="mb-4">Navigation Links Test</h2>
            <div class="card">
                <div class="card-body">
                    <h3 class="h5 mb-3">Main Navigation</h3>
                    <ul class="list-group mb-4">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <a href="#hero" class="link-test">Home</a>
                            <span class="status-badge status-testing">Testing</span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <a href="#agents" class="link-test">Agents</a>
                            <span class="status-badge status-testing">Testing</span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <a href="#services" class="link-test">Services</a>
                            <span class="status-badge status-testing">Testing</span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <a href="#categories" class="link-test">Apps</a>
                            <span class="status-badge status-testing">Testing</span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <a href="#case-studies" class="link-test">Case Studies</a>
                            <span class="status-badge status-testing">Testing</span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <a href="#contact" class="link-test">Contact</a>
                            <span class="status-badge status-testing">Testing</span>
                        </li>
                    </ul>

                    <h3 class="h5 mb-3">App Pages</h3>
                    <ul class="list-group">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <a href="incudes/pages/LinkedInContentGenerator.jsp" class="link-test">LinkedIn Content Generator</a>
                            <span class="status-badge status-testing">Testing</span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <a href="incudes/pages/StartUpLandingPageGenerator.jsp" class="link-test">Start-up Landing Page Generator</a>
                            <span class="status-badge status-testing">Testing</span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <a href="incudes/pages/BlogPostGenerator.jsp" class="link-test">Blog Post Generator</a>
                            <span class="status-badge status-testing">Testing</span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <a href="incudes/pages/EmailGenerator.jsp" class="link-test">Email Generator</a>
                            <span class="status-badge status-testing">Testing</span>
                        </li>
                    </ul>
                </div>
            </div>
        </section>

        <!-- Components Test -->
        <section class="mb-5">
            <h2 class="mb-4">Components Test</h2>
            
            <!-- Status Badges -->
            <div class="card mb-4">
                <div class="card-body">
                    <h3 class="h5 mb-3">Status Badges</h3>
                    <div class="d-flex gap-2 flex-wrap">
                        <span class="status-badge status-live">Live</span>
                        <span class="status-badge status-development">Development</span>
                        <span class="status-badge status-testing">Testing</span>
                        <span class="status-badge status-review">Review</span>
                        <span class="status-badge status-planning">Planning</span>
                        <span class="status-badge status-identified">Identified</span>
                    </div>
                </div>
            </div>

            <!-- Buttons -->
            <div class="card mb-4">
                <div class="card-body">
                    <h3 class="h5 mb-3">Buttons</h3>
                    <div class="d-flex gap-2 flex-wrap">
                        <button class="btn btn-primary">
                            <span class="material-icons">rocket_launch</span>
                            Primary Button
                        </button>
                        <button class="btn btn-secondary">
                            <span class="material-icons">info</span>
                            Secondary Button
                        </button>
                    </div>
                </div>
            </div>

            <!-- Icons -->
            <div class="card mb-4">
                <div class="card-body">
                    <h3 class="h5 mb-3">Icons</h3>
                    <div class="d-flex gap-3 flex-wrap">
                        <span class="material-icons icon-primary">star</span>
                        <span class="material-icons icon-success">check_circle</span>
                        <span class="material-icons icon-warning">warning</span>
                        <span class="material-icons icon-danger">error</span>
                        <span class="material-icons icon-info">info</span>
                        <span class="material-icons icon-muted">help</span>
                    </div>
                </div>
            </div>

            <!-- Form Elements -->
            <div class="card">
                <div class="card-body">
                    <h3 class="h5 mb-3">Form Elements</h3>
                    <form class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label">Text Input</label>
                            <input type="text" class="form-control" placeholder="Enter text">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Email Input</label>
                            <input type="email" class="form-control" placeholder="Enter email">
                        </div>
                        <div class="col-12">
                            <label class="form-label">Textarea</label>
                            <textarea class="form-control" rows="3" placeholder="Enter message"></textarea>
                        </div>
                    </form>
                </div>
            </div>
        </section>

        <!-- Test Results -->
        <section>
            <h2 class="mb-4">Test Results</h2>
            <div class="card">
                <div class="card-body">
                    <div id="testResults">
                        <!-- Test results will be displayed here -->
                    </div>
                </div>
            </div>
        </section>
    </div>

    <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Test Script -->
    <script>
        document.addEventListener('DOMContentLoaded', () => {
            const testResults = document.getElementById('testResults');
            const results = [];

            // Test navigation links
            document.querySelectorAll('.link-test').forEach(link => {
                const href = link.getAttribute('href');
                const linkText = link.textContent;
                try {
                    if (href && href !== '#') {
                        const status = href.startsWith('#') ? 'Valid anchor link' : 'Link exists';
                        results.push(`✅ ${linkText}: ${status}`);
                        link.closest('.list-group-item').querySelector('.status-badge').textContent = 'Passed';
                        link.closest('.list-group-item').querySelector('.status-badge').className = 'status-badge status-live';
                    } else {
                        results.push(`❌ ${linkText}: Invalid href`);
                        link.closest('.list-group-item').querySelector('.status-badge').textContent = 'Failed';
                        link.closest('.list-group-item').querySelector('.status-badge').className = 'status-badge status-danger';
                    }
                } catch (error) {
                    results.push(`❌ ${linkText}: ${error.message}`);
                }
            });

            // Display results
            testResults.innerHTML = `
                <h3 class="h5 mb-3">Link Test Results</h3>
                <ul class="list-group">
                    ${results.map(result => `
                        <li class="list-group-item">
                            ${result}
                        </li>
                    `).join('')}
                </ul>
            `;
        });
    </script>
</body>
</html>
