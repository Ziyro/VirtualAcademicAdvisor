/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.model;
//base class for ID
//used so student or course can share the same id setup
public abstract class BaseEntity 
{
    private final String id;
    protected BaseEntity(String id) 
    { 
        this.id = id; 
    }
    public String getId() { return id; }
}
