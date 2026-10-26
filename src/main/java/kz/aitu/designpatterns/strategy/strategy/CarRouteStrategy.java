package kz.aitu.designpatterns.strategy.strategy;

import kz.aitu.designpatterns.strategy.model.*;
import java.util.ArrayList;
import java.util.List;

public class CarRouteStrategy implements RouteStrategy {
    private static final double AVERAGE_SPEED_KMH = 45.0;
    private static final double ROUTE_CURVATURE_FACTOR = 1.30;
    private static final double FUEL_COST_PER_KM_KZT = 35.0;
    private static final double CO2_EMISSION_PER_KM_GRAMS = 140.0;

    @Override
    public Route buildRoute(Coordinate start, Coordinate destination) {
        double straightDistance = start.distanceTo(destination);
        double roadDistance = straightDistance * ROUTE_CURVATURE_FACTOR;
        double durationMinutes = (roadDistance / AVERAGE_SPEED_KMH) * 60.0;
        double estimatedCost = roadDistance * FUEL_COST_PER_KM_KZT;
        double calories = 12.0;
        double co2 = roadDistance * CO2_EMISSION_PER_KM_GRAMS;

        List<RouteStep> steps = new ArrayList<>();
        steps.add(new RouteStep(1, "Depart from " + start.getName() + " onto Mangilik El Ave", roadDistance * 0.25, durationMinutes * 0.25, "Dual Carriageway (60 km/h)"));
        steps.add(new RouteStep(2, "Follow main traffic artery towards Uly Dala Ave", roadDistance * 0.50, durationMinutes * 0.50, "Paved Multi-lane Avenue (70 km/h)"));
        steps.add(new RouteStep(3, "Turn right into the arrival slip lane and park at " + destination.getName(), roadDistance * 0.25, durationMinutes * 0.25, "Local Access Road (30 km/h)"));

        return new Route(start, destination, getTransportType(), roadDistance, durationMinutes, estimatedCost, calories, co2, steps);
    }

    @Override
    public TransportType getTransportType() {
        return TransportType.CAR;
    }
}