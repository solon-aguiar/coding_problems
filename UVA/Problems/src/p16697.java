import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class p16697 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();
		
		int cases = Integer.parseInt(bf.readLine());
		for(int i = 0; i < cases; i++) {
			String keyPhrase = bf.readLine();
			String text = bf.readLine().replaceAll("\\s+", "");
			
			Table t = buildTable(keyPhrase);
			int index = 0;
			while(index < text.length()) {
				Character cur = text.charAt(index++);
				Character next = null;
				if (index < text.length()) {
					next = text.charAt(index++);
				}
				if (next!= null && cur.charValue() == next.charValue()) {
					index--;
					next = 'x';
				}
				String cypher = process(cur, next, t);
				output.append(cypher.toUpperCase());
			}
			output.append("\n");
		}
		System.out.print(output.toString());
	}
	
	private static String process(Character a, Character b, Table t) {
		if (b == null) {
			return process(a,'x',t);
		} else if (t.sameRow(a, b)) {
			char rightA = t.findRight(a);
			char rightB = t.findRight(b);
			return rightA + "" + rightB;
		} else if (t.sameColumn(a, b)) {
			char belowA = t.findBelow(a);
			char belowB = t.findBelow(b);
			return belowA + "" + belowB;
		}
		return t.rectangle(a, b) + "" + t.rectangle(b, a);
	}

	private static Table buildTable(String keyPhrase) {
		keyPhrase = keyPhrase.replaceAll("\\s+", "");
		Table t = new Table();
		boolean[] used = new boolean['z'-'a'+1];
		for(int i = 0; i < keyPhrase.length(); i++){
			char c = keyPhrase.charAt(i);
			if (!used[c-'a']){
				t.add(c);
				used[c-'a'] = true;
			}
		}
		for(int i = 0; i < used.length; i++) {
			if (!used[i] && i != 16){
				t.add((char) ('a'+i)); 
			}
		}
		return t;
	}
	
	public static void printTable(char[] [] t) {
		for (int i = 0; i < t.length; i++) {
			System.out.println(Arrays.toString(t[i]));
		}
	}
}
class Table {
	char[] [] table;
	Map<Character,Location> locations;
	
	private int currentRow;
	private int currentColumn;
	
	Table() {
		currentRow = 0;
		currentColumn = 0;
		table = new char[5][5];
		locations = new HashMap<>();
	}
	
	void add(char c) {
		table[currentRow][currentColumn] = c;
		locations.put(c, new Location(currentRow, currentColumn));
		currentColumn++;
		if (currentColumn > 4) {
			currentRow++;
			currentColumn = 0;
		}
	}
	
	char findRight(char c) {
		Location pos = locations.get(c);
		int x = pos.row;
		int y = pos.column + 1;
		if (y > 4) {
			y = 0;
		}
		return table[x][y];
	}
	
	char findBelow(char c) {
		Location pos = locations.get(c);
		int x = pos.row + 1;
		int y = pos.column;
		if (x > 4) {
			x = 0;
		}
		return table[x][y];
	}
	
	boolean sameRow(char c, char b) {
		Location posC = locations.get(c);
		Location posB = locations.get(b);
		
		return posC.row == posB.row;
	}
	
	boolean sameColumn(char c, char b) {
		Location posC = locations.get(c);
		Location posB = locations.get(b);
		
		return posC.column == posB.column;
	}
	
	char rectangle(char a, char b) {
		Location posA = locations.get(a);
		Location posB = locations.get(b);
		
		return table[posA.row][posB.column];
	}
}
class Location {
	int row;
	int column;
	
	Location(int x, int y) {
		row = x;
		column = y;
	}
}
