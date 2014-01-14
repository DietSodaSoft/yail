package com.dietsodasoftware.yail.xmlrpc.service;

import com.dietsodasoftware.yail.xmlrpc.model.Model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class InfusionsoftModelCollectionResults<T extends Model> implements Iterable<T> {
	
	private final Object[] resultSet;
	private final Class<T> modelClass;
	
	public InfusionsoftModelCollectionResults(Class<T> modelClass, Object[] rawResponse){
		this.resultSet = rawResponse;
		this.modelClass = modelClass;
	}
	
	public Object[] getRawResults(){
		return resultSet;
	}

	@Override
	public String toString() {
		return "InfusionsoftModelCollectionResults [length = " +length()+ ", rawResponse=" + resultSet + "], " + valuesString();
	}
	
	public String valuesString(){
		final StringBuilder sb = new StringBuilder("Values [").append("\n");
		for(Object o: resultSet){
			sb.append("\t").append(o).append("\n");
		}
		
		return sb.append("]").toString();
	}
	
	public int length(){
		final int length;
		if(resultSet == null){
			length = 0;
		} else {
			length = getRawResults().length;
		}

		return length;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new ResultsIterator<T>();
	}
	

	private class ResultsIterator<IT> implements Iterator<IT>{
		private final int resultSetCount;
		private final AtomicInteger index = new AtomicInteger(0);
		private final Constructor<IT> ctor;
		
		@SuppressWarnings("unchecked")
		public ResultsIterator(){
			
			if(resultSet == null){
				resultSetCount = 0;
			} else {
				resultSetCount = resultSet.length;
			}

			ctor = (Constructor<IT>) Model.getModelMapConstructor(modelClass);
		}

		@Override
		public boolean hasNext() {
			final boolean has;
			if(resultSetCount == 0){
				has = false;
			} else if(index.get() < resultSetCount) {
				has = true;
			} else {
				has = false;
			}
			
			return has;
		}

		@Override
		public IT next() {
			if(!hasNext()){
				throw new IllegalStateException("No more elements (" +index.get()+ " of " +resultSetCount+ ")");
			}

			@SuppressWarnings("unchecked")
			final Map<String, Object> next = (Map<String, Object>) resultSet[index.getAndIncrement()];
			if(next == null){
				return null;
			}
			try {
				return ctor.newInstance(Collections.unmodifiableMap(next));
			} catch (IllegalArgumentException e) {
				throw new RuntimeException("Unable to create instance of " + modelClass + ".", e);
			} catch (InstantiationException e) {
				throw new RuntimeException("Unable to create instance of " + modelClass + ".", e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException("Unable to create instance of " + modelClass + ".", e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException("Unable to create instance of " + modelClass + ".", e);
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Immutable result set.  Cannot remove elements.");
		}
		
	}

	
}
