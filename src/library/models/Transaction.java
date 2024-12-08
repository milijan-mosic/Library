package library.models;

public class Transaction {
    private String id;
    private String bookId;
    private String ownerId;
    private int lentDate;
    private int returnDate;

    // Constructor
    public Transaction(String id, String bookId, String ownerId, int lentDate, int returnDate) {
        this.id = id;
        this.bookId = bookId;
        this.ownerId = ownerId;
        this.lentDate = lentDate;
        this.returnDate = returnDate;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getBookId() {
        return bookId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public int getLentDate() {
        return lentDate;
    }

    public int getReturnDate() {
        return returnDate;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setLentDate(int lentDate) {
        this.lentDate = lentDate;
    }

    public void setReturnDate(int returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", bookId='" + bookId + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", lentDate=" + lentDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
