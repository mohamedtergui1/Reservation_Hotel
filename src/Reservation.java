import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {
    private int id;
    private String startDate;
    private String endDate;
    private Room room;
    static String fileName = "./reservation.txt";
    static String tempFile = "./tmp.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public int getId() {
        return id;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public Room getRoom() {
        return room;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStartDate(String startDate) {
        if (startDate == null || isValidDate(startDate)) {
            System.out.print("Start date must be in the format yyyy-MM-dd and cannot be null TRY again :");
            return;
        }
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        if (endDate == null || isValidDate(endDate)) {
            System.out.println("End date must be in the format yyyy-MM-dd and cannot be null");
            return;
        }

        if (this.startDate != null && !isDateAfter(this.startDate, endDate)) {
            System.out.print("End date must be after the start date TRY again :");
            return;
        }
        this.endDate = endDate;
    }

    public void setRoom(Room room) {

        if (room == null) {
            System.out.print(" selected room not found  TRY again :");
            return;
        }

        this.room = room;
    }

    private boolean isValidDate(String date) {

        try {

            DATE_FORMAT.setLenient(false);
            DATE_FORMAT.parse(date);
            return false;

        } catch (ParseException e) {

            return true;

        }
    }

    private boolean isDateAfter(String startDate, String endDate) {
        try {
            Date start = DATE_FORMAT.parse(startDate);
            Date end = DATE_FORMAT.parse(endDate);
            return end.after(start);
        } catch (ParseException e) {
            return false;
        }
    }

}