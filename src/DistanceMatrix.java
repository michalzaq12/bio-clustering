import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class DistanceMatrix {

    private Map<Pair<Cluster, Cluster>, Double> matrix = new HashMap<>();

    public void add(Cluster node1, Cluster node2, Double distance){
        matrix.put(new Pair<>(node1, node2), distance);
        matrix.put(new Pair<>(node2, node1), distance);
    }


    public Pair<Cluster, Cluster> getNodesWithSmallestDistance(){
        return this.getNodesWithSmallestDistance(false);
    }

    public Pair<Cluster, Cluster> getNodesWithSmallestDistance(boolean allowNegative){
        Pair<Cluster, Cluster> out = null;
        Double val = Double.MAX_VALUE;

        for (Map.Entry<Pair<Cluster,Cluster>, Double> entry : matrix.entrySet()) {
            if(entry.getValue() < val && (allowNegative || entry.getValue() > 0)) {
                val = entry.getValue();
                out = entry.getKey();
            }
        }

        System.out.println("SMALLEST: " + out + " val: " + val);

        return out;
    }

    public Map<Pair<Cluster, Cluster>, Double> getMatrix() {
        return matrix;
    }

    public int size(){
        return (int) Math.sqrt(this.matrix.size());
    }

    public DistanceMatrix clone(){
        DistanceMatrix newDistanceMatrix = new DistanceMatrix();
        for (Map.Entry<Pair<Cluster,Cluster>, Double> entry : matrix.entrySet()) {
            Pair<Cluster, Cluster> key = entry.getKey();
            newDistanceMatrix.add(key.getKey(), key.getValue(), entry.getValue());
        }
        return newDistanceMatrix;
    }

}
