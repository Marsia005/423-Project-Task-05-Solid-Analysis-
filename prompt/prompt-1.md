# LLM Prompt — Task 3: SOLID Principles Compliance

## PROMPT — SYSTEM RECONSTRUCTION

**CONTEXT**
You are a Java developer building a Spring Boot backend from scratch, based only on the functional description below. You have not seen and must not reference any existing implementation of this system.

**TASK**
Build a Java Spring Boot module for a pet store's account, order, cart, and catalog management system.

**FUNCTIONAL REQUIREMENTS**
- Users can sign up for a new account and log in
- Users can view and edit their profile information
- Users can browse the catalog of items and view individual item details
- Users can add items to a shopping cart
- Users can place an order containing multiple items from their cart
- Checkout must calculate the total price, reduce item stock accordingly, and save the order
- Users can view their past orders

**RULES**
1. Do not reference, search for, or assume the structure of any existing pet store codebase.
2. Generate a complete, compilable project — not just a few isolated classes.
3. Use reasonable, standard Spring Boot conventions. Do not deliberately optimize for any specific design principle — just build it functionally, the way you normally would from this description alone.

**OUTPUT**
Generate all necessary classes (domain/entity classes, repositories, controllers, and any other components you judge necessary) to fulfill the requirements above.
