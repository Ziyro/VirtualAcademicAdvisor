package advisor.model;

/**
 * Represents a simple course.
 * Only includes code, name, and points for now.
 */
public class Course {
    private String code;
    private String name;
    private int points;

    public Course(String code, String name, int points) {
        this.code = code;
        this.name = name;
        this.points = points;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public int getPoints() { return points; }
}
