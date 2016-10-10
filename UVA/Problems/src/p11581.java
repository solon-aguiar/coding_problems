import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class p11581 {

	private static int[] INFINITY = { 0, 0, 0 };

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();

		int cases = Integer.parseInt(bf.readLine().trim());
		for (int i = 0; i < cases; i++) {
			bf.readLine();
			int[][] matrix = new int[3][3];
			for (int j = 0; j < 3; j++) {
				matrix[j] = getLine(bf.readLine().trim());
			}

			int index = -1;
			while (!matchesInfinity(matrix)) {
				index++;
				matrix = transform(matrix);
			}
			output.append(index + "\n");
		}
		System.out.print(output.toString());
	}

	private static int[][] transform(int[][] matrix) {
		int[][] transformed = new int[3][3];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				transformed[i][j] = getTransformed(i, j, matrix);
			}
		}
		return transformed;
	}

	private static int getTransformed(int i, int j, int[][] matrix) {
		int el = 0;
		if (i - 1 >= 0) {
			el += matrix[i - 1][j];
		}
		if (j - 1 >= 0) {
			el += matrix[i][j - 1];
		}
		if (i + 1 < 3) {
			el += matrix[i + 1][j];
		}
		if (j + 1 < 3) {
			el += matrix[i][j + 1];
		}
		return el % 2;
	}

	private static boolean matchesInfinity(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			if (!Arrays.equals(matrix[i], INFINITY)) {
				return false;
			}
		}
		return true;
	}

	private static int[] getLine(String readLine) {
		int[] line = new int[3];
		for (int i = 0; i < readLine.length(); i++) {
			line[i] = readLine.charAt(i) - '0';
		}
		return line;
	}
}
