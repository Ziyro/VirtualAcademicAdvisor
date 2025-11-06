package advisor.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a course record from database.
 * I included both constructors to handle DB data and manual creation.
 */
public class Course 
{
    private String code;
     private String name;
     private int points;
    private List<String> prerequisites;

    // used when loading from the database (already split prereq list)
    public Course(String code, String name, int points, List<String> prereq) {
        
         this.code = code;
        this.name = name;
         this.points = points;
        this.prerequisites = prereq == null ? new ArrayList<>() : new ArrayList<>(prereq);
    }

    // fallback for when we only have a single string of prereqs
    public Course(String code, String name, int points, String prereqString) {
        this.code = code;
        this.name = name;
        this.points = points;
        if (prereqString == null || prereqString.isBlank()) {
            this.prerequisites = new ArrayList<>();
        } else {
            this.prerequisites = Arrays.asList(prereqString.split(","));
        }
    }

    // getters used by GUI + repository
    public String getCode() { return code; }
    public String getName() { return name; }
    public int getPoints() { return points; }
    public List<String> getPrerequisites() { return prerequisites; }

    @Override
    public String toString() {
        return code + " - " + name + " (" + points + " pts)";
    }
}
