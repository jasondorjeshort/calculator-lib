package io.github.endreman0.calculator.util;

public class StringIterator{
	/**The index of the next character to display.*/
	private int index = 0;
	private String string;
	public StringIterator(String s){this(s, 0);}
	public StringIterator(String s, int startIndex){
		string = s;
		index = startIndex;
	}
	public char next(){return string.charAt(index++);}
	public boolean hasNext(){return index < string.length();}
	public char peek(){return string.charAt(index);}
	public String peek(int length){return string.substring(index, index + length);}
	public String readTo(String... targets){
		StringBuilder sb = new StringBuilder();
		if(check(targets)) return sb.append(next()).toString();//If it starts with a target, return the target.
		
		do sb.append(next()); while(hasNext() && !check(targets));
		return sb.toString();
	}
	private boolean check(String[] targets){
		String remaining = string.substring(index);
		for(String target : targets)
			if(remaining.startsWith(target)) return true;
		return false;
	}
	public void trim(){
		while(hasNext() && Character.isWhitespace(peek())) ++index;
	}
	public int length(){return string.length();}
	public int remaining(){return string.length() - index;}
	public String remainingString(){return string.substring(index);}
	
	@Override public String toString(){return "StringIterator[\"" + string + "\"@" + index + "]";}
}
