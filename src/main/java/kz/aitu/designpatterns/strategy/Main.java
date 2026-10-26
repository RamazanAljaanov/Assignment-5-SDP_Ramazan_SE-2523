package kz.aitu.designpatterns.strategy;

import kz.aitu.designpatterns.strategy.context.Navigator;
import kz.aitu.designpatterns.strategy.model.Coordinate;
import kz.aitu.designpatterns.strategy.model.Route;
import kz.aitu.designpatterns.strategy.strategy.CarRouteStrategy;
import kz.aitu.designpatterns.strategy.strategy.CyclingRouteStrategy;
import kz.aitu.designpatterns.strategy.strategy.RouteStrategy;
import kz.aitu.designpatterns.strategy.strategy.TransitRouteStrategy;
import kz.aitu.designpatterns.strategy.strategy.WalkingRouteStrategy;

import java.util.List;
import java.util.Scanner;


public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        List<Coordinate> locations = List.of(
                new Coordinate("Astana IT University (EXPO)", 51.0908, 71.4184),
                new Coordinate("Baiterek Monument", 51.1283, 71.4305),
                new Coordinate("Khan Shatyr", 51.1326, 71.4034),
                new Coordinate("Independence Palace", 51.0918, 71.4490),
                new Coordinate("Astana Railway Station", 51.1600, 71.4330)
        );

        System.out.println("Astana Route Planner — Strategy Pattern");
        Navigator navigator = null;

        try (Scanner scanner = new Scanner(System.in)) {
            boolean planAnotherRoute = true;

            while (planAnotherRoute) {
                Coordinate origin = selectLocation(scanner, locations, "Choose the starting point");
                Coordinate destination;
                do {
                    destination = selectLocation(scanner, locations, "Choose the destination");
                    if (origin.equals(destination)) {
                        System.out.println("Start and destination must be different. Choose again.");
                    }
                } while (origin.equals(destination));

                RouteStrategy selectedStrategy = selectStrategy(scanner);
                if (navigator == null) {
                    navigator = new Navigator(selectedStrategy);
                } else {
                    navigator.setStrategy(selectedStrategy);
                }

                Route route = navigator.planRoute(origin, destination);
                route.printSummary();
                planAnotherRoute = askToPlanAnother(scanner);
            }
        }

        System.out.println("Route planner finished.");
    }

    private static Coordinate selectLocation(Scanner scanner, List<Coordinate> locations, String prompt) {
        System.out.println("\n" + prompt + ":");
        for (int index = 0; index < locations.size(); index++) {
            System.out.printf("%d — %s%n", index + 1, locations.get(index).getName());
        }

        int selectedIndex = readInteger(scanner, "Enter a number: ", 1, locations.size());
        return locations.get(selectedIndex - 1);
    }

    private static RouteStrategy selectStrategy(Scanner scanner) {
        System.out.println("\nChoose a transport mode:");
        System.out.println("1 — Car");
        System.out.println("2 — Walking");
        System.out.println("3 — Bicycle");
        System.out.println("4 — Public transit");

        int choice = readInteger(scanner, "Enter a number: ", 1, 4);
        return switch (choice) {
            case 1 -> new CarRouteStrategy();
            case 2 -> new WalkingRouteStrategy();

            case 3 -> new CyclingRouteStrategy();
            case 4 -> new TransitRouteStrategy();
            default -> throw new IllegalStateException("Validated mode choice is out of range");
        };
    }

    private static int readInteger(Scanner scanner, String prompt, int minimum, int maximum) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= minimum && value <= maximum) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // Show the same helpful prompt for non-numeric input.
            }
            System.out.printf("Enter a whole number from %d to %d.%n", minimum, maximum);
        }
    }

    private static boolean askToPlanAnother(Scanner scanner) {
        while (true) {
            System.out.print("Plan another route? (y/n): ");
            String answer = scanner.nextLine().trim();
            if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {
                return true;
            }
            if (answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("no")) {
                return false;
            }
            System.out.println("Please enter y or n.");
        }
    }
}
