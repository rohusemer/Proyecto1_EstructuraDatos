/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Modelo.Album;
import Modelo.Persona;
import Modelo.TDAs.DoubleLinkedList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class PersonaPersistencia extends Persistencia<Persona> {

    private static PersonaPersistencia instancia = new PersonaPersistencia();

    private String personaAlbumes[];
    private Persona persona;

    private Album album;

    private PersonaPersistencia() {
        lines = readAllText("src/Persistencia/Archivos/Personas.txt"); // alias, nombres, apellidos
        personaAlbumes = readAllText("src/Persistencia/Archivos/PersonasAlbumes.txt"); // idPersona, idAlbum
    }

    public static PersonaPersistencia getInstancia() {
        return instancia;
    }

    @Override
    public void create(Persona persona) {

        String newLine = persona.getAlias() + ", " + persona.getNombres() + ", " + persona.getApellidos();

        if (!isLineContained(lines, newLine)) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(new File("src/Persistencia/Archivos/Personas.txt"), true);
                PrintWriter pw = new PrintWriter(fos);
                if (lines[0].equals("")) {
                    pw.append(newLine);
                } else {
                    pw.append("\n" + newLine);
                }
                pw.close();

                lines = readAllText("src/Persistencia/Archivos/Personas.txt");
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
    public void update(Persona persona) {

        StringBuilder sb = new StringBuilder();
        String update = persona.getAlias() + ", " + persona.getNombres() + ", " + persona.getApellidos();
        for (String line : lines) {
            String alias = line.split(", ")[0];
            if (persona.getAlias().equals(alias)) {
                sb.append(update + "\n");
            } else {
                sb.append(line + "\n");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        writeAllText(sb.toString(), "src/Persistencia/Archivos/Personas.txt");

        lines = readAllText("src/Persistencia/Archivos/Personas.txt");
    }

    @Override
    public void delete(String aliasEscogido) {
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            String alias = line.split(", ")[0];
            if (!(aliasEscogido).equals(alias)) {
                sb.append(line + "\n");
            } else {
                deletePersonaAlbumXAlias(aliasEscogido);
                (FotografiaPersistencia.getInstancia()).deleteEtiquetaXAlias(aliasEscogido);
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        writeAllText(sb.toString(), "src/Persistencia/Archivos/Personas.txt");

        lines = readAllText("src/Persistencia/Archivos/Personas.txt");
    }

    private void deletePersonaAlbumXAlias(String aliasEscogido) {
        StringBuilder sb = new StringBuilder();
        for (String line : personaAlbumes) {
            String alias = line.split(", ")[0];

            if (!(aliasEscogido).equals(alias)) {
                sb.append(line + "\n");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        writeAllText(sb.toString(), "src/Persistencia/Archivos/PersonasAlbumes.txt");

        personaAlbumes = readAllText("src/Persistencia/Archivos/PersonasAlbumes.txt");
    }

    public void deletePersonaAlbumXIdAlbum(String idAlbumEscogido) {
        StringBuilder sb = new StringBuilder();
        for (String line : personaAlbumes) {
            String idAlbum = line.split(", ")[1];

            if (!(idAlbumEscogido).equals(idAlbum)) {
                sb.append(line + "\n");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        writeAllText(sb.toString(), "src/Persistencia/Archivos/PersonasAlbumes.txt");

        personaAlbumes = readAllText("src/Persistencia/Archivos/PersonasAlbumes.txt");
    }

    @Override
    public Persona read(String aliasEscogido) {
        for (String line : lines) {
            if (line.equals("")) {
                break;
            }
            String[] linea = line.split(", ");
            String alias = linea[0];

            if (alias.equals(aliasEscogido)) {
                String nombres = linea[1];
                String apellidos = linea[2];
                persona = new Persona();
                persona.setAlias(alias);
                persona.setApellidos(apellidos);
                persona.setNombres(nombres);
                persona.setAlbumes(readAllXAlias(aliasEscogido));

            }
            Persistencia.mapaIds.put("Persona", alias);
        }

        return persona;
    }

    private DoubleLinkedList<Album> readAllXAlias(String aliasEscogido) {
        DoubleLinkedList<Album> albumes = new DoubleLinkedList();

        for (String line : personaAlbumes) {
            if (line.equals("")) {
                break;
            }
            String[] linea = line.split(", ");
            String alias = linea[0];

            if (alias.equals(aliasEscogido)) {
                String idAlbum = linea[1];
                album = new Album();
                album = album.leer(idAlbum);//
                album.setAlias(alias);
                albumes.addLast(album);
            }
        }

        return albumes;
    }

    public String readPersonaAlbum(String idAlbumBuscado) {

        for (String line : personaAlbumes) {
            if (line.equals("")) {
                break;
            }
            String[] linea = line.split(", ");
            String idAlbum = linea[1];

            if (idAlbum.equals(idAlbumBuscado)) {
                return linea[0];

            }
        }
        return null;
    }

    public void createPersonaAlbumes(String alias, String idAlbum) {
        String newLine = alias + ", " + idAlbum;
        if (!isLineContained(personaAlbumes, newLine)) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(new File("src/Persistencia/Archivos/PersonasAlbumes.txt"), true);
                PrintWriter pw = new PrintWriter(fos);
                if (lines[0].equals("")) {
                    pw.append(newLine);
                } else {
                    pw.append("\n" + newLine);
                }
                pw.close();

                personaAlbumes = readAllText("src/Persistencia/Archivos/PersonasAlbumes.txt");
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
