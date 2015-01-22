
public class MultiplicationCell {
	private int left;
	private int right;
	private int col;
	private int row;
	private String pos;
	
	public MultiplicationCell(int left, int right){
		this.left = left;
		this.right = right;
	}
	
	public MultiplicationCell(int row, int col, String pos){
		this.col = col;
		this.row = row;
		this.pos = pos;
	}
	
	public MultiplicationCell(int left, int right, int row, int col, String pos){
		this.left = left;
		this.right = right;
		this.col = col;
		this.row = row;
		this.pos = pos;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	
	
}
