/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CSGManagerApp;
import csg.data.CSGData;
import csg.data.ScheduleItem;
import csg.workspace.CSGWorkspace;
import javafx.scene.control.TableView;

/**
 *
 * @author Sharyar
 */
public class DeleteSchedule implements jTPS_Transaction{
        
    ScheduleItem original;
    ScheduleItem selectedItem;
    int item;

    private CSGManagerApp app;
    private CSGWorkspace workspace;
    private CSGData data;
    
    public DeleteSchedule(CSGManagerApp app){
        this.app = app;
        workspace = (CSGWorkspace)app.getWorkspaceComponent();
        data = (CSGData)app.getDataComponent();
        
        TableView scheduleItemsTable = workspace.getScheduleItemsTable();
        item = scheduleItemsTable.getSelectionModel().getFocusedIndex();
        ScheduleItem temp =((CSGData)app.getDataComponent()).getScheduleItemsList().get(item);  
        original= new ScheduleItem(temp.getType(), temp.getDate(), temp.getTime(), temp.getTitle(),
                temp.getTopic(), temp.getLink(), temp.getCriteria(), temp.getDate(), temp.getDate()); 
        selectedItem = (ScheduleItem) scheduleItemsTable.getSelectionModel().getSelectedItem();
    }
    
        
    @Override
    public void doTransaction() {
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView scheduleItemsTable = workspace.getScheduleItemsTable();
        data.removeScheduleItem(selectedItem.getType(), selectedItem.getDate(), selectedItem.getTitle());
    }

    @Override
    public void undoTransaction() {
        data.addScheduleItem(original.getType(), original.getDate() , original.getTime(), original.getTitle(), 
                original.getTopic(), original.getLink(), original.getCriteria(), original.getDate(), original.getDate() );
    }
    
    
    
}
