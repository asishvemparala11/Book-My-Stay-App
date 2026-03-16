import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingHistoryReporting {
    // Reservation class
    static class Reservation {
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
            return "Reservation ID: " + reservationId +
                    ", Guest: " + guestName +
                    ", Room Type: " + roomType;
        }
    }

    // Booking History (stores confirmed bookings)
    static class BookingHistory {

        private List<Reservation> confirmedBookings = new ArrayList<>();

        // Add confirmed reservation to history
        public void addReservation(Reservation reservation) {
            confirmedBookings.add(reservation);
        }

        // Retrieve booking history
        public List<Reservation> getReservations() {
            return confirmedBookings;
        }
    }

    // Booking Report Service
    static class BookingReportService {

        // Display full booking history
        public void showBookingHistory(List<Reservation> reservations) {

            System.out.println("\n--- Booking History ---");

            if (reservations.isEmpty()) {
                System.out.println("No bookings available.");
                return;
            }

            for (Reservation r : reservations) {
                System.out.println(r);
            }
        }

        // Generate summary report
        public void generateSummaryReport(List<Reservation> reservations) {

            System.out.println("\n--- Booking Summary Report ---");

            Map<String, Integer> roomTypeCount = new HashMap<>();

            for (Reservation r : reservations) {
                roomTypeCount.put(r.getRoomType(),
                        roomTypeCount.getOrDefault(r.getRoomType(), 0) + 1);
            }

            for (String roomType : roomTypeCount.keySet()) {
                System.out.println(roomType + " Rooms Booked: " + roomTypeCount.get(roomType));
            }

            System.out.println("Total Reservations: " + reservations.size());
        }
    }

    // Main Class

        public static void main(String[] args) {

            // Booking history storage
            BookingHistory history = new BookingHistory();

            // Simulate confirmed bookings
            history.addReservation(new Reservation("R101", "Alice", "Single"));
            history.addReservation(new Reservation("R102", "Bob", "Double"));
            history.addReservation(new Reservation("R103", "Charlie", "Single"));
            history.addReservation(new Reservation("R104", "David", "Suite"));

            // Admin requests report
            BookingReportService reportService = new BookingReportService();

            // Display history
            reportService.showBookingHistory(history.getReservations());

            // Generate summary report
            reportService.generateSummaryReport(history.getReservations());
        }
    }
