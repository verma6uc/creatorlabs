# Endpoints Used in the Prototype: Detailed Narrative

Below is a narrative outlining how each selected endpoint is used in the initial prototype. This story helps you understand the journey a typical user (e.g., Alex the entrepreneur) would take, and how we translate each step into static pages for the MVP. By starting with static pages, we can set the visual and informational structure even before hooking up dynamic logic.

## User Journey Overview

1. **User Access & Onboarding:**  
   The user signs up, logs in, and completes initial onboarding steps. This ensures they understand how the platform works and what to do next.

2. **Idea & Project Creation:**  
   The user creates their first project by providing a short product idea description. The platform then refines this idea (Memory Enhancement) to provide strategic insights.

3. **Blueprint & PRD Generation:**  
   With guided input from AI agents, the user generates a blueprint (navigation, features) and then a PRD (detailed visual specification).

4. **Code Kickstart:**  
   Finally, the user can request code generation to produce a starter prototype codebase.

At this stage, we are focusing on static pages that represent each step, guiding the user through the narrative. Eventually, these pages will connect to the endpoints described.

---

## Endpoints in the Prototype

### Authentication & Onboarding

**`POST /auth/signup`**  
**`POST /auth/login`**  
These endpoints let a new user sign up and then log in. In the prototype’s static pages, we’ll have:

- A **Sign-Up Page** (static mock): A form for email, password, and optional role selection. The user hits a “Sign Up” button (which will later trigger `/auth/signup`).
- A **Login Page** (static mock): A form for existing users to enter credentials. The “Login” button will eventually call `/auth/login`.

**`POST /api/onboarding/start`**, **`GET /api/onboarding/steps`**, **`POST /api/onboarding/completeStep`**  
Once logged in, the user begins onboarding. The static pages here:

- **Onboarding Intro Page:** Explains what the platform does. A “Begin Onboarding” button will later trigger `/api/onboarding/start`.
- **Onboarding Steps Page:** Displays a list of steps retrieved from `/api/onboarding/steps` (for now, mocked as static text: “Step 1: Input your idea,” “Step 2: Generate blueprint,” etc.).
- **Onboarding Completion Page:** After finishing a step, the user sees a confirmation. A “Mark as Complete” button would eventually post to `/api/onboarding/completeStep`.

Narrative Example:  
Alex signs up, logs in, sees a guided intro explaining the platform. The next static page shows steps: “1) Create your first project 2) Get strategic insights 3) Generate a blueprint 4) Produce a PRD 5) Optionally generate code.” Each step will be a static card describing what it means. When Alex “completes” a step (in the final product), the backend updates onboarding progress.

---

### Project Creation & Idea Input

**`POST /api/projects`**  
Alex now wants to start building a product. The static **New Project Page** includes:

- A text area for a short 3-5 line product idea description.
- A field for the project name.
- A “Create Project” button that, in the final system, calls `/api/projects`.

Narrative Example:  
After reading onboarding steps, Alex lands on a “Create Your First Project” page. He enters “Project Name: QuickFood,” and a brief idea: “An app that delivers fresh meals from local chefs to busy professionals in minutes.” Clicking “Create Project” (later wired to `/api/projects`) shows a confirmation page with a placeholder “Your project is ready” message.

---

### Memory Enhancement (AI Insights)

**`POST /api/projects/{projectId}/memory`**  
This step refines the product idea. The static **Memory Enhancement Page**:

- Explains: “We’ll analyze your idea and suggest key features, user behaviors, and market considerations.”
- A “Generate Insights” button (in the future, posts to `/api/projects/{projectId}/memory`).
- After clicking, a static “Insights Page” shows mocked results like: “Must-have features: user-friendly ordering flow, real-time delivery tracking. Market context: target urban professionals. Suggested first features: onboarding flow, menu browsing, checkout.”

Narrative Example:  
Alex sees a page stating: “We’re going to help refine your idea.” He clicks “Generate Insights,” and the next static page displays recommended features and priorities. In production, these insights come from AI, but for the static prototype, we just show placeholder text.

---

### Blueprint Creation

**`POST /api/projects/{projectId}/blueprint`**  
From insights to structure, the blueprint stage creates a navigational map and architecture outline. The static **Blueprint Page**:

- Shows a sample diagram: “Screen 1: Home → Screen 2: Menu → Screen 3: Checkout”
- Provides a “Generate Blueprint” button that would call `/api/projects/{projectId}/blueprint`.
- After generation, the page displays a static site map or flow diagram (PNG or SVG mock) and a brief JSON-like representation of backend endpoints, database tables, etc.

Narrative Example:  
Alex sees: “Your app will have a Home screen, a Menu screen, and a Checkout screen. The database will store Restaurants, Meals, and Orders.” He sees a static illustration of a simple user flow and some architecture notes. In the final product, the blueprint is dynamically created.

---

### PRD Generation

**`POST /api/projects/{projectId}/prd`**  
The PRD turns the blueprint into a visual specification. The static **PRD Page**:

- Contains a “Generate PRD” button that would call `/api/projects/{projectId}/prd`.
- After “generation,” a static PRD view is shown with page layouts, component specs, user flows in a more polished format—like a mini design spec doc: wireframes, state diagrams, and short textual descriptions of each component.

Narrative Example:  
Alex sees a well-structured spec: “Home Page: Display user’s location & featured meals. Menu Page: List of meals with ‘Add to Cart’ buttons. Checkout Page: Payment and delivery confirmation flow.” Each screen’s layout is a static wireframe image, each feature briefly described.

---

### Code Generation

**`POST /api/projects/{projectId}/code`**  
Finally, code generation provides a starter codebase. The static **Code Generation Page**:

- A “Generate Code” button (would call `/api/projects/{projectId}/code`).
- After clicking, a static result page shows a “Download Starter Code” link and lists what’s included: “frontend/ directory with basic HTML/JS,” “backend/ directory with Java servlets and a sample database schema.”

Narrative Example:  
Alex sees a page: “Your initial code is ready for download! This code includes servlets for user login, a basic controller for listing meals, and placeholder HTML templates for each screen.” While static now, it simulates what will eventually be a real download link or Git repo instructions.

---

### Using These Endpoints in Static Pages

For the prototype’s static phase, no real data is fetched. Instead, each endpoint is conceptually represented:

- The **Sign-Up Page** and **Login Page** link to `POST /auth/signup` and `POST /auth/login` respectively, but currently just show a static “You’re logged in!” message after a form submission simulation.
- The **Onboarding Pages** show steps and mark completion without calling real endpoints, just navigating between static pages.
- The **New Project Page** displays a form to “create” a project, then leads to a static confirmation page.
- The **Memory Enhancement Page** and **Blueprint Page** have “Generate” buttons that lead to static results screens demonstrating what would happen if the endpoint was called.
- The **PRD Page** and **Code Generation Page** similarly show static representations of final deliverables.

By creating these static pages mapped conceptually to the endpoints, the narrative is maintained: a user can start from sign-up, move through onboarding, create a project, enhance their idea, see a blueprint, produce a PRD, and even get starter code—entirely through placeholder text and images. Later, the same pages can be wired to actual endpoints to bring them to life.

---

**In Summary:**
For the MVP prototype, the following endpoints and their narrative roles are highlighted with static pages:

- **Auth:** `/auth/signup`, `/auth/login`  
  *Pages*: Sign-Up, Login
- **Onboarding:** `/api/onboarding/start`, `/api/onboarding/steps`, `/api/onboarding/completeStep`  
  *Pages*: Onboarding Intro, Onboarding Steps, Onboarding Completion
- **Project Creation:** `/api/projects` (POST)  
  *Page*: New Project Creation
- **Memory Enhancement:** `/api/projects/{projectId}/memory` (POST)  
  *Page*: Idea Insights/Refinement
- **Blueprint:** `/api/projects/{projectId}/blueprint` (POST)  
  *Page*: Blueprint Overview
- **PRD:** `/api/projects/{projectId}/prd` (POST)  
  *Page*: PRD View
- **Code Generation:** `/api/projects/{projectId}/code` (POST)  
  *Page*: Code Download/Ready Page

These are the critical endpoints (and thus pages) for the prototype narrative, allowing a complete, if static, user flow demonstration.
