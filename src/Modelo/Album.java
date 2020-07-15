/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Modelo.Comparadores.FechaAlbumComparator;
import Modelo.TDAs.CircularDoubleLinkedList;
import Modelo.TDAs.CircularQueue;
import Modelo.TDAs.DoubleLinkedList;
import Persistencia.AlbumPersistencia;
import Persistencia.Persistencia;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class Album implements ICrud<Album>, IBuscable<DoubleLinkedList<Album>> {

    private Long idAlbum;
    private String alias;
    private String titulo;
    private String descripcion;
    private LocalDate fecha;
    private CircularQueue<Fotografia> fotos;

    public Album() {
        fotos = new CircularQueue();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(Long idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setFotos(CircularQueue<Fotografia> fotos) {
        this.fotos = fotos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.idAlbum);
        hash = 79 * hash + Objects.hashCode(this.alias);
        hash = 79 * hash + Objects.hashCode(this.titulo);
        hash = 79 * hash + Objects.hashCode(this.descripcion);
        hash = 79 * hash + Objects.hashCode(this.fecha);
        hash = 79 * hash + Objects.hashCode(this.fotos);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Album other = (Album) obj;
        if (!Objects.equals(this.alias, other.alias)) {
            return false;
        }
        if (!Objects.equals(this.titulo, other.titulo)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.idAlbum, other.idAlbum)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        if (!Objects.equals(this.fotos, other.fotos)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idAlbum + ": " + titulo;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Persistencia
    @Override
    public void crear() {
        idAlbum = obtenerUltimoId() + 1l;
        (AlbumPersistencia.getInstancia()).create(this);
    }

    public void crearAlbumFotografia(String idFotografia) {
        (AlbumPersistencia.getInstancia()).createAlbumFotografias(idAlbum.toString(), idFotografia);
    }

    @Override
    public Album leer(String idAlbum) {
        return (AlbumPersistencia.getInstancia()).read(idAlbum);
    }

    @Override
    public void actualizar() {
        (AlbumPersistencia.getInstancia()).update(this);
    }

    @Override
    public void eliminar() {
        (AlbumPersistencia.getInstancia()).delete(idAlbum.toString());
    }

    private void eliminarAlbumFotografia(String idFotografia) {
        (AlbumPersistencia.getInstancia()).deleteAlbumFotografiaXIdFotografia(idFotografia);
    }

    @Override
    public Long obtenerUltimoId() {
        String id = Persistencia.mapaIds.get("Album");
        if (id.equals("")) {
            return 1l;
        }
        return Long.parseLong(Persistencia.mapaIds.get("Album"));
    }

    ////////////////////////////////////////////////////////////////////////////
    // Metodos basicos
    public CircularQueue<Fotografia> mostrarFotos() {
        return fotos;
    }

    public void añadirFoto(Fotografia foto) {
        fotos.enqueue(foto);
        crearAlbumFotografia(foto.getIdFotografia().toString());
    }

    public void eliminarFoto(Fotografia foto) {

        fotos.remove(foto);
        eliminarAlbumFotografia(foto.getIdFotografia().toString());
    }

    ////////////////////////////////////////////////////////////////////////////
    // Busquedas
    @Override
    public DoubleLinkedList<Album> buscarXFecha(DoubleLinkedList<Album> albumes, LocalDate fechaInicial, LocalDate fechaFinal) { // funciona
        return albumes.findBetween(new FechaAlbumComparator(fechaInicial, fechaFinal));
    }

    @Override
    public DoubleLinkedList<Album> buscarXLugar(DoubleLinkedList<Album> albumes, String lugar) { // funciona
        DoubleLinkedList<Album> resultados = new DoubleLinkedList();

        Iterator<Album> it1 = albumes.iterador();
        while (it1.hasNext()) {
            Album album = it1.next();
            CircularDoubleLinkedList<Fotografia> fotos = (album.mostrarFotos().getList());
            ListIterator<Fotografia> it2 = fotos.listIterator();

            boolean bandera = true;
            while (it2.hasNext() && bandera) {
                Fotografia foto = it2.next();

                if ((foto.getLugar()).equals(lugar) && !resultados.contains(album)) {

                    resultados.addLast(album);

                }

                if (foto.equals((fotos.getLast()).getContent())) {
                    bandera = false;
                }
            }
        }

        return resultados;
    }

    @Override
    public DoubleLinkedList<Album> buscarXPersonas(DoubleLinkedList<Album> albumes, Set<String> personas) { // funciona
        DoubleLinkedList<Album> resultados = new DoubleLinkedList();
        Iterator<Album> it1 = albumes.iterador();
        while (it1.hasNext()) {
            Album album = it1.next();
            CircularDoubleLinkedList<Fotografia> fotos = (album.mostrarFotos().getList());

            ListIterator<Fotografia> it2 = fotos.listIterator();

            boolean bandera = true;
            while (it2.hasNext() && bandera) {
                Fotografia foto = it2.next();
                Set<String> etiquetas = (foto.getEtiquetas()).toSet();
                if (etiquetas.retainAll(personas)) {
                    resultados.addLast(album);
                }
                if (foto.equals((fotos.getLast()).getContent())) {
                    bandera = false;
                }
            }
        }

        return resultados;
    }

    @Override
    public DoubleLinkedList<Album> buscarXPersonaLugar(DoubleLinkedList<Album> albumes, Set<String> personas, String lugar) { // funciona
        DoubleLinkedList<Album> resultados = new DoubleLinkedList();
        Iterator<Album> it1 = albumes.iterador();
        while (it1.hasNext()) {
            Album album = it1.next();
            CircularDoubleLinkedList<Fotografia> fotos = (album.mostrarFotos().getList());

            ListIterator<Fotografia> it2 = fotos.listIterator();

            boolean bandera = true;
            while (it2.hasNext() && bandera) {
                Fotografia foto = it2.next();
                Set<String> etiquetas = (it2.next().getEtiquetas()).toSet();
                if (etiquetas.retainAll(personas) && (foto.getLugar()).equals(lugar) && !resultados.contains(album)) {
                    resultados.addLast(album);
                }
                if (foto.equals((fotos.getLast()).getContent())) {
                    bandera = false;
                }
            }
        }

        return resultados;
    }

    @Override
    public DoubleLinkedList<Album> buscarXPersonaFecha(DoubleLinkedList<Album> albumes, Set<String> personas, LocalDate fechaInicial, LocalDate fechaFinal) { //funciona
        DoubleLinkedList<Album> resultados = new DoubleLinkedList();
        Iterator<Album> it1 = albumes.iterador();
        while (it1.hasNext()) {
            Album album = it1.next();
            boolean isFechaBetween = (album.getFecha()).compareTo(fechaInicial) >= 0 && (album.getFecha()).compareTo(fechaFinal) <= 0;

            if (isFechaBetween) {
                CircularDoubleLinkedList<Fotografia> fotos = (album.mostrarFotos()).getList();

                ListIterator<Fotografia> it2 = fotos.listIterator();

                boolean bandera = true;

                while (it2.hasNext() && bandera) {
                    Fotografia foto = it2.next();
                    Set<String> etiquetas = (foto.getEtiquetas()).toSet();

                    if (etiquetas.retainAll(personas) && !resultados.contains(album)) {
                        resultados.addLast(album);
                    }

                    if (foto.equals((fotos.getLast()).getContent())) {
                        bandera = false;
                    }
                }
            }
        }

        return resultados;
    }

    @Override
    public DoubleLinkedList<Album> buscarXLugarFecha(DoubleLinkedList<Album> albumes, String lugar, LocalDate fechaInicial, LocalDate fechaFinal) {// funciona
        DoubleLinkedList<Album> resultados = new DoubleLinkedList();
        Iterator<Album> it1 = albumes.iterador();
        while (it1.hasNext()) {
            Album album = it1.next();
            boolean isFechaBetween = (album.getFecha()).compareTo(fechaInicial) >= 0 && (album.getFecha()).compareTo(fechaFinal) <= 0;
            if (isFechaBetween) {
                CircularDoubleLinkedList<Fotografia> fotos = (album.mostrarFotos().getList());

                ListIterator<Fotografia> it2 = fotos.listIterator();

                boolean bandera = true;
                while (it2.hasNext() && bandera) {
                    Fotografia foto = it2.next();

                    if ((foto.getLugar()).equals(lugar) && !resultados.contains(album)) {
                        resultados.addLast(album);
                    }
                    if (foto.equals((fotos.getLast()).getContent())) {
                        bandera = false;
                    }
                }
            }
        }

        return resultados;
    }

}
