/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erdparser;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dell
 */
public class RelationTableModel extends DefaultTableModel{
    public RelationTableModel(Object[][] data,Object[] colNames){
        super(data,colNames);
    }
    @Override
    public boolean isCellEditable(int row, int column){
        return column>0;
    }
    @Override
    public Class<?> getColumnClass(int c){
        Class clazz=String.class;
        switch(c){
            case 0:
                clazz=String.class;
                break;
            case 1:
                clazz=String.class;
                break;
            case 2:
                clazz=Integer.class;
                break;
            case 3:
                clazz=Boolean.class;
                break;
            case 4:
                clazz=Boolean.class;
                break;
            case 5:
                clazz=String.class;
                break;
            default:
                break;
        }
        return clazz;
    }
}
