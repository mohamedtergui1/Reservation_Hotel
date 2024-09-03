import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class ReservationModel implements ReservationModelInterface {
    public boolean insert(Reservation reservation) {
        String data = reservation.getId() + System.lineSeparator() +
                reservation.getStartDate() + System.lineSeparator() +
                reservation.getEndDate() + System.lineSeparator() +
                 reservation.getRoom().getId() + System.lineSeparator();
        try {
            Files.write(Paths.get(Reservation.fileName), data.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        return false;
    }

    public boolean delete(Reservation reservation) {

        try (BufferedReader reader = new BufferedReader(new FileReader(Reservation.fileName));
             BufferedWriter writer = new BufferedWriter(new FileWriter(Reservation.tempFile))) {

            String line;
            Reservation resi = new Reservation();
            Room room = new Room();

            while ((line = reader.readLine()) != null) {

                resi.setId(Integer.parseInt(line));
                resi.setStartDate(reader.readLine());
                resi.setEndDate(reader.readLine());
                room.setId(Integer.parseInt(reader.readLine()));

                room.getByRoomId();
                resi.setRoom(room);

                if (resi.getId() != reservation.getId()) {
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

    public boolean update(Reservation reservation) {

        try (BufferedReader reader = new BufferedReader(new FileReader(Reservation.fileName));
             BufferedWriter writer = new BufferedWriter(new FileWriter(Reservation.tempFile))) {

            String line;
            Reservation resi = new Reservation();
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

                if (resi.getId() != reservation.getId()) {

                    writer.write(resi.getStartDate());
                    writer.newLine();
                    writer.write(resi.getEndDate());
                    writer.newLine();
                    writer.write(Integer.toString(resi.getRoom().getId()));
                    writer.newLine();

                } else {

                    writer.write(reservation.getStartDate());
                    writer.newLine();
                    writer.write(reservation.getEndDate());
                    writer.newLine();
                    writer.write(Integer.toString(reservation.getRoom().getId()));
                    writer.newLine();

                }
            }

        } catch (IOException e) {
            System.out.println("Error processing files: " + e.getMessage());
            return false;
        }

        return removeOldfileandRenameNewFile();
    }

    public Reservation get(int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Reservation.fileName))) {
            String line;
            Reservation reservation = new Reservation();
            Room room = new Room();
            while ((line = reader.readLine()) != null) {
                reservation.setId(Integer.parseInt(line));
                reservation.setStartDate(reader.readLine());
                reservation.setEndDate(reader.readLine());

                room.setId(Integer.parseInt(reader.readLine()));
                room.getByRoomId();
                reservation.setRoom(room);
                if (reservation.getId() == id)
                {
                    return reservation;
                }
            }
        } catch (IOException e) {
            System.out.println("Could not find or read the Reservation file.");
        }
        return null;
    }

    public ArrayList<Reservation> getAll() {
        ArrayList<Reservation> reservations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(Reservation.fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                Reservation reservation = new Reservation();
                Room room = new Room();
                reservation.setId(Integer.parseInt(line));
                reservation.setStartDate(reader.readLine());
                reservation.setEndDate(reader.readLine());

                room.setId(Integer.parseInt(reader.readLine()));
                room.getByRoomId();
                reservation.setRoom(room);
                reservations.add(reservation);
            }
        } catch (IOException e) {
            System.out.println("Could not find or read the Reservation file.");
        }
        return reservations;
    }

    private boolean removeOldfileandRenameNewFile()
    {
        try {
            // Rename the temporary file to replace the original file
            Path originalPath = Paths.get(Reservation.fileName);
            Path tempFilePath = Paths.get(Reservation.tempFile);

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

        try (BufferedReader reader = new BufferedReader(new FileReader(Reservation.fileName))) {
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
