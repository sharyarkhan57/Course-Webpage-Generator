package csg.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import csg.CSGManagerApp;
import csg.data.CSGData;
import csg.data.Course;
import csg.data.Recitation;
import csg.data.ScheduleItem;
import csg.data.Student;
import csg.data.TeachingAssistant;
import csg.data.Team;
import csg.workspace.CSGWorkspace;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * This class serves as the file component for the TA
 * manager app. It provides all saving and loading 
 * services for the application.
 * 
 * @author Richard McKenna
 */
public class CSGFiles implements AppFileComponent {
    // THIS IS THE APP ITSELF
    CSGManagerApp app;
    
    // THESE ARE USED FOR IDENTIFYING JSON TYPES
    static final String JSON_START_HOUR = "startHour";
    static final String JSON_END_HOUR = "endHour";
    static final String JSON_OFFICE_HOURS = "officeHours";
    static final String JSON_DAY = "day";
    static final String JSON_TIME = "time";
    static final String JSON_NAME = "name";
    static final String JSON_UNDERGRAD_TAS = "undergrad_tas";
    static final String JSON_EMAIL = "email";
    static final String JSON_UNDERGRAD = "undergrad";

    
    //RECITATIONS
    static final String RECITATIONS = "recitations";
    static final String RECITATIONS_SECTION = "section";
    static final String RECITATIONS_INSTRUCTOR = "instructor";
    static final String RECITATIONS_DAYTIME = "day_time";
    static final String RECITATIONS_LOCATION = "location";
    static final String RECITATIONS_TA_ONE = "ta_1";
    static final String RECITATIONS_TA_TWO = "ta_2";
    
    //SCHEDULE
    static final String SCHEDULE_ITEM_STARTING_MONDAY_MONTH = "startingMondayMonth";
    static final String SCHEDULE_ITEM_STARTING_MONDAY_DAY = "startingMondayDay";
    static final String SCHEDULE_ITEM_ENDING_FRIDAY_MONTH = "endingFridayMonth";
    static final String SCHEDULE_ITEM_ENDING_FRIDAY_DAY = "endingFridayDay";
    static final String SCHEDULE_ITEM_MONTH = "month";
    static final String SCHEDULE_ITEM_DAY = "day";
    static final String SCHEDULE_ITEM_TIME = "time";
    static final String SCHEDULE_ITEM_TITLE = "title";
    static final String SCHEDULE_ITEM_TOPIC = "topic";
    static final String SCHEDULE_ITEM_LINK = "link";
    static final String SCHEDULE_TIME= "time";
    static final String SCHEDULE_ITEM_CRITERIA = "criteria";
    
    static final String SCHEDULE_HOLIDAY= "holiday";
    static final String SCHEDULE_LECTURES = "lectures";
    static final String SCHEDULE_REFERENCES = "references";
    static final String SCHEDULE_RECITATIONS = "recitationss";
    static final String SCHEDULE_HWS = "hws";
    
    //TEAM
    static final String TEAMS = "teams";
    static final String TEAM_NAME = "name";
    static final String RED = "red";
    static final String BLUE = "blue";
    static final String GREEN = "green";
    static final String TEXT_COLOR = "text_color";
    static final String TEAM_LINK = "LINK";
    
    
    //Students
    static final String STUDENTS = "students";
    static final String LAST_NAME = "lastName";
    static final String FIRST_NAME = "firstName";
    static final String TEAM = "team";
    static final String ROLE = "role";

    //PROJECTS
    static final String PROJECT_NAME = "name";
    static final String PROJECT_STUDENTS = "students";
    static final String PROJECT_LINK = "link";
    static final String PROJECT_SEMESTER = "semesters";
    static final String PROJECTS = "projects";
    static final String WORK = "work";



    
    public CSGFiles(CSGManagerApp initApp) {
        app = initApp;
    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
	// CLEAR THE OLD DATA OUT
	CSGData dataManager = (CSGData)data;

	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);

	// LOAD THE START AND END HOURS
	String startHour = json.getString(JSON_START_HOUR);
        String endHour = json.getString(JSON_END_HOUR);
        dataManager.initHours(startHour, endHour);

        // NOW RELOAD THE WORKSPACE WITH THE LOADED DATA
        app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());

        // NOW LOAD ALL THE UNDERGRAD TAs
        JsonArray jsonTAArray = json.getJsonArray(JSON_UNDERGRAD_TAS);
        for (int i = 0; i < jsonTAArray.size(); i++) {
            JsonObject jsonTA = jsonTAArray.getJsonObject(i);
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
            String isUndergrad = jsonTA.getString(JSON_UNDERGRAD);
            
            if(isUndergrad.equals("true")){
                dataManager.addTA(name, email,true);
            }
            else{
                dataManager.addTA(name, email,false);
            }
                
        }

        // LOAD ALL THE OFFICE HOURS
        JsonArray jsonOfficeHoursArray = json.getJsonArray(JSON_OFFICE_HOURS);
        for (int i = 0; i < jsonOfficeHoursArray.size(); i++) {
            JsonObject jsonOfficeHours = jsonOfficeHoursArray.getJsonObject(i);
            String day = jsonOfficeHours.getString(JSON_DAY);
            String time = jsonOfficeHours.getString(JSON_TIME);
            String name = jsonOfficeHours.getString(JSON_NAME);
            dataManager.addOfficeHoursReservation(day, time, name);
        }
        
        // LOAD ALL THE RECITATIONS
        JsonArray recitationsArray = json.getJsonArray(RECITATIONS);
        for (int i = 0; i < recitationsArray.size(); i++) {
            JsonObject recitationsHours = recitationsArray.getJsonObject(i);
            String section = recitationsHours.getString(RECITATIONS_SECTION);
            String daytime = recitationsHours.getString(RECITATIONS_DAYTIME);
            String location = recitationsHours.getString(RECITATIONS_LOCATION);
            String taOne = recitationsHours.getString(RECITATIONS_TA_ONE);
            String taTwo = recitationsHours.getString(RECITATIONS_TA_TWO);
            dataManager.addRecitation(section.substring(0, 4),section.substring(section.indexOf("(")+1,section.indexOf(")")), daytime, location,taOne,taTwo);
            System.out.println(dataManager.getRecitationList().get(i).toString());
        }
        
        // LOAD ALL THE STARTING AND ENDING DATES
       	String startingMondayMonth = json.getString(SCHEDULE_ITEM_STARTING_MONDAY_MONTH);
        String startingMondayDay = json.getString(SCHEDULE_ITEM_STARTING_MONDAY_DAY);
        String endingFridayMonth = json.getString(SCHEDULE_ITEM_ENDING_FRIDAY_MONTH);
        String endingFridayDay = json.getString(SCHEDULE_ITEM_ENDING_FRIDAY_DAY);
            
        //handles creating date
            if(Integer.parseInt(startingMondayMonth)<10){
                "0".concat(startingMondayMonth);
            }
            if(Integer.parseInt(startingMondayDay)<10){
                "0".concat(startingMondayDay);
            }
            
            if(Integer.parseInt(endingFridayMonth)<10){
                "0".concat(endingFridayMonth);
            }
            if(Integer.parseInt(endingFridayDay)<10){
                "0".concat(endingFridayDay);
            }  
        String startDate= startingMondayMonth+"/"+startingMondayDay+"/"+"2017";
        String endDate= endingFridayMonth+"/"+endingFridayDay+"/"+"2017";

        System.out.println(startDate);
        System.out.println(endDate);

        LocalDate mon = LocalDate.of(2017,Integer.parseInt(startingMondayMonth),Integer.parseInt(startingMondayDay));
        LocalDate fri = LocalDate.of(2017,Integer.parseInt(endingFridayMonth),Integer.parseInt(endingFridayDay));
        
        dataManager.setMonday(mon);
        dataManager.setFriday(fri);
        
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        
        workspace.getStartingMondayDatePicker().getEditor().setText(startDate);
        workspace.getEndingFridayDatePicker().getEditor().setText(endDate);

        
        
        // LOAD ALL HOLIDAYS
        JsonArray holidayArray = json.getJsonArray(SCHEDULE_HOLIDAY);
        for (int i = 0; i < holidayArray.size(); i++) {
            JsonObject holiday = holidayArray.getJsonObject(i);
            String month = holiday.getString(SCHEDULE_ITEM_MONTH);
            String day = holiday.getString(SCHEDULE_ITEM_DAY);
            String title = holiday.getString(SCHEDULE_ITEM_TITLE);
            String link = holiday.getString(SCHEDULE_ITEM_LINK);
            
            //handles creating date
            if(Integer.parseInt(month)<10){
                "0".concat(month);
            }
            if(Integer.parseInt(day)<10){
                "0".concat(day);
            }
            String date= month+"/"+day+"/"+"2017";
            
            dataManager.addScheduleItem("Holiday",date,null,title,null,link,null,"startdate","enddate");
        }

        
        // LOAD ALL LECTURES
        JsonArray lecturesArray = json.getJsonArray(SCHEDULE_LECTURES);
        for (int i = 0; i < lecturesArray.size(); i++) {
            JsonObject lecturessHours = lecturesArray.getJsonObject(i);
            String month = lecturessHours.getString(SCHEDULE_ITEM_MONTH);
            String day = lecturessHours.getString(SCHEDULE_ITEM_DAY);
            String title = lecturessHours.getString(SCHEDULE_ITEM_TITLE);
            String topic = lecturessHours.getString(SCHEDULE_ITEM_TOPIC);
            
            //handles creating date
            if(Integer.parseInt(month)<10){
                "0".concat(month);
            }
            if(Integer.parseInt(day)<10){
                "0".concat(day);
            }
            String date= month+"/"+day+"/"+"2017";
            
            dataManager.addScheduleItem("Lecture",date,null,title,null,topic,null,startDate,endDate);
        }

        
        // LOAD ALL REFERENCES
         JsonArray referencesArray = json.getJsonArray(SCHEDULE_REFERENCES);
            for (int i = 0; i < referencesArray.size(); i++) {
                JsonObject references = referencesArray.getJsonObject(i);
                String month = references.getString(SCHEDULE_ITEM_MONTH);
                String day = references.getString(SCHEDULE_ITEM_DAY);
                String title = references.getString(SCHEDULE_ITEM_TITLE);
                String link = references.getString(SCHEDULE_ITEM_LINK);
            
                //handles creating date
                if(Integer.parseInt(month)<10){
                    "0".concat(month);
                }
                if(Integer.parseInt(day)<10){
                    "0".concat(day);
                }
                String date= month+"/"+day+"/"+"2017";

                dataManager.addScheduleItem("Reference",date,null,title,null,link,null,startDate,endDate);
        }
            
            
        // LOAD ALL RECITATIONS
            JsonArray recitationsScheduleArray = json.getJsonArray(SCHEDULE_RECITATIONS);
            for (int i = 0; i < recitationsScheduleArray.size(); i++) {
                JsonObject recitations = recitationsScheduleArray.getJsonObject(i);
                String month = recitations.getString(SCHEDULE_ITEM_MONTH);
                String day = recitations.getString(SCHEDULE_ITEM_DAY);
                String title = recitations.getString(SCHEDULE_ITEM_TITLE);
                String topic = recitations.getString(SCHEDULE_ITEM_TOPIC);

                //handles creating date
                if(Integer.parseInt(month)<10){
                    "0".concat(month);
                }
                if(Integer.parseInt(day)<10){
                    "0".concat(day);
                }
                String date= month+"/"+day+"/"+"2017";

                dataManager.addScheduleItem("Recitation",date,null,title,topic,null,null,startDate,endDate);
        }
        // LOAD ALL HWS
            JsonArray hwsScheduleArray = json.getJsonArray(SCHEDULE_HWS);
            for (int i = 0; i < hwsScheduleArray.size(); i++) {
                JsonObject hws = hwsScheduleArray.getJsonObject(i);
                String month = hws.getString(SCHEDULE_ITEM_MONTH);
                String day = hws.getString(SCHEDULE_ITEM_DAY);
                String title = hws.getString(SCHEDULE_ITEM_TITLE);
                String topic = hws.getString(SCHEDULE_ITEM_TOPIC);
                String link = hws.getString(SCHEDULE_ITEM_LINK);
                String time = hws.getString(SCHEDULE_ITEM_TIME);
                String criteria = hws.getString(SCHEDULE_ITEM_LINK);
                
                //handles creating date
                if(Integer.parseInt(month)<10){
                    "0".concat(month);
                }
                if(Integer.parseInt(day)<10){
                    "0".concat(day);
                }
                String date= month+"/"+day+"/"+"2017";
                dataManager.addScheduleItem("Hw",date,time,title,topic,link,criteria,startDate,endDate);
        }

        
//        // LOAD ALL TEAMS
            JsonArray teamsArray = json.getJsonArray(TEAMS);
            for (int i = 0; i < teamsArray.size(); i++) {
                JsonObject team = teamsArray.getJsonObject(i);
                String name = team.getString(TEAM_NAME);
                int red = team.getInt(RED);
                int green = team.getInt(GREEN);
                int blue = team.getInt(BLUE);
                String textColor = team.getString(TEXT_COLOR);
                String link = team.getString(PROJECT_LINK);
                String color="#"+Integer.toHexString(red)+Integer.toHexString(green)+Integer.toHexString(blue);
                dataManager.addTeam(name, color, textColor, link);
            }

        // LOAD ALL STUDENTS
            JsonArray studentsArray = json.getJsonArray(STUDENTS);
            for (int i = 0; i < studentsArray.size(); i++) {
                JsonObject students = studentsArray.getJsonObject(i);
                String lastName = students.getString(LAST_NAME);
                String firstName = students.getString(FIRST_NAME);
                String team = students.getString(TEAM);
                String role = students.getString(ROLE);

               System.out.println(firstName+" "+lastName);

               dataManager.addStudent(firstName, lastName, team, role);
            }

    }
      
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }

    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
	// GET THE DATA
	CSGData dataManager = (CSGData)data;

//                      load course data       
//          //          ____________________________________________            \\
//         //           NOW BUILD THE COURSEDETAILS JSON OBJCTS TO SAVE          \\
//        //            --------------------------------------------              \\
//        
//        Course courseData= dataManager.courseData;
//        
//	JsonArrayBuilder courseArrayBuilder = Json.createArrayBuilder();
//
//	    JsonObject recitationJson = Json.createObjectBuilder()
//		    .add(RECITATIONS_SECTION,""+ rc.getSection() + " ("+rc.getInstructor()+")")
//                    .add(RECITATIONS_DAYTIME, rc.getDayTime())
//                    .add(RECITATIONS_LOCATION, rc.getLocation())
//                    .add(RECITATIONS_TA_ONE, rc.getSupervisingTAOne())
//                    .add(RECITATIONS_TA_TWO, rc.getSupervisingTATwo()).build();
//	    courseArrayBuilder.add(recitationJson);
//	
//        JsonArray recitationArray = recitationArrayBuilder.build();
//        System.out.println(recitationArray);
        
	// NOW BUILD THE TA JSON OBJCTS TO SAVE
	JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
	ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
	for (TeachingAssistant ta : tas) {	
            if(ta.isIsUndergrad()){
                JsonObject taJson = Json.createObjectBuilder()
                .add(JSON_NAME, ta.getName())
                .add(JSON_EMAIL, ta.getEmail())
                .add(JSON_UNDERGRAD, "true").build();
                taArrayBuilder.add(taJson);
            }
            else{
                JsonObject taJson = Json.createObjectBuilder()
                .add(JSON_NAME, ta.getName())
                .add(JSON_EMAIL, ta.getEmail())
                .add(JSON_UNDERGRAD, "false").build();
                taArrayBuilder.add(taJson);
            }

	}
	JsonArray undergradTAsArray = taArrayBuilder.build();

	// NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
	JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
        
        //Original
        
        //ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager)
        //test
        ArrayList<TimeSlot> officeHours;
                try
            {
                officeHours = TimeSlot.buildOfficeHoursList(dataManager);
            }
            catch(NullPointerException e)
            {
                officeHours =  TimeSlot.test();
           }
       //end test     
                
	for (TimeSlot ts : officeHours) {	    
	    JsonObject tsJson = Json.createObjectBuilder()
		    .add(JSON_DAY, ts.getDay())
		    .add(JSON_TIME, ts.getTime())
		    .add(JSON_NAME, ts.getName()).build();
	    timeSlotArrayBuilder.add(tsJson);
	}
	JsonArray timeSlotsArray = timeSlotArrayBuilder.build();
        
        
          //          ____________________________________________         \\
         //           NOW BUILD THE RECITATION JSON OBJCTS TO SAVE          \\
        //            --------------------------------------------           \\
        
        ObservableList<Recitation> recitations= dataManager.getRecitationList();
        
	JsonArrayBuilder recitationArrayBuilder = Json.createArrayBuilder();

        for ( Recitation rc : recitations) {	    
	    JsonObject recitationJson = Json.createObjectBuilder()
		    .add(RECITATIONS_SECTION,""+ rc.getSection() + " ("+rc.getInstructor()+")")
                    .add(RECITATIONS_DAYTIME, rc.getDayTime())
                    .add(RECITATIONS_LOCATION, rc.getLocation())
                    .add(RECITATIONS_TA_ONE, rc.getSupervisingTAOne())
                    .add(RECITATIONS_TA_TWO, rc.getSupervisingTATwo()).build();
	    recitationArrayBuilder.add(recitationJson);
	}
        JsonArray recitationArray = recitationArrayBuilder.build();
        System.out.println(recitationArray);

        

          //          ____________________________________________         \\
         //            NOW BUILD THE SCHEDULE JSON OBJCTS TO SAVE           \\
        //            --------------------------------------------           \\
	
        ObservableList<ScheduleItem> scheduleItem= dataManager.getScheduleItemsList();

        JsonArrayBuilder scheduleDateArrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder scheduleHolidayArrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder scheduleLectureArrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder scheduleReferencesArrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder scheduleRecitationsArrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder scheduleHWSArrayBuilder = Json.createArrayBuilder();
        



        for ( ScheduleItem si : scheduleItem) {	    
            
            if( si.getType().compareToIgnoreCase("Holiday")==0){
                    JsonObject scheduleHolidayJson = Json.createObjectBuilder()
                    .add(SCHEDULE_ITEM_MONTH, si.getDateMonth())
                    .add(SCHEDULE_ITEM_DAY, si.getDateDay())
                    .add(SCHEDULE_ITEM_TITLE, si.getTitle())
                    .add(SCHEDULE_ITEM_LINK, si.getLink()).build();
                    
                scheduleHolidayArrayBuilder.add(scheduleHolidayJson);
             }
             
            if( si.getType().compareToIgnoreCase("lecture")==0){
                    JsonObject scheduleLectureJson = Json.createObjectBuilder()
                    .add(SCHEDULE_ITEM_MONTH, si.getDateMonth())
                    .add(SCHEDULE_ITEM_DAY, si.getDateDay())
                    .add(SCHEDULE_ITEM_TITLE, si.getTitle())
                    .add(SCHEDULE_ITEM_TOPIC, si.getTopic())
                    .add(SCHEDULE_ITEM_LINK, si.getLink()).build();
                    
                scheduleLectureArrayBuilder.add(scheduleLectureJson);
             }
            
            if( si.getType().compareToIgnoreCase("reference")==0){
                    JsonObject scheduleReferencesJson = Json.createObjectBuilder()
                    .add(SCHEDULE_ITEM_MONTH, si.getDateMonth())
                    .add(SCHEDULE_ITEM_DAY, si.getDateDay())
                    .add(SCHEDULE_ITEM_TITLE, si.getTitle())
                    .add(SCHEDULE_ITEM_LINK, si.getLink()).build();
                    
                scheduleReferencesArrayBuilder.add(scheduleReferencesJson);
             }
            
            if( si.getType().compareToIgnoreCase("recitation")==0){
                    JsonObject scheduleRecitationsJson = Json.createObjectBuilder()
                    .add(SCHEDULE_ITEM_MONTH, si.getDateMonth())
                    .add(SCHEDULE_ITEM_DAY, si.getDateDay())
                    .add(SCHEDULE_ITEM_TITLE, si.getTitle())
                    .add(SCHEDULE_ITEM_TOPIC, si.getTopic()).build();
                    
                scheduleRecitationsArrayBuilder.add(scheduleRecitationsJson);
             }
            
            if( si.getType().compareToIgnoreCase("hw")==0){
                    JsonObject scheduleHWSJson = Json.createObjectBuilder()
                    .add(SCHEDULE_ITEM_MONTH, si.getDateMonth())
                    .add(SCHEDULE_ITEM_DAY, si.getDateDay())
                    .add(SCHEDULE_ITEM_TITLE, si.getTitle())
                    .add(SCHEDULE_ITEM_TOPIC, si.getTopic())
                    .add(SCHEDULE_ITEM_LINK, si.getLink())
                    .add(SCHEDULE_TIME, si.getTime())
                    .add(SCHEDULE_ITEM_CRITERIA, si.getCriteria()).build();
                    
                scheduleHWSArrayBuilder.add(scheduleHWSJson);
             }
            

	}
        
        JsonArray scheduleDateArray = scheduleDateArrayBuilder.build();
        JsonArray scheduleHolidayArray = scheduleHolidayArrayBuilder.build();
        JsonArray scheduleLectureArray = scheduleLectureArrayBuilder.build();
        JsonArray scheduleReferencesArray = scheduleReferencesArrayBuilder.build();
        JsonArray scheduleRecitationsArray = scheduleRecitationsArrayBuilder.build();
        JsonArray scheduleHWSArray = scheduleHWSArrayBuilder.build();
        

          //          ____________________________________________________                  \\
         //            NOW BUILD THE TEAM AND STUDENTS JSON OBJCTS TO SAVE                   \\
        //            ----------------------------------------------------                    \\
	
        //teams
        ObservableList<Team> teamItem= dataManager.getTeamList();
        JsonArrayBuilder teamArrayBuilder = Json.createArrayBuilder();
        
        for ( Team t : teamItem) {	    
                    JsonObject teamJson = Json.createObjectBuilder()
                    .add(TEAM_NAME, t.getName())
                    .add(RED, t.getRedColor())
                    .add(GREEN, t.getGreenColor())
                    .add(BLUE, t.getBlueColor())
                    .add(TEXT_COLOR, t.getTextColorString())
                    .add(PROJECT_LINK, t.getLink()).build();
                    
                teamArrayBuilder.add(teamJson);
             }
        JsonArray teamArray = teamArrayBuilder.build();
        
        
        //students
        ObservableList<Student> studentItem= dataManager.getStudentList();
        JsonArrayBuilder studentArrayBuilder = Json.createArrayBuilder();

        for ( Student s : studentItem) {	    
                    JsonObject studentJson = Json.createObjectBuilder()
                    .add(LAST_NAME, s.getLastName())
                    .add(FIRST_NAME, s.getFirstName())
                    .add(TEAM, s.getTeam())
                    .add(ROLE, s.getRole()).build();
                    
                studentArrayBuilder.add(studentJson);
             }
        JsonArray studentArray = studentArrayBuilder.build();


                
          //          _____________________________________________           \\
         //            NOW BUILD THE PROJECTS JSON OBJCTS TO SAVE              \\
        //            ---------------------------------------------             \\
	
       JsonArrayBuilder projectsArrayBuilder = Json.createArrayBuilder();
	for (Team t : teamItem) 
        {
            JsonObject nameJson = Json.createObjectBuilder() 
                    .add(PROJECT_NAME, t.getName()).build();
            
            JsonArrayBuilder projectStudentsArrayBuilder = Json.createArrayBuilder();
            
            for (Student s : studentItem) 
            {	
                if(s.getTeam().equalsIgnoreCase(t.getName()))
                {
                    projectStudentsArrayBuilder.add(s.getFirstName() + " " + s.getLastName());
                }
            }
            
            JsonArray projectStudentsArray = projectStudentsArrayBuilder.build();
            JsonObject projectStudentsJson = Json.createObjectBuilder().add(PROJECT_STUDENTS, projectStudentsArray).build();
            
            JsonObject linkJson = Json.createObjectBuilder() .add(PROJECT_LINK, t.getLink()).build();
            
            projectsArrayBuilder.add(nameJson);
            projectsArrayBuilder.add(projectStudentsJson);
            projectsArrayBuilder.add(linkJson);
            
	}
        

        //add it to the projects array so that it is indented correctly
        JsonArrayBuilder workArrayBuilder = Json.createArrayBuilder();
        
        String semester= dataManager.getCourseData().getSemester();
        String year= dataManager.getCourseData().getYear();
        
        JsonObject projectWorkJson = Json.createObjectBuilder()
                .add(PROJECT_SEMESTER, semester + " " + year)
                .add(PROJECTS, projectsArrayBuilder)
                .build();
        

        
         //         ________________________________________
	//          THEN PUT IT ALL TOGETHER IN A JsonObject
       //           ------------------------------------------
       
               
	JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add(JSON_START_HOUR, "" + dataManager.getStartHour())
		.add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
                .add(RECITATIONS, recitationArray)
                .add(SCHEDULE_ITEM_STARTING_MONDAY_MONTH, ""+dataManager.getMondayMonth())
                .add(SCHEDULE_ITEM_STARTING_MONDAY_DAY,""+dataManager.getMondayDay())
		.add(SCHEDULE_ITEM_ENDING_FRIDAY_MONTH,""+dataManager.getFridayMonth())
		.add(SCHEDULE_ITEM_ENDING_FRIDAY_DAY,""+dataManager.getFridayDay())
                .add(SCHEDULE_HOLIDAY, scheduleHolidayArray)
                .add(SCHEDULE_LECTURES, scheduleLectureArray)
                .add(SCHEDULE_REFERENCES, scheduleReferencesArray)
                .add(SCHEDULE_RECITATIONS, scheduleRecitationsArray)
                .add(SCHEDULE_HWS, scheduleHWSArray)
                .add(TEAMS, teamArray)
                .add(STUDENTS, studentArray)
                .add(WORK, projectWorkJson).build();
        
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
        
//        System.out.println(Integer.toString(dataManager.getMondayMonth() )  );
//        System.out.println(Integer.toString(dataManager.getMondayDay()   )  );
//        System.out.println(Integer.toString(dataManager.getFridayMonth() )  );
//        System.out.println(Integer.toString(dataManager.getFridayDay()   )  );

     
    }
    
       
    // IMPORTING/EXPORTING DATA IS USED WHEN WE READ/WRITE DATA IN AN
    // ADDITIONAL FORMAT USEFUL FOR ANOTHER PURPOSE, LIKE ANOTHER APPLICATION
    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
    	CSGData dataManager = (CSGData)data;

        Boolean export=true;
        //Home
        if(export){
            
             //          ____________________________________________________                  \\
            //            NOW BUILD THE TEAM AND STUDENTS JSON OBJCTS TO SAVE                   \\
           //            ----------------------------------------------------                    \\
	
            //teams
            ObservableList<Team> teamItem= dataManager.getTeamList();
            JsonArrayBuilder teamArrayBuilder = Json.createArrayBuilder();

            for ( Team t : teamItem) {	    
                        JsonObject teamJson = Json.createObjectBuilder()
                        .add(TEAM_NAME, t.getName())
                        .add(RED, t.getRedColor())
                        .add(GREEN, t.getGreenColor())
                        .add(BLUE, t.getBlueColor())
                        .add(TEXT_COLOR, t.getTextColorString())
                        .add(PROJECT_LINK, t.getLink()).build();

                    teamArrayBuilder.add(teamJson);
                 }
            JsonArray teamArray = teamArrayBuilder.build();


            //students
            ObservableList<Student> studentItem= dataManager.getStudentList();
            JsonArrayBuilder studentArrayBuilder = Json.createArrayBuilder();

            for ( Student s : studentItem) {	    
                        JsonObject studentJson = Json.createObjectBuilder()
                        .add(LAST_NAME, s.getLastName())
                        .add(FIRST_NAME, s.getFirstName())
                        .add(TEAM, s.getTeam())
                        .add(ROLE, s.getRole()).build();

                    studentArrayBuilder.add(studentJson);
                 }
            JsonArray studentArray = studentArrayBuilder.build();
            
            JsonObject dataManagerJSO = Json.createObjectBuilder()
    
                    .add(TEAMS, teamArray)
                    .add(STUDENTS, studentArray).build();

            // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
            Map<String, Object> properties = new HashMap<>(1);
            properties.put(JsonGenerator.PRETTY_PRINTING, true);
            JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
            StringWriter sw = new StringWriter();
            JsonWriter jsonWriter = writerFactory.createWriter(sw);
            jsonWriter.writeObject(dataManagerJSO);
            jsonWriter.close();

            // INIT THE WRITER
            OutputStream os = new FileOutputStream("home.json");
            JsonWriter jsonFileWriter = Json.createWriter(os);
            jsonFileWriter.writeObject(dataManagerJSO);
            String prettyPrinted = sw.toString();
            PrintWriter pw = new PrintWriter("home.json");
            pw.write(prettyPrinted);
            pw.close();
            
        }
        
        //Syllabus
        if(export){
            
            //          ____________________________________________         \\
           //           NOW BUILD THE SYLLABUS JSON OBJCTS TO SAVE            \\
          //            --------------------------------------------           \\
        
            
        	// NOW BUILD THE TA JSON OBJCTS TO SAVE
	JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
	ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
	for (TeachingAssistant ta : tas) {	
            if(ta.isIsUndergrad()){
                JsonObject taJson = Json.createObjectBuilder()
                .add(JSON_NAME, ta.getName())
                .add(JSON_EMAIL, ta.getEmail())
                .add(JSON_UNDERGRAD, "true").build();
                taArrayBuilder.add(taJson);
            }
            else{
                JsonObject taJson = Json.createObjectBuilder()
                .add(JSON_NAME, ta.getName())
                .add(JSON_EMAIL, ta.getEmail())
                .add(JSON_UNDERGRAD, "false").build();
                taArrayBuilder.add(taJson);
            }

	}
	JsonArray undergradTAsArray = taArrayBuilder.build();

	// NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
	JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
        
        //Original
        
        //ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager)
        //test
        ArrayList<TimeSlot> officeHours;
                try
            {
                officeHours = TimeSlot.buildOfficeHoursList(dataManager);
            }
            catch(NullPointerException e)
            {
                officeHours =  TimeSlot.test();
           }
       //end test     
                
	for (TimeSlot ts : officeHours) {	    
	    JsonObject tsJson = Json.createObjectBuilder()
		    .add(JSON_DAY, ts.getDay())
		    .add(JSON_TIME, ts.getTime())
		    .add(JSON_NAME, ts.getName()).build();
	    timeSlotArrayBuilder.add(tsJson);
	}
	JsonArray timeSlotsArray = timeSlotArrayBuilder.build();
        
        
          //          ____________________________________________         \\
         //           NOW BUILD THE RECITATION JSON OBJCTS TO SAVE          \\
        //            --------------------------------------------           \\
        
        ObservableList<Recitation> recitations= dataManager.getRecitationList();
        
	JsonArrayBuilder recitationArrayBuilder = Json.createArrayBuilder();

        for ( Recitation rc : recitations) {	    
	    JsonObject recitationJson = Json.createObjectBuilder()
		    .add(RECITATIONS_SECTION,""+ rc.getSection() + " ("+rc.getInstructor()+")")
                    .add(RECITATIONS_DAYTIME, rc.getDayTime())
                    .add(RECITATIONS_LOCATION, rc.getLocation())
                    .add(RECITATIONS_TA_ONE, rc.getSupervisingTAOne())
                    .add(RECITATIONS_TA_TWO, rc.getSupervisingTATwo()).build();
	    recitationArrayBuilder.add(recitationJson);
	}
        JsonArray recitationArray = recitationArrayBuilder.build();
        
        
        
        //now build it
        JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add(JSON_START_HOUR, "" + dataManager.getStartHour())
		.add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
                .add(RECITATIONS, recitationArray).build();
        
            // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
            Map<String, Object> properties = new HashMap<>(1);
            properties.put(JsonGenerator.PRETTY_PRINTING, true);
            JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
            StringWriter sw = new StringWriter();
            JsonWriter jsonWriter = writerFactory.createWriter(sw);
            jsonWriter.writeObject(dataManagerJSO);
            jsonWriter.close();

            // INIT THE WRITER
            OutputStream os = new FileOutputStream("SyllabusData.json");
            JsonWriter jsonFileWriter = Json.createWriter(os);
            jsonFileWriter.writeObject(dataManagerJSO);
            String prettyPrinted = sw.toString();
            PrintWriter pw = new PrintWriter("SyllabusData.json");
            pw.write(prettyPrinted);
            pw.close();
        }
        
        
        
        
        
        //Schedule
        if(export){
            
            
          //          ____________________________________________         \\
         //            NOW BUILD THE SCHEDULE JSON OBJCTS TO SAVE           \\
        //            --------------------------------------------           \\
	
            ObservableList<ScheduleItem> scheduleItem= dataManager.getScheduleItemsList();

            JsonArrayBuilder scheduleDateArrayBuilder = Json.createArrayBuilder();
            JsonArrayBuilder scheduleHolidayArrayBuilder = Json.createArrayBuilder();
            JsonArrayBuilder scheduleLectureArrayBuilder = Json.createArrayBuilder();
            JsonArrayBuilder scheduleReferencesArrayBuilder = Json.createArrayBuilder();
            JsonArrayBuilder scheduleRecitationsArrayBuilder = Json.createArrayBuilder();
            JsonArrayBuilder scheduleHWSArrayBuilder = Json.createArrayBuilder();


            for ( ScheduleItem si : scheduleItem) {	    


                if( si.getType().compareToIgnoreCase("Holiday")==0){
                        JsonObject scheduleHolidayJson = Json.createObjectBuilder()
                        .add(SCHEDULE_ITEM_MONTH, si.getDateMonth())
                        .add(SCHEDULE_ITEM_DAY, si.getDateDay())
                        .add(SCHEDULE_ITEM_TITLE, si.getTitle())
                        .add(SCHEDULE_ITEM_LINK, si.getLink()).build();

                    scheduleHolidayArrayBuilder.add(scheduleHolidayJson);
                 }

                if( si.getType().compareToIgnoreCase("lecture")==0){
                        JsonObject scheduleLectureJson = Json.createObjectBuilder()
                        .add(SCHEDULE_ITEM_MONTH, si.getDateMonth())
                        .add(SCHEDULE_ITEM_DAY, si.getDateDay())
                        .add(SCHEDULE_ITEM_TITLE, si.getTitle())
//                        .add(SCHEDULE_ITEM_TOPIC, si.getTopic())
                        .add(SCHEDULE_ITEM_LINK, si.getLink()).build();

                    scheduleLectureArrayBuilder.add(scheduleLectureJson);
                 }

                if( si.getType().compareToIgnoreCase("reference")==0){
                        JsonObject scheduleReferencesJson = Json.createObjectBuilder()
                        .add(SCHEDULE_ITEM_MONTH, si.getDateMonth())
                        .add(SCHEDULE_ITEM_DAY, si.getDateDay())
                        .add(SCHEDULE_ITEM_TITLE, si.getTitle())
                        .add(SCHEDULE_ITEM_LINK, si.getLink()).build();

                    scheduleReferencesArrayBuilder.add(scheduleReferencesJson);
                 }

                if( si.getType().compareToIgnoreCase("recitation")==0){
                        JsonObject scheduleRecitationsJson = Json.createObjectBuilder()
                        .add(SCHEDULE_ITEM_MONTH, si.getDateMonth())
                        .add(SCHEDULE_ITEM_DAY, si.getDateDay())
                        .add(SCHEDULE_ITEM_TITLE, si.getTitle())
                        .add(SCHEDULE_ITEM_TOPIC, si.getTopic()).build();

                    scheduleRecitationsArrayBuilder.add(scheduleRecitationsJson);
                 }

            }
        
            JsonArray scheduleDateArray = scheduleDateArrayBuilder.build();
            JsonArray scheduleHolidayArray = scheduleHolidayArrayBuilder.build();
            JsonArray scheduleLectureArray = scheduleLectureArrayBuilder.build();
            JsonArray scheduleReferencesArray = scheduleReferencesArrayBuilder.build();
            JsonArray scheduleRecitationsArray = scheduleRecitationsArrayBuilder.build();
        
         //         ________________________________________
	//          THEN PUT IT ALL TOGETHER IN A JsonObject
       //           ------------------------------------------
       
               
                JsonObject dataManagerJSO = Json.createObjectBuilder()
	
                .add(SCHEDULE_ITEM_STARTING_MONDAY_MONTH,String.valueOf(dataManager.getMondayMonth()))
                .add(SCHEDULE_ITEM_STARTING_MONDAY_DAY,String.valueOf(dataManager.getMondayDay()))
		.add(SCHEDULE_ITEM_ENDING_FRIDAY_MONTH,String.valueOf(dataManager.getFridayMonth()))
		.add(SCHEDULE_ITEM_ENDING_FRIDAY_DAY,String.valueOf(dataManager.getFridayDay()))
                .add(SCHEDULE_HOLIDAY, scheduleHolidayArray)
                .add(SCHEDULE_LECTURES, scheduleLectureArray)
                .add(SCHEDULE_REFERENCES, scheduleReferencesArray)
                .add(SCHEDULE_RECITATIONS, scheduleRecitationsArray).build();
        
                // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
                Map<String, Object> properties = new HashMap<>(1);
                properties.put(JsonGenerator.PRETTY_PRINTING, true);
                JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
                StringWriter sw = new StringWriter();
                JsonWriter jsonWriter = writerFactory.createWriter(sw);
                jsonWriter.writeObject(dataManagerJSO);
                jsonWriter.close();

                // INIT THE WRITER
                OutputStream os = new FileOutputStream("ScheduleData.json");
                JsonWriter jsonFileWriter = Json.createWriter(os);
                jsonFileWriter.writeObject(dataManagerJSO);
                String prettyPrinted = sw.toString();
                PrintWriter pw = new PrintWriter("ScheduleData.json");
                pw.write(prettyPrinted);
                pw.close();
            
            

        }
        
        
        //HW
         if(export){
             
          //          ____________________________________________         \\
         //            NOW BUILD THE HW JSON OBJCTS TO SAVE                 \\
        //            --------------------------------------------           \\
	
            ObservableList<ScheduleItem> scheduleItem= dataManager.getScheduleItemsList();
            JsonArrayBuilder scheduleHWSArrayBuilder = Json.createArrayBuilder();

            for ( ScheduleItem si : scheduleItem) {	    
                if( si.getType().compareToIgnoreCase("hw")==0){
                        JsonObject scheduleHWSJson = Json.createObjectBuilder()
                        .add(SCHEDULE_ITEM_MONTH, si.getDateMonth())
                        .add(SCHEDULE_ITEM_DAY, si.getDateDay())
                        .add(SCHEDULE_ITEM_TITLE, si.getTitle())
                        .add(SCHEDULE_ITEM_TOPIC, si.getTopic())
                        .add(SCHEDULE_ITEM_LINK, si.getLink())
                        .add(SCHEDULE_TIME, si.getTime())
                        .add(SCHEDULE_ITEM_CRITERIA, si.getCriteria()).build();
                    scheduleHWSArrayBuilder.add(scheduleHWSJson);
                 }
            }
            JsonArray scheduleHWSArray = scheduleHWSArrayBuilder.build();

             //         ________________________________________
            //          THEN PUT IT ALL TOGETHER IN A JsonObject
           //           ------------------------------------------
       
               
            JsonObject dataManagerJSO = Json.createObjectBuilder()
                    .add(SCHEDULE_HW, scheduleHWSArray).build();

            // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
            Map<String, Object> properties = new HashMap<>(1);
            properties.put(JsonGenerator.PRETTY_PRINTING, true);
            JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
            StringWriter sw = new StringWriter();
            JsonWriter jsonWriter = writerFactory.createWriter(sw);
            jsonWriter.writeObject(dataManagerJSO);
            jsonWriter.close();

            // INIT THE WRITER
            OutputStream os = new FileOutputStream("HW.json");
            JsonWriter jsonFileWriter = Json.createWriter(os);
            jsonFileWriter.writeObject(dataManagerJSO);
            String prettyPrinted = sw.toString();
            PrintWriter pw = new PrintWriter("HW.json");
            pw.write(prettyPrinted);
            pw.close();

        }
        
         
        //Project
        if(export){
            
                //          _____________________________________________           \\
               //            NOW BUILD THE PROJECTS JSON OBJCTS TO SAVE              \\
              //            ---------------------------------------------             \\

            ObservableList<Team> teamItem= dataManager.getTeamList();
            ObservableList<Student> studentItem= dataManager.getStudentList();
            
            JsonArrayBuilder projectsArrayBuilder = Json.createArrayBuilder();
            for (Team t : teamItem){
               JsonObject nameJson = Json.createObjectBuilder() 
                       .add(PROJECT_NAME, t.getName()).build();

               JsonArrayBuilder projectStudentsArrayBuilder = Json.createArrayBuilder();

               for (Student s : studentItem) 
               {	
                   if(s.getTeam().equalsIgnoreCase(t.getName()))
                   {
                       projectStudentsArrayBuilder.add(s.getFirstName() + " " + s.getLastName());
                   }
               }

               JsonArray projectStudentsArray = projectStudentsArrayBuilder.build();
               JsonObject projectStudentsJson = Json.createObjectBuilder().add(PROJECT_STUDENTS, projectStudentsArray).build();

               JsonObject linkJson = Json.createObjectBuilder() .add(PROJECT_LINK, t.getLink()).build();

               projectsArrayBuilder.add(nameJson);
               projectsArrayBuilder.add(projectStudentsJson);
               projectsArrayBuilder.add(linkJson);

           }

           //add it to the projects array so that it is indented correctly
           JsonArrayBuilder workArrayBuilder = Json.createArrayBuilder();

           String semester= dataManager.getCourseData().getSemester();
           String year= dataManager.getCourseData().getYear();

           JsonObject projectWorkJson = Json.createObjectBuilder()
                   .add(PROJECT_SEMESTER, semester + " " + year)
                   .add(PROJECTS, projectsArrayBuilder)
                   .build();
           
           
           JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(WORK, projectWorkJson).build();
        
            // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
            Map<String, Object> properties = new HashMap<>(1);
            properties.put(JsonGenerator.PRETTY_PRINTING, true);
            JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
            StringWriter sw = new StringWriter();
            JsonWriter jsonWriter = writerFactory.createWriter(sw);
            jsonWriter.writeObject(dataManagerJSO);
            jsonWriter.close();

            // INIT THE WRITER
            OutputStream os = new FileOutputStream("Projects.json");
            JsonWriter jsonFileWriter = Json.createWriter(os);
            jsonFileWriter.writeObject(dataManagerJSO);
            String prettyPrinted = sw.toString();
            PrintWriter pw = new PrintWriter("Projects.json");
            pw.write(prettyPrinted);
            pw.close();

        }
    
    
    
    }
}