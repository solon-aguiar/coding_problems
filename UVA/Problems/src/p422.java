import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class p422 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();

		int l = Integer.parseInt(bf.readLine());
		char[][] matrix = new char[l][l];
		Map<Character, List<Position>> positions = new HashMap<>();

		for (int i = 0; i < l; i++) {
			matrix[i] = bf.readLine().trim().toUpperCase().toCharArray();
			for (int j = 0; j < l; j++) {
				char c = matrix[i][j];
				if (!positions.containsKey(c)) {
					positions.put(c, new LinkedList<>());
				}
				positions.get(c).add(new Position(i, j));
			}
		}

		while (true) {
			String word = bf.readLine().trim().toUpperCase();
			if (word.equals("0")) {
				break;
			}
			findWord(word, matrix, positions, output);
		}
		System.out.print(output);
	}

	private static void findWord(String word, char[][] matrix, Map<Character, List<Position>> positions,
			StringBuilder output) {
		char[] charArray = word.toCharArray();
		boolean found = false;

		List<Position> starting = positions.get(charArray[0]);
		for (Position p : starting) {
			Position end = findEnd(p, charArray, matrix);
			if (end != null) {
				found = true;
				output.append(p.toString() + " " + end.toString() + "\n");
			}
		}
		if (!found) {
			output.append("Not found\n");
		}

	}

	private static Position findEnd(Position startingPosition, char[] word, char[][] matrix) {
		Position p = null;
		if (p == null && startingPosition.line + word.length <= matrix.length) {
			p = findVertical(startingPosition.line, startingPosition.column, word, matrix);
		}

		if (p == null && startingPosition.column + word.length <= matrix.length) { // horizontal forward
			p = findHorizontal(startingPosition.line, startingPosition.column, word, matrix, 1);
		}

		if (p == null && startingPosition.column - word.length >= 0) { // horizontal backward
			p = findHorizontal(startingPosition.line, startingPosition.column, word, matrix, -1);
		}
		
		if (p == null) {
			p = findDiagonal(startingPosition.line, startingPosition.column, word, matrix, -1, -1);
		}
		
		if (p == null) {
			p = findDiagonal(startingPosition.line, startingPosition.column, word, matrix, -1, 1);
		}
		
		if (p == null) {
			p = findDiagonal(startingPosition.line, startingPosition.column, word, matrix, 1, -1);
		}
		
		if (p == null) {
			p = findDiagonal(startingPosition.line, startingPosition.column, word, matrix, 1, 1);
		}

		return p;
	}

	private static Position findHorizontal(int line, int column, char[] word, char[][] matrix, int j) {
		for (int i = 1; i < word.length; i++) {
			column += j;
			char c = matrix[line][column];
			char w = word[i];
			if (c != w) {
				return null;
			}
		}
		return new Position(line, column);
	}

	private static Position findVertical(int line, int column, char[] word, char[][] matrix) {
		for (int i = 1; i < word.length; i++) {
			line++;
			char c = matrix[line][column];
			char w = word[i];
			if (c != w) {
				return null;
			}
		}
		return new Position(line, column);
	}
	
	private static Position findDiagonal(int line, int column, char[] word, char[][] matrix, int x, int y) {
		for (int i = 1; i < word.length; i++) {
			line += x;
			column += y;
			if (line < 0 || line >= matrix.length || column < 0 || column >= matrix.length) {
				return null;
			}
			char c = matrix[line][column];
			char w = word[i];
			if (c != w) {
				return null;
			}
		}
		return new Position(line, column);
	}
}

class Position {
	int line;
	int column;

	public Position(int line, int column) {
		this.line = line;
		this.column = column;
	}

	public String toString() {
		return (line + 1) + "," + (column + 1);
	}
}
