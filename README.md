# 🧬 Life Simulation – Organism Battle on a Board

This project simulates a dynamic environment where different types of organisms interact, move, heal, attack, and compete until only one survives. It is implemented in Java and follows an object-oriented design using inheritance and interfaces to model the behaviors of different creatures.

## 🎯 Project Overview

The simulation is built on a 10x10 board (`Board` class) and includes three types of organisms:

- **Hunter (Predator)**  
  - Can jump over 2 tiles at once
  - Detects other organisms within a 2-tile radius
  - Deals 20 energy damage to attacked organisms

- **Bird**  
  - Can jump over 3 tiles
  - Attacks other organisms for 20 energy

- **Vegan (Herbivore)**  
  - Has the ability to heal itself by 5 energy points per turn
  - Avoids combat

Each organism inherits from a common `Organism` interface or abstract class (not shown here), and movement, energy mechanics, and position updates are handled in coordination with the `Board`.

## ⚙️ Main Logic

The core simulation is run by the `LifeSimulator` class. It:

- Initializes and places the organisms on the board
- Runs a loop where each organism:
  - Attempts a strategic move (based on their unique ability)
  - Loses or gains energy based on interactions
  - Dies when energy drops to 0, and is removed from the board

The loop terminates when only one organism remains.

## 🧠 Implemented Features

- Jump-based movement (with boundary checking)
- Basic vision/radius-based interaction for hunters
- Healing mechanism for herbivores
- Collision-based combat and energy drain
- Safe position checking to avoid illegal moves
- Board state management with energy and death tracking

## 📦 Sample Classes (Expected)

- `Board`: Holds organism positions and manages updates
- `Position`: Stores coordinates of each organism
- `Organism`: Interface or base class for all living units
- `Hunter`, `Bird`, `Vegan`: Organism subclasses with specific logic

## ▶️ How to Run

1. Compile all `.java` files:
   ```bash
   javac org/life/*.java org/board/*.java
