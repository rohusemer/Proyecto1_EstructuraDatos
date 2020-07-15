/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public interface ICrud<E> {

    void crear();

    E leer(String id);

    void actualizar();

    void eliminar();

    Long obtenerUltimoId();
}
