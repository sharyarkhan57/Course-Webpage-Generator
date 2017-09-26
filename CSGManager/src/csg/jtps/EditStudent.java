/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CSGManagerApp;
import csg.data.CSGData;
import csg.data.Recitation;
import csg.data.Student;
import csg.workspace.CSGWorkspace;
import javafx.scene.control.TableView;

/**
 *
 * @author Sharyar
 */
public class EditStudent implements jTPS_Transaction{
    
    private String firstName;
    private String lastName;
    private String team;
    private String role;
    
    private String newFirstName;
    private String newLastName;
    private String newTeam;
    private String newRole;
    
    private CSGManagerApp app;
    private CSGWorkspace workspace;
    
    public EditStudent(CSGManagerApp app){
        this.app = app;
        workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView pdStudentTable = workspace.getPdStudentTable();
        int selectedItem = pdStudentTable.getSelectionModel().getFocusedIndex();
        Student s =((CSGData)app.getDataComponent()).getStudentList().get(selectedItem);  
        
        firstName = s.getFirstName();
        lastName = s.getLastName();
        team = s.getTeam();
        role = s.getRole();
        
        newFirstName = workspace.getPdFirstNameTextField().getText();
        newLastName = workspace.getPdLastNameTextField().getText();
        newTeam = workspace.getPdBottomTeamComboBox().getSelectionModel().getSelectedItem().toString();
        newRole = workspace.getPdRoleTextField().getText();
       
    }
    
    @Override
    public void doTransaction() {
        workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView pdStudentTable = workspace.getPdStudentTable();
        int selectedItem = pdStudentTable.getSelectionModel().getFocusedIndex();
        Student r =((CSGData)app.getDataComponent()).getStudentList().get(selectedItem);  
   
        r.setFirstName(newFirstName);
        r.setLastName(newLastName);
        r.setTeam(newTeam);
        r.setRole(newRole);
        
        pdStudentTable.getSelectionModel().clearSelection();
        
    }

    
    @Override
    public void undoTransaction() {
        
        workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView pdStudentTable = workspace.getPdStudentTable();
        int selectedItem = pdStudentTable.getSelectionModel().getFocusedIndex();
        Student r =((CSGData)app.getDataComponent()).getStudentList().get(selectedItem);  
   
        r.setFirstName(firstName);
        r.setLastName(lastName);
        r.setTeam(team);
        r.setRole(role);
        
        pdStudentTable.getSelectionModel().clearSelection();
        
    }
    
    
}
