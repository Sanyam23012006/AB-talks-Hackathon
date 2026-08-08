# ABTalks 60-Day Coding Challenge - Context & Rules

## 🎯 Core Problem
A mobile-first (390px) coding challenge platform tailored for Indian college students coding late at night after classes.

## 💻 Internal Commands
- **Build**: `mvn clean compile`
- **Test**: `mvn test`
- **Run**: `mvn spring-boot:run`

## 🛠️ Tech Stack
- **Core**: Java 21, Spring Boot 3.3.x
- **Data**: Spring Data JPA, Hibernate, MySQL 8 (with fallback to H2 in-memory)
- **Frontend**: Thymeleaf, Tailwind CSS CDN, Lucide Icons
- **Tools & Utilities**: Lombok, Jakarta Validation

## 🛣️ Mandatory Routes
- `/` -> Landing Page
- `/dashboard` -> Student Dashboard
- `/day/{dayId}` (e.g., `/day/12`) -> Day Challenge View

## 📱 Viewport & UI Rules
- **Mobile Viewport**: Strictly `390px` width.
- **Container Shell**: Must use the following utility classes:
  `max-w-[390px] mx-auto min-h-screen bg-[#0B0F19] text-slate-100 font-sans border-x border-slate-800 shadow-2xl relative pb-24`
- **Accessibility**: Minimum touch target height of `48px` for all clickable elements (buttons, links, inputs).

## 🧠 Business Logic Invariants
- **Timezone**: The application must enforce the `Asia/Kolkata` timezone globally.
- **2:00 AM IST Grace Cutoff**: Submissions made between 00:00 (12:00 AM) and 02:00 AM IST count towards *yesterday's* streak.
- **Streak Shields**: A 1-Day gap is automatically consumed via `freezePasses` (if available to the user).
- **Validation**: Regex enforcement is mandatory on:
  - GitHub commit URLs
  - LinkedIn post URLs
