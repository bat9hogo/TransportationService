package transportation.trips.exception;

public class TripNotFoundException extends RuntimeException {

    public TripNotFoundException(String id) {
        super("Trip with id '" + id + "' not found");
    }
}
