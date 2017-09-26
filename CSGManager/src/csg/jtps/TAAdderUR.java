/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import java.util.regex.Pattern;
import csg.jtps.jTPS_Transaction;
import csg.CSGManagerApp;
import csg.data.CSGData;
import csg.workspace.CSGController;
import csg.workspace.CSGWorkspace;

/**
 *
 * @author zhaotingyi
 */
public class TAAdderUR implements jTPS_Transaction{
    
    private String TAName;
    private String TAEmail;
    boolean isUndergrad;
    private CSGManagerApp app;
    private CSGWorkspace workspace;
    
    public TAAdderUR(CSGManagerApp app){
        this.app = app;
        workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TAName = workspace.getNameTextField().getText();
        TAEmail = workspace.getEmailTextField().getText();
    }

    @Override
    public void doTransaction() {
        ((CSGData)app.getDataComponent()).addTA(TAName, TAEmail, true);
    }

    @Override
    public void undoTransaction() {
        ((CSGData)app.getDataComponent()).removeTA(TAName);
    }
}
