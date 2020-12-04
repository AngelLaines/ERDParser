/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erdparser;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author dell
 */
class MyTableModel extends AbstractTableModel {

    public Object[][] out;
    MyTableModel(Object[][] out){
        this.out=out;
    }
    
    private boolean DEBUG = false;
    String[] columnNames = {"Nombre", "Tipo", "Longitud", "No Nulo", "Clave Primaria", "Descripción"};

    public int getColumnCount() {
        return this.columnNames.length;
    }

    public int getRowCount() {
        return out.length;
    }

    public String getColumnName(int col) {
        return this.columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return out[row][col];
    }

    /*
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }*/
    @Override
    public Class<?> getColumnClass(int c) {
        Class clazz = String.class;
        switch (c) {
            case 0:
                clazz = String.class;
                break;
            case 1:
                clazz = String.class;
                break;
            case 2:
                clazz = Integer.class;
                break;
            case 3:
                clazz = Boolean.class;
                break;
            case 4:
                clazz = Boolean.class;
                break;
            case 5:
                clazz = String.class;
                break;
            default:
                break;
        }
        return clazz;
    }

    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col < 1) {
            return false;
        } else {
            return true;
        }
    }

    public void setValueAt(Object value, int row, int col) {
        if (DEBUG) {
            System.out.println("Setting value at " + row + "," + col
                    + " to " + value
                    + " (an instance of "
                    + value.getClass() + ")");
        }

        out[row][col] = value;
        fireTableCellUpdated(row, col);

        if (DEBUG) {
            System.out.println("New value of data:");
            printDebugData();
        }
    }

    private void printDebugData() {
        int numRows = getRowCount();
        int numCols = getColumnCount();

        for (int i = 0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j = 0; j < numCols; j++) {
                System.out.print("  " + out[i][j]);
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }

}
