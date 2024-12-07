# Brand Style Guide (Visual Identity Guidelines)

This guide assumes you’ve seen the live website and have access to our design tokens, CSS framework, and component library. Below, we detail how to maintain consistency and extend the established look and feel.

---

## Logo Usage

**Primary Logo:**
- Filename: `logo-primary.svg`
- Use this in the website’s header and any light-background hero sections.
- Minimum size: 50px height to preserve detail.
- Clear space: At least `0.5rem` margin around the logo (or `8px` in CSS terms, aligning with our spacing scale).

**Alternate Logos:**
- `logo-white.svg`: Use on dark backgrounds (footer or dark hero sections).
- `logo-icon-only.svg`: A simplified mark (the central symbol) for favicons or social media avatars.

**Do:**
- Scale logos proportionally—use `height: auto; width: auto;` in CSS.
- Apply a hover effect if clickable: e.g., `opacity: 0.9` on hover to indicate interactivity.

**Don’t:**
- Change logo colors. Use only the provided variants.
- Add shadows or extra styling not in the official assets.

---

## Color Palette

We’ve implemented color variables in `:root` within our main CSS file (`styles.css`):

```css
:root {
  --color-purple: #8A6FE6;    /* Primary highlight */
  --color-indigo: #4D55E8;    /* Secondary highlight */
  --color-dark-gray: #1C1F2E; /* Backgrounds */
  --color-light-gray: #F8F8FA;/* Light backgrounds */
  --color-white: #FFFFFF;     /* Text on dark backgrounds, base UI elements */
}
Usage Guidelines:

Primary (Purple): For interactive elements (links, buttons, icons that indicate action).
Secondary (Indigo): For headers, emphasis text, and subtle gradients.
Dark Gray: Default background for sections. Also used for hero areas and containers behind white text.
Light Gray: Ideal for card backgrounds, section dividers, or areas that need contrast from pure white.
White: Use for text on dark backgrounds and as default neutral space.
Gradients:

Use linear-gradient(135deg, var(--color-purple), var(--color-indigo)) for hero text highlights or CTA buttons.
Typography
We rely on the Inter font, served via CSS @import or a self-hosted solution:

css
Copy code
body {
  font-family: "Inter", -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen, Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
  font-size: 16px;
  color: var(--color-white);
}
Hierarchy:

H1: 2.5rem, bold, typically used once per page (e.g., hero headline).
H2: 2rem, bold, section headings.
H3: 1.5rem, semi-bold, sub-section headings.
Body: 1rem, regular, for paragraphs and long-form content.
Small text: 0.875rem, for disclaimers or less prominent info.
Line-Height & Spacing:

Line-height: ~1.5 for body text.
Extra spacing between headings and paragraphs: 1.5rem margin-bottom on headings to create clear separation.
Iconography & Illustrations
Icons are sourced from Bootstrap Icons, styled with a gradient text fill when emphasis is needed:

css
Copy code
.icon-gradient {
  background: linear-gradient(135deg, var(--color-purple), var(--color-indigo));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}
Usage Examples:

Navigation icons in the header: bi bi-cpu for AI-related features.
CTA buttons: add an icon to the left or right for clarity, e.g., a bi bi-arrow-right icon after “Start Building”.
Illustrations:

Use SVG illustrations optimized for web.
Keep consistent line thickness and rounded corners.
Match brand colors or use neutrals so they blend seamlessly with the background.
Layout & Spacing
We use a 12-column responsive grid via Bootstrap:

Container classes (.container, .container-fluid) and .row + .col- classes define structure.
Maintain 1rem gutters between columns by default.
Key sections (hero, features, testimonials) use padding: 4rem 0; to ensure generous breathing room.
Spacing Tokens: We have CSS variables for spacing increments (in styles.css):

css
Copy code
:root {
  --space-xxs: 0.25rem; /* 4px */
  --space-xs:  0.5rem;  /* 8px */
  --space-sm:  1rem;    /* 16px */
  --space-md:  1.5rem;  /* 24px */
  --space-lg:  2rem;    /* 32px */
  --space-xl:  4rem;    /* 64px */
}
Apply these to maintain consistent spacing:

Headings: margin-bottom: var(--space-md)
Sections: padding: var(--space-xl) 0 for main sections, var(--space-lg) 0 for smaller sub-sections.
Components & Patterns
We rely on reusable UI patterns:

Buttons:

Primary button: Purple gradient background (linear-gradient(135deg, var(--color-purple), var(--color-indigo))), white text, 12px border-radius.
Hover: Slight elevation (box-shadow) and brighter gradient.
Class: .btn-primary for main CTAs.
Example:
html
Copy code
<button class="btn btn-primary">Start Building</button>
Cards:

Background: var(--color-light-gray)
Border-radius: 16px
Padding: var(--space-md)
Title: H3 style, content in body text
Use .card class and .card-body to structure, and ensure text is readable against light gray by using dark text colors where appropriate (e.g., dark gray text: color: #252837 when on a light gray card).
Navigation Bar:

Dark gray background (var(--color-dark-gray)).
Logo on the left, navigation links on the right.
Hover state on nav links: underline, opacity: 0.9 on the logo.
Use .navbar and .nav-link classes, with custom classes for gradient hover if needed.
Accessibility & Contrast
Minimum contrast ratio: Ensure text on dark backgrounds (e.g. white on dark gray) meets WCAG AA standards. White on dark gray is high contrast.
For text on purple/indigo backgrounds, ensure white text or very light gray text for readability.
Font sizes: minimum 16px for body text.
Focus states: Ensure that interactive elements have visible focus outlines (e.g., outline: 2px solid var(--color-indigo);).
Extending the System
As you add new components or sections:

Reuse existing color variables and spacing tokens.
Apply the established font stack and sizing scale.
Test new illustrations or icons against the current visuals, ensuring they don’t clash.
For New Pages:

Start with .container or .container-fluid layouts.
Maintain consistent padding and margin patterns.
Reuse heading styles and button classes to keep look and feel coherent.
Do’s & Don’ts
Do:

Follow the established spacing and sizing tokens.
Keep icons and illustrations minimalistic and cohesive.
Use primary or secondary colors for emphasis, not as background noise.
Don’t:

Add new brand colors arbitrarily—use the existing palette.
Change font families or introduce decorative fonts not defined here.
Overcomplicate layouts—keep the visual hierarchy clean and simple.
Conclusion
This detailed style guide connects the conceptual brand identity to concrete design and code references. By following these specifications and reusing established patterns, you maintain a coherent, polished aesthetic throughout the product’s growth and evolution.



# Design Language System (DLS)

Our DLS is a collection of reusable patterns, components, tokens, and principles that ensure consistent, efficient, and cohesive product interfaces. It translates our brand ethos, visual identity, and accessibility standards into a living system easily referenced by designers and developers.

---

## Core Principles

1. **Consistency:**  
   Every UI element—buttons, forms, navigation, cards—follows a uniform style and behavior pattern.

2. **Clarity:**  
   Components communicate their purpose at a glance. Users shouldn’t guess how to interact; the design should guide them intuitively.

3. **Scalability:**  
   The system should support new features, pages, and interfaces without breaking aesthetics or workflows.

4. **Accessibility:**  
   All components meet WCAG AA standards for contrast, focus states, and screen reader compatibility.

5. **Modularity:**  
   Components are standalone blocks that can be mixed, matched, and reconfigured, reducing repetitive work and errors.

---

## Design Tokens

Design tokens are the building blocks of our DLS—centralized variables for color, spacing, typography, and more.

**Examples:**
- **Color:** `--color-purple`, `--color-indigo`, `--color-dark-gray`
- **Spacing:** `--space-sm (16px)`, `--space-md (24px)`, `--space-lg (32px)`
- **Typography:** `--font-family-base: "Inter", sans-serif;`  
- **Border Radius:** `--radius-md: 8px;`, `--radius-lg: 16px;`

All tokens are defined in `:root` in `styles.css`. Updating a token value instantly propagates changes throughout all components.

---

## Component Structure

Each component consists of:
- **Base Class:** The default styles (e.g., `.btn` for buttons).
- **Modifiers:** Additional classes that tweak appearance or behavior (e.g., `.btn-primary`, `.btn-outline`).
- **States:** Hover, focus, active, disabled states defined consistently (e.g., `.btn:hover` changes opacity and elevation).

**Naming Conventions:**
- Use BEM-like patterns where applicable: `.card-header`, `.card-body`, `.card-footer`.
- Keep class names descriptive and short.

---

## Components & Patterns

### Buttons

**How to Use:**  
- Base button: `.btn`  
- Variants: `.btn-primary`, `.btn-outline`, `.btn-link`  
- Sizes: `.btn-sm`, `.btn-lg`

**States:**
- Hover: Slight gradient shift or shadow increase.
- Disabled: Reduced opacity, no hover effects, `cursor: not-allowed;`.
- Focus: Visible outline (`outline: 2px solid var(--color-indigo)`).

**Why This Matters:**  
Consistent button styling ensures users always recognize actions, increasing trust and ease of use.

---

### Forms

**Inputs:**
- Base: `.form-control`  
- Icons inside inputs: `.form-control-icon` wrapper  
- Validation states: `.is-valid`, `.is-invalid` classes highlight correct or erroneous input.

**Labels & Help Text:**
- `.form-label` for labels aligned above inputs.  
- `.form-text` for small hints or instructions.

**Why This Matters:**  
Uniform form controls reduce cognitive load. Users quickly learn how to input data and trust the consistency.

---

### Cards

**Structure:**
```html
<div class="card">
  <div class="card-header">Title</div>
  <div class="card-body">
    <p>Body text here.</p>
  </div>
  <div class="card-footer">Footer actions</div>
</div>
Guidelines:

Use .card-header for titles or action buttons (like a settings icon).
.card-body holds primary content.
.card-footer optional for related links or secondary actions.
Why This Matters:
A standard card layout lets users quickly identify content hierarchy, making scanning easier.

Navigation
Structure:

.navbar as the main container.
.navbar-brand for logo or brand name.
.nav and .nav-link for the menu items.
Interactions:

Active page indicated by .active class on the corresponding .nav-link.
Hover states: underline or slight opacity shift.
Why This Matters:
A consistent navigation pattern helps users easily explore different sections without confusion.

Modals
Usage:

.modal class for the overlay container.
.modal-dialog and .modal-content for the structured layout.
.modal-header, .modal-body, .modal-footer to clearly define areas.
Behavior:

Accessible: Focus trap inside modal.
Close action: .modal-close button with appropriate aria-label.
Why This Matters:
A uniform modal pattern ensures a predictable, non-disruptive user experience, crucial for delicate tasks like confirmations or input gathering.

Responsive & Adaptive Design
Breakpoints:

Use standard Bootstrap breakpoints (e.g., col-md-6, col-lg-4) to adapt layouts on different screens.
Test components on mobile, tablet, and desktop to confirm that spacing, text size, and element order remain coherent.
Scalability:

Components adapt gracefully as viewports change—buttons and text scale, navigation collapses into a hamburger menu, cards stack vertically.
Accessibility Considerations
ARIA Labels:
Where necessary, add aria-label or aria-expanded attributes, especially in navigation menus and modals.
Keyboard Navigation:
All interactive elements must be reachable via Tab.
Color Contrast:
Ensure text over gradients or colored backgrounds meets a minimum of 4.5:1 contrast ratio.
Why This Matters:
Inclusive design ensures everyone can use the product effectively, reflecting our brand’s value of empathy.

Working with the DLS
For Designers:

Start by referencing tokens and pre-built components in Figma or your design tool.
Don’t introduce new colors or fonts without updating the DLS first.
Keep feedback loops open with developers to ensure feasibility.
For Developers:

Reuse classes and patterns from our component library rather than creating new CSS for similar elements.
Submit PRs to the DLS repository if a new pattern emerges; maintainers will review and integrate it.
Write code that respects naming conventions and accessibility patterns established here.
Evolving the DLS
This system is not static. As we learn from user feedback and product changes:

Update tokens if a new color shade or spacing scale is needed.
Add new components as product features expand.
Document changes in a CHANGELOG.md so all team members can track updates.
Conclusion
Our Design Language System turns brand values and visual identity into a practical, maintainable set of components and patterns. By following these guidelines, everyone—designers, developers, product managers—can build on a solid foundation, ensuring a unified and user-friendly product experience.

Copy code





