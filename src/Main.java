import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

public class Main {

    public static void main(String[] args) {
        MatrixReader matrixReader = new MatrixReader("test.txt");

        UPGMA upgma = new UPGMA(matrixReader.getDistanceMatrix(), matrixReader.getClusters());
        Node upgmaTree = upgma.run();
        TreePrinter upgmaPrinter = new TreePrinter(upgmaTree);
        System.out.print(upgmaPrinter);

        NJ nj = new NJ(matrixReader.getDistanceMatrix(), matrixReader.getClusters());
        Node njTree = nj.run();
        TreePrinter njPrinter = new TreePrinter(njTree);
        System.out.print(njPrinter);



        // ---------------------


        AdjacencyMatrix adjacencyMatrixUpgma = new AdjacencyMatrix(upgmaTree);
        Node upgmaCanonical = adjacencyMatrixUpgma.toCanonicalTree();
        System.out.print(new TreePrinter(upgmaCanonical));

        AdjacencyMatrix adjacencyMatrixNj = new AdjacencyMatrix(njTree);
        Node njCanonical = adjacencyMatrixNj.toCanonicalTree();
        System.out.print(new TreePrinter(njCanonical));

        boolean areEqual = compareTrees(upgmaCanonical, njCanonical);
        System.out.printf("Results are%s topologically equal", areEqual ? "" : " not");
    }


    private static boolean compareTrees(Node aNode, Node bNode){
        if (aNode.getChildren().size() == bNode.getChildren().size()) {
            if (aNode.getChildren().size() == 0 && bNode.getChildren().size() == 0) {
                if(aNode.isBranch() && bNode.isBranch()){
                    return true;
                }else if (aNode.getLabel().equals(bNode.getLabel())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                List<Boolean> comparisons = new ArrayList<>();
                for (Node aChild : aNode.getChildren()) {
                    List<Boolean> comparisons2 = new ArrayList<>();
                    for (Node bChild : bNode.getChildren()) {
                        boolean equals = compareTrees(aChild, bChild);
                        comparisons2.add(equals);
                    }
                    comparisons.add(comparisons2.stream().reduce(or()).get());
                }
                Boolean result = comparisons.stream().reduce(and()).get();
                return result;
            }
        } else {
            return false;
        }
    }

    private static BinaryOperator<Boolean> or() {
        return (a, b) -> a || b;
    }

    private static BinaryOperator<Boolean> and() {
        return (a, b) -> a && b;
    }

}
