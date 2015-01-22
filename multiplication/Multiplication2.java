public class Multiplication2 {
	public static void main(String[] args) {
		int number1 = -34;
		int number2 = 5;
		
		// pos and neg number
		int prefix = 1;
		if (number1 < 0 || number2 < 0){
			number1 = Math.abs(number1);
			number2 = Math.abs(number2);
			prefix = -1;
		}
		else if (number1 < 0 && number2 < 0) {
			number1 = Math.abs(number1);
			number2 = Math.abs(number2);
		}

		int[] num1 = convertNum(number1);
		int[] num2 = convertNum(number2);
		int lenNum1 = num1.length;
		int lenNum2 = num2.length;

		MultiplicationCell[][] matrix = prepareMatrix(number1, number2);
		int res = computeMultiplication(matrix, lenNum1 + lenNum2);
		res = res * prefix;
		System.out.println("result is: " + res);
	}

	public static MultiplicationCell[][] prepareMatrix(int number1, int number2) {
		// read the length of input numbers
		int[] num1 = convertNum(number1);
		int[] num2 = convertNum(number2);
		int lenNum1 = num1.length;
		int lenNum2 = num2.length;

		// 1D array to store the cross product
		int[] crossProduct = new int[lenNum1 * lenNum2];
		int k = 0;
		for (int i = 0; i < num1.length; i++) {
			for (int j = 0; j < num2.length; j++) {
				crossProduct[k] = num1[i] * num2[j];
				k++;
			}
		}

		for (int n : crossProduct) {
			System.out.println(n);
		}

		// set 2D array
		MultiplicationCell[][] matrix = new MultiplicationCell[lenNum2][lenNum1];

		// test example for int[][]
		int[][] m = new int[lenNum2][lenNum1];
		int index = 0;
		for (int c = 0; c < lenNum1; c++) { // c < column
			for (int r = 0; r < lenNum2; r++) { // r < row
				m[r][c] = crossProduct[index];
				index++;
				System.out.println(m[r][c]);
			}
		}

		for (int i = 0; i < lenNum2; i++) {
			for (int j = 0; j < lenNum1; j++) {
				System.out.printf("%5d ", m[i][j]);
			}
			System.out.println();
		}

		// loop the 2D array to store the cross product
		int zz = 0;
		int left = 0;
		int right = 0;
		for (int c = 0; c < lenNum1; c++) {
			for (int r = 0; r < lenNum2; r++) {
				int curr = crossProduct[zz];
				if (curr < 10) {
					left = 0;
					right = curr;
				} else { // curr >= 10
					left = (int) (curr / 10);
					right = (int) (curr % 10);
				}
				System.out.println("left: " + left);
				System.out.println("right: " + right);
				MultiplicationCell cell = new MultiplicationCell(left, right);
				matrix[r][c] = cell;
				cell.setRow(r);
				cell.setCol(c);
				zz++;
			}
		}
		return matrix;
	}

	public static int computeMultiplication(MultiplicationCell[][] matrix,
			int resultLen) {

		int[] multiTemp = new int[resultLen];

		// 1) move on the bottom row to get the diagonal strip sum
		int r = matrix.length - 1; // r = 1, bottom row
		String currPos = "right"; // position is always to read "right"
		int k = 0; // k as the index of int[] multiTemp

		// move from c -> 0 ; set row to the max -> matrix.length
		for (int c = matrix[0].length - 1; c >= 0; c--) {
			MultiplicationCell curr = new MultiplicationCell(
					matrix[r][c].getLeft(), matrix[r][c].getRight(), r, c,
					currPos);

			int sum = getSum(curr, matrix);
			multiTemp[k] = sum;
			k++;
		} // END FOR

		// 2) move on the first col to get the diagonal strip sum
		int cc = 0; // cc = 0, move on the leftest column
		String pos = "left";
		for (int rr = matrix.length - 1; rr >= 0; rr--) {
			MultiplicationCell current = new MultiplicationCell(
					matrix[rr][cc].getLeft(), matrix[rr][cc].getRight(), rr,
					cc, pos);
			System.out.println("current cell: " + current.getRow() + " "
					+ current.getCol() + " " + current.getPos());
			int sum = getSum(current, matrix);
			multiTemp[k] = sum;
			k++;
		}

		for (int i : multiTemp) {
			System.out.println(i);
		}

		// clean the array and get the result int
		int res = getValue(multiTemp);
		return res;
	}

	public static int getValue(int[] res) {
		// make a copy of res
		int[] copy = new int[res.length];
		for (int i = 0; i < res.length; i++) {
			copy[i] = res[i];
			System.out.println("copy: " + copy[i]);
		}

		// loop through 'res' and make changes to 'copy'
		boolean carryExist = false;
		for (int i = 0; i < res.length; i++) {
			if (res[i] >= 10) {
				if (carryExist == false) { // no previous carry-over
					int stay = (int) (res[i] % 10);
					int carry = (int) (res[i] / 10);
					copy[i] = stay;
					copy[i + 1] = copy[i + 1] + carry;
					carryExist = true;
				} else { // previous carry-over exists, then res[i] increment 1
					int stay = (int) ((res[i] + 1) % 10);
					int carry = (int) ((res[i] + 1) / 10);
					copy[i] = stay;
					copy[i + 1] = copy[i + 1] + carry;
					carryExist = true;
				}
			}
		}

		// convert int[] to int
		int value = 0;
		int p = copy.length - 1;
		for (int i = copy.length - 1; i >= 0; i--) {
			System.out.println("p: " + p);
			value += copy[i] * Math.pow(10, p);
			p--;
		}
		return value;
	}

	public static int getSum(MultiplicationCell curr,
			MultiplicationCell[][] matrix) {
		int sum = 0;

		if (curr.getPos().equals("right")) {
			sum = curr.getRight();
		} else if (curr.getPos().equals("left")) {
			sum = curr.getLeft();
		}

		while (true) {
			System.out.println("in while");
			MultiplicationCell next = getNext(curr, matrix);

			if (next == null) {
				break;
			}

			if (next.getPos().equals("left")) {
				sum += next.getLeft();
			}
			if (next.getPos().equals("right")) {
				sum += next.getRight();
			}
			curr = next;
		} // END WHILE

		return sum;
	}

	public static MultiplicationCell getNext(MultiplicationCell curr,
			MultiplicationCell[][] matrix) {
		MultiplicationCell next = new MultiplicationCell(0, 0, 0, 0, "");
		System.out.println("current cell**: " + curr.getRow() + " "
				+ curr.getCol() + " " + curr.getPos());

		// if (curr.getPos().equals("right")){ // go right
		// if (curr.getCol() == matrix[0].length - 1){
		// return null;
		// }
		// }
		//
		// else if (curr.getPos().equals("left")){
		// if (curr.getRow() == 0){ // go up
		// return null;
		// }
		// }

		if (curr.getPos().equals("left") && curr.getRow() > 0) { // go up
			next.setRow(curr.getRow() - 1);
			next.setCol(curr.getCol());
			next.setLeft(matrix[curr.getRow() - 1][curr.getCol()].getLeft());
			next.setRight(matrix[curr.getRow() - 1][curr.getCol()].getRight());
			next.setPos("right");
			return next;
		}

		if (curr.getPos().equals("right")
				&& curr.getCol() < matrix[0].length - 1) { // go right
			next.setRow(curr.getRow());
			next.setCol(curr.getCol() + 1);
			next.setLeft(matrix[curr.getRow()][curr.getCol() + 1].getLeft());
			next.setRight(matrix[curr.getRow()][curr.getCol() + 1].getRight());
			next.setPos("left");
			return next;
		}

		return null;
	}

	public static int[] convertNum(int n) {
		String s = Integer.toString(n);
		char[] l = s.toCharArray();
		int[] num = new int[l.length];
		for (int i = 0; i < l.length; i++) {
			char curr = l[i];
			num[i] = curr - 48;
		}
		return num;
	}

}
