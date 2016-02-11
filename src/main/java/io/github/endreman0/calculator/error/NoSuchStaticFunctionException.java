package io.github.endreman0.calculator.error;

import io.github.endreman0.calculator.token.type.Type;

public class NoSuchStaticFunctionException extends EvaluatorException{
	private static final long serialVersionUID = 1L;
	public NoSuchStaticFunctionException(String name, Class<? extends Type>[] args){
		super(String.format("No static function \"%s\" with args %s", name, format(args)));
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
