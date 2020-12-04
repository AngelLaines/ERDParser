/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erdparser;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;

/**
 *
 * @author dell
 */
public class fkButton {

    String ent;
    String att;
    
    fkButton(String ent, String att) {
        this.ent = ent;
        this.att = att;
        
    }

    public String sentencia() {
        String sql=null;
        sql=",\n\tFOREING KEY ("+att+") REFERENCES "+ent+"("+att+")\n\t\tON UPDATE CASCADE";

        //System.out.println(count);
        
        return sql;
    }

    String nombrecampo(char[] tokens) {
        String n = "";
        for (int i = 0; i < tokens.length - 1; i++) {
            n = n + tokens[i];
        }
        return n;
    }
}
