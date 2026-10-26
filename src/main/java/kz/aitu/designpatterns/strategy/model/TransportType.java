package kz.aitu.designpatterns.strategy.model;

public enum TransportType {
    CAR("Automobile Navigation"),
    WALKING("Pedestrian Route"),
    CYCLING("Bicycle Route"),
    PUBLIC_TRANSIT("Public Bus Transit");

    private final String displayName;

    TransportType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}