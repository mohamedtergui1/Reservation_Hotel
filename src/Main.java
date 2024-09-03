import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Helper helper = new Helper();
        ReservationController resirvation = new ReservationController(new ReservationModel());
        int n = 11;

        do {
            System.out.println("1 : mange rooms ");
            System.out.println("2 : mange reservations");
            System.out.print("Enter a number (0 to exit): ");
                n = scanner.nextInt();
                scanner.nextLine();
                switch (n)
                {
                    case 1: helper.listRooms(); break;
                    case 2: resirvation.index(); break;
                }


        } while (n != 0);

        scanner.close();
    }
}
