package kz.aitu.designpatterns.strategy.context;

import kz.aitu.designpatterns.strategy.model.Coordinate;
import kz.aitu.designpatterns.strategy.model.Route;
import kz.aitu.designpatterns.strategy.strategy.RouteStrategy;
import java.util.Objects;


public class Navigator {
    private RouteStrategy strategy;

    public Navigator(RouteStrategy initialStrategy) {
        setStrategy(initialStrategy);
    }


    public void setStrategy(RouteStrategy newStrategy) {
        this.strategy = Objects.requireNonNull(newStrategy, "RouteStrategy cannot be null");
        System.out.printf("[Navigator] Runtime strategy switched to: %s (%s)%n",
                newStrategy.getClass().getSimpleName(),
                newStrategy.getTransportType().getDisplayName());
    }


    public Route planRoute(Coordinate origin, Coordinate destination) {
        Objects.requireNonNull(origin, "Origin coordinate must not be null");
        Objects.requireNonNull(destination, "Destination coordinate must not be null");
        
        return this.strategy.buildRoute(origin, destination);
    }

    public RouteStrategy getActiveStrategy() {
        return this.strategy;
    }
}
