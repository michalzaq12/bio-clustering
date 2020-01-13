public class Node {
    private String label = "•";

    private Node child1 = null;
    private Node child2 = null;

    public Node(String label){
        this.label = label;
    }

    public Node(){}

    public void addChild(Node child){
        if(child1 == null) child1 = child;
        else if(child2 == null) child2 = child;
        else System.out.println("Error. To many children");
    }

    public Node getChild1() {
        return child1;
    }

    public Node getChild2() {
        return child2;
    }

    @Override
    public String toString() {
        return label;
    }
}
