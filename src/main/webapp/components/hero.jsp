<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
.hero-section {
    min-height: 100vh;
    display: flex;
    align-items: center;
    background: linear-gradient(180deg, #1C1F2E 0%, #252837 100%);
    padding: 100px 0;
    position: relative;
    overflow: hidden;
}

.neural-network {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    pointer-events: none;
    opacity: 0.6;
}

.node {
    position: absolute;
    width: 6px;
    height: 6px;
    background: #8A6FE6;
    border-radius: 50%;
    box-shadow: 0 0 10px rgba(138, 111, 230, 0.5);
    animation: pulse 3s infinite;
}

.connection {
    position: absolute;
    height: 2px;
    background: linear-gradient(90deg,
        rgba(138, 111, 230, 0),
        rgba(138, 111, 230, 0.3) 50%,
        rgba(138, 111, 230, 0)
    );
    transform-origin: left center;
    animation: glow 4s infinite;
}

.connection::before {
    content: '';
    position: absolute;
    width: 8px;
    height: 8px;
    background: rgba(138, 111, 230, 0.3);
    border-radius: 50%;
    top: 50%;
    transform: translateY(-50%);
    animation: moveAlong 4s linear infinite;
}

@keyframes pulse {
    0% { transform: scale(1); opacity: 0.3; }
    50% { transform: scale(1.5); opacity: 0.7; }
    100% { transform: scale(1); opacity: 0.3; }
}

@keyframes glow {
    0% { opacity: 0.1; }
    50% { opacity: 0.5; }
    100% { opacity: 0.1; }
}

@keyframes moveAlong {
    0% { left: 0%; }
    100% { left: 100%; }
}

.hero-content {
    position: relative;
    z-index: 2;
}

.hero-title {
    font-size: clamp(2.5rem, 5vw, 4rem);
    font-weight: 700;
    line-height: 1.2;
    margin-bottom: 1.5rem;
    position: relative;
}

.hero-title::after {
    content: '';
    position: absolute;
    bottom: -0.5rem;
    left: 0;
    width: 100px;
    height: 4px;
    background: linear-gradient(90deg, #8A6FE6, transparent);
}

.hero-subtitle {
    font-size: clamp(1rem, 2vw, 1.25rem);
    color: rgba(255, 255, 255, 0.8);
    margin-bottom: 2rem;
    max-width: 600px;
    position: relative;
}

.hero-subtitle::before {
    content: '';
    position: absolute;
    width: 30px;
    height: 30px;
    background: radial-gradient(circle, rgba(138, 111, 230, 0.2) 0%, transparent 70%);
    border-radius: 50%;
    left: -45px;
    top: 50%;
    transform: translateY(-50%);
}

.hero-cta {
    display: flex;
    gap: 1rem;
    margin-bottom: 3rem;
}

.btn-primary {
    background: linear-gradient(135deg, #8A6FE6, #4D55E8);
    border: none;
    padding: 1rem 2rem;
    border-radius: 12px;
    color: #fff;
    font-weight: 500;
    transition: all 0.3s ease;
    position: relative;
    overflow: hidden;
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

.btn-outline {
    background: transparent;
    border: 2px solid rgba(138, 111, 230, 0.5);
    padding: 1rem 2rem;
    border-radius: 12px;
    color: #fff;
    font-weight: 500;
    transition: all 0.3s ease;
}

.btn-outline:hover {
    background: rgba(138, 111, 230, 0.1);
    border-color: #8A6FE6;
    transform: translateY(-2px);
}

.hero-stats {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 1.5rem;
}

.stat-card {
    background: rgba(255, 255, 255, 0.03);
    border: 1px solid rgba(255, 255, 255, 0.1);
    border-radius: 16px;
    padding: 1.5rem;
    transition: all 0.3s ease;
    position: relative;
    overflow: hidden;
    backdrop-filter: blur(10px);
}

.stat-card::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 3px;
    background: linear-gradient(90deg, #8A6FE6, transparent);
    opacity: 0;
    transition: opacity 0.3s ease;
}

.stat-card:hover {
    transform: translateY(-5px);
}

.stat-card:hover::before {
    opacity: 1;
}

.stat-icon {
    font-size: 2.5rem;
    color: #8A6FE6;
    margin-bottom: 1rem;
}

.stat-number {
    font-size: 2.5rem;
    font-weight: 700;
    background: linear-gradient(135deg, #fff, #8A6FE6);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    margin-bottom: 0.5rem;
}

.stat-label {
    color: rgba(255, 255, 255, 0.8);
    font-size: 1rem;
    margin-bottom: 0.25rem;
}

.stat-sublabel {
    color: rgba(255, 255, 255, 0.5);
    font-size: 0.875rem;
}

@media (max-width: 768px) {
    .hero-section {
        padding: 60px 0;
        text-align: center;
    }
    
    .hero-title::after {
        left: 50%;
        transform: translateX(-50%);
    }
    
    .hero-subtitle::before {
        display: none;
    }
    
    .hero-cta {
        flex-direction: column;
        align-items: stretch;
    }
    
    .hero-stats {
        grid-template-columns: 1fr;
    }
}
</style>

<section id="hero" class="hero-section">
    <div class="neural-network" id="neuralNetwork"></div>
    
    <div class="container-fluid">
        <div class="row align-items-center">
            <div class="col-lg-6 hero-content" data-aos="fade-up">
                <h1 class="hero-title">
                    It Starts with an Idea
                </h1>
                <p class="hero-subtitle">
                    We’re in the early days of building a platform that helps turn your sparks of inspiration into working concepts. There’s still a lot to figure out, but we’re committed to growing and learning—using honest feedback, careful iteration, and a collection of small, evolving AI tools. Think of it as laying the groundwork, one piece at a time.
                </p>
                <div class="hero-cta">
                    <button class="btn btn-primary" onclick="window.location.href='login.jsp'">
                        <i class="bi bi-rocket-takeoff me-2"></i>
                        Start Exploring
                    </button>
                    <button class="btn btn-outline" onclick="watchDemo()">
                        <i class="bi bi-play-circle me-2"></i>
                        Learn More
                    </button>
                </div>
            </div>

            <div class="col-lg-6">
                <div class="hero-stats">
                    <div class="stat-card" data-aos="fade-up" data-aos-delay="100">
                        <div class="stat-icon">
                            <i class="bi bi-cpu"></i>
                        </div>
                        <div class="stat-number">3</div>
                        <div class="stat-label">AI Modules Under Development</div>
                        <div class="stat-sublabel">We’re testing ways to assist at every stage</div>
                    </div>
                    
                    <div class="stat-card" data-aos="fade-up" data-aos-delay="200">
                        <div class="stat-icon">
                            <i class="bi bi-lightbulb"></i>
                        </div>
                        <div class="stat-number">2</div>
                        <div class="stat-label">Concepts Refined from Feedback</div>
                        <div class="stat-sublabel">We’ve already adjusted plans based on early insights</div>
                    </div>
                    
                    <div class="stat-card" data-aos="fade-up" data-aos-delay="300">
                        <div class="stat-icon">
                            <i class="bi bi-hammer"></i>
                        </div>
                        <div class="stat-number">1</div>
                        <div class="stat-label">Prototype in Progress</div>
                        <div class="stat-sublabel">Our first tangible example is taking shape</div>
                    </div>
                    
                    <div class="stat-card" data-aos="fade-up" data-aos-delay="400">
                        <div class="stat-icon">
                            <i class="bi bi-compass"></i>
                        </div>
                        <div class="stat-number">∞</div>
                        <div class="stat-label">Room to Grow</div>
                        <div class="stat-sublabel">The journey is just beginning—we’re open to possibilities</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<script>
function createNeuralNetwork() {
    const network = document.getElementById('neuralNetwork');
    const nodes = 30;
    const connections = 40;
    
    // Create nodes
    for (let i = 0; i < nodes; i++) {
        const node = document.createElement('div');
        node.className = 'node';
        node.style.left = `${Math.random() * 100}%`;
        node.style.top = `${Math.random() * 100}%`;
        node.style.animationDelay = `${Math.random() * 3}s`;
        network.appendChild(node);
    }
    
    // Create connections
    for (let i = 0; i < connections; i++) {
        const connection = document.createElement('div');
        connection.className = 'connection';
        connection.style.left = `${Math.random() * 100}%`;
        connection.style.top = `${Math.random() * 100}%`;
        connection.style.width = `${Math.random() * 300 + 100}px`;
        connection.style.transform = `rotate(${Math.random() * 360}deg)`;
        connection.style.animationDelay = `${Math.random() * 4}s`;
        network.appendChild(connection);
    }
}

function watchDemo() {
    const modal = document.createElement('div');
    modal.className = 'modal fade';
    modal.id = 'demoModal';
    modal.innerHTML = `
        <div class="modal-dialog modal-lg modal-dialog-centered">
            <div class="modal-content bg-dark">
                <div class="modal-header border-0">
                    <h5 class="modal-title text-light">Learn More</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <p class="text-light">We’re still defining what a demo should look like. As we take our first steps, your patience and input mean a lot. Stick around, and we’ll share more as we learn.</p>
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

// Initialize neural network on page load
document.addEventListener('DOMContentLoaded', createNeuralNetwork);

// Recreate neural network on window resize
window.addEventListener('resize', function() {
    const network = document.getElementById('neuralNetwork');
    network.innerHTML = '';
    createNeuralNetwork();
});
</script>
