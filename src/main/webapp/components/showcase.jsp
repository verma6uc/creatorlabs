<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
.showcase-section {
    background: linear-gradient(180deg, #252837 0%, #1C1F2E 100%);
    padding: 100px 0;
    position: relative;
    overflow: hidden;
}

.showcase-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: 2rem;
    padding: 2rem 0;
}

.showcase-card {
    background: rgba(255, 255, 255, 0.05);
    border: 1px solid rgba(255, 255, 255, 0.1);
    border-radius: 16px;
    padding: 2rem;
    position: relative;
    overflow: hidden;
    backdrop-filter: blur(10px);
    transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.showcase-card::before {
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

.showcase-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
}

.showcase-card:hover::before {
    opacity: 1;
}

.case-icon {
    width: 64px;
    height: 64px;
    background: rgba(138, 111, 230, 0.1);
    border-radius: 16px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 1.5rem;
    position: relative;
    transition: transform 0.3s ease;
}

.case-icon::after {
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

.case-icon i {
    font-size: 28px;
    background: linear-gradient(135deg, #8A6FE6, #4D55E8);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
}

.showcase-card:hover .case-icon {
    transform: scale(1.1) rotate(5deg);
}

.results-list {
    margin-top: 1rem;
    padding-left: 1rem;
    border-left: 2px solid rgba(138, 111, 230, 0.3);
}

.results-list li {
    color: rgba(255, 255, 255, 0.8);
    margin-bottom: 0.5rem;
    font-size: 0.9rem;
    position: relative;
    padding-left: 1.5rem;
}

.results-list li::before {
    content: '✓';
    position: absolute;
    left: 0;
    color: #8A6FE6;
}

.category-tag {
    display: inline-block;
    padding: 0.25rem 0.75rem;
    border-radius: 1rem;
    font-size: 0.75rem;
    font-weight: 500;
    margin-bottom: 1rem;
    letter-spacing: 0.5px;
    text-transform: uppercase;
}

.tag-pharma {
    background: rgba(138, 111, 230, 0.1);
    color: #8A6FE6;
}

.tag-edtech {
    background: rgba(255, 107, 107, 0.1);
    color: #FF6B6B;
}

.tag-b2c {
    background: rgba(77, 171, 247, 0.1);
    color: #4DABF7;
}

.metrics {
    display: flex;
    gap: 1rem;
    margin-top: 1.5rem;
    padding-top: 1.5rem;
    border-top: 1px solid rgba(255, 255, 255, 0.1);
}

.metric {
    flex: 1;
    text-align: center;
}

.metric-value {
    font-size: 1.5rem;
    font-weight: 700;
    background: linear-gradient(135deg, #fff, #8A6FE6);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    margin-bottom: 0.25rem;
}

.metric-label {
    font-size: 0.8rem;
    color: rgba(255, 255, 255, 0.6);
}

@media (max-width: 768px) {
    .showcase-section {
        padding: 60px 0;
    }
    
    .showcase-grid {
        grid-template-columns: 1fr;
    }
}
</style>

<section id="showcase" class="showcase-section">
    <div class="container-fluid">
        <!-- Section Header -->
        <div class="row justify-content-center mb-5">
            <div class="col-lg-8 text-center">
                <h2 class="display-5 fw-bold mb-4" data-aos="fade-up">
                    Success <span style="background: linear-gradient(135deg, #8A6FE6, #4D55E8); -webkit-background-clip: text; -webkit-text-fill-color: transparent;">Stories</span>
                </h2>
                <p class="lead text-light-emphasis mb-0" data-aos="fade-up" data-aos-delay="100">
                    Real-world impact through AI-powered solutions
                </p>
            </div>
        </div>

        <!-- Showcase Grid -->
        <div class="showcase-grid">
            <!-- Pharma Case Study -->
            <div class="showcase-card" data-aos="fade-up">
                <span class="category-tag tag-pharma">Pharma Manufacturing</span>
                <div class="case-icon">
                    <i class="bi bi-building-check"></i>
                </div>
                <h3 style="color: #8A6FE6;">Leading Pharma Manufacturer</h3>
                <p style="color: rgba(255,255,255,0.8);">Comprehensive quality control and environmental monitoring system</p>
                <ul class="results-list list-unstyled">
                    <li>40% reduction in quality control time</li>
                    <li>99.9% monitoring accuracy</li>
                    <li>Zero compliance issues</li>
                    <li>100% audit trail coverage</li>
                </ul>
                <div class="metrics">
                    <div class="metric">
                        <div class="metric-value">40%</div>
                        <div class="metric-label">Time Saved</div>
                    </div>
                    <div class="metric">
                        <div class="metric-value">100%</div>
                        <div class="metric-label">Compliance</div>
                    </div>
                </div>
            </div>

            <!-- EdTech Case Study -->
            <div class="showcase-card" data-aos="fade-up" data-aos-delay="100">
                <span class="category-tag tag-edtech">EdTech</span>
                <div class="case-icon">
                    <i class="bi bi-mortarboard"></i>
                </div>
                <h3 style="color: #FF6B6B;">Global Education Platform</h3>
                <p style="color: rgba(255,255,255,0.8);">AI-powered learning management and analytics system</p>
                <ul class="results-list list-unstyled">
                    <li>85% student engagement increase</li>
                    <li>50% better learning outcomes</li>
                    <li>30% reduction in dropout rates</li>
                    <li>24/7 automated support</li>
                </ul>
                <div class="metrics">
                    <div class="metric">
                        <div class="metric-value">85%</div>
                        <div class="metric-label">Engagement</div>
                    </div>
                    <div class="metric">
                        <div class="metric-value">50%</div>
                        <div class="metric-label">Better Results</div>
                    </div>
                </div>
            </div>

            <!-- B2C Case Study -->
            <div class="showcase-card" data-aos="fade-up" data-aos-delay="200">
                <span class="category-tag tag-b2c">E-commerce</span>
                <div class="case-icon">
                    <i class="bi bi-cart-check"></i>
                </div>
                <h3 style="color: #4DABF7;">Major Retail Chain</h3>
                <p style="color: rgba(255,255,255,0.8);">Smart inventory and customer experience platform</p>
                <ul class="results-list list-unstyled">
                    <li>200% increase in sales</li>
                    <li>45% better inventory turnover</li>
                    <li>90% customer satisfaction</li>
                    <li>60% reduced support costs</li>
                </ul>
                <div class="metrics">
                    <div class="metric">
                        <div class="metric-value">200%</div>
                        <div class="metric-label">Sales Growth</div>
                    </div>
                    <div class="metric">
                        <div class="metric-value">90%</div>
                        <div class="metric-label">Satisfaction</div>
                    </div>
                </div>
            </div>
        </div>

        <!-- CTA -->
        <div class="text-center mt-5" data-aos="fade-up">
            <button class="btn btn-primary btn-lg px-5" onclick="startBuilding()">
                Start Your Success Story
                <i class="bi bi-arrow-right ms-2"></i>
            </button>
        </div>
    </div>
</section>
