package edu.smith.cs.csc212.lists;

import me.jjfoley.adt.ListADT;
import me.jjfoley.adt.errors.BadIndexError;
import me.jjfoley.adt.errors.EmptyListError;
import me.jjfoley.adt.errors.TODOErr;

/**
 * This is a data structure that has an array inside each node of an ArrayList.
 * Therefore, we only make new nodes when they are full. Some remove operations
 * may be easier if you allow "chunks" to be partially filled.
 * 
 * @author jfoley
 * @param <T> - the type of item stored in the list.
 */
public class ChunkyArrayList<T> extends ListADT<T> {
	/**
	 * How big is each chunk?
	 */
	private int chunkSize;
	/**
	 * Where do the chunks go?
	 */
	private GrowableList<FixedSizeList<T>> chunks;

	/**
	 * Create a ChunkedArrayList with a specific chunk-size.
	 * @param chunkSize - how many items to store per node in this list.
	 */
	public ChunkyArrayList(int chunkSize) {
		this.chunkSize = chunkSize;
		chunks = new GrowableList<>();
	}
	
	private FixedSizeList<T> makeChunk() {
		return new FixedSizeList<>(chunkSize);
	}

	@Override
	public T removeFront() {
		//throw new TODOErr();
		checkNotEmpty();
		
		T frontValue = this.chunks.getFront().getFront();
		chunks.getFront().removeFront();
		// if chunk empty, delete thqt too //need this on remove index and remove back
		if (chunks.getFront().isEmpty()) {
			chunks.removeFront();
		}
		return frontValue;
	}

	@Override
	public T removeBack() {
		//throw new TODOErr();
		checkNotEmpty();
		T backValue = this.chunks.getBack().getBack();
		chunks.getBack().removeBack();
		if (chunks.getBack().isEmpty()) {
			chunks.removeBack();
		}
		return backValue;
	}

	@Override
	public T removeIndex(int index) {
		//throw new TODOErr();
		int chunkIndex = 0;
		int start = 0;
		//check bad index here 
		checkNotEmpty();
		
		for (FixedSizeList<T> chunk : this.chunks) {
			// calculate bounds of this chunk.
			int end = start + chunk.size();
			
			// Check whether the index should be in this chunk:
			if (start <= index && index < end) {
				T removedItem = chunk.removeIndex(index - start);
				if (chunk.isEmpty()) {
					chunks.removeIndex(chunkIndex);
				}
				return removedItem;
			}
			
			// update bounds of next chunk.
			start = end;
			chunkIndex++;
		}
		
		
		throw new BadIndexError(index);
	}

	@Override
	public void addFront(T item) {
		if (chunks.isEmpty() || chunks.getFront().isFull()) {
			chunks.addFront(makeChunk());
		}
		chunks.getFront().addFront(item);
	}

	@Override
	public void addBack(T item) {
		//throw new TODOErr();
		if (chunks.isEmpty() || chunks.getBack().isFull()) {
			chunks.addBack(makeChunk());
		}
		//this.addIndex(size(), item);
		FixedSizeList<T> lastChunk = chunks.getBack();
		if (lastChunk.isFull()) {
			FixedSizeList<T> newChunk = makeChunk();
			newChunk.addFront(item);
			chunks.addBack(newChunk);
		} else {
			lastChunk.addBack(item);
		}
		
	}

	@Override
	public void addIndex(int index, T item) {
		// THIS IS THE HARDEST METHOD IN CHUNKY-ARRAY-LIST.
		// DO IT LAST.
		
		
		int chunkIndex = 0;
		int start = 0;
		//check bad index here 
		
		if (index == size()) {
			this.addBack(item);
			return;
		}
		
		
		if (this.chunks.isEmpty()) {
			this.chunks.addFront(this.makeChunk());
		}
		
		for (FixedSizeList<T> chunk : this.chunks) {
			// calculate bounds of this chunk.
			int end = start + chunk.size();
			//System.out.println("end " + String.valueOf(end));
			//System.out.println("size " + String.valueOf(chunk.size()));
			
			
			// Check whether the index should be in this chunk:
			if (start <= index && index < end) {
				//System.out.println("start " + String.valueOf(start));
				if (chunk.isFull()) {
					// check can roll to next
					// or need a new chunk
					//throw new TODOErr();
					//make new chunk
					FixedSizeList<T> newChunk = this.makeChunk();
					//loop
					for (int i = index - start; i < end - start; i++) {
						newChunk.addBack(chunk.getIndex(i));
					}
					for (int i = end - start - 1; i >= index - start; i--) {
						chunk.removeIndex(i);
					}
					chunk.addIndex(index - start, item);
					chunks.addIndex(chunkIndex + 1, newChunk);
				} else {
					// put right in this chunk, there's space.
					//throw new TODOErr();
					chunk.addIndex(index - start, item);
					
				}	
				// upon adding, return.
				return;
			}
			
			// update bounds of next chunk.
			start = end;
			chunkIndex++;
		}
		throw new BadIndexError(index); // problem passing 
	}
	
	@Override
	public T getFront() {
		return this.chunks.getFront().getFront();
	}

	@Override
	public T getBack() {
		return this.chunks.getBack().getBack();
	}


	@Override
	public T getIndex(int index) {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
		int start = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			// calculate bounds of this chunk.
			int end = start + chunk.size();
			
			// Check whether the index should be in this chunk:
			if (start <= index && index < end) {
				return chunk.getIndex(index - start);
			}
			
			// update bounds of next chunk.
			start = end;
		}
		throw new BadIndexError(index);
	}
	
	@Override
	public void setIndex(int index, T value) {
		//throw new TODOErr();
		//will this work?
		//this.chunks.getIndex(index).setIndex(index, value);
		checkNotEmpty();
		int start = 0;
		
		if (this.size()<= index || index < 0) {
			throw new BadIndexError(index);
		}
		for (FixedSizeList<T> chunk : this.chunks) {
			int end = start + chunk.size();
			
			//Check whether index should be in this chunk
			if (start <= index && index < end) {
				chunk.setIndex(index - start, value); 
			}
			
			//update bounds of next chunk
			start = end;
		}
		//bad index here
	}

	@Override
	public int size() {
		int total = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			total += chunk.size();
		}
		return total;
	}

	@Override
	public boolean isEmpty() {
		return this.chunks.isEmpty();
	}
}