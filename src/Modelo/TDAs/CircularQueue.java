package Modelo.TDAs;

import java.util.Comparator;
import java.util.ListIterator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Luis A. Sarango-Parrales
 * @param <E>
 */
public class CircularQueue<E> {

    private CircularDoubleLinkedList<E> list;

    public CircularQueue(CircularDoubleLinkedList<E> lista) {
        list = lista;
    }

    public CircularQueue() {
        list = new CircularDoubleLinkedList();
    }

    public void enqueue(E element) {
        list.insertLast(element);
    }

    public DoubleNode<E> dequeue() {
        return list.removeFirst();
    }

    public DoubleNode<E> getFront() {
        return list.getHeader();
    }

    public void setFront(DoubleNode<E> front) {
        list.setHeader(front);
    }

    public DoubleNode<E> getRear() {
        return list.getLast();
    }

    public void setRear(DoubleNode<E> rear) {
        list.setLast(rear);
    }

    public CircularDoubleLinkedList<E> getList() {
        return list;
    }

    public void setList(CircularDoubleLinkedList<E> list) {
        this.list = list;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void remove(E element) {
        list.remove(element);
    }

    public boolean contains(E element) {
        return list.contains(element);
    }

    public ListIterator<E> listIterator() {
        return list.listIterator();
    }

    public CircularQueue<E> findAllByOne(Comparator cmp) {
        return new CircularQueue(list.findAllByOne(cmp));
    }

    public CircularQueue<E> findBetween(Comparator cmp) {
        return new CircularQueue(list.findBetween(cmp));
    }
    
    public int size(){
        return list.size();
    }

    @Override
    public String toString() {
        return "CircularQueue{" + "list=" + list + '}';
    }

}
