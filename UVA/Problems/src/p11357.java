import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class p11357 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();
		
		int cases = Integer.parseInt(bf.readLine());
		
		for (int i = 0; i < cases; i++) {
			String formula = bf.readLine().trim();
			
			if (isSolveable(formula)) {
				output.append("YES");
			} else {
				output.append("NO");
			}
			output.append("\n");
		}
		System.out.print(output);
	}

	private static boolean isSolveable(String formula) {
		String[] conjunctions = formula.split("\\|");
		for (String conjunction : conjunctions) { 
			if (isSatisfable(conjunction)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isSatisfable(String conjunction) {
		boolean[] positive = new boolean['z' - 'a' + 1];
		boolean[] negations = new boolean['z' - 'a' + 1];
		boolean isNegation = false;
		
		for (int i = 0; i < conjunction.length(); i++) {
			char c = conjunction.charAt(i);
			if (operator(c)) {
				continue;
			}
			if (negation(c)) {
				isNegation = true;
			} else {
				if (isNegation) {
					if (positive[c - 'a']) {
						return false;
					}
					negations[c - 'a'] = true;
				} else {
					if (negations[c - 'a']) {
						return false;
					}
					positive[c - 'a'] = true;
				}
				isNegation = false;
			}
		}
		return true;
	}

	private static boolean negation(char c) {
		return c == '~';
	}

	private static boolean operator(char c) {
		return c == '&' || c == ')' || c == '(';
	}

}
