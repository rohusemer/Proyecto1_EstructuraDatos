/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.TDAs;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class DoubleLinkedList<E> {

    private DoubleNode<E> header;
    private DoubleNode<E> last;
    private int efectivo;

    public DoubleLinkedList() {
        header = last = null;
        efectivo = 0;
    }

    public boolean addFirst(E element) {
        DoubleNode<E> nodo = new DoubleNode(element);
        if (element == null) {
            return false;
        } else if (isEmpty()) {
            header = last = nodo;
        } else {
            nodo.setNext(header);
            header.setPrevious(nodo);
            header = nodo;
        }
        efectivo++;
        return true;
    }

    public boolean addLast(E element) {
        DoubleNode<E> nodo = new DoubleNode(element);
        if (element == null) {
            return false;
        } else if (isEmpty()) {
            header = last = nodo;
        } else {
            last.setNext(nodo);
            nodo.setPrevious(last);
            last = nodo;
        }

        efectivo++;
        return true;
    }

    public DoubleNode<E> getFirst() {
        return header;
    }

    public DoubleNode<E> getLast() {
        return last;
    }

    public boolean removeFirst() {
        if (isEmpty()) {
            return false;
        } else if (header == last) {
            header = last = null;
        } else {
            header = header.getNext();
            header.getPrevious().setNext(null);
            header.setPrevious(null);
        }
        efectivo--;
        return true;
    }

    public boolean removeLast() {
        if (this.isEmpty()) {
            return false;
        } else if (header == last) {
            header = last = null;
        } else {
            last = last.getPrevious();
            last.getNext().setPrevious(null);
            last.setNext(null);
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
        for (DoubleNode<E> i = header; i != null; i = i.getNext()) {
            if (i.getContent().equals(element)) {
                return true;
            }
        }
        return false;
    }

    public boolean remove(E element) {
        if (element == null) {
            return false;
        } else if (header.getContent().equals(element)) {
            removeFirst();
            return true;
        } else if (last.getContent().equals(element)) {
            removeLast();
            return true;
        }

        for (DoubleNode<E> i = header; i != null; i = i.getNext()) {
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
        if (o == null || !(o instanceof DoubleLinkedList)) {
            return false;
        }
        DoubleLinkedList<E> lista = (DoubleLinkedList<E>) o;

        if (efectivo != lista.efectivo) {
            return false;
        }

        DoubleNode<E> nodo = lista.header;

        for (DoubleNode<E> i = header; i != null; i = i.getNext()) {
            if (!nodo.getContent().equals(i.getContent())) {
                return false;
            }

            nodo = nodo.getNext();
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        if (isEmpty()) {
            return "[]";
        }
        s.append("[");

        for (DoubleNode<E> i = header; i != null; i = i.getNext()) {
            if (i != last) {
                s.append(i.getContent()).append(",");
            } else {
                s.append(i.getContent()).append("]");
            }
        }
        return s.toString();
    }

    public Iterator<E> iterador() {
        return new Iterator() {
            private DoubleNode<E> p = header;

            @Override
            public boolean hasNext() {
                return p != null;
            }

            @Override
            public E next() {
                E tmp = p.getContent();
                p = p.getNext();
                return tmp;
            }
        };

    }

    public DoubleLinkedList<E> findBetween(Comparator cmp) {
        DoubleLinkedList<E> results = new DoubleLinkedList();
        Iterator<E> it = this.iterador();
        while (it.hasNext()) {
            E element = it.next();

            if (cmp.compare(element, null) != 0) {
                results.addLast(element);
            }

        }
        return results;
    }

    public Set<E> toSet() {
        Set<E> newSet = new HashSet();
        Iterator<E> it = iterador();
        while (it.hasNext()) {
            newSet.add(it.next());
        }
        return newSet;
    }

}
