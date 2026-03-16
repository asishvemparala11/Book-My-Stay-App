import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class ConcurrentBookingSimulation {


    // Reservation class
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

    // Inventory Service (Shared Resource)
    static class InventoryService {

        private Map<String, Integer> inventory = new HashMap<>();
        private int roomCounter = 1;

        public void addRoomType(String type, int count) {
            inventory.put(type, count);
        }

        // synchronized critical section
        public synchronized void allocateRoom(Reservation reservation) {

            String type = reservation.getRoomType();
            int available = inventory.getOrDefault(type, 0);

            if (available > 0) {

                String roomId = type.substring(0,1).toUpperCase() + roomCounter++;
                inventory.put(type, available - 1);

                System.out.println(Thread.currentThread().getName() +
                        " → Booking confirmed for " +
                        reservation.getGuestName() +
                        " | Room Type: " + type +
                        " | Room ID: " + roomId);

            } else {

                System.out.println(Thread.currentThread().getName() +
                        " → Booking failed for " +
                        reservation.getGuestName() +
                        " | No rooms available for " + type);
            }
        }
    }

    // Shared Booking Queue
    static class BookingQueue {

        private Queue<Reservation> queue = new LinkedList<>();

        public synchronized void addRequest(Reservation reservation) {
            queue.add(reservation);
        }

        public synchronized Reservation getNextRequest() {
            return queue.poll();
        }

        public synchronized boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    // Concurrent Booking Processor (Thread)
    static class BookingProcessor extends Thread {

        private BookingQueue queue;
        private InventoryService inventory;

        public BookingProcessor(String name, BookingQueue queue, InventoryService inventory) {
            super(name);
            this.queue = queue;
            this.inventory = inventory;
        }

        public void run() {

            while (true) {

                Reservation reservation;

                synchronized (queue) {
                    if (queue.isEmpty()) {
                        break;
                    }
                    reservation = queue.getNextRequest();
                }

                if (reservation != null) {
                    inventory.allocateRoom(reservation);
                }
            }
        }
    }

    // Main Class

        public static void main(String[] args) {

            InventoryService inventory = new InventoryService();

            // Initialize inventory
            inventory.addRoomType("Single", 2);
            inventory.addRoomType("Double", 1);

            BookingQueue queue = new BookingQueue();

            // Simulate multiple guest requests
            queue.addRequest(new Reservation("Alice", "Single"));
            queue.addRequest(new Reservation("Bob", "Single"));
            queue.addRequest(new Reservation("Charlie", "Single"));
            queue.addRequest(new Reservation("David", "Double"));
            queue.addRequest(new Reservation("Eva", "Double"));

            // Create multiple booking processor threads
            Thread t1 = new BookingProcessor("Processor-1", queue, inventory);
            Thread t2 = new BookingProcessor("Processor-2", queue, inventory);
            Thread t3 = new BookingProcessor("Processor-3", queue, inventory);

            // Start concurrent booking
            t1.start();
            t2.start();
            t3.start();
        }
    }
