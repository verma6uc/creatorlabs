<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="step-content">
    <h3 class="text-center mb-4">How would you describe your brand's personality?</h3>
    
    <form action="onboarding" method="post">
        <input type="hidden" name="step" value="3">
        
        <div class="brand-options">
            <div class="option-card mb-3" onclick="selectOption('hero')">
                <input type="radio" name="brandType" value="hero" class="d-none" required>
                <div class="p-4 rounded-lg border border-light">
                    <h4>The Hero</h4>
                    <p class="text-muted mb-0">We inspire and empower others to overcome challenges and achieve greatness.</p>
                </div>
            </div>
            
            <div class="option-card mb-3" onclick="selectOption('sage')">
                <input type="radio" name="brandType" value="sage" class="d-none">
                <div class="p-4 rounded-lg border border-light">
                    <h4>The Sage</h4>
                    <p class="text-muted mb-0">We guide through knowledge and wisdom, helping others understand and grow.</p>
                </div>
            </div>
            
            <div class="option-card mb-3" onclick="selectOption('creator')">
                <input type="radio" name="brandType" value="creator" class="d-none">
                <div class="p-4 rounded-lg border border-light">
                    <h4>The Creator</h4>
                    <p class="text-muted mb-0">We innovate and build, bringing new ideas and solutions to life.</p>
                </div>
            </div>
            
            <div class="option-card mb-3" onclick="selectOption('caregiver')">
                <input type="radio" name="brandType" value="caregiver" class="d-none">
                <div class="p-4 rounded-lg border border-light">
                    <h4>The Caregiver</h4>
                    <p class="text-muted mb-0">We nurture and support, ensuring others feel safe and cared for.</p>
                </div>
            </div>
        </div>
        
        <button type="submit" class="btn btn-primary w-100">
            Continue
            <i class="bi bi-arrow-right ms-2"></i>
        </button>
    </form>
</div>

<style>
.option-card {
    cursor: pointer;
    transition: all 0.3s ease;
}

.option-card:hover {
    transform: translateY(-2px);
}

.option-card input[type="radio"]:checked + div {
    border-color: var(--color-purple) !important;
    background: rgba(138, 111, 230, 0.1);
}
</style>

<script>
function selectOption(value) {
    // Reset all cards
    document.querySelectorAll('.option-card input').forEach(input => {
        input.checked = false;
        input.parentElement.querySelector('div').style.borderColor = 'rgba(255,255,255,0.1)';
    });
    
    // Select clicked card
    const card = document.querySelector(`input[value="${value}"]`);
    card.checked = true;
    card.parentElement.querySelector('div').style.borderColor = 'var(--color-purple)';
}
</script>