/*---------------------------------------------------------------------------
ANSWER THE QUESTIONS FROM THE DOCUMENT HERE

(1) Which graph representation did you choose, and why?

I chose to represent the graph as an adjacency list. An adjacency list runs faster and because the graph is not too dense (not close to the max number of possible edges), an adjacency list is the more prudent choice to save space and time. Because the graph is not dense, O(n+m) < O(n^2) which means that an adjacency list will save space and time.

(2) Which search algorithm did you choose, and why?

I chose Depth-First search because I knew that there was only one possible solution to each maze (without backtracking) so DFS would not get stuck in an infinite loop (which is a possible disadvantage of DFS). I also knew that DFS could arrive at the end of the maze before checking every single other node of the graph, which would potentially save time and memory.

---------------------------------------------------------------------------*/

import java.io.*;
import java.lang.Math;
import java.util.LinkedList;

public class MazeSolver {

    public void run(String filename) throws IOException {

	// read the input file to extract relevant information about the maze
	String[] readFile = parse(filename);
	int mazeSize = Integer.parseInt(readFile[0]);
	int numNodes = mazeSize*mazeSize;
	String mazeData = readFile[1];

	// construct a maze based on the information read from the file
	Graph mazeGraph = buildGraph(mazeData, numNodes);

	// do something here to solve the maze
	Node[] cool = solve(mazeGraph);

	// print out the final maze with the solution path
	printMaze(mazeGraph.nodes, mazeData, mazeSize);
    }

    // prints out the maze in the format used for HW8
    // includes the final path from entrance to exit, if one has been recorded,
    // and which cells have been visited, if this has been recorded
    public void printMaze(Node[] mazeCells, String mazeData, int mazeSize) {
	
	int ind = 0;
	int inputCtr = 0;

	System.out.print("+");
	for(int i = 0; i < mazeSize; i++) {
	    System.out.print("--+");
	}
	System.out.println();

	for(int i = 0; i < mazeSize; i++) {
	    if(i == 0) System.out.print(" ");
	    else System.out.print("|");

	    for(int j = 0; j < mazeSize; j++) {
		System.out.print(mazeCells[ind] + "" + mazeCells[ind] +  mazeData.charAt(inputCtr));
		inputCtr++;
		ind++;
	    }
	    System.out.println();

	    System.out.print("+");
	    for(int j = 0; j < mazeSize; j++) {
		System.out.print(mazeData.charAt(inputCtr) + "" +  mazeData.charAt(inputCtr) + "+");
		inputCtr++;
	    }
	    System.out.println();
	}
	
    }

    // reads in a maze from an appropriately formatted file (this matches the
    // format of the mazes you generated in HW8)
    // returns an array of Strings, where position 0 stores the size of the maze
    // grid (i.e., the length/width of the grid) and position 1 stores minimal 
    // information about which walls exist
    public String[] parse(String filename) throws IOException {
	FileReader fr = new FileReader(filename);

	// determine size of maze
	int size = 0;
	int nextChar = fr.read();
	while(nextChar >= 48 && nextChar <= 57) {
	    size = 10*size + nextChar - 48;
	    nextChar = fr.read();
	}

	String[] result = new String[2];
	result[0] = size + "";
	result[1] = "";


	// skip over up walls on first row
	for(int j = 0; j < size; j++) {
	    fr.read();
	    fr.read();
	    fr.read();
	}
	fr.read();
	fr.read();

	for(int i = 0; i < size; i++) {
	    // skip over left wall on each row
	    fr.read();
	    
	    for(int j = 0; j < size; j++) {
		// skip over two spaces for the cell
		fr.read();
		fr.read();

		// read wall character
		nextChar = fr.read();
		result[1] = result[1] + (char)nextChar;

	    }
	    // clear newline character at the end of the row
	    fr.read();
	    
	    // read down walls on next row of input file
	    for(int j = 0; j < size; j++)  {
		// skip over corner
		fr.read();
		
		//skip over next space, then handle wall
		fr.read();
		nextChar = fr.read();
		result[1] = result[1] + (char)nextChar;
	    }

	    // clear last wall and newline character at the end of the row
	    fr.read();
	    fr.read();
	    
	}

	return result;
    }
    
    public Graph buildGraph(String maze, int numNodes) {

	Graph mazeGraph = new Graph(numNodes);
	int size = (int)Math.sqrt(numNodes);

	int mazeInd = 0;
	for(int i = 0; i < size; i++) {
	    // create edges for right walls in row i
	    for(int j = 0; j < size; j++) {
		char nextChar = maze.charAt(mazeInd);
		mazeInd++;
		if(nextChar == ' ') {
		    // add an edge corresponding to a right wall, using the 
		    // indexing convention for nodes
		    mazeGraph.addEdge(size*i + j, size*i + j + 1);
		}
	    }

	    // create edges for down walls below row i
	    for(int j = 0; j < size; j++)  {
		char nextChar = maze.charAt(mazeInd);
		mazeInd++;
		if(nextChar == ' ') {
		    // add an edge corresponding to a down wall, using the 
		    // indexing convention for nodes
		    mazeGraph.addEdge(size*i + j, size*(i+1) + j);
		}
	    }    
	}

	return mazeGraph;
    }


    public Node[] solve(Graph g) {

	Stack<Node> s = new Stack<Node>();

	Node n = null;
	int counter = 0;
	s.push(g.get(0));
	int length = g.getLength();
	while(s.peek().index != length-1) {
	    n = s.peek();
	    int size = n.list.size();
	    Node finder = null;
	    for(int i = 0; i < size; i += 1){
		if(n.list.get(i).visited == false){ 
		    finder = n.list.get(i);
		}
	    }
	    //If node hasn't been visited yet:
	    if(n.visited == false) {

		    //If node has no unvisited children or no children, backtrack until you find node with unvisited children, setting inSolution to false
		    if(finder == null){
			n.inSolution = false;
			n.visited = true;
			s.pop();
		    }


		    //If node has unvisited children, visit them
		    else if (finder != null) {
			
			n.visited = true;
			n.inSolution = true;
			s.push(g.get(finder.index));
			
		    }
	    
	    }
	    
	    //If node has been visited and has an unvisited child, add the unvisited child
	    else if(n.visited == true && n.inSolution == true && finder != null) {
		n.visited = true;
		s.push(g.get(finder.index));
		
	 
	    }



	    else if(n.visited = true && finder == null) {
		s.pop();
		n.inSolution = false;
	
	    //	n.inSolution = false;
	    }

	    //counter++;

	}


	Node x = s.pop();
	x.inSolution = true;
	x.visited = true;
	s.push(x);

	//System.out.println(s.peek().inSolution);

	Node[] toReturn = new Node[s.size()];


	for(int i = s.size()-1; i >= 0; i -= 1){
	    toReturn[i] = s.pop();
	    //System.out.println(toReturn[i].inSolution);
	}


	//System.out.println(toReturn[9].inSolution);

	

	return toReturn;



    }
       
    public static void main(String [] args) {
	if(args.length < 1) {
	    System.out.println("USAGE: java MazeSolver <filename>");
	}
	else{
	    try{
		new MazeSolver().run(args[0]);
	    }
	    catch(IOException e) {
		e.printStackTrace();
	    }
	}
    }

}