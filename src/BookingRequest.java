import java.util.LinkedList;
import java.util.Queue;

public class BookingRequest {

    // Reservation class representing a booking request
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

        public String toString() {
            return "Guest: " + guestName +
                    ", Room Type: " + roomType +
                    ", Nights: " + nights;
        }
    }

    // Booking Request Queue
    static class BookingRequestQueue {

        private Queue<Reservation> requestQueue;

        public BookingRequestQueue() {
            requestQueue = new LinkedList<>();
        }

        // Add booking request to queue
        public void addRequest(Reservation reservation) {
            requestQueue.add(reservation);
            System.out.println("Booking request added for " + reservation.getGuestName());
        }

        // Display queued requests
        public void showRequests() {
            System.out.println("\nBooking Requests in Queue (FIFO Order):");

            if (requestQueue.isEmpty()) {
                System.out.println("No booking requests.");
                return;
            }

            for (Reservation r : requestQueue) {
                System.out.println(r);
            }
        }
    }

    // Main Program
        public static void main(String[] args) {

            // Create booking queue
            BookingRequestQueue queue = new BookingRequestQueue();

            // Guests submit booking requests
            Reservation r1 = new Reservation("Alice", "Single", 2);
            Reservation r2 = new Reservation("Bob", "Double", 3);
            Reservation r3 = new Reservation("Charlie", "Suite", 1);

            // Add requests to queue (arrival order preserved)
            queue.addRequest(r1);
            queue.addRequest(r2);
            queue.addRequest(r3);

            // Display queued requests
            queue.showRequests();
        }
    }
