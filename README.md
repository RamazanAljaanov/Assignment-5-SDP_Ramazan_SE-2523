# Route Planner — Strategy Pattern

A Java console application that demonstrates the Strategy design pattern with route-planning examples around Astana.

This is an educational simulation, not a navigation service. It does not call Google Maps or 2GIS APIs. The program calculates estimates from straight-line coordinates and mode-specific factors; the street names and turn-by-turn instructions are illustrative examples.

## Requirements

- JDK 17 or newer
- Apache Maven

The Maven compiler is configured with `--release 17`, so the project targets Java 17 even when it is run with a newer JDK.

## Build and run

Open a terminal in the project root and run:

```bash
mvn clean package
mvn exec:java
```

The console guides you through these choices:

1. Select a starting location from the predefined Astana locations.
2. Select a different destination.
3. Select a transport mode: car, walking, bicycle, or public transit.
4. Choose whether to plan another route. The same `Navigator` can then use a different strategy.

Invalid menu input is rejected and prompted again. To change the predefined locations, edit the `locations` list in `Main.java`.

## Project structure

```text
src/main/java/kz/aitu/designpatterns/strategy/
├── Main.java                         # Client: reads input and selects a strategy
├── context/
│   └── Navigator.java                # Context: delegates to the active strategy
├── model/
│   ├── Coordinate.java               # Named geographic coordinate and distance calculation
│   ├── Route.java                    # Route result and summary output
│   ├── RouteStep.java                # One illustrative route instruction
│   └── TransportType.java            # Transport-mode labels
└── strategy/
    ├── RouteStrategy.java            # Common strategy interface
    ├── CarRouteStrategy.java
    ├── WalkingRouteStrategy.java
    ├── CyclingRouteStrategy.java
    └── TransitRouteStrategy.java

docs/
├── class-diagram.puml                # UML class diagram source
└── report.md                         # Assignment report draft
```

## Strategy pattern roles

- **Strategy:** `RouteStrategy` defines `buildRoute(...)` and `getTransportType()`.
- **Concrete strategies:** the car, walking, cycling, and transit classes provide mode-specific route estimates and steps.
- **Context:** `Navigator` stores a `RouteStrategy`, delegates route calculation to it, and can switch strategies at runtime.
- **Client:** `Main` reads the user's choices and supplies the selected strategy to `Navigator`.

The strategy-selection `switch` is in `Main`; `Navigator` does not branch on concrete strategy types.

## Add another transport strategy

1. Add a value to `TransportType` for the new mode.
2. Create a class that implements `RouteStrategy` and implement both interface methods.
3. Add the new mode to the menu and strategy-selection `switch` in `Main`.
4. Update `docs/class-diagram.puml` and the report to include the new strategy.

The new concrete algorithm can be used by `Navigator` without changing its delegation logic. The client menu and transport-type enum still need configuration updates.
