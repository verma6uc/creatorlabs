<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="step-content">
    <h3 class="text-center mb-4">Let's get to know your company</h3>
    
    <form action="onboarding" method="post" class="max-w-md mx-auto">
        <input type="hidden" name="step" value="2">
        
        <div class="mb-4">
            <label class="form-label">Company Name</label>
            <input type="text" 
                   name="companyName" 
                   class="form-control"
                   placeholder="Enter your company name" 
                   required>
        </div>
        
        <div class="mb-4">
            <label class="form-label">Company Website</label>
            <input type="url" 
                   name="website" 
                   class="form-control"
                   placeholder="https://example.com">
            <small class="text-muted">Optional - helps us learn more about your company</small>
        </div>
        
        <div class="mb-4">
            <label class="form-label">Industry</label>
            <select name="industry" class="form-control" required>
                <option value="">Select your industry</option>
                <option value="tech">Technology</option>
                <option value="healthcare">Healthcare</option>
                <option value="finance">Finance</option>
                <option value="retail">Retail</option>
                <option value="education">Education</option>
                <option value="other">Other</option>
            </select>
        </div>
        
        <button type="submit" class="btn btn-primary w-100">
            Continue
            <i class="bi bi-arrow-right ms-2"></i>
        </button>
    </form>
</div>