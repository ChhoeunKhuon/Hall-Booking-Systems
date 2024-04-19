import java.time.LocalDateTime;
import java.util.Scanner;
public class Main {

    private static int row;
    private static int col;
    private static int[][] morningHall;
    private static int[][] afternoonHall;
    private static int[][] nightHall;
    private static String[] bookedSeats = new String[100];
    private static int[] studentIds = new int[100];
    private static String[] datetimes = new String[100];
    private static String[] hallNames = new String[3];
    private static int bookingCount = 0;

    public static void main(String[] args) {

        System.out.println("=".repeat(80));
        System.out.println("Hall Booking System");
        System.out.println("=".repeat(80));

        System.out.print("> Config total row in hall: ");
        row = new Scanner(System.in).nextInt();
        System.out.print("> Config total seats per row in hall: ");
        col = new Scanner(System.in).nextInt();

        // Initialize the hall with all seats available
        morningHall = new int[row][col];
        afternoonHall = new int[row][col];
        nightHall = new int[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                morningHall[i][j] = 1; // 1 represents available seat, 0 represents booked seat
                afternoonHall[i][j] = 1;
                nightHall[i][j] = 1;
            }
        }

        do {
            System.out.println("=".repeat(80));
            System.out.println("[[ Application Menu ]]");
            System.out.println(" <A> Booking");
            System.out.println(" <B> Hall");
            System.out.println(" <C> Showtime");
            System.out.println(" <D> Reboot Showtime");
            System.out.println(" <E> History");
            System.out.println(" <F> Exit");
            System.out.print(">> Please select menu no: ");
            String no = new Scanner(System.in).nextLine().toUpperCase();

            switch (no) {
                case "A" -> {
                    bookingSeat();
                    break;
                }
                case "B" -> {
                    displayHallStatus("Morning Hall", morningHall);
                    displayHallStatus("Afternoon Hall", afternoonHall);
                    displayHallStatus("Night Hall", nightHall);
                    break;
                }
                case "C" -> {
                    showTime();
                    break;
                }
                case "D" -> {
                    rebootHall(morningHall);
                    rebootHall(afternoonHall);
                    rebootHall(nightHall);
                    break;
                }
                case "E" -> {
                    history();
                    break;
                }
                case "F" -> {
                    System.exit(0);
                }
                default -> {
                    System.out.println("==>> Invalid Input. Please choose from A to F <<==");

                }
            }

        } while (true);
    }

    // Booking seat
    public static void bookingSeat() {

        System.out.println("=".repeat(80));
        System.out.println(" Start booking process");
        showTime();
        System.out.print(" Please select time show (A | B | C): ");
        String bookTime = new Scanner(System.in).nextLine().toUpperCase();

        if (bookTime.equals("A") ) {
            System.out.println("=".repeat(80));
            System.out.println("Hall " +bookTime);
            displayHallStatus("Morning Hall", morningHall);
            booked("Morning Hall", morningHall);
        } else if (bookTime.equals("B")) {
            System.out.println("=".repeat(80));
            System.out.println("Hall " +bookTime);
            displayHallStatus("Afternoon Hall", afternoonHall);
            booked("Afternoon Hall", afternoonHall);
        }
        else if (bookTime.equals("C")){
            System.out.println("=".repeat(80));
            System.out.println("Hall " +bookTime);
            displayHallStatus("Night Hall", nightHall);
            booked("Night Hall", nightHall);
        }
    }

    // method to display hall status
    public static void displayHallStatus(String hallName, int[][] hall) {
        System.out.println("=".repeat(80));
        System.out.println("Hall: " +hallName);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                String status = (hall[i][j] == 1) ? "AV" : "BO";
                System.out.print("|" + (char)('A' + i) + "-" + (j + 1) + ":: " + status + "|   ");
            }
            System.out.println();
        }
    }

    public static void booked(String hallName, int[][] hall) {
        System.out.println("=".repeat(80));
        System.out.print("> Please select number seats to book: ");
        String seatNumber = new Scanner(System.in).nextLine().toUpperCase();
        System.out.print("> Please enter your Id: ");
        int id = new Scanner(System.in).nextInt();
        System.out.print("> Are you sure to book? (Y/n): ");
        String confirm = new Scanner(System.in).nextLine();

        if (confirm.equalsIgnoreCase("Y")) {
            String[] selectSeat = seatNumber.split(",");
            for (String seat: selectSeat) {
                String[] parts = seat.trim().split("-");
                char rowChar = Character.toUpperCase(parts[0].charAt(0));
                int col = Integer.parseInt(parts[1]) - 1;

                // convert character to index
                int row = rowChar - 'A';
                // Update status of seat to booked
                hall[row][col] = 0;

                // add book to history record detail in arrays
                bookedSeats[bookingCount] = seat;
                studentIds[bookingCount] = id;
                datetimes[bookingCount] = LocalDateTime.now().toString();
                hallNames[bookingCount] = hallName;
                bookingCount++;
            }
        } else {
            System.out.println("=".repeat(80));
            System.out.println(" >> Your book Unsuccessfully...! ");
        }
    }

    // Booking history
    public static void history() {

        System.out.println("=".repeat(80));
        System.out.println("Booking History");
        System.out.println("=".repeat(80));

        if (bookingCount == 0) {
            System.out.println("No booking history available.");
        } else {
            System.out.printf("|%-5s|%-10s|%-15s|%-15s|%-29s|\n",
                    "No.", "Seat", "Hall", "Student Id", "Booking Time");
            System.out.println("|" + "=".repeat(5) + "|" + "=".repeat(10) + "|" +
                    "=".repeat(15) + "|" + "=".repeat(15) + "|" + "=".repeat(29) + "|");

            for (int i = 0; i < bookingCount; i++) {
                System.out.printf("|%-5d|%-10s|%-15s|%-15s|%-29s|\n",
                        (i + 1), bookedSeats[i].trim(),
                        hallNames[i],"CSTAD-" +String.format("%03d", studentIds[i]), datetimes[i]);
            }
        }
        System.out.println("=".repeat(80));
    }

    // time
    public static void showTime() {
        System.out.println("=".repeat(80));
        System.out.println("#  Show time Available");
        System.out.println("#  A) Morning (10:00 - 12:30)");
        System.out.println("#  B) Afternoon (3:00 - 5:30");
        System.out.println("#  C) Night (7:00 - 9:30");
        System.out.println("=".repeat(80));
    }

    // method to reboot hall
    public static void rebootHall(int[][] hall) {

        // Iterate over each seat in the hall
        for (int i = 0; i < hall.length; i++) {
            for (int j = 0; j < hall[i].length; j++) {
                // Check if the seat is booked (status = 0)
                if (hall[i][j] == 0) {
                    // If booked, reset the status to available (1 represents available)
                    hall[i][j] = 1;
                }
            }
        }
        System.out.println("All booked seats have been reset to available.");
    }
}