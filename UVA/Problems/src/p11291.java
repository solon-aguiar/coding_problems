import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Stack;

public class p11291 {
	
	private static final DecimalFormat FORMAT = new DecimalFormat("0.00");

	public static void main(String[] args) throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();
		
		while(true) {
			String line = bf.readLine().trim();
			if (line.equals("()")) {
				break;
			}
			output.append(evaluation(line)+ "\n");
		}
		System.out.print(output);
	}

	private static String evaluation(String line) {
		line = line.replaceAll("\\(", "( ");
		line = line.replaceAll("\\)", " )");
		Stack<String> parenthesis = new Stack<>();
		Stack<Double> operands = new Stack<>();
		String[] values = line.split("\\s+");
		for(String value : values) {
			if (value.equals("(")) {
				parenthesis.push("(");
			} else if (value.equals(")")){
				parenthesis.pop();
				double e2 = operands.pop();
				double e1 = operands.pop();
				double p = operands.pop();
				operands.push(evaluate(p, e1, e2));
			} else {
				operands.push(Double.parseDouble(value));
			}
		}
		return FORMAT.format(operands.pop());
	}
	
	public static double evaluate(Double p, Double e1, Double e2) {
		if (e1 == null) {
			return p;
		}
		return p*(e1 + e2) + (1 - p)*(e1 - e2);
	}
}
