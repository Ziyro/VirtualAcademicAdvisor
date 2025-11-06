/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.gui;

import javax.swing.*;

//input validation for text area
//make sure user enters only valid data
public final class GuiValidator 
{
    private GuiValidator() {}

    public static boolean requireDigits(JTextField field, String name) 
    {
        //check if the field only has numbers
        String v = field.getText().trim();
        if (!v.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, name + " must be digits only.");
            field.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean requirePersonName(JTextField field, String name) 
    {
        //checks for a proper name format (e.g. "John Smith")
        String v = field.getText().trim().replaceAll("\\s+", " ");
        if (!v.matches("(?i)[A-Za-z][A-Za-z'\\-]*(?:\\s+[A-Za-z][A-Za-z'\\-]*)+")) {
            JOptionPane.showMessageDialog(null, "Enter a real " + name + " (e.g., Bob Smith).");
            field.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean requireGpaInRange(JTextField field, String name, double min, double max) 
    {
        try 
        {
            //make sure GPA is a valid number
            double g = Double.parseDouble(field.getText().trim());
            if (g < min || g > max) throw new IllegalArgumentException();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, name + " must be between " + min + " and " + max + ".");
            field.requestFocus();
            return false;
        }
    }
}
