/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Extra;

import Modelo.Album;
import java.util.Iterator;
import javafx.collections.ObservableList;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class Extra {

    public static int getIndexFromObs(ObservableList<Album> obs, String id) {
        Iterator<Album> it = obs.iterator();
        for (int i = 0; it.hasNext(); i++) {
            Album a = it.next();

            if ((a.getIdAlbum().toString()).equals(id)) {
                System.out.println("idAlbum: " + i);
                return i;
            }
        }
        return -1;
    }

}
