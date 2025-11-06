/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

  //Base class just to give all entities an ID.
// Helps keep code consistent.
 package advisor.model;

public abstract class BaseEntity {
    private final String id;

    protected BaseEntity(String id) {
        this.id = id;
    }

    public String getId() { return id; }
}
