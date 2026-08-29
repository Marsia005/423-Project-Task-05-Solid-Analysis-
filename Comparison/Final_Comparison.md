# Final Comparison — Task 3: SOLID Principles Compliance

## Table 1 — SOLID Audit: Original (Human, 2022 snapshot, 15 classes)

| Class | Layer | Violations |
|---|---|---|
| Account | Domain | None |
| Item | Domain | None |
| ItemMapper | Repository | None |
| OrderMapper | Repository | None |
| CartItem | Domain | None |
| CatalogService | Service | None |
| Order | Domain | SRP, OCP |
| AccountMapper | Repository | ISP |
| AccountService | Service | OCP |
| OrderService | Service | SRP, OCP |
| Cart | Domain | SRP |
| AccountActionBean | Controller | SRP, OCP, DIP |
| OrderActionBean | Controller | SRP, OCP, DIP |
| CartActionBean | Controller | SRP, OCP, DIP |
| CatalogActionBean | Controller | SRP, OCP, DIP |

**Total: 9 of 15 classes violated. SVC = 19.**

## Table 2 — SOLID Audit: LLM Iteration 1 (11 classes, single vague prompt)

| Class | Layer | Violations |
|---|---|---|
| Account | Domain | None |
| Item | Domain | None |
| Cart | Domain | None |
| Order | Domain | None |
| AccountRepository | Repository | None |
| ItemRepository | Repository | None |
| OrderRepository | Repository | None |
| CatalogController | Controller | None |
| AccountController | Controller | SRP |
| OrderController | Controller | SRP, OCP |
| CartController | Controller | SRP |

**Total: 3 of 11 classes violated. SVC = 4.**

## Table 3 — SOLID Audit: LLM Iteration 2 (15 classes, Service layer added)

| Class | Layer | Violations |
|---|---|---|
| Account | Domain | None |
| Item | Domain | None |
| Order | Domain | None |
| Cart | Domain | None |
| AccountRepository | Repository | None |
| ItemRepository | Repository | None |
| OrderRepository | Repository | None |
| OrderService | Service | None |
| CartService | Service | None |
| CatalogService | Service | None |
| AccountService | Service | OCP |
| AccountController | Controller | DIP |
| OrderController | Controller | DIP |
| CartController | Controller | DIP |
| CatalogController | Controller | DIP |

**Total: 5 of 15 classes violated. SVC = 5.**

## Table 4 — SOLID Audit: LLM Iteration 3 (16 classes, constructor injection)

| Class | Layer | Violations |
|---|---|---|
| Account | Domain | None |
| Item | Domain | None |
| Order | Domain | None |
| Cart | Domain | None |
| CartItem | Domain | None |
| AccountRepository | Repository | None |
| ItemRepository | Repository | None |
| OrderRepository | Repository | None |
| OrderService | Service | None |
| CartService | Service | None |
| CatalogService | Service | None |
| AccountService | Service | OCP |
| AccountController | Controller | DIP (partial — see note below) |
| OrderController | Controller | DIP (partial — see note below) |
| CartController | Controller | DIP (partial — see note below) |
| CatalogController | Controller | DIP (partial — see note below) |

**Total: 5 of 16 classes violated. SVC = 5.**

> **Important precision note:** Iteration 3's Controllers switched from field injection (`@Autowired`) to constructor injection, which is what Prompt 3 explicitly requested. This is a genuine, verified improvement in dependency explicitness and testability. However, to be fully precise: a *strict* DIP fix would also require the Services themselves to be interfaces (e.g., `AccountService` interface + `AccountServiceImpl` class), which was not part of Prompt 3's scope. So Controllers still technically depend on concrete Service classes — just now via a cleaner injection mechanism. This is marked "partial" rather than "resolved" so the analysis stays honest about exactly what changed.

## Table 5 — Overall Comparison

| | Original (Human) | Iteration 1 | Iteration 2 | Iteration 3 |
|---|---|---|---|---|
| Classes analyzed | 15 | 11 | 15 | 16 |
| Classes with violations | 9 | 3 | 5 | 5 |
| Clean classes | 6 | 8 | 10 | 11 |
| **SOLID Violation Count (SVC)** | **19** | **4** | **5** | **5** |

## Table 6 — Dependency Inversion Score (DIS) by Layer

| Layer | Original (Human) | Iteration 1 | Iteration 2 | Iteration 3 |
|---|---|---|---|---|
| Repository/Mapper | High (interfaces, constructor injection) | High (interfaces) | High (interfaces) | High (interfaces) |
| Service | High (interfaces, constructor injection) | N/A — no Service layer | Low (concrete class dependency in AccountService's own sequencing) | Low (same) |
| Controller | Low (concrete Service classes, field injection) | High (direct interface dependency, no Service layer to complicate this) | Low (concrete Service classes, field injection) | Low-Medium (concrete Service classes, but constructor injection) |

## Table 7 — Responsibility Entanglement Index (REI), Controller Layer Only

| | Original (Human) | Iteration 1 | Iteration 2 | Iteration 3 |
|---|---|---|---|---|
| Avg. responsibilities per Controller | 3–4 | 1–2 | 1 | 1 |

## Discussion

**Finding 1 — Violation count is not monotonic, and that's meaningful.**
SVC went 19 → 4 → 5 → 5. It did *not* keep dropping as prompts got more specific — Iteration 1 actually has the fewest violations of all four versions. This is because Iteration 1 has the smallest scope (no Service layer at all means fewer classes exist to have problems in). Once a Service layer was added in Iteration 2, a new violation appeared (`AccountService`'s hardcoded save sequence) that didn't exist when there was no Service layer to have that problem in the first place. **The lesson: fewer classes can mean fewer violations without meaning better design** — SVC alone can be misleading without looking at what's actually being measured.

**Finding 2 — Controllers are the consistently weak layer, across every version, human or LLM.**
In all four versions — original and all three LLM iterations — every violation found in a Controller-layer class was some combination of SRP, OCP, or DIP. Not one Controller class, in any version, was ever fully clean except Iteration 1's `CatalogController` (which had almost no logic to begin with). This is the single strongest, most repeatable finding across the whole comparison.

**Finding 3 — Constructor injection improved explicitness but did not fully resolve DIP.**
This is the most important nuance in the whole analysis, and worth stating plainly to avoid overclaiming: Prompt 3 asked for constructor injection, and the LLM delivered exactly that — correctly, consistently, across all 4 controllers. But constructor injection alone doesn't make a dependency an *abstraction* — the Services being injected are still concrete classes, not interfaces. A truly complete DIP fix would require one more round (Prompt 4, not required by this task) introducing Service interfaces. This is flagged honestly here rather than claimed as fully resolved.

**Finding 4 — The LLM never introduced a Service layer unprompted.**
Given only a plain functional description (Iteration 1), the LLM did not decide on its own to add a Service layer — a structural choice a deliberate human architect made in the original 2022 code. It took an explicit instruction (Prompt 2) to introduce one. This suggests Service-layer separation is not a "default best practice" an LLM reaches for automatically; it requires the requester to know to ask for it.

## Reflection

This comparison shows that LLM-generated code does not organically converge toward the same architectural discipline present in the original human-written codebase — even a well-structured discipline the human code only partially achieved. Structural improvements (a Service layer, constructor injection) only appeared when specifically requested, and even then, improvements were sometimes only partial (constructor injection without interface-based Services). The one constant across every version analyzed — human and LLM alike — is that the Controller layer is where responsibility separation breaks down most consistently, suggesting this may be a genuinely hard boundary to get right regardless of who (or what) is writing the code.

One honest limitation of this analysis: SOLID scoring at this scale involved manual, qualitative judgment rather than automated static analysis tooling, so some subjectivity is inherent to counts like SVC and REI reported here.
