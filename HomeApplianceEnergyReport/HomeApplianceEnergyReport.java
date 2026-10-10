import java.util.Locale;
import java.util.Scanner;

public class HomeApplianceEnergyReport {
    private interface SaverMode {
        default double applySaverMode(double units) {
            return units * 0.75;
        }
    }

    private abstract static class Appliance {
        private static final double COST_PER_UNIT = 8.0;
        private final double powerWatts;

        Appliance(double powerWatts) {
            this.powerWatts = powerWatts;
        }

        final double units(double hours) {
            return powerWatts * hours / 1000.0;
        }

        final double cost(double units) {
            return units * COST_PER_UNIT;
        }
    }

    private static final class Fridge extends Appliance {
        Fridge() {
            super(150.0);
        }
    }

    private static final class AirConditioner extends Appliance implements SaverMode {
        AirConditioner() {
            super(1500.0);
        }
    }

    private static final class Television extends Appliance {
        Television() {
            super(100.0);
        }
    }

    private static final class Washer extends Appliance implements SaverMode {
        Washer() {
            super(500.0);
        }
    }

    private static Appliance createAppliance(String type) {
        return switch (type) {
            case "FRIDGE" -> new Fridge();
            case "AC" -> new AirConditioner();
            case "TV" -> new Television();
            case "WASHER" -> new Washer();
            default -> throw new IllegalArgumentException("Unknown appliance type: " + type);
        };
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        try (Scanner scanner = new Scanner(System.in)) {
            int applianceCount = Integer.parseInt(scanner.nextLine().trim());
            double totalCost = 0.0;

            for (int i = 0; i < applianceCount; i++) {
                String[] parts = scanner.nextLine().trim().split("\\s+");
                String type = parts[0];
                double hours = Double.parseDouble(parts[1]);
                boolean saverRequested = parts.length == 3 && parts[2].equals("SAVER");
                Appliance appliance = createAppliance(type);

                if (saverRequested && !(appliance instanceof SaverMode)) {
                    System.out.printf("%s: saver mode not supported%n", type);
                    continue;
                }

                double units = appliance.units(hours);
                if (saverRequested) {
                    units = ((SaverMode) appliance).applySaverMode(units);
                }
                double cost = appliance.cost(units);
                System.out.printf("%s: Units=%.2f Cost=%.2f%n", type, units, cost);
                totalCost += cost;
            }

            System.out.printf("Total Cost: %.2f%n", totalCost);
        }
    }
}
