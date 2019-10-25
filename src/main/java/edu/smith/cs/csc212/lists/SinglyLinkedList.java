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
	public T removeFront() {
		checkNotEmpty();
		//throw new TODOErr();
		return this.removeIndex(0);
		
	}

	@Override
	public T removeBack() {
		//throw new TODOErr();
		return this.removeIndex(size()-1);
		
	}

	@Override
	public T removeIndex(int index) {
		//throw new TODOErr();
		checkNotEmpty();
		
		
		
	}

	@Override
	public void addFront(T item) {
		this.start = new Node<T>(item, start);
	}

	@Override
	public void addBack(T item) {
		//throw new TODOErr();
		//looping
		if (this.start == null) {
			this.addFront(item);
			return;
		} else {
		
		int counter = 0;
		Node<T> n = this.start;
		while (n.next != null) {
			counter++;
			n = n.next;
		}
		
		this.addIndex(counter, item);
		}
		
		
		
	}

	@Override
	public void addIndex(int index, T item) {
		//throw new TODOErr();
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
		//throw new TODOErr();
		return this.getIndex(0);
	}

	@Override
	public T getBack() {
		checkNotEmpty();
		//throw new TODOErr();
		return this.getIndex(size()-1);
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
		throw new TODOErr();
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
