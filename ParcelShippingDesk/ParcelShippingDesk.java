import java.util.Locale;
import java.util.Scanner;

public class ParcelShippingDesk {
    private interface Insurable {
        default double insurance(double declaredValue) {
            return declaredValue * 0.02;
        }
    }

    private abstract static class Parcel {
        final double weightKg;
        final double declaredValue;

        Parcel(double weightKg, double declaredValue) {
            this.weightKg = weightKg;
            this.declaredValue = declaredValue;
        }

        abstract double charge();

        final double insurance() {
            return this instanceof Insurable insurable
                    ? insurable.insurance(declaredValue)
                    : 0.0;
        }
    }

    private static class StandardParcel extends Parcel {
        StandardParcel(double weightKg, double declaredValue) {
            super(weightKg, declaredValue);
        }

        @Override
        double charge() {
            return 40.0 + 10.0 * weightKg;
        }
    }

    private static final class ExpressParcel extends Parcel implements Insurable {
        ExpressParcel(double weightKg, double declaredValue) {
            super(weightKg, declaredValue);
        }

        @Override
        double charge() {
            return 80.0 + 15.0 * weightKg;
        }
    }

    private static final class FragileParcel extends StandardParcel implements Insurable {
        FragileParcel(double weightKg, double declaredValue) {
            super(weightKg, declaredValue);
        }

        @Override
        double charge() {
            return super.charge() + 50.0;
        }
    }

    private static Parcel createParcel(String type, double weightKg, double declaredValue) {
        return switch (type) {
            case "STANDARD" -> new StandardParcel(weightKg, declaredValue);
            case "EXPRESS" -> new ExpressParcel(weightKg, declaredValue);
            case "FRAGILE" -> new FragileParcel(weightKg, declaredValue);
            default -> throw new IllegalArgumentException("Unknown parcel type: " + type);
        };
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        try (Scanner scanner = new Scanner(System.in)) {
            int parcelCount = scanner.nextInt();
            double grandTotal = 0.0;

            for (int i = 0; i < parcelCount; i++) {
                String type = scanner.next();
                double weightKg = scanner.nextDouble();
                double declaredValue = scanner.nextDouble();
                Parcel parcel = createParcel(type, weightKg, declaredValue);
                double charge = parcel.charge();
                double insurance = parcel.insurance();
                double total = charge + insurance;

                System.out.printf(
                        "%s: Charge=%.2f Insurance=%.2f Total=%.2f%n",
                        type, charge, insurance, total);
                grandTotal += total;
            }

            System.out.printf("Grand Total: %.2f%n", grandTotal);
        }
    }
}
