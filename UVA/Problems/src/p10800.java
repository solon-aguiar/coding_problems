import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class p10800 {
	public static void main(String[] args) throws Exception {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();
		
		int cases = Integer.parseInt(bf.readLine());
		for (int i = 0; i < cases; i++) {
			String line = bf.readLine();
			output.append("Case #" + (i + 1) + ":\n");
			graph(line, output);
		}
		System.out.print(output);
	}

	private static void graph(String line, StringBuilder out) {
		Map<Integer,List<StockResult>> pointsInTime = new HashMap<>();
		
		int y = 0;
		List<StockResult> stockResults = new ArrayList<StockResult>();
		int smallestY = 0;
		for(int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			StockResult result;
			if (c == 'R') {
				y++;
				result = new RiseStock(i + 1, y);
			} else if (c == 'F') {
				result = new DownStock(i + 1, y);
				y--;
			} else {
				result = new ConstantStock(i + 1, y);
			}
			
			if (y < smallestY) {
				smallestY = y;
			}
			stockResults.add(result);
		}
		for (StockResult s: stockResults) {
			s.adjust((int) Math.abs(smallestY));
			if (!pointsInTime.containsKey(s.getLine())) {
				pointsInTime.put(s.getLine(), new LinkedList<>());
			}
			pointsInTime.get(s.getLine()).add(s);
		}
		Object[] keys = pointsInTime.keySet().toArray();
		Arrays.sort(keys);
		
		for (int i = keys.length - 1; i >=0; i--) {
			int key = (int) keys[i];
			List<StockResult> points = pointsInTime.get(key);
			int start = 0;
			out.append("|");
			for (StockResult s : points) {
				int end = s.day;
				for (;start < end; start++) {
					out.append(" ");
				}
				out.append(s.getString());
				start++;
			}
			out.append("\n");
		}
		out.append("+");
		for(int i = 0; i <= line.length() + 1; i++) {
			out.append("-");
		}
		out.append("\n\n");
	}
}

abstract class StockResult {
	int day;
	int height;
	
	public StockResult(int day, int height) {
		this.day = day;
		this.height = height;
	}
	
	public abstract int getLine();
	
	public void adjust(int val) {
		this.height += val;
	}
	
	public abstract String getString();
}

class RiseStock extends StockResult {
	public RiseStock(int day, int height) {
		super(day, height);
	}

	public int getLine() {
		return height - 1;
	}

	public String getString() {
		return "/";
	}
}

class DownStock extends StockResult {
	public DownStock(int day, int height) {
		super(day, height);
	}

	public int getLine() {
		return height - 1;
	}

	public String getString() {
		return "\\";
	}
}

class ConstantStock extends StockResult {
	public ConstantStock(int day, int height) {
		super(day, height);
	}

	public int getLine() {
		return height;
	}

	public String getString() {
		return "_";
	}
}