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
public class DeleteRecitation implements jTPS_Transaction{
    Recitation original;
    Recitation selectedItem;
    int item;
            
    private CSGManagerApp app;
    private CSGWorkspace workspace;
    private CSGData data;

    public DeleteRecitation(CSGManagerApp app){
        this.app = app;
        workspace = (CSGWorkspace)app.getWorkspaceComponent();
        data = (CSGData)app.getDataComponent();

        TableView recitationDataTable = workspace.getRecitationDataTable();
        item = recitationDataTable.getSelectionModel().getFocusedIndex();
        Recitation temp =((CSGData)app.getDataComponent()).getRecitationList().get(item);  
        original= new Recitation(temp.getSection(), temp.getInstructor(), temp.getDayTime(), temp.getLocation(),
                temp.getSupervisingTAOne(), temp.getSupervisingTATwo()); 
        selectedItem = (Recitation) recitationDataTable.getSelectionModel().getSelectedItem();

    }
    
    
    @Override
    public void doTransaction() {
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView recitationDataTable = workspace.getRecitationDataTable();
        data.removeRecitation(selectedItem.getSection(), selectedItem.getInstructor(), selectedItem.getLocation());
    }

    @Override
    public void undoTransaction() {
        data.addRecitation(original.getSection(), original.getInstructor() , original.getDayTime(),
                original.getLocation(), original.getSupervisingTAOne(), original.getSupervisingTATwo() );
    }
    
}
