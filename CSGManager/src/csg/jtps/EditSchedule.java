/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CSGManagerApp;
import csg.data.CSGData;
import csg.data.Recitation;
import csg.data.ScheduleItem;
import csg.workspace.CSGWorkspace;
import javafx.scene.control.TableView;

/**
 *
 * @author Sharyar
 */
public class EditSchedule implements jTPS_Transaction{
  
    private String type;
    private String date;
    private String time;
    private String title;
    private String link;
    private String criteria;

    private String newType;
    private String newDate;
    private String newTime;
    private String newTitle;
    private String newTopic;
    private String newLink;
    private String newCriteria;
    
    private CSGManagerApp app;
    private CSGWorkspace workspace;
    private CSGData data;
    
    TableView scheduleItemsTable;
    ScheduleItem si;
    
    public EditSchedule(CSGManagerApp app){
        this.app = app;
        workspace = (CSGWorkspace)app.getWorkspaceComponent();
        scheduleItemsTable = workspace.getScheduleItemsTable();
        int selectedItem = scheduleItemsTable.getSelectionModel().getFocusedIndex();
        data =(CSGData) app.getDataComponent();
        si =data.getScheduleItemsList().get(selectedItem);  

        type= si.getType();
        date= si.getDate();
        time= si.getTitle();
        title= si.getTitle();
        link= si.getLink();
        criteria= si.getCriteria();
        
        newType = workspace.getSdTypeComboBox().getSelectionModel().getSelectedItem().toString();
        newDate = workspace.getSdDatePicker().getEditor().getText();
        newTime = workspace.getSdTimeTextField().getText();
        newTitle = workspace.getSdTitleTextField().getText();
        newTopic = workspace.getSdTopicTextField().getText();
        newLink = workspace.getSdLinkTextField().getText();
        newCriteria = workspace.getSdCriteriaTextField().getText();
    }
    
        
    @Override
    public void doTransaction() {
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView scheduleItemsTable = workspace.getRecitationDataTable();
        int selectedItem = scheduleItemsTable.getSelectionModel().getFocusedIndex();
        si.setType(newType);
        si.setDate(newDate);
        si.setTime(newTime);
        si.setTitle(newTitle);
        si.setLink(newLink);
        si.setCriteria(newCriteria);
        scheduleItemsTable.getSelectionModel().clearSelection();

    }

    @Override
    public void undoTransaction() {
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView scheduleItemsTable = workspace.getRecitationDataTable();
        int selectedItem = scheduleItemsTable.getSelectionModel().getFocusedIndex();
        si.setType(type);
        si.setDate(date);
        si.setTime(time);
        si.setTitle(title);
        si.setLink(link);
        si.setCriteria(criteria);
        scheduleItemsTable.getSelectionModel().clearSelection();
    }
    
    
}
