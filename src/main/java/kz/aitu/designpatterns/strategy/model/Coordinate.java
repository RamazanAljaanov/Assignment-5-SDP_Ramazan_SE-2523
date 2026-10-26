package kz.aitu.designpatterns.strategy.model;

import java.util.Objects;


public final class Coordinate {
    private final String name;
    private final double latitude;
    private final double longitude;

    public Coordinate(String name, double latitude, double longitude) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Coordinate name cannot be null or blank");
        }
        if (latitude < -90.0 || latitude > 90.0) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90");
        }
        if (longitude < -180.0 || longitude > 180.0) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180");
        }
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double distanceTo(Coordinate other) {
        Objects.requireNonNull(other, "Target coordinate must not be null");
        final double earthRadiusKm = 6371.0;
        double dLat = Math.toRadians(other.latitude - this.latitude);
        double dLon = Math.toRadians(other.longitude - this.longitude);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(other.latitude))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadiusKm * c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate that)) return false;
        return Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0 &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, latitude, longitude);
    }

    @Override
    public String toString() {
        return String.format("%s (%.4f° N, %.4f° E)", name, latitude, longitude);
    }
}
