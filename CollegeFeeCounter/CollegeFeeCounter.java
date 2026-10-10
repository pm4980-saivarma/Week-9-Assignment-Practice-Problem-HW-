import java.util.Locale;
import java.util.Scanner;

public class CollegeFeeCounter {
    private abstract static class Student {
        private static final double TRANSPORT_FEE = 12000.0;
        final String name;

        Student(String name) {
            this.name = name;
        }

        abstract double tuitionFee();

        abstract boolean usesBus();

        double additionalFee() {
            return 0.0;
        }

        final double totalFee() {
            return tuitionFee() + additionalFee() + (usesBus() ? TRANSPORT_FEE : 0.0);
        }
    }

    private static final class DayScholar extends Student {
        DayScholar(String name) {
            super(name);
        }

        @Override
        double tuitionFee() {
            return 40000.0;
        }

        @Override
        boolean usesBus() {
            return true;
        }
    }

    private static final class Hosteller extends Student {
        Hosteller(String name) {
            super(name);
        }

        @Override
        double tuitionFee() {
            return 40000.0;
        }

        @Override
        boolean usesBus() {
            return false;
        }

        @Override
        double additionalFee() {
            return 60000.0;
        }
    }

    private static final class Scholar extends Student {
        Scholar(String name) {
            super(name);
        }

        @Override
        double tuitionFee() {
            return 20000.0;
        }

        @Override
        boolean usesBus() {
            return true;
        }
    }

    private static Student createStudent(String type, String name) {
        return switch (type) {
            case "DAY_SCHOLAR" -> new DayScholar(name);
            case "HOSTELLER" -> new Hosteller(name);
            case "SCHOLAR" -> new Scholar(name);
            default -> throw new IllegalArgumentException("Unknown student type: " + type);
        };
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        try (Scanner scanner = new Scanner(System.in)) {
            int studentCount = scanner.nextInt();
            double totalCollected = 0.0;

            for (int i = 0; i < studentCount; i++) {
                Student student = createStudent(scanner.next(), scanner.next());
                double fee = student.totalFee();
                System.out.printf("%s: %.2f%n", student.name, fee);
                totalCollected += fee;
            }

            System.out.printf("Total Collected: %.2f%n", totalCollected);
        }
    }
}
