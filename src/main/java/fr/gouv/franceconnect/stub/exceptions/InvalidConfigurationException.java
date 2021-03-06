package fr.gouv.franceconnect.stub.exceptions;

/**
 * Created by tchabaud on 30/09/2016.
 * Thrown if there is a problem at configuration loading.
 */
public class InvalidConfigurationException extends RuntimeException {


    /**
     * Constructor.
     *
     * @param message exception message.
     */
    public InvalidConfigurationException(String message) {
        super(message);
    }


    /**
     * Constructor.
     *
     * @param message   exception message.
     * @param exception parent exception.
     */
    public InvalidConfigurationException(String message, Exception exception) {
        super(message, exception);
    }
}
