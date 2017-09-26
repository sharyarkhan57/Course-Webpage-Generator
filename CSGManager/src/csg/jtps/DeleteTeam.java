/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CSGManagerApp;
import csg.data.CSGData;
import csg.data.Student;
import csg.data.Team;
import csg.workspace.CSGWorkspace;
import java.util.Iterator;
import java.util.Stack;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Sharyar
 */
public class DeleteTeam implements jTPS_Transaction{
    
    Team original;
    Team selectedItem;
    int item;
    
    //will store all the students that will be deleted in order to allow for undo
    Stack<Student> studentStack;
            
    private CSGManagerApp app;
    private CSGWorkspace workspace;
    private CSGData data;
    
    public DeleteTeam(CSGManagerApp app){
        this.app = app;
        workspace = (CSGWorkspace)app.getWorkspaceComponent();
        data = (CSGData)app.getDataComponent();

        TableView pdTeamTableView = workspace.getPdTeamTableView();
        item = pdTeamTableView.getSelectionModel().getFocusedIndex();
        Team temp =((CSGData)app.getDataComponent()).getTeamList().get(item);  
        
        original= new Team(temp.getName(), temp.getColor(), temp.getTextColor(), temp.getLink()); 
        
        selectedItem = (Team) pdTeamTableView.getSelectionModel().getSelectedItem();

        
    }

    @Override
    public void doTransaction() {
        storeStudents();
        data.removeStudentsFromTeam(selectedItem.getName());
        data.removeTeam(selectedItem.getName(), selectedItem.getColor(), selectedItem.getTextColor(), selectedItem.getLink());
    }

    @Override
    public void undoTransaction() {
        while(!studentStack.empty()){
            Student temp= studentStack.pop();
            data.addStudent(temp.getFirstName(), temp.getLastName(),temp.getRole(),temp.getTeam());
        }
        data.addTeam(original.getName(), original.getColor(), original.getTextColor(), original.getLink() );    
    }
    
    
    public void storeStudents(){
        studentStack= new Stack();
        for (Student s : data.getStudentList()) {
            if (selectedItem.getName().equals(s.getTeam()) ) {
                studentStack.push(s);
            }
        }
    }
    

}

