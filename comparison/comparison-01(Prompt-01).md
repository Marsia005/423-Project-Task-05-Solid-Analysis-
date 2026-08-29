# Final Comparison — Task 3: SOLID Principles Compliance

## Comparison Overview

| | Original (Human, 2022 snapshot) | LLM-Generated (Single prompt) |
|---|---|---|
| Classes analyzed | 15 | 11 |
| Classes with violations | 9 | 3 |
| Clean classes | 6 | 8 |
| **Total SOLID Violation Count (SVC)** | **19** | **4** |

## Class-by-Class Breakdown — Original (Human)

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

## Class-by-Class Breakdown — LLM-Generated

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

## Dependency Inversion Score (DIS)

| | Mapper/Repository Layer | Service Layer | Controller Layer |
|---|---|---|---|
| Original (Human) | High — interfaces, constructor injection | High — interfaces, constructor injection | Low — concrete classes, field injection |
| LLM-Generated | High — interfaces (Spring Data default) | N/A — no Service layer generated | High — depends directly on Repository interfaces |

## Responsibility Entanglement Index (REI)

| | Average responsibilities per Controller |
|---|---|
| Original (Human) | 3–4 |
| LLM-Generated | 1–2 |

## Key Findings

| Finding | Explanation |
|---|---|
| LLM has fewer total violations (4 vs. 19) | Mostly because it built a smaller system — not evidence of better design |
| Controllers are the weak layer in BOTH versions | Every violation in both codebases occurs in the Controller layer; Domain and Repository classes are clean in both |
| LLM's DIS is more consistent | Spring Data's interface-based repositories made good DIP the default, even without being asked |
| LLM's REI is lower | Reflects smaller functional scope, not more disciplined separation of concerns |

## Reflection

The original codebase implements substantially more functionality than the LLM prompt requested (payment defaults, multi-table transactional writes, sequence generation), so a smaller LLM violation count is expected rather than evidence of "better" code. The more meaningful comparison is *where* violations cluster — and on that front, both versions tell the same story: Controllers are the layer most in need of discipline, in human-written and LLM-generated code alike.

## Structural Coverage Gap

Out of the 15 architectural roles present in the original human codebase, the LLM generated only 11. The 4 missing roles were:

| Missing Class | Role | Why it matters |
|---|---|---|
| AccountService | Service | LLM skipped the Service layer entirely — Controllers call Repositories directly |
| OrderService | Service | Same — checkout logic lives directly in the Controller instead |
| CatalogService | Service | Same pattern, third time — confirms this wasn't a one-off omission |
| CartItem | Domain | LLM stored cart contents as a plain list of item IDs instead of a dedicated line-item class |

**Finding:** Given only a plain functional description — with no mention of layering or SOLID — the LLM did not invent a Service layer on its own, and collapsed cart contents into a simpler data shape than the original design. This suggests these structural choices (a Service layer, a dedicated line-item class) are not default LLM behavior; they only appear when explicitly requested, which is consistent with what the [architectural evolution seen in Iterations 2 and 3](#) showed when the Service layer was specifically prompted for.
