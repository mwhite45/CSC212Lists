package edu.smith.cs.csc212.lists;


import me.jjfoley.adt.ListADT;
import me.jjfoley.adt.errors.BadIndexError;
import me.jjfoley.adt.errors.TODOErr;

/**
 * A Doubly-Linked List is a list based on nodes that know of their successor and predecessor.
 * @author jfoley
 *
 * @param <T>
 */
public class DoublyLinkedList<T> extends ListADT<T> {
	/**
	 * This is a reference to the first node in this list.
	 */
	private Node<T> start;
	/**
	 * This is a reference to the last node in this list.
	 */
	private Node<T> end;
	
	/**
	 * A doubly-linked list starts empty.
	 */
	public DoublyLinkedList() {
		this.start = null;
		this.end = null;
	}
	

	@Override
	public T removeFront() {
		checkNotEmpty();
		//throw new TODOErr();
		Node<T> n = this.start;
		T deletedFront = n.value;
		this.start = n.after;
		
		
		return deletedFront;
		
	}

	@Override
	public T removeBack() {
		checkNotEmpty();
		//throw new TODOErr();
		//not deleting item from back size stays the same.
		//if 1 thing left 
		if (size() == 1) {
			T deletedEnd = start.value;
			start = end = null;
			return deletedEnd;
		}
		
		T deletedEnd = this.end.value;
		this.end = this.end.before;
		this.end.after = null;
		return deletedEnd;
	}

	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		//throw new TODOErr();
		if (index == 0) {
			return removeFront();
		}
		
		Node<T> n = this.start;
		for (int i = 0; i < index-1; i++) { 
			n = n.after;
		}
		if (n.after == null) { //1 item in list
			T deletedOldNode = n.value;
			n = null;
			return deletedOldNode;
		}
			
		T deletedOldNode = n.after.value; //node to be deleted (C)
		n.after = n.after.after;
		if (n.after != null) {
			n.after.before = n;
		}
		return deletedOldNode;
	}

	@Override
	public void addFront(T item) {
		//throw new TODOErr();
		if (this.size() == 0) {
			start = end = new Node<T>(item);
			return; //edit
		} else {
		
		Node<T> oldNode = this.start;
		this.start = new Node<T>(item);
		this.start.after = oldNode;
		this.start.after.before = this.start;
		
		}
		
	}

	@Override
	public void addBack(T item) {
		if (end == null) {
			start = end = new Node<T>(item);
		} else {
			Node<T> secondLast = end;
			end = new Node<T>(item);
			end.before = secondLast;
			secondLast.after = end;
		}
	}

	@Override
	public void addIndex(int index, T item) { //check linking didnt link first one to second.
		//throw new TODOErr();
		
		if (this.size() < index || index < 0) {
			throw new BadIndexError(index);
		}
		
		if (this.size() == 0) {//added this could be wrong - prob not
			start = end = new Node<T>(item);
			return;
		}
		
		if (index == 0) {
			this.addFront(item); //needs to be in terms of node
			return;
		}
		
		if (index == size()) {
			this.addBack(item);
			return;
		}
		
		Node<T> n = this.start;
		for (int i = 0; i < index-1; i++) { 
			n = n.after;
		}
		
		Node<T> nodeBeforeNew = n;
		Node<T> oldNode = n.after;
		n.after = new Node<T>(item);
		n.after.after = oldNode;
		n.after.before = nodeBeforeNew;

		
	}

	@Override
	public T getFront() {
		//throw new TODOErr();
		checkNotEmpty();
		
		T frontValue = this.start.value;
		return frontValue;
	}

	@Override
	public T getBack() {
		//throw new TODOErr();
		checkNotEmpty();
		T backValue = this.end.value;
		return backValue;
		
		
	}
	
	@Override
	public T getIndex(int index) {
		//throw new TODOErr();
		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.after) {
			if (at++ == index) {
				return n.value;
			}
		}
		throw new BadIndexError(index);
	}
	
	public void setIndex(int index, T value) {
		//throw new TODOErr();
		checkNotEmpty();
		
		if (this.size()<= index || index < 0) {
			throw new BadIndexError(index);
		}
		
		Node<T> n = this.start;
		for (int i = 0; i < index; i++) { 
			n = n.after; //want n to be index we are setting
		}
		n.value = value;
	}
	
	@Override
	public int size() {
		//throw new TODOErr();
		int count = 0;
		for (Node<T> n = this.start; n != null; n = n.after) {
			count++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		//throw new TODOErr();
		return this.start == null;
	}
	
	/**
	 * The node on any linked list should not be exposed.
	 * Static means we don't need a "this" of DoublyLinkedList to make a node.
	 * @param <T> the type of the values stored.
	 */
	private static class Node<T> {
		/**
		 * What node comes before me?
		 */
		public Node<T> before;
		/**
		 * What node comes after me?
		 */
		public Node<T> after;
		/**
		 * What value is stored in this node?
		 */
		public T value;
		/**
		 * Create a node with no friends.
		 * @param value - the value to put in it.
		 */
		public Node(T value) {
			this.value = value;
			this.before = null;
			this.after = null;
		}
	}
}
