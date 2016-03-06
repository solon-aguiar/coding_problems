import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class p11475 {
	public static void main(String[] args) throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();

		while (true) {
			String line = bf.readLine();
			if (line == null) {
				break;
			}

			computePalindrome(line, output);
			System.out.println(output);
		}
		System.out.print(output);
	}

	private static void computePalindrome(String line, StringBuilder output) {
		char[] original = line.toCharArray();
		StringBuilder reverse = new StringBuilder();
		int lastIndex = original.length - 1;
		boolean foundEqual = false;

		for (int i = 0; i < original.length; i++) {
			char x = original[i];
			char z = original[lastIndex];
			if (x != z) {
				reverse.append(String.valueOf(x));
			    if (foundEqual) {
			    	lastIndex--;
			    	foundEqual = false;
			    }
			} else {
				lastIndex--;
				foundEqual = true;
			}
		}
		output.append(new StringBuilder(line).append(reverse.reverse()) + "\n");
	}

}
