package diheng.exceptions;

public class DiHengException extends Exception {
    private final String recoverySuggestion;

    /**
     * Constructor for DiHengException with a message and a recovery suggestion.
     *
     * @param message            The error message.
     * @param recoverySuggestion The recovery suggestion.
     */
    public DiHengException(String message, String recoverySuggestion) {
        super(message);
        this.recoverySuggestion = recoverySuggestion;
    }

    /**
     * Returns the error message, prefixed with "OOPS!!!" and followed by the recovery suggestion.
     *
     * @return the error message
     */
    @Override
    public String getMessage() {
        return String.format("OOPS!!! Di Heng says: Hmm… %s\n\uD83D\uDD27 Here's a fix suggestion: %s",
                super.getMessage(), recoverySuggestion);
    }
}
