# Human Snapshot — SOLID Analysis
### Repository: mybatis/jpetstore-6 | Commit: dd173b78 (2022-12-30)

---

## Part 1 — Class Details

| # | Class | Layer | LOC | Methods | Inheritance | Interface Dependency |
|---|---|---|---|---|---|---|
| 1 | Account.java | Domain | 196 | 36 | None (Serializable only) | None |
| 2 | Item.java | Domain | 145 | 25 | None (Serializable only) | None |
| 3 | Order.java | Domain | 335 | 58 | None (Serializable only) | None |
| 4 | AccountMapper.java | Mapper (Repository) | 43 | 8 | N/A (interface) | N/A |
| 5 | ItemMapper.java | Mapper (Repository) | 38 | 4 | N/A (interface) | N/A |
| 6 | OrderMapper.java | Mapper (Repository) | 37 | 4 | N/A (interface) | N/A |
| 7 | AccountService.java | Service | 75 | 4 | None | Yes — AccountMapper |
| 8 | OrderService.java | Service | 132 | 4 | None | Yes — 4 mapper interfaces |
| 9 | AccountActionBean.java | Controller | 208 | 19 | Yes — extends AbstractActionBean | None (concrete classes) |
| 10 | OrderActionBean.java | Controller | 197 | 16 | Yes — extends AbstractActionBean | None (concrete classes) |
| 11 | Cart.java | Domain | 125 | 12 | None (Serializable only) | None |
| 12 | CartItem.java | Domain | 76 | 9 | None (Serializable only) | None |
| 13 | CatalogService.java | Service | 89 | 9 | None | Yes — 3 mapper interfaces |
| 14 | CartActionBean.java | Controller | 139 | 10 | Yes — extends AbstractActionBean | None (concrete classes) |
| 15 | CatalogActionBean.java | Controller | 219 | 26 | Yes — extends AbstractActionBean | None (concrete classes) |

---

## Part 2 — Class Descriptions

| # | Class | Description |
|---|---|---|
| 1 | Account.java | Represents a registered customer — login credentials, contact info, and UI/locale preferences. Pure data holder. |
| 2 | Item.java | Represents a purchasable item variant — pricing, supplier, stock quantity, reference to parent Product. |
| 3 | Order.java | Represents a customer order — shipping/billing data, payment info, line items, plus `initOrder()` which populates a new order from an Account and Cart. |
| 4 | AccountMapper.java | MyBatis interface for account persistence — retrieve, insert/update account, profile, and signon data. |
| 5 | ItemMapper.java | MyBatis interface for item/inventory access — fetch item, fetch by product, read/update stock. |
| 6 | OrderMapper.java | MyBatis interface for order persistence — fetch orders, insert order and its status. |
| 7 | AccountService.java | Coordinates account business logic — fetch, create (3 tables in one transaction), update. |
| 8 | OrderService.java | Coordinates order placement — assign ID, decrement inventory, persist order + line items, generate sequence numbers. |
| 9 | AccountActionBean.java | Web-layer entry point for account creation, profile editing, sign-on/off, and auth-state checks. |
| 10 | OrderActionBean.java | Web-layer entry point for order listing, multi-step checkout, and order viewing. |
| 11 | Cart.java | Holds cart items and computes the running subtotal via `getSubTotal()`. |
| 12 | CartItem.java | One cart line — an Item reference, quantity, and `calculateTotal()` for that line. |
| 13 | CatalogService.java | Coordinates catalog logic — categories, products, items, stock checks. |
| 14 | CartActionBean.java | Web-layer entry point for add/remove cart items, quantity updates, and view routing. |
| 15 | CatalogActionBean.java | Web-layer entry point for category browsing, product/item viewing, and search. |

---

## Part 3 — SOLID Analysis (SRP Focus)

| # | Class | SRP Status | Reason |
|---|---|---|---|
| 1 | Account.java | ✅ Clean | Pure data holder; only reason to change is if account data shape changes |
| 2 | Item.java | ✅ Clean | Pure data holder; one reason to change is item schema |
| 3 | Order.java | ❌ Violation | `initOrder()` hardcodes business defaults (card number, expiry, courier) directly in the domain class |
| 4 | AccountMapper.java | ✅ Clean | Every method is account persistence, one coherent job |
| 5 | ItemMapper.java | ✅ Clean | Narrow, coherent, one job |
| 6 | OrderMapper.java | ✅ Clean | Same reasoning as ItemMapper |
| 7 | AccountService.java | ⚠️ Borderline | `insertAccount()` hardcodes the exact 3-step save sequence inline |
| 8 | OrderService.java | ❌ Violation | `getNextId()` is generic sequence-generation, unrelated to order logic |
| 9 | AccountActionBean.java | ❌ Violation | 4 distinct concerns: account creation, profile editing, session/auth, product list pulling |
| 10 | OrderActionBean.java | ❌ Violation | Combines listing, checkout, viewing; unchecked casts to grab other beans from session |
| 11 | Cart.java | ⚠️ Mild violation | Mixes "holds cart data" with "calculates monetary totals" |
| 12 | CartItem.java | ✅ Clean | `calculateTotal()` only concerns this object's own two fields |
| 13 | CatalogService.java | ✅ Clean | Every method is catalog data retrieval; well-scoped |
| 14 | CartActionBean.java | ❌ Violation | 3 concerns bundled; silently swallows parse exceptions |
| 15 | CatalogActionBean.java | ❌ Violation | 4 unrelated features in one file; largest class analyzed |

---

## Summary

- **9 of 15 classes violate SRP** (including 1 borderline, 1 mild)
- **6 of 15 classes are fully clean**
- **Total SOLID Violation Count (SVC): 19**

**Pattern:** every Controller-layer class has a violation. Every clean class is a Domain object or Mapper interface.
