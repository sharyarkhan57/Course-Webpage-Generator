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
public class ScheduleItem {
    // THE TABLE WILL STORE SCHEDULE ITEMS
    private final StringProperty type;
    private final StringProperty date;
    private final StringProperty time;
    private final StringProperty title;
    private final StringProperty topic;
    private final StringProperty link;
    private final StringProperty criteria;
    private final StringProperty startDate;
    private final StringProperty endDate;

    
    public ScheduleItem(String type, String date, String time, String title, String topic, String link, String criteria, String startDate, String endDate) {
      this.type = new SimpleStringProperty(type);
      this.date = new SimpleStringProperty(date);
      this.time = new SimpleStringProperty(time);
      this.title = new SimpleStringProperty(title);
      this.topic = new SimpleStringProperty(topic);
      this.link = new SimpleStringProperty(link);
      this.criteria = new SimpleStringProperty(criteria);
      this.startDate = new SimpleStringProperty(startDate);
      this.endDate = new SimpleStringProperty(endDate);

    }
    
      
    public String getType() {
        return type.get();
    }

    public String getDate() {
        return date.get();
    }
    public String getTime(){
        return time.get();
    }
    public String getTitle() {
        return title.get();
    }

    public String getTopic() {
        return topic.get();
    }

    public String getLink() {
        return link.get();
    }

    public String getCriteria() {
        return criteria.get();
    }
     
    public String getStartDate() {
        return startDate.get();
    }
    
    public String getEndDate() {
        return endDate.get();
    }
    
    
    //added
    
    public String getStartingMondayMonth() {
        String stringDate=this.getStartDate();
        String [] dateParser=stringDate.split("/");
        return dateParser[0];
    }
    public String getStartingMondayDay() {
        String stringDate=this.getStartDate();
        String [] dateParser=stringDate.split("/");
        return dateParser[1];
    }
   public String getEndingFridayMonth() {
        String stringDate=this.getEndDate();
        String [] dateParser=stringDate.split("/");
        return dateParser[0];    
    }
    public String getEndingFridayDay() {
        String stringDate=this.getEndDate();
        String [] dateParser=stringDate.split("/");
        return dateParser[1];    
    }
    
    
    public String getDateMonth() {
        String date=this.getDate();
        String [] dateParser=date.split("/");
        return dateParser[0];    
    }
    public String getDateDay() {
        String date=this.getDate();
        String [] dateParser=date.split("/");
        return dateParser[1];    
    }

    //setters
    
    public void setType(String newString) {
        type.set(newString);
    }

    public void setDate(String newString) {
        date.set(newString);
    }
    
    public void setTime(String newString){
        time.set(newString);
    }
    
    public void setTitle(String newString) {
        title.set(newString);
    }

    public void setTopic(String newString) {
        topic.set(newString);
    }

    public void setLink(String newString) {
        link.set(newString);
    }

    public void setCriteria(String newString){
        criteria.set(newString);
    }

    public void setStartDate(String newString) {
        startDate.set(newString);
    }

    public void setEndDate(String newString) {
        endDate.set(newString);
    }

}