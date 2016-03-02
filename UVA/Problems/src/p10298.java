import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class p10298 {

	public static void main(String[] args) throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();
		
		while (true) {
			String s = bf.readLine();
			if (s == null || s.equals(".")) {
				break;
			}
			output.append(getN(s) + "\n");
		}
		System.out.print(output);
	}

	private static int getN(String s) {
		StringBuilder sequence = new StringBuilder();
		sequence.append(s.charAt(0));
		for (int i = 1; i < s.length(); i++) {
			if (s.length() % i == 0) {
				int repetitions = s.length() / i;
				StringBuilder composed = new StringBuilder();
				for (int j = 0; j < repetitions; j++) {
					composed.append(sequence);
				}
				if (s.equals(composed.toString())) {
					return repetitions;
				}
			}
			sequence.append(s.charAt(i));
		}
		return 1;
	}
}
