import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class p11878 {
	public static void main(String[] args) throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		
		int correctAnswers = 0;
		
		while(true) {
			String line = bf.readLine();
			if (line == null) {
				break;
			}
			String[] tokens = line.split("=");
			if (tokens[1].contentEquals("?")) {
				continue;
			}
			String[] ab = tokens[0].split("[\\+\\-]");
			int c = Integer.parseInt(tokens[1]);
			int a = Integer.parseInt(ab[0]);
			int b = Integer.parseInt(ab[1]);
			int calc = 0;
			
			int op = tokens[0].indexOf("+");
			if (op == -1) {
			  	calc = a - b;
			} else {
				calc = a + b;
			}
			if (calc == c) {
				correctAnswers++;
			}
		}
		System.out.println(correctAnswers);
	}
}
