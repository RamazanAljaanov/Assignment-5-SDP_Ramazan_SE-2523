package kz.aitu.designpatterns.strategy.strategy;

import kz.aitu.designpatterns.strategy.model.Coordinate;
import kz.aitu.designpatterns.strategy.model.Route;
import kz.aitu.designpatterns.strategy.model.TransportType;


public interface RouteStrategy {


    Route buildRoute(Coordinate start, Coordinate destination);


    TransportType getTransportType();
}
