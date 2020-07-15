/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.time.LocalDate;
import java.util.Set;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public interface IBuscable<E> {

    E buscarXFecha(E coleccion, LocalDate fechaInicial, LocalDate fechaFinal);

    E buscarXLugar(E coleccion, String lugar);

    E buscarXPersonas(E coleccion, Set<String> personas);

    E buscarXPersonaLugar(E coleccion, Set<String> personas, String lugar);

    E buscarXPersonaFecha(E coleccion, Set<String> personas, LocalDate fechaInicial, LocalDate fechaFinal);

    E buscarXLugarFecha(E coleccion, String lugar, LocalDate fechaInicial, LocalDate fechaFinal);

}
