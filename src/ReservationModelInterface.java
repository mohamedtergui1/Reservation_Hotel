import java.util.ArrayList;

public interface ReservationModelInterface {
    public boolean insert(Reservation reservation);
    public boolean delete(Reservation reservation);
    public boolean update(Reservation reservation);
    public Reservation get(int id);
    public ArrayList<Reservation> getAll();
    public int lastId();
}
