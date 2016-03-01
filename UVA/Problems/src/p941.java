import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;

public class p941 {

	private static BigInteger[] factorials = new BigInteger[20];

	public static void main(String[] args) throws NumberFormatException, IOException {
		calculateFactorials();
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();
		
		int cases = Integer.parseInt(bf.readLine());
		for (int i = 0; i < cases; i++) {
			char[] s = bf.readLine().toCharArray();
			Arrays.sort(s);
			
			LinkedList<Character> chars = new LinkedList<Character>();
			for (char c: s) {
				chars.addLast(c);
			}
			
			BigInteger n = new BigInteger(bf.readLine());
			while (chars.size() > 1) {
				int combinationsTotal = chars.size() - 1;
				
				BigInteger[] divideAndRemainder = n.divideAndRemainder(factorials[combinationsTotal]);
				
				int index = divideAndRemainder[0].intValue();
				output.append(chars.remove(index));
				n = n.subtract(factorials[combinationsTotal].multiply(divideAndRemainder[0]));
			}
			output.append(chars.remove(0));
			output.append("\n");
		}
		System.out.print(output);
	}

	public static void calculateFactorials() {
		factorials[0] = BigInteger.ONE;
		for (int i = 1; i < 20; i++) {
			factorials[i] = new BigInteger("" + i).multiply(factorials[i - 1]);
		}
	}
}
