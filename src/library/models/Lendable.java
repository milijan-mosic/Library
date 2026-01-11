package library.models;

public interface Lendable {
    void lend(String userId);
    void returnItem();
    boolean isAvailable();
}
