/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public abstract class Persistencia<E> {

    protected PrintWriter pw;
    protected String lines[];

    public static Map<String, String> mapaIds = new HashMap();

    static {
        mapaIds.put("Persona", "");
        mapaIds.put("Album", "");
        mapaIds.put("Fotografia", "");
    }

    protected String[] readAllText(String ruta) {
        StringBuilder sb = new StringBuilder();
        try {
            Files.lines(Paths.get(ruta)).forEach(linea -> {
                sb.append(linea + "\n");
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
       
        return (sb.toString()).split("\n");
    }

    protected void writeAllText(String text, String fileout) {
        try {
            pw = new PrintWriter(fileout);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        pw.print(text);
        pw.close();
    }

        protected boolean isLineContained(String[] lineas, String line) {
            for (String linea : lineas) {
                if (linea.equals(line)) {
                    return true;
                }
            }
            return false;
        }

    protected String[] getLines() {
        return lines;
    }

    protected void setLines(String[] lines) {
        this.lines = lines;
    }

    public abstract void create(E element);

    public abstract E read(String id);

    public abstract void update(E element);

    public abstract void delete(String element);

}
