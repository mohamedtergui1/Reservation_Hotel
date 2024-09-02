import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.IOException;
import java.io.*;
import java.nio.file.*;

public class Room {
    private int id;
    private String roomName;
    private int roomCapacity;
    private float roomPrice;
    static String fileName = "./rooms.txt";

    // Getter methods
    public int getId() {
        return id;
    }

    public String getRoomName() {
        return roomName;
    }


    public int getRoomCapacity() {
        return roomCapacity;
    }

    public float getRoomPrice() {
        return roomPrice;
    }

    // Setter methods
    public void setId(int id) {
        this.id = id;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }



    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    public void setRoomPrice(float roomPrice) {
        this.roomPrice = roomPrice;
    }

    public void add() {

        String data = Integer.toString(this.id) + System.lineSeparator() +
                this.roomName + System.lineSeparator() +
                Integer.toString(this.roomCapacity) + System.lineSeparator() +
                Float.toString(this.roomPrice) + System.lineSeparator();
        try {
            Files.write(Paths.get(fileName), data.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }


    public static void printAllRooms()
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(Room.fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Room room = new Room();
                room.setId(Integer.parseInt(line));
                room.setRoomName(reader.readLine());
                room.setRoomCapacity(Integer.parseInt(reader.readLine()));
                room.setRoomPrice(Float.parseFloat(reader.readLine()));
                System.out.println();
                System.out.println("Room ID: " + room.getId());
                System.out.println("Name: " + room.getRoomName());
                System.out.println("Capacity: " + room.getRoomCapacity());
                System.out.println("Price: " + room.getRoomPrice());
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
        } catch (IOException e) {
            System.out.println("Could not find or read the rooms file.");
        }
    }


    public void update() {
        // Define the paths for the old and new files

        String tempFile = "./tmp.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                Room room = new Room();
                room.setId(Integer.parseInt(line));
                room.setRoomName(reader.readLine());
                room.setRoomCapacity(Integer.parseInt(reader.readLine()));
                room.setRoomPrice(Float.parseFloat(reader.readLine()));

                if (room.getId() == this.id) {

                    writer.write(Integer.toString(this.id));
                    writer.newLine();
                    writer.write(this.roomName);
                    writer.newLine();
                    writer.write(Integer.toString(this.roomCapacity));
                    writer.newLine();
                    writer.write(Float.toString(this.roomPrice));
                    writer.newLine();
                } else {

                    writer.write(Integer.toString(room.getId()));
                    writer.newLine();
                    writer.write(room.getRoomName());
                    writer.newLine();
                    writer.write(Integer.toString(room.getRoomCapacity()));
                    writer.newLine();
                    writer.write(Float.toString(room.getRoomPrice()));
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            System.out.println("Error processing files: " + e.getMessage());
            return;
        }

        try {
            Path path = Paths.get(fileName);
            Path tempFilePath = Paths.get(tempFile);
            Files.deleteIfExists(path);
            Files.move(tempFilePath, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Error updating files: " + e.getMessage());
        }
    }
    public boolean getByRoomId() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (Integer.parseInt(line) == this.id) {

                    String name = reader.readLine();
                    int capacity = Integer.parseInt(reader.readLine());
                    float price = Float.parseFloat(reader.readLine());

                    this.setRoomName(name);
                    this.setRoomCapacity(capacity);
                    this.setRoomPrice(price);
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error processing files: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("the room not found  OR  Error parsing data: " + e.getMessage());
        }
        return false;
    }

    public void destroy() {
        // Define the paths for the old and new files

        String tempFile = "./tmp.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                Room room = new Room();
                room.setId(Integer.parseInt(line));
                room.setRoomName(reader.readLine());
                room.setRoomCapacity(Integer.parseInt(reader.readLine()));
                room.setRoomPrice(Float.parseFloat(reader.readLine()));

                if (room.getId() != this.id) {
                    writer.write(Integer.toString(room.getId()));
                    writer.newLine();
                    writer.write(room.getRoomName());
                    writer.newLine();
                    writer.write(Integer.toString(room.getRoomCapacity()));
                    writer.newLine();
                    writer.write(Float.toString(room.getRoomPrice()));
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            System.out.println("Error processing files: " + e.getMessage());
            return;
        }

        try {
            Path path = Paths.get(fileName);
            Path tempFilePath = Paths.get(tempFile);
            Files.deleteIfExists(path);
            Files.move(tempFilePath, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Error updating files: " + e.getMessage());
        }
    }


}
