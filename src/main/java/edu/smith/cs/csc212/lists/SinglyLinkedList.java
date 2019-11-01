package edu.smith.cs.csc212.lists;

import me.jjfoley.adt.ListADT;
import me.jjfoley.adt.errors.BadIndexError;
import me.jjfoley.adt.errors.TODOErr;

/**
 * A Singly-Linked List is a list that has only knowledge of its very first
 * element. Elements after that are chained, ending with a null node.
 * 
 * @author jfoley
 *
 * @param <T> - the type of the item stored in this list.
 */
public class SinglyLinkedList<T> extends ListADT<T> {
	/**
	 * The start of this list. Node is defined at the bottom of this file.
	 */
	Node<T> start;

	@Override
	public T removeFront() {//all set
		checkNotEmpty();
		Node<T> n = this.start;
		T deletedFront = n.value;
		this.start = n.next; //value after the front becomes the front so size decreases
		
		
		return deletedFront;
		
		
	}

	@Override
	public T removeBack() {
		checkNotEmpty();
		
		Node<T> n = this.start;
		if (n.next == null) { //when 1 thing in list
			return this.removeFront();
		}
		
		while (n.next.next != null) { //while two ahead is not null
			n = n.next; //n becomes next node (stops at C)
		}
		T deletedEnd = n.next.value;
		n.next = null; //set D value to null which deletes it from list
		return deletedEnd;
		
	}

	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		
		Node<T> n = this.start;
		for (int i = 0; i < index-1; i++) { //
			n = n.next;
		}
		if (n.next == null) {
			T deletedOldNode = n.value;
			n = null;
			return deletedOldNode;
		}
		T deletedOldNode = n.next.value; //node to be deleted (C)
		n.next = n.next.next;
		return deletedOldNode;
		
		
		
		
		
	}

	@Override
	public void addFront(T item) {
		this.start = new Node<T>(item, start);
	}

	@Override
	public void addBack(T item) {
		//looping
		if (this.start == null) {
			this.addFront(item);
			return;
		} else {
		
		Node<T> n = this.start;
		while (n.next != null) {
			n = n.next;
		}
		n.next = new Node<T>(item, null);
		
		}
		
		
		
	}

	@Override
	public void addIndex(int index, T item) {
		if (this.size()< index || index < 0) {
			throw new BadIndexError(index);
		}
		
		Node<T> n = this.start;
		for (int i = 0; i < index-1; i++) { 
			n = n.next;
		}
		Node<T> oldNode = n.next;
		n.next = new Node<T>(item, oldNode);
		
	}

	@Override
	public T getFront() {
		checkNotEmpty();
		Node<T> n = this.start;
		T frontValue = this.start.value;
		return frontValue;
	}

	@Override
	public T getBack() {
		checkNotEmpty();
		Node<T> n = this.start;
		while (n.next != null) {
			n = n.next; //stops at last value
		}
		T backValue = n.value;
		return backValue;
	}

	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			if (at++ == index) {
				return n.value;
			}
		}
		throw new BadIndexError(index);
	}

	@Override
	public void setIndex(int index, T value) {
		checkNotEmpty();
		if (this.size()<= index || index < 0) {
			throw new BadIndexError(index);
		}
		
		Node<T> n = this.start;
		for (int i = 0; i < index; i++) { 
			n = n.next; //want n to be index we are setting
		}
		n.value = value;
		
		
	}

	@Override
	public int size() {
		int count = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			count++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		return this.start == null;
	}

	/**
	 * The node on any linked list should not be exposed. Static means we don't need
	 * a "this" of SinglyLinkedList to make a node.
	 * 
	 * @param <T> the type of the values stored.
	 */
	private static class Node<T> {
		/**
		 * What node comes after me?
		 */
		public Node<T> next;
		/**
		 * What value is stored in this node?
		 */
		public T value;

		/**
		 * Create a node with no friends.
		 * 
		 * @param value - the value to put in it.
		 * @param next - the successor to this node.
		 */
		public Node(T value, Node<T> next) {
			this.value = value;
			this.next = next;
		}
	}

}
