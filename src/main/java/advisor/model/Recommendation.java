/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.model;

//recommended course and message for the student
//display advice results
public class Recommendation
{
    private final String code;
    private final String message;

    public Recommendation(String code, String message)
    {
        this.code = code;
        this.message = message;
    }

    //getters
    public String getCode() { return code; }
    public String getMessage() { return message; }

    
    public String message() { return message; }

    @Override
    public String toString() { 
        //shows course code and message together
        return code + ": " + message; 
    }
}
