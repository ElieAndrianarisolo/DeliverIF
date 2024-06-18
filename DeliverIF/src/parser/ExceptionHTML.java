package parser;

/**
 * The ExceptionXML class represents an exception specific to HTML handling.
 * @author H4114
 */
public class ExceptionHTML extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ExceptionHTML with the specified detail message.
     *
     * @param message The detail message of the exception.
     */
    public ExceptionHTML(String message) {

        super(message);

    }

}
