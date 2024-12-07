<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
.contact-section {
    min-height: 100vh;
    display: flex;
    align-items: center;
    background: linear-gradient(180deg, #252837 0%, #1C1F2E 100%);
    padding: 100px 0;
    position: relative;
    overflow: hidden;
}

.contact-section::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 1px;
    background: linear-gradient(90deg, transparent, #8A6FE6, transparent);
}

.contact-title {
    font-size: clamp(2.5rem, 5vw, 4rem);
    font-weight: 700;
    line-height: 1.2;
    margin-bottom: 1rem;
}

.contact-subtitle {
    font-size: clamp(1rem, 2vw, 1.25rem);
    color: rgba(255, 255, 255, 0.8);
    margin-bottom: 3rem;
}

.contact-form {
    background: rgba(255, 255, 255, 0.03);
    border: 1px solid rgba(255, 255, 255, 0.1);
    border-radius: 24px;
    padding: 2.5rem;
    backdrop-filter: blur(10px);
    position: relative;
    overflow: hidden;
}

.form-group {
    margin-bottom: 1.5rem;
}

.form-label {
    color: rgba(255, 255, 255, 0.8);
    margin-bottom: 0.5rem;
    font-weight: 500;
}

.form-control {
    background: rgba(255, 255, 255, 0.05);
    border: 1px solid rgba(255, 255, 255, 0.1);
    border-radius: 12px;
    color: #fff;
    padding: 1rem;
    height: auto;
    transition: all 0.3s ease;
}

.form-control:focus {
    background: rgba(255, 255, 255, 0.08);
    border-color: #8A6FE6;
    box-shadow: 0 0 0 0.25rem rgba(138, 111, 230, 0.25);
    color: #fff;
}

.form-control::placeholder {
    color: rgba(255, 255, 255, 0.4);
}

select.form-control {
    appearance: none;
    background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='16' height='16' fill='%238A6FE6' viewBox='0 0 16 16'%3E%3Cpath d='M7.247 11.14L2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z'/%3E%3C/svg%3E");
    background-repeat: no-repeat;
    background-position: right 1rem center;
    padding-right: 2.5rem;
}

.quick-contact {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 1.5rem;
    margin-top: 3rem;
}

.contact-method {
    background: rgba(255, 255, 255, 0.03);
    border: 1px solid rgba(255, 255, 255, 0.1);
    border-radius: 16px;
    padding: 1.5rem;
    text-align: center;
    transition: all 0.3s ease;
}

.contact-method:hover {
    transform: translateY(-5px);
    background: rgba(255, 255, 255, 0.05);
}

.contact-icon {
    width: 48px;
    height: 48px;
    background: rgba(138, 111, 230, 0.1);
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 0 auto 1rem;
}

.contact-icon i {
    font-size: 24px;
    color: #8A6FE6;
}

.btn-submit {
    background: linear-gradient(135deg, #8A6FE6, #4D55E8);
    border: none;
    border-radius: 12px;
    color: #fff;
    font-weight: 500;
    padding: 1rem 2rem;
    width: 100%;
    transition: all 0.3s ease;
}

.btn-submit:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 15px rgba(138, 111, 230, 0.3);
}

@media (max-width: 768px) {
    .contact-section {
        padding: 60px 0;
    }
    
    .contact-form {
        padding: 1.5rem;
    }
    
    .quick-contact {
        grid-template-columns: 1fr;
    }
}
</style>

<section id="contact" class="contact-section">
    <div class="container-fluid">
        <div class="row justify-content-center">
            <div class="col-lg-8 text-center">
                <h2 class="contact-title">
                    Let's <span style="background: linear-gradient(135deg, #8A6FE6, #4D55E8); -webkit-background-clip: text; -webkit-text-fill-color: transparent;">Build Together</span>
                </h2>
                <p class="contact-subtitle">
                    Our AI team is ready to transform your vision into reality
                </p>
            </div>
        </div>

        <div class="row justify-content-center">
            <div class="col-lg-8">
                <form class="contact-form" id="contactForm">
                    <div class="form-group">
                        <label class="form-label" for="name">Your Name</label>
                        <input type="text" class="form-control" id="name" placeholder="Enter your name" required>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="email">Email Address</label>
                        <input type="email" class="form-control" id="email" placeholder="Enter your email" required>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="projectType">Project Type</label>
                        <select class="form-control" id="projectType" required>
                            <option value="">Select Project Type</option>
                            <option value="pharma">Pharma Manufacturing</option>
                            <option value="edtech">EdTech Solution</option>
                            <option value="b2c">B2C Platform</option>
                            <option value="custom">Custom Solution</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="message">Project Description</label>
                        <textarea class="form-control" id="message" rows="4" placeholder="Describe your project" required></textarea>
                    </div>

                    <button type="submit" class="btn btn-submit">
                        <i class="bi bi-rocket-takeoff me-2"></i>
                        Start Building
                    </button>
                </form>

                <div class="quick-contact">
                    <div class="contact-method">
                        <div class="contact-icon">
                            <i class="bi bi-chat-text"></i>
                        </div>
                        <h4 class="h5 mb-2">Live Chat</h4>
                        <p class="text-light-emphasis mb-0">Chat with our AI team 24/7</p>
                    </div>

                    <div class="contact-method">
                        <div class="contact-icon">
                            <i class="bi bi-calendar-check"></i>
                        </div>
                        <h4 class="h5 mb-2">Schedule Demo</h4>
                        <p class="text-light-emphasis mb-0">See our solutions in action</p>
                    </div>

                    <div class="contact-method">
                        <div class="contact-icon">
                            <i class="bi bi-telephone"></i>
                        </div>
                        <h4 class="h5 mb-2">Call Us</h4>
                        <p class="text-light-emphasis mb-0">Get instant support</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<script>
document.addEventListener('DOMContentLoaded', function() {
    const contactForm = document.getElementById('contactForm');

    contactForm.addEventListener('submit', function(event) {
        event.preventDefault();
        
        // Get form values
        const name = document.getElementById('name').value;
        const email = document.getElementById('email').value;
        const projectType = document.getElementById('projectType').value;
        const message = document.getElementById('message').value;

        // Here you would typically send the data to your server
        console.log('Form submitted:', { name, email, projectType, message });

        // Show success message
        alert('Thank you for your interest! Our team will contact you shortly.');
        
        // Reset form
        contactForm.reset();
    });
});
</script>
