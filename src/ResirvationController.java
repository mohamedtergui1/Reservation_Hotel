import java.util.ArrayList;
import java.util.Scanner;

public class ResirvationController {
    ResirvationModelInterface model;
    Scanner scanner = new Scanner(System.in);

    public ResirvationController( ResirvationModelInterface model ) {
        this.model = model;
    }

    public void index()
    {
        ArrayList<Resirvation> resirvations = this.model.getAll();
        System.out.println("-----------------------------------------------------------------------------");
        for (Resirvation resirvation : resirvations) {
            printResirvation(resirvation);
            System.out.println("------------------------------------------------------------------------------");
        }
        System.out.println("1: Add Resirvation");
        System.out.println("2: Edit Resirvation");
        System.out.println("3: Delete Resirvation");
        System.out.println("4: main menu");
        System.out.print("enter your choice: ");
        int n = this.scanner.nextInt();
        this.scanner.nextLine();

        switch (n) {
            case 1:
                create();
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    public void create()
    {
        Resirvation resirvation = new Resirvation();
        Room room = new Room();
        System.out.print("Enter the starting date: ");
        resirvation.setStartDate(scanner.nextLine());
        System.out.print("Enter the ending date: ");
        resirvation.setEndDate(scanner.nextLine());
        System.out.print("Enter the room id: ");
        room.setId(scanner.nextInt());
        room.getByRoomId();
        resirvation.setRoom(room);

        if(model.insert(resirvation))
            System.out.println("Resirvation added");
        else
        {
            System.out.println("Resirvation already exists");
            System.out.println("try Again ? (y/n) : ");
            if (this.scanner.next().equals("y"))
                create();
        }
        this.scanner.close();
        index();
    }

    public void printResirvation(Resirvation resirvation)
    {
        System.out.println("resirvation id :" +  resirvation.getId());
        System.out.println("resirvation startdate : " + resirvation.getStartDate() );
        System.out.println("resirvation enddate : " + resirvation.getEndDate() );
        System.out.println("resirvation room information: " );
        System.out.println("=>               room id : " + resirvation.getRoom().getId());
        System.out.println("=>               room : " + resirvation.getRoom().getRoomName());
        System.out.println("=>               room capacity : " + resirvation.getRoom().getRoomCapacity());
        System.out.println("=>               room price : " + resirvation.getRoom().getRoomPrice());
    }
}
