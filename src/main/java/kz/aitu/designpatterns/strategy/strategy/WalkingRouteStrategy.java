package kz.aitu.designpatterns.strategy.strategy;

import kz.aitu.designpatterns.strategy.model.*;
import java.util.ArrayList;
import java.util.List;

public class WalkingRouteStrategy implements RouteStrategy {
    private static final double AVERAGE_WALKING_SPEED_KMH = 4.8;
    private static final double WALKING_DETOUR_FACTOR = 1.15;
    private static final double CALORIES_BURNED_PER_KM = 65.0;

    @Override
    public Route buildRoute(Coordinate start, Coordinate destination) {
        double straightDistance = start.distanceTo(destination);
        double pathDistance = straightDistance * WALKING_DETOUR_FACTOR;
        double durationMinutes = (pathDistance / AVERAGE_WALKING_SPEED_KMH) * 60.0;
        double costKzt = 0.0;
        double calories = pathDistance * CALORIES_BURNED_PER_KM;
        double co2 = 0.0;

        List<RouteStep> steps = new ArrayList<>();
        steps.add(new RouteStep(1, "Exit " + start.getName() + " onto pedestrian sidewalk", pathDistance * 0.30, durationMinutes * 0.30, "Paved Sidewalk"));
        steps.add(new RouteStep(2, "Cross via signalized zebra crossing and take park trail", pathDistance * 0.45, durationMinutes * 0.45, "Botanical Park Trail"));
        steps.add(new RouteStep(3, "Arrive on foot at entrance of " + destination.getName(), pathDistance * 0.25, durationMinutes * 0.25, "Pedestrian Plaza"));

        return new Route(start, destination, getTransportType(), pathDistance, durationMinutes, costKzt, calories, co2, steps);
    }

    @Override
    public TransportType getTransportType() {
        return TransportType.WALKING;
    }
}
