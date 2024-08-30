import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class ResirvationModel implements ResirvationModelInterface {
    public boolean insert(Resirvation resirvation) {
        String data = Integer.toString(resirvation.getId()) + System.lineSeparator() +
                resirvation.getStartDate() + System.lineSeparator() +
                resirvation.getEndDate() + System.lineSeparator() +
                Integer.toString(resirvation.getRoom().getId()) + System.lineSeparator();
        try {
            Files.write(Paths.get(Resirvation.fileName), data.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        return false;
    }

    public boolean delete(Resirvation resirvation) {

        try (BufferedReader reader = new BufferedReader(new FileReader(Resirvation.fileName));
             BufferedWriter writer = new BufferedWriter(new FileWriter(Resirvation.tempFile))) {

            String line;
            Resirvation resi = new Resirvation();
            Room room = new Room();

            while ((line = reader.readLine()) != null) {

                resi.setId(Integer.parseInt(line));
                resi.setStartDate(reader.readLine());
                resi.setEndDate(reader.readLine());
                room.setId(Integer.parseInt(reader.readLine()));

                room.getByRoomId();
                resi.setRoom(room);

                if (resi.getId() != resirvation.getId()) {
                    writer.write(Integer.toString(resi.getId()));
                    writer.newLine();
                    writer.write(resi.getStartDate());
                    writer.newLine();
                    writer.write(resi.getEndDate());
                    writer.newLine();
                    writer.write(Integer.toString(resi.getRoom().getId()));
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            System.out.println("Error processing files: " + e.getMessage());
            return false;
        }
        return removeOldfileandRenameNewFile();

    }

    public boolean update(Resirvation resirvation) {

        try (BufferedReader reader = new BufferedReader(new FileReader(Resirvation.fileName));
             BufferedWriter writer = new BufferedWriter(new FileWriter(Resirvation.tempFile))) {

            String line;
            Resirvation resi = new Resirvation();
            Room room = new Room() ;
            while ((line = reader.readLine()) != null) {

                resi.setId(Integer.parseInt(line));
                resi.setStartDate(reader.readLine());
                resi.setEndDate(reader.readLine());
                room.setId(Integer.parseInt(reader.readLine()));
                room.getByRoomId();
                resi.setRoom(room);

                writer.write(Integer.toString(resi.getId()));
                writer.newLine();

                if (resi.getId() != resirvation.getId()) {

                    writer.write(resi.getStartDate());
                    writer.newLine();
                    writer.write(resi.getEndDate());
                    writer.newLine();
                    writer.write(Integer.toString(resi.getRoom().getId()));
                    writer.newLine();

                } else {

                    writer.write(resirvation.getStartDate());
                    writer.newLine();
                    writer.write(resirvation.getEndDate());
                    writer.newLine();
                    writer.write(Integer.toString(resirvation.getRoom().getId()));
                    writer.newLine();

                }
            }

        } catch (IOException e) {
            System.out.println("Error processing files: " + e.getMessage());
            return false;
        }

        return removeOldfileandRenameNewFile();
    }

    public Resirvation get(int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Resirvation.fileName))) {
            String line;
            Resirvation resirvation = new Resirvation();
            Room room = new Room();
            while ((line = reader.readLine()) != null) {
                resirvation.setId(Integer.parseInt(line));
                resirvation.setStartDate(reader.readLine());
                resirvation.setEndDate(reader.readLine());

                room.setId(Integer.parseInt(reader.readLine()));
                room.getByRoomId();
                resirvation.setRoom(room);
                if (resirvation.getId() == id)
                {
                    return resirvation;
                }
            }
        } catch (IOException e) {
            System.out.println("Could not find or read the Reservation file.");
        }
        return null;
    }

    public ArrayList<Resirvation> getAll() {
        ArrayList<Resirvation> resirvations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(Resirvation.fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                Resirvation resirvation = new Resirvation();
                Room room = new Room();
                resirvation.setId(Integer.parseInt(line));
                resirvation.setStartDate(reader.readLine());
                resirvation.setEndDate(reader.readLine());

                room.setId(Integer.parseInt(reader.readLine()));
                room.getByRoomId();
                resirvation.setRoom(room);
                resirvations.add(resirvation);
            }
        } catch (IOException e) {
            System.out.println("Could not find or read the Reservation file.");
        }
        return resirvations;
    }

    private boolean removeOldfileandRenameNewFile()
    {
        try {
            // Rename the temporary file to replace the original file
            Path originalPath = Paths.get(Resirvation.fileName);
            Path tempFilePath = Paths.get(Resirvation.tempFile);

            Files.deleteIfExists(originalPath);
            Files.move(tempFilePath, originalPath, StandardCopyOption.REPLACE_EXISTING);

            return true;
        } catch (IOException e) {
            System.out.println("Error updating files: " + e.getMessage());
            return false;
        }
    }

    public int lastId()
    {
        int idlast = 1;

        try (BufferedReader reader = new BufferedReader(new FileReader(Resirvation.fileName))) {
              String line;
            while ((line = reader.readLine()) != null) {

                idlast = Integer.parseInt(line);
                reader.readLine();
                reader.readLine();
                reader.readLine();

            }
        } catch (IOException e) {
            System.out.println("Could not find or read the Reservation file.");
        }
        return idlast;
    }
}
