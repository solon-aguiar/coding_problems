import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class p11203 {
	
	private static final Pattern PATTERN = Pattern.compile("(?<group>\\?{1,})(M)(?<group2>\\?{1,})(E)(?<group3>\\?{1,})(\\?{1,})");

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();
		
		int cases = Integer.parseInt(bf.readLine());
		for (int i = 0; i < cases; i++) {
			String testLine = bf.readLine();
			output.append(evaluation(testLine) + "\n");
		}
		System.out.print(output);
	}

	private static String evaluation(String testLine) {
		if (isTheorem(testLine)) {
			return "theorem";
		}
		return "no-theorem";
	}

	private static boolean isTheorem(String testLine) {
		Matcher matcher = PATTERN.matcher(testLine);
		if (!matcher.matches()) {
			return false;
		}
		String x1 = matcher.group("group");
		String x2 = matcher.group("group2");
		String x3 = matcher.group("group3");
		
		int extra = x3.length() - x1.length();
		if (extra < 0) {
			return false;
		}
		return extra == x2.length() - 1;
	}
}
