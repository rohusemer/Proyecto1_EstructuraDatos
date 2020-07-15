/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Modelo.TDAs.CircularQueue;
import Modelo.TDAs.DoubleLinkedList;
import Persistencia.PersonaPersistencia;
import Persistencia.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class Persona implements ICrud<Persona> {

    private String alias;
    private String nombres;
    private String apellidos;
    private DoubleLinkedList<Album> albumes;

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setAlbumes(DoubleLinkedList<Album> albumes) {
        this.albumes = albumes;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.alias);
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
        final Persona other = (Persona) obj;
        if (!Objects.equals(this.alias, other.alias)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return alias + ": " + nombres + " " + apellidos;
    }

    /////////////////////////////////////////////////////////////////////////
    //Persistencia
    @Override
    public void crear() {
        (PersonaPersistencia.getInstancia()).create(this);
        //crearCallback.accept(this);
    }

    private void crearPersonaAlbum(String idAlbum) {
        (PersonaPersistencia.getInstancia()).createPersonaAlbumes(alias, idAlbum);
    }

    @Override
    public Persona leer(String alias) {
        return (PersonaPersistencia.getInstancia()).read(alias);
    }

    @Override
    public void eliminar() {
        (PersonaPersistencia.getInstancia()).delete(alias);
    }

    @Override
    public void actualizar() {
        (PersonaPersistencia.getInstancia()).update(this);
    }

    @Override
    public Long obtenerUltimoId() {
        String id = Persistencia.mapaIds.get("Album");
        if (id.equals("")) {
            return 0l;
        }
        return Long.parseLong(Persistencia.mapaIds.get("Persona"));
    }

    ////////////////////////////////////////////////////////////////////////
    // Metodos Basicos 
    public DoubleLinkedList<Album> mostrarAlbumes() {
        return albumes;
    }

    public void añadirAlbum(Album album) {
        albumes.addLast(album);
        crearPersonaAlbum(album.getIdAlbum().toString());
    }

    public void eliminarAlbum(Album album) {
        albumes.remove(album);
        album.eliminar();
    }
    ////////////////////////////////////////////////////////////////////////

    public Set<Persona> mostrarPersonas() {
        Set<Persona> personas = new HashSet();
        Iterator<Album> it = albumes.iterador();
        while (it.hasNext()) {

            Album a = it.next();
            ListIterator<Fotografia> lit = a.mostrarFotos().listIterator();
            boolean bandera = true;
            while (lit.hasNext() && bandera) {
                Fotografia foto = lit.next();
                Iterator<String> it2 = foto.getEtiquetas().iterador();

                while (it2.hasNext()) {
                    personas.add(leer(it2.next()));
                }

                if (foto.equals((a.mostrarFotos().getRear()).getContent())) {
                    bandera = false;
                }

            }

        }

        return personas;
    }

    public CircularQueue<Fotografia> mostrarTodaLasFotos() {
        CircularQueue<Fotografia> fotos = new CircularQueue();
        Iterator<Album> it = albumes.iterador();
        while (it.hasNext()) {

            Album a = it.next();
            ListIterator<Fotografia> lit = a.mostrarFotos().listIterator();
            boolean bandera = true;
            while (lit.hasNext() && bandera) {
                Fotografia foto = lit.next();
                fotos.enqueue(foto);

                if (foto.equals((a.mostrarFotos().getRear()).getContent())) {
                    bandera = false;
                }

            }

        }
        return fotos;

    }

}
