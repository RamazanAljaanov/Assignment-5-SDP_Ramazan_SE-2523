package kz.aitu.designpatterns.strategy.strategy;

import kz.aitu.designpatterns.strategy.model.*;
import java.util.ArrayList;
import java.util.List;

public class CyclingRouteStrategy implements RouteStrategy {
    private static final double AVERAGE_CYCLING_SPEED_KMH = 18.0;
    private static final double CYCLING_DETOUR_FACTOR = 1.20;
    private static final double CALORIES_BURNED_PER_KM = 35.0;

    @Override
    public Route buildRoute(Coordinate start, Coordinate destination) {
        double straightDistance = start.distanceTo(destination);
        double cycleDistance = straightDistance * CYCLING_DETOUR_FACTOR;
        double durationMinutes = (cycleDistance / AVERAGE_CYCLING_SPEED_KMH) * 60.0;
        double costKzt = 0.0;
        double calories = cycleDistance * CALORIES_BURNED_PER_KM;
        double co2 = 0.0;

        List<RouteStep> steps = new ArrayList<>();
        steps.add(new RouteStep(1, "Mount bicycle at " + start.getName() + " and join Astana Bike lane", cycleDistance * 0.35, durationMinutes * 0.35, "Dedicated Bike Lane"));
        steps.add(new RouteStep(2, "Follow boulevard bike path avoiding elevated curbs", cycleDistance * 0.45, durationMinutes * 0.45, "Segregated Asphalt Cycleway"));
        steps.add(new RouteStep(3, "Lock bicycle at station near " + destination.getName(), cycleDistance * 0.20, durationMinutes * 0.20, "Shared Pedestrian-Cycle Zone"));

        return new Route(start, destination, getTransportType(), cycleDistance, durationMinutes, costKzt, calories, co2, steps);
    }

    @Override
    public TransportType getTransportType() {
        return TransportType.CYCLING;
    }
}
