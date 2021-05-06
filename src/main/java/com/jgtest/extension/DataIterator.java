package com.jgtest.extension;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

public class DataIterator implements Iterator<Object[]>{

    private final Iterator<Object> iterator;

    public DataIterator(Stream<Object> stream){
        this.iterator = stream.iterator();
    }

    public DataIterator(Object[] stream){
        this.iterator = Arrays.stream(stream).iterator();
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public Object[] next() {
        return new Object[] {this.iterator.next()};
    }

    @Override
    public void remove() {
        this.iterator.remove();
    }
}
