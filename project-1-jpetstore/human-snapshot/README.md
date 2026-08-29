## Selected Files Description (Detailed)

*All files from snapshot commit dd173b78 (dd173b7813008deb577837bcdd14421f19aca5d3), dated 2022-12-30.*

### 1. Account.java
**Layer:** Domain | **LOC:** 196 | **Methods:** 36 | **Inheritance:** None (implements Serializable only, a marker interface with no behavior) | **Interface Dependency:** None
**Description:** Represents a registered customer — login credentials, contact info, and UI/locale preferences. Purely a data holder; every method is a getter or setter.
**SRP Analysis:** Clean. Its only reason to change is if the shape of account data itself changes. It doesn't persist data, validate business rules, or make decisions — those live elsewhere.

### 2. Item.java
**Layer:** Domain | **LOC:** 145 | **Methods:** 25 | **Inheritance:** None (Serializable only) | **Interface Dependency:** None
**Description:** Represents a purchasable item variant — pricing, supplier, stock quantity, and a reference to its parent Product.
**SRP Analysis:** Clean. Pure data-holder; its one reason to change is the item schema itself.

### 3. Order.java
**Layer:** Domain | **LOC:** 335 | **Methods:** 58 | **Inheritance:** None (Serializable only) | **Interface Dependency:** None
**Description:** Represents a customer order — shipping/billing data, payment info, line items, plus an `initOrder()` method that populates a new order from an Account and Cart.
**SRP Analysis:** Violation. `initOrder()` hardcodes business defaults directly in the domain class — a placeholder card number, expiry date, card type, courier, and locale. This gives Order two reasons to change: the data schema, and the business rules around defaults.

### 4. AccountMapper.java
**Layer:** Mapper (Repository) | **LOC:** 43 | **Methods:** 8 | **Inheritance:** N/A (this is an interface) | **Interface Dependency:** N/A (it IS the interface other classes depend on)
**Description:** MyBatis interface for account persistence — retrieving accounts, and separately inserting/updating account, profile, and signon data.
**SRP Analysis:** Not a SRP violation — every method is account persistence, one coherent job. (Its 8 bundled methods raise a separate Interface Segregation concern, not SRP.)

### 5. ItemMapper.java
**Layer:** Mapper (Repository) | **LOC:** 38 | **Methods:** 4 | **Inheritance:** N/A (interface) | **Interface Dependency:** N/A
**Description:** MyBatis interface for item/inventory access — fetch item, fetch by product, read/update stock.
**SRP Analysis:** Clean. Narrow, coherent, one job.

### 6. OrderMapper.java
**Layer:** Mapper (Repository) | **LOC:** 37 | **Methods:** 4 | **Inheritance:** N/A (interface) | **Interface Dependency:** N/A
**Description:** MyBatis interface for order persistence — fetch orders, insert order and its status.
**SRP Analysis:** Clean. Same reasoning as ItemMapper.

### 7. AccountService.java
**Layer:** Service | **LOC:** 75 | **Methods:** 4 | **Inheritance:** None | **Interface Dependency:** Yes — depends on AccountMapper (interface, constructor-injected)
**Description:** Coordinates account business logic — fetch, create (across 3 tables in one transaction), update (with conditional password change).
**SRP Analysis:** Borderline violation. Conceptually one responsibility, but `insertAccount()` hardcodes the exact 3-step save sequence inline.

### 8. OrderService.java
**Layer:** Service | **LOC:** 132 | **Methods:** 4 | **Inheritance:** None | **Interface Dependency:** Yes — depends on 4 mapper interfaces (ItemMapper, OrderMapper, SequenceMapper, LineItemMapper), all constructor-injected
**Description:** Coordinates order placement — assign ID, decrement inventory, persist order + line items, retrieve past orders, generate sequence numbers.
**SRP Analysis:** Violation. `getNextId()` is generic sequence-generation, unrelated to order logic specifically.

### 9. AccountActionBean.java
**Layer:** Controller (Stripes ActionBean) | **LOC:** 208 | **Methods:** 19 | **Inheritance:** Yes — extends AbstractActionBean | **Interface Dependency:** None — depends on concrete AccountService and CatalogService classes
**Description:** Web-layer entry point for account creation, profile editing, sign-on/off, and auth-state checks.
**SRP Analysis:** Clear violation. At least 4 distinct concerns in one class: account creation, profile editing, session/auth management, and pulling a product list from CatalogService.

### 10. OrderActionBean.java
**Layer:** Controller (Stripes ActionBean) | **LOC:** 197 | **Methods:** 16 | **Inheritance:** Yes — extends AbstractActionBean | **Interface Dependency:** None — depends on concrete OrderService
**Description:** Web-layer entry point for order listing, multi-step checkout, and order viewing.
**SRP Analysis:** Violation. Combines listing, checkout orchestration, and viewing; also does unchecked casts to grab other ActionBeans directly from the session.

### 11. Cart.java
**Layer:** Domain | **LOC:** 125 | **Methods:** 12 | **Inheritance:** None (Serializable only) | **Interface Dependency:** None
**Description:** Holds cart items and computes the running subtotal via `getSubTotal()`.
**SRP Analysis:** Mild violation. Mixes "holds cart data" with "calculates monetary totals."

### 12. CartItem.java
**Layer:** Domain | **LOC:** 76 | **Methods:** 9 | **Inheritance:** None (Serializable only) | **Interface Dependency:** None
**Description:** One cart line — an Item reference, quantity, and `calculateTotal()` for that line.
**SRP Analysis:** Not a violation. `calculateTotal()` only concerns this object's own two fields.

### 13. CatalogService.java
**Layer:** Service | **LOC:** 89 | **Methods:** 9 | **Inheritance:** None | **Interface Dependency:** Yes — depends on 3 mapper interfaces, constructor-injected
**Description:** Coordinates catalog logic — categories, products, items, stock checks.
**SRP Analysis:** Not a violation. Every method is catalog data retrieval; well-scoped.

### 14. CartActionBean.java
**Layer:** Controller (Stripes ActionBean) | **LOC:** 139 | **Methods:** 10 | **Inheritance:** Yes — extends AbstractActionBean | **Interface Dependency:** None — depends on concrete CatalogService
**Description:** Web-layer entry point for add/remove cart items, quantity updates, and view routing.
**SRP Analysis:** Violation. At least 3 distinct concerns bundled, plus quantity-parsing that silently swallows exceptions.

### 15. CatalogActionBean.java
**Layer:** Controller (Stripes ActionBean) | **LOC:** 219 | **Methods:** 26 | **Inheritance:** Yes — extends AbstractActionBean | **Interface Dependency:** None — depends on concrete CatalogService
**Description:** Web-layer entry point for category browsing, product/item viewing, and search.
**SRP Analysis:** Clearest violation in the set, and the largest class analyzed. Four unrelated user-facing features live in one file.
