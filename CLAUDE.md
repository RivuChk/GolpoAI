# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

GolpoAI is a Kotlin Multiplatform storytelling app that generates stories using Gemini GenAI. "Golpo" means "stories" in Bengali. The app supports both online and offline story generation across Android and iOS platforms.

## Architecture

This is a multi-module KMP project with clear separation of concerns:

- **`/composeApp`** - UI and Presentation Layer using Compose Multiplatform
- **`/shared`** - Data and Domain layers with platform-specific implementations
- **`/genai`** - Mediapipe AI/ML text generation implementation
- **`/server`** - Ktor backend server
- **`/iosApp`** - iOS-specific application wrapper

### Key Technologies

- **Navigation**: Voyager (cafe.adriel.voyager)
- **AI Integration**: generative-ai-kmp for Gemini APIs + Google Mediapipe
- **DI**: Koin
- **Database**: SQLDelight with platform drivers
- **Settings**: RussWolf Multiplatform Settings
- **Testing**: Kotest framework with MockK

### Module Structure

The shared module follows standard KMP structure with `commonMain`, `androidMain`, `iosMain`, and `jvmMain` source sets. Platform-specific implementations are provided for database drivers and dependency injection through the DI modules.

## Common Development Commands

### Building
```bash
./gradlew build                    # Build all modules
./gradlew assemble                 # Build without tests
./gradlew :composeApp:build        # Build specific module
```

### Testing
```bash
./gradlew test                     # Run all unit tests
./gradlew jvmTest                  # Run JVM-specific tests
./gradlew allTests                 # Run tests for all targets with aggregated report
./gradlew :shared:jvmTest          # Run tests for specific module
```

### Verification
```bash
./gradlew check                    # Run all checks and tests
./gradlew lint                     # Run Android lint
./gradlew lintFix                  # Run lint and apply safe fixes
```

### Platform-Specific
```bash
./gradlew iosSimulatorArm64Test    # Run iOS simulator tests
./gradlew connectedAndroidTest     # Run Android instrumentation tests
```

## Key Architectural Patterns

### Data Flow
- UI (Compose) → ScreenModel (Voyager) → UseCase → Repository → DataStore/API
- Uses Koin DI throughout with platform-specific modules
- SQLDelight for local persistence with platform-specific drivers

### AI Integration

Dual-mode story generation with flexible switching:

**Online Generation**:
- Google Generative AI SDK for cloud-based Gemini models
- Supports multiple Gemini variants (API, Nano, Vertex AI)

**Offline Generation**:
- MediaPipe LLM Inference with Gemma 3 model
- Local on-device inference for privacy and offline usage
- Platform-specific toggle for switching modes

**Core Interface**:
```kotlin
interface GenerativeModel {
    suspend fun generateStory(prompt: String, awaitReadiness: Boolean = false): Result<String>
    val isReady: StateFlow<Boolean>
}
```

**Configuration**:
- Configurable model parameters (`topK`, `maxTokens`)
- Generation settings managed through LocalGenerationSettings
- Platform-specific implementations for iOS CocoaPods integration

### Platform Abstractions
- `Platform.kt` with platform-specific implementations
- `PlatformSqlDriverFactory` for database drivers
- Platform-specific DI modules in each sourceSet