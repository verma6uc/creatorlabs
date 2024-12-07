// Initialize when DOM is ready
document.addEventListener('DOMContentLoaded', function() {
    initializeAOS();
    initializeScrollEffects();
    initializeNavigation();
    initializeInteractions();
});

// Initialize AOS animations
function initializeAOS() {
    AOS.init({
        duration: 800,
        easing: 'ease-out',
        once: true,
        offset: 100,
        delay: 100
    });
}

// Initialize scroll effects
function initializeScrollEffects() {
    // Header scroll effect
    const header = document.querySelector('.main-header');
    window.addEventListener('scroll', () => {
        if (window.scrollY > 50) {
            header.classList.add('scrolled');
        } else {
            header.classList.remove('scrolled');
        }
    });

    // Scroll progress indicator
    window.addEventListener('scroll', () => {
        const winScroll = document.body.scrollTop || document.documentElement.scrollTop;
        const height = document.documentElement.scrollHeight - document.documentElement.clientHeight;
        const scrolled = (winScroll / height) * 100;
        document.documentElement.style.setProperty('--scroll', `${scrolled}%`);
    });

    // Reveal sections on scroll
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
}

// Initialize smooth navigation
function initializeNavigation() {
    // Smooth scroll for anchor links
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

    // Mobile menu
    const navbarToggler = document.querySelector('.navbar-toggler');
    const navbarCollapse = document.querySelector('.navbar-collapse');
    if (navbarToggler && navbarCollapse) {
        navbarToggler.addEventListener('click', function() {
            navbarCollapse.classList.toggle('show');
        });

        // Close mobile menu when clicking outside
        document.addEventListener('click', function(event) {
            if (!navbarCollapse.contains(event.target) && !navbarToggler.contains(event.target)) {
                navbarCollapse.classList.remove('show');
            }
        });
    }
}

// Initialize interactive elements
function initializeInteractions() {
    // Team cards hover effect
    const teamMembers = document.querySelectorAll('.team-member');
    teamMembers.forEach(member => {
        member.addEventListener('mouseenter', () => {
            teamMembers.forEach(m => {
                if (m !== member) {
                    m.style.transform = 'scale(0.95)';
                    m.style.opacity = '0.7';
                }
            });
        });

        member.addEventListener('mouseleave', () => {
            teamMembers.forEach(m => {
                m.style.transform = '';
                m.style.opacity = '';
            });
        });
    });

    // Process cards connection lines
    const processCards = document.querySelectorAll('.process-card');
    processCards.forEach((card, index) => {
        if (index < processCards.length - 1) {
            const line = card.querySelector('.connection-line');
            if (line) {
                card.addEventListener('mouseenter', () => {
                    line.style.opacity = '1';
                });
                card.addEventListener('mouseleave', () => {
                    line.style.opacity = '0.3';
                });
            }
        }
    });
}

// Button Actions
function startBuilding() {
    const button = event.target;
    button.innerHTML = '<i class="bi bi-hourglass-split me-2"></i>Loading...';
    button.disabled = true;

    setTimeout(() => {
        window.location.href = '/start-building';
    }, 500);
}

function watchDemo() {
    const modal = document.createElement('div');
    modal.className = 'modal fade';
    modal.id = 'demoModal';
    modal.innerHTML = `
        <div class="modal-dialog modal-lg modal-dialog-centered">
            <div class="modal-content bg-dark">
                <div class="modal-header border-0">
                    <h5 class="modal-title text-light">Watch Demo</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="ratio ratio-16x9">
                        <iframe src="https://www.youtube.com/embed/demo-video-id" 
                                title="Product Demo" 
                                allowfullscreen>
                        </iframe>
                    </div>
                </div>
            </div>
        </div>
    `;
    document.body.appendChild(modal);
    
    const modalInstance = new bootstrap.Modal(modal);
    modalInstance.show();
    
    modal.addEventListener('hidden.bs.modal', function () {
        modal.remove();
    });
}

function startProcess() {
    const button = event.target;
    button.innerHTML = '<i class="bi bi-arrow-repeat spin me-2"></i>Initializing...';
    button.disabled = true;

    setTimeout(() => {
        window.location.href = '/start-building';
    }, 500);
}
