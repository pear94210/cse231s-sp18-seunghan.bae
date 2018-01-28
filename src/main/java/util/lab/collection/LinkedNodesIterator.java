/*******************************************************************************
 * Copyright (C) 2016-2017 Dennis Cosgrove, Ben Choi
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package util.lab.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.wustl.cse231s.NotYetImplementedException;
import net.jcip.annotations.NotThreadSafe;

/**
 * @author Seunghan Bae
 * @author Ben Choi (benjaminchoi@wustl.edu)
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
@NotThreadSafe
/* package-private */ class LinkedNodesIterator<E> implements Iterator<E> {
	
	private final LinkedNodesCollection<E> collection;
	private LinkedNode<E> prev, curr;
	private boolean removable;
	
	public LinkedNodesIterator(LinkedNodesCollection<E> collection) {
		this.collection = collection;
		this.prev = null;
		this.curr = collection.getHeadNode();
		this.removable = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasNext() {
		return (this.curr.getNext() != null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E next() {
		if (this.hasNext()) {
			this.prev = this.curr;
			this.curr = this.curr.getNext();
			this.removable = true;
			return this.curr.getValue();
		}
		else throw new NoSuchElementException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove() {
		if (this.removable) {
			this.prev.setNext(this.curr.getNext());
			this.collection.decrementSize();
			this.removable = false;
		}
		else throw new IllegalStateException();
		
	}
}
