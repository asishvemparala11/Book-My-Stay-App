import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RoomSearchAvailabilityCheck {

    // Room Domain Model
    static class Room {
        private String type;
        private double price;
        private String amenities;

        public Room(String type, double price, String amenities) {
            this.type = type;
            this.price = price;
            this.amenities = amenities;
        }

        public String getType() {
            return type;
        }

        public double getPrice() {
            return price;
        }

        public String getAmenities() {
            return amenities;
        }
    }

    // Inventory Class (State Holder)
    static class Inventory {
        private Map<String, Integer> availability = new HashMap<>();

        public void addRoomType(String type, int count) {
            availability.put(type, count);
        }

        // Read-only access
        public int getAvailability(String type) {
            return availability.getOrDefault(type, 0);
        }

        public Set<String> getRoomTypes() {
            return availability.keySet();
        }
    }

    // Search Service (Read-only operations)
    static class SearchService {

        public void searchAvailableRooms(Inventory inventory, Map<String, Room> roomCatalog) {

            System.out.println("Available Rooms:\n");

            for (String type : inventory.getRoomTypes()) {

                int available = inventory.getAvailability(type);

                // Validation Logic - show only rooms with availability > 0
                if (available > 0) {

                    Room room = roomCatalog.get(type);

                    if (room != null) { // Defensive Programming
                        System.out.println("Room Type: " + room.getType());
                        System.out.println("Price: $" + room.getPrice());
                        System.out.println("Amenities: " + room.getAmenities());
                        System.out.println("Available Count: " + available);
                        System.out.println("---------------------------");
                    }
                }
            }
        }
    }

    // Main Class
        public static void main(String[] args) {

            // Room Catalog (Domain objects)
            Map<String, Room> roomCatalog = new HashMap<>();

            roomCatalog.put("Single", new Room("Single", 100.0, "WiFi, TV, Single Bed"));
            roomCatalog.put("Double", new Room("Double", 180.0, "WiFi, TV, Double Bed"));
            roomCatalog.put("Suite", new Room("Suite", 300.0, "WiFi, TV, King Bed, Living Area"));

            // Inventory Setup
            Inventory inventory = new Inventory();
            inventory.addRoomType("Single", 3);
            inventory.addRoomType("Double", 0); // unavailable
            inventory.addRoomType("Suite", 2);

            // Guest initiates search
            SearchService searchService = new SearchService();
            searchService.searchAvailableRooms(inventory, roomCatalog);
        }
    }
