/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.TDAs;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class DoubleNode<E> {

    private E content;
    private DoubleNode<E> next;
    private DoubleNode<E> previous;

    public DoubleNode(E content) {
        this.content = content;
    }

    public DoubleNode<E> getNext() {
        return next;
    }

    public void setNext(DoubleNode<E> next) {
        this.next = next;
    }

    public DoubleNode<E> getPrevious() {
        return previous;
    }

    public void setPrevious(DoubleNode<E> previous) {
        this.previous = previous;
    }

    public E getContent() {
        return content;
    }

    public void setContent(E content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "DoubleNode{" + "content=" + content + ", next=" + next + ", previous=" + previous + '}';
    }

}
