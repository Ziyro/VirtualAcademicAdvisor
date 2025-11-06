package advisor.gui;

/**
 * Controls transitions between GUI panels.
 * In this early version, only the main frame and dashboard are active.
 */
public class AppController {
    private final AdvisorFrame frame;

    public AppController() {
        this.frame = new AdvisorFrame(this);
    }

    public AdvisorFrame getFrame() {
        return frame;
    }
}
