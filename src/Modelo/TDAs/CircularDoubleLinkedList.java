/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.TDAs;

import java.util.Comparator;
import java.util.ListIterator;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class CircularDoubleLinkedList<E> {

    private DoubleNode<E> header;
    private DoubleNode<E> last;
    private int efectivo;

    public CircularDoubleLinkedList() {
        header = last = null;
        efectivo = 0;
    }

  
    public DoubleNode<E> getHeader() {
        return header;
    }

    public void setHeader(DoubleNode<E> header) {
        this.header = header;
    }

    public DoubleNode<E> getLast() {
        return last;
    }

    public void setLast(DoubleNode<E> last) {
        this.last = last;
    }

    public boolean insertLast(E element) {
        DoubleNode<E> nodo = new DoubleNode<>(element);
        if (element == null) {
            return false;
        } else if (isEmpty()) {
            header = last = nodo;
        } else {
            last.setNext(nodo);
            nodo.setPrevious(last);
            nodo.setNext(header); // circular
            header.setPrevious(nodo); //circular
            last = nodo;
        }

        efectivo++;
        return true;
    }

    public DoubleNode<E> removeFirst() {
        DoubleNode<E> previousHeader = new DoubleNode(header.getContent());
        if (isEmpty()) {
            return previousHeader;
        } else if (header == last) {
            header = last = null;
        } else {
            header = header.getNext();
            (header.getPrevious()).setNext(null);
            (header.getPrevious()).setPrevious(null);
            header.setPrevious(last);// circular
            last.setNext(header);//circular
        }
        efectivo--;
        return previousHeader;
    }

    public boolean removeLast() {
        if (this.isEmpty()) {
            return false;
        } else if (header == last) {
            header = last = null;
        } else {
            last = last.getPrevious();
            (last.getNext()).setPrevious(null);
            (last.getNext()).setNext(null);
            header.setPrevious(last);//circular
            last.setNext(header);//circular
        }
        efectivo--;
        return true;
    }

    public boolean isEmpty() {
        return (header == null && last == null);
    }

    public boolean contains(E element) {
        if (element == null || isEmpty()) {
            return false;
        }
        for (DoubleNode<E> i = header; i != last; i = i.getNext()) {
            if ((i.getContent()).equals(element)) {
                return true;
            }
        }
        if ((last.getContent()).equals(element)) {
            return true;
        }
        return false;
    }

    public boolean remove(E element) {

        if (element == null) {
            return false;
        } else if ((header.getContent()).equals(element)) {

            removeFirst();
            return true;
        } else if ((last.getContent()).equals(element)) {

            removeLast();
            return true;
        }
        for (DoubleNode<E> i = header; i != last; i = i.getNext()) {
            if (i.getContent().equals(element)) {
                i.getPrevious().setNext(i.getNext());
                i.getNext().setPrevious(i.getPrevious());
                i.setNext(null);
                i.setPrevious(null);
                efectivo--;
                return true;
            }
        }

        return false;
    }

    public int size() {
        return efectivo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof CircularDoubleLinkedList)) {
            return false;
        }
        CircularDoubleLinkedList<E> lista = (CircularDoubleLinkedList<E>) o;

        if (efectivo != lista.efectivo) {
            return false;
        }

        DoubleNode<E> nodo = lista.header;

        for (DoubleNode<E> i = header; i != last; i = i.getNext()) {
            if (!(nodo.getContent()).equals(i.getContent())) {
                return false;
            }
            nodo = nodo.getNext();
        }

        if ((nodo.getContent()).equals(last.getContent())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        if (isEmpty()) {
            return "[]";
        }
        s.append("[");

        for (DoubleNode<E> i = header; i != last; i = i.getNext()) {
            if (i != last) {
                s.append(i.getContent()).append(", ");
            }
        }

        s.append(last.getContent()).append("]");
        return s.toString();
    }

    public ListIterator<E> listIterator() {
        ListIterator<E> it = new ListIterator<E>() {
            private DoubleNode<E> node1 = header;
            private DoubleNode<E> node2 = last;

            @Override
            public boolean hasNext() {
                return node1 != null;
            }

            @Override
            public E next() {
                E tmp = node1.getContent();
                node1 = node1.getNext();
                return tmp;
            }

            @Override
            public boolean hasPrevious() {
                return node2 != null;
            }

            @Override
            public E previous() {
                E temp = node2.getContent();
                node2 = node2.getPrevious();
                return temp;
            }

            @Override
            public int nextIndex() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int previousIndex() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void set(E e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void add(E e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        };
        return it;

    }

    //Busquedas unarias
    // Un lugar
    public CircularDoubleLinkedList<E> findAllByOne(Comparator cmp) {
        CircularDoubleLinkedList<E> results = new CircularDoubleLinkedList();
        ListIterator<E> it = this.listIterator();

        boolean bandera = true;
        while (it.hasNext() && bandera) {

            E element = it.next();
            if (cmp.compare(element, null) == 0) {
                results.insertLast(element);
            }
            if (element.equals(last.getContent())) {
                bandera = false;
            }

        }
        return results;
    }

    //Entre fechas y una fecha
    public CircularDoubleLinkedList<E> findBetween(Comparator cmp) {
        CircularDoubleLinkedList<E> results = new CircularDoubleLinkedList();
        ListIterator<E> it = this.listIterator();
        boolean bandera = true;
        while (it.hasNext() && bandera) {
            E element = it.next();
            if (cmp.compare(element, null) != 0) {
                results.insertLast(element);
            }
            if (element.equals(last.getContent())) {
                bandera = false;
            }
        }
        return results;
    }

}
