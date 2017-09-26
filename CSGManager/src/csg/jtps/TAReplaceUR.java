package csg.jtps;

import javafx.scene.control.TableView;
import csg.jtps.jTPS_Transaction;
import csg.CSGManagerApp;
import csg.data.CSGData;
import csg.data.TeachingAssistant;
import csg.workspace.CSGWorkspace;

/**
 *
 * @author zhaotingyi
 */
public class TAReplaceUR implements jTPS_Transaction{
    private String TAname;
    private String TAemail;
    private String newName;
    private String newEmail;
    private CSGManagerApp app;
    private CSGData data;
    
    public TAReplaceUR(CSGManagerApp app){
        this.app = app;
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        data = (CSGData)app.getDataComponent();
        newName = workspace.getNameTextField().getText();
        newEmail = workspace.getEmailTextField().getText();
        TableView taTable = workspace.getTATable();
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        TeachingAssistant ta = (TeachingAssistant)selectedItem;
        TAname = ta.getName();
        TAemail = ta.getEmail();
    }

    @Override
    public void doTransaction() {
        data.replaceTAName(TAname, newName);
        data.removeTA(TAname);
        data.addTA(newName, newEmail, true);
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView taTable = workspace.getTATable();
        taTable.getSelectionModel().select(data.getTA(newName));
    }

    @Override
    public void undoTransaction() {
        data.replaceTAName(newName, TAname);
        data.removeTA(newName);
        data.addTA(TAname, TAemail, true);
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView taTable = workspace.getTATable();
        taTable.getSelectionModel().select(data.getTA(TAname));
    }
    
    
    
}