/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import csg.jtps.jTPS_Transaction;
import csg.CSGManagerApp;
import csg.data.CSGData;
import csg.workspace.CSGWorkspace;

/**
 *
 * @author Sharyar Khan
 */
public class TAdeletUR implements jTPS_Transaction{
    
    private CSGManagerApp app;
    private CSGData data;
    private ArrayList<StringProperty> cellProps = new ArrayList<StringProperty>();
    private String TAname;
    private String TAemail;
    
    public TAdeletUR(CSGManagerApp app, String TAname){
        this.app = app;
        data = (CSGData)app.getDataComponent();
        this.TAname = TAname;
        TAemail = data.getTA(TAname).getEmail();
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        HashMap<String, Label> labels = workspace.getOfficeHoursGridTACellLabels();
        for (Label label : labels.values()) {
            if (label.getText().equals(TAname)
            || (label.getText().contains(TAname + "\n"))
            || (label.getText().contains("\n" + TAname))) {
                cellProps.add(label.textProperty());
            }
        }
    }

    @Override
    public void doTransaction() {
        data.removeTA(TAname);
        for(StringProperty cellProp : cellProps){
            data.removeTAFromCell(cellProp, TAname);
        }
    }

    @Override
    public void undoTransaction() {
        data.addTA(TAname, TAemail,true);
        for(StringProperty cellProp : cellProps){
            String cellText = cellProp.getValue();
            if (cellText.length() == 0){
                cellProp.setValue(TAname);
            } else {
                cellProp.setValue(cellText + "\n" + TAname);}
        }
    }
}
