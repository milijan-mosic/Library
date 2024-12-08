package library.models;

public class Book {
    private String id;
    private String name;
    private String author;
    private String category;
    private String ownerId;
    private int releaseDate;

    // Constructor
    public Book(String id, String name, String author, String category, String ownerId, int releaseDate) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.category = category;
        this.ownerId = ownerId;
        this.releaseDate = releaseDate;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
