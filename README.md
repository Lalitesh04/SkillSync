# SkillSync 💼🚀  
**AI-Powered Freelance Collaboration Platform**

An enterprise-grade platform that connects freelancers and clients through smart gig matching, real-time collaboration, and modular microservices.

---

## 📌 Overview

SkillSync is a scalable full-stack application where freelancers can:

- Create profiles with skill tags  
- Get smart gig recommendations via ML  
- Collaborate with teams in real-time (chat, task boards)  
- Apply for jobs, message clients, and track progress  

**Built using a microservices architecture with:**

- Spring Boot (Auth, User, Gig Management)  
- FastAPI (ML/NLP Matching Engine)  
- Django (Admin Panel & Analytics)  
- React + Tailwind CSS (Frontend)  
- Kafka, Redis, PostgreSQL, MongoDB for infrastructure  

---

## 🧩 Modules

| Module No. | Name                    | Description                                                    | Tech Stack               |
|------------|-------------------------|----------------------------------------------------------------|--------------------------|
| 1          | User & Auth Service     | Registration, login, role management (OAuth2, JWT)             | Spring Boot              |
| 2          | Profile & Skill Mgmt    | User profile creation/editing, skill tagging, portfolio upload | Spring Boot              |
| 3          | Gig/Job Service         | Post, apply, shortlist, assign gigs/projects                   | Spring Boot              |
| 4          | ML Recommendation       | Skill-to-gig matching using NLP and ML models                  | FastAPI                  |
| 5          | Real-Time Chat Service  | One-on-one/group chat using WebSockets & Redis Pub/Sub         | Spring Boot              |
| 6          | Notification Service    | Alerts for gigs, chat, system updates                          | Spring Boot + Kafka      |
| 7          | Task/Kanban Board       | Drag-and-drop task management                                  | React                    |
| 8          | Admin Dashboard         | User moderation, gig approvals, analytics                      | Django                   |
| 9          | Frontend Client         | Freelancer/client dashboards, gig browsing                     | React + Tailwind CSS     |

---

## 👥 User Roles

| Role        | Description                         | Key Permissions                                     |
|-------------|-------------------------------------|----------------------------------------------------|
| Freelancer  | Offers services, applies to gigs    | Profile management, apply gigs, chat, task tracking |
| Client      | Posts gigs/projects                 | Create gigs, shortlist freelancers, assign tasks   |
| Admin       | Platform moderator                  | User/gig moderation, analytics dashboard           |
| Super Admin | Oversees platform & infrastructure  | Manage admins, system health, billing logs         |


---

## 🛠️ Tech Stack

| Layer        | Technology                                         |
|--------------|----------------------------------------------------|
| Frontend     | React, Tailwind CSS                                |
| Backend      | Spring Boot (Auth, Gigs, Chat), Django (Admin), FastAPI (ML) |
| ML/NLP       | FastAPI, Scikit-learn, spaCy / BERT                |
| Databases    | PostgreSQL (core), MongoDB (chat), Redis (cache/pub-sub) |
| Messaging    | Kafka (notifications), WebSockets (chat)           |
| Auth & Crypto| OAuth2, JWT, AES-GCM, RSA encryption               |

---

## 📄 What is a Gig?

A **Gig** is a freelance job posted by a client that freelancers can browse and apply to. It includes:

- Title and detailed description  
- Required skills  
- Budget and deadlines  
- Status and assigned freelancers  
- Applications and client details  

### 🔄 Typical Workflow:

1. Client posts a gig  
2. Freelancers browse and apply  
3. Client selects freelancer(s)  
4. Freelancers work and update progress  
5. Completion and client feedback  

---


## 💡 Concept

A real-time collaboration platform where freelancers can create profiles, showcase skills, apply for gigs, form teams, and collaborate via integrated tools like chat, code sharing, and more.

> 🛠️ **Kanban-based task management** will be introduced in future releases to enhance team productivity and project tracking.
