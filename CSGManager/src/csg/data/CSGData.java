package csg.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import djf.components.AppDataComponent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.Pane;
import properties_manager.PropertiesManager;
import csg.CSGManagerApp;
import csg.CSGManagerProp;
import csg.file.TimeSlot;
import csg.workspace.CSGWorkspace;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

/**
 * This is the data component for CSGManagerApp. It has all the data needed
 * to be set by the user via the User Interface and file I/O can set and get
 * all the data from this object
 * 
 * @author Richard McKenna
 */
public class CSGData implements AppDataComponent {

    // WE'LL NEED ACCESS TO THE APP TO NOTIFY THE GUI WHEN DATA CHANGES
    CSGManagerApp app;
    
    // NOTE THAT THIS DATA STRUCTURE WILL DIRECTLY STORE THE
    // DATA IN THE ROWS OF THE TABLE VIEW
    ObservableList<SitePage> sitePages;
    

   // NOTE THAT THIS DATA STRUCTURE WILL DIRECTLY STORE THE
   // DATA IN THE ROWS OF THE TABLE VIEW
    public Course courseData;
   
    // NOTE THAT THIS DATA STRUCTURE WILL DIRECTLY STORE THE
    // DATA IN THE ROWS OF THE TABLE VIEW
    ObservableList<Recitation> recitations;
    
    // NOTE THAT THIS DATA STRUCTURE WILL DIRECTLY STORE THE
    // DATA IN THE ROWS OF THE TABLE VIEW
    ObservableList<Team> teams;
    
    // NOTE THAT THIS DATA STRUCTURE WILL DIRECTLY STORE THE
    // DATA IN THE ROWS OF THE TABLE VIEW
    ObservableList<ScheduleItem> scheduleItems;

    // NOTE THAT THIS DATA STRUCTURE WILL DIRECTLY STORE THE
    // DATA IN THE ROWS OF THE TABLE VIEW
    ObservableList<TeachingAssistant> teachingAssistants;
    
    // NOTE THAT THIS DATA STRUCTURE WILL DIRECTLY STORE THE
    // DATA IN THE ROWS OF THE TABLE VIEW
    ObservableList<Student> students;

    // THIS WILL STORE ALL THE OFFICE HOURS GRID DATA, WHICH YOU
    // SHOULD NOTE ARE StringProperty OBJECTS THAT ARE CONNECTED
    // TO UI LABELS, WHICH MEANS IF WE CHANGE VALUES IN THESE
    // PROPERTIES IT CHANGES WHAT APPEARS IN THOSE LABELS
    HashMap<String, StringProperty> officeHours;
    
    // THESE ARE THE LANGUAGE-DEPENDENT VALUES FOR
    // THE OFFICE HOURS GRID HEADERS. NOTE THAT WE
    // LOAD THESE ONCE AND THEN HANG ON TO THEM TO
    // INITIALIZE OUR OFFICE HOURS GRID
    ArrayList<String> gridHeaders;
    
    // THESE ARE THE TIME BOUNDS FOR THE OFFICE HOURS GRID. NOTE
    // THAT THESE VALUES CAN BE DIFFERENT FOR DIFFERENT FILES, BUT
    // THAT OUR APPLICATION USES THE DEFAULT TIME VALUES AND PROVIDES
    // NO MEANS FOR CHANGING THESE VALUES
    int startHour;
    int endHour;
    
    // DEFAULT VALUES FOR START AND END HOURS IN MILITARY HOURS
    public static final int MIN_START_HOUR = 0;
    public static final int MAX_END_HOUR = 23;
    
    
    //VALUES THAT HOLD SCHEDULE START AND END DATES
    LocalDate monday;
    LocalDate friday;



    /**
     * This constructor will setup the required data structures for
     * use, but will have to wait on the office hours grid, since
     * it receives the StringProperty objects from the Workspace.
     * 
     * @param initApp The application this data manager belongs to. 
     */
    public CSGData(CSGManagerApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
        
        //Default Course
        
        courseData= new Course();
        
        sitePages=FXCollections.observableArrayList();
        recitations=FXCollections.observableArrayList();
        teams=FXCollections.observableArrayList();
        scheduleItems=FXCollections.observableArrayList();
        teachingAssistants=FXCollections.observableArrayList();
        students=FXCollections.observableArrayList();
        
        // CONSTRUCT THE LIST OF TAs FOR THE TABLE
        teachingAssistants = FXCollections.observableArrayList();

        // THESE ARE THE DEFAULT OFFICE HOURS
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;
        
        //THIS WILL STORE OUR OFFICE HOURS
        officeHours = new HashMap();
        
        // THESE ARE THE LANGUAGE-DEPENDENT OFFICE HOURS GRID HEADERS
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ArrayList<String> timeHeaders = props.getPropertyOptionsList(CSGManagerProp.OFFICE_HOURS_TABLE_HEADERS);
        ArrayList<String> dowHeaders = props.getPropertyOptionsList(CSGManagerProp.DAYS_OF_WEEK);
        gridHeaders = new ArrayList();
        gridHeaders.addAll(timeHeaders);
        gridHeaders.addAll(dowHeaders);
        
        //add site pages to course data
        sitePages.add(new SitePage(true,"Home", "index.html", "HomeBuilder.js") );
        sitePages.add(new SitePage(true,"Syllabus", "syllabus.html", "SyllabusBuilder.js") );
        sitePages.add(new SitePage(true,"Schedule", "schedule.html", "ScheduleBuilder.js") );
        sitePages.add(new SitePage(true,"HWs", "hws.html", "HWs.js") );
        sitePages.add(new SitePage(true,"Projects", "projects.html", "ProjectBuilder.js") );

        
    }
    
    /**
     * Called each time new work is created or loaded, it resets all data
     * and data structures such that they can be used for new values.
     */
    @Override
    public void resetData() {
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;
        teachingAssistants.clear();
        officeHours.clear();
        
        CSGWorkspace workspaceComponent = (CSGWorkspace)app.getWorkspaceComponent();
        
        workspaceComponent.getOfficeHour(true).getSelectionModel().select(null);
        workspaceComponent.getOfficeHour(true).getSelectionModel().select(startHour);
        workspaceComponent.getOfficeHour(false).getSelectionModel().select(null);
        workspaceComponent.getOfficeHour(false).getSelectionModel().select(endHour);
    }
    
  // ACCESSOR METHODS

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }
    
    public ArrayList<String> getGridHeaders() {
        return gridHeaders;
    }

    public ObservableList getTeachingAssistants() {
        return teachingAssistants;
    }

    public ObservableList getSitePages() {
        return sitePages;
    }

    public ObservableList getRecitations() {
        return recitations;
    }

    public ObservableList getScheduleItems() {
        return scheduleItems;
    }

    public ObservableList getTeams() {
        return teams;
    }

    public ObservableList getStudents() {
        return students;
    }
    
    
    public String getCellKey(int col, int row) {
        return col + "_" + row;
    }

    public StringProperty getCellTextProperty(int col, int row) {
        String cellKey = getCellKey(col, row);
        return officeHours.get(cellKey);
    }

    public HashMap<String, StringProperty> getOfficeHours() {
        return officeHours;
    }
    
    public int getNumRows() {
        return ((endHour - startHour) * 2) + 1;
    }

    public String getTimeString(int militaryHour, boolean onHour) {
        String minutesText = "00";
        if (!onHour) {
            minutesText = "30";
        }

        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        String cellText = "" + hour + ":" + minutesText;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }
    
    public String getCellKey(String day, String time) {
        int col = gridHeaders.indexOf(day);
        int row = 1;
        int hour = Integer.parseInt(time.substring(0, time.indexOf("_")));
        int milHour = hour;
        
        if(time.contains("pm"))
            milHour += 12;
        if(time.contains("12"))
            milHour -= 12;
        row += (milHour - startHour) * 2;
        if (time.contains("_30"))
            row += 1;
        return getCellKey(col, row);
    }
    
    public TeachingAssistant getTA(String testName) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getName().equals(testName)) {
                return ta;
            }
        }
        return null;
    }
    
    /**
     * This method is for giving this data manager the string property
     * for a given cell.
     */
    public void setCellProperty(int col, int row, StringProperty prop) {
        String cellKey = getCellKey(col, row);
        officeHours.put(cellKey, prop);
    }    
    
    /**
     * This method is for setting the string property for a given cell.
     */
    public void setGridProperty(ArrayList<ArrayList<StringProperty>> grid,int column, int row, StringProperty prop) {
        grid.get(row).set(column, prop);
    }
    

    
    private void initOfficeHours(int initStartHour, int initEndHour) {
        // NOTE THAT THESE VALUES MUST BE PRE-VERIFIED
        startHour = initStartHour;
        endHour = initEndHour;
        
        // EMPTY THE CURRENT OFFICE HOURS VALUES
        officeHours.clear();
            
        // WE'LL BUILD THE USER INTERFACE COMPONENT FOR THE
        // OFFICE HOURS GRID AND FEED THEM TO OUR DATA
        // STRUCTURE AS WE GO
        CSGWorkspace workspaceComponent = (CSGWorkspace)app.getWorkspaceComponent();
        workspaceComponent.reloadOfficeHoursGrid(this);
        
        workspaceComponent.getOfficeHour(true).getSelectionModel().select(startHour);
        workspaceComponent.getOfficeHour(false).getSelectionModel().select(endHour);
    }
 
    public void initHours(String startHourText, String endHourText) {
        int initStartHour = Integer.parseInt(startHourText);
        int initEndHour = Integer.parseInt(endHourText);
        if ((initStartHour >= MIN_START_HOUR)
                && (initEndHour <= MAX_END_HOUR)
                && (initStartHour <= initEndHour)) {
            // THESE ARE VALID HOURS SO KEEP THEM
            initOfficeHours(initStartHour, initEndHour);
        }
    }

    public boolean containsTA(String testName, String testEmail) {
        for (TeachingAssistant ta : teachingAssistants) {            
            if (ta.getName().equals(testName)) {
                return true;
            }
            if (ta.getEmail().equals(testEmail)) {
                return true;
            }
        }
        return false;
    }



    //ADDING TA
    public void addTA(String initName, String initEmail, boolean isUndergrad) {
        // MAKE THE TA
        TeachingAssistant ta = new TeachingAssistant(initName, initEmail, isUndergrad);

        // ADD THE TA
        if (!containsTA(initName, initEmail)) {
            teachingAssistants.add(ta);
        }

        // SORT THE TAS
        Collections.sort(teachingAssistants);
    }

    public void removeTA(String name) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (name.equals(ta.getName())) {
                teachingAssistants.remove(ta);
                return;
            }
        }
    }
    
    public void addOfficeHoursReservation(String day, String time, String taName) {
        String cellKey = getCellKey(day, time);
        toggleTAOfficeHours(cellKey, taName);
    }
    

    //TA OFFICE HOUR TABLE

    /**
     * This function toggles the taName in the cell represented
     * by cellKey. Toggle means if it's there it removes it, if
     * it's not there it adds it.
     */
    public void toggleTAOfficeHours(String cellKey, String taName) {
        StringProperty cellProp = officeHours.get(cellKey);
        String cellText = cellProp.getValue();

        // IF IT ALREADY HAS THE TA, REMOVE IT
        if (cellText.contains(taName)) {
            removeTAFromCell(cellProp, taName);
        } // OTHERWISE ADD IT
        else if (cellText.length() == 0) {
            cellProp.setValue(taName);
        } else {
            cellProp.setValue(cellText + "\n" + taName);
        }
    }
    
    /**
     * This method removes taName from the office grid cell
     * represented by cellProp.
     */
    public void removeTAFromCell(StringProperty cellProp, String taName) {
        // GET THE CELL TEXT
        String cellText = cellProp.getValue();
        // IS IT THE ONLY TA IN THE CELL?
        if (cellText.equals(taName)) {
            cellProp.setValue("");
        }
        // IS IT THE FIRST TA IN A CELL WITH MULTIPLE TA'S?
        else if (cellText.indexOf(taName) == 0) {
            int startIndex = cellText.indexOf("\n") + 1;
            cellText = cellText.substring(startIndex);
            cellProp.setValue(cellText);
        }
        // IS IT IN THE MIDDLE OF A LIST OF TAs
        else if (cellText.indexOf(taName) < cellText.indexOf("\n", cellText.indexOf(taName))) {
            int startIndex = cellText.indexOf("\n" + taName);
            int endIndex = startIndex + taName.length() + 1;
            cellText = cellText.substring(0, startIndex) + cellText.substring(endIndex);
            cellProp.setValue(cellText);
        }
        // IT MUST BE THE LAST TA
        else {
            int startIndex = cellText.indexOf("\n" + taName);
            cellText = cellText.substring(0, startIndex);
            cellProp.setValue(cellText);
        }
    }
    
    public void replaceTAName(String name, String newName){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        for(Pane p : workspace.getOfficeHoursGridTACellPanes().values()){
            String cellKey = p.getId();
            StringProperty cellProp = officeHours.get(cellKey);
            String cellText = cellProp.getValue();
            if (cellText.contains(name)) {
                toggleTAOfficeHours(cellKey, name);
                toggleTAOfficeHours(cellKey, newName);
            }
        }
    }
    
    public void changeTime(int startTime, int endTime, ArrayList<TimeSlot> officeHours){
        initHours("" + startTime, "" + endTime);
        for(TimeSlot ts : officeHours){
            String temp = ts.getTime();
            int tempint = Integer.parseInt(temp.substring(0, temp.indexOf('_')));
            if(temp.contains("pm"))
                tempint += 12;
            if(temp.contains("12"))
                tempint -= 12;
            if(tempint >= startTime && tempint <= endTime)
                addOfficeHoursReservation(ts.getDay(), ts.getTime(), ts.getName());
        }
    }
    

    //RECITATION
    public void addRecitation(String section, String instructor, String dayTime, String location, String supervisingTAOne, String supervisingTATwo){ 
        // MAKE THE RECITATION
        Recitation recitation = new Recitation(section, instructor, dayTime, location, supervisingTAOne,supervisingTATwo);

        // ADD THE TA
        recitations.add(recitation);

    }
    
    public void removeRecitation(String section, String instructor,String location ) {
        for (Recitation r : recitations) {
            if (location.equals(r.getLocation()) && instructor.equals(r.getInstructor()) ) {
                recitations.remove(r);
                return;
            }
        }
    }
    
    
    public void removeRecitationByIndex(int index) {
        Recitation rec=recitations.get(index);
        recitations.remove(rec);
    }
       
    
    public Recitation getRecitation(String getSection, String getInstructor) {
        for (Recitation recitation : recitations) {
            if (recitation.getSection().equals(getSection) && recitation .getInstructor().equals(getInstructor)) {
                return recitation;
            }
        }
        return null;
    }
    
    public ObservableList<Recitation> getRecitationList() {
        return recitations;
    }
    
    
    //COURSE DATA STUFF

    public void addCourseData(String subject, String number, String semester, String year, String title, String instructorName, String instructorHome, String exportDirectory, String bannerSchool, String leftFooter, String rightFooter){ 
        // MAKE THE RECITATION
        courseData = new Course(subject, number, semester, year, title,instructorName,instructorHome,exportDirectory,bannerSchool, leftFooter,rightFooter);

    }
    
    public Course getCourseData() {
        return courseData;
    }
    
    
    //SCHEDULE ITEM STUFF
    
    public void addScheduleItem(String type, String date, String time, String title, String topic, String link, String criteria, String startDate, String endDate){ 
        // MAKE THE RECITATION
       ScheduleItem scheduleItem = new ScheduleItem(type, date, time, title, topic,link,criteria, startDate, endDate);

        // ADD THE TA
        scheduleItems.add(scheduleItem);

    }
    
    public void removeScheduleItem(String type, String date, String title ){
          for (ScheduleItem si : scheduleItems) {
            if (type.equals(si.getType()) && date.equals(si.getDate()) && title.equals(si.getTitle()) ) {
                scheduleItems.remove(si);
                return;
            }
        }
    }
     
    public ObservableList<ScheduleItem> getScheduleItemsList() {
        return scheduleItems;
    }
    
    public void addSitePage(Boolean use, String navbarTitle, String fileName, String script){ 
        // MAKE THE RECITATION
        SitePage sitepage = new SitePage(use, navbarTitle, fileName, script);

        // ADD THE TA
        sitePages.add(sitepage);

    }
    
    public void setDate(LocalDate mon, LocalDate fri){
        monday= mon;
        friday= fri;
    }
    
    
    //TEAM STUFF
    public ObservableList<Team> getTeamList() {
        return teams;
    }

    public void addTeam(String name, String color, String textColor, String link){ 
        // MAKE THE RECITATION
        Team team = new Team(name, color, textColor, link);

        // ADD THE TA
        teams.add(team);

    }
    
    public void removeTeam(String name, String color, String textColor, String link){ 
        // MAKE THE RECITATION
        
        for (Team t : teams) {
            if (name.equals(t.getName()) && color.equals(t.getColor()) && textColor.equals(t.getTextColor()) && link.equals(t.getLink())  ) {
                teams.remove(t);
            }
        }
//        //NOW MUST REMOVE ALL STUDENTS IN THE TEAM THAT WAS JUST REMOVED
//        removeStudentsFromTeam(name);
//        return;
    }
     

    
    
    //Student Stuff
    
    public ObservableList<Student> getStudentList() {
        return students;
    }
  
    public void addStudent(String firstName, String lastName, String team, String role){ 
        // MAKE THE RECITATION
        Student student = new Student(firstName, lastName, team, role);

        // ADD THE TA
        students.add(student);
    }
    
    public void removeStudent(String firstName, String lastName, String team, String role){ 
        // MAKE THE RECITATION
        for (Student s : students) {
            if (firstName.equals(s.getFirstName()) && lastName.equals(s.getLastName()) && team.equals(s.getTeam()) && role.equals(s.getRole())  ) {
                students.remove(s);
                return;
            }
        }        
    }
    
    //WHEN DELETEING A TEAM, THIS FUNCTION WILL BE CALLED AND WILL REMOVE ALL THE STUDENTS IN THAT TEAM
    public void removeStudentsFromTeam(String team){ 
        
        //Prevent multiple iterators from running at the same time
        //prevents concurrecny issues
        Iterator<Student> studentsTemp = students.iterator();
        
        while(studentsTemp.hasNext()){
            if(team.equals(studentsTemp.next().getTeam())){
                
                studentsTemp.remove();
            }
        }
        

        
    }

    
    //DATE STUFF
    
    public int getMondayMonth() {
        return monday.getMonthValue();
    }
    
    public int getMondayDay() {
        return monday.getDayOfMonth();
    }

    public int getFridayMonth() {
        return friday.getMonthValue();
    }
    
    public int getFridayDay() {
        return friday.getDayOfMonth();
    }
    
//    
    
    public void setMonday(LocalDate monday) {
        this.monday = monday;
    }

    public void setFriday(LocalDate friday) {
        this.friday = friday;
    }

    
}