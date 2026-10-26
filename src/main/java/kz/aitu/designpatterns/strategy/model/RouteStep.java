package kz.aitu.designpatterns.strategy.model;

public record RouteStep(
        int stepNumber,
        String instruction,
        double distanceKm,
        double estimatedMinutes,
        String surfaceOrRoadType
) {
    public RouteStep {
        if (stepNumber < 1) throw new IllegalArgumentException("Step number must be positive");
        if (distanceKm < 0) throw new IllegalArgumentException("Distance cannot be negative");
    }

    @Override
    public String toString() {
        return String.format("%d. %s (%.2f km, ~%.1f min) [%s]",
                stepNumber, instruction, distanceKm, estimatedMinutes, surfaceOrRoadType);
    }
}