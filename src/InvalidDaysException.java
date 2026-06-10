/**
 * SYLLABUS: LAB 5 – Exception Handling
 *
 * Custom exception class extending Exception.
 * Thrown when the user enters invalid rental days (days <= 0).
 * Custom exceptions allow meaningful error messages for business rules.
 */
public class InvalidDaysException extends Exception {

    /**
     * Creates exception with a custom message.
     *
     * @param message error description
     */
    public InvalidDaysException(String message) {
        super(message);
    }
}
