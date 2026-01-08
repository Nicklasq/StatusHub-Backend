# StatusHub-Backend
StatusHub

StatusHub er en proof-of-concept webapplikation, der samler information om IT-miljøer, deployments og driftsopdateringer i ét fælles dashboard.

Projektet er udviklet som studieprojekt på Datamatikeruddannelsen (KEA).

Features

Environments med status (OK, ISSUES, FREEZE, DOWN)

Posts for deployments, incidents og information

Kommentarer på posts

Rollebaseret adgang (ADMIN / VIEWER)

Simpelt login og brugeroprettelse

Dashboard med samlet overblik

Tech Stack

Backend

Java, Spring Boot

Spring Data JPA, Hibernate

MySQL (Docker)

Frontend

HTML, CSS, JavaScript (vanilla)

Run locally
Backend
docker-compose up -d
./mvnw spring-boot:run


Backend kører på:
http://localhost:8080

Frontend

Åbn index.html i browseren
(eller brug Live Server i VS Code)

Login

Brugere kan oprettes via login-siden

Default rolle: VIEWER

ADMIN opnås via admin-kode (prototype)

⚠️ Sikkerhed er forenklet og ikke produktionsklar.

Authors

Nicklas Sang Cao

Magnus Sørensen

Datamatiker, KEA

Status

Prototype / Proof-of-Concept
