package csg.workspace;

import djf.controller.AppFileController;
import djf.ui.AppGUI;
import static csg.CSGManagerProp.*;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import properties_manager.PropertiesManager;
import csg.CSGManagerApp;
import csg.data.CSGData;
import csg.data.TeachingAssistant;
import csg.style.CSGStyle;
import static csg.style.CSGStyle.CLASS_HIGHLIGHTED_GRID_CELL;
import static csg.style.CSGStyle.CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN;
import static csg.style.CSGStyle.CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE;
import csg.workspace.CSGWorkspace;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyEvent;
import csg.jtps.jTPS;
import csg.jtps.jTPS_Transaction;
import csg.CSGManagerProp;
import csg.data.Recitation;
import csg.data.ScheduleItem;
import csg.data.Student;
import csg.data.Team;
import csg.file.CSGFiles;
import csg.file.TimeSlot;
import csg.jtps.AddRecitation;
import csg.jtps.AddSchedule;
import csg.jtps.AddStudent;
import csg.jtps.AddTeam;
import csg.jtps.DeleteRecitation;
import csg.jtps.DeleteSchedule;
import csg.jtps.DeleteStudent;
import csg.jtps.DeleteTeam;
import csg.jtps.EditRecitation;
import csg.jtps.EditSchedule;
import csg.jtps.EditStudent;
import csg.jtps.EditTeam;
import csg.jtps.TAAdderUR;
import csg.jtps.TAReplaceUR;
import csg.jtps.TAdeletUR;
import csg.jtps.TAhourschangeUR;
import csg.jtps.TAtoggleUR;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_DATA;
import static djf.settings.AppStartupConstants.PATH_EXPORT;
import static djf.settings.AppStartupConstants.PATH_TEMPLATES;
import static djf.settings.AppStartupConstants.PATH_WORK;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Calendar;
import javafx.scene.control.DatePicker;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/**
 * This class provides responses to all workspace interactions, meaning
 * interactions with the application controls not including the file
 * toolbar.
 * 
 * @author Richard McKenna
 * @version 1.0
 */
public class CSGController {
    static jTPS jTPS = new jTPS();
    // THE APP PROVIDES ACCESS TO OTHER COMPONENTS AS NEEDED
    CSGManagerApp app;

    /**
     * Constructor, note that the app must already be constructed.
     */
    public CSGController(CSGManagerApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
    }
    
    /**
     * This helper method should be called every time an edit happens.
     */    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
    
    /**
     * This method responds to when the user requests to add
     * a new TA via the UI. Note that it must first do some
     * validation to make sure a unique name and email address
     * has been provided.
     */
    public void handleAddTA() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TextField nameTextField = workspace.getNameTextField();
        TextField emailTextField = workspace.getEmailTextField();
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        CSGData data = (CSGData)app.getDataComponent();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (name.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));            
        }
        // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (email.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));                        
        }
        // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
        else if (data.containsTA(name, email)) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));                                    
        }
        else if (!Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(email).matches()){
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TA_EMAIL_INVALID_TITLE), props.getProperty(TA_EMAIL_INVALID_MESSAGE));
        }
        // EVERYTHING IS FINE, ADD A NEW TA
        else {
            
            // ADD THE NEW TA TO THE DATA
            jTPS_Transaction addTAUR = new TAAdderUR(app);
            jTPS.addTransaction(addTAUR);
            
            // CLEAR THE TEXT FIELDS
            nameTextField.setText("");
            emailTextField.setText("");
            
            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            nameTextField.requestFocus();
            
            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
    }

    /**
     * This function provides a response for when the user presses a
     * keyboard key. Note that we're only responding to Delete, to remove
     * a TA.
     * 
     * @param code The keyboard code pressed.
     */
    public void handleKeyPress(KeyCode code) {
        // DID THE USER PRESS THE DELETE KEY?
        if (code == KeyCode.DELETE) {
            // GET THE TABLE
            CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
            TableView taTable = workspace.getTATable();
            
            // IS A TA SELECTED IN THE TABLE?
            Object selectedItem = taTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // GET THE TA AND REMOVE IT
                TeachingAssistant ta = (TeachingAssistant)selectedItem;
                String taName = ta.getName();
                CSGData data = (CSGData)app.getDataComponent();
                
                jTPS_Transaction deletUR = new TAdeletUR(app, taName);
                jTPS.addTransaction(deletUR);
                
                // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
                // WE'VE CHANGED STUFF
                markWorkAsEdited();
            }
        }
    }

    /**
     * This function provides a response for when the user clicks
     * on the office hours grid to add or remove a TA to a time slot.
     * 
     * @param pane The pane that was toggled.
     */
    public void handleCellToggle(Pane pane) {
        // GET THE TABLE
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView taTable = workspace.getTATable();
        
        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // GET THE TA
            TeachingAssistant ta = (TeachingAssistant)selectedItem;
            String taName = ta.getName();
            CSGData data = (CSGData)app.getDataComponent();
            String cellKey = pane.getId();
            
            // AND TOGGLE THE OFFICE HOURS IN THE CLICKED CELL
            jTPS_Transaction toggleUR = new TAtoggleUR(taName, cellKey, data);
            jTPS.addTransaction(toggleUR);
            
            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
    }
    
    void handleGridCellMouseExited(Pane pane) {
        String cellKey = pane.getId();
        CSGData data = (CSGData)app.getDataComponent();
        int column = Integer.parseInt(cellKey.substring(0, cellKey.indexOf("_")));
        int row = Integer.parseInt(cellKey.substring(cellKey.indexOf("_") + 1));
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();

        Pane mousedOverPane = workspace.getTACellPane(data.getCellKey(column, row));
        mousedOverPane.getStyleClass().clear();
        mousedOverPane.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);

        // THE MOUSED OVER COLUMN HEADER
        Pane headerPane = workspace.getOfficeHoursGridDayHeaderPanes().get(data.getCellKey(column, 0));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // THE MOUSED OVER ROW HEADERS
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(0, row));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(1, row));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        
        // AND NOW UPDATE ALL THE CELLS IN THE SAME ROW TO THE LEFT
        for (int i = 2; i < column; i++) {
            cellKey = data.getCellKey(i, row);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
            cell.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        }

        // AND THE CELLS IN THE SAME COLUMN ABOVE
        for (int i = 1; i < row; i++) {
            cellKey = data.getCellKey(column, i);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
            cell.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        }
    }

    void handleGridCellMouseEntered(Pane pane) {
        String cellKey = pane.getId();
        CSGData data = (CSGData)app.getDataComponent();
        int column = Integer.parseInt(cellKey.substring(0, cellKey.indexOf("_")));
        int row = Integer.parseInt(cellKey.substring(cellKey.indexOf("_") + 1));
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        
        // THE MOUSED OVER PANE
        Pane mousedOverPane = workspace.getTACellPane(data.getCellKey(column, row));
        mousedOverPane.getStyleClass().clear();
        mousedOverPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_CELL);
        
        // THE MOUSED OVER COLUMN HEADER
        Pane headerPane = workspace.getOfficeHoursGridDayHeaderPanes().get(data.getCellKey(column, 0));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        
        // THE MOUSED OVER ROW HEADERS
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(0, row));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(1, row));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        
        // AND NOW UPDATE ALL THE CELLS IN THE SAME ROW TO THE LEFT
        for (int i = 2; i < column; i++) {
            cellKey = data.getCellKey(i, row);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        }

        // AND THE CELLS IN THE SAME COLUMN ABOVE
        for (int i = 1; i < row; i++) {
            cellKey = data.getCellKey(column, i);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        }
    }
    
    public void Undo(){
        jTPS.undoTransaction();
        markWorkAsEdited();
    }
    public void Redo(){
        jTPS.doTransaction();
        markWorkAsEdited();
    }
    
    public void changeTime(){
        CSGData data = (CSGData)app.getDataComponent();
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ComboBox comboBox1 = workspace.getOfficeHour(true);
        ComboBox comboBox2 = workspace.getOfficeHour(false);
        int startTime = data.getStartHour();
        int endTime = data.getEndHour();
        int newStartTime = comboBox1.getSelectionModel().getSelectedIndex();
        int newEndTime = comboBox2.getSelectionModel().getSelectedIndex();
        if(newStartTime > endTime || newEndTime < startTime){
            comboBox1.getSelectionModel().select(startTime);
            comboBox2.getSelectionModel().select(endTime);
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGManagerProp.START_OVER_END_TITLE.toString()), props.getProperty(CSGManagerProp.START_OVER_END_MESSAGE.toString()));
            return;
        }
        ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(data);
        if(officeHours.isEmpty()){
            workspace.getOfficeHoursGridPane().getChildren().clear();
            data.initHours("" + newStartTime, "" + newEndTime);
        }
        String firsttime = officeHours.get(0).getTime();
        int firsthour = Integer.parseInt(firsttime.substring(0, firsttime.indexOf('_')));
        if(firsttime.contains("pm"))
            firsthour += 12;
        if(firsttime.contains("12"))
            firsthour -= 12;
        String lasttime = officeHours.get(officeHours.size() - 1).getTime();
        int lasthour = Integer.parseInt(lasttime.substring(0, lasttime.indexOf('_')));
        if(lasttime.contains("pm"))
            lasthour += 12;
        if(lasttime.contains("12"))
            lasthour -= 12;
        if(firsthour < newStartTime || lasthour + 1 > newEndTime){
            AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
            yesNoDialog.show(props.getProperty(CSGManagerProp.OFFICE_HOURS_REMOVED_TITLE.toString()), props.getProperty(CSGManagerProp.OFFICE_HOURS_REMOVED_MESSAGE).toString());
            String selection = yesNoDialog.getSelection();
            if (!selection.equals(AppYesNoCancelDialogSingleton.YES)){
                comboBox1.getSelectionModel().select(startTime);
                comboBox2.getSelectionModel().select(endTime);
                return;
            }
        }
        
        jTPS_Transaction changeTimeUR = new TAhourschangeUR(app);
        jTPS.addTransaction(changeTimeUR);
        
        markWorkAsEdited();
    }
    
    public void changeExistTA(){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView taTable = workspace.getTATable();
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        CSGData data = (CSGData)app.getDataComponent();
        TeachingAssistant ta = (TeachingAssistant)selectedItem;
        String name = ta.getName();
        String newName = workspace.getNameTextField().getText();
        String newEmail = workspace.getEmailTextField().getText();
        jTPS_Transaction replaceTAUR = new TAReplaceUR(app);
        jTPS.addTransaction(replaceTAUR);
        markWorkAsEdited();
    }
    
    public void loadTAtotext(){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView taTable = workspace.getTATable();
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            TeachingAssistant ta = (TeachingAssistant)selectedItem;
            String name = ta.getName();
            String email = ta.getEmail();
            workspace.getNameTextField().setText(name);
            workspace.getEmailTextField().setText(email);
        }
    }
    
    // COURSE DETAILS
    
    public void handleAddCourse(){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        CSGData data = (CSGData)app.getDataComponent();
        
        if(!(workspace.getSubjectComboBox().getSelectionModel().isEmpty()) ){
            String subject = workspace.getSubjectComboBox().getSelectionModel().getSelectedItem().toString();
            data.courseData.setSubject(subject);
        }
        
        if(!(workspace.getNumberComboBox().getSelectionModel().isEmpty())){
            String number = workspace.getNumberComboBox().getSelectionModel().getSelectedItem().toString();
            data.courseData.setNumber(number);

        }
        
        if(!(workspace.getSemesterComboBox().getSelectionModel().isEmpty())){
            String semester = workspace.getSemesterComboBox().getSelectionModel().getSelectedItem().toString();
            data.courseData.setSemester(semester);
        }
        
        if(!(workspace.getYearComboBox().getSelectionModel().isEmpty())){
            String year = workspace.getYearComboBox().getSelectionModel().getSelectedItem().toString();
            data.courseData.setYear(year);
        }
        
        if(!(workspace.getTileTextField().getText().isEmpty())){
            String title = workspace.getTileTextField().getText();            
            data.courseData.setTitle(title);
        }
        
        if(!(workspace.getInstructorNameTextField().getText().isEmpty())){
            String name = workspace.getInstructorNameTextField().getText();
            data.courseData.setInstructorName(name);
        }

        if(!(workspace.getInstructorHomeTextField().getText().isEmpty())){
            String home = workspace.getInstructorHomeTextField().getText();
            data.courseData.setInstructorHome(home);
        }

        System.out.println(data.courseData.getSubject()+"  "+ data.courseData.getNumber()+"  "+ 
                data.courseData.getSemester()+"  "+ data.courseData.getYear()+"  "+ data.courseData.getTitle()+"  "
        + data.courseData.getInstructorName()+"  "+ data.courseData.getInstructorHome());

    }
   

    // RECITATIONS
    public void handleAddRecitation(){

        jTPS_Transaction addRec = new AddRecitation(app);
        jTPS.addTransaction(addRec);
        markWorkAsEdited();   
    }
    
    public void handleEditRecitation(){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView recitationDataTable = workspace.getRecitationDataTable();
        
        CSGData data = (CSGData)app.getDataComponent();

        jTPS_Transaction editRecitation = new EditRecitation(app);
        jTPS.addTransaction(editRecitation);
        markWorkAsEdited();
        recitationDataTable.refresh();
    }
    
    public void handleDeleteRecitation(){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView recitationDataTable = workspace.getRecitationDataTable();
        CSGData data = (CSGData)app.getDataComponent();
        jTPS_Transaction deleteRecitation = new DeleteRecitation(app);
        jTPS.addTransaction(deleteRecitation);
        markWorkAsEdited();
        recitationDataTable.refresh();
    }
    
    public void loadRecitationTotext(){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView recitationDataTable = workspace.getRecitationDataTable();
        Object selectedItem = recitationDataTable.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            Recitation r = (Recitation)selectedItem;
            workspace.getRdSectionTextField().setText(r.getSection());
            workspace.getRdInstructorTextField().setText(r.getInstructor());
            workspace.getRdDayTimeTextField().setText(r.getDayTime());
            workspace.getRdLocationTextField().setText(r.getLocation());
            workspace.getRdFirstTaComboBox().setValue(r.getSupervisingTAOne());
            workspace.getRdSecondTaComboBox().setValue(r.getSupervisingTATwo());

        }
    }
    
    
   // SCHEDULE DATA
    public void handleDatePicker(){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();

         LocalDate monday;
         LocalDate friday;
         
        if(!(workspace.getStartingMondayDatePicker().getValue()==null)){
            monday= workspace.getStartingMondayDatePicker().getValue();

            if(!(monday.getDayOfWeek().getValue()==1)){
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(INCORRECT_DATA), props.getProperty(MONDAY_START_MESSAGE));            
                workspace.getStartingMondayDatePicker().getEditor().clear();
                return;
            }
            
        }
        else{
            return;
        }
        
        
        if(!(workspace.getEndingFridayDatePicker().getValue()==null)){
            
            friday= workspace.getEndingFridayDatePicker().getValue();

            if(!(friday.getDayOfWeek().getValue()==5)){
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(INCORRECT_DATA), props.getProperty(FRIDAY_START_MESSAGE));            
                workspace.getEndingFridayDatePicker().getEditor().clear();
                return;
            }
        }
        else{
            return;
        }
        
        //dates are okay so we can formally declare now
        
        monday= workspace.getStartingMondayDatePicker().getValue();
        friday= workspace.getEndingFridayDatePicker().getValue();

        if(!friday.isAfter(monday)){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(INCORRECT_DATA), props.getProperty(INCORRECT_DATES));            
            workspace.getEndingFridayDatePicker().getEditor().clear();
            return;
        }
        
        CSGData data = (CSGData)app.getDataComponent();
        
        data.setDate(monday, friday);


    }
    
    public void handleAddSchedule(){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView scheduleItemsTable = workspace.getScheduleItemsTable();

        jTPS_Transaction addRec = new AddSchedule(app);
        jTPS.addTransaction(addRec);
        markWorkAsEdited();  
        scheduleItemsTable.refresh();

    }
    
    public void handleEditSchedule(){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView scheduleItemsTable = workspace.getScheduleItemsTable();
        
        CSGData data = (CSGData)app.getDataComponent();

        jTPS_Transaction editSchedule = new EditSchedule(app);
        jTPS.addTransaction(editSchedule);
        markWorkAsEdited();
        scheduleItemsTable.refresh();
    }
    
    public void handleDeleteSchedule(){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView scheduleItemsTable = workspace.getScheduleItemsTable();
        CSGData data = (CSGData)app.getDataComponent();
        jTPS_Transaction deleteSchedule = new DeleteSchedule(app);
        jTPS.addTransaction(deleteSchedule);
        markWorkAsEdited();
        scheduleItemsTable.refresh();
    }
    
    public void loadScheduleTotext(){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView scheduleItemsTable = workspace.getScheduleItemsTable();
        Object selectedItem = scheduleItemsTable.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            ScheduleItem si = (ScheduleItem)selectedItem;
            workspace.getSdTypeComboBox().getSelectionModel().select(si.getType());
            workspace.getSdDatePicker().getEditor().setText(si.getDate());
            workspace.getSdTimeTextField().setText(si.getTime());
            workspace.getSdTitleTextField().setText(si.getTitle());
            workspace.getSdTopicTextField().setText(si.getTopic());
            workspace.getSdLinkTextField().setText(si.getLink());
            workspace.getSdCriteriaTextField().setText(si.getCriteria());
            
            System.out.println(si.getTime()+"  "+ si.getTitle()+"  "+si.getTopic() +"  "+si.getLink() +"  "+ si.getCriteria());
        
        }
    }
   
    
   // PROJECT DATA
    public void handleAddTeam(){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView pdTeamTableView = workspace.getPdTeamTableView();

        jTPS_Transaction addTeam = new AddTeam(app);
        jTPS.addTransaction(addTeam);
        markWorkAsEdited();  
        pdTeamTableView.refresh();

    }
    
    public void handleEditTeam(){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView pdTeamTableView = workspace.getPdTeamTableView();
        CSGData data = (CSGData)app.getDataComponent();

        jTPS_Transaction editTeam = new EditTeam(app);
        jTPS.addTransaction(editTeam);
        markWorkAsEdited();
        pdTeamTableView.refresh();
    }
    
    public void handleDeleteTeam(){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView pdTeamTableView = workspace.getPdTeamTableView();
        CSGData data = (CSGData)app.getDataComponent();
        jTPS_Transaction deleteTeam = new DeleteTeam(app);
        jTPS.addTransaction(deleteTeam);
        markWorkAsEdited();
        pdTeamTableView.refresh();
    }
    
    public void loadTeamTotext(){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView pdTeamTableView = workspace.getPdTeamTableView();
        Object selectedItem = pdTeamTableView.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            Team t = (Team)selectedItem;
            
            workspace.getPdTopNameTextField().setText(t.getName());
            Color teamColor= Color.web(t.getColor());
            Color textColor= Color.web(t.getTextColor());
            workspace.getPdColorPicker().setValue(teamColor);
            workspace.getPdTextColorPicker().setValue(textColor);
            workspace.getPdTopLinkTextField().setText(t.getLink());

        }
    }
    
    

   // STUDENT DATA
    public void handleAddStudent(){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView pdStudentTable = workspace.getPdStudentTable();

        jTPS_Transaction addStudent = new AddStudent(app);
        jTPS.addTransaction(addStudent);
        markWorkAsEdited();  
        pdStudentTable.refresh();

    }

    public void handleEditStudent(){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView pdStudentTable = workspace.getPdStudentTable();
        CSGData data = (CSGData)app.getDataComponent();

        jTPS_Transaction editStudent = new EditStudent(app);
        jTPS.addTransaction(editStudent);
        markWorkAsEdited();
        pdStudentTable.refresh();
    }

    public void handleDeleteStudent(){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView pdStudentTable = workspace.getPdStudentTable();
        CSGData data = (CSGData)app.getDataComponent();
        jTPS_Transaction deleteStudent = new DeleteStudent(app);
        jTPS.addTransaction(deleteStudent);
        markWorkAsEdited();
        pdStudentTable.refresh();
    }

    public void loadStudentTotext(){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView pdStudentTable = workspace.getPdStudentTable();
        Object selectedItem = pdStudentTable.getSelectionModel().getSelectedItem();
        
        if(selectedItem != null){
            Student s = (Student)selectedItem;
            workspace.getPdFirstNameTextField().setText(s.getFirstName());
            workspace.getPdLastNameTextField().setText(s.getLastName());
            workspace.getPdBottomTeamComboBox().getSelectionModel().select(s.getTeam());
            workspace.getPdRoleTextField().setText(s.getRole());
        }
    }

    
    public void handleChangeDirectoy(){
       CSGData data = (CSGData)app.getDataComponent();

        // PROMPT THE USER FOR A FILE NAME
        DirectoryChooser dc = new DirectoryChooser();
        dc.setInitialDirectory(new File(PATH_EXPORT));
        String path = dc.showDialog(null).getPath();
        data.courseData.setExportDirectory(path);
        System.out.println(path);
        
    }
    
    //Export Request
    public void handleExportRequest() throws IOException{
        CSGFiles fileComponent= (CSGFiles) app.getFileComponent();
        CSGData data = (CSGData)app.getDataComponent();
        
        //will export the data into the desired String Path
        fileComponent.exportData(data,data.courseData.getExportDirectory());

    }
    
    public void handleTemplateDirectory() throws FileNotFoundException, IOException{
        CSGFiles fileComponent= (CSGFiles) app.getFileComponent();
        CSGData data = (CSGData)app.getDataComponent();
//        
        DirectoryChooser dc = new DirectoryChooser();
        dc.setInitialDirectory(new File(PATH_TEMPLATES));
        String path = dc.showDialog(null).getPath();
        
        copy(path,data.courseData.getExportDirectory());

        }
        
    
    public void copy(String oldPath, String newPath) { 
        try { 
            (new File(newPath)).mkdirs();
            File old =new File(oldPath); 
            String[] file = old.list(); 
            File target = null; 
            for (int i = 0; i < file.length; i++) { 
                if(oldPath.endsWith(File.separator))
                    target = new File(oldPath+file[i]); 
                else
                    target = new File(oldPath+File.separator+file[i]); 
                if(target.isFile()){ 
                    FileInputStream input = new FileInputStream(target); 
                    FileOutputStream output = new FileOutputStream(newPath + "/" + (target.getName()).toString()); 
                    byte[] b = new byte[1024 * 5]; 
                    int templength; 
                    while ( (templength = input.read(b)) != -1)
                        output.write(b, 0, templength); 
                    output.flush(); 
                    output.close(); 
                    input.close(); 
                } 
                if(target.isDirectory())
                    copy(oldPath+"/"+file[i],newPath+"/"+file[i]); 
            } 
        } 
        catch (Exception e) {  
            e.printStackTrace(); 
        } 
    }
     
    
}