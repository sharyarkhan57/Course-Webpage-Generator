/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CSGManagerApp;
import csg.data.CSGData;
import csg.workspace.CSGWorkspace;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Sharyar
 */
public class AddTeam implements jTPS_Transaction{
    
    private String name;
    private String color;
    private String textColor;
    private String link;
    
    
    private CSGManagerApp app;
    private CSGWorkspace workspace;
    
    public AddTeam(CSGManagerApp app){
        this.app = app;
        workspace = (CSGWorkspace)app.getWorkspaceComponent();
        
        name=workspace.getPdTopNameTextField().getText();
        color= workspace.getPdColorPicker().getValue().toString();
        textColor= workspace.getPdTextColorPicker().getValue().toString();
        link= workspace.getPdTopLinkTextField().getText();
        
    }
    
     @Override
    public void doTransaction() {
        ((CSGData)app.getDataComponent()).addTeam(name, color, textColor, link);
    }

    @Override
    public void undoTransaction() {
        ((CSGData)app.getDataComponent()).removeTeam(name, color, textColor, link);
    }
    
}
