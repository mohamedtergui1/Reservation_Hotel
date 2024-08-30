import java.util.ArrayList;

public interface ResirvationModelInterface {
    public boolean insert(Resirvation resirvation);
    public boolean delete(Resirvation resirvation);
    public boolean update(Resirvation resirvation);
    public Resirvation get(int id);
    public ArrayList<Resirvation> getAll();
}
