//Holds a course recommendation and message.
 //Shown in both the table and console.

package advisor.model;

public class Recommendation {
    private final String code;
    private final String message;

    public Recommendation(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }

    // old-style call (kept for backward compatibility)
    public String message() { return message; }

    @Override
    public String toString() {
        return code + ": " + message;
    }
}