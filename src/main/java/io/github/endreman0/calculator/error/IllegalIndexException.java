package io.github.endreman0.calculator.error;

public class IllegalIndexException extends IndexOutOfBoundsException{
	private static final long serialVersionUID = 1L;
	public IllegalIndexException(int index, int size){super("Index: " + index + ", Size: " + size);}
	public IllegalIndexException(int index){super("Index: " + index);}
}
