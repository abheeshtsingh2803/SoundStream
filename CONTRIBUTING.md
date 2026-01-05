# Contributing to SoundStream 🎵

Thank you for your interest in contributing to **SoundStream**!  
We welcome contributions that help improve code quality, add features, fix bugs, or enhance documentation.

SoundStream is a **Spring Boot–based music streaming backend** inspired by platforms like Spotify. It focuses on **clean architecture, scalable REST APIs, and real-world JPA/Hibernate best practices**.

---

## Code of Conduct

This project follows a [Code of Conduct](./CODE_OF_CONDUCT.md).  
By participating, you agree to uphold a respectful, inclusive, and professional environment.

---

## How You Can Contribute

You can contribute in several ways:

### 🐞 Reporting Bugs
If you find a bug, please open a **GitHub Issue** and include:
- Clear steps to reproduce the issue
- Expected behavior
- Actual behavior
- Relevant logs or error messages
- Your environment (Java version, OS, database)

---

### 💡 Suggesting Enhancements
Feature ideas and improvements are welcome!  
Open a **Feature Request** issue describing:
- The problem it solves
- Why it is useful
- Any implementation ideas (optional)

---

### 💻 Contributing Code

Follow these steps to contribute code:

## 1️⃣ Fork the Repository
Click the **Fork** button on GitHub to create your own copy.

## 2️⃣ Clone Your Fork
```bash
git clone https://github.com/abheeshtsingh2803/soundstream.git
cd soundstream
```
## 3️⃣ Create a Feature Branch
Always create a new branch for your changes:
```
git checkout -b feature/short-description
```
## 4️⃣ Make Your Changes
You can work on:
- APIs (Songs, Artists, Albums, Playlists)
- DTOs & mappers
- Validation
- Exception handling
- Tests
- Documentation
- Performance improvements

Please follow existing project structure and conventions.

## 5️⃣ Test Your Changes
Backend (Spring Boot)
```bash
./gradlew clean build
./gradlew bootRun
```
Ensure:
- Application starts successfully
- APIs behave as expected
- No new warnings or errors appear in logs
- If you add new features, include relevant tests where possible.

## 6️⃣ Commit Your Changes
Write clear and meaningful commit messages:
```bash
git add .
git commit -m "Add validation for CreateSongRequest"
```

Commit message guidelines:
- Use present tense (Add, Fix, Improve)
- Keep summary under 50 characters
- Be descriptive but concise

## 7️⃣ Push Your Branch
```
git push origin feature/short-description
```
## 8️⃣ Open a Pull Request
- Open a PR against the main branch
- Clearly describe what your change does
- Reference related issues if applicable
- Be open to feedback and requested changes

---

## Testing Guidelines
- While the project is evolving, contributors are encouraged to:
- Add unit tests for services
- Add controller tests using MockMvc
- Avoid breaking existing APIs
- Testcontainers and integration tests are planned future enhancements.

## Coding Guidelines
- Follow Java naming conventions
- Avoid exposing JPA entities directly from controllers
- Prefer DTOs for API contracts
- Do not log JPA entities directly
- Use constructor injection (@RequiredArgsConstructor)
- Keep services thin and focused

## Project Roadmap (Open for Contributions)
- Swagger / OpenAPI documentation
- Docker & docker-compose support
- Testcontainers integration
- MapStruct for DTO mapping
- Role-based authentication
- Performance optimization (N+1 query fixes)

## Acknowledgments
Thanks to the open-source community and tools that make this project possible:
- Spring Boot
- Hibernate / JPA
- PostgreSQL
- Docker

And thank you to every contributor who helps improve SoundStream 🚀