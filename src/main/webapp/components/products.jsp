<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
.products-section {
    background: linear-gradient(180deg, #1C1F2E 0%, #252837 100%);
    padding: 100px 0;
    position: relative;
    overflow: hidden;
}

.products-section::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 1px;
    background: linear-gradient(90deg, transparent, #8A6FE6, transparent);
}

.product-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: 2rem;
    padding: 2rem 0;
}

.product-card {
    background: rgba(255, 255, 255, 0.05);
    border: 1px solid rgba(255, 255, 255, 0.1);
    border-radius: 16px;
    padding: 2rem;
    position: relative;
    overflow: hidden;
    backdrop-filter: blur(10px);
    transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.product-card::before {
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

.product-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
}

.product-card:hover::before {
    opacity: 1;
}

.product-icon {
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

.product-icon::after {
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

.product-icon i {
    font-size: 28px;
    background: linear-gradient(135deg, #8A6FE6, #4D55E8);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
}

.product-card:hover .product-icon {
    transform: scale(1.1) rotate(5deg);
}

.feature-list {
    margin-top: 1rem;
    padding-left: 1rem;
    border-left: 2px solid rgba(138, 111, 230, 0.3);
}

.feature-list li {
    color: rgba(255, 255, 255, 0.8);
    margin-bottom: 0.5rem;
    font-size: 0.9rem;
    position: relative;
    padding-left: 1.5rem;
}

.feature-list li::before {
    content: '•';
    position: absolute;
    left: 0;
    color: #8A6FE6;
}

.category-label {
    display: inline-block;
    padding: 0.25rem 0.75rem;
    border-radius: 1rem;
    font-size: 0.75rem;
    font-weight: 500;
    margin-bottom: 1rem;
    letter-spacing: 0.5px;
    text-transform: uppercase;
}

.category-pharma {
    background: rgba(138, 111, 230, 0.1);
    color: #8A6FE6;
}

.category-edtech {
    background: rgba(255, 107, 107, 0.1);
    color: #FF6B6B;
}

.category-b2c {
    background: rgba(77, 171, 247, 0.1);
    color: #4DABF7;
}

.product-category {
    margin-bottom: 4rem;
    opacity: 0;
    transform: translateY(20px);
    animation: fadeInUp 0.6s ease forwards;
}

.product-category:nth-child(2) {
    animation-delay: 0.2s;
}

.product-category:nth-child(3) {
    animation-delay: 0.4s;
}

.product-category-title {
    margin-bottom: 2rem;
    padding-left: 1rem;
    border-left: 4px solid;
    font-size: 2rem;
    font-weight: 600;
}

.pharma-title {
    border-color: #8A6FE6;
}

.edtech-title {
    border-color: #FF6B6B;
}

.b2c-title {
    border-color: #4DABF7;
}

@keyframes fadeInUp {
    from {
        opacity: 0;
        transform: translateY(20px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

@media (max-width: 768px) {
    .products-section {
        padding: 60px 0;
    }
    
    .product-grid {
        grid-template-columns: 1fr;
    }
    
    .product-category {
        margin-bottom: 3rem;
    }
    
    .product-category-title {
        font-size: 1.5rem;
    }
}
</style>

<section id="products" class="products-section">
    <div class="container-fluid">
        <!-- Section Header -->
        <div class="row justify-content-center mb-5">
            <div class="col-lg-8 text-center">
                <h2 class="display-5 fw-bold mb-4" data-aos="fade-up">
                    Our <span style="background: linear-gradient(135deg, #8A6FE6, #4D55E8); -webkit-background-clip: text; -webkit-text-fill-color: transparent;">AI-Powered Solutions</span>
                </h2>
                <p class="lead text-light-emphasis mb-0" data-aos="fade-up" data-aos-delay="100">
                    Transforming industries through intelligent automation
                </p>
            </div>
        </div>

        <!-- Pharma Manufacturing Solutions -->
        <div class="product-category">
            <h3 class="product-category-title pharma-title text-light mb-4">Pharma Manufacturing Solutions</h3>
            <div class="product-grid">
                <div class="product-card" data-aos="fade-up">
                    <span class="category-label category-pharma">Quality Control</span>
                    <div class="product-icon">
                        <i class="bi bi-shield-check"></i>
                    </div>
                    <h3 style="color: #8A6FE6;">Quality Control Suite</h3>
                    <p style="color: rgba(255,255,255,0.8);">Comprehensive quality control and testing management</p>
                    <ul class="feature-list list-unstyled">
                        <li>Material Test Packet Checklist</li>
                        <li>Related Substance Test (RST)</li>
                        <li>Impurity Calculation</li>
                        <li>Environmental Monitoring</li>
                    </ul>
                </div>

                <div class="product-card" data-aos="fade-up" data-aos-delay="100">
                    <span class="category-label category-pharma">Environmental</span>
                    <div class="product-icon">
                        <i class="bi bi-thermometer-half"></i>
                    </div>
                    <h3 style="color: #8A6FE6;">Environmental Control</h3>
                    <p style="color: rgba(255,255,255,0.8);">Advanced environmental monitoring and control</p>
                    <ul class="feature-list list-unstyled">
                        <li>Temperature Monitoring</li>
                        <li>Relative Humidity Tracking</li>
                        <li>Equipment Logging</li>
                        <li>Automated Alerts</li>
                    </ul>
                </div>

                <div class="product-card" data-aos="fade-up" data-aos-delay="200">
                    <span class="category-label category-pharma">Access Control</span>
                    <div class="product-icon">
                        <i class="bi bi-key"></i>
                    </div>
                    <h3 style="color: #8A6FE6;">Access Management</h3>
                    <p style="color: rgba(255,255,255,0.8);">Comprehensive access management system</p>
                    <ul class="feature-list list-unstyled">
                        <li>User Access Management</li>
                        <li>Door Access Control</li>
                        <li>Audit Logs</li>
                        <li>Access Request Management</li>
                    </ul>
                </div>
            </div>
        </div>

        <!-- EdTech Solutions -->
        <div class="product-category">
            <h3 class="product-category-title edtech-title text-light mb-4">EdTech Solutions</h3>
            <div class="product-grid">
                <div class="product-card" data-aos="fade-up">
                    <span class="category-label category-edtech">CRM</span>
                    <div class="product-icon">
                        <i class="bi bi-mortarboard"></i>
                    </div>
                    <h3 style="color: #FF6B6B;">Smart Campus CRM</h3>
                    <p style="color: rgba(255,255,255,0.8);">Comprehensive education management system</p>
                    <ul class="feature-list list-unstyled">
                        <li>Student Lifecycle Management</li>
                        <li>Admission Pipeline Tracking</li>
                        <li>Course Progress Analytics</li>
                        <li>Performance Monitoring</li>
                    </ul>
                </div>

                <div class="product-card" data-aos="fade-up" data-aos-delay="100">
                    <span class="category-label category-edtech">Analytics</span>
                    <div class="product-icon">
                        <i class="bi bi-graph-up"></i>
                    </div>
                    <h3 style="color: #FF6B6B;">Learning Insights</h3>
                    <p style="color: rgba(255,255,255,0.8);">AI-powered learning analytics platform</p>
                    <ul class="feature-list list-unstyled">
                        <li>Personalized Learning Paths</li>
                        <li>Engagement Analytics</li>
                        <li>Progress Tracking</li>
                        <li>Outcome Prediction</li>
                    </ul>
                </div>

                <div class="product-card" data-aos="fade-up" data-aos-delay="200">
                    <span class="category-label category-edtech">Content</span>
                    <div class="product-icon">
                        <i class="bi bi-journal-text"></i>
                    </div>
                    <h3 style="color: #FF6B6B;">Content Hub</h3>
                    <p style="color: rgba(255,255,255,0.8);">Smart content management and delivery</p>
                    <ul class="feature-list list-unstyled">
                        <li>Dynamic Course Creation</li>
                        <li>Interactive Content</li>
                        <li>Assessment Generation</li>
                        <li>Resource Library</li>
                    </ul>
                </div>
            </div>
        </div>

        <!-- B2C Solutions -->
        <div class="product-category">
            <h3 class="product-category-title b2c-title text-light mb-4">B2C Solutions</h3>
            <div class="product-grid">
                <div class="product-card" data-aos="fade-up">
                    <span class="category-label category-b2c">E-commerce</span>
                    <div class="product-icon">
                        <i class="bi bi-cart"></i>
                    </div>
                    <h3 style="color: #4DABF7;">Smart Shop</h3>
                    <p style="color: rgba(255,255,255,0.8);">AI-powered e-commerce platform</p>
                    <ul class="feature-list list-unstyled">
                        <li>Personalized Recommendations</li>
                        <li>Smart Inventory Management</li>
                        <li>Dynamic Pricing</li>
                        <li>Customer Behavior Analytics</li>
                    </ul>
                </div>

                <div class="product-card" data-aos="fade-up" data-aos-delay="100">
                    <span class="category-label category-b2c">Support</span>
                    <div class="product-icon">
                        <i class="bi bi-headset"></i>
                    </div>
                    <h3 style="color: #4DABF7;">AI Support</h3>
                    <p style="color: rgba(255,255,255,0.8);">Intelligent customer service automation</p>
                    <ul class="feature-list list-unstyled">
                        <li>Smart Chatbot Integration</li>
                        <li>Ticket Management</li>
                        <li>Sentiment Analysis</li>
                        <li>Support Analytics</li>
                    </ul>
                </div>

                <div class="product-card" data-aos="fade-up" data-aos-delay="200">
                    <span class="category-label category-b2c">Loyalty</span>
                    <div class="product-icon">
                        <i class="bi bi-award"></i>
                    </div>
                    <h3 style="color: #4DABF7;">Rewards Plus</h3>
                    <p style="color: rgba(255,255,255,0.8);">Smart loyalty and rewards management</p>
                    <ul class="feature-list list-unstyled">
                        <li>Points Management</li>
                        <li>Personalized Rewards</li>
                        <li>Campaign Automation</li>
                        <li>Engagement Analytics</li>
                    </ul>
                </div>
            </div>
        </div>

        <!-- CTA -->
        <div class="text-center mt-5" data-aos="fade-up">
            <button class="btn btn-primary btn-lg px-5" onclick="startBuilding()">
                Explore All Solutions
                <i class="bi bi-arrow-right ms-2"></i>
            </button>
        </div>
    </div>
</section>
