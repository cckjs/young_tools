package com.young.tools.common.util.structures.tree.binary;

public interface Tree<AnyType extends Comparable<? super AnyType>> {

    public enum Sort{
    	asc,desc;
    }
	
	public void makeEmpty();
	
	public boolean isEmpty();
	
	public boolean contains(AnyType element);
	
	public AnyType findMin() throws Exception;
	
	public AnyType findMax() throws Exception;
	
	public void insert(AnyType element);
	
	public void remove(AnyType element);
	
	public void printTree(Sort sort);
	
	public int size();
}
