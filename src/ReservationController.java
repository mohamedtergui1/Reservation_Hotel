import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ReservationController {
    ReservationModelInterface model;
    Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public ReservationController(ReservationModelInterface model ) {
        this.model = model;
    }

    public void index()
    {
        ArrayList<Reservation> reservations = this.model.getAll();
        System.out.println("-----------------------------------------------------------------------------");
        for (Reservation reservation : reservations) {
            printResirvation(reservation);
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
                edit();
                break;
            case 3:
                delete();
                break;
            case 4:
                break;
            default:
                System.err.println("Invalid option.");
        }
    }

    public void create()
    {
        Reservation reservation = new Reservation();
        Room room = new Room();
        reservation.setId(this.model.lastId() + 1);

        System.out.print("Enter the starting date: (yyyy-MM-dd) ");
        while (reservation.getStartDate() == null) {
            reservation.setStartDate(scanner.nextLine());
        }

        System.out.print("Enter the ending date: (yyyy-MM-dd) ");
        while (reservation.getEndDate() == null) {
            reservation.setEndDate(scanner.nextLine());
        }
        Room.printAllRooms();
        System.out.print("Enter the room id: ");
        while (reservation.getRoom() == null) {
            room.setId(this.scanner.nextInt());
            this.scanner.nextLine();

            reservation.setRoom(room.getByRoomId() ? room : null);
        }
        while (true) {
            if (!validateResirvation(reservation,-1)) {
                if (model.insert(reservation))
                    System.out.println("Reservation added");
                else {
                    System.out.println("Reservation doesn't added");
                }
                break;
            } else {
                System.out.println("The room not available in this time choose another another room");
                System.out.print("Enter the room id: ");
                while (reservation.getRoom() == null) {
                    room.setId(scanner.nextInt());
                    this.scanner.nextLine();
                    reservation.setRoom(room.getByRoomId() ? room : null);
                }
            }
        }




        index();
    }


    public void edit()
    {
        System.out.print("Enter the id of the resirvation: ");
        int id = scanner.nextInt();
        Reservation res = this.model.get(id);
        if(res != null)
        {
            System.out.println("the selected resirvation: ");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                printResirvation(res);
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            Room room = new Room();
            Reservation reservation = new Reservation();
            reservation.setId(res.getId());

            System.out.print("Enter the new starting date: (yyyy-MM-dd) ");
            while (reservation.getStartDate() == null) {
                reservation.setStartDate(scanner.nextLine());
            }

            System.out.print("Enter the new ending date: (yyyy-MM-dd) ");
            while (reservation.getEndDate() == null) {
                reservation.setEndDate(scanner.nextLine());
            }
            Room.printAllRooms();
            System.out.print("Enter the new room id: ");
            while (reservation.getRoom() == null) {
                room.setId(scanner.nextInt());
                this.scanner.nextLine();
                reservation.setRoom(room.getByRoomId() ? room : null);
            }

            System.out.print("you want to save the changes (y/n) : ");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                while (!validateResirvation(reservation, reservation.getId())) {
                    if (!validateResirvation(reservation, reservation.getId()))
                    {
                        System.out.println("The room not available in this time choose another another room");
                        System.out.print("Enter the new room id: ");
                        while (reservation.getRoom() == null) {
                            room.setId(this.scanner.nextInt());
                            this.scanner.nextLine();
                            reservation.setRoom(room.getByRoomId() ? room : null);
                        }
                    }
                }
                if (model.update(reservation))
                    System.out.println("Reservation Updated");
                else {
                    System.out.println("Reservation doesn't Updated");
                }

            }
            index();
        }
        else {
            System.err.println("Resirvation doesn't exist");
            System.out.println("try again");
            edit();
        }

    }

    public void delete()
    {
        System.out.print("Enter the id of the resirvation: ");
        Reservation reservation = this.model.get(this.scanner.nextInt());
        this.scanner.nextLine();
        if(reservation == null)
        {
            System.err.println("Resirvation doesn't exist");
            return;
        }
        System.out.print("Are you sure you want to delete this reservation (y/n): ");
        String confirmation = this.scanner.nextLine();

        if (confirmation.equalsIgnoreCase("y")) {
            if (this.model.delete(reservation)) {
                System.out.println("Reservation deleted.");
            } else {
                System.err.println("Failed to delete reservation.");
            }
        } else if (confirmation.equalsIgnoreCase("n")) {
            System.out.println("Deletion cancelled.");
        } else {
            System.err.println("Invalid input. Deletion cancelled.");
        }
        index();
    }

    private void printResirvation(Reservation reservation)
    {
        System.out.println("resirvation id :" +  reservation.getId());
        System.out.println("resirvation startdate : " + reservation.getStartDate() );
        System.out.println("resirvation enddate : " + reservation.getEndDate() );
        System.out.println("resirvation room information: " );
        System.out.println("=>               room id : " + reservation.getRoom().getId());
        System.out.println("=>               room : " + reservation.getRoom().getRoomName());
        System.out.println("=>               room capacity : " + reservation.getRoom().getRoomCapacity());
        System.out.println("=>               room price : " + reservation.getRoom().getRoomPrice());
    }

    private boolean validateResirvation(Reservation reservation, int id)
    {
        ArrayList<Reservation> reservations = this.model.getAll();
        for (Reservation resirv : reservations) {
            if (resirv.getRoom().getId() == reservation.getRoom().getId() && id != resirv.getId()) {
                if (
                        this.isDateInRange(reservation.getStartDate(), resirv.getEndDate(), resirv.getEndDate())
                                ||
                                this.isDateInRange(reservation.getEndDate(), resirv.getEndDate(), resirv.getEndDate())
                )
                {
                    return false;
                }
            }
        }
        return true;
    }

    private  boolean isDateInRange(String dateString, String startDateString, String endDateString) {
        try {

            Date date = DATE_FORMAT.parse(dateString);
            Date startDate = DATE_FORMAT.parse(startDateString);
            Date endDate = DATE_FORMAT.parse(endDateString);

            return !date.before(startDate) && !date.after(endDate);
        } catch (ParseException e) {
            System.err.println("Date parsing error: " + e.getMessage());
            return false;
        }
    }

}
