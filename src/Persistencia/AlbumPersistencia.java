/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Modelo.Album;
import Modelo.Fotografia;
import Modelo.TDAs.CircularQueue;
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
public class AlbumPersistencia extends Persistencia<Album> {

    private static final AlbumPersistencia instancia = new AlbumPersistencia();

    private String albumFotografias[];
    private Album album;
    private Fotografia foto;

    private AlbumPersistencia() {
        lines = readAllText("src/Persistencia/Archivos/Albumes.txt");  // idAlbum, titulo, descripcion, fechaHora
        albumFotografias = readAllText("src/Persistencia/Archivos/AlbumesFotografias.txt");  // idAlbum, idFotografia
    }

    public static AlbumPersistencia getInstancia() {
        return instancia;
    }

    @Override
    public Album read(String idAlbumEscogido) {
        for (String line : lines) {
            if (line.equals("")) {
                break;
            }
            String[] linea = line.split(", ");
            String idAlbum = linea[0];

            if (idAlbum.equals(idAlbumEscogido)) {
                String titulo = linea[1];
                String descripcion = linea[2];//
                String fechaHora = linea[3];

                album = new Album();
                album.setIdAlbum(Long.parseLong(idAlbum));
                album.setAlias((PersonaPersistencia.getInstancia()).readPersonaAlbum(idAlbumEscogido));
                album.setTitulo(titulo);
                album.setDescripcion(descripcion);
                album.setFecha(LocalDate.parse(fechaHora));
                album.setFotos(readAllXIdAlbum(idAlbumEscogido));
            }

            Persistencia.mapaIds.put("Album", idAlbum);
        }

        return album;
    }

    @Override
    public void create(Album album) {

        String newLine = album.getIdAlbum() + ", " + album.getTitulo() + ", " + album.getDescripcion() + ", " + album.getFecha();
        if (!isLineContained(lines, newLine)) {

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(new File("src/Persistencia/Archivos/Albumes.txt"), true);
                PrintWriter pw = new PrintWriter(fos);
                if (lines[0].equals("")) {
                    pw.append(newLine);
                } else {
                    pw.append("\n" + newLine);
                }
                pw.close();

                lines = readAllText("src/Persistencia/Archivos/Albumes.txt");

                (PersonaPersistencia.getInstancia()).createPersonaAlbumes(album.getAlias(), album.getIdAlbum().toString());

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
    public void update(Album album) {
        StringBuilder sb = new StringBuilder();
        String update = album.getIdAlbum() + ", " + album.getTitulo() + ", " + album.getDescripcion() + ", " + album.getFecha();
        for (String line : lines) {
            if (line.equals("")) {
                break;
            }
            String idAlbum = line.split(", ")[0];

            if ((album.getIdAlbum().toString()).equals(idAlbum)) {
                sb.append(update + "\n");
            } else {
                sb.append(line + "\n");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        writeAllText(sb.toString(), "src/Persistencia/Archivos/Albumes.txt");

        lines = readAllText("src/Persistencia/Archivos/Albumes.txt");
    }

    @Override
    public void delete(String idAlbumEscogido) {
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            if (line.equals("")) {
                break;
            }
            String idAlbum = line.split(", ")[0];
            if ((idAlbumEscogido).equals(idAlbum)) {
                deleteAlbumFotografiaXIdAlbum(idAlbum);
                (PersonaPersistencia.getInstancia()).deletePersonaAlbumXIdAlbum(idAlbumEscogido);
            } else {
                sb.append(line + "\n");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        writeAllText(sb.toString(), "src/Persistencia/Archivos/Albumes.txt");

        lines = readAllText("src/Persistencia/Archivos/Albumes.txt");
    }

    private void deleteAlbumFotografiaXIdAlbum(String idAlbumEscogido) {
        StringBuilder sb = new StringBuilder();
        for (String line : albumFotografias) {
            if (line.equals("")) {
                break;
            }
            String idAlbum = line.split(", ")[0];

            if (!(idAlbumEscogido).equals(idAlbum)) {
                sb.append(line + "\n");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        writeAllText(sb.toString(), "src/Persistencia/Archivos/AlbumesFotografias.txt");

        albumFotografias = readAllText("src/Persistencia/Archivos/AlbumesFotografias.txt");
    }

    public void deleteAlbumFotografiaXIdFotografia(String idFotografiaEscogido) {
        StringBuilder sb = new StringBuilder();
        for (String line : albumFotografias) {
            if (line.equals("")) {
                break;
            }
            String idFotografia = line.split(", ")[1];

            if (!(idFotografiaEscogido).equals(idFotografia)) {
                sb.append(line + "\n");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        writeAllText(sb.toString(), "src/Persistencia/Archivos/AlbumesFotografias.txt");

        albumFotografias = readAllText("src/Persistencia/Archivos/AlbumesFotografias.txt");
    }

    private CircularQueue<Fotografia> readAllXIdAlbum(String idAlbumEscogido) {
        CircularQueue<Fotografia> fotos = new CircularQueue();

        for (String line : albumFotografias) {
            if (line.equals("")) {
                break;
            }
            String[] linea = line.split(", ");
            String idAlbum = linea[0];

            if (idAlbum.equals(idAlbumEscogido)) {
                String idFotografia = linea[1];
                foto = new Fotografia();
                foto = foto.leer(idFotografia);
                foto.setIdAlbum(Long.parseLong(idAlbum));
                fotos.enqueue(foto);
            }
        }

        return fotos;
    }

    public Long readAlbumFotografia(String idFotografiaBuscada) {
        for (String line : albumFotografias) {
            if (line.equals("")) {
                break;
            }
            String[] linea = line.split(", ");
            String idFotografia = linea[1];

            if (idFotografia.equals(idFotografiaBuscada)) {
                return Long.parseLong(linea[0]);
            }
        }
        return null;
    }

    public void createAlbumFotografias(String idAlbum, String idFotografia) {
        String newLine = idAlbum + ", " + idFotografia;
        if (!isLineContained(albumFotografias, newLine)) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(new File("src/Persistencia/Archivos/AlbumesFotografias.txt"), true);
                PrintWriter pw = new PrintWriter(fos);
                if (lines[0].equals("")) {
                    pw.append(newLine);
                } else {
                    pw.append("\n" + newLine);
                }
                pw.close();

                albumFotografias = readAllText("src/Persistencia/Archivos/AlbumesFotografias.txt");
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
