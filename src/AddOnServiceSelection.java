import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddOnServiceSelection {

    // Add-On Service class
    static class AddOnService {
        private String serviceName;
        private double price;

        public AddOnService(String serviceName, double price) {
            this.serviceName = serviceName;
            this.price = price;
        }

        public String getServiceName() {
            return serviceName;
        }

        public double getPrice() {
            return price;
        }
    }

    // Reservation class
    static class Reservation {
        private String reservationId;
        private String guestName;

        public Reservation(String reservationId, String guestName) {
            this.reservationId = reservationId;
            this.guestName = guestName;
        }

        public String getReservationId() {
            return reservationId;
        }

        public String getGuestName() {
            return guestName;
        }
    }

    // Add-On Service Manager
    static class AddOnServiceManager {

        // Map reservation ID -> List of services
        private Map<String, List<AddOnService>> reservationServices = new HashMap<>();

        // Add service to reservation
        public void addService(String reservationId, AddOnService service) {

            reservationServices.putIfAbsent(reservationId, new ArrayList<>());
            reservationServices.get(reservationId).add(service);

            System.out.println("Added service: " + service.getServiceName() +
                    " to Reservation: " + reservationId);
        }

        // Display services for reservation
        public void showServices(String reservationId) {

            List<AddOnService> services = reservationServices.get(reservationId);

            if (services == null || services.isEmpty()) {
                System.out.println("No services selected.");
                return;
            }

            System.out.println("\nServices for Reservation " + reservationId + ":");

            for (AddOnService s : services) {
                System.out.println("- " + s.getServiceName() + " ($" + s.getPrice() + ")");
            }
        }

        // Calculate total cost
        public double calculateTotalServiceCost(String reservationId) {

            double total = 0;
            List<AddOnService> services = reservationServices.get(reservationId);

            if (services != null) {
                for (AddOnService s : services) {
                    total += s.getPrice();
                }
            }

            return total;
        }
    }

    // Main Class
        public static void main(String[] args) {

            // Example reservation
            Reservation reservation = new Reservation("R101", "Alice");

            // Create service manager
            AddOnServiceManager manager = new AddOnServiceManager();

            // Guest selects add-on services
            AddOnService breakfast = new AddOnService("Breakfast", 20);
            AddOnService airportPickup = new AddOnService("Airport Pickup", 50);
            AddOnService spa = new AddOnService("Spa Access", 40);

            manager.addService(reservation.getReservationId(), breakfast);
            manager.addService(reservation.getReservationId(), airportPickup);
            manager.addService(reservation.getReservationId(), spa);

            // Display selected services
            manager.showServices(reservation.getReservationId());

            // Calculate additional cost
            double totalCost = manager.calculateTotalServiceCost(reservation.getReservationId());

            System.out.println("\nTotal Additional Service Cost: $" + totalCost);
        }
    }