# Assignment 5 — Strategy Pattern

## 1. Introduction
TODO: describe the route-planning domain and algorithm family.

## 2. UML class diagram
TODO: include Context, Strategy, concrete strategies, and Client. The current source diagram must match the project packages and method signatures before submission.

## 3. Clean Code

> **Reference draft:** rewrite these notes in your own words and confirm each point against your final code. The assignment asks for your own reflection and annotated code excerpts.

### 3.1 Replace algorithm-selection conditionals with polymorphism

`Navigator` delegates route planning to the strategy interface:

```java
public Route planRoute(Coordinate origin, Coordinate destination) {
    return this.strategy.buildRoute(origin, destination);
}
```

**Annotation:** `Navigator` does not branch on whether the active mode is car, walking, cycling, or transit. Each strategy supplies the implementation of `buildRoute`.

**Add in your own words:** contrast this with a `switch` inside `Navigator` and explain why the current delegation makes the context simpler.

### 3.2 Single Responsibility Principle

Each concrete strategy contains the calculations and route steps for one transport mode. For example, `WalkingRouteStrategy` defines walking speed, walking detour, calorie estimate, and pedestrian instructions; `CarRouteStrategy` handles car-specific estimates and instructions.

```java
private static final double AVERAGE_WALKING_SPEED_KMH = 4.8;

public Route buildRoute(Coordinate start, Coordinate destination) {
    // Calculate the walking estimate and construct walking steps.
}
```

**Annotation:** the walking-specific calculation belongs to the walking strategy. `Navigator` delegates, while `Route` stores and displays the result.

**Add in your own words:** describe one responsibility of a second class in your implementation.

### 3.3 Meaningful, intention-revealing names

Names such as `CyclingRouteStrategy`, `estimatedDurationMinutes`, and `AVERAGE_CYCLING_SPEED_KMH` communicate the purpose or unit of the value.

```java
private static final double BUS_CO2_PER_PASSENGER_KM = 45.0;
double co2 = transitDistance * BUS_CO2_PER_PASSENGER_KM;
```

**Annotation:** the constant name explains both what is estimated and the unit basis, which is clearer than an unexplained numeric literal.

**Add in your own words:** choose one more name from your final code and explain what it tells the reader.

### 3.4 Depend on the Strategy abstraction

`Navigator` stores the interface, not a concrete route algorithm:

```java
private RouteStrategy strategy;

public void setStrategy(RouteStrategy newStrategy) {
    this.strategy = Objects.requireNonNull(newStrategy, "RouteStrategy cannot be null");
}
```

**Annotation:** the context can accept any implementation of `RouteStrategy`; it does not need the internals of `CarRouteStrategy` or the other concrete classes.

**Add in your own words:** explain how calling `setStrategy` lets the same `Navigator` use a different algorithm at runtime.

### 3.5 Extend the context through new strategies

A new algorithm can implement `RouteStrategy` and return a `Route`. `Navigator` can delegate to it without changing its route-planning method.

```java
public interface RouteStrategy {
    Route buildRoute(Coordinate start, Coordinate destination);
    TransportType getTransportType();
}
```

**Annotation and limitation:** this demonstrates extension at the Strategy/Context level. In this project, adding a mode still requires registering its choice in `Main` and adding a `TransportType` value. Do not claim that no existing file changes at all unless you redesign that selection/registration mechanism.

**Add in your own words:** state precisely which class stays unchanged when your new strategy is added, and which client configuration needs updating.

## 4. Strategy vs. State

**Reference draft:** Strategy represents alternative algorithms for the same task. In this project, the client selects a transport mode and gives its strategy to `Navigator`; the navigator delegates route planning to that strategy. State represents an object's current condition, where behavior changes as the object moves through state transitions. The route planner does not model a lifecycle such as `Idle` → `Planning` → `Arrived`; its alternatives are user-selected route algorithms, so Strategy fits the problem.

**Add in your own words:** connect this distinction to one concrete example from your program and explain who selects the strategy.

## 5. Conclusion
TODO: reflect on flexibility gained and classes added.

## 6. GitHub repository
TODO: add repository link.
