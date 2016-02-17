import java.util.List;
import java.util.Stack;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class p10854 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();

		int cases = Integer.parseInt(bf.readLine().trim());
		for (int i = 0; i < cases; i++) {
			Program program = new Program();
			while (true) {
				String line = bf.readLine().trim();
				if (line.equals("ENDPROGRAM")) {
					break;
				}
				if (line.equals("IF")) {
					program.createIf();
				} else if (line.equals("ELSE")) {
					program.goElse();
				} else if (line.equals("END_IF")) {
					program.doneConditional();
				}
			}
			long total = program.totalPaths();
			output.append(total + "\n");
		}
		System.out.print(output);
	}

}

class Program {
	private Stack<IfElse> ifs;
	private List<IfElse> branches;
	
	public Program() {
		this.ifs = new Stack<>();
		branches = new LinkedList<>();
	}

	public long totalPaths() {
		long total = 1;
		for (IfElse branch : branches) {
			total *= branch.totalPaths();
		}
		return total;
	}
	
	public void createIf() {
		IfElse newNode = new IfElse();
		IfElse topOfStack = getTopOfStack();
		
		if (topOfStack != null) {
			topOfStack.addChild(newNode);
		} else {
			branches.add(newNode); //no parent - is top level 
		}
		
		ifs.push(newNode);
	}
	
	private IfElse getTopOfStack() {
		if (!this.ifs.isEmpty()) {
			return ifs.peek();
		}
		return null;
	}

	public void doneConditional() {
		ifs.pop();
	}
	
	public void goElse() {
		ifs.peek().doneIf();
	}
}

class IfElse {

	private Node ifNode;
	private Node active;
	private Node elseNode;

	public IfElse() {
		ifNode = new Node();
		active = ifNode;
	}

	public void doneIf() {
		elseNode = new Node();
		active = elseNode;
	}
	
	public void addChild(IfElse newChild) {
		this.active.addChild(newChild);
	}

	public long totalPaths() {
		return ifNode.totalPaths() + elseNode.totalPaths();
	}

}

class Node {
	private List<IfElse> children;

	public Node() {
		children = new LinkedList<>();
	}

	public void addChild(IfElse newChild) {
		children.add(newChild);
	}

	public long totalPaths() {
		int total = 1;
		for (IfElse child : children) {
			total *= child.totalPaths();
		}
		return total;
	}
}