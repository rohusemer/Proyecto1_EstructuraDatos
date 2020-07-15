/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Modelo.Comparadores.FechaFotoComparator;
import Modelo.Comparadores.LugarFotoComparator;
import Modelo.TDAs.CircularDoubleLinkedList;
import Modelo.TDAs.CircularQueue;
import Modelo.TDAs.DoubleLinkedList;
import Persistencia.FotografiaPersistencia;
import Persistencia.Persistencia;
import java.time.LocalDate;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Set;
import javafx.scene.image.ImageView;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class Fotografia implements ICrud<Fotografia>, IBuscable<CircularQueue<Fotografia>> {

    private Long idFotografia;
    private Long idAlbum;
    private String ruta;
    private ImageView imagen;
    private String descripcion;
    private String lugar;
    private LocalDate fecha;
    private DoubleLinkedList<String> etiquetas;

    public Fotografia() {
        etiquetas = new DoubleLinkedList();
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta.trim();
        this.imagen = new ImageView(ruta);
    }

    public ImageView getImagen() {
        return imagen;
    }

    public void setImagen(ImageView imagen) {
        this.imagen = imagen;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Long getIdFotografia() {
        return idFotografia;
    }

    public void setIdFotografia(Long idFotografia) {
        this.idFotografia = idFotografia;
    }

    public Long getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(Long idAlbum) {
        this.idAlbum = idAlbum;
    }

    public DoubleLinkedList<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(DoubleLinkedList<String> etiquetas) {
        this.etiquetas = etiquetas;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.idFotografia);
        hash = 59 * hash + Objects.hashCode(this.idAlbum);
        hash = 59 * hash + Objects.hashCode(this.ruta);
        hash = 59 * hash + Objects.hashCode(this.descripcion);
        hash = 59 * hash + Objects.hashCode(this.lugar);
        hash = 59 * hash + Objects.hashCode(this.fecha);
        hash = 59 * hash + Objects.hashCode(this.etiquetas);
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
        final Fotografia other = (Fotografia) obj;
        if (!Objects.equals(this.ruta, other.ruta)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.lugar, other.lugar)) {
            return false;
        }
        if (!Objects.equals(this.idFotografia, other.idFotografia)) {
            return false;
        }
        if (!Objects.equals(this.idAlbum, other.idAlbum)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        if (!Objects.equals(this.etiquetas, other.etiquetas)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Fotografia{" + "idFotografia=" + idFotografia + ", idAlbum=" + idAlbum + ", ruta=" + ruta + ", imagen=" + imagen + ", descripcion=" + descripcion + ", lugar=" + lugar + ", fecha=" + fecha + ", etiquetas=" + etiquetas + '}';
    }

    ////////////////////////////////////////////////////////////////////////////
    // Persistencia
    @Override
    public void crear() {
        idFotografia = obtenerUltimoId() + 1l;
        (FotografiaPersistencia.getInstancia()).create(this);
    }

    private void crearFotografiaPersona(String alias) {
        (FotografiaPersistencia.getInstancia()).createFotografiaPersonas(idFotografia.toString(), alias);
    }

    @Override
    public Fotografia leer(String idFotografia) {
        return (FotografiaPersistencia.getInstancia()).read(idFotografia);
    }

    @Override
    public void actualizar() {
        (FotografiaPersistencia.getInstancia()).update(this);
    }

    @Override
    public void eliminar() {
        (FotografiaPersistencia.getInstancia()).delete(idFotografia.toString());
    }

    private void eliminarFotografiaPersona(String alias) {
        (FotografiaPersistencia.getInstancia()).deleteEtiquetaXAlias(alias);
    }

    @Override
    public Long obtenerUltimoId() {
        String id = Persistencia.mapaIds.get("Fotografia");
        if (id.equals("")) {
            return 0l;
        }
        return Long.parseLong(id);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Metodos basicos
    public void cambiarAlbum(Album anterior, Album nuevo) {
        if (anterior == null || nuevo == null || anterior.equals(nuevo)) {
            try {
                throw new Exception("Ingrese albumes validos");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {

            anterior.eliminarFoto(this);
            CircularQueue<Fotografia> fotos2 = nuevo.mostrarFotos();

            if (!fotos2.contains(this)) {
                nuevo.añadirFoto(this);
            }

            nuevo.crearAlbumFotografia(this.getIdFotografia().toString());
        }
    }

    public void etiquetarPersona(String alias) {
        etiquetas.addLast(alias);
        crearFotografiaPersona(alias);
    }

    public void eliminarPersona(String alias) {
        etiquetas.remove(alias);
        eliminarFotografiaPersona(alias);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Busquedas
    @Override
    public CircularQueue<Fotografia> buscarXFecha(CircularQueue<Fotografia> fotos, LocalDate fechaInicial, LocalDate fechaFinal) { // funciona
        return fotos.findBetween(new FechaFotoComparator(fechaInicial, fechaFinal));
    }

    @Override
    public CircularQueue<Fotografia> buscarXLugar(CircularQueue<Fotografia> fotos, String lugar) { // funciona
        return fotos.findAllByOne(new LugarFotoComparator(lugar));
    }

    @Override
    public CircularQueue<Fotografia> buscarXPersonas(CircularQueue<Fotografia> fotos, Set<String> personas) {// funciona pero se debe tener una lista con todas las fotografias sin importar el album
        CircularDoubleLinkedList<Fotografia> resultados = new CircularDoubleLinkedList();
        boolean bandera = true;
        ListIterator<Fotografia> it = fotos.listIterator();
        //Sustentacion persona que no aparezca etiqyetada
        String id = personas.iterator().next();
        while (it.hasNext() && bandera) {
            Fotografia foto = it.next();
            Set<String> etiquetas = (foto.getEtiquetas()).toSet();

            if (!etiquetas.contains(id) && !resultados.contains(foto)) {
                resultados.insertLast(foto);
            }
            if (foto.equals((fotos.getRear()).getContent())) {
                bandera = false;
            }
        }

        return new CircularQueue(resultados);
    }

    @Override
    public CircularQueue<Fotografia> buscarXPersonaLugar(CircularQueue<Fotografia> fotos, Set<String> personas, String lugar) { //funciona
        CircularDoubleLinkedList<Fotografia> resultados = new CircularDoubleLinkedList();
        boolean bandera = true;
        ListIterator<Fotografia> it = fotos.listIterator();

        while (it.hasNext() && bandera) {
            Fotografia foto = it.next();
            Set<String> etiquetas = (foto.getEtiquetas()).toSet();

            if (etiquetas.retainAll(personas) && (foto.getLugar()).equals(lugar) && !resultados.contains(foto)) {
                resultados.insertLast(foto);
            }
            if (foto.equals((fotos.getRear()).getContent())) {
                bandera = false;
            }
        }

        return new CircularQueue(resultados);
    }

    @Override
    public CircularQueue<Fotografia> buscarXPersonaFecha(CircularQueue<Fotografia> fotos, Set<String> personas, LocalDate fechaInicial, LocalDate fechaFinal) {//funciona
        CircularDoubleLinkedList<Fotografia> resultados = new CircularDoubleLinkedList();
        boolean bandera = true;
        ListIterator<Fotografia> it = fotos.listIterator();

        while (it.hasNext() && bandera) {
            Fotografia foto = it.next();
            Set<String> etiquetas = (foto.getEtiquetas()).toSet();

            boolean isFechaBetween = (foto.getFecha()).compareTo(fechaInicial) >= 0 && (foto.getFecha()).compareTo(fechaFinal) <= 0;

            if (etiquetas.retainAll(personas) && isFechaBetween && !resultados.contains(foto)) {
                resultados.insertLast(foto);
            }
            if (foto.equals((fotos.getRear()).getContent())) {
                bandera = false;
            }
        }

        return new CircularQueue(resultados);
    }

    @Override
    public CircularQueue<Fotografia> buscarXLugarFecha(CircularQueue<Fotografia> fotos, String lugar, LocalDate fechaInicial, LocalDate fechaFinal) { //funciona

        CircularDoubleLinkedList<Fotografia> resultados = new CircularDoubleLinkedList();
        boolean bandera = true;
        ListIterator<Fotografia> it = fotos.listIterator();

        while (it.hasNext() && bandera) {
            Fotografia foto = it.next();

            boolean isFechaBetween = (foto.getFecha()).compareTo(fechaInicial) >= 0 && (foto.getFecha()).compareTo(fechaFinal) <= 0;

            if ((foto.getLugar()).equals(lugar) && isFechaBetween && !resultados.contains(foto)) {
                resultados.insertLast(foto);
            }
            if (foto.equals((fotos.getRear()).getContent())) {
                bandera = false;
            }
        }

        return new CircularQueue(resultados);
    }

}
