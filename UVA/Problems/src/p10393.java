import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class p10393 {
	private static int[] letterToFingerMapping = { 
			1, // a
			4, // b
			3, // c
			3, // d
			3, // e
			4, // f
			4, // g
			7, // h
			8, // i
			7, // j
			8, // k
			9, // l
			7, // m
			7, // n
			9, // o
			10, // p
			1, // q
			4, // r
			2, // s
			4, // t
			7, // u
			4, // v
			2, // w
			2, // x
			7, // y
			1, // z
	};

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		StringBuilder output = new StringBuilder();
		
		while (sc.hasNext()) {
			int f = sc.nextInt();
			int n = sc.nextInt();

			int [] impossibleFingers = new int[f];
			for (int i = 0; i < f; i++) {
				impossibleFingers[i] = sc.nextInt();
			}
			
			int maximumSize = Integer.MIN_VALUE;
			Set<String> words = new TreeSet<>();

			for (int i = 0; i < n; i++) {
				String word = sc.next();
				if (canType(word, impossibleFingers)) {
					if (word.length() > maximumSize) {
						maximumSize = word.length();
						words = new TreeSet<>();
						words.add(word);
					} else if (word.length() == maximumSize) {
						words.add(word);
					}
				}
			}
			output.append(words.size() + "\n");

			for (String possibleWord : words) {
				output.append(possibleWord + "\n");
			}
		}
		sc.close();
		System.out.print(output);
	}

	private static boolean canType(String word, int[] forbidFingers) {
		Set<Integer> requiredFingers = computeRequiredFingers(word);
		for (int i = 0; i < forbidFingers.length; i++) {
			if (requiredFingers.contains(forbidFingers[i])) {
				return false;
			}
		}
		return true;
	}

	private static Set<Integer> computeRequiredFingers(String word) {
		Set<Integer> requiredFingers = new HashSet<>();
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			requiredFingers.add(letterToFingerMapping[c - 'a']);
		}
		return requiredFingers;
	}
}
