package io.github.endreman0.calculator.util;

public class Patterns{
	public static final String INTEGER_UNSIGNED = "\\d{1,}";
	public static final String INTEGER = "\\-?" + INTEGER_UNSIGNED;
	public static final String INTEGER_COMMAS_UNSIGNED = "\\d{1,}(\\,\\d{3}){0,}";
	public static final String INTEGER_COMMAS = "\\-?" + INTEGER_COMMAS_UNSIGNED;
	public static final String FRACTION = INTEGER_COMMAS + "\\/" + INTEGER_COMMAS;
	public static final String MIXED_NUMBER = INTEGER_COMMAS + "_" + INTEGER_COMMAS + "\\/" + INTEGER_COMMAS;
	public static final String DECIMAL = INTEGER_COMMAS + "\\." + INTEGER_UNSIGNED;
	public static final String NUMBER = "(" + INTEGER + "|" + FRACTION + "|" + MIXED_NUMBER + ")";
	private static final String matrixRow = "\\[(" + NUMBER + "\\|){0,}" + NUMBER + "\\]";
	public static final String MATRIX = "(" + matrixRow + "){1,}";
	public static final String VECTOR = "<(" + NUMBER + "\\|){0,}" + NUMBER + ">";
	public static final String SET = "\\{(" + NUMBER + "\\|){0,}" + NUMBER + "\\}";
	public static final String SWITCH = "(true|false)";
	public static final String BASED_NUMBER = INTEGER_COMMAS_UNSIGNED + "x" + "\\-?[0-9a-zA-Z]{1,}";
	public static final String TIME = INTEGER_UNSIGNED + ":" + INTEGER_UNSIGNED + "(:" + INTEGER_UNSIGNED + ")?";
	public static final String VARIABLE = "[a-zA-Z][a-zA-Z0-9]*";
}
