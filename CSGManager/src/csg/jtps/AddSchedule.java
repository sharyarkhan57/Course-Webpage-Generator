/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CSGManagerApp;
import csg.data.CSGData;
import csg.data.Recitation;
import csg.workspace.CSGWorkspace;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Sharyar
 */
public class AddSchedule implements jTPS_Transaction{
    
    private String type;
    private String date;
    private String time;
    private String title;
    private String topic;
    private String link;
    private String criteria;
    
    private CSGManagerApp app;
    private CSGWorkspace workspace;
    
    public AddSchedule(CSGManagerApp app){
        this.app = app;
        workspace = (CSGWorkspace)app.getWorkspaceComponent();
        
                
        type = workspace.getSdTypeComboBox().getSelectionModel().getSelectedItem().toString();
        date = workspace.getSdDatePicker().getEditor().getText();
        time = workspace.getSdTimeTextField().getText();
        title = workspace.getSdTitleTextField().getText();
        topic = workspace.getSdTopicTextField().getText();
        link = workspace.getSdLinkTextField().getText();
        criteria = workspace.getSdCriteriaTextField().getText();
    }
    
        
    @Override
    public void doTransaction() {
        ((CSGData)app.getDataComponent()).addScheduleItem(type, date, time, title, type, link, criteria, date, date);

    }
    
    @Override
    public void undoTransaction() {
        ((CSGData)app.getDataComponent()).removeScheduleItem(type, date, title);

    }
}
