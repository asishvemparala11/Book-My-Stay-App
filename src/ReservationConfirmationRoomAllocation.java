import java.util.*;

public class ReservationConfirmationRoomAllocation {

    // Reservation Request
    static class Reservation {
        private String guestName;
        private String roomType;
        private int nights;

        public Reservation(String guestName, String roomType, int nights) {
            this.guestName = guestName;
            this.roomType = roomType;
            this.nights = nights;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }

        public int getNights() {
            return nights;
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

        public void decrementRoom(String type) {
            int count = inventory.get(type);
            inventory.put(type, count - 1);
        }
    }

    // Booking Request Queue
    static class BookingRequestQueue {
        private Queue<Reservation> queue = new LinkedList<>();

        public void addRequest(Reservation r) {
            queue.add(r);
        }

        public Reservation getNextRequest() {
            return queue.poll(); // FIFO
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    // Booking Service (Room Allocation)
    static class BookingService {

        private Set<String> allocatedRoomIds = new HashSet<>();
        private Map<String, Set<String>> roomTypeAllocation = new HashMap<>();
        private int roomCounter = 1;

        public void processBookings(BookingRequestQueue requestQueue, InventoryService inventory) {

            while (!requestQueue.isEmpty()) {

                Reservation request = requestQueue.getNextRequest();
                String roomType = request.getRoomType();

                System.out.println("\nProcessing reservation for " + request.getGuestName());

                // Check availability
                if (inventory.getAvailability(roomType) > 0) {

                    // Generate unique room ID
                    String roomId = roomType.substring(0,1).toUpperCase() + roomCounter++;

                    // Ensure uniqueness using Set
                    if (!allocatedRoomIds.contains(roomId)) {

                        allocatedRoomIds.add(roomId);

                        // Map room type to allocated IDs
                        roomTypeAllocation.putIfAbsent(roomType, new HashSet<>());
                        roomTypeAllocation.get(roomType).add(roomId);

                        // Update inventory immediately
                        inventory.decrementRoom(roomType);

                        System.out.println("Reservation Confirmed!");
                        System.out.println("Guest: " + request.getGuestName());
                        System.out.println("Room Type: " + roomType);
                        System.out.println("Assigned Room ID: " + roomId);

                    }

                } else {
                    System.out.println("Reservation Failed - No available rooms for type: " + roomType);
                }
            }
        }
    }

    // Main Class
        public static void main(String[] args) {

            // Setup Inventory
            InventoryService inventory = new InventoryService();
            inventory.addRoomType("Single", 2);
            inventory.addRoomType("Double", 1);

            // Create Booking Request Queue
            BookingRequestQueue requestQueue = new BookingRequestQueue();

            // Add booking requests (FIFO order)
            requestQueue.addRequest(new Reservation("Alice", "Single", 2));
            requestQueue.addRequest(new Reservation("Bob", "Double", 3));
            requestQueue.addRequest(new Reservation("Charlie", "Single", 1));
            requestQueue.addRequest(new Reservation("David", "Double", 2));

            // Booking Service processes requests
            BookingService bookingService = new BookingService();
            bookingService.processBookings(requestQueue, inventory);
        }
    }
