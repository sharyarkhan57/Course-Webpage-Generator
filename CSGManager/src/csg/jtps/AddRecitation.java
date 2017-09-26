/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CSGManagerApp;
import csg.data.CSGData;
import csg.workspace.CSGWorkspace;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 *
 * @author Sharyar
 */
public class AddRecitation implements jTPS_Transaction{
    
    private String section;
    private String instructor;
    private String dayTime;
    private String location;
    private String firstTA;
    private String secondTA;
    
    private CSGManagerApp app;
    private CSGWorkspace workspace;
    
    public AddRecitation(CSGManagerApp app){
        this.app = app;
        workspace = (CSGWorkspace)app.getWorkspaceComponent();

        
        TextField rdSectionTextField = workspace.getRdSectionTextField();
        TextField rdInstructorTextField = workspace.getRdInstructorTextField();
        TextField rdDayTimeTextField = workspace.getRdSectionTextField();
        TextField rdLocationTextField = workspace.getRdInstructorTextField();
        
        ComboBox rdFirstTaComboBox = workspace.getRdFirstTaComboBox();
        ComboBox rdSecondTaComboBox = workspace.getRdSecondTaComboBox();
        
                //store data in strings
        if(rdSectionTextField.getText()!=null){
            section = rdSectionTextField.getText();
        }

        if(rdInstructorTextField.getText()!=null){
            instructor = rdInstructorTextField.getText(); 
        }
        
        if(rdDayTimeTextField.getText()!=null){
            dayTime = rdDayTimeTextField.getText();
        }
        
        if(rdLocationTextField.getText()!=null){
            location = rdLocationTextField.getText(); 
        }
        
        if(rdSecondTaComboBox.getSelectionModel().getSelectedItem().toString()!=null){
            firstTA =rdSecondTaComboBox.getSelectionModel().getSelectedItem().toString();
        }
        
        if(rdSecondTaComboBox.getSelectionModel().getSelectedItem().toString()!=null){
            secondTA =rdSecondTaComboBox.getSelectionModel().getSelectedItem().toString();
        }
        
       
        
        
    }

    @Override
    public void doTransaction() {
        ((CSGData)app.getDataComponent()).addRecitation(section, instructor, dayTime, location, firstTA, secondTA);
    }

    @Override
    public void undoTransaction() {
        ((CSGData)app.getDataComponent()).removeRecitation(section, instructor, location);
    }
}
