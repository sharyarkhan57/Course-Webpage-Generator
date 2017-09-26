/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CSGManagerApp;
import csg.data.CSGData;
import csg.data.Recitation;
import csg.data.TeachingAssistant;
import csg.workspace.CSGWorkspace;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Sharyar
 */
public class EditRecitation implements jTPS_Transaction{
    
    private String section;
    private String instructor;
    private String dayTime;
    private String location;
    private String firstTA;
    private String secondTA;
    
    private String newSection;
    private String newInstructor;
    private String newDayTime;
    private String newLocation;
    private String newFirstTA;
    private String newSecondTA;
    
    private CSGManagerApp app;
    private CSGWorkspace workspace;

    public EditRecitation(CSGManagerApp app){
        this.app = app;
        workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView recitationDataTable = workspace.getRecitationDataTable();
        int selectedItem = recitationDataTable.getSelectionModel().getFocusedIndex();
        Recitation r =((CSGData)app.getDataComponent()).getRecitationList().get(selectedItem);  
        section= r.getSection();
        instructor= r.getInstructor();
        dayTime= r.getDayTime();
        location= r.getLocation();
        firstTA= r.getSupervisingTAOne();
        secondTA= r.getSupervisingTATwo();
        
        newSection= workspace.getRdSectionTextField().getText();
        newInstructor= workspace.getRdInstructorTextField().getText();
        newDayTime= workspace.getRdDayTimeTextField().getText();
        newLocation= workspace.getRdLocationTextField().getText();
        newFirstTA=  workspace.getRdFirstTaComboBox().getSelectionModel().getSelectedItem().toString();
        newSecondTA= workspace.getRdSecondTaComboBox().getSelectionModel().getSelectedItem().toString();
        
    }

    
    @Override
    public void doTransaction() {
        workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView recitationDataTable = workspace.getRecitationDataTable();
        int selectedItem = recitationDataTable.getSelectionModel().getFocusedIndex();
        Recitation r =((CSGData)app.getDataComponent()).getRecitationList().get(selectedItem);  
        r.setSection(newSection);
        r.setInstructor(newInstructor);
        r.setDayTime(newDayTime);
        r.setLocation(newLocation);
        r.setSuperVisingTAOne(newFirstTA);
        r.setSuperVisingTATwo(newSecondTA);
        
        recitationDataTable.getSelectionModel().clearSelection();
    }

    @Override
    public void undoTransaction() {
        workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView recitationDataTable = workspace.getRecitationDataTable();
        int selectedItem = recitationDataTable.getSelectionModel().getFocusedIndex();
        Recitation r =((CSGData)app.getDataComponent()).getRecitationList().get(selectedItem);  
        r.setSection(section);
        r.setInstructor(instructor);
        r.setDayTime(dayTime);
        r.setLocation(location);
        r.setSuperVisingTAOne(firstTA);
        r.setSuperVisingTATwo(secondTA);
        
        recitationDataTable.getSelectionModel().clearSelection();

    }
    
    
    
    
}
