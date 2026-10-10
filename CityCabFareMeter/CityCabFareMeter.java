import java.util.Locale;
import java.util.Scanner;

public class CityCabFareMeter {
    private interface NightService {
        default double applyNightFare(double fare) {
            return fare * 1.20;
        }
    }

    private abstract static class Cab {
        private static final double MINIMUM_FARE = 100.0;

        abstract double ratePerKm();

        final double fare(double km) {
            return Math.max(km * ratePerKm(), MINIMUM_FARE);
        }
    }

    private static final class MiniCab extends Cab {
        @Override
        double ratePerKm() {
            return 10.0;
        }
    }

    private static final class SedanCab extends Cab implements NightService {
        @Override
        double ratePerKm() {
            return 14.0;
        }
    }

    private static final class SuvCab extends Cab implements NightService {
        @Override
        double ratePerKm() {
            return 18.0;
        }
    }

    private static Cab createCab(String type) {
        return switch (type) {
            case "MINI" -> new MiniCab();
            case "SEDAN" -> new SedanCab();
            case "SUV" -> new SuvCab();
            default -> throw new IllegalArgumentException("Unknown cab type: " + type);
        };
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        try (Scanner scanner = new Scanner(System.in)) {
            int tripCount = scanner.nextInt();
            double total = 0.0;

            for (int i = 0; i < tripCount; i++) {
                String type = scanner.next();
                double km = scanner.nextDouble();
                String time = scanner.next();
                Cab cab = createCab(type);

                if (time.equals("NIGHT") && !(cab instanceof NightService)) {
                    System.out.printf("%s: night service not available%n", type);
                    continue;
                }

                double fare = cab.fare(km);
                if (time.equals("NIGHT")) {
                    fare = ((NightService) cab).applyNightFare(fare);
                }
                System.out.printf("%s: %.2f%n", type, fare);
                total += fare;
            }

            System.out.printf("Total: %.2f%n", total);
        }
    }
}
