import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataPersistenceSystemRecovery {

    // Reservation class (Serializable for persistence)
    static class Reservation implements Serializable {
        private static final long serialVersionUID = 1L;

        private String reservationId;
        private String guestName;
        private String roomType;

        public Reservation(String reservationId, String guestName, String roomType) {
            this.reservationId = reservationId;
            this.guestName = guestName;
            this.roomType = roomType;
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

        public String toString() {
            return reservationId + " | Guest: " + guestName + " | Room: " + roomType;
        }
    }

    // System State (Inventory + Booking History)
    static class SystemState implements Serializable {
        private static final long serialVersionUID = 1L;

        Map<String, Integer> inventory;
        List<Reservation> bookingHistory;

        public SystemState(Map<String, Integer> inventory, List<Reservation> bookingHistory) {
            this.inventory = inventory;
            this.bookingHistory = bookingHistory;
        }
    }

    // Persistence Service
    class PersistenceService {

        private static final String FILE_NAME = "hotel_state.dat";

        // Save state to file
        public static void saveState(SystemState state) {
            try (ObjectOutputStream oos =
                         new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

                oos.writeObject(state);
                System.out.println("System state saved successfully.");

            } catch (IOException e) {
                System.out.println("Error saving system state: " + e.getMessage());
            }
        }

        // Load state from file
        public static SystemState loadState() {

            try (ObjectInputStream ois =
                         new ObjectInputStream(new FileInputStream(FILE_NAME))) {

                System.out.println("System state loaded successfully.");
                return (SystemState) ois.readObject();

            } catch (FileNotFoundException e) {
                System.out.println("No previous state found. Starting fresh.");

            } catch (Exception e) {
                System.out.println("Error loading system state: " + e.getMessage());
            }

            return new SystemState(new HashMap<>(), new ArrayList<>());
        }
    }

    // Main Class

        public static void main(String[] args) {

            // Load persisted state during startup
            SystemState state = PersistenceService.loadState();

            Map<String, Integer> inventory = state.inventory;
            List<Reservation> bookings = state.bookingHistory;

            // If first run, initialize inventory
            if (inventory.isEmpty()) {
                inventory.put("Single", 3);
                inventory.put("Double", 2);
            }

            // Simulate new booking
            Reservation r1 = new Reservation("R101", "Alice", "Single");
            bookings.add(r1);

            // Update inventory
            inventory.put("Single", inventory.get("Single") - 1);

            // Display current state
            System.out.println("\nCurrent Inventory:");
            for (String type : inventory.keySet()) {
                System.out.println(type + " : " + inventory.get(type));
            }

            System.out.println("\nBooking History:");
            for (Reservation r : bookings) {
                System.out.println(r);
            }

            // Save state before shutdown
            PersistenceService.saveState(new SystemState(inventory, bookings));
        }
    }
