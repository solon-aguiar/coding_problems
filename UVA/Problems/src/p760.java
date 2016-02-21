import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeSet;

public class p760 {

	public static void main(String[] args) throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();
		boolean isFirst = true;
		boolean shouldExit = false;

		while (!shouldExit) {
			String seq1 = bf.readLine();
			String seq2 = bf.readLine();
			if (bf.readLine() == null) {
				shouldExit = true;
			}

			TreeSet<String> matching = longestCommonSubstrings(seq1, seq2);
			if (!isFirst) {
				output.append("\n");
			}
			if (matching.isEmpty()) {
				output.append("No common sequence.\n");
			} else {
				for (String s : matching) {
					output.append(s + "\n");
				}
			}
			isFirst = false;
		}
		System.out.print(output);
	}

	public static TreeSet<String> longestCommonSubstrings(String s, String t) {
		TreeSet<String> ret = new TreeSet<>();
		int[][] l = new int[s.length()][t.length()];
		int max = 0;
		for (int i = 0; i < s.length(); i++) {
			for (int j = 0; j < t.length(); j++) {
				char sC = s.charAt(i);
				char tC = t.charAt(j);
				if (sC == tC) {
					if (i == 0 || j == 0) {
						l[i][j] = 1;
					} else {
						l[i][j] = l[i - 1][j - 1] + 1;
					}
					if (l[i][j] > max) {
						max = l[i][j];
						ret = new TreeSet<>();
					}
					if (l[i][j] == max) {
						ret.add(s.substring(i - max + 1, i + 1));
					}
				} else {
					l[i][j] = 0;
				}
			}
		}
		return ret;
	}
}
