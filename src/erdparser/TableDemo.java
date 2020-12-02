/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erdparser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author 
 */
public class TableDemo extends JPanel{
    private boolean DEBUG = false;
    Object[][] out;
    
    public TableDemo(Object[][] out,int i,String name,String relations,ArrayList<String> at,ArrayList<String> listaent) {
        super(new BorderLayout());
        this.out=out;
        MyTableModel my=new MyTableModel();
        JTable table = new JTable(my);
        JPanel panel=new JPanel(new BorderLayout());
        JPanel relaciones=new JPanel(new BorderLayout());
        JPanel fk=new JPanel(new FlowLayout());
        JTextArea rela=new JTextArea(15,20);
        JLabel etiqueta=new JLabel("Selecciona la entidad y su clave primaria:");
        rela.setText(relations);
        JTextArea text=new JTextArea(25,30);
        Font tamletra=new Font("Dialog",Font.BOLD,20);
        text.setFont(tamletra);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        JButton button=new JButton("Obtener Sentencia SQL");
        TableColumn column=new TableColumn();
        column=table.getColumnModel().getColumn(1);
        JComboBox combo=new JComboBox();
        
        combo.addItem("Texto");
        combo.addItem("Numero");
        combo.addItem("Fecha");
        
        JComboBox ent=new JComboBox();
        JComboBox att=new JComboBox();
        for (String entidad : listaent) {
            ent.addItem(entidad);
        }
        ent.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                att.removeAllItems();
                String key=ent.getSelectedItem().toString();
                for (String string : at) {
                    if (string.contains(key)==true) {
                        String[] atrib=string.split(" ");
                        for (int j = 1; j < atrib.length; j++) {
                            att.addItem(atrib[j]);
                        }
                    }
                }
            }
            
        });
        
        column.setCellEditor(new DefaultCellEditor(combo));
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CommandButton com=new CommandButton(table,name,ent,att);
                text.setText(com.sentencia(i));
            }
        });
        
        table.setPreferredScrollableViewportSize(new Dimension(800, 70));
        
        table.setFillsViewportHeight(true);
        
        panel.add(button,BorderLayout.NORTH);
        panel.add(text,BorderLayout.SOUTH);
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
        relaciones.add(scrollPane,BorderLayout.NORTH);
        relaciones.add(rela,BorderLayout.EAST);
        fk.add(etiqueta,BorderLayout.LINE_START);
        fk.add(ent,FlowLayout.CENTER);
        fk.add(att,FlowLayout.RIGHT);
        relaciones.add(fk,BorderLayout.CENTER);
        //Add the scroll pane to this panel.
        add(panel,BorderLayout.WEST);
       // add(relaciones,"South");
       
        add(relaciones,BorderLayout.EAST);
    }
    class MyTableModel extends AbstractTableModel{
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

            for (int i=0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j=0; j < numCols; j++) {
                    System.out.print("  " + out[i][j]);
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    }
    private static void createAndShowGUI(Object[][] salida,String name,int i,String relations,ArrayList<String> at,ArrayList<String> listaent) {
        //Create and set up the window.
        JFrame frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Create and set up the content pane.
        TableDemo newContentPane = new TableDemo(salida,i,name,relations,at,listaent);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        //Display the window.
        //frame.setSize(500, 200);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void main(Object[][] out,String name, int i,String relations,ArrayList<String> at,ArrayList<String> listaent) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(out,name,i,relations,at,listaent);
            }
        });
    }
}
