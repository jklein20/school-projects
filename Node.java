import java.util.LinkedList;

public class Node {

    int index;
    LinkedList<Node> list;

    // to help you keep track of things as you're solving the maze
    boolean visited = false;
    boolean inSolution = false;

    static final String PATH = "X";
    static final String VISIT = ".";
    static final String NOT_VISIT = " ";

    public String toString() {
	if(visited) {
	    if(inSolution) return PATH;
	    else return VISIT;
	}
	else return NOT_VISIT;
    }


    public Node(int i) {
	index = i;
	list = new LinkedList<Node>();
    }

}