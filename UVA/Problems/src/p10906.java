import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class p10906 {

	private static Pattern pattern = Pattern.compile("(?<leftExp>.+)\\s*(?<operation>[\\+\\*]{1})\\s*(?<rightExp>.+)");

	public static void main(String[] args) throws Exception {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();

		int cases = Integer.parseInt(bf.readLine());

		for (int i = 0; i < cases; i++) {
			int nExpressions = Integer.parseInt(bf.readLine());

			Expression last = null;
			Map<String, Expression> symbolTable = new HashMap<>(nExpressions);

			for (int j = 0; j < nExpressions; j++) {
				String expressionString = bf.readLine();
				Expression e = buildExpression(expressionString, symbolTable);
				if (j == nExpressions - 1) {
					last = e;
				} else {
					symbolTable.put(e.key, e);
				}
			}
			output.append("Expression #" + (i + 1) + ": " + last.evaluate() + "\n");
		}
		System.out.print(output);
	}

	private static Expression buildExpression(String expressionString, Map<String, Expression> symbolTable) {
		String[] values = expressionString.split("\\s+=\\s+");

		String key = values[0].trim();
		Matcher matcher = pattern.matcher(values[1]);
		matcher.matches();

		String operation = matcher.group("operation").trim();
		String leftSide = matcher.group("leftExp").trim();
		String rightSide = matcher.group("rightExp").trim();

		Expression leftSideExpr = getExpression(leftSide, symbolTable);
		Expression rightSideExpr = getExpression(rightSide, symbolTable);

		Expression e;
		if (operation.equals("+")) {
			e = new PlusExpression(leftSideExpr, rightSideExpr, key);
		} else {
			e = new TimesExpression(leftSideExpr, rightSideExpr, key);
		}

		return e;
	}

	private static Expression getExpression(String expr, Map<String, Expression> symbolTable) {
		if (expr.matches("\\d+")) {
			return new NumeralExpression(expr);
		}
		return symbolTable.get(expr);
	}
}

abstract class Expression {
	String key;

	public abstract String evaluate();
}

class NumeralExpression extends Expression {
	public NumeralExpression(String value) {
		this.key = value;
	}

	public String evaluate() {
		return this.key;
	}
}

abstract class CompositeExpression extends Expression {
	Expression leftSide;
	Expression rightSide;
}

class PlusExpression extends CompositeExpression {
	public PlusExpression(Expression leftSide, Expression rightSide, String key) {
		this.leftSide = leftSide;
		this.rightSide = rightSide;
		this.key = key;
	}

	public String evaluate() {
		String rightEvaluation = rightSide.evaluate();
		if (rightSide instanceof PlusExpression) {
			rightEvaluation = "(" + rightEvaluation + ")";
		}
		String leftEvaluation = leftSide.evaluate();
		return leftEvaluation + "+" + rightEvaluation;
	}
}

class TimesExpression extends CompositeExpression {
	public String evaluate() {
		String rightEvaluation = rightSide.evaluate();
		if (rightSide instanceof PlusExpression || rightSide instanceof TimesExpression) {
			rightEvaluation = "(" + rightEvaluation + ")";
		}
		String leftEvaluation = leftSide.evaluate();
		if (leftSide instanceof PlusExpression) {
			leftEvaluation = "(" + leftEvaluation + ")";
		}
		return leftEvaluation + "*" + rightEvaluation;
	}

	public TimesExpression(Expression leftSide, Expression rightSide, String key) {
		this.leftSide = leftSide;
		this.rightSide = rightSide;
		this.key = key;
	}

}