import java.util.ArrayList;

public class Cluster {

    private ArrayList<Node> members;
    private Node root;

    public Cluster(Node initialNode){
        this.members = new ArrayList<>();
        members.add(initialNode);
        root = initialNode;
    }


    public Cluster(Cluster cluster1, Cluster cluster2, Node root){
        this.members = new ArrayList<>();
        this.members.addAll(cluster1.getMembers());
        this.members.addAll(cluster2.getMembers());
        this.root = root;
    }


    public Node getRoot(){
        return root;
    }

    public ArrayList<Node> getMembers() {
        return members;
    }

    public void addMember(Node node){
        members.add(node);
    }


    @Override
    public String toString() {
        String out = "[";
        for(Node member: members){
            out += member + ",";
        }
        out += "]";
        return out;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != getClass())
            return false;
        Cluster c = (Cluster) o;
        return c.toString().equals(this.toString());
    }
}
