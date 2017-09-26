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
public class DeleteStudent implements jTPS_Transaction{
    
    Student original;
    Student selectedItem;
    int item;


    private CSGManagerApp app;
    private CSGWorkspace workspace;
    private CSGData data;

    
    public DeleteStudent(CSGManagerApp app){
        this.app = app;
        workspace = (CSGWorkspace)app.getWorkspaceComponent();
        data = (CSGData)app.getDataComponent();

        TableView pdStudentTable = workspace.getPdStudentTable();
        item = pdStudentTable.getSelectionModel().getFocusedIndex();
        Student temp =(Student) data.getStudents().get(item);  
        original= new Student(temp.getFirstName(), temp.getLastName(), temp.getTeam(), temp.getRole());
        selectedItem = (Student) pdStudentTable.getSelectionModel().getSelectedItem();

    }
    
    @Override
    public void doTransaction() {
        TableView pdStudentTable = workspace.getPdStudentTable();
        data.removeStudent(selectedItem.getFirstName(), selectedItem.getLastName(), selectedItem.getTeam(), selectedItem.getRole());
    }

    @Override
    public void undoTransaction() {
        TableView pdStudentTable = workspace.getPdStudentTable();
        data.addStudent(original.getFirstName(), original.getLastName(), original.getTeam(), original.getRole());
    }
    
    
    
}
