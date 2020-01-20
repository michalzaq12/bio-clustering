import javafx.util.Pair;
import java.util.*;

public class AdjacencyMatrix {

    private Map<Pair<String, String>, Integer> adjacencyMatrix = new HashMap<>();
    private HashSet<String> uniqueLabels = new HashSet<>();
    private int i = 0;

    public AdjacencyMatrix(Node root) {
        makeLabelsUnique(root);
        walk(root);
    }

    private void makeLabelsUnique(Node rootNode) {
        if (uniqueLabels.contains(rootNode.getLabel())) {
            rootNode.setLabel("BRANCH_" + i);
            i++;
        }
        System.out.println(rootNode.getLabel());
        uniqueLabels.add(rootNode.getLabel());
        rootNode.getChildren().forEach(this::makeLabelsUnique);
    }

    private void walk(Node rootNode) {
        for (Node node : rootNode.getChildren()) {
//            int position2 = adjacencyMatrix.establishPosition(node.getLabel());
//            data[position][position2] = 1;
//            data[position2][position] = 1;
            adjacencyMatrix.put(new Pair<>(rootNode.getLabel(), node.getLabel()), 1);
            adjacencyMatrix.put(new Pair<>(node.getLabel(), rootNode.getLabel()), 1);
            walk(node);
        }
    }

    public void print() {
        System.out.print(adjacencyMatrix);
    }

    public Node toCanonicalTree() {
        List<String> visited = new ArrayList<>();
        return createNodes("a", visited);
    }

    private Node createNodes(String label, List<String> visited) {
        visited.add(label);
        Node node = new Node(label);

        for (Map.Entry<Pair<String,String>, Integer> entry : adjacencyMatrix.entrySet()) {
            String label2= entry.getKey().getKey();
            if (!visited.contains(label2) && adjacencyMatrix.containsKey(new Pair<>(label, label2))) {
                node.getChildren().add(createNodes(label2, visited));
            }
        }

        return node;
    }
}