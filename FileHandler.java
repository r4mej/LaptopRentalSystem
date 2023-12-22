import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileHandler {
    private String filename;

    public FileHandler(String filename) {
        this.filename = filename;
    }

    public Map<String, String> readFromFile() {
        Map<String, String> rentedLaptops = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                rentedLaptops.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rentedLaptops;
    }

    public void writeToFile(Map<String, String> rentedLaptops, Map<String, Laptop> availableLaptops) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
             BufferedWriter returnedWriter = new BufferedWriter(new FileWriter("returnedLaptops.txt", true))) {

            // Write back existing rented laptops to the file
            for (Map.Entry<String, String> entry : rentedLaptops.entrySet()) {
                String laptopId = entry.getKey();
                String studentId = entry.getValue();
                double price = availableLaptops.get(laptopId).getPrice();

                writer.write(laptopId + "," + studentId + "," + price);
                writer.newLine();
            }

            // Append returned laptops to "returnedLaptops.txt"
            for (Map.Entry<String, String> entry : rentedLaptops.entrySet()) {
                String laptopId = entry.getKey();
                if (!availableLaptops.containsKey(laptopId)) {
                    String studentId = entry.getValue();
                    double price = availableLaptops.get(laptopId).getPrice();

                    returnedWriter.write(laptopId + "," + studentId + "," + price);
                    returnedWriter.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
