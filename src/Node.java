import java.util.LinkedList;

public class Node {
    private String label = "•";
    private LinkedList<Node> children = new LinkedList<>();
    private boolean isBranch = true;

    private double distanceToParent = 0.0;

    public Node(String label){
        this.label = label;
        this.isBranch = false;
    }

    public Node(){}

    public void addChild(Node child){
        children.add(child);
    }

    public Node getChild1() {
        if(children.isEmpty()) return null;
        return children.get(0);
    }

    public Node getChild2() {
        if(children.size() < 2) return null;
        return children.get(1);
    }

    public LinkedList<Node> getChildren() {
        return children;
    }

    public void setDistanceToParent(double distance){
        this.distanceToParent = distance;
    }

    private String printDistanceToParent(){
        if(distanceToParent > 0.0) return " (" + distanceToParent + ")";
        else return "";
    }

    public double getDistanceToParent(){
        return this.distanceToParent;
    }

    public String getLabelWithDistance(){
        return label + this.printDistanceToParent();
    }

    public boolean isBranch() {
        return isBranch;
    }

    @Override
    public String toString() {
        return label;
    }


    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
