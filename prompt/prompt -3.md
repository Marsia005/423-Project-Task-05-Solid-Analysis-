## PROMPT 3 — DEPENDENCY INVERSION REFINEMENT

**CONTEXT**
Iteration 2 introduced a Service layer, fixing the separation-of-concerns flaw from Iteration 1. However, reviewing that output revealed a second structural flaw: Controllers use field-based dependency injection (`@Autowired` on a field) directly on concrete Service classes, rather than depending on an injectable abstraction through the constructor. This is a Dependency Inversion weakness.

**TASK**
Refine the Iteration 2 code to fix this specific weakness.

**REQUIREMENTS**
1. Replace all field-based `@Autowired` injection with constructor-based injection across all Controllers.
2. Do not change any functional behavior — this is a structural refinement only.
3. Keep all class and method names the same as Iteration 2 unless a change is required to support constructor injection.

**OUTPUT**
Generate the complete updated project with constructor injection applied throughout.

**RUBRIC ALIGNMENT (Task 3, Criterion 3 — LLM Prompt Design & Iterative Refinement)**
This iteration satisfies:
- *"Responding to a structural flaw observed in the previous output"* — Iteration 2's field injection on concrete classes is a Dependency Inversion weakness; this prompt targets that specific issue.
- *"Switching from a high-level description to a component-by-component instruction"* — unlike Iterations 1 and 2 (which described desired behavior broadly), this prompt gives one precise, targeted structural instruction rather than a general description.
