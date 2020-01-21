import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        MatrixReader matrixReader = new MatrixReader("test.txt");

        UPGMA upgma = new UPGMA(matrixReader.getDistanceMatrix(), matrixReader.getClusters());
        Node upgmaTree = upgma.build();
        TreePrinter upgmaPrinter = new TreePrinter(upgmaTree);
        System.out.println("\n__UPGMA__");
        System.out.print(upgmaPrinter);

        NJ nj = new NJ(matrixReader.getDistanceMatrix(), matrixReader.getClusters());
        Node njTree = nj.build();
        TreePrinter njPrinter = new TreePrinter(njTree);
        System.out.println("\n___NJ___");
        System.out.print(njPrinter);



        // ---------------------
        AdjacencyMatrix adjacencyMatrixUpgma = new AdjacencyMatrix(upgmaTree);
        Node upgmaCanonical = adjacencyMatrixUpgma.toCanonicalTree();
        System.out.println("\n__UPGMA__");
        System.out.print(new TreePrinter(upgmaCanonical));

        AdjacencyMatrix adjacencyMatrixNj = new AdjacencyMatrix(njTree);
        Node njCanonical = adjacencyMatrixNj.toCanonicalTree();
        System.out.println("\n___NJ___");
        System.out.print(new TreePrinter(njCanonical));

        boolean compatibility = compareTrees(upgmaCanonical, njCanonical);
        System.out.println("\n Topological compatibility: " + compatibility);
    }


    private static boolean compareTrees(Node node1, Node node2){
        if (node1.getChildren().size() == node2.getChildren().size()) {
            if (node1.getChildren().size() == 0) {
                if(node1.isBranch() && node2.isBranch()) return true;
                else return node1.getLabel().equals(node2.getLabel());
            } else {
                List<Boolean> childrenComp = new ArrayList<>();
                for (Node aChild : node1.getChildren()) {
                    List<Boolean> childComp = new ArrayList<>();
                    for (Node bChild : node2.getChildren()) {
                        childComp.add(compareTrees(aChild, bChild));
                    }
                    childrenComp.add(childComp.stream().reduce((a, b) -> a || b).get());
                }
                return childrenComp.stream().reduce((a, b) -> a && b).get();
            }
        } else {
            return false;
        }
    }

}
