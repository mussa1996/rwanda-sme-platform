# Rwanda SME QR Platform

A Spring Boot–based backend platform that enables **QR-based payments and digital business tools for Small and Medium Enterprises (SMEs) in Rwanda**, built on top of existing **mobile money infrastructure**.

The platform focuses on **practical impact**: helping small merchants accept digital payments, track sales, manage inventory, and build reliable transaction history without replacing banks or mobile money providers.

---

## 🎯 Project Goal

To empower Rwandan SMEs with a **simple, secure, and scalable digital operating system** that transforms everyday transactions into actionable business insights.

---

## 🚀 Key Features

- **QR-based merchant payments**
    - Static QR per merchant
    - Compatible with existing Mobile Money apps (MTN / Airtel)
    - No POS hardware required

- **Secure payment confirmation**
    - Webhook-based verification from licensed payment aggregators
    - Idempotent processing (no duplicate payments)

- **Sales & transaction tracking**
    - Automatic sales records
    - Daily, weekly, and monthly summaries

- **Inventory management**
    - Stock tracking per product
    - Automatic deduction on successful payment
    - Low-stock alerts

- **Merchant dashboard**
    - Sales analytics
    - Top products
    - Exportable reports (CSV/PDF)

- **SMS notifications**
    - Payment receipts
    - Daily summaries
    - Operational alerts

---

## 🧱 System Architecture (High Level)

- **Backend**: Spring Boot (Java 17)
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA / Hibernate
- **Security**: JWT-based authentication
- **Payments**: Mobile Money via licensed payment aggregators
- **Notifications**: SMS Gateway
- **Migrations**: Flyway

The platform does **not handle customer funds directly**.  
All money movement occurs through licensed Mobile Money providers.

---

## 🏗 Project Structure (Simplified)

