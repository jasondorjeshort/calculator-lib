package io.github.endreman0.calculator.token.type;

import io.github.endreman0.calculator.Calculator;
import io.github.endreman0.calculator.annotation.Factory;
import io.github.endreman0.calculator.annotation.Function;
import io.github.endreman0.calculator.annotation.Operator;
import io.github.endreman0.calculator.error.CalculatorException;
import io.github.endreman0.calculator.util.Patterns;
import io.github.endreman0.calculator.util.Utility;

public class Matrix extends Type{
	private MixedNumber[][] data;
	private Matrix(MixedNumber[][] data){
		this.data = copy(data);
	}
	private Matrix(int rows, int columns){
		data = new MixedNumber[rows][columns];
		for(int r=0; r<rows; r++)
			for(int c=0; c<columns; c++)
				data[r][c] = MixedNumber.valueOf(0);
	}
	
	@Operator("+")
	public Matrix add(Matrix other){
		checkSize(other);
		Matrix ret = new Matrix(rows(), columns());
		for(int r=0; r<other.rows(); r++){
			for(int c=0; c<other.columns(); c++){
				ret.data[r][c] = data[r][c].add(other.data[r][c]);
			}
		}
		return ret;
	}
	@Operator("-")
	public Matrix subtract(Matrix other){
		checkSize(other);
		Matrix ret = new Matrix(rows(), columns());
		for(int r=0; r<other.rows(); r++){
			for(int c=0; c<other.columns(); c++){
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
		for(int r=0; r<ret.rows(); r++){
			for(int c=0; c<ret.columns(); c++){
				MixedNumber sum = MixedNumber.valueOf(0);
				for(int i=0; i<other.rows(); i++){
					sum = sum.add(get(r, i).multiply(other.get(i, c)));
				}
				ret.data[r][c] = sum;
			}
		}
		return ret;
	}
	@Operator("*")
	public Matrix multiply(MixedNumber other){
		Utility.checkNull(other, "Cannot multiply by null");
		Matrix ret = new Matrix(rows(), columns());
		for(int r=0; r<rows(); r++){
			for(int c=0; c<columns(); c++){
				ret.data[r][c] = data[r][c].multiply(other);
			}
		}
		return ret;
	}
	@Operator("/")
	public Matrix divide(MixedNumber other){
		Utility.checkNull(other, "Cannot multiply by null");
		Matrix ret = new Matrix(rows(), columns());
		for(int r=0; r<rows(); r++){
			for(int c=0; c<columns(); c++){
				ret.data[r][c] = data[r][c].divide(other);
			}
		}
		return ret;
	}
	
	@Function
	public static Matrix abs(Matrix in){
		Utility.checkNull(in);
		Matrix ret = new Matrix(in.rows(), in.columns());
		for(int r=0; r<in.rows(); r++)
			for(int c=0; c<in.columns(); c++)
				ret.data[r][c] = MixedNumber.abs(in.data[r][c]);
		return ret;
	}
	@Function
	public Matrix inverse(){
		if(rows() != columns()) throw new IllegalArgumentException("Unequal dimensions: " + rows() + " vs " + columns());
		
		Matrix full = new Matrix(rows(), 2*columns());//Create an augmented matrix, representing the input and the identity matrix
		for(int y=0;y<rows();y++){
			for(int x=0;x<columns();x++){
				full.data[y][x] = data[y][x];//Write in current data
				full.data[y][x+columns()] = MixedNumber.valueOf(x == y ? 1 : 0);//Create diagonal line of 1s (identity matrix)
			}
		}
		
		//The below for-loops apply matrix row operations to turn the left half of the augmented matrix into the identity matrix.
		//Once this has been achieved, the right half will be the inverse matrix.
		for(int y=0;y<rows();y++){
			full.multiplyRow(y, full.data[y][y].reciprocal());//Make the cells on the diagonal equal 1
			for(int y2=y+1; y2<rows(); y2++) full.addMultRow(y, y2, full.data[y2][y].multiply(MixedNumber.valueOf(-1)));//Zero out everything below the 1
		}
		for(int y=rows()-1; y>=0; y--)//For each row, bottom to top
			for(int y2=y-1; y2>=0; y2--)//For each row above the current, bottom to top
				full.addMultRow(y, y2, full.data[y2][y].multiply(MixedNumber.valueOf(-1)));//Zero out everything above the 1 in this row
		//Create a matrix from the right half of the augmented matrix, which is now the inverse
		Matrix ret = new Matrix(rows(), columns());
		for(int y=0;y<full.rows();y++) for(int x=0;x<columns();x++) ret.data[y][x] = full.data[y][x+columns()];
		return ret;
	}
	private void multiplyRow(int row, MixedNumber factor){for(int x=0;x<columns();x++) data[row][x] = data[row][x].multiply(factor);}
	private void addMultRow(int source, int dest, MixedNumber factor){for(int x=0;x<columns();x++) data[dest][x].add(data[source][x].multiply(factor));}
	
	@Function("rows") public MixedNumber fnRows(){return MixedNumber.valueOf(rows());}
	@Function("columns") public MixedNumber fnColumns(){return MixedNumber.valueOf(columns());}
	@Function public MixedNumber get(MixedNumber row, MixedNumber column){return get(row.whole(), column.whole());}
	
	public int rows(){return data.length;}
	public int columns(){return data[0].length;}
	public MixedNumber get(int row, int column){return data[row][column];}
	public void checkSize(Matrix other){
		Utility.checkNull(other, "Cannot compare to null matrix");
		if(rows() != other.rows()) throw new IllegalArgumentException("Unequal number of rows: " + rows() + " vs " + other.rows());
		if(columns() != other.columns()) throw new IllegalArgumentException("Unequal number of columns: " + columns() + " vs " + other.columns());
	}
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof Matrix)) return false;
		Matrix b = (Matrix)obj;
		try{checkSize(b);}catch(IllegalArgumentException ex){return false;}//Check dimensions
		for(int row=0; row<rows() ;row++)
			for(int column=0; column<columns(); column++)
				if(!get(row, column).equals(b.get(row, column))) return false;
		return true;
	}
	@Override
	public int hashCode(){
		double sum = 0;
		for(MixedNumber[] row : data)
			for(MixedNumber cell : row)
				sum += cell.value();
		return (int)sum;
	}
	public String toParseableString(){return toDisplayString().replace("\r\n", "").replace(" ", "");}
	public String toDescriptorString(){return "Matrix[" + rows() + "," + columns() + "]";}
	public String toDisplayString(){
		StringBuilder sb = new StringBuilder();
		for(int r=0; r<rows(); r++){
			sb.append('[');
			for(int c=0; c<columns(); c++){
				sb.append(get(r, c));
				if(c+1 < columns()) sb.append(" | ");
			}
			sb.append(']');
			if(r+1 < rows()) sb.append("\r\n");
		}
		return sb.toString();
	}
	public MixedNumber[][] toArray(){return copy(data);}
	public Matrix clone(){return new Matrix(data);}
	
	public static Matrix valueOf(MixedNumber[][] data){return new Matrix(data);}
	@Factory({Patterns.MATRIX})
	public static Matrix valueOf(String string) throws CalculatorException{
		String[] rows = string.substring(1, string.length()-1).split("\\]\\s{0,}\\[");//Cut off leading and trailing brackets, and split on row separators from there
		MixedNumber[][] data = new MixedNumber[rows.length][];
		for(int r=0; r<rows.length; r++){
			String[] cells = rows[r].split("\\|");
			data[r] = new MixedNumber[cells.length];
			for(int c=0; c<cells.length; c++){
				data[r][c] = (MixedNumber)Calculator.calculate(cells[c].trim());
			}
		}
		return new Matrix(data);
	}
	
	private static void arrayCheck(Object[][] data){
		if(data == null) throw new IllegalArgumentException("data cannot equal null");
		if(data.length == 0 || data[0].length == 0) throw new IllegalArgumentException("Cannot create empty matrix");
		int columns = data[0].length;
		for(Object[] row : data)
			if(row.length != columns) throw new IllegalArgumentException("Inconsistent row length: " + columns + " vs " + row.length);
	}
	public static Matrix identity(int size){
		MixedNumber[][] data = new MixedNumber[size][size];
		for(int row=0; row<data.length; row++)
			for(int column=0; column<data[row].length; column++)
				data[row][column] = MixedNumber.valueOf(row == column ? 1 : 0);
		return new Matrix(data);
	}
	private static MixedNumber[][] copy(MixedNumber[][] input){
		arrayCheck(input);
		MixedNumber[][] array = new MixedNumber[input.length][input[0].length];
		for(int r=0; r<array.length; r++)
			for(int c=0; c<array[r].length; c++)
				array[r][c] = input[r][c] == null ? MixedNumber.valueOf(0) : input[r][c].clone();
		return array;
	}
}
