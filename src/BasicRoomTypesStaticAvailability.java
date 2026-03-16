public class BasicRoomTypesStaticAvailability {
    abstract class Room {

        String roomType;
        int beds;
        int size;
        double price;

        Room(String roomType, int beds, int size, double price) {
            this.roomType = roomType;
            this.beds = beds;
            this.size = size;
            this.price = price;
        }

        void displayDetails() {
            System.out.println("Room Type: " + roomType);
            System.out.println("Beds: " + beds);
            System.out.println("Size: " + size + " sq ft");
            System.out.println("Price per night: $" + price);
        }
    }

    class SingleRoom extends Room {

        SingleRoom() {
            super("Single Room", 1, 200, 100);
        }
    }

    class DoubleRoom extends Room {

        DoubleRoom() {
            super("Double Room", 2, 350, 180);
        }
    }

    class SuiteRoom extends Room {

        SuiteRoom() {
            super("Suite Room", 3, 500, 300);
        }
    }

        public void main(String[] args) {

            Room single = new SingleRoom();
            Room dbl = new DoubleRoom();
            Room suite = new SuiteRoom();

            int singleAvailability = 5;
            int doubleAvailability = 3;
            int suiteAvailability = 2;

            System.out.println("Hotel Room Details\n");

            single.displayDetails();
            System.out.println("Available: " + singleAvailability);
            System.out.println();

            dbl.displayDetails();
            System.out.println("Available: " + doubleAvailability);
            System.out.println();

            suite.displayDetails();
            System.out.println("Available: " + suiteAvailability);
        }
    }