import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Helper {
    private Room room =  new Room();
    private final Scanner scanner = new Scanner(System.in);

    public void listRooms() {


        System.out.println("1: Add Room");
        System.out.println("2: Edit Room");
        System.out.println("3: Delete Room");
        System.out.println("4: main menu");
        System.out.print("enter your choice: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        switch (n) {
            case 1:
                enterRoomdata();
                break;
            case 2:
                editRoom();
                break;
                case 3:
                    deleteRoom();
                    break;
                    case 4:
                        break;
                 default:
                System.out.println("Invalid option.");
        }
    }

    public void printRoom() {
        System.out.println();
        System.out.println("Room ID: " + room.getId());
        System.out.println("Name: " + this.room.getRoomName());
        System.out.println("Capacity: " + this.room.getRoomCapacity());
        System.out.println("Price: " + this.room.getRoomPrice());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    private void enterRoomdata() {
        System.out.println("Enter room ID:");
        this.room.setId(this.scanner.nextInt());
        this.scanner.nextLine();
        System.out.println("Enter room name:");
        this.room.setRoomName(scanner.nextLine());
        System.out.println("Enter room capacity:");
        this.room.setRoomCapacity(this.scanner.nextInt());
        this.scanner.nextLine();
        System.out.println("Enter room price:");
        this.room.setRoomPrice(this.scanner.nextFloat());
        this.scanner.nextLine();
        room.add();
        listRooms();
    }
    public void editRoom() {
        while (true) {
            System.out.print("Enter room ID: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid ID. Please enter a valid integer.");
                scanner.next();
            }
            this.room.setId(this.scanner.nextInt());
            this.scanner.nextLine();

            if (this.room.getByRoomId()) {
                System.out.println("The room selected:");
                printRoom();

                System.out.print("Enter New room name: ");
                this.room.setRoomName(this.scanner.nextLine());

                System.out.print("Enter New room capacity: ");
                while (!this.scanner.hasNextInt()) {
                    System.out.println("Invalid capacity. Please enter a valid integer.");
                    this.scanner.next();
                }
                this.room.setRoomCapacity(scanner.nextInt());
                this.scanner.nextLine();

                System.out.print("Enter New room price: ");
                while (!scanner.hasNextFloat()) {
                    System.out.println("Invalid price. Please enter a valid float.");
                    scanner.next();
                }
                this.room.setRoomPrice(scanner.nextFloat());
                this.scanner.nextLine();

                System.out.print("Are you sure to save changes (y/n): ");
                char c = this.scanner.next().toLowerCase().charAt(0);
                this.scanner.nextLine();

                if (c == 'y') {
                    this.room.update();
                    System.out.println("The room updated successfully.");
                }

                listRooms();
                break;
            } else {
                System.out.println("The room not found.");
                System.out.print("Try again? (y/n): ");
                char retry = this.scanner.next().toLowerCase().charAt(0);
                this.scanner.nextLine();

                if (retry != 'y' && retry != 'Y') {
                    System.out.println("Exiting edit room.");
                    break;
                }
            }
        }

    }

    public void deleteRoom()
    {
        System.out.print("Enter room ID: ");

        this.room.setId( this.scanner.nextInt() );

        if (this.room.getByRoomId()) {
            System.out.println("The room selected:");
            printRoom();
            System.out.print("Are you sure to Delete (y/n): ");
            this.scanner.nextLine();
            char c = this.scanner.next().toLowerCase().charAt(0);

            if (c == 'y' || c == 'Y') {
                this.room.destroy();
                System.out.println("The room delete successfully.");
            }
        }
        else
        {
            System.out.println("The room not found.");
            System.out.print("Try again? (y/n): ");
            if (this.scanner.next().toLowerCase().charAt(0) == 'y')
                deleteRoom();
        }


        listRooms();
    }
}