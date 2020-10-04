package de.relluem94.vulcan.toolbox.generators;

import java.util.ArrayList;

public class FixedSizeList<T> extends ArrayList<T>{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FixedSizeList(int capacity) {
        super(capacity);
        for (int i = 0; i < capacity; i++) {
            super.add(null);
        }
    }

    public FixedSizeList(T[] initialElements) {
        super(initialElements.length);
        for (T loopElement : initialElements) {
            super.add(loopElement);
        }
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Elements may not be cleared from a fixed size List.");
    }

    @Override
    public boolean add(T o) {
        throw new UnsupportedOperationException("Elements may not be added to a fixed size List, use set() instead.");
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException("Elements may not be added to a fixed size List, use set() instead.");
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException("Elements may not be removed from a fixed size List.");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Elements may not be removed from a fixed size List.");
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Elements may not be removed from a fixed size List.");
    }
}