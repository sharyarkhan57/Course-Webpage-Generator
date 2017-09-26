package csg.style;

import djf.AppTemplate;
import djf.components.AppStyleComponent;
import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import csg.data.TeachingAssistant;
import csg.workspace.CSGWorkspace;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class manages all CSS style for this application.
 * 
 * @author Richard McKenna
 * @version 1.0
 */
public class CSGStyle extends AppStyleComponent {
    // FIRST WE SHOULD DECLARE ALL OF THE STYLE TYPES WE PLAN TO USE
    
    //tab
    public static String WORKSPACE_TAB = "tab-pane";
    public static String TAB_VBOX= "tab_vbox";

    public static String COURSE_DETAILS_TAB = "course_detail_tab";

    
    public static String BASIC = "basic";

    
    //Course Details Tab
    public static String Course_Detail_VBox = "course_detail_vbox";
    public static String Course_Scroll_Pane = "course_scroll_pane";

    
    //Course info portion (top)
    public static String Course_Info_VBox = "course_info_vbox";
    public static String Course_Info_Label = "course_info_label";

    //Subject and Number
    public static String Subject_And_Number_HBox = "subject_and_number_hbox";
    public static String Semester_And_Year_HBox = "semester_and_year_hbox";
    public static String cdTitleHBox = "cd_title_hbox";
    public static String cdInstructorNameHBox = "cd_instructor_name_hbox";
    public static String cdInstructorHomeHBox = "cd_instructor_home_hbox";
    public static String cdExportHBox = "cd_export_hbox";

    //Site Template
    public static String Site_Template_VBox = "site_template_vbox";
    public static String Site_Template_Label = "site_template_label";
    public static String Site_Pages_Label = "site_pages_label";
    
    //Page Style
    public static String Page_Style_Image_VBox = "page_style_image_vbox";
    public static String Page_Style_Label = "page_style_label";
    public static String Banner_School_Image_HBox = "banner_school_image_hbox";
    public static String Left_Footer_HBox = "left_footer_hbox";
    public static String Right_Footer_HBox = "right_footer_hbox";
    public static String Style_Sheet_HBox = "style_sheet_hbox";


    //TA DATA TAB
    
    
    // WE'LL USE THIS FOR ORGANIZING LEFT AND RIGHT CONTROLS
    public static String CLASS_PLAIN_PANE = "plain_pane";
    
    // THESE ARE THE HEADERS FOR EACH SIDE
    public static String CLASS_HEADER_PANE = "header_pane";
    public static String CLASS_HEADER_LABEL = "header_label";

    // ON THE LEFT WE HAVE THE TA ENTRY
    public static String CLASS_TA_TABLE = "ta_table";
    public static String CLASS_TA_TABLE_COLUMN_HEADER = "ta_table_column_header";
    public static String CLASS_ADD_TA_PANE = "add_ta_pane";
    public static String CLASS_ADD_TA_TEXT_FIELD = "add_ta_text_field";
    public static String CLASS_ADD_TA_BUTTON = "add_ta_button";

    // ON THE RIGHT WE HAVE THE OFFICE HOURS GRID
    public static String CLASS_OFFICE_HOURS_GRID = "office_hours_grid";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_PANE = "office_hours_grid_time_column_header_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_LABEL = "office_hours_grid_time_column_header_label";
    public static String CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_PANE = "office_hours_grid_day_column_header_pane";
    public static String CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_LABEL = "office_hours_grid_day_column_header_label";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_CELL_PANE = "office_hours_grid_time_cell_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_CELL_LABEL = "office_hours_grid_time_cell_label";
    public static String CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE = "office_hours_grid_ta_cell_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TA_CELL_LABEL = "office_hours_grid_ta_cell_label";

    // FOR HIGHLIGHTING CELLS, COLUMNS, AND ROWS
    public static String CLASS_HIGHLIGHTED_GRID_CELL = "highlighted_grid_cell";
    public static String CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN = "highlighted_grid_row_or_column";
    
    
    
    
    
    //Recitation Data
    public static String RECITATION_SCROLL_PANE = "recitations_scroll_pane";
    public static String RECITATION_HBOX = "recitations_hbox";

    
    public static String RECITATION_HEADER_HBOX = "recitations_label_hbox";
    public static String RECITATIONS_LABEL = "recitation_label";
    public static String RECITATION_DELETE_BUTTON = "recitation_delete_button_text";
    public static String TOP_RECITATION_DATA_VBOX = "top_recitation_data_vbox";
    
    //bottom
    public static String BOTTOM_RECITATION_BOX= "bottom";
    public static String RD_SECTION_HEADER_LABEL= "rd_section_header_label";
    public static String BOTTOM_RECITATION_DATA_VBOX = "bottom_recitation_data_vbox";
    
    public static String BOTTOM_RECITATION_DATA_BOXES = "bottom_recitation_data_boxes";
    
    
    
    
    //Schedule
    public static String SCHEDULE_DATA_TAB_VBOX = "schedule_data_tab_vbox";
    //TOP
    public static String SD_TOP_VBOX = "sd_top_vbox";

    public static String SD_SCHEDULE_HEADER_LABEL = "sd_schedule_header_label";
    public static String SD_CALENDAR_BOUNDARIES_LABEL = "sd_calendar_boundaries_label";
    
    //TOP HBOX
    public static String SD_CALENDAR_PICKER_HBOX = "sd_calendar_picker_hbox";
    //SUB H-BOXES
    public static String SD_STARTING_MONDAY_PICKER_HBOX = "sd_starting_monday_picker_hbox";
    public static String SD_ENDING_FRIDAY_PICKER_HBOX = "sd_ending_friday_picker_hbox";

    //BOTTOM HBOX   
    public static String SD_BOTTOM_VBOX = "sd_bottom_vbox";
    public static String SD_SCHEDULE_ITEMS_HEADER_HBOX = "sd_schedule_items_header_hbox";
    //add/edit label
    public static String SD_ADD_EDIT_LABEL = "sd_add_edit_label";
    //bottom button boxes
    public static String SD_BUTTONS_HBOX = "sd_buttons_hbox";
    
    
    
    //PROJECT DATA
    
    //TOP
    public static String PROJECT_DATA_VBOX = "project_data_vbox";
    public static String PD_TOP_VBOX = "pd_top_vbox";
    public static String PD_PROJECTS_HEADER_LABEL = "pd_projects_header_label";

    //Filler hbox 
    public static String PD_FILLER_HBOX = "pd_filler_hbox";

    //add/edit button
    public static String PD_TOP_ADD_EDIT_LABEL = "pd_top_add_edit_label";

    //top HBOX
    public static String PD_TOP_COLOR_HBOX = "pd_top_color_hbox";

    //top add/update button
    public static String PD_TOP_ADD_UPDATE_BUTTON_HBOX = "pd_top_add_update_button_hbox";

    //bottom
    public static String PD_BOTTOM_VBOX = "pd_bottom_vbox";
    //header
    public static String PD_STUDENT_HEADER_LABEL = "pd_student_header_label";

    
    
    

    // THIS PROVIDES ACCESS TO OTHER COMPONENTS
    private AppTemplate app;
    
    /**
     * This constructor initializes all style for the application.
     * 
     * @param initApp The application to be stylized.
     */
    public CSGStyle(AppTemplate initApp) {
        // KEEP THIS FOR LATER
        app = initApp;

        // LET'S USE THE DEFAULT STYLESHEET SETUP
        super.initStylesheet(app);

        // INIT THE STYLE FOR THE FILE TOOLBAR
        app.getGUI().initFileToolbarStyle();

        // AND NOW OUR WORKSPACE STYLE
        initTAWorkspaceStyle();
    }

    /**
     * This function specifies all the style classes for
     * all user interface controls in the workspace.
     */
    private void initTAWorkspaceStyle() {
        CSGWorkspace workspaceComponent = (CSGWorkspace)app.getWorkspaceComponent();

        //TAB
//        workspaceComponent.getWorkspace().getStyleClass().add(BASIC);
//
        workspaceComponent.getWorkspaceTabPaneVbox().getStyleClass().add(TAB_VBOX);
        workspaceComponent.getWorkspaceTabs().getStyleClass().add(WORKSPACE_TAB);
//        workspaceComponent.getCourseDetailsTab().getStyleClass().add(COURSE_DETAILS_TAB);

        
        workspaceComponent.getTaDataTab().getStyleClass().add(BASIC);
        
        //COURSE DATA
        workspaceComponent.getCourseScrollPane().getStyleClass().add(COURSE_DETAILS_TAB);
        workspaceComponent.getCourseDetailsVBox().getStyleClass().add(Course_Detail_VBox);

        //Top
        workspaceComponent.getCourseInfoVBox().getStyleClass().add(Course_Info_VBox);
        
        workspaceComponent.getCourseInfoLabel().getStyleClass().add(Course_Info_Label);
        
        workspaceComponent.getSubjectAndNumberHBox().getStyleClass().add(Subject_And_Number_HBox);
        workspaceComponent.getSemesterAndYearHBox().getStyleClass().add(Semester_And_Year_HBox);
        workspaceComponent.getCdTitleHBox().getStyleClass().add(cdTitleHBox);
        workspaceComponent.getCdInstructorNameHBox().getStyleClass().add(cdInstructorNameHBox);
        workspaceComponent.getCdInstructorHomeHBox().getStyleClass().add(cdInstructorHomeHBox);
        workspaceComponent.getCdExportHBox().getStyleClass().add(cdExportHBox);
        
        //Site Template
        workspaceComponent.getSiteTemplateVBox().getStyleClass().add(Site_Template_VBox);
        workspaceComponent.getSiteTemplateLabel().getStyleClass().add(Site_Template_Label);
        workspaceComponent.getSiteTemplateVBox().getStyleClass().add(Site_Template_VBox);
        workspaceComponent.getSitePagesLabel().getStyleClass().add(Site_Pages_Label);
        
        //Page Style Header
        workspaceComponent.getPageStyleImageVBox().getStyleClass().add(Page_Style_Image_VBox);
        workspaceComponent.getPageStyleLabel().getStyleClass().add(Page_Style_Label);
        workspaceComponent.getBannerschoolImageHBox().getStyleClass().add(Banner_School_Image_HBox);
        workspaceComponent.getLeftFooterHBox().getStyleClass().add(Left_Footer_HBox);
        workspaceComponent.getRightFooterHBox().getStyleClass().add(Right_Footer_HBox);
        workspaceComponent.getStyleSheetHBox().getStyleClass().add(Style_Sheet_HBox);

        //TA DATA
        workspaceComponent.getTaDataSplitPane().getStyleClass().add(BASIC);


        
        // LEFT SIDE - THE HEADER
        workspaceComponent.getTAsHeaderBox().getStyleClass().add(CLASS_HEADER_PANE);
        workspaceComponent.getTAsHeaderLabel().getStyleClass().add(CLASS_HEADER_LABEL);

        // LEFT SIDE - THE TABLE
        TableView<TeachingAssistant> taTable = workspaceComponent.getTATable();
        taTable.getStyleClass().add(CLASS_TA_TABLE);
        for (TableColumn tableColumn : taTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TA_TABLE_COLUMN_HEADER);
        }

        // LEFT SIDE - THE TA DATA ENTRY
        workspaceComponent.getAddBox().getStyleClass().add(CLASS_ADD_TA_PANE);
        workspaceComponent.getNameTextField().getStyleClass().add(CLASS_ADD_TA_TEXT_FIELD);
        workspaceComponent.getEmailTextField().getStyleClass().add(CLASS_ADD_TA_TEXT_FIELD);
        workspaceComponent.getAddButton().getStyleClass().add(CLASS_ADD_TA_BUTTON);
        workspaceComponent.getClearButton().getStyleClass().add(CLASS_ADD_TA_BUTTON);

        // RIGHT SIDE - THE HEADER
        workspaceComponent.getOfficeHoursSubheaderBox().getStyleClass().add(CLASS_HEADER_PANE);
        workspaceComponent.getOfficeHoursSubheaderLabel().getStyleClass().add(CLASS_HEADER_LABEL);
        
        //HBox
        workspaceComponent.getLeftPane().getStyleClass().add(BASIC);
        workspaceComponent.getRightPane().getStyleClass().add(BASIC);

        
        
        
        //Recitation DATA TAB
        //trying to fix coloring
        workspaceComponent.getRecitationScrollPane().getStyleClass().add(BASIC);
        workspaceComponent.getRecitationsDataVBox().getStyleClass().add(RECITATION_HBOX); 
        
        
        //Top
        workspaceComponent.getRecitationHeaderHBox().getStyleClass().add(RECITATION_HEADER_HBOX);
        workspaceComponent.getRecitationsLabel().getStyleClass().add(RECITATIONS_LABEL);
        //boxes
        workspaceComponent.getTopRecitationDataVBox().getStyleClass().add(TOP_RECITATION_DATA_VBOX);
        workspaceComponent.getBottomRecitationDataVBox().getStyleClass().add(BOTTOM_RECITATION_BOX);

        //Bottom
        workspaceComponent.getRdSectionHeaderLabel().getStyleClass().add(RD_SECTION_HEADER_LABEL);
        workspaceComponent.getRdSectionHeaderLabel().getStyleClass().add(BOTTOM_RECITATION_DATA_VBOX);
        workspaceComponent.getRdSectionHBox().getStyleClass().add(BOTTOM_RECITATION_DATA_VBOX);
        workspaceComponent.getRdInstructorHBox().getStyleClass().add(BOTTOM_RECITATION_DATA_VBOX);
        workspaceComponent.getRdDayTimeHBox().getStyleClass().add(BOTTOM_RECITATION_DATA_VBOX);
        workspaceComponent.getRdLocationHBox().getStyleClass().add(BOTTOM_RECITATION_DATA_VBOX);
        workspaceComponent.getRdSecondTaHbox().getStyleClass().add(BOTTOM_RECITATION_DATA_VBOX);
        workspaceComponent.getRdFirstTaHBox().getStyleClass().add(BOTTOM_RECITATION_DATA_VBOX);
        workspaceComponent.getRdButtonsHbox().getStyleClass().add(BOTTOM_RECITATION_DATA_VBOX);
        
        
        
        //SCHEDULE DATA TAB
        workspaceComponent.getScheduleDataTabVBox().getStyleClass().add(SCHEDULE_DATA_TAB_VBOX);
        //Header SCHEDULE LABEL
        workspaceComponent.getSdScheduleHeaderLabel().getStyleClass().add(SD_SCHEDULE_HEADER_LABEL);
        //TOP V BOX
        workspaceComponent.getSdTopVBox().getStyleClass().add(SD_TOP_VBOX);

        //CALENDAR BOUNDARIES LABEL
        workspaceComponent.getSdCalendarBoundariesLabel().getStyleClass().add(SD_CALENDAR_BOUNDARIES_LABEL);
        
        //HBOX that holds both starting and ending hboxes
        workspaceComponent.getSdCalendarPickerHBox().getStyleClass().add(SD_CALENDAR_PICKER_HBOX);
        //Starting monday HBOX
        workspaceComponent.getSdStartingMondayPickerHBox().getStyleClass().add(SD_STARTING_MONDAY_PICKER_HBOX);
        //Ending Friday HBox
        workspaceComponent.getSdEndingFridayPickerHBox().getStyleClass().add(SD_ENDING_FRIDAY_PICKER_HBOX);

        //BOTTOM HBOX   
        workspaceComponent.getSdBottomVBox().getStyleClass().add(SD_BOTTOM_VBOX);
        workspaceComponent.getSdScheduleItemsHeaderHBox().getStyleClass().add(SD_SCHEDULE_ITEMS_HEADER_HBOX);
        
        //ADD/EDIT LABEL
        workspaceComponent.getSdScheduleItemsHeaderHBox().getStyleClass().add(SD_ADD_EDIT_LABEL);
        //button
        workspaceComponent.getSdButtonsHbox().getStyleClass().add(SD_BUTTONS_HBOX);
        
        
        
        //Project Data
        
        workspaceComponent.getProjectDataVBox().getStyleClass().add(SCHEDULE_DATA_TAB_VBOX);
        //top vbox
        workspaceComponent.getPdTopVBox().getStyleClass().add(PD_TOP_VBOX);
        workspaceComponent.getPdProjectsHeaderLabel().getStyleClass().add(PD_PROJECTS_HEADER_LABEL);
        
        //filler Hbox
        workspaceComponent.getPdFillerHbox().getStyleClass().add(PD_FILLER_HBOX);
        workspaceComponent.getPdTopAddEditLabel().getStyleClass().add(PD_TOP_ADD_EDIT_LABEL);
        //TOP COLOR BOX
        workspaceComponent.getPdTopColorHBox().getStyleClass().add(PD_TOP_COLOR_HBOX);
        workspaceComponent.getPdTopAddUpdateButtonHBox().getStyleClass().add(PD_TOP_ADD_UPDATE_BUTTON_HBOX);
        
        //BOTTOM HBOX
        workspaceComponent.getPdBottomVBox().getStyleClass().add(PD_BOTTOM_VBOX);
        
        //bottom student table header
        workspaceComponent.getPdStudentHeaderLabel().getStyleClass().add(PD_STUDENT_HEADER_LABEL);

        workspaceComponent.getPdButtonHBox().getStyleClass().add(PD_TOP_ADD_UPDATE_BUTTON_HBOX);
        



        
        
    }
    
    /**
     * This method initializes the style for all UI components in
     * the office hours grid. Note that this should be called every
     * time a new TA Office Hours Grid is created or loaded.
     */
    public void initOfficeHoursGridStyle() {
        // RIGHT SIDE - THE OFFICE HOURS GRID TIME HEADERS
        CSGWorkspace workspaceComponent = (CSGWorkspace)app.getWorkspaceComponent();
        workspaceComponent.getOfficeHoursGridPane().getStyleClass().add(CLASS_OFFICE_HOURS_GRID);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTimeHeaderPanes(), CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_PANE);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTimeHeaderLabels(), CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_LABEL);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridDayHeaderPanes(), CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_PANE);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridDayHeaderLabels(), CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_LABEL);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTimeCellPanes(), CLASS_OFFICE_HOURS_GRID_TIME_CELL_PANE);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTimeCellLabels(), CLASS_OFFICE_HOURS_GRID_TIME_CELL_LABEL);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTACellPanes(), CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTACellLabels(), CLASS_OFFICE_HOURS_GRID_TA_CELL_LABEL);
    }
    
    /**
     * This helper method initializes the style of all the nodes in the nodes
     * map to a common style, styleClass.
     */
    private void setStyleClassOnAll(HashMap nodes, String styleClass) {
        for (Object nodeObject : nodes.values()) {
            Node n = (Node)nodeObject;
            n.getStyleClass().add(styleClass);
        }
    }
}