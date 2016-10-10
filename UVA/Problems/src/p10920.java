import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class p10920 {

	private static Map<Long, Long> SQUARE_ROOTS = new HashMap<>();
	private static Map<Long, Long> POWERS = new HashMap<>();

	public static void main(String[] args) throws Exception {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();

		while (true) {
			String line = bf.readLine().trim();
			if (line.equals("0 0")) {
				break;
			}

			String[] values = line.split("\\s+");
			long sz = Long.parseLong(values[0]);
			long p = Long.parseLong(values[1]);

			output.append(findPoint(sz, p) + ".\n");
		}
		System.out.print(output);
	}

	private static String findPoint(long sz, long p) {
		long center = (long) Math.ceil(sz / 2.0);

		if (p == 1) {
			return "Line = " + center + ", column = " + center;
		}

		long squareSize = getTop(p);
		long topCoordinates = center + (squareSize - 1) / 2;
		
		long topVal = getSquare(squareSize);

		long dist = topVal - p;
		return find(dist, topCoordinates, squareSize);
	}

	private static String find(long dist, long topCoordinates, long squareSize) {
		long hops = squareSize - 1;
		long complete = (long) Math.ceil((double) dist / hops) - 1;
		long r = dist % hops;

		if (r == 0) {
			complete++;
			if (complete == 0) {
				return "Line = " + topCoordinates + ", column = " + topCoordinates;
			} else if (complete == 1) {
				return "Line = " + (topCoordinates - hops) + ", column = " + topCoordinates;
			} else if (complete == 2) {
				return "Line = " + (topCoordinates - hops) + ", column = " + (topCoordinates - hops);
			} else {
				return "Line = " + topCoordinates + ", column = " + (topCoordinates - hops);
			}
		} else {
			if (complete < 0) {
				return "Line = " + topCoordinates + ", column = " + topCoordinates;
			} else if (complete == 0) {
				return "Line = " + (topCoordinates - r) + ", column = " + topCoordinates;
			} else if (complete == 1) {
				return "Line = " + (topCoordinates - hops) + ", column = " + (topCoordinates - r);
			} else if (complete == 2) {
				return "Line = " + (topCoordinates - hops + r) + ", column = " + (topCoordinates - hops);
			} else {
				return "Line = " + topCoordinates + ", column = " + (topCoordinates - hops + r);
			}
		}
	}

	private static long getSquare(long squareSize) {
		Long power = POWERS.get(squareSize);
		if (power != null) {
			return power;
		}
		long sqr = squareSize * squareSize;
		POWERS.put(squareSize, sqr);
		return sqr;
	}

	private static long getTop(long p) {
		Long sqrt = SQUARE_ROOTS.get(p);
		if (sqrt != null) {
			return sqrt;
		}
		sqrt = (long) Math.ceil(Math.sqrt(p));
		if (sqrt % 2 == 0) {
			sqrt++;
		}
		SQUARE_ROOTS.put(p, sqrt);

		return sqrt;
	}
}
