import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;
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

        return out;
    }


    public void update(Cluster cluster1, Cluster cluster2, Cluster newCluster){
        System.out.println("update " + cluster1 + " " + cluster2);
        LinkedList<Pair<Cluster, Cluster>> keysToRemove = new LinkedList<>();
        HashMap<Pair<Cluster,Cluster>, Double> valuesToAdd = new HashMap<>();
        for (Map.Entry<Pair<Cluster,Cluster>, Double> entry : matrix.entrySet()) {
            Cluster x = entry.getKey().getValue();
            System.out.println(x);
            if(!x.equals(cluster1) && !x.equals(cluster2)){
                System.out.println("distance update");
                Double dis1 = matrix.get(new Pair<>(cluster1, x));
                System.out.println(dis1);
                Double dis2 = matrix.get(new Pair<>(cluster2, x));
                System.out.println(dis2);
                Double newDistance = (dis1 + dis2) / 2.0;
                valuesToAdd.put(new Pair<>(newCluster, x), newDistance);
                valuesToAdd.put(new Pair<>(x, newCluster), newDistance);
            }else {
                keysToRemove.add(entry.getKey());
                keysToRemove.add(new Pair<>(entry.getKey().getValue(), entry.getKey().getKey()));
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }
        }

        for(Pair<Cluster,Cluster> key: keysToRemove){
            matrix.remove(key);
        }

        this.matrix.putAll(valuesToAdd);
    }


    public Map<Pair<Cluster, Cluster>, Double> getMatrix() {
        return matrix;
    }

    public int size(){
        return (int) Math.sqrt(this.matrix.size());
    }

}
