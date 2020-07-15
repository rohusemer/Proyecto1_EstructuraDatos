/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Modelo.Fotografia;
import Modelo.TDAs.DoubleLinkedList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class FotografiaPersistencia extends Persistencia<Fotografia> {

    private static final FotografiaPersistencia instancia = new FotografiaPersistencia();
    private String fotografiaPersonas[];
    private Fotografia fotografia;

    private FotografiaPersistencia() {
        lines = readAllText("src/Persistencia/Archivos/Fotografias.txt"); // idFotografia, ruta, lugar, descripcion, fechaHora
        fotografiaPersonas = readAllText("src/Persistencia/Archivos/FotografiasPersonas.txt"); // idFotografia, alias
    }

    public static FotografiaPersistencia getInstancia() {
        return instancia;
    }

    @Override
    public Fotografia read(String idFotografiaEscogida) {
        for (String line : lines) {
            if (line.equals("")) {
                break;
            }
            String[] linea = line.split(", ");
            String idFotografia = linea[0];

            if (idFotografia.equals(idFotografiaEscogida)) {

                String ruta = linea[1];
                String lugar = linea[2];
                String descripcion = linea[3];
                String fecha = linea[4];
                fotografia = new Fotografia();
                fotografia.setIdFotografia(Long.parseLong(idFotografia));
                fotografia.setIdAlbum((AlbumPersistencia.getInstancia()).readAlbumFotografia(idFotografiaEscogida));
                fotografia.setLugar(lugar);
                fotografia.setRuta(ruta);
                fotografia.setFecha(LocalDate.parse(fecha));
                fotografia.setDescripcion(descripcion);
                fotografia.setEtiquetas(readAllXIdFotografia(idFotografiaEscogida));

            }
            Persistencia.mapaIds.put("Fotografia", idFotografia);
        }

        return fotografia;
    }

    private DoubleLinkedList<String> readAllXIdFotografia(String idFotografiaEscogida) {
        DoubleLinkedList<String> etiquetas = new DoubleLinkedList();

        for (String line : fotografiaPersonas) {
            if (line.equals("")) {
                break;
            }
            String[] linea = line.split(", ");
            String idFotografia = linea[0];

            if (idFotografia.equals(idFotografiaEscogida)) {
                String alias = linea[1];
                etiquetas.addLast(alias);
            }
        }
        return etiquetas;

    }

    @Override
    public void create(Fotografia foto) {
        String newLine = foto.getIdFotografia() + ", " + foto.getRuta() + ", " + foto.getLugar() + ", " + foto.getDescripcion() + ", " + foto.getFecha();
        if (!isLineContained(lines, newLine)) {

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(new File("src/Persistencia/Archivos/Fotografias.txt"), true);
                PrintWriter pw = new PrintWriter(fos);
                if (lines[0].equals("")) {
                    pw.append(newLine);
                } else {
                    pw.append("\n" + newLine);
                }
                pw.close();

                lines = readAllText("src/Persistencia/Archivos/Fotografias.txt");

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void update(Fotografia foto) {
        StringBuilder sb = new StringBuilder();
        String update = foto.getIdFotografia() + ", " + foto.getRuta() + ", " + foto.getLugar() + ", " + foto.getDescripcion() + ", " + foto.getFecha();
        for (String line : lines) {
            String idFotografia = line.split(", ")[0];
            if ((foto.getIdFotografia().toString()).equals(idFotografia)) {
                sb.append(update + "\n");
            } else {
                sb.append(line + "\n");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        writeAllText(sb.toString(), "src/Persistencia/Archivos/Fotografias.txt");

        lines = readAllText("src/Persistencia/Archivos/Fotografias.txt");
    }

    @Override
    public void delete(String idFotografiaEscogido) {
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            String idFotografia = line.split(", ")[0];
            if ((idFotografiaEscogido).equals(idFotografia)) {

                deleteEtiquetaXidFotografia(idFotografia);
                (AlbumPersistencia.getInstancia()).deleteAlbumFotografiaXIdFotografia(idFotografiaEscogido);
            } else {
                sb.append(line + "\n");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        writeAllText(sb.toString(), "src/Persistencia/Archivos/Fotografias.txt");

        lines = readAllText("src/Persistencia/Archivos/Fotografias.txt");

    }

    private void deleteEtiquetaXidFotografia(String idFotografiaEscogido) {
        StringBuilder sb = new StringBuilder();
        for (String line : fotografiaPersonas) {
            String idFotografia = line.split(", ")[0];

            if (!(idFotografiaEscogido).equals(idFotografia)) {
                sb.append(line + "\n");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        writeAllText(sb.toString(), "src/Persistencia/Archivos/FotografiasPersonas.txt");

        fotografiaPersonas = readAllText("src/Persistencia/Archivos/FotografiasPersonas.txt");
    }

    public void deleteEtiquetaXAlias(String aliasEscogido) {
        StringBuilder sb = new StringBuilder();
        for (String line : fotografiaPersonas) {
            String alias = line.split(", ")[1];

            if (!(aliasEscogido).equals(alias)) {
                sb.append(line + "\n");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        writeAllText(sb.toString(), "src/Persistencia/Archivos/FotografiasPersonas.txt");

        fotografiaPersonas = readAllText("src/Persistencia/Archivos/FotografiasPersonas.txt");
    }

    public void createFotografiaPersonas(String idFotografia, String alias) {
        String newLine = idFotografia + ", " + alias;

        if (!isLineContained(fotografiaPersonas, newLine)) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(new File("src/Persistencia/Archivos/FotografiasPersonas.txt"), true);
                PrintWriter pw = new PrintWriter(fos);

                if (lines[0].equals("")) {
                    pw.append(newLine);
                } else {
                    pw.append("\n" + newLine);
                }
                pw.close();

                fotografiaPersonas = readAllText("src/Persistencia/Archivos/FotografiasPersonas.txt");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
