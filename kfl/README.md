# kfl - OpenFL for Kotlin (JVM)

`kfl` is a high-fidelity Kotlin port of the OpenFL framework, targetting the JVM and designed with multiplatform-ready library selections. It recreates the core OpenFL systems (display list, events, rendering, assets, input, etc.) while mapping them to modern Kotlin-compatible libraries.

## 🚀 Key Library Mappings

To provide a high-performance experience on the JVM, `kfl` leverages the following ecosystem:

| Subsystem | Original (OpenFL/Lime) | kfl Implementation |
| :--- | :--- | :--- |
| **2D Rendering** | Canvas / Cairo | **Skiko (Skia)** |
| **Windowing & Input** | SDL2 / Lime | **LWJGL (GLFW)** |
| **GPU Rendering** | OpenGL | **LWJGL (OpenGL)** |
| **Audio** | OpenAL / SDL_Mixer | **JVM Sound (javax.sound.sampled)** |
| **Networking** | URLLoader (Lime) | **Ktor** |
| **Async / Concurrency** | Haxe Threads | **Kotlin Coroutines** |
| **Build System** | Haxelib / Lime | **Gradle (Kotlin DSL)** |

## 🏗️ Architecture

### Display List
The display list follows the standard OpenFL hierarchy (`Stage` -> `DisplayObjectContainer` -> `Sprite` -> `Bitmap`). 2D rendering is handled by the `Graphics` class, which records drawing commands and executes them using the Skia GPU-backed canvas via Skiko.

### Events
The event system supports full bubbling and capture phases. Input events from GLFW are polled and translated into native OpenFL `MouseEvent` and `KeyboardEvent` instances.

### Stage3D
Full Stage3D support is provided via the `openfl.display3D` package, which binds directly to OpenGL via LWJGL. This allows for high-performance custom shaders and advanced 3D rendering pipelines.

### Assets & I/O
- `openfl.utils.ByteArray` is implemented using `java.nio.ByteBuffer` for high-performance binary I/O.
- `openfl.net.URLLoader` uses Ktor for non-blocking asynchronous requests.
- `openfl.media.Sound` loads and plays audio through the standard JVM audio system.

## 📦 Project Structure

```text
kfl/
├── src/
│   ├── main/kotlin/openfl/       # Ported OpenFL Source
│   │   ├── display/              # Display List & Rendering
│   │   ├── events/               # Event System
│   │   ├── geom/                 # Geometry & Math
│   │   ├── net/                  # Networking (Ktor)
│   │   ├── media/                # Sound (JVM)
│   │   ├── display3D/            # Stage3D (LWJGL)
│   │   └── ...                   # Other OpenFL packages
│   └── test/kotlin/openfl/       # Unit Tests
├── build.gradle.kts              # Gradle Configuration
└── README.md                     # This file
```

## 🛠️ Usage

### Setup
Add the library to your Gradle project:

```kotlin
dependencies {
    implementation("openfl:kfl:1.0.0")
}
```

### Basic Application
```kotlin
import openfl.display.Sprite
import openfl.display.Stage
import openfl.events.Event

class Main : Sprite() {
    init {
        addEventListener(Event.ADDED_TO_STAGE) {
            graphics.beginFill(0xFF0000)
            graphics.drawRect(0.0, 0.0, 100.0, 100.0)
            graphics.endFill()
        }
    }
}
```

## ⚖️ License
This port is provided under the same license terms as the original OpenFL framework.
