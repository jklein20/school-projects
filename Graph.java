public class Graph {

    int numNodes;
    Node[] nodes;

    public void addEdge(int i, int j) {

	if(j >= nodes.length)
	    return;

	nodes[i].list.add(nodes[j]);

	nodes[j].list.add(nodes[i]);

    }

    //Returns true if an edge exists between the two nodes. 
    public boolean edgeExists(int i){

	return(nodes[i].list.isEmpty());

    }

    public Node get(int i){
	return nodes[i];


    }

    public int getLength(){
	return nodes.length;
    }


    
 

     Graph(int num) {
	numNodes = num;
	nodes = new Node[numNodes];
	for(int i = 0; i < numNodes; i++) {
	    nodes[i] = new Node(i);
	}

	// you might also want to do other things here
    }

}