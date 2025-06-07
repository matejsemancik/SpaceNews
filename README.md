# SpaceNews

A Kotlin Multiplatform mobile application that fetches and displays content from the [Spaceflight News API](https://api.spaceflightnewsapi.net/v4/docs/#/articles/articles_list).
Also my playground to test new things.

## Tech Stack
- **UI Layer**
  - Compose Multiplatform for shared UI components
  - Native screen presentation on iOS for optimal performance
  - Decompose for navigation and screen management

- **Architecture**
  - MVI-like architecture with clear separation of concerns
  - UseCase pattern for business logic
  - Repository pattern for data management
  - Flow-based reactive programming

- **Cross-Platform**
  - Kotlin Multiplatform for shared business logic
  - MOKO Resources for shared string resources
  - Ktor for networking
  - Kotlinx Serialization for JSON parsing




Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
