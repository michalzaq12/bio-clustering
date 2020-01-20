public class Node {
    private String label = "•";

    private Node child1 = null;
    private Node child2 = null;

    private double distanceToParent = 0.0;

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

    @Override
    public String toString() {
        return label;
    }
}
