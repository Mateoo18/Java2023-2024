# 🌌 StarMap – Interactive Star and Constellation Viewer

StarMap is an interactive JavaFX-based desktop application that allows users to view, manage, and manipulate a star map with stars and constellations loaded from a JSON file. The application provides both graphical visualization and data editing capabilities.

## ✨ Features

- 📍 Display stars and constellations on a canvas
- 💡 Brightness-based star visuals
- 📐 XY axis toggle
- 🖱️ Drag-and-drop star repositioning
- 🧩 Add and remove stars and constellations
- 🔁 Edit star brightness and names
- 🔄 Auto-save changes back to `stars.json`
- 🌈 Random color generation per constellation
- 📚 Menu interface for all actions (Add, Delete, Edit, View)

## 📁 Project Structure

```
src/
├── controller/
│   └── StarMapController.java
├── model/
│   ├── Star.java
│   └── Constellation.java
├── utils/
│   ├── DataLoader.java
│   └── DataWriter.java
├── view/
│   └── StarMapView.java
│   └── NumberSizeException.java
├── resources/
│   └── stars.json
```

## 🧠 Architecture Overview

- **MVC Pattern**:
  - `StarMapController`: Logic and data flow
  - `StarMapView`: JavaFX canvas UI with event handling and rendering
  - `Star` & `Constellation`: Domain models
- **Persistence**:
  - JSON-based input/output with `DataLoader` and `DataWriter`

## 🔧 How to Run

1. Ensure you have **Java 11+** and **JavaFX** configured.
2. Place a valid `stars.json` file in `src/main/resources/`.
3. Compile and run the application using your IDE or:

```bash
javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls -d out $(find ./src -name "*.java")
java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls -cp out org.starmap.Main
```

## 📝 JSON File Format (`stars.json`)

```json
{
  "stars": [
    { "name": "Sirius", "xPosition": 100, "yPosition": 300, "brightness": 2.5 },
    ...
  ],
  "constellations": [
    { "name": "Orion", "stars": ["Betelgeuse", "Rigel", "Bellatrix"] }
  ]
}
```

## ✅ Validation Rules

- `xPosition`: 10 – 1024
- `yPosition`: 0 – 768
- `brightness`: 0 – 7

Invalid values will raise a `NumberSizeException`.

## 🛠️ Technologies

- Java 11+
- JavaFX
- JSON parsing via `org.json`
- MVC Architecture


