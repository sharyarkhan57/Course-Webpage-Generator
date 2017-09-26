
package csg.test_bed;

import csg.CSGManagerApp;
import csg.data.CSGData;
import csg.data.Course;
import csg.data.Recitation;
import csg.data.ScheduleItem;
import csg.data.SitePage;
import csg.data.Student;
import csg.data.TeachingAssistant;
import csg.data.Team;
import csg.file.CSGFiles;
import csg.file.TimeSlot;
import djf.components.AppDataComponent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;


public class TestSave {
    public static void main(String []Args){
        
        CSGManagerApp app=new CSGManagerApp();

        app.loadProperties("app_properties.xml");
        app.test();
        CSGData dataComponent = new CSGData(app);
        CSGFiles fileComponent = new CSGFiles(app);
        
        //CREATE COURSE DATA
        dataComponent.addCourseData("CSE","219","Spring","2017", "Java 3","Banerjee", "www.google.com", "C/:", "","","");
        dataComponent.addCourseData("CSE","219","Fall","2017", "Java 3","Mckenna", "www.ask.com", "C/:", "","","");
        //create office hours data
        //got NULL POINTERS SO I ADDED Directly IN TIME SLOTS
        
//        ArrayList<TimeSlot> officeHoursList = new ArrayList();
//        dataComponent.addOfficeHoursReservation("MONDAY","12_00pm","Sharyar");
//        dataComponent.addOfficeHoursReservation("MONDAY","12_30pm","Sharyar");
//        dataComponent.addOfficeHoursReservation("MONDAY","1_00pm","Sharyar");
//
//        
//        dataComponent.addOfficeHoursReservation("MONDAY","12_00pm","Eddie");
//        dataComponent.addOfficeHoursReservation("MONDAY","12_30pm","Eddie");
//        dataComponent.addOfficeHoursReservation("MONDAY","1_00pm","Eddie");
//        dataComponent.addOfficeHoursReservation("MONDAY","1_30pm","Eddie");

        
        //CREATE RECITATION DATA
        dataComponent.addRecitation("R01","McKenna","Monday, 3:30pm-4:30pm","Javits 100","Joe Shmoe","John Doe");
        dataComponent.addRecitation("R02","Banerjee","Tuesday, 3:30pm-4:30pm","OLD CS 2178","Kendall Jenner","Mila Kunis");
        dataComponent.addRecitation("R01","Esmaili","Wednesday, 1:30pm-2:30pm","Frey 101","Taylor Swift","Hillary Clinton");
        dataComponent.addRecitation("R02","JWONG","Fridau, 10:30am-1:30pm","Javits 100","Joe Shmoe","John Doe");



        //CREATE SCHEDULE ITEM DATA
        dataComponent.addScheduleItem("Holiday","4/1/2017","12:00am","April Fools","", "reddit.com", "none", "1/27/2017","5/17/2017");
        dataComponent.addScheduleItem("Holiday","3/14/2017","12:00am","Pi Day","", "reddit.com", "none", "1/27/2017","5/17/2017");

        dataComponent.addScheduleItem("Holiday","3/14/2017","12:00am","Pi Day","", "reddit.com", "none", "1/27/2017","5/17/2017");
        dataComponent.addScheduleItem("Holiday","3/14/2017","12:00am","Pi Day","", "reddit.com", "none", "1/27/2017","5/17/2017");
        dataComponent.addScheduleItem("Holiday","3/14/2017","12:00am","Pi Day","", "reddit.com", "none", "1/27/2017","5/17/2017");
        
        dataComponent.addScheduleItem("Lecture","3/2/2017","12:00pm","lecture","Java 1", "reddit.com", "none", "1/27/2017","5/17/2017");
        dataComponent.addScheduleItem("Lecture","3/4/2017","12:00pm","lecture","Java 2", "reddit.com", "none", "1/27/2017","5/17/2017");
        dataComponent.addScheduleItem("Lecture","3/8/2017","12:00pm","lecture","Java 3", "reddit.com", "none", "1/27/2017","5/17/2017");
        dataComponent.addScheduleItem("Lecture","3/10/2017","12:00pm","lecture","Java 4", "reddit.com", "none", "1/27/2017","5/17/2017");

        dataComponent.addScheduleItem("Reference","3/2/2017","","References","JavaFX Examples", "reddit.com", "none", "1/27/2017","5/17/2017");
        dataComponent.addScheduleItem("Reference","3/4/2017","","References","JavaFX Examples part 2", "reddit.com", "none", "1/27/2017","5/17/2017");
        
        dataComponent.addScheduleItem("Recitation","3/2/2017","","Recitation 1","JavaFX Examples part 2", "reddit.com", "none", "1/27/2017","5/17/2017");
        dataComponent.addScheduleItem("Recitation","3/4/2017","","Recitation 2","JavaFX Examples part 2", "reddit.com", "none", "1/27/2017","5/17/2017");
        
        dataComponent.addScheduleItem("Hw","3/2/2017","","HW 1","due at 11:59pm", "reddit.com", "https://docs.google.com/", "1/27/2017","5/17/2017");
        dataComponent.addScheduleItem("Hw","3/4/2017","","HW 2","due at 11:59pm", "reddit.com", "https://docs.google.com/", "1/27/2017","5/17/2017");

        //CREATE SITE PAGE
        dataComponent.addSitePage(true,"Home","index.html", "HomeBuilder.js");
        
        //CREATE STUDENTS
        dataComponent.addStudent("Sharyar", "Khan", "Best Team", "Lead Programmer");
        dataComponent.addStudent("Ariana", "Grande", "Best Team", "Lead Programmer");
        dataComponent.addStudent("Jospeh", "Stalin", "Best Team", "Lead Programmer");
        dataComponent.addStudent("Donald", "Trump", "Best Team", "Lead Programmer");
        
        dataComponent.addStudent("Kanye", "West", "Dream Team", "Lead Programmer");
        dataComponent.addStudent("Lebron", "James", "Dream Team", "Lead Programmer");
        dataComponent.addStudent("Trump", "DADDY", "Dream Team", "Lead Programmer");
        dataComponent.addStudent("Earl", "Sweatshirt", "Dream Team", "Lead Programmer");

        
        
        //CREATE TEACHING ASSISTANTS
        dataComponent.addTA("Sharyar Khan","sharyar.khan@stonybrook.edu", true);
        dataComponent.addTA("Kanye West","kanye.west@stonybrook.edu", true);
        dataComponent.addTA("Mac DeMarco","mac.demarco@stonybrook.edu", false);

        
        //CREATE TEAM
        dataComponent.addTeam("Best Team","#000000","#000000","google.com");
        dataComponent.addTeam("Dream Team","#FFFFFF","#FFFFFF","ask.com");

        LocalDate mon = LocalDate.of(2017, Month.JANUARY, 6);
        LocalDate fri = LocalDate.of(2017, Month.DECEMBER, 8);
        
        dataComponent.setFriday(fri);
        dataComponent.setMonday(mon);
       

//        try{
//            fileComponent.exportData(dataComponent, "I_Hate_This_Project.json");
//        } catch (Exception e){
//            System.out.println("COuld not save");
//        }
        
        try{
            fileComponent.saveData(dataComponent, "UNDERGRADTATATATATATTA__HAHAHAH.json");
        } catch (Exception e){
            System.out.println("COuld not save");
        }  
        

    }   
}