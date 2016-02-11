package io.github.endreman0.calculator.token;

import java.util.Iterator;

import io.github.endreman0.calculator.error.IllegalIndexException;
import io.github.endreman0.calculator.util.Utility;

public class TokenList implements Iterable<Token>{
	private Element first, last;
	public TokenList(){
		first = null;
		last = null;
	}
	public TokenList(Token... tokens){
		for(Token token : tokens) add(token);
	}
	public void add(Token token){
		if(token == null) throw new IllegalArgumentException("null tokens not allowed");
		if(first == null) first = (last = new Element(token, 0, this));
		else add(new Element(token, last.index + 1, this));
	}
	private void add(Element element){
		Element oldLast = last;
		last = element;
		
		oldLast.next = last;
		element.previous = oldLast;
	}
	public void add(int index, Token token){
		if(token == null) throw new IllegalArgumentException("null tokens not allowed");
		else if(index < 0) throw new IllegalIndexException(index);
		else if(index > size()) throw new IllegalIndexException(index, size());
		else add(index, new Element(token, index, this));
	}
	private void add(int index, Element element){
		if(first == null) first = (last = element);
		else if(index == 0){//New first element
			Element oldFirst = first;
			first = element;
			oldFirst.previous = element;
			element.next = oldFirst;
		}else if(index == size()){//New last element
			add(element);//Add to end of list
		}else{
			Element previous = getElement(index-1), next = getElement(index);//Next is what's currently at the index, because it'll be pushed up an index
			previous.next = element;
			element.previous = previous;
			
			next.previous = element;
			element.next = next;
		}
		for(Element e = element.next; e != null; e = e.next) e.index++;//Add one to the index of everything after the new insertion
	}
	public void remove(Element element){
		if(element == null) throw new IllegalArgumentException("Cannot remove null element");
		else if(!this.equals(element.parent)) throw new IllegalArgumentException("ELement is not a child of this list");
		
		for(Element e = element.next; e != null; e = e.next) e.index--;//Move everything after it up an index 
		if(first.equals(element)){
			if(last.equals(element)){
				first = last = null;
			}else{
				first = element.next;
				first.previous = null;
			}
		}else if(last.equals(element)){
			last = element.previous;
			last.next = null;
		}else{
			element.next.previous = element.previous;
			element.previous.next = element.next;
		}
		element.parent = null; element.next = element.previous = null;
	}
	public Token get(int index){return getElement(index).token;}
	public Element getElement(int index){
		Element e = first;
		while(e.index < index) e = Utility.checkNull(e.next, new IllegalIndexException(index, e.index));
		return e;
	}
	public Token first(){return firstElement().token;}
	public Element firstElement(){return Utility.checkNull(first, new IllegalIndexException(0, 0));}
	public int size(){return last == null ? 0 : last.index+1;}
	
	@Override public TokenListIterator iterator(){return new TokenListIterator(this);}
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder("[");
		for(Element e = first; e != null; e = e.next){
			sb.append('\"').append(e.token).append('\"');
			if(e.next != null) sb.append(", ");
		}
		return sb.append("]").toString();
	}
	public String toDescriptorString(){
		StringBuilder sb = new StringBuilder("[");
		for(Element e = first; e != null; e = e.next){
			sb.append(e.token.toDescriptorString());
			if(e.next != null) sb.append(", ");
		}
		return sb.append("]").toString();
	}
	public String toParseableString(){
		StringBuilder sb = new StringBuilder("[");
		for(Element e = first; e != null; e = e.next){
			sb.append('\"').append(e.token.toParseableString()).append('\"');
			if(e.next != null) sb.append(", ");
		}
		return sb.append("]").toString();
	}
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof TokenList)) return false;
		TokenList list = (TokenList)obj;
		if(size() != list.size()) return false;
		for(Element e1 = first, e2 = list.first; e1.next != null && e2.next != null; e1 = e1.next, e2 = e2.next)
			if(!e1.token.equals(e2.token)) return false;
		return true;
	}
	@Override
	public int hashCode(){
		int code = 0;
		for(Element e = first; e != null; e = e.next){
			if(e.index % 2 == 0) code += e.token.hashCode(); else code -= e.token.hashCode();
		}
		return code;
	}
	
	public static class Element{
		private int index;
		private Token token;
		private TokenList parent;
		private Element previous = null;
		private Element next = null;
		private Element(Token token, int index, TokenList parent){this.token = token; this.index = index; this.parent = parent;}
		public int index(){return index;}
		public Token token(){return token;}
		public TokenList parent(){return parent;}
		public Element previous(){return previous;}
		public Element next(){return next;}
		public void before(Token token){parent.add(index-1, token);}
		public void after(Token token){parent.add(index+1, token);}
		public void replace(Token token){this.token = token;}
		@Override public String toString(){return "TokenListElement[" + token.toString() + "]";}
	}
	public static class TokenListIterator implements Iterator<Token>{
		private Element e;
		private TokenList list;
		private TokenListIterator(TokenList list){this(list, list.first);}
		private TokenListIterator(TokenList list, Element e){
			this.list = list;
			this.e = e;
		}
		@Override public boolean hasNext(){return e != null;}
		@Override
		public Token next(){
			Token ret = e.token;
			e = e.next;
			return ret;
		}
		public Token peek(){return e.token;}
		public Element peekElement(){return e;}
		public boolean hasPrevious(){return e.previous != null;}
		public Token previous(){return (e = e.previous).token;}
		public TokenList list(){return list;}
		public int index(){return e == null ? 0 : e.index;}
		public TokenListIterator clone(){return new TokenListIterator(list, e);}
		
		@Override public String toString(){return getClass().getSimpleName() + "[" + e + " in " + list + "]";}
	}
	public static abstract class TokenListVisitor{
		public static final int CONTINUE = 0, EXIT = -1;
		public abstract int visit(Element e);
		public void visit(TokenList list){
			for(Element e = list.first; e != null; e = e.next){
				int code = visit(e);
				if(code == CONTINUE) continue;
				else if(code == EXIT) return;
				else throw new IllegalArgumentException("Illegal exit code " + code);
			}
		}
		public void visitReverse(TokenList list){
			for(Element e = list.last; e.previous != null; e = e.previous){
				int code = visit(e);
				if(code == CONTINUE) continue;
				else if(code == EXIT) return;
				else throw new IllegalArgumentException("Illegal exit code " + code);
			}
		}
	}
}
