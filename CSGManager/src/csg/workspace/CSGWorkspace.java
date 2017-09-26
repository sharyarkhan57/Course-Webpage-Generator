package csg.workspace;

import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import static djf.settings.AppPropertyType.SAVE_UNSAVED_WORK_MESSAGE;
import static djf.settings.AppPropertyType.SAVE_UNSAVED_WORK_TITLE;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import csg.CSGManagerApp;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;
import csg.CSGManagerProp;
import static csg.CSGManagerProp.ADD_BUTTON_TEXT;
import static csg.CSGManagerProp.MISSING_TA_NAME_MESSAGE;
import static csg.CSGManagerProp.MISSING_TA_NAME_TITLE;
import static csg.CSGManagerProp.UPDATE_BUTTON_TEXT;
import csg.style.CSGStyle;
import csg.data.CSGData;
import csg.data.Recitation;
import csg.data.ScheduleItem;
import csg.data.SitePage;
import csg.data.Student;
import csg.data.TeachingAssistant;
import csg.data.Team;
import csg.file.TimeSlot;
import static djf.settings.AppPropertyType.SAVE_WORK_TITLE;
import static djf.settings.AppPropertyType.WORK_FILE_EXT;
import static djf.settings.AppPropertyType.WORK_FILE_EXT_DESC;
import static djf.settings.AppStartupConstants.PATH_WORK;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Screen;

/**
 * This class serves as the workspace component for the TA Manager
 * application. It provides all the user interface controls in 
 * the workspace area.
 * 
 * @author Richard McKenna
 */
public class CSGWorkspace extends AppWorkspaceComponent {
    
    // THIS PROVIDES US WITH ACCESS TO THE APP COMPONENTS
    CSGManagerApp app;
    boolean add;
    boolean addRecitation;
    boolean addSchedule;
    boolean addTeam;
    boolean addStudent;

    // THIS PROVIDES RESPONSES TO INTERACTIONS WITH THIS WORKSPACE
    CSGController controller;

    //For the Tabbed Buttons
    TabPane workspaceTabs= new TabPane();
    Tab courseDetailsTab = new Tab("Course Details");
    Tab taDataTab = new Tab("TA Data");
    Tab recitationDataTab = new Tab("Recitation Data");
    Tab scheduleDataTab = new Tab("Schedule Data");
    Tab projectDataTab = new Tab("Project Data");
    
    Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();
    
    VBox workspaceTabPaneVbox;
    
    String path="";



    
    // NOTE THAT EVERY CONTROL IS PUT IN A BOX TO HELP WITH ALIGNMENT
    
    //**********      COURSE DETAILS TAB         ***************************
    //**********************************************************************
    //Hold all of Course Details
    VBox courseDetailsVBox;
    ScrollPane courseScrollPane;
    
    //Courseinfo portion VBox
    VBox courseInfoVBox;
    Label courseInfoLabel;

    
    //Subject And Number HBox
    HBox subjectAndNumberHBox;
    
    //Subject HBox
    HBox subjectHBox;
    Label subjectLabel;
    ComboBox subjectComboBox;
    ObservableList<String> subject_options;

    //Number HBox
    HBox numberHBox;
    Label numberLabel;
    ComboBox numberComboBox;
    ObservableList<String> number_options;

    
    //Semester And Year HBox
    HBox semesterAndYearHBox;
    
    //Semester HBox
    HBox semesterHBox;
    Label semesterLabel;
    ComboBox semesterComboBox;
    ObservableList<String> semester_options;

    //Year Hbox
    HBox yearHbox;
    Label yearLabel;
    ComboBox yearComboBox;
    ObservableList<String> year_options;

    
    //Title HBox
    HBox cdTitleHBox;
    Label titleLabel;
    TextField tileTextField;

    //Instructor name HBox
    HBox cdInstructorNameHBox;
    Label instructorNameLabel;
    TextField instructorNameTextField;
    
    
    //Instructor home HBox
    HBox cdInstructorHomeHBox;
    Label instructorHomeLabel;
    TextField instructorHomeTextField;
    
    //Export HBox
    HBox cdExportHBox;
    Label exportDirLabel;
    Button changeDirButton;
    

    
    //Site Template Portion Vbox
    VBox siteTemplateVBox;

    Label siteTemplateLabel;
    Label selectedDirectoryDirectionsLabel;
    Label selectedDirectoryLabel;
    Button selectTemplateDirectoryButton;
    
    Label sitePagesLabel;
        
    //Table for Site Pages
    TableView<SitePage> sitePageTable;
    TableColumn<SitePage, Boolean> useColumn;
    TableColumn<SitePage, String> navBarColumn;
    TableColumn<SitePage, String> fileNameColumn;
    TableColumn<SitePage, String> scriptColumn;

    
    
    //PageStyle Portion VBox
    VBox pageStyleImageVBox;
    
    //Page Style Header
    Label pageStyleLabel;
    
    // Banner School Image HBox
    HBox bannerschoolImageHBox;
        Label bannerSchoolImageLabel;
        Button changeBannerSchoolButton;
        ImageView bannerSchoolImage;
    
    // Left Footer Image HBox
    HBox leftFooterHBox;
        Label leftFooterImageLabel;
        Button leftFooterImageButton;
        ImageView leftFooterImage;

    //Right Footer Image HBox
    HBox rightFooterHBox;
        Label rightFooterImageLabel;
        Button rightFooterImageButton;
        ImageView rightFooterImage;
    
    //Style sheet HBox
    HBox styleSheetHBox;
        Label styleSheetLabel;
        ComboBox styleSheetComboBox;
    
    //Bottom Note
    Label noteLabel;
    
    
    
    
    //******************        TA DATA TAB         ************************
    //**********************************************************************
    
    
    HBox taDataSplitPane;
    
    VBox leftPane;
    VBox rightPane;


    // FOR THE HEADER ON THE LEFT
    HBox tasHeaderBox;
    Label tasHeaderLabel;
    Button tasHeaderDeleteButton;
    
    
    // FOR THE TA TABLE
    TableView<TeachingAssistant> taTable;
    TableColumn<TeachingAssistant, String> nameColumn;
    TableColumn<TeachingAssistant, String> emailColumn;
    TableColumn<TeachingAssistant, Boolean> undergradCheckBoxColumn;

    
    

    // THE TA INPUT
    HBox addBox;
    TextField nameTextField;
    TextField emailTextField;
    Button addButton;
    Button clearButton;

    // THE HEADER ON THE RIGHT
    HBox officeHoursHeaderBox;
    Label officeHoursHeaderLabel;
    
    ObservableList<String> time_options;
    ComboBox comboBox1;
    ComboBox comboBox2;
    
    // THE OFFICE HOURS GRID
    GridPane officeHoursGridPane;
    HashMap<String, Pane> officeHoursGridTimeHeaderPanes;
    HashMap<String, Label> officeHoursGridTimeHeaderLabels;
    HashMap<String, Pane> officeHoursGridDayHeaderPanes;
    HashMap<String, Label> officeHoursGridDayHeaderLabels;
    HashMap<String, Pane> officeHoursGridTimeCellPanes;
    HashMap<String, Label> officeHoursGridTimeCellLabels;
    HashMap<String, Pane> officeHoursGridTACellPanes;
    HashMap<String, Label> officeHoursGridTACellLabels;
    
    
    
    //******************        RECITATION DATA TAB         ****************
    //**********************************************************************
    
    //Hold all of Recitation Data
    VBox recitationsDataVBox;
    ScrollPane recitationsScrollPane;
    
    //Top portion of RecitationDataVBox
    VBox topRecitationDataVBox;
    
    HBox recitationHeaderHBox;
    Label recitationsLabel;
    Button recitationDeleteButton;

    ///initialize table 
    TableView<Recitation> recitationDataTable;
    TableColumn<Recitation, String> sectionColumn;
    TableColumn<Recitation, String> instructorColumn;
    TableColumn<Recitation, String> dayTimeColumn;
    TableColumn<Recitation, String> locationColumn;
    TableColumn<Recitation, String> firstTAColumn;
    TableColumn<Recitation, String> secondTAColumn;


    // VBox for the bottom half of the 
    VBox bottomRecitationDataVBox;
    Label rdSectionHeaderLabel;

    HBox rdSectionHBox;
    Label rdSectionLabel;
    TextField rdSectionTextField;

    HBox rdInstructorHBox;
    Label rdInstructorLabel;
    TextField rdInstructorTextField;

    HBox rdDayTimeHBox;
    Label rdDayTimeLabel;
    TextField rdDayTimeTextField;

    HBox rdLocationHBox;
    Label rdLocationLabel;
    TextField rdLocationTextField;

    HBox rdFirstTaHBox;
    Label rdFirstTaLabel;
    ComboBox<TeachingAssistant> rdFirstTaComboBox;

    HBox rdSecondTaHbox;
    Label rdSecondTaLabel;
    ComboBox<TeachingAssistant> rdSecondTaComboBox;

    HBox rdButtonsHbox;
    Button rdAddButton;
    Button rdClearButton;
    
    //******************        SCHEDULE DATA TAB         ******************
    //**********************************************************************
    VBox scheduleDataTabVBox;

    ScrollPane scheduleDataScrollPane;
    //will be at the top of scheduleDataTabVBox VBox
    Label sdScheduleHeaderLabel;


    //TOP VBox decleration
    VBox sdTopVBox;
    Label sdCalendarBoundariesLabel;


    //Following two HBox's go into sdCalendarPickerHBox
    HBox sdCalendarPickerHBox;

    HBox sdStartingMondayPickerHBox;
    Label sdStartingMondayLabel;
    DatePicker startingMondayDatePicker;

    HBox sdEndingFridayPickerHBox;
    Label sdEndingFridayLabel;
    DatePicker endingFridayDatePicker;


    //Schedule Items VBoc
    VBox sdBottomVBox;

    HBox sdScheduleItemsHeaderHBox;
    Label scheduleItemsLabel;
    Button scheduleItemsDeleteButton;

    //table view initialization
    TableView<ScheduleItem> scheduleItemsTable;
    TableColumn<ScheduleItem, String> typeColumn;
    TableColumn<ScheduleItem, String> dateColumn;
    TableColumn<ScheduleItem, String> titleColumn;
    TableColumn<ScheduleItem, String> topicColumn;

    //filler hbox
    HBox sdFillerHBox;

    Label sdAddEditLabel;

    //Type
    HBox sdTypeHBox;
    Label sdTypeLabel;
    ObservableList<String> type_options;
    ComboBox sdTypeComboBox;

    //Date
    HBox sdDateHBox;
    Label sdDateLabel;
    DatePicker sdDatePicker;

    //Time
    HBox sdTimeHBox;
    Label sdTimeLabel;
    TextField sdTimeTextField;

    //Title
    HBox sdTitleHBox;
    Label sdTitleLabel;
    TextField sdTitleTextField;

    //Link
    HBox sdTopicHBox;
    Label sdTopicLabel;
    TextField sdTopicTextField;

    //Link
    HBox sdLinkHBox;
    Label sdLinkLabel;
    TextField sdLinkTextField;

    //Criteria
    HBox sdCriteriaHBox;
    Label sdCriteriaLabel;
    TextField sdCriteriaTextField;

    //Buttons
    HBox sdButtonsHbox;
    Button sdAddUpdateButton;
    Button sdClearButton;
    
    
    
    
    
    
    //******************        PROJECT DATA TAB         ******************
    //**********************************************************************
    
    ScrollPane projectDataScrollPane;


    VBox projectDataVBox;
    //hold top
    VBox pdTopVBox;
    
    
    
    
    Label pdProjectsHeaderLabel;
    HBox pdTeamHeaderHBox;
    Label pdTeamLabel;
    Button pdTeamDeleteButton;


    //set up team table
    TableView<Team> pdTeamTableView;
    TableColumn<Team, String> pdNameColumn;
    TableColumn<Team, String> pdColorColumn;
    TableColumn<Team, String> pdTextColorColumn;
    TableColumn<Team, String> pdLinkColumn;

    //Filler HBox
    HBox pdFillerHbox;

    //add/edit top portion

    Label pdTopAddEditLabel;

    HBox pdTopNameHBox;
    Label pdTopNameLabel;
    TextField pdTopNameTextField;


    HBox pdTopColorHBox;

    //left side
    HBox pdColorHBox;
    Label pdColorLabel;
    ColorPicker pdColorPicker;

    //right side
    HBox pdTextColorHBox;
    Label pdTextColorLabel;
    ColorPicker pdTextColorPicker;


    HBox pdTopLinkHBox;
    Label pdTopLinkLabel;
    TextField pdTopLinkTextField;

    //Buttons
    HBox pdTopAddUpdateButtonHBox;
    Button pdTopAddUpdateButton;
    Button pdTopClearButton;


    //      bottom portion of tab       ////
    
    
    VBox pdBottomVBox;
    //student header box
    HBox pdStudentTableHeaderHbox;
    Label pdStudentHeaderLabel;
    Button pdStudentDeleteButton;


    //student table
     //table view initialization
    TableView<Student> pdStudentTable;
    TableColumn<Student, String> pdFirstNameColumn;
    TableColumn<Student, String> pdLastNameColumn;
    TableColumn<Student, String> pdTeamColumn;
    TableColumn<Student, String> pdRoleColumn;

    //add/edit Label
    Label pdAddEditLabel;

    //First Name
    HBox pdFirstNameHBox;
    Label pdFirstNameLabel;
    TextField pdFirstNameTextField;

    //Last Name
    HBox pdLastNameHBox;
    Label pdLastNameLabel;
    TextField pdLastNameTextField;

    //T team
    HBox pdBottomTeamHBox;
    Label pdBottomTeamLabel;
    ObservableList<String> team_options;
    ComboBox<Team> pdBottomTeamComboBox;

    //Role
    HBox pdRoleHBox;
    Label pdRoleLabel;
    TextField pdRoleTextField;


    //Add/EditButtons
    HBox pdButtonHBox;
    Button pdAddUpdateButton;
    Button pdClearButton;

    
    

    /**
     * The constructor initializes the user interface, except for
     * the full office hours grid, since it doesn't yet know what
     * the hours will be until a file is loaded or a new one is created.
     */
    public CSGWorkspace(CSGManagerApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
        add = true;
        addSchedule= true;
        addTeam= true;
        addStudent= true;


        // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        //Set Up Tabs
        //Make Sure They Can't be closed
        workspaceTabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
        //add tabs to tabPane
        workspaceTabs.getTabs().addAll(courseDetailsTab,taDataTab,
                recitationDataTab, scheduleDataTab,projectDataTab);
    
        this.courseDetailsTabInit();
        this.taDataTabInit();
        this.recitationDataTabInit();
        this.scheduleDataTabInit();
        this.projectDataTabInit();
        
        // AND PUT EVERYTHING IN THE WORKSPACE
        workspace = new BorderPane();
        workspaceTabPaneVbox = new VBox();
        workspaceTabPaneVbox.getChildren().add(workspaceTabs);
        
        workspaceTabPaneVbox.prefWidthProperty().bind(workspace.widthProperty());


        ((BorderPane) workspace).setCenter(workspaceTabPaneVbox);

        // MAKE SURE THE TABLE EXTENDS DOWN FAR ENOUGH
//        taTable.prefHeightProperty().bind(workspace.heightProperty().multiply(1.9));

        
        
        // NOW LET'S SETUP THE EVENT HANDLING
        controller = new CSGController(app);
        
        //System Buttons
        app.getGUI().exportButton.setOnAction(e -> {
            
            if(path.isEmpty()){
                controller.handleChangeDirectoy();
            }
            
            try {
                controller.handleExportRequest();
            } catch (IOException ex) {
                Logger.getLogger(CSGWorkspace.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        
        app.getGUI().undoButton.setOnAction(e -> {
                    controller.Undo();
        });
               
        app.getGUI().redoButton.setOnAction(e -> {
                    controller.Redo();
        });
        
        
        
        
        
        //      CourseDetails  TAB
        
        subjectComboBox.setOnAction(e -> {
            controller.handleAddCourse();
        });
        numberComboBox.setOnAction(e -> {
               controller.handleAddCourse();
        });
        semesterComboBox.setOnAction(e -> {
            controller.handleAddCourse();
        });
        yearComboBox.setOnAction(e -> {
            controller.handleAddCourse();
        });
        
        tileTextField.setOnAction(e -> {
            controller.handleAddCourse();
        });
        instructorNameTextField.setOnAction(e -> {
            controller.handleAddCourse();
        });
        instructorHomeTextField.setOnAction(e -> {
            controller.handleAddCourse();
        });
        
        
        //HANDLER SHOULD EDIT DESKTOP JAVAFRAMEWORK TO REFLECT EXPORT Change
        changeDirButton.setOnAction(e ->{
                controller.handleChangeDirectoy();
        });
        
        selectTemplateDirectoryButton.setOnAction(e ->{
            try {
                controller.handleTemplateDirectory();
            } catch (IOException ex) {
                Logger.getLogger(CSGWorkspace.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        
        //Bottom (Page Style)
        changeBannerSchoolButton.setOnAction(e ->{

        });
        
        leftFooterImageButton.setOnAction(e ->{

        });
        
        rightFooterImageButton.setOnAction(e ->{

        });
        
        
        
        
        
        
        
        

        // CONTROLS FOR ADDING TAs
        
        nameTextField.setOnAction(e -> {
            if(!add)
                controller.changeExistTA();
            else
                controller.handleAddTA();
        });
        emailTextField.setOnAction(e -> {
            if(!add)
                controller.changeExistTA();
            else
                controller.handleAddTA();
        });
        addButton.setOnAction(e -> {
            if(!add)
                controller.changeExistTA();
            else
                controller.handleAddTA();
        });
        clearButton.setOnAction(e -> {
            addButton.setText(ADD_BUTTON_TEXT.toString());
            add = true;
            nameTextField.clear();
            emailTextField.clear();
            taTable.getSelectionModel().select(null);
        });

        taTable.setFocusTraversable(true);
        taTable.setOnKeyPressed(e -> {
            controller.handleKeyPress(e.getCode());
        });
        taTable.setOnMouseClicked(e -> {
            addButton.setText(UPDATE_BUTTON_TEXT.toString());
            add = false;
            controller.loadTAtotext();
        });
        
        workspace.setOnKeyPressed(e ->{
            if(e.isControlDown())
                if(e.getCode() == KeyCode.Z)
                    controller.Undo();
                else if(e.getCode() == KeyCode.Y)
                    controller.Redo();
        });
        
        
        //CONTROLS FOR RECITATIONS
        
        recitationDeleteButton.setOnAction(e -> {
                controller.handleDeleteRecitation();  
        });
                
        rdAddButton.setOnAction(e -> {
            if(!addRecitation){
                controller.handleEditRecitation();
            }
            else{
                controller.handleAddRecitation();
            }
        });
        rdClearButton.setOnAction(e -> {
            rdAddButton.setText(ADD_BUTTON_TEXT.toString());
            addRecitation = true;

            rdSectionTextField.clear();
            rdInstructorTextField.clear();
            rdDayTimeTextField.clear();
            rdLocationTextField.clear();

            recitationDataTable.getSelectionModel().select(null);
        });
        recitationDataTable.setOnMouseClicked(e -> {
            rdAddButton.setText(UPDATE_BUTTON_TEXT.toString());
            addRecitation = false;
            controller.loadRecitationTotext();
        });
        
        
        
        
        // CONTROLS FOR SCHEDULE
        
        startingMondayDatePicker.setOnAction(e -> {
                controller.handleDatePicker();  
        });
        
        endingFridayDatePicker.setOnAction(e -> {
                controller.handleDatePicker();  
        });

        scheduleItemsDeleteButton.setOnAction(e -> {
                controller.handleDeleteSchedule();  
        });
        sdAddUpdateButton.setOnAction(e -> {
            if(!addSchedule){
                controller.handleEditSchedule();
            }
            else{
                controller.handleAddSchedule();
            }
        });
        sdClearButton.setOnAction(e -> {
            rdAddButton.setText(ADD_BUTTON_TEXT.toString());
            addSchedule = true;

            sdTypeComboBox.getSelectionModel().clearSelection();
            sdDatePicker.getEditor().clear();
            sdTimeTextField.clear();
            sdTitleTextField.clear();
            sdTopicTextField.clear();
            sdLinkTextField.clear();
            sdCriteriaTextField.clear();
            scheduleItemsTable.getSelectionModel().select(null);
            
        });
        scheduleItemsTable.setOnMouseClicked(e -> {
            sdAddUpdateButton.setText(UPDATE_BUTTON_TEXT.toString());
            addSchedule = false;
            controller.loadScheduleTotext();
        });
        
        
        
        
        
        
        // CONTROLS FOR TEAM
        
        pdTeamDeleteButton.setOnAction(e -> {
                controller.handleDeleteTeam(); 
                pdTopClearButton.fire();
                pdStudentTable.refresh();
        });
        pdTopAddUpdateButton.setOnAction(e -> {
            if(!addTeam){
                controller.handleEditTeam();
            }
            else{
                controller.handleAddTeam();
            }
        });
        pdTopClearButton.setOnAction(e -> {
            rdAddButton.setText(ADD_BUTTON_TEXT.toString());
            addTeam = true;

            pdTopNameTextField.clear();
            pdColorPicker.setValue(Color.BLACK);
            pdTextColorPicker.setValue(Color.BLACK);
            pdTopLinkTextField.clear();
            pdTeamTableView.getSelectionModel().select(null);
            
        });
        pdTeamTableView.setOnMouseClicked(e -> {
            sdAddUpdateButton.setText(UPDATE_BUTTON_TEXT.toString());
            addTeam = false;
            controller.loadTeamTotext();
        });
        
        
        
        
        
        
        // CONTROLS FOR STUDENTS
        
        pdStudentDeleteButton.setOnAction(e -> {
                controller.handleDeleteStudent();  
        });
        pdAddUpdateButton.setOnAction(e -> {
            if(!addStudent){
                controller.handleEditStudent();
            }
            else{
                controller.handleAddStudent();
            }
        });
        pdClearButton.setOnAction(e -> {
            pdAddUpdateButton.setText(ADD_BUTTON_TEXT.toString());
            addStudent = true;

            pdFirstNameTextField.clear();
            pdLastNameTextField.clear();
            pdBottomTeamComboBox.getSelectionModel().clearSelection();
            pdRoleTextField.clear();

            pdStudentTable.getSelectionModel().select(null);
            
        });
        pdStudentTable.setOnMouseClicked(e -> {
            pdAddUpdateButton.setText(UPDATE_BUTTON_TEXT.toString());
            addStudent = false;
            controller.loadStudentTotext();
        });
        
        
        
        
    }
    
    //construct CourseDetails Tab
    
    //Tab Initilization Functinos
    void courseDetailsTabInit(){
        //**********      COURSE DETAILS TAB Initialization        *************
        //**********************************************************************
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        //Courseinfo portion VBox
        courseInfoVBox= new VBox();
        
        String courseInfoText = props.getProperty(CSGManagerProp.CD_HEADER_TEXT.toString());
        courseInfoLabel= new Label(courseInfoText);

        //Subject HBox
        subjectHBox= new HBox();

        String subjectLabelText = props.getProperty(CSGManagerProp.CD_SUBJECT_LABEL_TEXT.toString());
        subjectLabel= new Label(subjectLabelText);
        subjectLabel.setMinWidth(150);

        subject_options = FXCollections.observableArrayList(props.getProperty(CSGManagerProp.SUBJECT_CSE.toString()),
            props.getProperty(CSGManagerProp.SUBJECT_MAT.toString()),
            props.getProperty(CSGManagerProp.SUBJECT_ESE.toString()),
            props.getProperty(CSGManagerProp.SUBJECT_AMS.toString()));
        subjectComboBox= new ComboBox(subject_options);
        subjectComboBox.setMinWidth(150);

        subjectHBox.getChildren().addAll(subjectLabel,subjectComboBox);

        //Number HBox
        numberHBox= new HBox();

        String numberLabelText = props.getProperty(CSGManagerProp.CD_NUMBER_LABEL_TEXT.toString());
        numberLabel= new Label(numberLabelText);
        numberLabel.setMinWidth(150);

        number_options = FXCollections.observableArrayList(props.getProperty(CSGManagerProp.CLASS_214.toString()),
            props.getProperty(CSGManagerProp.CLASS_219.toString()),
            props.getProperty(CSGManagerProp.CLASS_220.toString()),
            props.getProperty(CSGManagerProp.CLASS_320.toString()));
        numberComboBox= new ComboBox(number_options);
        numberComboBox.setMinWidth(150);

        numberHBox.getChildren().addAll(numberLabel,numberComboBox);

        //Subject And Number HBox
        subjectAndNumberHBox= new HBox();
        subjectAndNumberHBox.getChildren().addAll(subjectHBox,numberHBox);

        //Semester And Year HBox
        semesterAndYearHBox= new HBox();

        //Semester HBox
        semesterHBox= new HBox();
        String semesterLabelText = props.getProperty(CSGManagerProp.CD_SEMESTER_LABEL_TEXT.toString());
        semesterLabel= new Label(semesterLabelText);
        semesterLabel.setMinWidth(150);

        semester_options = FXCollections.observableArrayList(props.getProperty(CSGManagerProp.SPRING_SEMESTER.toString()),
            props.getProperty(CSGManagerProp.SUMMER_SEMESTER.toString()),
            props.getProperty(CSGManagerProp.FALL_SEMESTER.toString()),
            props.getProperty(CSGManagerProp.WINTER_SEMESTER.toString()));
            
        semesterComboBox= new ComboBox(semester_options);
        semesterComboBox.setMinWidth(150);
        
        semesterHBox.getChildren().addAll(semesterLabel,semesterComboBox);
        
        //Year Hbox
        yearHbox= new HBox();
        String yearLabelText = props.getProperty(CSGManagerProp.CD_YEAR_LABEL_TEXT.toString());
        yearLabel= new Label(yearLabelText);
        yearLabel.setMinWidth(150);
       
        year_options = FXCollections.observableArrayList(props.getProperty(CSGManagerProp.YEAR_2017.toString()),
            props.getProperty(CSGManagerProp.YEAR_2018.toString()),
            props.getProperty(CSGManagerProp.YEAR_2019.toString()),
            props.getProperty(CSGManagerProp.YEAR_2020.toString()));
        yearComboBox= new ComboBox(year_options);
        yearComboBox.setMinWidth(150);

        yearHbox.getChildren().addAll(yearLabel,yearComboBox);
        
        semesterAndYearHBox.getChildren().addAll(semesterHBox,yearHbox);

        //Title HBox
        cdTitleHBox= new HBox();
        String titleLabelText = props.getProperty(CSGManagerProp.CD_TITLE_LABEL_TEXT.toString());
        titleLabel= new Label(titleLabelText);
        titleLabel.setMinWidth(150);

        String tileTextFieldText = props.getProperty(CSGManagerProp.CD_TITLE_TEXT_FIELD.toString());
        tileTextField= new TextField();
        tileTextField.setPromptText(tileTextFieldText);
        tileTextField.setMinWidth(450);

        cdTitleHBox.getChildren().addAll(titleLabel,tileTextField);
        
        //Instructor name HBox
        cdInstructorNameHBox= new HBox();
        String instructorNameLabelText = props.getProperty(CSGManagerProp.CD_INSTRUCTOR_NAME_LABEL_TEXT.toString());
        instructorNameLabel= new Label(instructorNameLabelText);
        instructorNameLabel.setMinWidth(150);

        String instructorNameTextFieldTEXT = props.getProperty(CSGManagerProp.CD_INSTRUCTOR_NAME_LABEL_FIELD.toString());
        instructorNameTextField= new TextField();
        instructorNameTextField.setMinWidth(450);
        instructorNameTextField.setPromptText(instructorNameTextFieldTEXT);
        cdInstructorNameHBox.getChildren().addAll(instructorNameLabel,instructorNameTextField);

        //Instructor home HBox
        cdInstructorHomeHBox= new HBox();
        String instructorHomeLabelText = props.getProperty(CSGManagerProp.CD_INSTRUCTOR_HOME_LABEL_TEXT.toString());    
        instructorHomeLabel= new Label(instructorHomeLabelText);
        instructorHomeLabel.setMinWidth(150);
        String  instructorHomeTextFieldText = props.getProperty(CSGManagerProp.CD_INSTRUCTOR_HOME_LABEL_FIELD.toString());    
        instructorHomeTextField= new TextField();
        instructorHomeTextField.setMinWidth(450);
        instructorHomeTextField.setPromptText(instructorHomeTextFieldText);
        
        cdInstructorHomeHBox.getChildren().addAll(instructorHomeLabel,instructorHomeTextField);

        //Export HBox
        cdExportHBox= new HBox();
        String exportDirLabelText = props.getProperty(CSGManagerProp.CD_EXPORT_DIR_LABEL_TEXT.toString());    
        exportDirLabel= new Label(exportDirLabelText);
        exportDirLabel.setMinWidth(150);
        String changeDirButtonText = props.getProperty(CSGManagerProp.CD_CHANGE_DIR_BUTTON_TEXT.toString());    
        changeDirButton= new Button(changeDirButtonText);
        
        cdExportHBox.getChildren().addAll(exportDirLabel,changeDirButton);

        //Create Course Info VBox
        courseInfoVBox.getChildren().addAll(courseInfoLabel,subjectAndNumberHBox,semesterAndYearHBox,cdTitleHBox,cdInstructorNameHBox,cdInstructorHomeHBox,cdExportHBox);


        //Site Template Portion Vbox
        siteTemplateVBox= new VBox();
        String siteTemplateLabelText = props.getProperty(CSGManagerProp.CD_SITE_TEMPLATE_LABEL_TEXT.toString());    
        siteTemplateLabel= new Label(siteTemplateLabelText);
        siteTemplateLabel.setMinWidth(150);
        String selectedDirectoryDirectionsLabelText = props.getProperty(CSGManagerProp.CD_SELECTED_DIRECTORY_DIRECTIONS_LABEL_TEXT.toString());    
        selectedDirectoryDirectionsLabel= new Label(selectedDirectoryDirectionsLabelText);
        selectedDirectoryDirectionsLabel.setMinWidth(150);
        String selectedDirectoryLabelText = props.getProperty(CSGManagerProp.CD_SELECTED_DIRECTORY_LABEL_TEXT.toString());    
        selectedDirectoryLabel= new Label(selectedDirectoryLabelText);
        selectedDirectoryLabel.setMinWidth(150);
        String selectedTemplateDirectoryButtonText = props.getProperty(CSGManagerProp.CD_SELECTED_TEMPLATE_DIRECTORY_BUTTON_TEXT.toString());        
        selectTemplateDirectoryButton= new Button(selectedTemplateDirectoryButtonText);

        String sitePagesLabelText = props.getProperty(CSGManagerProp.CD_SITE_PAGES_LABEL_TEXT.toString());        
        sitePagesLabel= new Label(sitePagesLabelText);
        sitePagesLabel.setMinWidth(150);

        //Table for Site Pages
        sitePageTable= new TableView();
        sitePageTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        CSGData data = (CSGData) app.getDataComponent();
        ObservableList<SitePage> sitePage= data.getSitePages();
        sitePageTable.setItems(sitePage);
        sitePageTable.setEditable(true);
        
        //SET UP COLUMNS
        String useColumnText= props.getProperty(CSGManagerProp.CD_USE_COLUMN_TEXT.toString());
        String navBarColumnText= props.getProperty(CSGManagerProp.CD_NAV_BAR_COLUMN_TEXT.toString());
        String fileNameColumnText= props.getProperty(CSGManagerProp.CD_FILE_NAME_COLUMN_TEXT.toString());
        String scriptColumnText= props.getProperty(CSGManagerProp.CD_SCRIPT_COLUMN_TEXT.toString());

        useColumn= new TableColumn(useColumnText);
        navBarColumn= new TableColumn(navBarColumnText);
        fileNameColumn= new TableColumn(fileNameColumnText);
        scriptColumn= new TableColumn(scriptColumnText);
        
        useColumn.setCellValueFactory(
                new PropertyValueFactory<SitePage,Boolean>("Use")
        );
        useColumn.setCellFactory( CheckBoxTableCell.forTableColumn(useColumn) );
        
        
        navBarColumn.setCellValueFactory(
                new PropertyValueFactory<SitePage, String>("navbarTitle")
        );
        
        
        fileNameColumn.setCellValueFactory(
                new PropertyValueFactory<SitePage, String>("fileName")
        );
        scriptColumn.setCellValueFactory(
                new PropertyValueFactory<SitePage, String>("script")
        );
        
        sitePageTable.getColumns().add(useColumn);
        sitePageTable.getColumns().add(navBarColumn);
        sitePageTable.getColumns().add(fileNameColumn);
        sitePageTable.getColumns().add(scriptColumn);

         // Column Width
        useColumn.prefWidthProperty().bind(sitePageTable.widthProperty().divide(4));
        navBarColumn.prefWidthProperty().bind(sitePageTable.widthProperty().divide(4));
        fileNameColumn.prefWidthProperty().bind(sitePageTable.widthProperty().divide(4));
        scriptColumn.prefWidthProperty().bind(sitePageTable.widthProperty().divide(4));
        


         siteTemplateVBox.getChildren().addAll(siteTemplateLabel,selectedDirectoryDirectionsLabel,
                    selectedDirectoryLabel,selectTemplateDirectoryButton,sitePagesLabel,sitePageTable);

         

        //PageStyle Portion VBox
        pageStyleImageVBox= new VBox();

        //Page Style Header
        String pageStyleLabelText= props.getProperty(CSGManagerProp.CD_PAGE_STYLE_LABEL_TEXT.toString());
        pageStyleLabel= new Label(pageStyleLabelText);
        pageStyleLabel.setMinWidth(150);

        // Banner School Image HBox
        bannerschoolImageHBox= new HBox();
        String bannerSchoolImageLabelText= props.getProperty(CSGManagerProp.CD_CHANGE_BANNER_SCHOOL_LABEL_TEXT.toString());
        bannerSchoolImageLabel= new Label(bannerSchoolImageLabelText);
        bannerSchoolImageLabel.setMinWidth(175);
        String changeBannerSchoolButtonText= props.getProperty(CSGManagerProp.CD_CHANGE_BANNER_SCHOOL_BUTTON_TEXT.toString());
        changeBannerSchoolButton= new Button(changeBannerSchoolButtonText);
//        File file = new File();
//        Image image = new Image(file.toURI().toString());
        bannerSchoolImage= new ImageView();
        bannerSchoolImage.setFitWidth(150);

        bannerschoolImageHBox.getChildren().addAll(bannerSchoolImageLabel,bannerSchoolImage,changeBannerSchoolButton);

        // Left Footer Image HBox
        leftFooterHBox= new HBox();
        String leftFooterImageLabelText= props.getProperty(CSGManagerProp.CD_LEFT_FOOT_IMAGE_LABEL_TEXT.toString());
        leftFooterImageLabel= new Label(leftFooterImageLabelText);
        leftFooterImageLabel.setMinWidth(175);
        String leftFooterImageButtonText= props.getProperty(CSGManagerProp.CD_LEFT_FOOT_IMAGE_BUTTON_TEXT.toString());
        leftFooterImageButton= new Button(leftFooterImageButtonText);
//      File file = new File();
//      Image image = new Image(file.toURI().toString());
        leftFooterImage= new ImageView();
        leftFooterImage.setFitWidth(150);

        leftFooterHBox.getChildren().addAll(leftFooterImageLabel,leftFooterImage,leftFooterImageButton);


        //Right Footer Image HBox
        rightFooterHBox= new HBox();
        String rightFooterImageLabelText= props.getProperty(CSGManagerProp.CD_RIGHT_FOOT_IMAGE_LABEL_TEXT.toString());
        rightFooterImageLabel= new Label(rightFooterImageLabelText);
        rightFooterImageLabel.setMinWidth(175);
        String rightFooterImageButtonText= props.getProperty(CSGManagerProp.CD_RIGHT_FOOT_IMAGE_BUTTON_TEXT.toString());
        rightFooterImageButton= new Button(rightFooterImageButtonText);
//      File file = new File();
//      Image image = new Image(file.toURI().toString());
        rightFooterImage= new ImageView();
        rightFooterImage.setFitWidth(150);

        rightFooterHBox.getChildren().addAll(rightFooterImageLabel,rightFooterImage,rightFooterImageButton);


        //Style sheet HBox
        styleSheetHBox= new HBox();
        String styleSheetLabelText= props.getProperty(CSGManagerProp.CD_STYLE_SHEET_LABEL_TEXT.toString());
        styleSheetLabel= new Label(styleSheetLabelText);
        styleSheetLabel.setMinWidth(175);
        styleSheetComboBox= new ComboBox();
        styleSheetComboBox.setMinWidth(150);

        styleSheetHBox.getChildren().addAll(styleSheetLabel,styleSheetComboBox);
        
        //Bottom Note
        String noteLabelText= props.getProperty(CSGManagerProp.CD_NOTE_LABEL_TEXT.toString());
        noteLabel= new Label();
        noteLabel.setMinWidth(150);

        //Set Up Page Style
        pageStyleImageVBox.getChildren().addAll(pageStyleLabel,bannerschoolImageHBox,leftFooterHBox,rightFooterHBox,
                styleSheetHBox,noteLabel);
        
        //add sepeare boxes to overall Course Detail Tab
        courseDetailsVBox= new VBox();
        courseDetailsVBox.getChildren().addAll(courseInfoVBox,siteTemplateVBox,pageStyleImageVBox);
        

        
        courseScrollPane= new ScrollPane(courseDetailsVBox);
        courseDetailsTab.setContent(courseScrollPane);
        
        courseScrollPane.setFitToWidth(true);




    }
    
    void taDataTabInit(){
        //******************        TA DATA TAB Initialization      ************
        //**********************************************************************
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CSGData data = (CSGData) app.getDataComponent();

        // INIT THE HEADER ON THE LEFT
        tasHeaderBox = new HBox();
        String tasHeaderText = props.getProperty(CSGManagerProp.TAS_HEADER_TEXT.toString());
        tasHeaderLabel = new Label(tasHeaderText);
        tasHeaderBox.getChildren().add(tasHeaderLabel);

        // MAKE THE TABLE AND SETUP THE DATA MODEL
        taTable = new TableView();
        taTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ObservableList<TeachingAssistant> tableData = data.getTeachingAssistants();
        taTable.setItems(tableData);
        String nameColumnText = props.getProperty(CSGManagerProp.NAME_COLUMN_TEXT.toString());
        String emailColumnText = props.getProperty(CSGManagerProp.EMAIL_COLUMN_TEXT.toString());
        String undergradColumnText = props.getProperty(CSGManagerProp.UNDERGRAD_COLUMN_TEXT.toString());

        nameColumn = new TableColumn(nameColumnText);
        emailColumn = new TableColumn(emailColumnText);
        undergradCheckBoxColumn = new TableColumn(undergradColumnText);
        
        undergradCheckBoxColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant,Boolean>("undergrad")
        
        );
        undergradCheckBoxColumn.setCellFactory( CheckBoxTableCell.forTableColumn(undergradCheckBoxColumn) );
        

        
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("name")
        );
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("email")
        );
        taTable.getColumns().add(nameColumn);
        taTable.getColumns().add(emailColumn);
        taTable.getColumns().add(undergradCheckBoxColumn);
        taTable.setEditable(true);

        // Column Width
        nameColumn.prefWidthProperty().bind(taTable.widthProperty().divide(3));
        emailColumn.prefWidthProperty().bind(taTable.widthProperty().divide(3));
        undergradCheckBoxColumn.prefWidthProperty().bind(taTable.widthProperty().divide(3));
        
        
        // ADD BOX FOR ADDING A TA
        String namePromptText = props.getProperty(CSGManagerProp.NAME_PROMPT_TEXT.toString());
        String emailPromptText = props.getProperty(CSGManagerProp.EMAIL_PROMPT_TEXT.toString());
        String addButtonText = props.getProperty(CSGManagerProp.ADD_BUTTON_TEXT.toString());
        String clearButtonText = props.getProperty(CSGManagerProp.CLEAR_BUTTON_TEXT.toString());
        nameTextField = new TextField();
        emailTextField = new TextField();
        nameTextField.setPromptText(namePromptText);
        emailTextField.setPromptText(emailPromptText);
        addButton = new Button(addButtonText);
        clearButton = new Button(clearButtonText);
        addBox = new HBox();
        nameTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.2));
        emailTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.2));
        addButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.17));
        clearButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.17));
        addBox.getChildren().add(nameTextField);
        addBox.getChildren().add(emailTextField);
        addBox.getChildren().add(addButton);
        addBox.getChildren().add(clearButton);

        // INIT THE HEADER ON THE RIGHT
        officeHoursHeaderBox = new HBox();
        String officeHoursGridText = props.getProperty(CSGManagerProp.OFFICE_HOURS_SUBHEADER.toString());
        officeHoursHeaderLabel = new Label(officeHoursGridText);
        officeHoursHeaderLabel.setMinWidth(200);
        officeHoursHeaderBox.getChildren().add(officeHoursHeaderLabel);
        
        // THESE WILL STORE PANES AND LABELS FOR OUR OFFICE HOURS GRID
        officeHoursGridPane = new GridPane();
        officeHoursGridTimeHeaderPanes = new HashMap();
        officeHoursGridTimeHeaderLabels = new HashMap();
        officeHoursGridDayHeaderPanes = new HashMap();
        officeHoursGridDayHeaderLabels = new HashMap();
        officeHoursGridTimeCellPanes = new HashMap();
        officeHoursGridTimeCellLabels = new HashMap();
        officeHoursGridTACellPanes = new HashMap();
        officeHoursGridTACellLabels = new HashMap();

        // ORGANIZE THE LEFT AND RIGHT PANES
        leftPane = new VBox();        
        leftPane.getChildren().add(tasHeaderBox);        
        leftPane.getChildren().add(taTable);        
        leftPane.getChildren().add(addBox);
        rightPane = new VBox();
        rightPane.getChildren().add(officeHoursHeaderBox);
        
        
        
        time_options = FXCollections.observableArrayList(props.getProperty(CSGManagerProp.TIME_12AM.toString()),
        props.getProperty(CSGManagerProp.TIME_1AM.toString()),
        props.getProperty(CSGManagerProp.TIME_2AM.toString()),
        props.getProperty(CSGManagerProp.TIME_3AM.toString()),
        props.getProperty(CSGManagerProp.TIME_4AM.toString()),
        props.getProperty(CSGManagerProp.TIME_5AM.toString()),
        props.getProperty(CSGManagerProp.TIME_6AM.toString()),
        props.getProperty(CSGManagerProp.TIME_7AM.toString()),
        props.getProperty(CSGManagerProp.TIME_8AM.toString()),
        props.getProperty(CSGManagerProp.TIME_9AM.toString()),
        props.getProperty(CSGManagerProp.TIME_10AM.toString()),
        props.getProperty(CSGManagerProp.TIME_11AM.toString()),
        props.getProperty(CSGManagerProp.TIME_12PM.toString()),
        props.getProperty(CSGManagerProp.TIME_1PM.toString()),
        props.getProperty(CSGManagerProp.TIME_2PM.toString()),
        props.getProperty(CSGManagerProp.TIME_3PM.toString()),
        props.getProperty(CSGManagerProp.TIME_4PM.toString()),
        props.getProperty(CSGManagerProp.TIME_5PM.toString()),
        props.getProperty(CSGManagerProp.TIME_6PM.toString()),
        props.getProperty(CSGManagerProp.TIME_7PM.toString()),
        props.getProperty(CSGManagerProp.TIME_8PM.toString()),
        props.getProperty(CSGManagerProp.TIME_9PM.toString()),
        props.getProperty(CSGManagerProp.TIME_10PM.toString()),
        props.getProperty(CSGManagerProp.TIME_11PM.toString())
        );
        comboBox1 = new ComboBox(time_options);
        comboBox2 = new ComboBox(time_options);
        
        officeHoursHeaderBox.getChildren().add(comboBox1);
        comboBox1.getSelectionModel().select(data.getStartHour());
        comboBox1.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                if(t != null && t1 != null)
                    if(comboBox1.getSelectionModel().getSelectedIndex() != data.getStartHour())
                        controller.changeTime();
            }
        });
        officeHoursHeaderBox.getChildren().add(comboBox2);
        comboBox2.getSelectionModel().select(data.getEndHour());
        comboBox2.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                if(t != null && t1 != null)
                    if(comboBox2.getSelectionModel().getSelectedIndex() != data.getEndHour())
                        controller.changeTime();
            }    
        });
        rightPane.getChildren().add(officeHoursGridPane);
        
        // BOTH PANES WILL NOW GO IN A SPLIT PANE
        taDataSplitPane = new HBox(leftPane, new ScrollPane(rightPane));
        taDataSplitPane.setSpacing(15);
        taDataSplitPane.setStyle("-fx-border-color: #F2E4F9");        

        
        leftPane.prefWidthProperty().bind(taDataSplitPane.widthProperty().multiply(.5));
        rightPane.prefWidthProperty().bind(taDataSplitPane.widthProperty().multiply(.5));
        leftPane.setStyle("-fx-border-color: #F2E4F9");        
        rightPane.setStyle("-fx-border-color: #F2E4F9");
        //ADD THE SPLIT PANE INTO THE TAB
        taDataTab.setContent(taDataSplitPane);
        

        
        
        
    }
    
    void recitationDataTabInit(){
        //******************        Recitation DATA TAB Initialization      ************

        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CSGData data = (CSGData) app.getDataComponent();

       //create header
       recitationHeaderHBox= new HBox();
       String recitationsLabelText = props.getProperty(CSGManagerProp.RD_RECITATION_LABEL_TEXT.toString());
       recitationsLabel= new Label(recitationsLabelText);

       String recitationDeleteButtonText = props.getProperty(CSGManagerProp.RD_RECITATION_BUTTON_TEXT.toString());
       recitationDeleteButton= new Button(recitationDeleteButtonText);
       
       recitationHeaderHBox.getChildren().addAll(recitationsLabel,recitationDeleteButton);

       ///initialize table 
       recitationDataTable= new TableView();
       recitationDataTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
       ObservableList<Recitation> recitationsList = data.getRecitationList();
       recitationDataTable.setItems(recitationsList);


       String sectionColumnText= props.getProperty(CSGManagerProp.RD_SECTION_COLUMN_TEXT.toString());
       String instructorColumnText= props.getProperty(CSGManagerProp.RD_INSTRUCTOR_COLUMN_TEXT.toString());
       String dayTimeColumnText= props.getProperty(CSGManagerProp.RD_DAYTIME_COLUMN_TEXT.toString());
       String locationColumnText= props.getProperty(CSGManagerProp.RD_LOCATION_COLUMN_TEXT.toString());
       String firstTAColumnText= props.getProperty(CSGManagerProp.RD_FIRST_TA_COLUMN_TEXT.toString());
       String secondTAColumnText= props.getProperty(CSGManagerProp.RD_SECOND_TA_COLUMN_TEXT.toString());


       sectionColumn= new TableColumn(sectionColumnText);
       instructorColumn= new TableColumn(instructorColumnText);
       dayTimeColumn= new TableColumn(dayTimeColumnText);
       locationColumn= new TableColumn(locationColumnText);
       firstTAColumn= new TableColumn(firstTAColumnText);
       secondTAColumn= new TableColumn(secondTAColumnText);

        sectionColumn.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("Section")
        );
        instructorColumn.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("Instructor")
        );
        dayTimeColumn.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("dayTime")
        );
        locationColumn.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("Location")
        );
        firstTAColumn.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("supervisingTAOne")
        );
        secondTAColumn.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("supervisingTATwo")
        );
        
       recitationDataTable.getColumns().add(sectionColumn);
       recitationDataTable.getColumns().add(instructorColumn);
       recitationDataTable.getColumns().add(dayTimeColumn);
       recitationDataTable.getColumns().add(locationColumn);
       recitationDataTable.getColumns().add(firstTAColumn);
       recitationDataTable.getColumns().add(secondTAColumn);
       
        // Column Width
        sectionColumn.prefWidthProperty().bind(recitationDataTable.widthProperty().divide(6));
        instructorColumn.prefWidthProperty().bind(recitationDataTable.widthProperty().divide(6));
        dayTimeColumn.prefWidthProperty().bind(recitationDataTable.widthProperty().divide(6));
        locationColumn.prefWidthProperty().bind(recitationDataTable.widthProperty().divide(6));
        firstTAColumn.prefWidthProperty().bind(recitationDataTable.widthProperty().divide(6));
        secondTAColumn.prefWidthProperty().bind(recitationDataTable.widthProperty().divide(6));

       //create top half of tab
       //Top portion of RecitationDataVBox
       topRecitationDataVBox= new VBox();
       
       topRecitationDataVBox.getChildren().addAll(recitationHeaderHBox,recitationDataTable);

       
       // for the bottom half of the 
       bottomRecitationDataVBox= new VBox();
       
       String rdSectionHeaderLabelText = props.getProperty(CSGManagerProp.RD_SECTION_HEADER_LABEL_TEXT.toString());
       rdSectionHeaderLabel= new Label(rdSectionHeaderLabelText);
       rdSectionHeaderLabel.setMinWidth(150);


       rdSectionHBox= new HBox();
       String rdSectionLabelText = props.getProperty(CSGManagerProp.RD_SECTION_LABEL_TEXT.toString());
       rdSectionLabel= new Label(rdSectionLabelText);
       rdSectionLabel.setMinWidth(150);
       String rdSectionTextFieldText = props.getProperty(CSGManagerProp.RD_SECTION_TEXTFIELD_TEXT.toString());
       rdSectionTextField= new TextField(rdSectionTextFieldText);
       rdSectionTextField.setMinWidth(250);
       rdSectionHBox.getChildren().addAll(rdSectionLabel,rdSectionTextField);

       
       rdInstructorHBox= new HBox();
       String rdInstructorLabelText = props.getProperty(CSGManagerProp.RD_INSTRUCTOR_LABEL_TEXT.toString());
       rdInstructorLabel= new Label(rdInstructorLabelText);
       rdInstructorLabel.setMinWidth(150);
       String rdInstructorTextFieldText = props.getProperty(CSGManagerProp.RD_INSTRUCTOR_TEXTFIELD_TEXT.toString());
       rdInstructorTextField= new TextField(rdInstructorTextFieldText);
       rdInstructorTextField.setMinWidth(250);
       rdInstructorHBox.getChildren().addAll(rdInstructorLabel,rdInstructorTextField);    
       
       rdDayTimeHBox= new HBox();
       String rdDayTimeLabelText = props.getProperty(CSGManagerProp.RD_DAYTIME_LABEL_TEXT.toString());
       rdDayTimeLabel= new Label(rdDayTimeLabelText);
       rdDayTimeLabel.setMinWidth(150);
       String rdDayTimeTextFieldText = props.getProperty(CSGManagerProp.RD_DAYTIME_TEXTFIELD_TEXT.toString());
       rdDayTimeTextField= new TextField(rdDayTimeTextFieldText);
       rdDayTimeTextField.setMinWidth(250);
       rdDayTimeHBox.getChildren().addAll(rdDayTimeLabel,rdDayTimeTextField);

       rdLocationHBox= new HBox();
       String rdLocationLabelText = props.getProperty(CSGManagerProp.RD_LOCATION_LABEL_TEXT.toString());
       rdLocationLabel= new Label(rdLocationLabelText);
       rdLocationLabel.setMinWidth(150);
       String rdLocationTextFieldText = props.getProperty(CSGManagerProp.RD_LOCATION_TEXTFIELD_TEXT.toString());
       rdLocationTextField= new TextField(rdLocationTextFieldText);
       rdLocationTextField.setMinWidth(250);
       rdLocationHBox.getChildren().addAll(rdLocationLabel,rdLocationTextField);

       rdFirstTaHBox= new HBox();
       String rdFirstTaLabelText = props.getProperty(CSGManagerProp.RD_FIRST_TA_LABEL_TEXT.toString());
       rdFirstTaLabel= new Label(rdFirstTaLabelText);
       rdFirstTaLabel.setMinWidth(150);
       rdFirstTaComboBox= new ComboBox<TeachingAssistant>();
       rdFirstTaComboBox.setItems(data.getTeachingAssistants());
       rdFirstTaComboBox.setMinWidth(150);
       rdFirstTaHBox.getChildren().addAll(rdFirstTaLabel,rdFirstTaComboBox);

       rdSecondTaHbox= new HBox();
       String rdSecondTaLabelText = props.getProperty(CSGManagerProp.RD_SECOND_TA_LABEL_TEXT.toString());
       rdSecondTaLabel= new Label(rdSecondTaLabelText);
       rdSecondTaLabel.setMinWidth(150);
       rdSecondTaComboBox= new ComboBox<TeachingAssistant>();
       rdSecondTaComboBox.setItems(data.getTeachingAssistants());
       rdSecondTaComboBox.setMinWidth(150);
       rdSecondTaHbox.getChildren().addAll(rdSecondTaLabel,rdSecondTaComboBox);

       rdButtonsHbox= new HBox();
       String rdAddButtonText = props.getProperty(CSGManagerProp.RD_ADD_BUTTON_TEXT.toString());
       rdAddButton= new Button(rdAddButtonText);
       rdAddButton.setMinWidth(150);

       String rdClearButtonText = props.getProperty(CSGManagerProp.RD_CLEAR_BUTTON_TEXT.toString());
       rdClearButton= new Button(rdClearButtonText);
       rdClearButton.setMinWidth(150);
       rdButtonsHbox.getChildren().addAll(rdAddButton,rdClearButton);
        
       
       //add all nodes to bottom VBox

       bottomRecitationDataVBox.getChildren().addAll(rdSectionHeaderLabel,rdSectionHBox,rdInstructorHBox,rdDayTimeHBox,rdLocationHBox,rdFirstTaHBox,rdSecondTaHbox,rdButtonsHbox);

        
//     Construct tab, Hold all of Recitation Data
       recitationsDataVBox= new VBox();
       recitationsDataVBox.getChildren().addAll(topRecitationDataVBox,bottomRecitationDataVBox);
  



        recitationsScrollPane= new ScrollPane(recitationsDataVBox);
        recitationsScrollPane.setFitToWidth(true);
        
        //set tab content
        recitationDataTab.setContent(recitationsScrollPane);
//        recitationsDataVBox.prefWidthProperty().bind(recitationsScrollPane.widthProperty().multiply(.75));
        

    }
    
    void scheduleDataTabInit(){

       //******************       SCHEDULE DATA TAB Initialization      ********
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CSGData data = (CSGData) app.getDataComponent();
       
        scheduleDataTabVBox= new VBox();

        //will be at the top of scheduleDataTabVBox
        String sdScheduleHeaderLabelText = props.getProperty(CSGManagerProp.SD_SCHEDULE_HEADER_TEXT.toString());
        sdScheduleHeaderLabel= new Label(sdScheduleHeaderLabelText);


        //TOP decleration
        sdTopVBox= new VBox();
        String sdCalendarBoundariesLabelText = props.getProperty(CSGManagerProp.SD_CALENDAR_BOUNDARY_LABEL_TEXT.toString());
        sdCalendarBoundariesLabel= new Label(sdCalendarBoundariesLabelText);


        //Following two HBox's go into sdCalendarPickerHBox
        sdCalendarPickerHBox= new HBox();

        sdStartingMondayPickerHBox= new HBox();
        String sdStartingMondayLabelText = props.getProperty(CSGManagerProp.SD_STARTING_MONDAY_LABEL_TEXT.toString());
        sdStartingMondayLabel= new Label(sdStartingMondayLabelText);
        sdStartingMondayLabel.setMinWidth(150);
        startingMondayDatePicker= new DatePicker();
        startingMondayDatePicker.setMinWidth(150);
        sdStartingMondayPickerHBox.getChildren().addAll(sdStartingMondayLabel,startingMondayDatePicker);

        sdEndingFridayPickerHBox= new HBox();
        String sdEndingFridayLabelText = props.getProperty(CSGManagerProp.SD_ENDING_FRIDAY_LABEL_TEXT.toString());
        sdEndingFridayLabel= new Label(sdEndingFridayLabelText);
        sdEndingFridayLabel.setMinWidth(150);
        endingFridayDatePicker= new DatePicker();
        endingFridayDatePicker.setMinWidth(150);
        sdEndingFridayPickerHBox.getChildren().addAll(sdEndingFridayLabel,endingFridayDatePicker);

        //put both date picker boxes into one HBox
        sdCalendarPickerHBox.getChildren().addAll(sdStartingMondayPickerHBox,sdEndingFridayPickerHBox);
        
        sdTopVBox.getChildren().addAll(sdCalendarBoundariesLabel,sdCalendarPickerHBox);

        //Schedule Items VBoc
        sdBottomVBox= new VBox();

        sdScheduleItemsHeaderHBox= new HBox();
        String scheduleItemsLabelText = props.getProperty(CSGManagerProp.SD_SCHEDULE_ITEMS_LABEL_TEXT.toString());
        scheduleItemsLabel= new Label(scheduleItemsLabelText);
        scheduleItemsLabel.setMinWidth(150);
        String scheduleItemsDeleteButtonText = props.getProperty(CSGManagerProp.SD_SCHEDULE_DELETE_BUTTON_TEXT.toString());
        scheduleItemsDeleteButton= new Button(scheduleItemsDeleteButtonText);

        sdScheduleItemsHeaderHBox.getChildren().addAll(scheduleItemsLabel,scheduleItemsDeleteButton);
        //table view initialization

        //set up table columns
        String typeColumnText= props.getProperty(CSGManagerProp.SD_TYPE_COLUMN_TEXT.toString());
        String dateColumnText= props.getProperty(CSGManagerProp.SD_DATE_COLUMN_TEXT.toString());
        String titleColumnText= props.getProperty(CSGManagerProp.SD_TITLE_COLUMN_TEXT.toString());
        String topicColumnText= props.getProperty(CSGManagerProp.SD_TOPIC_COLUMN_TEXT.toString());

        scheduleItemsTable= new TableView();
        scheduleItemsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ObservableList<ScheduleItem> scheduleItemsList = data.getScheduleItemsList();
        scheduleItemsTable.setItems(scheduleItemsList);

        
        typeColumn= new TableColumn(typeColumnText);
        dateColumn= new TableColumn(dateColumnText);
        titleColumn= new TableColumn(titleColumnText);
        topicColumn= new TableColumn(topicColumnText);
        
        typeColumn.setCellValueFactory(
                new PropertyValueFactory<ScheduleItem, String>("Type")
        );
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<ScheduleItem, String>("Date")
        );
        titleColumn.setCellValueFactory(
                new PropertyValueFactory<ScheduleItem, String>("Title")
        );
        topicColumn.setCellValueFactory(
                new PropertyValueFactory<ScheduleItem, String>("Topic")
        );
        
        //add up table columns
        scheduleItemsTable.getColumns().add(typeColumn);
        scheduleItemsTable.getColumns().add(dateColumn);
        scheduleItemsTable.getColumns().add(titleColumn);
        scheduleItemsTable.getColumns().add(topicColumn);
        // Column Width
        typeColumn.prefWidthProperty().bind(scheduleItemsTable.widthProperty().divide(4));
        dateColumn.prefWidthProperty().bind(scheduleItemsTable.widthProperty().divide(4));
        titleColumn.prefWidthProperty().bind(scheduleItemsTable.widthProperty().divide(4));
        topicColumn.prefWidthProperty().bind(scheduleItemsTable.widthProperty().divide(4));

        //filler hbox
        sdFillerHBox= new HBox();
        
        String ssdAddEditLabelText = props.getProperty(CSGManagerProp.SD_ADD_EDIT_LABEL_TEXT.toString());
        sdAddEditLabel= new Label(ssdAddEditLabelText);

        sdTypeHBox= new HBox();
        String sdTypeLabelText = props.getProperty(CSGManagerProp.SD_TYPE_LABEL_TEXT.toString());
        sdTypeLabel= new Label(sdTypeLabelText);
        sdTypeLabel.setMinWidth(150);
        type_options= FXCollections.observableArrayList(props.getProperty(CSGManagerProp.HOLIDAY.toString()),
                    props.getProperty(CSGManagerProp.HW.toString()),
                    props.getProperty(CSGManagerProp.LECTURE.toString()),
                    props.getProperty(CSGManagerProp.RECITATION.toString()),
                    props.getProperty(CSGManagerProp.LECTURE.toString()),
                    props.getProperty(CSGManagerProp.TEST.toString()));

        sdTypeComboBox= new ComboBox(type_options);
        sdTypeHBox.getChildren().addAll(sdTypeLabel,sdTypeComboBox);
        sdTypeHBox.setMinWidth(175);
        
        sdDateHBox= new HBox();
        String sdDateLabelText = props.getProperty(CSGManagerProp.SD_DATE_LABEL_TEXT.toString());
        sdDateLabel= new Label(sdDateLabelText);
        sdDateLabel.setMinWidth(150);
        sdDatePicker= new DatePicker();
        sdDatePicker.setMinWidth(150);
        sdDateHBox.getChildren().addAll(sdDateLabel,sdDatePicker);

        sdTimeHBox= new HBox();
        String sdTimeLabelText = props.getProperty(CSGManagerProp.SD_TIME_LABEL_TEXT.toString());
        sdTimeLabel= new Label(sdTimeLabelText);
        sdTimeLabel.setMinWidth(150);
        String sdTimeTextFieldText = props.getProperty(CSGManagerProp.SD_TIME_TEXTFIELD_TEXT.toString());
        sdTimeTextField=new TextField(sdTimeTextFieldText);
        sdTimeTextField.setMinWidth(250);
        sdTimeHBox.getChildren().addAll(sdTimeLabel,sdTimeTextField);

        sdTitleHBox= new HBox();
        String sdTitleLabelText = props.getProperty(CSGManagerProp.SD_TITLE_LABEL_TEXT.toString());
        sdTitleLabel= new Label(sdTitleLabelText);
        sdTitleLabel.setMinWidth(150);
        String sdTitleTextFieldText = props.getProperty(CSGManagerProp.SD_TITLE_TEXTFIELD_TEXT.toString());
        sdTitleTextField=new TextField(sdTitleTextFieldText);
        sdTitleTextField.setMinWidth(250);
        sdTitleHBox.getChildren().addAll(sdTitleLabel,sdTitleTextField);

        sdTopicHBox= new HBox();
        String sdTopicLabelText = props.getProperty(CSGManagerProp.SD_TOPIC_LABEL_TEXT.toString());
        sdTopicLabel= new Label(sdTopicLabelText);
        sdTopicLabel.setMinWidth(150);
        String sdTopicTextFieldText = props.getProperty(CSGManagerProp.SD_TOPIC_TEXTFIELD_TEXT.toString());
        sdTopicTextField=new TextField(sdTopicTextFieldText);
        sdTopicTextField.setMinWidth(250);
        sdTopicHBox.getChildren().addAll(sdTopicLabel,sdTopicTextField);

        sdLinkHBox= new HBox();
        String sdLinkLabelText = props.getProperty(CSGManagerProp.SD_LINK_LABEL_TEXT.toString());
        sdLinkLabel= new Label(sdLinkLabelText);
        sdLinkLabel.setMinWidth(150);
        String sdLinkTextFieldText = props.getProperty(CSGManagerProp.SD_LINK_TEXTFIELD_TEXT.toString());
        sdLinkTextField=new TextField(sdLinkTextFieldText);
        sdLinkTextField.setMinWidth(250);
        sdLinkHBox.getChildren().addAll(sdLinkLabel,sdLinkTextField);

        sdCriteriaHBox= new HBox();
        String sdCriteriaLabelText = props.getProperty(CSGManagerProp.SD_CRITERIA_LABEL_TEXT.toString());
        sdCriteriaLabel= new Label(sdCriteriaLabelText);
        sdCriteriaLabel.setMinWidth(150);
        String sdCriteriaTextFieldText = props.getProperty(CSGManagerProp.SD_CRITERA_TEXTFIELD_TEXT.toString());
        sdCriteriaTextField=new TextField(sdCriteriaTextFieldText);
        sdCriteriaTextField.setMinWidth(250);
        sdCriteriaHBox.getChildren().addAll(sdCriteriaLabel,sdCriteriaTextField);


        sdButtonsHbox= new HBox();
        String sdAddUpdateButtonText = props.getProperty(CSGManagerProp.SD_ADD_UPDATE_BUTTON_TEXT.toString());
        sdAddUpdateButton= new Button(sdAddUpdateButtonText);
        sdAddUpdateButton.setMinWidth(150);
        String sdClearButtonText = props.getProperty(CSGManagerProp.SD_CLEAR_BUTTON_TEXT.toString());
        sdClearButton= new Button(sdClearButtonText);
        sdClearButton.setMinWidth(50);
        sdButtonsHbox.getChildren().addAll(sdAddUpdateButton,sdClearButton);

        //set up bottom Box
        sdBottomVBox.getChildren().addAll(sdScheduleItemsHeaderHBox,scheduleItemsTable,sdFillerHBox,sdAddEditLabel,sdTypeHBox,sdDateHBox,sdTimeHBox,sdTitleHBox,sdTopicHBox,sdLinkHBox,sdCriteriaHBox,sdButtonsHbox);
        
        
        
        scheduleDataTabVBox.getChildren().addAll(sdScheduleHeaderLabel,sdTopVBox,sdBottomVBox);
        
        scheduleDataScrollPane= new ScrollPane(scheduleDataTabVBox);
        //set SChedule tab to VBOX
        
        scheduleDataTab.setContent(scheduleDataScrollPane);
        scheduleDataScrollPane.setFitToWidth(true);


    } 
    
    void projectDataTabInit(){
       //******************       PROJECT DATA TAB Initialization      *********
       //*********************************************************************** 
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CSGData data = (CSGData) app.getDataComponent();

        projectDataVBox=new VBox();
        
        //hold everything in the top
        pdTopVBox= new VBox();
        
        String pdProjectsHeaderLabelText = props.getProperty(CSGManagerProp.PD_PROJECT_HEADER_LABEL_TEXT.toString());
	pdProjectsHeaderLabel= new Label(pdProjectsHeaderLabelText);

	pdTeamHeaderHBox= new HBox();
	String pdTeamLabelText = props.getProperty(CSGManagerProp.PD_TEAM_LABEL_TEXT.toString());
	pdTeamLabel= new Label(pdTeamLabelText);
	String pdTeamDeleteButtonButtonText = props.getProperty(CSGManagerProp.PD_TEAM_DELETE_BUTTON_TEXT.toString());
	pdTeamDeleteButton= new Button(pdTeamDeleteButtonButtonText);
        pdTeamHeaderHBox.getChildren().addAll(pdTeamLabel,pdTeamDeleteButton);
                
	//set up table
	String pdNameColumnText= props.getProperty(CSGManagerProp.PD_NAME_COLUMN_TEXT.toString());
        String pdColorColumnText= props.getProperty(CSGManagerProp.PD_COLOR_COLUMN_TEXT.toString());
        String pdTextColorColumnText= props.getProperty(CSGManagerProp.PD_TEXT_COLOR_COLUMN_TEXT.toString());
        String pdLinkColumnText= props.getProperty(CSGManagerProp.PD_LINK_COLUMN_TEXT.toString());

        pdTeamTableView= new TableView();
        pdTeamTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ObservableList<Team> teamData = data.getTeamList();
        pdTeamTableView.setItems(teamData);
        pdTeamTableView.setEditable(true);
        
        
        pdNameColumn= new TableColumn(pdNameColumnText);
        pdColorColumn= new TableColumn(pdColorColumnText);
        pdTextColorColumn= new TableColumn(pdTextColorColumnText);
        pdLinkColumn= new TableColumn(pdLinkColumnText);

        pdNameColumn.setCellValueFactory(
                new PropertyValueFactory<Team, String>("Name")
        );
        pdColorColumn.setCellValueFactory(
                new PropertyValueFactory<Team, String>("Color")
        );
        pdTextColorColumn.setCellValueFactory(
                new PropertyValueFactory<Team, String>("textColor")
        );
        pdLinkColumn.setCellValueFactory(
                new PropertyValueFactory<Team, String>("Link")
        );
        //add up table columns
        pdTeamTableView.getColumns().add(pdNameColumn);
        pdTeamTableView.getColumns().add(pdColorColumn);
        pdTeamTableView.getColumns().add(pdTextColorColumn);
        pdTeamTableView.getColumns().add(pdLinkColumn);

        // Column Width
        pdNameColumn.prefWidthProperty().bind(pdTeamTableView.widthProperty().divide(4));
        pdColorColumn.prefWidthProperty().bind(pdTeamTableView.widthProperty().divide(4));
        pdTextColorColumn.prefWidthProperty().bind(pdTeamTableView.widthProperty().divide(4));
        pdLinkColumn.prefWidthProperty().bind(pdTeamTableView.widthProperty().divide(4));


        //filler hbox between table and colors
        pdFillerHbox=new HBox();

        // add/edit color portion of top of Projects Tab
 	String pdTopAddEditLabelText = props.getProperty(CSGManagerProp.PD_TOP_ADD_EDIT_LABEL_TEXT.toString());
        pdTopAddEditLabel=new Label(pdTopAddEditLabelText);

	pdTopNameHBox= new HBox();
	String pdTopNameLabelText = props.getProperty(CSGManagerProp.PD_TOP_NAME_LABEL_TEXT.toString());
	pdTopNameLabel= new Label(pdTopNameLabelText);
        pdTopNameLabel.setMinWidth(150);
	String pdTopNameTextFieldText = props.getProperty(CSGManagerProp.PD_TOP_NAME_TEXT_FIELD_TEXT.toString());
	pdTopNameTextField=new TextField(pdTopNameTextFieldText);
        pdTopNameTextField.setMinWidth(250);
        pdTopNameHBox.getChildren().addAll(pdTopNameLabel,pdTopNameTextField);

	pdTopColorHBox= new HBox();

   	//left side
   	pdColorHBox= new HBox();
   	String pdColorLabelText = props.getProperty(CSGManagerProp.PD_COLOR_TEXT.toString());
 	pdColorLabel= new Label(pdColorLabelText);
        pdColorLabel.setMinWidth(150);
        pdColorPicker= new ColorPicker();
        pdColorHBox.getChildren().addAll(pdColorLabel,pdColorPicker);


        //right side
        pdTextColorHBox= new HBox();
   	String pdTextColorLabelText = props.getProperty(CSGManagerProp.PD_TEXT_COLOR_TEXT.toString());
	pdTextColorLabel= new Label(pdTextColorLabelText);
        pdTextColorLabel.setMinWidth(150);
        pdTextColorPicker= new ColorPicker();
        pdTextColorHBox.getChildren().addAll(pdTextColorLabel,pdTextColorPicker);

	pdTopColorHBox.getChildren().addAll(pdColorHBox,pdTextColorHBox);


	pdTopLinkHBox= new HBox();
	String pdTopLinkLabelText = props.getProperty(CSGManagerProp.PD_TOP_LINK_LABEL_TEXT.toString());
	pdTopLinkLabel= new Label(pdTopLinkLabelText);
        pdTopLinkLabel.setMinWidth(150);
	String pdTopLinkTextFieldText = props.getProperty(CSGManagerProp.PD_TOP_LINK_TEXT_FIELD_TEXT.toString());
	pdTopLinkTextField=new TextField(pdTopLinkTextFieldText);
        pdTopLinkTextField.setMinWidth(250);
        pdTopLinkHBox.getChildren().addAll(pdTopLinkLabel,pdTopLinkTextField);

	pdTopAddUpdateButtonHBox= new HBox();
	String pdTopAddUpdateButtonText = props.getProperty(CSGManagerProp.PD_TOP_ADD_UPDATE_BUTTON_TEXT.toString());
	pdTopAddUpdateButton= new Button(pdTopAddUpdateButtonText);
	String pdTopClearButtonText = props.getProperty(CSGManagerProp.PD_TOP_CLEAR_BUTTON_TEXT.toString());
	pdTopClearButton= new Button(pdTopClearButtonText);
        pdTopAddUpdateButtonHBox.getChildren().addAll(pdTopAddUpdateButton,pdTopClearButton);

        
	//		Bottom portion of Projects Data Tab
        pdBottomVBox= new VBox();

        pdStudentTableHeaderHbox= new HBox();
	String pdStudentHeaderLabelText = props.getProperty(CSGManagerProp.PD_STUDENT_HEADER_LABEL_TEXT.toString());
	pdStudentHeaderLabel= new Label(pdStudentHeaderLabelText);
        pdStudentHeaderLabel.setMinWidth(150);
	String pdStudentDeleteButtonText = props.getProperty(CSGManagerProp.PD_STUDENT_DELETE_BUTTON_TEXT.toString());
	pdStudentDeleteButton= new Button(pdStudentDeleteButtonText);
        pdStudentTableHeaderHbox.getChildren().addAll(pdStudentHeaderLabel,pdStudentDeleteButton);
         
        //Students Table
	String pdFirstNameColumnText= props.getProperty(CSGManagerProp.PD_FIRST_NAME_COLUMN_TEXT.toString());
        String pdLastNameColumnText= props.getProperty(CSGManagerProp.PD_LAST_NAME_COLUMN_TEXT.toString());
        String pdTeamColumnText= props.getProperty(CSGManagerProp.PD_TEAM_COLUMN_TEXT.toString());
        String pdRoleColumnText= props.getProperty(CSGManagerProp.PD_ROLE_COLUMN_TEXT.toString());

        pdStudentTable= new TableView();
        pdStudentTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ObservableList<Student> studentData = data.getStudents();
        pdStudentTable.setItems(studentData);
        pdStudentTable.setEditable(true);
        
        
        pdFirstNameColumn= new TableColumn(pdFirstNameColumnText);
        pdLastNameColumn= new TableColumn(pdLastNameColumnText);
        pdTeamColumn= new TableColumn(pdTeamColumnText);
        pdRoleColumn= new TableColumn(pdRoleColumnText);

        pdFirstNameColumn.setCellValueFactory(
                new PropertyValueFactory<Student, String>("firstName")
        );
        pdLastNameColumn.setCellValueFactory(
                new PropertyValueFactory<Student, String>("lastName")
        );
        pdTeamColumn.setCellValueFactory(
                new PropertyValueFactory<Student, String>("Team")
        );
        pdRoleColumn.setCellValueFactory(
                new PropertyValueFactory<Student, String>("Role")
        );
        
        
        //add up table columns
        pdStudentTable.getColumns().add(pdFirstNameColumn);
        pdStudentTable.getColumns().add(pdLastNameColumn);
        pdStudentTable.getColumns().add(pdTeamColumn);
        pdStudentTable.getColumns().add(pdRoleColumn);

        // Column Width
        pdFirstNameColumn.prefWidthProperty().bind(pdStudentTable.widthProperty().divide(4));
        pdLastNameColumn.prefWidthProperty().bind(pdStudentTable.widthProperty().divide(4));
        pdTeamColumn.prefWidthProperty().bind(pdStudentTable.widthProperty().divide(4));
        pdRoleColumn.prefWidthProperty().bind(pdStudentTable.widthProperty().divide(4));

        //Add/Edit Label 
        String pdAddEditLabelText= props.getProperty(CSGManagerProp.PD_ADD_EDIT_LABELTEXT.toString());
        pdAddEditLabel= new Label(pdAddEditLabelText);

        //Add/edit fields
  	pdFirstNameHBox= new HBox();
	String pdFirsttNameLabelText = props.getProperty(CSGManagerProp.PD_FIRST_NAME_LABEL_TEXT.toString());
	pdFirstNameLabel= new Label(pdFirsttNameLabelText);
        pdFirstNameLabel.setMinWidth(150);
	String pdFirsttNameTextFieldText = props.getProperty(CSGManagerProp.PD_FIRST_NAME_TEXTFIELD_TEXT.toString());
	pdFirstNameTextField=new TextField(pdFirsttNameTextFieldText);
        pdFirstNameTextField.setMinWidth(250);
        pdFirstNameHBox.getChildren().addAll(pdFirstNameLabel,pdFirstNameTextField);

        pdLastNameHBox= new HBox();
    	String pdLastNameLabelText = props.getProperty(CSGManagerProp.PD_LAST_NAME_LABEL_TEXT.toString());
	pdLastNameLabel= new Label(pdLastNameLabelText);
        pdLastNameLabel.setMinWidth(150);
	String pdLastNameTextFieldText = props.getProperty(CSGManagerProp.PD_LAST_NAME_TEXTFIELD_TEXT.toString());
	pdLastNameTextField=new TextField(pdLastNameTextFieldText);
        pdLastNameTextField.setMinWidth(250);
        pdLastNameHBox.getChildren().addAll(pdLastNameLabel,pdLastNameTextField);


	pdBottomTeamHBox= new HBox();
	String pdBottomTeamLabelText = props.getProperty(CSGManagerProp.PD_BOTTOM_TEAM_LABEL_TEXT.toString());
	pdBottomTeamLabel= new Label(pdBottomTeamLabelText);
        pdBottomTeamLabel.setMinWidth(150);
	team_options = FXCollections.observableArrayList(props.getProperty(CSGManagerProp.TEAM_ONE.toString()),
        props.getProperty(CSGManagerProp.TEAM_TWO.toString()));
        pdBottomTeamComboBox= new ComboBox<Team>();
	pdBottomTeamComboBox.setItems(data.getTeamList());
        pdBottomTeamComboBox.setMinWidth(250);
        pdBottomTeamHBox.getChildren().addAll(pdBottomTeamLabel,pdBottomTeamComboBox);


	pdRoleHBox= new HBox();
	String pdRoleLabelText = props.getProperty(CSGManagerProp.PD_ROLE_LABEL_TEXT.toString());
	pdRoleLabel= new Label(pdRoleLabelText);
        pdRoleLabel.setMinWidth(150);
	String pdRoleTextFieldText = props.getProperty(CSGManagerProp.PD_ROLE_TEXTFIELD_TEXT.toString());
	pdRoleTextField=new TextField(pdRoleTextFieldText);
        pdRoleTextField.setMinWidth(250);
        pdRoleHBox.getChildren().addAll(pdRoleLabel,pdRoleTextField);


	pdButtonHBox= new HBox();
	String pdAddUpdateButtonText = props.getProperty(CSGManagerProp.PD_ADD_UPDATE_BUTTON_TEXT.toString());
	pdAddUpdateButton= new Button(pdAddUpdateButtonText);
	String pdClearButtonText = props.getProperty(CSGManagerProp.PD_CLEAR_BUTTON_TEXT.toString());
	pdClearButton= new Button(pdClearButtonText);
        pdButtonHBox.getChildren().addAll(pdAddUpdateButton,pdClearButton);

        pdBottomVBox.getChildren().addAll(pdStudentTableHeaderHbox,pdStudentTable,pdAddEditLabel,pdFirstNameHBox,pdLastNameHBox,
        pdBottomTeamHBox,pdRoleHBox,pdButtonHBox);
       
        //Add everything to VBox
        pdTopVBox.getChildren().addAll(pdProjectsHeaderLabel,pdTeamHeaderHBox,
                pdTeamTableView,pdFillerHbox,pdTopAddEditLabel,pdTopNameHBox,pdTopColorHBox,
                pdTopLinkHBox,pdTopAddUpdateButtonHBox);
       
        projectDataVBox.getChildren().addAll(pdTopVBox,pdBottomVBox);
        
        
        projectDataScrollPane= new ScrollPane(projectDataVBox);
        //add to tab
        projectDataTab.setContent(projectDataScrollPane);
        
        projectDataScrollPane.setFitToWidth(true);

        
        
        
        
    }
    
    
    
    
    // WE'LL PROVIDE AN GETTERS METHOD FOR EACH VISIBLE COMPONENT
    
    
    public HBox getTAsHeaderBox() {
        return tasHeaderBox;
    }

    public Label getTAsHeaderLabel() {
        return tasHeaderLabel;
    }

    public TableView getTATable() {
        return taTable;
    }

    public HBox getAddBox() {
        return addBox;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public TextField getEmailTextField() {
        return emailTextField;
    }

    public Button getAddButton() {
        return addButton;
    }
    
    public Button getClearButton() {
        return clearButton;
    }

    public HBox getOfficeHoursSubheaderBox() {
        return officeHoursHeaderBox;
    }

    public Label getOfficeHoursSubheaderLabel() {
        return officeHoursHeaderLabel;
    }

    public GridPane getOfficeHoursGridPane() {
        return officeHoursGridPane;
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeHeaderPanes() {
        return officeHoursGridTimeHeaderPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTimeHeaderLabels() {
        return officeHoursGridTimeHeaderLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridDayHeaderPanes() {
        return officeHoursGridDayHeaderPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridDayHeaderLabels() {
        return officeHoursGridDayHeaderLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeCellPanes() {
        return officeHoursGridTimeCellPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTimeCellLabels() {
        return officeHoursGridTimeCellLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridTACellPanes() {
        return officeHoursGridTACellPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTACellLabels() {
        return officeHoursGridTACellLabels;
    }
    
    public String getCellKey(Pane testPane) {
        for (String key : officeHoursGridTACellLabels.keySet()) {
            if (officeHoursGridTACellPanes.get(key) == testPane) {
                return key;
            }
        }
        return null;
    }

    public Label getTACellLabel(String cellKey) {
        return officeHoursGridTACellLabels.get(cellKey);
    }

    public Pane getTACellPane(String cellPane) {
        return officeHoursGridTACellPanes.get(cellPane);
    }
    
    public ComboBox getOfficeHour(boolean start){
        if(start)
            return comboBox1;
        return comboBox2;
    }

    public String buildCellKey(int col, int row) {
        return "" + col + "_" + row;
    }

    public String buildCellText(int militaryHour, String minutes) {
        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        String cellText = "" + hour + ":" + minutes;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }

    @Override
    public void resetWorkspace() {
        // CLEAR OUT THE GRID PANE
        officeHoursGridPane.getChildren().clear();
        
        // AND THEN ALL THE GRID PANES AND LABELS
        officeHoursGridTimeHeaderPanes.clear();
        officeHoursGridTimeHeaderLabels.clear();
        officeHoursGridDayHeaderPanes.clear();
        officeHoursGridDayHeaderLabels.clear();
        officeHoursGridTimeCellPanes.clear();
        officeHoursGridTimeCellLabels.clear();
        officeHoursGridTACellPanes.clear();
        officeHoursGridTACellLabels.clear();
    }
    
    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        CSGData taData = (CSGData)dataComponent;
        reloadOfficeHoursGrid(taData);
    }

    public void reloadOfficeHoursGrid(CSGData dataComponent) {        
        ArrayList<String> gridHeaders = dataComponent.getGridHeaders();

        // ADD THE TIME HEADERS
        for (int i = 0; i < 2; i++) {
            addCellToGrid(dataComponent, officeHoursGridTimeHeaderPanes, officeHoursGridTimeHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));
        }
        
        // THEN THE DAY OF WEEK HEADERS
        for (int i = 2; i < 7; i++) {
            addCellToGrid(dataComponent, officeHoursGridDayHeaderPanes, officeHoursGridDayHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));            
        }
        
        // THEN THE TIME AND TA CELLS
        int row = 1;
        for (int i = dataComponent.getStartHour(); i < dataComponent.getEndHour(); i++) {
            // START TIME COLUMN
            int col = 0;
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(i, "00"));
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row+1);
            dataComponent.getCellTextProperty(col, row+1).set(buildCellText(i, "30"));

            // END TIME COLUMN
            col++;
            int endHour = i;
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(endHour, "30"));
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row+1);
            dataComponent.getCellTextProperty(col, row+1).set(buildCellText(endHour+1, "00"));
            col++;

            // AND NOW ALL THE TA TOGGLE CELLS
            while (col < 7) {
                addCellToGrid(dataComponent, officeHoursGridTACellPanes, officeHoursGridTACellLabels, col, row);
                addCellToGrid(dataComponent, officeHoursGridTACellPanes, officeHoursGridTACellLabels, col, row+1);
                col++;
            }
            row += 2;
        }

        // CONTROLS FOR TOGGLING TA OFFICE HOURS
        for (Pane p : officeHoursGridTACellPanes.values()) {
            p.setFocusTraversable(true);
            p.setOnKeyPressed(e -> {
                controller.handleKeyPress(e.getCode());
            });
            p.setOnMouseClicked(e -> {
                controller.handleCellToggle((Pane) e.getSource());
            });
            p.setOnMouseExited(e -> {
                controller.handleGridCellMouseExited((Pane) e.getSource());
            });
            p.setOnMouseEntered(e -> {
                controller.handleGridCellMouseEntered((Pane) e.getSource());
            });
        }
        
        // AND MAKE SURE ALL THE COMPONENTS HAVE THE PROPER STYLE
        CSGStyle taStyle = (CSGStyle)app.getStyleComponent();
        taStyle.initOfficeHoursGridStyle();
    }
    
    public void addCellToGrid(CSGData dataComponent, HashMap<String, Pane> panes, HashMap<String, Label> labels, int col, int row) {       
        // MAKE THE LABEL IN A PANE
        Label cellLabel = new Label("");
        HBox cellPane = new HBox();
        cellPane.setAlignment(Pos.CENTER);
        cellPane.getChildren().add(cellLabel);

        // BUILD A KEY TO EASILY UNIQUELY IDENTIFY THE CELL
        String cellKey = dataComponent.getCellKey(col, row);
        cellPane.setId(cellKey);
        cellLabel.setId(cellKey);
        
        // NOW PUT THE CELL IN THE WORKSPACE GRID
        officeHoursGridPane.add(cellPane, col, row);
        
        // AND ALSO KEEP IN IN CASE WE NEED TO STYLIZE IT
        panes.put(cellKey, cellPane);
        labels.put(cellKey, cellLabel);
        
        // AND FINALLY, GIVE THE TEXT PROPERTY TO THE DATA MANAGER
        // SO IT CAN MANAGE ALL CHANGES
        dataComponent.setCellProperty(col, row, cellLabel.textProperty());        
    }


    //***********           MY GETTERS         *****************
    
    //TAB GETTERS
    public TabPane getWorkspaceTabs(){
        return   workspaceTabs;

    }

    public Tab getCourseDetailsTab() {
        return courseDetailsTab;
    }

    public Tab getTaDataTab() {
        return taDataTab;
    }

    public Tab getScheduleDataTab() {
        return scheduleDataTab;
    }

    public Tab getProjectDataTab() {
        return projectDataTab;
    }
		

    public CSGManagerApp getApp() {
        return app;
    }

    public boolean isAdd() {
        return add;
    }

    public CSGController getController() {
        return controller;
    }

    public Tab getRecitationDataTab() {
        return recitationDataTab;
    }

    public VBox getCourseDetailsVBox() {
        return courseDetailsVBox;
    }

    public VBox getCourseInfoVBox() {
        return courseInfoVBox;
    }

    public Label getCourseInfoLabel() {
        return courseInfoLabel;
    }

    public HBox getSubjectAndNumberHBox() {
        return subjectAndNumberHBox;
    }

    public HBox getSubjectHBox() {
        return subjectHBox;
    }

    public Label getSubjectLabel() {
        return subjectLabel;
    }

    public ComboBox getSubjectComboBox() {
        return subjectComboBox;
    }

    public ObservableList<String> getSubject_options() {
        return subject_options;
    }

    public HBox getNumberHBox() {
        return numberHBox;
    }

    public Label getNumberLabel() {
        return numberLabel;
    }

    public ComboBox getNumberComboBox() {
        return numberComboBox;
    }

    public ObservableList<String> getNumber_options() {
        return number_options;
    }

    public HBox getSemesterAndYearHBox() {
        return semesterAndYearHBox;
    }

    public HBox getSemesterHBox() {
        return semesterHBox;
    }

    public Label getSemesterLabel() {
        return semesterLabel;
    }

    public ComboBox getSemesterComboBox() {
        return semesterComboBox;
    }

    public ObservableList<String> getSemester_options() {
        return semester_options;
    }

    public HBox getYearHbox() {
        return yearHbox;
    }

    public Label getYearLabel() {
        return yearLabel;
    }

    public ComboBox getYearComboBox() {
        return yearComboBox;
    }

    public ObservableList<String> getYear_options() {
        return year_options;
    }

    public HBox getCdTitleHBox() {
        return cdTitleHBox;
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public TextField getTileTextField() {
        return tileTextField;
    }

    public HBox getCdInstructorNameHBox() {
        return cdInstructorNameHBox;
    }

    public Label getInstructorNameLabel() {
        return instructorNameLabel;
    }

    public TextField getInstructorNameTextField() {
        return instructorNameTextField;
    }

    public HBox getCdInstructorHomeHBox() {
        return cdInstructorHomeHBox;
    }

    public Label getInstructorHomeLabel() {
        return instructorHomeLabel;
    }

    public TextField getInstructorHomeTextField() {
        return instructorHomeTextField;
    }

    public HBox getCdExportHBox() {
        return cdExportHBox;
    }

    public Label getExportDirLabel() {
        return exportDirLabel;
    }

    public Button getChangeDirButton() {
        return changeDirButton;
    }

    public VBox getSiteTemplateVBox() {
        return siteTemplateVBox;
    }

    public Label getSiteTemplateLabel() {
        return siteTemplateLabel;
    }

    public Label getSelectedDirectoryDirectionsLabel() {
        return selectedDirectoryDirectionsLabel;
    }

    public Label getSelectedDirectoryLabel() {
        return selectedDirectoryLabel;
    }

    public Button getSelectTemplateDirectoryButton() {
        return selectTemplateDirectoryButton;
    }

    public Label getSitePagesLabel() {
        return sitePagesLabel;
    }

    public TableView<SitePage> getSitePageTable() {
        return sitePageTable;
    }

    public TableColumn<SitePage, Boolean> getUseColumn() {
        return useColumn;
    }

    public TableColumn<SitePage, String> getNavBarColumn() {
        return navBarColumn;
    }

    public TableColumn<SitePage, String> getFileNameColumn() {
        return fileNameColumn;
    }

    public TableColumn<SitePage, String> getScriptColumn() {
        return scriptColumn;
    }

    public VBox getPageStyleImageVBox() {
        return pageStyleImageVBox;
    }

    public Label getPageStyleLabel() {
        return pageStyleLabel;
    }

    public HBox getBannerschoolImageHBox() {
        return bannerschoolImageHBox;
    }

    public Label getBannerSchoolImageLabel() {
        return bannerSchoolImageLabel;
    }

    public Button getChangeBannerSchoolButton() {
        return changeBannerSchoolButton;
    }

    public ImageView getBannerSchoolImage() {
        return bannerSchoolImage;
    }

    public HBox getLeftFooterHBox() {
        return leftFooterHBox;
    }

    public Label getLeftFooterImageLabel() {
        return leftFooterImageLabel;
    }

    public Button getLeftFooterImageButton() {
        return leftFooterImageButton;
    }

    public ImageView getLeftFooterImage() {
        return leftFooterImage;
    }

    public HBox getRightFooterHBox() {
        return rightFooterHBox;
    }

    public Label getRightFooterImageLabel() {
        return rightFooterImageLabel;
    }

    public Button getRightFooterImageButton() {
        return rightFooterImageButton;
    }

    public ImageView getRightFooterImage() {
        return rightFooterImage;
    }

    public HBox getStyleSheetHBox() {
        return styleSheetHBox;
    }

    public Label getStyleSheetLabel() {
        return styleSheetLabel;
    }

    public ComboBox getStyleSheetComboBox() {
        return styleSheetComboBox;
    }

    public Label getNoteLabel() {
        return noteLabel;
    }

    public HBox getTasHeaderBox() {
        return tasHeaderBox;
    }

    public Label getTasHeaderLabel() {
        return tasHeaderLabel;
    }

    public Button getTasHeaderDeleteButton() {
        return tasHeaderDeleteButton;
    }

    public TableView<TeachingAssistant> getTaTable() {
        return taTable;
    }

    public TableColumn<TeachingAssistant, String> getNameColumn() {
        return nameColumn;
    }

    public TableColumn<TeachingAssistant, String> getEmailColumn() {
        return emailColumn;
    }

    public HBox getOfficeHoursHeaderBox() {
        return officeHoursHeaderBox;
    }

    public Label getOfficeHoursHeaderLabel() {
        return officeHoursHeaderLabel;
    }

    public ObservableList<String> getTime_options() {
        return time_options;
    }

    public ComboBox getComboBox1() {
        return comboBox1;
    }

    public ComboBox getComboBox2() {
        return comboBox2;
    }

    public VBox getRecitationsDataVBox() {
        return recitationsDataVBox;
    }

    public VBox getTopRecitationDataVBox() {
        return topRecitationDataVBox;
    }

    public HBox getRecitationHeaderHBox() {
        return recitationHeaderHBox;
    }

    public Label getRecitationsLabel() {
        return recitationsLabel;
    }

    public Button getRecitationDeleteButton() {
        return recitationDeleteButton;
    }

    public TableView<Recitation> getRecitationDataTable() {
        return recitationDataTable;
    }

    public TableColumn<Recitation, String> getSectionColumn() {
        return sectionColumn;
    }

    public TableColumn<Recitation, String> getInstructorColumn() {
        return instructorColumn;
    }

    public TableColumn<Recitation, String> getDayTimeColumn() {
        return dayTimeColumn;
    }

    public TableColumn<Recitation, String> getLocationColumn() {
        return locationColumn;
    }

    public TableColumn<Recitation, String> getFirstTAColumn() {
        return firstTAColumn;
    }

    public TableColumn<Recitation, String> getSecondTAColumn() {
        return secondTAColumn;
    }

    public VBox getBottomRecitationDataVBox() {
        return bottomRecitationDataVBox;
    }
    
    public ScrollPane getRecitationScrollPane(){
        
        return recitationsScrollPane;
    }

    public Label getRdSectionHeaderLabel() {
        return rdSectionHeaderLabel;
    }

    public HBox getRdSectionHBox() {
        return rdSectionHBox;
    }

    public Label getRdSectionLabel() {
        return rdSectionLabel;
    }

    public TextField getRdSectionTextField() {
        return rdSectionTextField;
    }

    public HBox getRdInstructorHBox() {
        return rdInstructorHBox;
    }

    public Label getRdInstructorLabel() {
        return rdInstructorLabel;
    }

    public TextField getRdInstructorTextField() {
        return rdInstructorTextField;
    }

    public HBox getRdDayTimeHBox() {
        return rdDayTimeHBox;
    }

    public Label getRdDayTimeLabel() {
        return rdDayTimeLabel;
    }

    public TextField getRdDayTimeTextField() {
        return rdDayTimeTextField;
    }

    public HBox getRdLocationHBox() {
        return rdLocationHBox;
    }

    public Label getRdLocationLabel() {
        return rdLocationLabel;
    }

    public TextField getRdLocationTextField() {
        return rdLocationTextField;
    }

    public HBox getRdFirstTaHBox() {
        return rdFirstTaHBox;
    }

    public Label getRdFirstTaLabel() {
        return rdFirstTaLabel;
    }

    public ComboBox getRdFirstTaComboBox() {
        return rdFirstTaComboBox;
    }

    public HBox getRdSecondTaHbox() {
        return rdSecondTaHbox;
    }

    public Label getRdSecondTaLabel() {
        return rdSecondTaLabel;
    }

    public ComboBox getRdSecondTaComboBox() {
        return rdSecondTaComboBox;
    }

    public HBox getRdButtonsHbox() {
        return rdButtonsHbox;
    }

    public Button getRdAddButton() {
        return rdAddButton;
    }

    public Button getRdClearButton() {
        return rdClearButton;
    }

    public VBox getScheduleDataTabVBox() {
        return scheduleDataTabVBox;
    }

    public Label getSdScheduleHeaderLabel() {
        return sdScheduleHeaderLabel;
    }

    public VBox getSdTopVBox() {
        return sdTopVBox;
    }

    public Label getSdCalendarBoundariesLabel() {
        return sdCalendarBoundariesLabel;
    }

    public HBox getSdCalendarPickerHBox() {
        return sdCalendarPickerHBox;
    }

    public HBox getSdStartingMondayPickerHBox() {
        return sdStartingMondayPickerHBox;
    }

    public Label getSdStartingMondayLabel() {
        return sdStartingMondayLabel;
    }

    public DatePicker getStartingMondayDatePicker() {
        return startingMondayDatePicker;
    }

    public HBox getSdEndingFridayPickerHBox() {
        return sdEndingFridayPickerHBox;
    }

    public Label getSdEndingFridayLabel() {
        return sdEndingFridayLabel;
    }

    public DatePicker getEndingFridayDatePicker() {
        return endingFridayDatePicker;
    }

    public VBox getSdBottomVBox() {
        return sdBottomVBox;
    }

    public HBox getSdScheduleItemsHeaderHBox() {
        return sdScheduleItemsHeaderHBox;
    }

    public Label getScheduleItemsLabel() {
        return scheduleItemsLabel;
    }

    public Button getScheduleItemsDeleteButton() {
        return scheduleItemsDeleteButton;
    }

    public TableView<ScheduleItem> getScheduleItemsTable() {
        return scheduleItemsTable;
    }

    public TableColumn<ScheduleItem, String> getTypeColumn() {
        return typeColumn;
    }

    public TableColumn<ScheduleItem, String> getDateColumn() {
        return dateColumn;
    }

    public TableColumn<ScheduleItem, String> getTitleColumn() {
        return titleColumn;
    }

    public TableColumn<ScheduleItem, String> getTopicColumn() {
        return topicColumn;
    }

    public HBox getSdFillerHBox() {
        return sdFillerHBox;
    }

    public Label getSdAddEditLabel() {
        return sdAddEditLabel;
    }

    public HBox getSdTypeHBox() {
        return sdTypeHBox;
    }

    public Label getSdTypeLabel() {
        return sdTypeLabel;
    }

    public ObservableList<String> getType_options() {
        return type_options;
    }

    public ComboBox getSdTypeComboBox() {
        return sdTypeComboBox;
    }

    public HBox getSdDateHBox() {
        return sdDateHBox;
    }

    public Label getSdDateLabel() {
        return sdDateLabel;
    }

    public DatePicker getSdDatePicker() {
        return sdDatePicker;
    }

    public HBox getSdTimeHBox() {
        return sdTimeHBox;
    }

    public Label getSdTimeLabel() {
        return sdTimeLabel;
    }

    public TextField getSdTimeTextField() {
        return sdTimeTextField;
    }

    public HBox getSdTitleHBox() {
        return sdTitleHBox;
    }

    public Label getSdTitleLabel() {
        return sdTitleLabel;
    }

    public TextField getSdTitleTextField() {
        return sdTitleTextField;
    }

    public HBox getSdTopicHBox() {
        return sdTopicHBox;
    }

    public Label getSdTopicLabel() {
        return sdTopicLabel;
    }

    public TextField getSdTopicTextField() {
        return sdTopicTextField;
    }

    public HBox getSdLinkHBox() {
        return sdLinkHBox;
    }

    public Label getSdLinkLabel() {
        return sdLinkLabel;
    }

    public TextField getSdLinkTextField() {
        return sdLinkTextField;
    }

    public HBox getSdCriteriaHBox() {
        return sdCriteriaHBox;
    }

    public Label getSdCriteriaLabel() {
        return sdCriteriaLabel;
    }

    public TextField getSdCriteriaTextField() {
        return sdCriteriaTextField;
    }

    public HBox getSdButtonsHbox() {
        return sdButtonsHbox;
    }

    public Button getSdAddUpdateButton() {
        return sdAddUpdateButton;
    }

    public Button getSdClearButton() {
        return sdClearButton;
    }

    public VBox getProjectDataVBox() {
        return projectDataVBox;
    }

    public VBox getPdTopVBox() {
        return pdTopVBox;
    }

    public Label getPdProjectsHeaderLabel() {
        return pdProjectsHeaderLabel;
    }

    public HBox getPdTeamHeaderHBox() {
        return pdTeamHeaderHBox;
    }

    public Label getPdTeamLabel() {
        return pdTeamLabel;
    }

    public Button getPdTeamDeleteButton() {
        return pdTeamDeleteButton;
    }

    public TableView<Team> getPdTeamTableView() {
        return pdTeamTableView;
    }

    public TableColumn<Team, String> getPdNameColumn() {
        return pdNameColumn;
    }

    public TableColumn<Team, String> getPdColorColumn() {
        return pdColorColumn;
    }

    public TableColumn<Team, String> getPdTextColorColumn() {
        return pdTextColorColumn;
    }

    public TableColumn<Team, String> getPdLinkColumn() {
        return pdLinkColumn;
    }

    public HBox getPdFillerHbox() {
        return pdFillerHbox;
    }

    public Label getPdTopAddEditLabel() {
        return pdTopAddEditLabel;
    }

    public HBox getPdTopNameHBox() {
        return pdTopNameHBox;
    }

    public Label getPdTopNameLabel() {
        return pdTopNameLabel;
    }

    public TextField getPdTopNameTextField() {
        return pdTopNameTextField;
    }

    public HBox getPdTopColorHBox() {
        return pdTopColorHBox;
    }

    public HBox getPdColorHBox() {
        return pdColorHBox;
    }

    public Label getPdColorLabel() {
        return pdColorLabel;
    }

    public ColorPicker getPdColorPicker() {
        return pdColorPicker;
    }

    public HBox getPdTextColorHBox() {
        return pdTextColorHBox;
    }

    public Label getPdTextColorLabel() {
        return pdTextColorLabel;
    }

    public ColorPicker getPdTextColorPicker() {
        return pdTextColorPicker;
    }

    public HBox getPdTopLinkHBox() {
        return pdTopLinkHBox;
    }

    public Label getPdTopLinkLabel() {
        return pdTopLinkLabel;
    }

    public TextField getPdTopLinkTextField() {
        return pdTopLinkTextField;
    }

    public HBox getPdTopAddUpdateButtonHBox() {
        return pdTopAddUpdateButtonHBox;
    }

    public Button getPdTopAddUpdateButton() {
        return pdTopAddUpdateButton;
    }

    public Button getPdTopClearButton() {
        return pdTopClearButton;
    }

    public VBox getPdBottomVBox() {
        return pdBottomVBox;
    }

    public HBox getPdStudentTableHeaderHbox() {
        return pdStudentTableHeaderHbox;
    }

    public Label getPdStudentHeaderLabel() {
        return pdStudentHeaderLabel;
    }

    public Button getPdStudentDeleteButton() {
        return pdStudentDeleteButton;
    }

    public TableView<Student> getPdStudentTable() {
        return pdStudentTable;
    }

    public TableColumn<Student, String> getPdFirstNameColumn() {
        return pdFirstNameColumn;
    }

    public TableColumn<Student, String> getPdLastNameColumn() {
        return pdLastNameColumn;
    }

    public TableColumn<Student, String> getPdTeamColumn() {
        return pdTeamColumn;
    }

    public TableColumn<Student, String> getPdRoleColumn() {
        return pdRoleColumn;
    }

    public Label getPdAddEditLabel() {
        return pdAddEditLabel;
    }

    public HBox getPdFirstNameHBox() {
        return pdFirstNameHBox;
    }

    public Label getPdFirstNameLabel() {
        return pdFirstNameLabel;
    }

    public TextField getPdFirstNameTextField() {
        return pdFirstNameTextField;
    }

    public HBox getPdLastNameHBox() {
        return pdLastNameHBox;
    }

    public Label getPdLastNameLabel() {
        return pdLastNameLabel;
    }

    public TextField getPdLastNameTextField() {
        return pdLastNameTextField;
    }

    public HBox getPdBottomTeamHBox() {
        return pdBottomTeamHBox;
    }

    public Label getPdBottomTeamLabel() {
        return pdBottomTeamLabel;
    }

    public ObservableList<String> getTeam_options() {
        return team_options;
    }

    public ComboBox getPdBottomTeamComboBox() {
        return pdBottomTeamComboBox;
    }

    public HBox getPdRoleHBox() {
        return pdRoleHBox;
    }

    public Label getPdRoleLabel() {
        return pdRoleLabel;
    }

    public TextField getPdRoleTextField() {
        return pdRoleTextField;
    }

    public HBox getPdButtonHBox() {
        return pdButtonHBox;
    }

    public Button getPdAddUpdateButton() {
        return pdAddUpdateButton;
    }

    public Button getPdClearButton() {
        return pdClearButton;
    }
    
    public ScrollPane getCourseScrollPane(){
        return courseScrollPane;
    }
    
    public TableColumn<TeachingAssistant, Boolean> getUndergradCheckBoxColumn() {
        return undergradCheckBoxColumn;
    }

    public ScrollPane getRecitationsScrollPane() {
        return recitationsScrollPane;
    }

    public ScrollPane getProjectDataScrollPane() {
        return projectDataScrollPane;
    }
    
    public HBox getTaDataSplitPane() {
        return taDataSplitPane;
    }
    
    public VBox getLeftPane() {
        return leftPane;
    }

    public VBox getRightPane() {
        return rightPane;
    }
    
    public VBox getWorkspaceTabPaneVbox() {
        return workspaceTabPaneVbox;
    }

    public ScrollPane getScheduleDataScrollPane() {
        return scheduleDataScrollPane;
    }
    
 
}
