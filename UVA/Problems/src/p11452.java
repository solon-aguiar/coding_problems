import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class p11452 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();

		int cases = Integer.parseInt(bf.readLine());
		for (int i = 0; i < cases; i++) {
			String dance = bf.readLine();
			String sequence = getSequence(dance);

			String completeDance = "";
			while (completeDance.length() < dance.length() + 8) {
				completeDance += sequence;
			}
			output.append(completeDance.substring(dance.length(), dance.length() + 8) + "...\n");
		}
		System.out.print(output);
	}

	private static String getSequence(String dance) {
		String seenSequence = "" + dance.charAt(0);
		for (int i = 1; i < dance.length(); i++) {
			String candidate = dance.substring(i, i + seenSequence.length());
			if (candidate.equals(seenSequence) && seenSequence.length() >= dance.length()/3) {
				break;
			}
			seenSequence += dance.charAt(i);
		}
		return seenSequence;
	}
}
