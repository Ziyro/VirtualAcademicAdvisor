package advisor.gui;

import javax.swing.*;
//this si the main class and were we run/compile our code
public class GuiMain 
{
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() ->
        {
            try 
            {
                AppController controller = new AppController();
               controller.getFrame().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Error starting GUI: " + e.getMessage(),
                        "Startup Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
