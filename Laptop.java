public class Laptop {
    private String id;
    private String name;
    private double price;
    private String imagePath; // New field for the image path

    public Laptop(String id, String name, String imagePath, double price) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.price = price;

    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
