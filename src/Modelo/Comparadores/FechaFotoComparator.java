/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Comparadores;

import Modelo.Fotografia;
import java.time.LocalDate;
import java.util.Comparator;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class FechaFotoComparator implements Comparator<Fotografia> {

    private LocalDate fechaInicial, fechaFinal;

    public FechaFotoComparator(LocalDate fechaInicial, LocalDate fechaFinal) {
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
    }

    @Override
    public int compare(Fotografia o1, Fotografia o2) {
        boolean condicion1 = (o1.getFecha()).compareTo(fechaInicial) >= 0;
        boolean condicion2 = (o1.getFecha()).compareTo(fechaFinal) <= 0;
        if (condicion1 && condicion2) {
            return 1;
        } else {
            return 0;
        }
    }

}
