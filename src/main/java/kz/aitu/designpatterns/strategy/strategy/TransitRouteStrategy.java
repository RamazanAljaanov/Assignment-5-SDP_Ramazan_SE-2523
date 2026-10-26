package kz.aitu.designpatterns.strategy.strategy;

import kz.aitu.designpatterns.strategy.model.Coordinate;
import kz.aitu.designpatterns.strategy.model.Route;
import kz.aitu.designpatterns.strategy.model.RouteStep;
import kz.aitu.designpatterns.strategy.model.TransportType;

import java.util.ArrayList;
import java.util.List;

public class TransitRouteStrategy implements RouteStrategy {
    private static final double BUS_AVERAGE_SPEED_KMH = 24.0;
    private static final double BUS_ROUTE_FACTOR = 1.35;
    private static final double ASTANA_BUS_FARE_KZT = 110.0;
    private static final double BUS_CO2_PER_PASSENGER_KM = 45.0;
    private static final double MAX_WALK_TO_STOP_KM = 0.25;
    private static final double WALKING_SPEED_KMH = 3.75;
    private static final double BUS_WAIT_MINUTES = 8.0;

    @Override
    public Route buildRoute(Coordinate start, Coordinate destination) {
        double straightDistance = start.distanceTo(destination);
        double transitDistance = straightDistance * BUS_ROUTE_FACTOR;

        // Keep the total of access walk, bus ride, and egress walk equal to the route distance.
        double accessDistance = Math.min(MAX_WALK_TO_STOP_KM, transitDistance / 2.0);
        double egressDistance = Math.min(MAX_WALK_TO_STOP_KM, transitDistance / 2.0);
        double busDistance = Math.max(0.0, transitDistance - accessDistance - egressDistance);

        double accessDurationMinutes = (accessDistance / WALKING_SPEED_KMH) * 60.0;
        double egressDurationMinutes = (egressDistance / WALKING_SPEED_KMH) * 60.0;
        double busDurationMinutes = (busDistance / BUS_AVERAGE_SPEED_KMH) * 60.0;
        boolean usesBus = busDistance > 0.0;
        double waitMinutes = usesBus ? BUS_WAIT_MINUTES : 0.0;
        double totalDurationMinutes = accessDurationMinutes + waitMinutes
                + busDurationMinutes + egressDurationMinutes;
        double estimatedCost = usesBus ? ASTANA_BUS_FARE_KZT : 0.0;
        double calories = 40.0;
        double co2 = busDistance * BUS_CO2_PER_PASSENGER_KM;

        List<RouteStep> steps = new ArrayList<>();
        int stepNumber = 1;
        if (accessDistance > 0.0) {
            steps.add(new RouteStep(stepNumber++,
                    "Walk to a bus stop near " + start.getName(),
                    accessDistance, accessDurationMinutes, "Sidewalk"));
        }
        if (usesBus) {
            steps.add(new RouteStep(stepNumber++,
                    "Board Bus #10 / #12 heading towards the central district",
                    busDistance, busDurationMinutes, "Bus lane (illustrative)"));
        }
        if (egressDistance > 0.0) {
            steps.add(new RouteStep(stepNumber,
                    "Walk from the stop to " + destination.getName(),
                    egressDistance, egressDurationMinutes, "Sidewalk"));
        }

        return new Route(start, destination, getTransportType(), transitDistance,
                totalDurationMinutes, estimatedCost, calories, co2, steps);
    }

    @Override
    public TransportType getTransportType() {
        return TransportType.PUBLIC_TRANSIT;
    }
}
