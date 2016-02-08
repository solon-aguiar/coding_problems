import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class p10851 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();
		int cases = Integer.parseInt(bf.readLine());
		
		for (int i = 0; i < cases; i++) {
			if (i != 0) {
				bf.readLine();
			}
			String[] matrix = new String[10];
			for (int j = 0; j < 10; j++) {
				matrix[j] = bf.readLine();
			}
			output.append(decypher(matrix) + "\n");
		}
		System.out.print(output);
	}

	private static String decypher(String[] matrix) {
		StringBuilder message = new StringBuilder();
		for (int i = 1; i < matrix[0].length() - 1; i++) {
			int asciiValue = 0;
			for (int j = 1; j < 10; j++) {
				char c = matrix[j].charAt(i);
				if (c == '\\') {
					asciiValue += (int) Math.pow(2, j - 1);
				}
			}
			message.append((char) asciiValue);
		}
		return message.toString();
	}
}
