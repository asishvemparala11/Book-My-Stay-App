import java.util.HashMap;
import java.util.Map;

public class ErrorHandlingValidation {
    // Custom Exception for Invalid Booking
    static class InvalidBookingException extends Exception {
        public InvalidBookingException(String message) {
            super(message);
        }
    }

    // Reservation Class
    static class Reservation {
        private String guestName;
        private String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }
    }

    // Inventory Service
    static class InventoryService {

        private Map<String, Integer> inventory = new HashMap<>();

        public void addRoomType(String type, int count) {
            inventory.put(type, count);
        }

        public int getAvailability(String type) {
            return inventory.getOrDefault(type, 0);
        }

        public void allocateRoom(String type) throws InvalidBookingException {

            int available = getAvailability(type);

            if (available <= 0) {
                throw new InvalidBookingException("No rooms available for type: " + type);
            }

            inventory.put(type, available - 1);
        }

        public boolean isValidRoomType(String type) {
            return inventory.containsKey(type);
        }
    }

    // Invalid Booking Validator
    static class BookingValidator {

        public void validateReservation(Reservation reservation, InventoryService inventory)
                throws InvalidBookingException {

            // Validate guest name
            if (reservation.getGuestName() == null || reservation.getGuestName().isEmpty()) {
                throw new InvalidBookingException("Guest name cannot be empty.");
            }

            // Validate room type
            if (!inventory.isValidRoomType(reservation.getRoomType())) {
                throw new InvalidBookingException("Invalid room type: " + reservation.getRoomType());
            }

            // Check availability
            if (inventory.getAvailability(reservation.getRoomType()) <= 0) {
                throw new InvalidBookingException("Requested room type is fully booked.");
            }
        }
    }

    // Booking Service
    static class BookingService {

        private InventoryService inventory;
        private BookingValidator validator;

        public BookingService(InventoryService inventory) {
            this.inventory = inventory;
            this.validator = new BookingValidator();
        }

        public void processBooking(Reservation reservation) {

            try {

                // Validate input
                validator.validateReservation(reservation, inventory);

                // Allocate room
                inventory.allocateRoom(reservation.getRoomType());

                System.out.println("Booking confirmed for " + reservation.getGuestName() +
                        " (Room Type: " + reservation.getRoomType() + ")");

            } catch (InvalidBookingException e) {

                // Graceful failure handling
                System.out.println("Booking failed: " + e.getMessage());
            }
        }
    }

    // Main Class

        public static void main(String[] args) {

            InventoryService inventory = new InventoryService();

            // Initialize inventory
            inventory.addRoomType("Single", 1);
            inventory.addRoomType("Double", 0);

            BookingService bookingService = new BookingService(inventory);

            // Valid booking
            Reservation r1 = new Reservation("Alice", "Single");

            // Invalid room type
            Reservation r2 = new Reservation("Bob", "Suite");

            // No availability
            Reservation r3 = new Reservation("Charlie", "Double");

            // Empty guest name
            Reservation r4 = new Reservation("", "Single");

            bookingService.processBooking(r1);
            bookingService.processBooking(r2);
            bookingService.processBooking(r3);
            bookingService.processBooking(r4);
        }
    }

