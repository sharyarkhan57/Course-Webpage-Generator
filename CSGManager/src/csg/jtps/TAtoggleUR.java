/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.data.CSGData;

/**
 *
 * @author Sharyar
 */
public class TAtoggleUR implements jTPS_Transaction{
    
    private String TAname;
    private String cellKey;
    private CSGData data;
    
    public TAtoggleUR(String TAname, String cellKey, CSGData data){
        this.TAname = TAname;
        this.cellKey = cellKey;
        this.data = data;
    }
    

    @Override
    public void doTransaction() {
        data.toggleTAOfficeHours(cellKey, TAname);
    }

    @Override
    public void undoTransaction() {
        data.toggleTAOfficeHours(cellKey, TAname);
    }
    
}