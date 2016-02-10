import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class p11385 {
	private static int[] fibonacci = new int[1000];
	private static Map<Integer, Integer> fibIndex = new HashMap<>();
	private static int calculatedIndex = 1;

	public static void main(String[] args) throws NumberFormatException, IOException {
		fibonacci[0] = 1;
		fibonacci[1] = 2;

		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();

		int cases = Integer.parseInt(bf.readLine());

		for (int i = 0; i < cases; i++) {
			int numbers = Integer.parseInt(bf.readLine());

			char[] decoded = new char[100];

			String[] fibs = bf.readLine().split("\\s+");
			Set<Integer> seenFibs = new HashSet<>();
			int maxFib = Integer.MIN_VALUE;
			char[] cyphered = bf.readLine().toCharArray();
			int currentStringPos = 0;

			for (int j = 0; j < numbers; j++) {
				int currentFib = Integer.parseInt(fibs[j]);
				if (currentFib > maxFib) {
					maxFib = currentFib;
				}
				seenFibs.add(currentFib);
				char c = cyphered[currentStringPos++];
				while (!Character.isUpperCase(c)) {
					c = cyphered[currentStringPos++];
				}
				decoded[fibIndex(currentFib)] = c;
			}

			int maxFibIndex = fibIndex(maxFib);
			for (int j = 0; j <= maxFibIndex; j++) {
				int fib = fibonacci[j];
				if (!seenFibs.contains(fib)) {
					output.append(" ");
				} else {
					output.append(decoded[j]);
				}
			}
			output.append("\n");
		}
		System.out.print(output.toString());
	}

	private static int fibIndex(int num) {
		if (fibIndex.containsKey(num)) {
			return fibIndex.get(num);
		}

		int index = 0;
		if (num > fibonacci[calculatedIndex]) {
			index = calculateFibonnaci(num);
		}
		index = index(num);
		fibIndex.put(num, index);

		return index;
	}

	private static int index(int num) {
		return binarySearch(num, 0, calculatedIndex);
	}

	private static int binarySearch(int num, int start, int end) {
		int mid = start + (end - start) / 2;

		if (fibonacci[mid] == num) {
			return mid;
		} else if (fibonacci[mid] < num) {
			return binarySearch(num, mid + 1, end);
		} else {
			return binarySearch(num, start, mid - 1);
		}
	}

	private static int calculateFibonnaci(int num) {
		int cur = fibonacci[calculatedIndex];
		int prev = fibonacci[calculatedIndex - 1];
		while (cur != num) {
			calculatedIndex++;
			fibonacci[calculatedIndex] = cur + prev;
			fibIndex.put(cur + prev, calculatedIndex);
			prev = cur;
			cur = fibonacci[calculatedIndex];
		}
		return calculatedIndex;
	}
}
