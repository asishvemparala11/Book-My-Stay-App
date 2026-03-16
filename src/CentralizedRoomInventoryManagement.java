import java.util.HashMap;

public class CentralizedRoomInventoryManagement {

    static class RoomInventory {

        private HashMap<String, Integer> inventory;

        public RoomInventory() {
            inventory = new HashMap<>();

            inventory.put("Single", 5);
            inventory.put("Double", 3);
            inventory.put("Suite", 2);
        }

        public int getAvailability(String roomType) {
            return inventory.getOrDefault(roomType, 0);
        }

        public void updateAvailability(String roomType, int count) {
            inventory.put(roomType, count);
        }

        public void displayInventory() {
            System.out.println("Current Room Inventory:");

            for (String roomType : inventory.keySet()) {
                System.out.println(roomType + " Rooms Available: " + inventory.get(roomType));
            }
        }
    }

        public static void main(String[] args) {

            RoomInventory inventory = new RoomInventory();

            inventory.displayInventory();

            System.out.println("\nUpdating Double Room Availability...\n");

            inventory.updateAvailability("Double", 4);

            inventory.displayInventory();
        }
    }
