/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

/**
 *
 * @author sharyar
 */
public class Team {
      // THE TABLE WILL STORE SCHEDULE ITEMS
    private final StringProperty name;
    private final StringProperty color;
    private final StringProperty textColor;
    private final StringProperty link;
    
      public Team(String name, String color, String textColor, String link) {
        this.name = new SimpleStringProperty(name);
        this.color = new SimpleStringProperty(color);
        this.textColor = new SimpleStringProperty(textColor);
        this.link = new SimpleStringProperty(link);
    }
      
    public String getName() {
        return name.get();
    }

    public String getColor() {
        return color.get();
    }
    
    public int getRedColor() {
    Color c =  Color.web(this.getColor()); 
    return (int) c.getRed();
    
    }
    
    public int getBlueColor() {
        Color c =  Color.web(this.getColor()); 
    return (int) c.getBlue();
    }
        
    public int getGreenColor() {
        Color c =  Color.web(this.getColor()); 
    return (int) c.getGreen();
    }


    public String getTextColor() {
        return textColor.get();
    }
    
    public String getTextColorString() {
        if(textColor.get().compareTo("#FFFFFF")==0){
            return "white";
        }
        else{
            return "black";
        }
    }

    public String getLink() {
        return link.get();
    } 
      
      
    public void setName(String name) {
        this.name.set(name);
    }
    
    public void setColor(String color) {
        this.color.set(color);
    }
    
    public void setTextColor(String textColor) {
        this.textColor.set(textColor);
    }
    
    public void setLink(String link) {
        this.link.set(link);
    }
    
    @Override
    public String toString() {
        return name.getValue();
    }
    
}
