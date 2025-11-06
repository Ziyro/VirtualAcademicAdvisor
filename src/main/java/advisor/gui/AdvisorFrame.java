package advisor.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Main app wndow 
 *  99 dashboard and navigation bar.
 */
public class AdvisorFrame extends JFrame 
{
     private final AppController controller;
    private final CardLayout layout;
    private final JPanel cards;

    public AdvisorFrame(AppController controller)
    {
        this.controller = controller;
        setTitle("Virtual Academic Advisor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        layout = new CardLayout();
        cards = new JPanel(layout);
        getContentPane().setLayout(new BorderLayout());

        DashboardPanel dashboard = new DashboardPanel();
        AdvicePanel advice = new AdvicePanel();  // Added placeholder advice screen
        NavPanel nav = new NavPanel(this);

        cards.add(dashboard, "DASHBOARD");
        cards.add(advice, "ADVICE");
        add(cards, BorderLayout.CENTER);
        add(nav, BorderLayout.SOUTH);

        layout.show(cards, "DASHBOARD");
    }

    public void showCard(String name) 
    {
        layout.show(cards, name);
    }
}
