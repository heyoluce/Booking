package kg.epam.booking.domain.exception;

public class RoomNotAvailableException extends RuntimeException {

    public RoomNotAvailableException() {
        super("No available rooms for the selected dates");
    }
}
