import javafx.util.Pair;
import java.util.*;

public class AdjacencyMatrix {

    private Map<Pair<String, String>, Integer> matrix = new HashMap<>();
    private HashSet<String> labels = new HashSet<>();
    private int i = 0;

    public AdjacencyMatrix(Node root) {
        prepareLabels(root);
        walk(root);
    }

    private void prepareLabels(Node rootNode) {
        if (labels.contains(rootNode.getLabel())) {
            rootNode.setLabel(rootNode.getLabel() + (i++));
        }
        labels.add(rootNode.getLabel());
        rootNode.getChildren().forEach(this::prepareLabels);
    }

    private void walk(Node rootNode) {
        for (Node node : rootNode.getChildren()) {
            matrix.put(new Pair<>(rootNode.getLabel(), node.getLabel()), 1);
            matrix.put(new Pair<>(node.getLabel(), rootNode.getLabel()), 1);
            walk(node);
        }
    }


    public Node toCanonicalTree() {
        return createNodes("a", new ArrayList<>());
    }

    private Node createNodes(String label, List<String> visited) {
        visited.add(label);
        Node node = new Node(label);

        for (Map.Entry<Pair<String,String>, Integer> entry : matrix.entrySet()) {
            String label2= entry.getKey().getKey();
            if (!visited.contains(label2) && matrix.containsKey(new Pair<>(label, label2))) {
                node.getChildren().add(createNodes(label2, visited));
            }
        }

        return node;
    }
}