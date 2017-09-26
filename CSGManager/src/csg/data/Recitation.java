/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sharyar
 */
public class Recitation {
    // THE TABLE WILL STORE RECITATIONS
    private final StringProperty section;
    private final StringProperty instructor;
    private final StringProperty dayTime;
    private final StringProperty location;
    private final StringProperty supervisingTAOne;
    private final StringProperty supervisingTATwo;

    public Recitation(String section, String instructor, String dayTime, String location, String supervisingTAOne, String supervisingTATwo) {
      
        this.section = new SimpleStringProperty(section);
        this.instructor = new SimpleStringProperty(instructor);
        this.dayTime = new SimpleStringProperty(dayTime);
        this.location = new SimpleStringProperty(location);
        this.supervisingTAOne = new SimpleStringProperty(supervisingTAOne);
        this.supervisingTATwo = new SimpleStringProperty(supervisingTATwo);
    }

    public String getSection() {
        return section.get();
    }

    public String getInstructor() {
        return instructor.get();
    }

    public String getDayTime() {
        return dayTime.get();
    }

    public String getLocation() {
        return location.get();
    }

    public String getSupervisingTAOne() {
        return supervisingTAOne.get();
    }

    public String getSupervisingTATwo() {
        return supervisingTATwo.get();
    }
     
    public void setSection(String newSection) {
        section.set(newSection);
    }
    
    public void setInstructor(String newInstructor) {
        instructor.set(newInstructor);
    }
    
    public void setDayTime(String newDayTime) {
        dayTime.set(newDayTime);
    }
    
    public void setLocation(String newLocation) {
        location.set(newLocation);
    }
    
    public void setSuperVisingTAOne(String newSupervisingTAOne) {
        supervisingTAOne.set(newSupervisingTAOne);
    }
    
    public void setSuperVisingTATwo(String newSupervisingTATwo) {
        supervisingTATwo.set(newSupervisingTATwo);
    }
    
    @Override
    public String toString(){
        return section.getValue()+" "+instructor.getValue()+" "+dayTime.getValue()
                +" "+location.getValue()+" "+supervisingTAOne.getValue()+" "+supervisingTATwo.getValue();
    }
  
}

