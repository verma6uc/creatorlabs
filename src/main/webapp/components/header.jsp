<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
.main-header {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    z-index: 1000;
    padding: 1rem 0;
    background: rgba(28, 31, 46, 0.95);
    backdrop-filter: blur(10px);
    transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.main-header.scrolled {
    padding: 0.75rem 0;
    background: rgba(28, 31, 46, 0.98);
    box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
}

.main-header::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    height: 1px;
    background: linear-gradient(90deg, 
        transparent,
        rgba(138, 111, 230, 0.2),
        rgba(77, 85, 232, 0.2),
        rgba(138, 111, 230, 0.2),
        transparent
    );
}

.navbar {
    padding: 0;
}

.navbar-brand {
    display: flex;
    align-items: center;
    gap: 0.75rem;
    text-decoration: none;
    position: relative;
}

.brand-icon {
    width: 40px;
    height: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
    background: rgba(138, 111, 230, 0.1);
    border-radius: 12px;
    transition: all 0.3s ease;
    position: relative;
}

.brand-icon::after {
    content: '';
    position: absolute;
    inset: -2px;
    border-radius: 12px;
    padding: 2px;
    background: linear-gradient(135deg, #8A6FE6, #4D55E8);
    -webkit-mask: linear-gradient(#fff 0 0) content-box, linear-gradient(#fff 0 0);
    mask: linear-gradient(#fff 0 0) content-box, linear-gradient(#fff 0 0);
    -webkit-mask-composite: xor;
    mask-composite: exclude;
    opacity: 0.5;
    transition: opacity 0.3s ease;
}

.navbar-brand:hover .brand-icon::after {
    opacity: 1;
}

.brand-icon i {
    font-size: 24px;
    background: linear-gradient(135deg, #8A6FE6, #4D55E8);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
}

.brand-text {
    font-size: 1.5rem;
    font-weight: 700;
    color: #fff;
    transition: color 0.3s ease;
}

.brand-text span {
    background: linear-gradient(135deg, #8A6FE6, #4D55E8);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
}

.navbar-nav {
    gap: 2rem;
}

.nav-link {
    color: rgba(255, 255, 255, 0.8) !important;
    font-weight: 500;
    font-size: 0.95rem;
    padding: 0.5rem 0 !important;
    position: relative;
    transition: all 0.3s ease;
}

.nav-link::before {
    content: '';
    position: absolute;
    bottom: 0;
    left: 50%;
    width: 0;
    height: 2px;
    background: linear-gradient(90deg, #8A6FE6, #4D55E8);
    transition: all 0.3s ease;
    transform: translateX(-50%);
    border-radius: 2px;
    opacity: 0;
}

.nav-link:hover {
    color: #fff !important;
}

.nav-link:hover::before {
    width: 100%;
    opacity: 1;
}

.navbar-toggler {
    border: none;
    padding: 0.5rem;
    position: relative;
    width: 48px;
    height: 48px;
    border-radius: 12px;
    background: rgba(138, 111, 230, 0.1);
    transition: all 0.3s ease;
}

.navbar-toggler:hover {
    background: rgba(138, 111, 230, 0.2);
}

.navbar-toggler:focus {
    box-shadow: none;
}

.navbar-toggler-icon {
    width: 24px;
    height: 2px;
    background: #fff;
    position: relative;
    transition: all 0.3s ease;
}

.navbar-toggler-icon::before,
.navbar-toggler-icon::after {
    content: '';
    position: absolute;
    width: 24px;
    height: 2px;
    background: #fff;
    transition: all 0.3s ease;
    left: 0;
}

.navbar-toggler-icon::before {
    top: -8px;
}

.navbar-toggler-icon::after {
    bottom: -8px;
}

.navbar-toggler[aria-expanded="true"] .navbar-toggler-icon {
    background: transparent;
}

.navbar-toggler[aria-expanded="true"] .navbar-toggler-icon::before {
    transform: rotate(45deg);
    top: 0;
}

.navbar-toggler[aria-expanded="true"] .navbar-toggler-icon::after {
    transform: rotate(-45deg);
    bottom: 0;
}

.btn-primary {
    background: linear-gradient(135deg, #8A6FE6, #4D55E8);
    border: none;
    border-radius: 12px;
    padding: 0.75rem 1.5rem;
    font-weight: 500;
    font-size: 0.95rem;
    color: #fff;
    position: relative;
    overflow: hidden;
    transition: all 0.3s ease;
}

.btn-primary::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: linear-gradient(rgba(255,255,255,0.1), transparent);
    transform: translateY(-100%);
    transition: transform 0.3s ease;
}

.btn-primary:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 15px rgba(138, 111, 230, 0.3);
}

.btn-primary:hover::before {
    transform: translateY(0);
}

@media (max-width: 991px) {
    .navbar-collapse {
        background: rgba(37, 40, 55, 0.98);
        backdrop-filter: blur(10px);
        margin-top: 1rem;
        padding: 1.5rem;
        border-radius: 16px;
        border: 1px solid rgba(138, 111, 230, 0.1);
        box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
    }

    .navbar-nav {
        gap: 0.5rem;
    }

    .nav-link {
        padding: 0.75rem 1rem !important;
        border-radius: 8px;
    }

    .nav-link:hover {
        background: rgba(138, 111, 230, 0.1);
    }

    .nav-link::before {
        display: none;
    }

    .btn-primary {
        margin-top: 1rem;
        width: 100%;
    }
}
</style>

<header class="main-header">
    <div class="container-fluid">
        <nav class="navbar navbar-expand-lg">
            <a class="navbar-brand" href="#hero">
                <div class="brand-icon">
                    <i class="bi bi-cpu"></i>
                </div>
                <div class="brand-text">Creator <span>Labs</span></div>
            </a>

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarMain">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarMain">
                <ul class="navbar-nav mx-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="#hero">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#team">Our AI Team</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#process">How It Works</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#products">Solutions</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#showcase">Showcase</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#contact">Contact</a>
                    </li>
                </ul>

                <button class="btn btn-primary" onclick="window.location.href='login.jsp'">
                    <i class="bi bi-rocket-takeoff me-2"></i>
                    Start Building
                </button>
            </div>
        </nav>
    </div>
</header>

<script>
document.addEventListener('scroll', function() {
    const header = document.querySelector('.main-header');
    if (window.scrollY > 50) {
        header.classList.add('scrolled');
    } else {
        header.classList.remove('scrolled');
    }
});

// Smooth scroll for navigation links
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function(e) {
        e.preventDefault();
        const target = document.querySelector(this.getAttribute('href'));
        if (target) {
            const headerOffset = 80;
            const elementPosition = target.getBoundingClientRect().top;
            const offsetPosition = elementPosition + window.pageYOffset - headerOffset;

            window.scrollTo({
                top: offsetPosition,
                behavior: 'smooth'
            });

            // Close mobile menu if open
            const navbarCollapse = document.querySelector('.navbar-collapse');
            if (navbarCollapse.classList.contains('show')) {
                navbarCollapse.classList.remove('show');
            }
        }
    });
});
</script>
