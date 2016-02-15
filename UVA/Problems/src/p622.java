import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class p622 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();

		int cases = Integer.parseInt(bf.readLine());
		for (int i = 0; i < cases; i++) {
			String exp = bf.readLine();

			ReversePolishNotation polishNotation = convertToReversePolishNotation(exp);
			if (polishNotation == null) {
				output.append("ERROR");
			} else {
				String evaluation = evaluate(polishNotation);
				output.append(evaluation);
			}

			output.append("\n");
		}
		System.out.print(output);
	}
	
	private static String evaluate(ReversePolishNotation polishNotation) {
		Stack<Integer> values = new Stack<>();
		for (Token t : polishNotation.tokens) {
			if (t.value.matches("\\d+")) {
				values.add(Integer.parseInt(t.value));
			} else if (t.value.equals("*")) {
				if (values.isEmpty()) {
					return "ERROR";
				}
				int left = values.pop();
				if (values.isEmpty()) {
					return "ERROR";
				}
				int right = values.pop();
				values.add(left * right);
			} else if (t.value.equals("+")) {
				if (values.isEmpty()) {
					return "ERROR";
				}
				int left = values.pop();
				if (values.isEmpty()) {
					return "ERROR";
				}
				int right = values.pop();
				values.add(left + right);
			}
		}
		if (values.isEmpty()) {
			return "ERROR";
		}
		
		return String.valueOf(values.pop());
	}

	private static ReversePolishNotation convertToReversePolishNotation(String exp) {
		exp = exp.trim().replaceAll("\\s*\\+\\s*", "+").replaceAll("\\s*\\*\\s*", "*").replaceAll("\\s*\\(\\s*", "(").replaceAll("\\s*\\)\\s*", ")");
		Stack<Character> operators = new Stack<Character>();
		ReversePolishNotation reversePolish = new ReversePolishNotation();
		String number = "";
		for (int i = 0; i < exp.length(); i++) {
			char c = exp.charAt(i);
			if (Character.isDigit(c)) {
				number += String.valueOf(c);
			} else if (c == '*' || c == '+') {
				if (!number.equals("")) {
					reversePolish.addToken(number);
					number = "";
				}
				if (c == '+') {
					while (!operators.isEmpty() && (operators.peek() == '*' || operators.peek() == '+')) {
						char top = operators.pop();
						if (top != '(' && top != ')') {
							reversePolish.addToken(String.valueOf(top));
						}
					}
				} else {
					while (!operators.isEmpty() && operators.peek() == '*') {
						char top = operators.pop();
						if (top != '(' && top != ')') {
							reversePolish.addToken(String.valueOf(top));
						}
					}
				}
				operators.push(c);
			} else if (c == '(') {
				if (!number.equals("")) {
					return null;
				}
				operators.push(c);
			} else if (c == ')') {
				if (!number.equals("")) {
					reversePolish.addToken(number);
					number = "";
				}
				while (true) {
					if (operators.isEmpty()) {
						return null;
					}
					char top = operators.pop();
					if (top == '(') {
						break;
					} else {
						reversePolish.addToken(String.valueOf(top));
					}
				}
			} else {
				return null;
			}
		}
		if (!number.equals("")) {
			reversePolish.addToken(number);
		}
		while (!operators.isEmpty()) {
			char top = operators.pop();
			if (top == '(' || top == ')') {
				return null;
			}
			reversePolish.addToken(String.valueOf(top));
		}
		return reversePolish;
	}

}

class ReversePolishNotation {
	List<Token> tokens;
	
	public ReversePolishNotation () {
		tokens = new LinkedList<>();
	}
	
	public void addToken(String token) {
		tokens.add(new Token(token));
	}
	
	public String toString() {
		return tokens.toString();
	}
}

class Token {
	public Token(String token) {
		this.value = token;
	}

	String value;
	
	public String toString() {
		return value;
	}
}
