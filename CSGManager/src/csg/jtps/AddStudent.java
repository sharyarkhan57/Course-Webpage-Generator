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
public class AddStudent implements jTPS_Transaction{
    
    private String firstName;
    private String lastName;
    private String team;
    private String role;
    
    private CSGManagerApp app;
    private CSGWorkspace workspace;
    
    public AddStudent(CSGManagerApp app){
        this.app = app;
        workspace = (CSGWorkspace)app.getWorkspaceComponent();
        
        firstName = workspace.getPdFirstNameTextField().getText();
        lastName = workspace.getPdLastNameTextField().getText();
        team = workspace.getPdBottomTeamComboBox().getSelectionModel().getSelectedItem().toString();
        role = workspace.getPdRoleTextField().getText();
       
    }
    
    @Override
    public void doTransaction() {
        ((CSGData)app.getDataComponent()).addStudent(firstName, lastName, team, role);

    }

    @Override
    public void undoTransaction() {
        ((CSGData)app.getDataComponent()).removeStudent(firstName, lastName, team, role);

    }
    
    
    
}
