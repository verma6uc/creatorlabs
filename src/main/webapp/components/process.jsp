<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
.process-section {
    position: relative;
    padding: 100px 0;
    background: #1C1F2E;
    overflow: hidden;
}

.process-card {
    background: rgba(255, 255, 255, 0.05);
    border: 1px solid rgba(255, 255, 255, 0.1);
    border-radius: 16px;
    position: relative;
    overflow: hidden;
    backdrop-filter: blur(10px);
    transition: all 0.4s ease;
    animation: float 6s ease-in-out infinite;
    display: flex;
    flex-direction: column;
    padding: 2rem;
}

.process-card:hover {
    transform: translateY(-5px) scale(1.02);
    box-shadow: 0 8px 20px rgba(138, 111, 230, 0.2);
}

.process-card::before {
    content: '';
    position: absolute;
    top: 0;
    left: 25%;
    right: 25%;
    height: 4px;
    background: linear-gradient(90deg, #8A6FE6, #4D55E8);
    opacity: 0;
    transition: opacity 0.4s ease;
}

.process-card:hover::before {
    opacity: 1;
}

.step-number {
    position: absolute;
    top: 1rem;
    right: 1rem;
    font-size: 3.5rem;
    font-weight: 700;
    color: rgba(138, 111, 230, 0.1);
    line-height: 1;
}

.process-icon {
    width: 64px;
    height: 64px;
    background: rgba(138, 111, 230, 0.1);
    border-radius: 16px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 1.5rem;
    position: relative;
    transition: transform 0.4s ease;
}

.process-card:hover .process-icon {
    transform: rotate(10deg) scale(1.1);
}

.process-card h3 {
    color: #8A6FE6;
    font-weight: 600;
    margin-bottom: 1rem;
    font-size: 1.2rem;
}

.process-card p {
    color: rgba(255,255,255,0.85);
    font-size: 1rem;
    line-height: 1.4;
}

.process-lists {
    margin-top: auto;
}

.process-lists h4 {
    color: #8A6FE6;
    font-size: 1.1rem;
    margin-top: 1.5rem;
    margin-bottom: 0.5rem;
    font-weight: 600;
}

.process-details {
    padding-left: 1rem;
    border-left: 2px solid rgba(138, 111, 230, 0.3);
    font-size: 0.95rem;
    margin-bottom: 1rem;
}

.process-details li {
    color: rgba(255, 255, 255, 0.8);
    margin-bottom: 0.5rem;
    font-size: 0.95rem;
    position: relative;
    padding-left: 1.5rem;
}

.process-details li::before {
    content: '•';
    position: absolute;
    left: 0;
    color: #8A6FE6;
}

@keyframes float {
    0% { transform: translateY(0); }
    50% { transform: translateY(-5px); }
    100% { transform: translateY(0); }
}
</style>

<section id="process" class="process-section">
    <div class="container-fluid">
        <!-- Section Header -->
        <div class="row justify-content-center mb-5">
            <div class="col-lg-8 text-center">
                <h2 class="display-5 fw-bold mb-4" data-aos="fade-up">
                    The 8-Step Journey: How & What You Gain at Every Stage
                </h2>
                <p class="lead text-light-emphasis mb-0" data-aos="fade-up" data-aos-delay="100">
                    Guided by Asimov-inspired AI agents, each phase is crafted to show you not just <em>how</em> the product evolves, but also <em>what</em> you gain and why it matters.
                </p>
            </div>
        </div>

        <!-- First 4 Steps -->
        <div class="row row-cols-1 row-cols-md-4 g-4 d-flex align-items-stretch" data-aos="fade-up" data-aos-delay="200">
            <!-- Step 1 -->
            <div class="col d-flex">
                <div class="process-card h-100">
                    <div class="step-number">01</div>
                    <div class="process-icon">
                        <i class="bi bi-chat-text"></i>
                    </div>
                    <h3>Initial Understanding</h3>
                    <p><strong>How:</strong> Seldon (strategist) and Baley (researcher) decipher your core idea in a few lines, framing initial goals and context.</p>
                    <p><strong>What You Get:</strong> A clarified starting vision—like a compass—for all subsequent steps.</p>
                    <p><strong>Why It Matters:</strong> Without a clear direction, building a meaningful product is guesswork. This step ensures purposeful beginnings.</p>
                    <div class="process-lists">
                        <h4>Key Outcomes:</h4>
                        <ul class="process-details list-unstyled">
                            <li>Defined product spark & direction</li>
                            <li>Initial market & user insights</li>
                            <li>Strategic framing to guide decisions</li>
                        </ul>
                    </div>
                </div>
            </div>

            <!-- Step 2 -->
            <div class="col d-flex">
                <div class="process-card h-100">
                    <div class="step-number">02</div>
                    <div class="process-icon">
                        <i class="bi bi-braces-asterisk"></i>
                    </div>
                    <h3>Memory Enhancement</h3>
                    <p><strong>How:</strong> Dors (frontend), Daneel (backend), Giskard (integration), and Calvin (quality) contribute their perspectives, layering more depth onto your concept’s memory.</p>
                    <p><strong>What You Get:</strong> A multi-faceted understanding of features, user flows, technical requirements, and improvement paths.</p>
                    <p><strong>Why It Matters:</strong> Holistic insight prevents blind spots. You’re now armed with a richer, more complete product narrative.</p>
                    <div class="process-lists">
                        <h4>Key Outcomes:</h4>
                        <ul class="process-details list-unstyled">
                            <li>Detailed feature evaluation</li>
                            <li>User behavior & journey mapping</li>
                            <li>Early integration & quality considerations</li>
                        </ul>
                    </div>
                </div>
            </div>

            <!-- Step 3 -->
            <div class="col d-flex">
                <div class="process-card h-100">
                    <div class="step-number">03</div>
                    <div class="process-icon">
                        <i class="bi bi-diagram-2"></i>
                    </div>
                    <h3>Blueprint Creation</h3>
                    <p><strong>How:</strong> Seldon shapes strategic structure, Dors refines navigation, and Daneel ensures logical data architecture. They craft a navigable blueprint to follow.</p>
                    <p><strong>What You Get:</strong> A clear roadmap—like architectural plans for a house—guiding the build phase with confidence.</p>
                    <p><strong>Why It Matters:</strong> Without a blueprint, you risk wasting time on guesswork. This ensures efficient development and coherent feature sets.</p>
                    <div class="process-lists">
                        <h4>Key Outcomes:</h4>
                        <ul class="process-details list-unstyled">
                            <li>Navigation & interaction design</li>
                            <li>Technical feature specifications</li>
                            <li>Aligned data and logic models</li>
                        </ul>
                    </div>
                </div>
            </div>

            <!-- Step 4 -->
            <div class="col d-flex">
                <div class="process-card h-100">
                    <div class="step-number">04</div>
                    <div class="process-icon">
                        <i class="bi bi-window-desktop"></i>
                    </div>
                    <h3>Visual PRD</h3>
                    <p><strong>How:</strong> Dors translates the blueprint into tangible visual elements—wireframes, layout specs, and user flows now have a visible shape.</p>
                    <p><strong>What You Get:</strong> A visual Product Requirement Document that developers and stakeholders can see, touch, and understand.</p>
                    <p><strong>Why It Matters:</strong> Visual clarity reduces misunderstandings and aligns everyone before coding begins.</p>
                    <div class="process-lists">
                        <h4>Key Outcomes:</h4>
                        <ul class="process-details list-unstyled">
                            <li>Wireframes & state diagrams</li>
                            <li>Clear user flow mapping</li>
                            <li>Component-level visual clarity</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <!-- Steps 5-8 -->
        <div class="row row-cols-1 row-cols-md-4 g-4 d-flex align-items-stretch mt-3" data-aos="fade-up" data-aos-delay="300">
            <!-- Step 5 -->
            <div class="col d-flex">
                <div class="process-card h-100">
                    <div class="step-number">05</div>
                    <div class="process-icon">
                        <i class="bi bi-code-square"></i>
                    </div>
                    <h3>Development</h3>
                    <p><strong>How:</strong> Daneel orchestrates data logic, Giskard ensures integration, while code is generated and components are assembled into a working prototype.</p>
                    <p><strong>What You Get:</strong> A functional skeleton—your product now has form and basic functionality, ready for refinement.</p>
                    <p><strong>Why It Matters:</strong> Turning plans into working code validates assumptions and reveals areas needing adjustment.</p>
                    <div class="process-lists">
                        <h4>Key Outcomes:</h4>
                        <ul class="process-details list-unstyled">
                            <li>Core features coded</li>
                            <li>Initial frontend-backend integration</li>
                            <li>Performance optimization starts</li>
                        </ul>
                    </div>
                </div>
            </div>

            <!-- Step 6 -->
            <div class="col d-flex">
                <div class="process-card h-100">
                    <div class="step-number">06</div>
                    <div class="process-icon">
                        <i class="bi bi-rocket-takeoff"></i>
                    </div>
                    <h3>Launch</h3>
                    <p><strong>How:</strong> Calvin checks quality and stability, ensuring you’re ready to deploy. Security, performance, and user experience are validated.</p>
                    <p><strong>What You Get:</strong> A launch-ready product, stable and secure, officially stepping onto the stage of the market.</p>
                    <p><strong>Why It Matters:</strong> A strong debut builds trust with users. Smooth performance and reliability foster early adoption and positive first impressions.</p>
                    <div class="process-lists">
                        <h4>Key Outcomes:</h4>
                        <ul class="process-details list-unstyled">
                            <li>Quality assured release</li>
                            <li>Security & performance checks complete</li>
                            <li>Confident public launch</li>
                        </ul>
                    </div>
                </div>
            </div>

            <!-- Step 7 -->
            <div class="col d-flex">
                <div class="process-card h-100">
                    <div class="step-number">07</div>
                    <div class="process-icon">
                        <i class="bi bi-graph-up-arrow"></i>
                    </div>
                    <h3>Post-Launch Feedback</h3>
                    <p><strong>How:</strong> Baley revisits market data, Calvin interprets user feedback and metrics. Together, they identify actionable refinements.</p>
                    <p><strong>What You Get:</strong> Insightful feedback loops and data-driven improvement plans.</p>
                    <p><strong>Why It Matters:</strong> Real-world usage reveals what actually resonates with users. Continuous improvement keeps your product relevant.</p>
                    <div class="process-lists">
                        <h4>Key Outcomes:</h4>
                        <ul class="process-details list-unstyled">
                            <li>User-informed feature tweaks</li>
                            <li>Analytics-driven decisions</li>
                            <li>Roadmap adjustments for growth</li>
                        </ul>
                    </div>
                </div>
            </div>

            <!-- Step 8 -->
            <div class="col d-flex">
                <div class="process-card h-100">
                    <div class="step-number">08</div>
                    <div class="process-icon">
                        <i class="bi bi-infinity"></i>
                    </div>
                    <h3>Growth & Evolution</h3>
                    <p><strong>How:</strong> Seldon guides strategic expansions, Dors updates interfaces, Daneel & Giskard integrate new capabilities, and Calvin ensures continuous quality.</p>
                    <p><strong>What You Get:</strong> A living product that adapts, scales, and continuously refines to meet evolving user needs.</p>
                    <p><strong>Why It Matters:</strong> Stagnation kills products. Ongoing evolution ensures long-term relevance and user satisfaction.</p>
                    <div class="process-lists">
                        <h4>Key Outcomes:</h4>
                        <ul class="process-details list-unstyled">
                            <li>Regular feature enhancements</li>
                            <li>Interface & UX improvements</li>
                            <li>Market-responsive growth strategy</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <!-- CTA -->
        <div class="text-center mt-5" data-aos="fade-up" data-aos-delay="400">
            <button class="btn btn-primary btn-lg px-5" onclick="startProcess()">
                Begin Your Transformation
                <i class="bi bi-arrow-right ms-2"></i>
            </button>
        </div>
    </div>
</section>

<script>
function startProcess() {
    alert("Your 8-step transformative journey begins now!");
}
</script>
