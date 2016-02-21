import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class p12155 {
	public static void main(String[] args) throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();
		int caseNo = 1;
		
		while(true) {
			String[] values = bf.readLine().split("\\s+");
			if (values[0].equals("0")) {
				break;
			}
			int n = Integer.parseInt(values[0]);
			int row1 = Integer.parseInt(values[1]);
			int col1 = Integer.parseInt(values[2]);
			int row2 = Integer.parseInt(values[3]);
			int col2 = Integer.parseInt(values[4]);
			
			output.append("Case " + caseNo + ":\n");
			
			for (int i = row1; i <= row2; i++) {
				for (int j = col1; j <= col2; j++) {
					output.append(getChar(i + 1, j + 1, n));
				}
				output.append("\n");
			}
			caseNo++;
		}
		System.out.print(output);
	}
	
	private static char getChar(int row, int column, int n) {
		if (n == 1) {
			return 'a';
		}
		int factor = 2*n-1;
		int xd = (int) Math.abs((n + (row/factor)*factor)-row);
		int yd = (int) Math.abs((n + (column/factor)*factor)-column);
		
		if (xd == 0 && yd == 0) {
			return 'a';
		} 
		else if (xd == 0 && yd == n) {
			return (char) ('a' + yd - 1);
		} 
		else if (yd == 0 && xd == n) {
			return (char) ('a' + xd - 1);
		}
        else if (xd + yd >= n) {
			return '.';
		} 
		return (char) ('a' + (xd + yd)%26);
	}
}
