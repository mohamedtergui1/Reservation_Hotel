
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Resirvation {
    private int id;
    private String startDate;
    private String endDate;
    private Room room;
    static String fileName = "./resirvation.txt";
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
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be a positive integer");
        }
        this.id = id;
    }

    public void setStartDate(String startDate) {
        if (startDate == null || !isValidDate(startDate)) {
            throw new IllegalArgumentException("Start date must be in the format yyyy-MM-dd and cannot be null");
        }
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        if (endDate == null || !isValidDate(endDate)) {
            throw new IllegalArgumentException("End date must be in the format yyyy-MM-dd and cannot be null");
        }

        // Check if end date is after start date
        if (this.startDate != null && !isDateAfter(this.startDate, endDate)) {
            throw new IllegalArgumentException("End date must be after the start date");
        }

        this.endDate = endDate;
    }

    public void setRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        this.room = room;
    }

    private boolean isValidDate(String date) {
        try {
            DATE_FORMAT.setLenient(false);
            DATE_FORMAT.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
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
