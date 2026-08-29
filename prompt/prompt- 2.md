## PROMPT 2 — SERVICE LAYER REFINEMENT

**CONTEXT**
Iteration 1 was generated from a single functional description with no architectural guidance. Reviewing that output revealed a structural flaw: Controllers called Repositories directly and contained all business logic (checkout math, inventory updates, profile editing) themselves, with no separating layer in between.

**TASK**
Rebuild the same account, order, cart, and catalog module, fixing this specific structural flaw.

**REQUIREMENTS**
1. Introduce dedicated Service classes (AccountService, OrderService, CartService, CatalogService) containing all business logic.
2. Controllers must only handle incoming HTTP requests and delegate to these Service classes.
3. Controllers must not call Repository classes directly.
4. Preserve all existing functionality from Iteration 1 — do not remove or change any user-facing behavior.

**OUTPUT**
Generate the complete updated project, including the new Service classes and revised Controllers.

**RUBRIC ALIGNMENT (Task 3, Criterion 3 — LLM Prompt Design & Iterative Refinement)**
This iteration satisfies two of the criterion's listed conditions for a valid iteration:
- *"Responding to a structural flaw observed in the previous output"* — Iteration 1 had no Service layer; this prompt directly addresses that gap.
- *"Adding or removing constraints"* — this prompt adds an explicit architectural constraint (mandatory Service layer, no direct Controller-to-Repository calls) that Iteration 1's prompt did not include.
