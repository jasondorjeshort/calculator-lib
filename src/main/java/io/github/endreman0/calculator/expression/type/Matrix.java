package io.github.endreman0.calculator.expression.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import io.github.endreman0.calculator.Processor;
import io.github.endreman0.calculator.annotation.ComplexFactory;
import io.github.endreman0.calculator.annotation.Function;
import io.github.endreman0.calculator.annotation.Operator;
import io.github.endreman0.calculator.expression.Expression;
import io.github.endreman0.calculator.util.Utility;

public class Matrix extends Type{
	private NumericType[][] data;
	private Matrix(NumericType[][] data){
		this.data = data;
	}
	private Matrix(int rows, int columns){
		this(new NumericType[rows][columns]);
	}
	
	@Operator("+")
	public Matrix add(Matrix other){
		checkSize(other);
		Matrix ret = new Matrix(rows(), columns());
		for(int r = 0; r < other.rows(); r++){
			for(int c = 0; c < other.columns(); c++){
				ret.data[r][c] = data[r][c].add(other.data[r][c]);
			}
		}
		return ret;
	}
	@Operator("-")
	public Matrix subtract(Matrix other){
		checkSize(other);
		Matrix ret = new Matrix(rows(), columns());
		for(int r = 0; r < other.rows(); r++){
			for(int c = 0; c < other.columns(); c++){
				ret.data[r][c] = data[r][c].subtract(other.data[r][c]);
			}
		}
		return ret;
	}
	@Operator("*")
	public Matrix multiply(Matrix other){
		Utility.checkNull(other, "Cannot multiply by null");
		if(columns() != other.rows()) throw new IllegalArgumentException("Unequal interior dimensions: " + columns() + " vs " + other.rows());
		Matrix ret = new Matrix(rows(), other.columns());
		for(int r = 0; r < ret.rows(); r++){
			for(int c = 0; c < ret.columns(); c++){
				NumericType sum = get(r, 0).multiply(other.get(0, c));//Start sum with first item to be summed
				for(int i = 1; i < other.rows(); i++){
					sum = sum.add(get(r, i).multiply(other.get(i, c)));
				}
				ret.data[r][c] = sum;
			}
		}
		return ret;
	}
	@Operator("*")
	public Matrix multiply(NumericType scalar){
		Utility.checkNull(scalar, "Cannot multiply by null");
		Matrix ret = new Matrix(rows(), columns());
		for(int r = 0; r < rows(); r++){
			for(int c = 0; c < columns(); c++){
				ret.data[r][c] = data[r][c].multiply(scalar);
			}
		}
		return ret;
	}
	@Operator("/")
	public Matrix divide(NumericType scalar){
		Utility.checkNull(scalar, "Cannot multiply by null");
		Matrix ret = new Matrix(rows(), columns());
		for(int r = 0; r < rows(); r++){
			for(int c = 0; c < columns(); c++){
				ret.data[r][c] = data[r][c].divide(scalar);
			}
		}
		return ret;
	}
	
	@Function
	public int columns(){
		return data.length == 0 ? 0 : data[0].length;
	}
	@Function
	public int rows(){
		return data.length;
	}
	@Function
	public NumericType get(NumericType row, NumericType column){
		return get((int)Math.floor(row.value()), (int)Math.floor(column.value()));
	}
	public NumericType get(int row, int column){
		if(row >= rows())
			throw new IllegalArgumentException("No row " + row + "; max is " + (rows() - 1));
		else if(column >= columns())
			throw new IllegalArgumentException("No column " + column + "; max is " + (columns() - 1));
		else if(data[row][column] == null)
			return data[row][column] = Decimal.valueOf(0);
		else
			return data[row][column];
	}
	@Function
	public static Matrix abs(Matrix in){
		Utility.checkNull(in);
		Matrix ret = new Matrix(in.rows(), in.columns());
		for(int r = 0; r < in.rows(); r++)
			for(int c = 0; c < in.columns(); c++)
				ret.data[r][c] = in.data[r][c].abs();
		return ret;
	}
	
	/**
	 * Find the inverse of this matrix. This algorithm works by creating an augmented matrix: two matrices appended to each other side-by-side. (In the code, this is represented by one matrix that is twice as wide as the original.) The first part of the augmented matrix is the original matrix, and the second is an identity matrix the same size as the original. The augmented matrix is then converted to reduced row echelon form. The first half becomes an identity matrix, and the second half becomes the original's inverse. You can try out the reduced row echelon form algorithm <a href="http://www.math.odu.edu/~bogacki/cgi-bin/lat.cgi?c=rref">here</a>.
	 * 
	 * @return The inverse of this matrix
	 */
	@Function
	public Matrix inverse(){
		if(rows() != columns()) throw new IllegalArgumentException("Unequal dimensions: " + rows() + " vs " + columns());//Only square matrices have inverses
		
		Matrix full = new Matrix(rows(), 2 * columns());//Create an augmented matrix, representing the input and the identity matrix
		for(int y = 0; y < rows(); y++){
			for(int x = 0; x < columns(); x++){
				full.data[y][x] = data[y][x];//Write in current data
				full.data[y][x + columns()] = MixedNumber.valueOf(x == y ? 1 : 0);//Create diagonal line of 1s (identity matrix)
			}
		}
		
		//The below for-loops apply matrix row operations to turn the left half of the augmented matrix into the identity matrix.
		//Once this has been achieved, the right half will be the inverse matrix.
		for(int y = 0; y < rows(); y++){
			full.multiplyRow(y, full.data[y][y].reciprocal());//Make the cells on the diagonal equal 1
			for(int y2 = y + 1; y2 < rows(); y2++)
				full.addMultRow(y, y2, full.data[y2][y].multiply(MixedNumber.valueOf(-1)));//Zero out everything below the 1
		}
		for(int y = rows() - 1; y >= 0; y--)//For each row, bottom to top
			for(int y2 = y - 1; y2 >= 0; y2--)//For each row above the current, bottom to top
				full.addMultRow(y, y2, full.data[y2][y].multiply(MixedNumber.valueOf(-1)));//Zero out everything above the 1 in this row
		//Create a matrix from the right half of the augmented matrix, which is now the inverse
		Matrix ret = new Matrix(rows(), columns());
		for(int y = 0; y < full.rows(); y++)
			for(int x = 0; x < columns(); x++)
				ret.data[y][x] = full.data[y][x + columns()];
		return ret;
	}
	@Function
	public int width(){
		return columns();
	}
	@Function
	public int height(){
		return rows();
	}
	@Function
	public boolean isEmpty(){
		return columns() == 0 || rows() == 0;
	}
	
	@Override
	public String toParseableString(){
		StringBuilder sb = new StringBuilder("[");
		for(int r = 0; r < data.length; ++r){
			for(int c = 0; c < data[r].length; ++c){
				sb.append(data[r][c].toDisplayString());
				if(c + 1 < data[r].length) sb.append(", ");
			}
			if(r + 1 < data.length) sb.append("][");
		}
		return sb.append("]").toString();
	}
	@Override
	public String toDisplayString(){
		StringBuilder sb = new StringBuilder("[");
		for(int r = 0; r < data.length; ++r){
			for(int c = 0; c < data[r].length; ++c){
				sb.append(data[r][c].toDisplayString());
				if(c + 1 < data[r].length) sb.append(", ");
			}
			if(r + 1 < data.length) sb.append(']').append(System.lineSeparator()).append('[');
		}
		return sb.append("]").toString();
	}
	@Override
	public String toDescriptorString(){
		return "Matrix[w:" + columns() + ",h:" + rows() + "]";
	}
	
	public static Matrix valueOf(NumericType[][] data){
		Utility.checkNull(data, "Cannot create matrix from null data");
		if(data.length == 0 || data[0].length == 0) return new Matrix(0, 0);
		
		//Defensive copy of the data
		NumericType[][] clone = new NumericType[data.length][data[0].length];
		for(int r = 0; r < clone.length; ++r)
			for(int c = 0; c < data[0].length; ++c)
				clone[r][c] = data[r][c];
		return new Matrix(clone);
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof Matrix)
			return Arrays.deepEquals(data, ((Matrix)obj).data);
		else
			return false;
	}
	@Override
	public int hashCode(){
		int sum = 0;
		for(int r = 0; r < rows(); ++r)
			for(int c = 0; c < columns(); ++c)
				sum += (int)data[r][c].value();
		return sum;
	}
	@Override
	public Matrix clone(){
		return valueOf(data);//valueOf() makes a defensive copy
	}
	
	@ComplexFactory("[")
	public static Matrix valueOf(Queue<String> tokens){
		List<Expression[]> rows = new ArrayList<>();
		rows.add(readRow(tokens));
		while(true){
			if("[".equals(tokens.peek())){
				tokens.poll();//Read "["
				rows.add(readRow(tokens));
			}else
				break;
		}
		
		if(rows.isEmpty()) return new Matrix(new NumericType[0][0]);
		
		NumericType[][] data = new NumericType[rows.size()][rows.get(0).length];
		for(int r = 0; r < data.length; ++r){
			Expression[] row = rows.get(r);
			if(row.length != data[0].length) throw new IllegalArgumentException("Cannot create jagged matrix: initial width " + data[0].length + ", row " + r + " width " + row.length);
			for(int c = 0; c < data[0].length; ++c){
				Type t = row[c].evaluate();
				if(t instanceof NumericType)
					data[r][c] = (NumericType)t;
				else
					throw new IllegalArgumentException(t + " @ (row " + r + ", column " + c + ") is not a number");
			}
		}
		return new Matrix(data);
	}
	//Reads a matrix row, assuming the "[" has already been read; does read the trailing "]"
	private static Expression[] readRow(Queue<String> tokens){
		List<Expression> components = new ArrayList<>();
		while(!"]".equals(tokens.peek())){
			components.add(readComponent(tokens).evaluate());
			if(",".equals(tokens.peek())) tokens.poll();//Read ","
		}
		tokens.poll();//Read "]"
		return components.toArray(new Expression[components.size()]);
	}
	private static Expression readComponent(Queue<String> tokens){
		List<String> ts = new ArrayList<>();//List of tokens making up this component
		while(tokens.peek() != null && !(tokens.peek().equals(",") || tokens.peek().equals("]")))
			ts.add(tokens.poll());
		return Processor.process(ts);
	}
	
	private void checkSize(Matrix other){
		Utility.checkNull(other, "Cannot compare to null matrix");
		if(columns() != other.columns()) throw new IllegalArgumentException("Unequal width: " + columns() + " vs " + other.columns());
		if(rows() != other.rows()) throw new IllegalArgumentException("Unequal height: " + rows() + " vs " + other.rows());
	}
	private void multiplyRow(int row, NumericType factor){
		for(int x = 0; x < columns(); x++)
			data[row][x] = data[row][x].multiply(factor);
	}
	private void addMultRow(int source, int dest, NumericType factor){
		for(int x = 0; x < columns(); x++)
			data[dest][x].add(data[source][x].multiply(factor));
	}
}
