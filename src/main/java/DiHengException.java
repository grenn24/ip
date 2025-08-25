
public class DiHengException extends Exception {

    private final String recoverySuggestion;

    public DiHengException(String message, String recoverySuggestion) {
        super(message);
        this.recoverySuggestion = recoverySuggestion;
    }

    @Override
    public String getMessage() {
        return String.format("OOPS!!! %s\n%s", super.getMessage(), recoverySuggestion);
    }
}
