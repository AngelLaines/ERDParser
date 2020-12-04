/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erdparser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author
 */
public class TableDemo extends JPanel {

    private boolean DEBUG = false;
    public Object[][] out;
   

    public TableDemo(Object[][] out, int i, String name, String relations, ArrayList<String> at, ArrayList<String> listaent) {
        super(new BorderLayout());
        this.out = out;
    
        MyTableModel my = new MyTableModel(out);
        JTable table = new JTable(my);
        JPanel panel = new JPanel(new BorderLayout());
        JPanel relaciones = new JPanel(new BorderLayout());
        JPanel fk = new JPanel(new FlowLayout());
        JTextArea rela = new JTextArea(20, 20);
        JLabel etiqueta = new JLabel("Selecciona la entidad y su clave primaria:");
        rela.setText(relations);
        JTextArea text = new JTextArea(25, 30);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        JButton button = new JButton("Obtener Sentencia SQL");
        JButton button2 = new JButton("Claves foraneas");
        JButton button3 = new JButton(");");
        TableColumn column = new TableColumn();
        column = table.getColumnModel().getColumn(1);
        JComboBox combo = new JComboBox();

        combo.addItem("Texto");
        combo.addItem("Numero");
        combo.addItem("Fecha");

        JComboBox ent = new JComboBox();
        JComboBox att = new JComboBox();
        for (String entidad : listaent) {
            ent.addItem(entidad);
        }
        ent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                att.removeAllItems();
                String key = ent.getSelectedItem().toString();
                for (String string : at) {
                    if (string.contains(key) == true) {
                        String[] atrib = string.split(" ");
                        for (int j = 1; j < atrib.length; j++) {
                            att.addItem(atrib[j]);
                        }
                    }
                }
            }

        });
        
        att.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                int a = my.getRowCount() + 1;
                
                Object[][] row = new Object[a][6];
                for (int j = 0; j < a - 1; j++) {
                    row[j][0] = out[j][0];
                }
                row[a - 1][0] = att.getSelectedItem();
                MyTableModel newtable = new MyTableModel(row);
                table.setModel(newtable);
                TableColumn column = new TableColumn();
                column = table.getColumnModel().getColumn(1);
                JComboBox combo = new JComboBox();

                combo.addItem("Texto");
                combo.addItem("Numero");
                combo.addItem("Fecha");
                System.out.println(a);
                column.setCellEditor(new DefaultCellEditor(combo));
            }

        });
        column.setCellEditor(new DefaultCellEditor(combo));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CommandButton com = new CommandButton(table, name);
                text.setText(com.sentencia(table.getRowCount()));
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = text.getText();
                String enti = ent.getSelectedItem().toString(), atrib = att.getSelectedItem().toString();
                fkButton foreing = new fkButton(enti, atrib);
                texto = texto + foreing.sentencia();
                text.setText(texto);
            }

        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = text.getText();
                texto = texto + "\n);";
                text.setText(texto);
            }

        });
        table.setPreferredScrollableViewportSize(new Dimension(800, 70));

        table.setFillsViewportHeight(true);
        panel.add(button, BorderLayout.BEFORE_FIRST_LINE);
        panel.add(button3, BorderLayout.PAGE_END);
        panel.add(text, FlowLayout.CENTER);
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
        relaciones.add(scrollPane, BorderLayout.NORTH);
        relaciones.add(rela, BorderLayout.EAST);
        fk.add(etiqueta, BorderLayout.LINE_START);
        fk.add(ent, FlowLayout.CENTER);
        fk.add(att, FlowLayout.RIGHT);
        fk.add(button2, FlowLayout.LEFT);
        relaciones.add(fk, BorderLayout.CENTER);
        //Add the scroll pane to this panel.
        add(panel, BorderLayout.WEST);
        // add(relaciones,"South");

        add(relaciones, BorderLayout.EAST);
    }

    private static void createAndShowGUI(Object[][] salida, String name, int i, String relations, ArrayList<String> at, ArrayList<String> listaent) {
        //Create and set up the window.
        JFrame frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        TableDemo newContentPane = new TableDemo(salida, i, name, relations, at, listaent);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        //Display the window.
        //frame.setSize(500, 200);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void main(Object[][] out, String name, int i, String relations, ArrayList<String> at, ArrayList<String> listaent) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(out, name, i, relations, at, listaent);
            }
        });
    }
}
