<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
/* Team Section Styles */
.team-section {
    background: linear-gradient(180deg, #252837 0%, #1C1F2E 100%);
    padding: 100px 0;
    position: relative;
}

.team-grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 2rem;
    padding: 2rem 0;
}

.team-member {
    background: rgba(255, 255, 255, 0.05);
    border: 1px solid rgba(255, 255, 255, 0.1);
    border-radius: 16px;
    padding: 2rem;
    transition: all 0.3s ease;
    position: relative;
    overflow: hidden;
    backdrop-filter: blur(10px);
}

.team-member::before {
    content: '';
    position: absolute;
    top: 0;
    left: 20%;
    right: 20%;
    height: 4px;
    background: linear-gradient(90deg, #8A6FE6, #4D55E8);
    opacity: 0;
    transition: opacity 0.3s ease;
}

.team-member:hover {
    transform: translateY(-5px);
}

.team-member:hover::before {
    opacity: 1;
}

.member-icon {
    width: 64px;
    height: 64px;
    background: rgba(138, 111, 230, 0.1);
    border-radius: 16px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 1.5rem;
    transition: transform 0.3s ease;
    position: relative;
}

.member-icon::after {
    content: '';
    position: absolute;
    inset: -2px;
    border-radius: 16px;
    padding: 2px;
    background: linear-gradient(135deg, #8A6FE6, #4D55E8);
    -webkit-mask: linear-gradient(#fff 0 0) content-box, linear-gradient(#fff 0 0);
    mask: linear-gradient(#fff 0 0) content-box, linear-gradient(#fff 0 0);
    -webkit-mask-composite: xor;
    mask-composite: exclude;
}

.member-icon i {
    font-size: 28px;
    background: linear-gradient(135deg, #8A6FE6, #4D55E8);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
}

.member-quote {
    background: rgba(138, 111, 230, 0.05);
    border-left: 3px solid #8A6FE6;
    padding: 1rem;
    border-radius: 0 8px 8px 0;
    margin-top: 1.5rem;
    position: relative;
}

.member-quote::before {
    content: '"';
    position: absolute;
    top: 0.5rem;
    left: 1rem;
    font-size: 2rem;
    color: rgba(138, 111, 230, 0.3);
}

.floating {
    animation: float 6s ease-in-out infinite;
}

@keyframes float {
    0% { transform: translateY(0); }
    50% { transform: translateY(-10px); }
    100% { transform: translateY(0); }
}

@media (max-width: 992px) {
    .team-grid {
        grid-template-columns: repeat(2, 1fr);
    }
}

@media (max-width: 576px) {
    .team-section {
        padding: 60px 0;
    }
    
    .team-grid {
        grid-template-columns: 1fr;
        padding: 1rem;
    }
}
</style>

<section id="team" class="team-section">
    <div class="container-fluid">
        <!-- Section Header -->
        <div class="row justify-content-center mb-5">
            <div class="col-lg-8 text-center">
                <h2 class="display-5 fw-bold mb-4" data-aos="fade-up">
                    Meet Our <span style="background: linear-gradient(135deg, #8A6FE6, #4D55E8); -webkit-background-clip: text; -webkit-text-fill-color: transparent;">Asimov-Inspired AI Agents</span>
                </h2>
                <p class="lead text-light-emphasis mb-0" data-aos="fade-up" data-aos-delay="100">
                    Drawing on the spirit of Asimov’s universe, we’ve conceptualized a team of specialized AI agents—each playing a distinct role in guiding your product from idea to reality. Today, they’re outlines of what they’ll become; tomorrow, they’ll plan, research, design, build, integrate, and refine your vision into something extraordinary.
                </p>
            </div>
        </div>

        <!-- Team Grid -->
        <div class="team-grid">
            <!-- Planner (Strategic Architect) -->
            <div class="team-member floating" data-aos="fade-up" data-aos-delay="100">
                <div class="member-icon">
                    <i class="bi bi-diagram-3"></i>
                </div>
                <h3 style="color: #8A6FE6;">Seldon</h3>
                <p style="color: #4D55E8; font-weight: 500;">Strategic Planner</p>
                <p style="color: rgba(255,255,255,0.8);">
                    Inspired by Hari Seldon’s predictive genius, Seldon sets the course. Over time, he’ll map out milestones, ensure coherence, and help you navigate each critical decision point on your product journey.
                </p>
                <div class="member-quote">
                    <p style="color: rgba(255,255,255,0.9); font-style: italic;">
                        "A well-charted path keeps every goal within reach..."
                    </p>
                </div>
            </div>

            <!-- Research Agent -->
            <div class="team-member floating" data-aos="fade-up" data-aos-delay="150">
                <div class="member-icon">
                    <i class="bi bi-search"></i>
                </div>
                <h3 style="color: #8A6FE6;">Baley</h3>
                <p style="color: #4D55E8; font-weight: 500;">Research & Insights</p>
                <p style="color: rgba(255,255,255,0.8);">
                    Echoing Elijah Baley’s investigative prowess, Baley probes markets, competitors, and user needs. As he matures, he’ll distill vast information into actionable insights, helping you make informed, data-driven choices.
                </p>
                <div class="member-quote">
                    <p style="color: rgba(255,255,255,0.9); font-style: italic;">
                        "Knowledge transforms uncertainty into opportunity..."
                    </p>
                </div>
            </div>

            <!-- Frontend Builder -->
            <div class="team-member floating" data-aos="fade-up" data-aos-delay="200">
                <div class="member-icon">
                    <i class="bi bi-layout-text-window-reverse"></i>
                </div>
                <h3 style="color: #8A6FE6;">Dors</h3>
                <p style="color: #4D55E8; font-weight: 500;">Frontend Experience</p>
                <p style="color: rgba(255,255,255,0.8);">
                    Like Dors Venabili’s gentle guidance, Dors shapes friendly, intuitive interfaces. Eventually, she’ll craft clean layouts, fluid navigation, and a user experience that feels both natural and engaging.
                </p>
                <div class="member-quote">
                    <p style="color: rgba(255,255,255,0.9); font-style: italic;">
                        "A well-designed interface invites exploration..."
                    </p>
                </div>
            </div>

            <!-- SQL Query Writer / Backend Specialist -->
            <div class="team-member floating" data-aos="fade-up" data-aos-delay="250">
                <div class="member-icon">
                    <i class="bi bi-database"></i>
                </div>
                <h3 style="color: #8A6FE6;">Daneel</h3>
                <p style="color: #4D55E8; font-weight: 500;">Backend & Data Logic</p>
                <p style="color: rgba(255,255,255,0.8);">
                    Channeling R. Daneel Olivaw’s reliability, Daneel will handle data processes and queries. In time, he’ll ensure your product’s backend is efficient, stable, and always ready to serve up the right information.
                </p>
                <div class="member-quote">
                    <p style="color: rgba(255,255,255,0.9); font-style: italic;">
                        "Structured data lays the foundation for clarity..."
                    </p>
                </div>
            </div>

            <!-- Integration Expert -->
            <div class="team-member floating" data-aos="fade-up" data-aos-delay="300">
                <div class="member-icon">
                    <i class="bi bi-gear-wide-connected"></i>
                </div>
                <h3 style="color: #8A6FE6;">Giskard</h3>
                <p style="color: #4D55E8; font-weight: 500;">Integration & Cohesion</p>
                <p style="color: rgba(255,255,255,0.8);">
                    Reflecting R. Giskard’s subtle interplay, Giskard will orchestrate seamless communication between systems. As he evolves, expect effortless integrations, ensuring all parts work together in perfect harmony.
                </p>
                <div class="member-quote">
                    <p style="color: rgba(255,255,255,0.9); font-style: italic;">
                        "When every piece fits, the whole grows stronger..."
                    </p>
                </div>
            </div>
            
            <!-- Quality & Continuous Improvement -->
            <div class="team-member floating" data-aos="fade-up" data-aos-delay="350">
                <div class="member-icon">
                    <i class="bi bi-check2-circle"></i>
                </div>
                <h3 style="color: #8A6FE6;">Calvin</h3>
                <p style="color: #4D55E8; font-weight: 500;">Quality & Evolution</p>
                <p style="color: rgba(255,255,255,0.8);">
                    Inspired by Susan Calvin’s deep understanding of robotic minds, Calvin will focus on testing, refining, and iterating. Over time, she’ll assess feedback, suggest improvements, and ensure your product continuously evolves for the better.
                </p>
                <div class="member-quote">
                    <p style="color: rgba(255,255,255,0.9); font-style: italic;">
                        "Refinement is the engine of lasting progress..."
                    </p>
                </div>
            </div>
        </div>
    </div>
</section>
