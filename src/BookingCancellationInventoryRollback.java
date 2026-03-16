import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class BookingCancellationInventoryRollback {
    // Reservation class
    static class Reservation {
        private String reservationId;
        private String guestName;
        private String roomType;
        private String roomId;
        private boolean cancelled;

        public Reservation(String reservationId, String guestName, String roomType, String roomId) {
            this.reservationId = reservationId;
            this.guestName = guestName;
            this.roomType = roomType;
            this.roomId = roomId;
            this.cancelled = false;
        }

        public String getReservationId() {
            return reservationId;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }

        public String getRoomId() {
            return roomId;
        }

        public boolean isCancelled() {
            return cancelled;
        }

        public void cancel() {
            cancelled = true;
        }
    }

    // Inventory Service
    static class InventoryService {

        private Map<String, Integer> inventory = new HashMap<>();

        public void addRoomType(String type, int count) {
            inventory.put(type, count);
        }

        public void incrementRoom(String type) {
            inventory.put(type, inventory.getOrDefault(type, 0) + 1);
        }

        public void showInventory() {
            System.out.println("\nCurrent Inventory:");
            for (String type : inventory.keySet()) {
                System.out.println(type + " : " + inventory.get(type));
            }
        }
    }

    // Booking History
    static class BookingHistory {

        private Map<String, Reservation> reservations = new HashMap<>();

        public void addReservation(Reservation reservation) {
            reservations.put(reservation.getReservationId(), reservation);
        }

        public Reservation getReservation(String id) {
            return reservations.get(id);
        }

        public void showReservations() {
            System.out.println("\nBooking History:");
            for (Reservation r : reservations.values()) {
                System.out.println("ID: " + r.getReservationId() +
                        ", Guest: " + r.getGuestName() +
                        ", Room ID: " + r.getRoomId() +
                        ", Cancelled: " + r.isCancelled());
            }
        }
    }

    // Cancellation Service
    static class CancellationService {

        private Stack<String> rollbackStack = new Stack<>();

        public void cancelReservation(String reservationId,
                                      BookingHistory history,
                                      InventoryService inventory) {

            Reservation reservation = history.getReservation(reservationId);

            if (reservation == null) {
                System.out.println("Cancellation failed: Reservation does not exist.");
                return;
            }

            if (reservation.isCancelled()) {
                System.out.println("Cancellation failed: Reservation already cancelled.");
                return;
            }

            // Record released room ID in stack (rollback tracking)
            rollbackStack.push(reservation.getRoomId());

            // Restore inventory
            inventory.incrementRoom(reservation.getRoomType());

            // Mark reservation cancelled
            reservation.cancel();

            System.out.println("Reservation cancelled successfully.");
            System.out.println("Released Room ID: " + rollbackStack.peek());
        }

        public void showRollbackStack() {
            System.out.println("\nRollback Stack (Recently Released Rooms): " + rollbackStack);
        }
    }

    // Main Class

        public static void main(String[] args) {

            InventoryService inventory = new InventoryService();
            inventory.addRoomType("Single", 1);
            inventory.addRoomType("Double", 1);

            BookingHistory history = new BookingHistory();

            // Simulate confirmed bookings
            Reservation r1 = new Reservation("R101", "Alice", "Single", "S1");
            Reservation r2 = new Reservation("R102", "Bob", "Double", "D1");

            history.addReservation(r1);
            history.addReservation(r2);

            CancellationService cancellationService = new CancellationService();

            // Cancel a reservation
            cancellationService.cancelReservation("R101", history, inventory);

            // Attempt invalid cancellation
            cancellationService.cancelReservation("R999", history, inventory);

            // Attempt duplicate cancellation
            cancellationService.cancelReservation("R101", history, inventory);

            // Display system state
            history.showReservations();
            inventory.showInventory();
            cancellationService.showRollbackStack();
        }
    }
