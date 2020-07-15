/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Comparadores;

import Modelo.Fotografia;
import java.util.Comparator;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class LugarFotoComparator implements Comparator<Fotografia> {

    private String lugar;

    public LugarFotoComparator(String lugar) {
        this.lugar = lugar;
    }

    @Override
    public int compare(Fotografia o1, Fotografia o2) {
        return lugar.compareTo(o1.getLugar());
    }

}
