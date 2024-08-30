import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Helper helper = new Helper();
        ResirvationController resirvation = new ResirvationController( new ResirvationModel() );
        int n;

        do {
            System.out.println("1 : mange rooms ");
            System.out.println("2 : mange reservations");

            System.out.print("Enter a number (0 to exit): ");
            n = scanner.nextInt();
            switch (n)
            {
                case 1: helper.listRooms(); break;
                case 2: resirvation.index(); break;
            }
        } while (n != 0);

        scanner.close();
    }
}
