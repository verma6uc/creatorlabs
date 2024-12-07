document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('onboardingForm');
    const progressBar = document.querySelector('.progress-bar');
    const steps = document.querySelectorAll('.step');
    let currentStep = 1;
    
    // Initialize the form
    initializeForm();
    
    function initializeForm() {
        // Hide all sections except the first one
        document.querySelectorAll('.section-content').forEach((section, index) => {
            if (index === 0) {
                section.classList.add('active');
            } else {
                section.style.display = 'none';
            }
        });
        
        // Fetch current state from server
        fetch('/onboarding')
            .then(response => response.json())
            .then(data => {
                if (data.currentStep) {
                    goToStep(data.currentStep);
                }
                if (data.stepData) {
                    restoreStepData(data.stepData);
                }
            })
            .catch(error => console.error('Error fetching onboarding state:', error));
    }
    
    // Handle card option selections
    document.querySelectorAll('.card-option').forEach(card => {
        card.addEventListener('click', function() {
            const radio = this.querySelector('input[type="radio"]');
            if (radio) {
                // Unselect all cards in the same group
                const name = radio.getAttribute('name');
                document.querySelectorAll(`input[name="${name}"]`).forEach(input => {
                    input.closest('.card-option').classList.remove('selected');
                });
                
                // Select clicked card
                radio.checked = true;
                this.classList.add('selected');
                
                // Validate step after selection
                validateCurrentStep();
            }
        });
    });
    
    // Handle form submission
    if (form) {
        form.addEventListener('submit', function(e) {
            e.preventDefault();
            handleStepSubmission();
        });
    }
    
    // Handle workspace preference changes
    document.querySelectorAll('select').forEach(select => {
        select.addEventListener('change', validateCurrentStep);
    });
    
    function validateCurrentStep() {
        const stepData = collectStepData();
        
        fetch('/onboarding/validate-step', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `step=${currentStep}&data=${JSON.stringify(stepData)}`
        })
        .then(response => response.json())
        .then(data => {
            if (data.valid) {
                enableNextButton();
            } else {
                disableNextButton();
                showError(data.error);
            }
        })
        .catch(error => console.error('Validation error:', error));
    }
    
    function handleStepSubmission() {
        const stepData = collectStepData();
        
        fetch('/onboarding/save-step', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `step=${currentStep}&data=${JSON.stringify(stepData)}`
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                if (data.nextStep > 3) {
                    completeOnboarding();
                } else {
                    goToStep(data.nextStep);
                }
            }
        })
        .catch(error => console.error('Submission error:', error));
    }
    
    function collectStepData() {
        const data = {};
        
        switch(currentStep) {
            case 1:
                const selectedIdentity = document.querySelector('input[name="brandIdentity"]:checked');
                data.brandIdentity = selectedIdentity ? selectedIdentity.id : null;
                break;
                
            case 2:
                const devFocus = document.querySelector('select[name="developmentFocus"]');
                const teamCollab = document.querySelector('select[name="teamCollaboration"]');
                data.developmentFocus = devFocus ? devFocus.value : null;
                data.teamCollaboration = teamCollab ? teamCollab.value : null;
                break;
                
            case 3:
                const commStyle = document.querySelector('input[name="aiComm"]:checked');
                data.communicationStyle = commStyle ? commStyle.id : null;
                break;
        }
        
        return data;
    }
    
    function goToStep(step) {
        const sections = document.querySelectorAll('.section-content');
        const targetSection = sections[step - 1];
        
        // Fade out current section
        sections.forEach(section => {
            if (section.classList.contains('active')) {
                section.style.opacity = '0';
                setTimeout(() => {
                    section.style.display = 'none';
                    section.classList.remove('active');
                }, 300);
            }
        });
        
        // Fade in target section
        setTimeout(() => {
            targetSection.style.display = 'block';
            targetSection.classList.add('active');
            setTimeout(() => {
                targetSection.style.opacity = '1';
            }, 50);
        }, 300);
        
        // Update progress
        currentStep = step;
        updateProgress();
        updateStepIndicators();
    }
    
    function updateProgress() {
        const progress = ((currentStep - 1) / 3) * 100;
        progressBar.style.width = `${progress}%`;
    }
    
    function updateStepIndicators() {
        steps.forEach((step, index) => {
            if (index + 1 < currentStep) {
                step.classList.add('completed');
                step.classList.remove('active');
            } else if (index + 1 === currentStep) {
                step.classList.add('active');
                step.classList.remove('completed');
            } else {
                step.classList.remove('completed', 'active');
            }
        });
    }
    
    function enableNextButton() {
        const nextBtn = document.querySelector('.btn-primary');
        if (nextBtn) {
            nextBtn.disabled = false;
            nextBtn.classList.remove('disabled');
        }
    }
    
    function disableNextButton() {
        const nextBtn = document.querySelector('.btn-primary');
        if (nextBtn) {
            nextBtn.disabled = true;
            nextBtn.classList.add('disabled');
        }
    }
    
    function showError(message) {
        // Create or update error message element
        let errorDiv = document.querySelector('.error-message');
        if (!errorDiv) {
            errorDiv = document.createElement('div');
            errorDiv.className = 'error-message alert alert-danger mt-3';
            form.insertBefore(errorDiv, form.firstChild);
        }
        errorDiv.textContent = message;
        errorDiv.style.opacity = '1';
        
        // Auto-hide after 3 seconds
        setTimeout(() => {
            errorDiv.style.opacity = '0';
            setTimeout(() => errorDiv.remove(), 300);
        }, 3000);
    }
    
    function completeOnboarding() {
        fetch('/onboarding/complete', {
            method: 'POST'
        })
        .then(response => response.json())
        .then(data => {
            if (data.success && data.redirect) {
                window.location.href = data.redirect;
            }
        })
        .catch(error => console.error('Completion error:', error));
    }
    
    function restoreStepData(data) {
        switch(currentStep) {
            case 1:
                if (data.brandIdentity) {
                    const radio = document.getElementById(data.brandIdentity);
                    if (radio) {
                        radio.checked = true;
                        radio.closest('.card-option').classList.add('selected');
                    }
                }
                break;
                
            case 2:
                if (data.developmentFocus) {
                    const devFocus = document.querySelector('select[name="developmentFocus"]');
                    if (devFocus) devFocus.value = data.developmentFocus;
                }
                if (data.teamCollaboration) {
                    const teamCollab = document.querySelector('select[name="teamCollaboration"]');
                    if (teamCollab) teamCollab.value = data.teamCollaboration;
                }
                break;
                
            case 3:
                if (data.communicationStyle) {
                    const commStyle = document.getElementById(data.communicationStyle);
                    if (commStyle) {
                        commStyle.checked = true;
                        commStyle.closest('.card-option').classList.add('selected');
                    }
                }
                break;
        }
        
        validateCurrentStep();
    }
});
