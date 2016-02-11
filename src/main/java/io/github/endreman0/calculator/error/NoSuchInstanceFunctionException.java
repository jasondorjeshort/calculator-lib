package io.github.endreman0.calculator.error;

import io.github.endreman0.calculator.token.type.Type;

public class NoSuchInstanceFunctionException extends ProcessorException{
	private static final long serialVersionUID = 1L;
	public NoSuchInstanceFunctionException(Class<? extends Type> obj, String name, Class<? extends Type>[] args){
		super(String.format("Type %s has no function \"%s\" with args %s", obj, name, format(args)));
	}
	private static String format(Class<? extends Type>[] args){
		StringBuilder sb = new StringBuilder("[");
		for(int i=0; i<args.length; i++){
			sb.append(args[i].getSimpleName());
			if(i+1 < args.length) sb.append(", ");//Add comma separator for all but last item of array
		}
		return sb.append("]").toString();
	}
}
