/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erdparser;

import java.awt.TextField;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import javax.swing.JCheckBox;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author dell and more
 */
public class ERDParser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {

        FileReader fp = new FileReader("university-erd.json");

        // Crear el tokenizador del documento JSON  
        JSONTokener tokenizer = new JSONTokener(fp);

        // Objeto principal que contiene todos los componentes
        // del diagrama ERD
        JSONObject JSONDoc = new JSONObject(tokenizer);

        //Obtenet los nombres de los objetos 
        JSONArray names = JSONDoc.names();
        System.out.println(names);

        // Solo recuperar los objetos que describen entidades
        JSONArray entidades = JSONDoc.getJSONArray("entidades");
        System.out.println(entidades);

        Iterator it = entidades.iterator();

        // Procesar cada una de las entidades
        while (it.hasNext()) {

            JSONObject entidad = (JSONObject) it.next();

            //names = entidad.names();
            // Para cada entidad, mostrar su nombre
            String entityName = entidad.getString("nombre");
            System.out.println(entityName);

            // Para cada entidad, mostrar los atributos
            JSONArray atributos = entidad.getJSONArray("atributos");
            Iterator attribIt = atributos.iterator();

            int i = 0;
            String temp = "";
            while (attribIt.hasNext()) {
                JSONObject atributo = (JSONObject) attribIt.next();
                System.out.print("\t");
                System.out.print(atributo.getString("nombre"));

                if (atributo.getInt("tipo") == 1) {
                    System.out.println(" *");

                } else {
                    System.out.println();
                }
                i++;
            }
            attribIt = atributos.iterator();
            Object[][] tabla = new Object[i][6];
            //tabla[][]=new Object[i][6];
            i = 0;
            while (attribIt.hasNext()) {
                JSONObject atributo = (JSONObject) attribIt.next();
                
                tabla[i][0] = atributo.getString("nombre").toString();
                
                if (atributo.getInt("tipo") == 1) {
                    //System.out.println(" *");
                    temp = tabla[i][0].toString();
                    temp = temp + "*";
                    tabla[i][0] = temp;
                } else {
                    //System.out.println();
                }

                i++;
            }
            TableDemo tab = new TableDemo(tabla, i, entityName);
            //Frm form=new Frm(tabla);
            //form.mein(tabla,entityName);
            tab.main(tabla, entityName, i);
        }
        System.out.println("");
        System.out.println("** DEBILES **");
        // Solo recuperar los objetos que describen entidades debiles
        JSONArray debiles = JSONDoc.getJSONArray("debiles");
        System.out.println(debiles);

        it = debiles.iterator();

        // Procesar cada una de las entidades debiles
        while (it.hasNext()) {

            JSONObject entidad = (JSONObject) it.next();

            //names = entidad.names();
            // Para cada entidad, mostrar su nombre
            String entityName = entidad.getString("nombre");
            System.out.println(entityName);

            // Para cada entidad, mostrar los atributos
            JSONArray atributos = entidad.getJSONArray("atributos");
            Iterator attribIt = atributos.iterator();
            int i = 0;
            while (attribIt.hasNext()) {
                JSONObject atributo = (JSONObject) attribIt.next();
                System.out.print("\t");
                System.out.print(atributo.getString("nombre"));

                if (atributo.getInt("tipo") == 1) {
                    System.out.println(" *");
                } else {
                    System.out.println();
                }
                i++;
            }

            String temp = "";
            attribIt = atributos.iterator();
            Object[][] table = new Object[i][6];
            //tabla[][]=new Object[i][6];
            i = 0;
            while (attribIt.hasNext()) {
                JSONObject atributo = (JSONObject) attribIt.next();
                //System.out.print("\t");
                //System.out.print(atributo.getString("nombre"));
                table[i][0] = atributo.getString("nombre");
                if (atributo.getInt("tipo") == 1) {
                    temp = table[i][0].toString();
                    temp = temp + "*";
                    table[i][0] = temp;
                    //System.out.println(" *");
                } else {
                    //System.out.println();
                }

                i++;
            }
            TableDemo tab = new TableDemo(table, i, entityName);
            //Frm form=new Frm(tabla);
            //form.mein(tabla,entityName);
            tab.main(table, entityName, i);
        }

        System.out.println("");

        // Solo recuperar los objetos que describen entidades
        JSONArray relations = JSONDoc.getJSONArray("relaciones");

        System.out.println("**** RELACIONES ****");

        it = relations.iterator();

        while (it.hasNext()) {
            JSONObject rel = (JSONObject) it.next();

            //System.out.println( rel );
            //System.out.println( rel.names());
            System.out.println(rel.getString("nombre"));

            JSONArray cards = rel.getJSONArray("cardinalidades");

            int n = cards.length();

            for (int i = 0; i < n; i++) {
                JSONObject e1 = cards.getJSONObject(i);

                System.out.printf("\t%s (%s,%s)\n", e1.getString("entidad"),
                        e1.getString("min"),
                        e1.getString("max"));

            }

        }

    }

}
