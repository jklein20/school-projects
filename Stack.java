import java.util.Vector;


public class Stack<E> {

    
    private Vector<E> list;

    public int size() {
	return list.size();
    }

    public boolean isEmpty() {
	return list.isEmpty();
    }

    public void push(E toAdd) {
	list.add(toAdd);

    }

    public E pop() {
	if(list.isEmpty()) return null;

	else {
	    return list.remove(list.size() - 1);
	}
    }

    public E peek() {
	return list.get(list.size() - 1);
    }
    

    public void print() {
	System.out.print("[");
	for(int i = list.size() - 1; i >= 0; i--) {
	    System.out.print(list.elementAt(i) + " ");
	}
	System.out.println("]");
    }

    public Stack() {
	list = new Vector<E>();
    }

}
