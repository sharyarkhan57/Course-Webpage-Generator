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
import csg.data.Team;
import csg.workspace.CSGWorkspace;
import java.util.Stack;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Sharyar
 */
public class EditTeam implements jTPS_Transaction{
    
    private String name;
    private String color;
    private String textColor;
    private String link;
    
    private String newName;
    private String newColor;
    private String newTextColor;
    private String newLink;
    
    private CSGManagerApp app;
    private CSGWorkspace workspace;
    private CSGData data;

    Stack<Student> studentStack;

    
    public EditTeam(CSGManagerApp app){
        this.app = app;
        workspace = (CSGWorkspace)app.getWorkspaceComponent();
        data = (CSGData)app.getDataComponent();

        TableView pdTeamTableView = workspace.getPdTeamTableView();
        int selectedItem = pdTeamTableView.getSelectionModel().getFocusedIndex();
        Team t =((CSGData)app.getDataComponent()).getTeamList().get(selectedItem); 
        
        name= t.getName();
        color= t.getColor();
        textColor= t.getTextColor();
        link= t.getLink();
        
        newName=workspace.getPdTopNameTextField().getText();
        newColor= workspace.getPdColorPicker().getValue().toString();
        newTextColor= workspace.getPdTextColorPicker().getValue().toString();
        newLink= workspace.getPdTopLinkTextField().getText();
       
        
        
    }

    @Override
    public void doTransaction() {
        storeStudents();
        TableView pdTeamTableView = workspace.getPdTeamTableView();
        int selectedItem = pdTeamTableView.getSelectionModel().getFocusedIndex();
        Team t =((CSGData)app.getDataComponent()).getTeamList().get(selectedItem); 
        t.setName(newName);
        t.setColor(newColor);
        t.setTextColor(newTextColor);
        t.setLink(newLink);

        //remove students with the original team name
        data.removeStudentsFromTeam(name);
        
        while(!studentStack.empty()){
            Student temp= studentStack.pop();
            data.addStudent(temp.getFirstName(), temp.getLastName(), newName,temp.getRole());
        }
        
        pdTeamTableView.getSelectionModel().clearSelection();
    }

    @Override
    public void undoTransaction() {
        TableView pdTeamTableView = workspace.getPdTeamTableView();
        int selectedItem = pdTeamTableView.getSelectionModel().getFocusedIndex();
        Team t =((CSGData)app.getDataComponent()).getTeamList().get(selectedItem); 
        t.setName(name);
        t.setColor(color);
        t.setTextColor(textColor);
        t.setLink(link);

        pdTeamTableView.getSelectionModel().clearSelection();
    }
    
    
    public void storeStudents(){
        studentStack= new Stack();
        for (Student s : data.getStudentList()) {
            if (name.equals(s.getTeam()) ) {
                studentStack.push(s);
            }
        }
    }
    
}
