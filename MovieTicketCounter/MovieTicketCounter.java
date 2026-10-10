import java.util.Locale;
import java.util.Scanner;

public class MovieTicketCounter {
    private abstract static class Ticket {
        private static final double CONVENIENCE_FEE = 20.0;

        abstract double price();

        final double amount(int count) {
            return count * (price() + CONVENIENCE_FEE);
        }
    }

    private static final class RegularTicket extends Ticket {
        @Override
        double price() {
            return 150.0;
        }
    }

    private static final class PremiumTicket extends Ticket {
        @Override
        double price() {
            return 250.0;
        }
    }

    private static final class ReclinerTicket extends Ticket {
        @Override
        double price() {
            return 400.0;
        }
    }

    private static Ticket createTicket(String seat) {
        return switch (seat) {
            case "REGULAR" -> new RegularTicket();
            case "PREMIUM" -> new PremiumTicket();
            case "RECLINER" -> new ReclinerTicket();
            default -> throw new IllegalArgumentException("Unknown seat type: " + seat);
        };
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        try (Scanner scanner = new Scanner(System.in)) {
            int bookings = scanner.nextInt();
            double total = 0.0;

            for (int i = 0; i < bookings; i++) {
                String seat = scanner.next();
                int count = scanner.nextInt();
                double amount = createTicket(seat).amount(count);
                System.out.printf("%s: %.2f%n", seat, amount);
                total += amount;
            }

            System.out.printf("Total: %.2f%n", total);
        }
    }
}
