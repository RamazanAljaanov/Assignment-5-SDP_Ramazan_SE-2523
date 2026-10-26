package kz.aitu.designpatterns.strategy.model;

import java.util.Collections;
import java.util.List;

public record Route(
        Coordinate start,
        Coordinate destination,
        TransportType transportType,
        double totalDistanceKm,
        double estimatedDurationMinutes,
        double estimatedCostKzt,
        double estimatedCaloriesKcal,
        double carbonEmissionGrams,
        List<RouteStep> steps
) {
    public Route {
        steps = Collections.unmodifiableList(steps);
    }

    public void printSummary() {
        System.out.println("==================================================");
        System.out.println("ROUTE PLAN: " + transportType.getDisplayName().toUpperCase());
        System.out.println("==================================================");
        System.out.printf(" Origin:       %s%n", start);
        System.out.printf(" Destination:  %s%n", destination);
        System.out.printf(" Distance:     %.2f km%n", totalDistanceKm);
        System.out.printf(" Est. Time:    %.1f min (%.2f hrs)%n", estimatedDurationMinutes, estimatedDurationMinutes / 60.0);
        System.out.printf(" Est. Cost:    %.0f KZT%n", estimatedCostKzt);
        System.out.printf(" Calories:     %.0f kcal%n", estimatedCaloriesKcal);
        System.out.printf(" Carbon (CO2): %.0f g%n", carbonEmissionGrams);
        System.out.println("--------------------------------------------------");
        System.out.println(" Turn-by-Turn Navigation Steps:");
        for (RouteStep step : steps) {
            System.out.println("   " + step);
        }
        System.out.println("==================================================\n");
    }
}
