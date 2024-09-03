import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
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
            printReservation(reservation);
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

        System.out.print("Enter the starting date: (yyyy-MM-dd): ");
        while (reservation.getStartDate() == null) {
            reservation.setStartDate(scanner.nextLine());
        }

        System.out.print("Enter the ending date: (yyyy-MM-dd): ");
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

        System.out.print("you want to add the reservation (y/n) : ");

        if (scanner.nextLine().equalsIgnoreCase("y")) {

            while (!validateReservation(reservation, -1)) {
                System.out.println("The room is not available at this time. Choose another room.");
                System.out.print("Enter another room ID: ");
                reservation.setRoomNull();


                while (reservation.getRoom() == null) {
                    int roomId = this.scanner.nextInt();
                    this.scanner.nextLine();

                    room.setId(roomId);
                    reservation.setRoom(room.getByRoomId() ? room : null);

                    if (reservation.getRoom() == null) {
                        System.out.println("Invalid room ID. Please try again.");
                    }
                }
            }


            if (model.insert(reservation)) {
                System.out.println("Reservation updated successfully.");
            } else {
                System.out.println("Reservation could not be updated.");
            }
        }





        index();
    }


    public void edit()
    {
        System.out.print("Enter the id of the resirvation: ");
        int id = this.scanner.nextInt();
        this.scanner.nextLine();
        Reservation res = this.model.get(id);
        if(res != null)
        {
            System.out.println("the selected resirvation: ");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                printReservation(res);
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            Room room = new Room();
            Reservation reservation = new Reservation();
            reservation.setId(res.getId());

            System.out.print("Enter the new starting date: (yyyy-MM-dd): ");
            while (reservation.getStartDate() == null) {
                reservation.setStartDate(scanner.nextLine());
            }

            System.out.print("Enter the new ending date: (yyyy-MM-dd): ");
            while (reservation.getEndDate() == null) {
                reservation.setEndDate(scanner.nextLine());
            }
            Room.printAllRooms();
            System.out.print("Enter the new room id: ");
            while (reservation.getRoom() == null) {
                room.setId(this.scanner.nextInt());
                this.scanner.nextLine();
                reservation.setRoom(room.getByRoomId() ? room : null);
            }


            System.out.print("Do you want to update the reservation (y/n): ");
            if (scanner.nextLine().equalsIgnoreCase("y")) {


                while (!validateReservation(reservation, reservation.getId())) {
                    System.out.println("The room is not available at this time. Choose another room.");
                    System.out.print("Enter the new room ID: ");
                    reservation.setRoomNull();


                    while (reservation.getRoom() == null) {
                        try {
                            int roomId = this.scanner.nextInt();
                            this.scanner.nextLine();

                            room.setId(roomId);
                            reservation.setRoom(room.getByRoomId() ? room : null);

                            if (reservation.getRoom() == null) {
                                System.out.println("Invalid room ID. Please try again.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a numeric room ID.");
                            this.scanner.nextLine(); // Clear the invalid input
                        }
                    }
                }

                // Attempt to update the reservation and provide feedback
                if (model.update(reservation)) {
                    System.out.println("Reservation updated successfully.");
                } else {
                    System.out.println("Reservation could not be updated.");
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

    private void printReservation(Reservation reservation)
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



    private boolean validateReservation(Reservation reservation, int id) {
        ArrayList<Reservation> reservations = this.model.getAll();
        for (Reservation reserv : reservations) {
            if (reserv.getRoom().getId() == reservation.getRoom().getId() && id != reserv.getId()) {
                if (isDateRangeOverlap(
                        reservation.getStartDate(), reservation.getEndDate(),
                        reserv.getStartDate(), reserv.getEndDate()
                )) {
                    return false;
                }
            }
        }
        return true;
    }
    private boolean isDateRangeOverlap(String start1, String end1, String start2, String end2) {
        try {
            Date startDate1 = DATE_FORMAT.parse(start1);
            Date endDate1 = DATE_FORMAT.parse(end1);
            Date startDate2 = DATE_FORMAT.parse(start2);
            Date endDate2 = DATE_FORMAT.parse(end2);


            return !(endDate1.before(startDate2) || startDate1.after(endDate2));
        } catch (ParseException e) {
            System.err.println("Date parsing error: " + e.getMessage());
            return false;
        }
    }



}
