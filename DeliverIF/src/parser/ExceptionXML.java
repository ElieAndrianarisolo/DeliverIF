package parser;

/**
 * The ExceptionXML class represents an exception specific to XML handling.
 * @author H4114
 */
public class ExceptionXML extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ExceptionXML with the specified detail message.
     *
     * @param message The detail message of the exception.
     */
    public ExceptionXML(String message) {

        super(message);

    }

}
