import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Map;

public class UPGMA {

    private DistanceMatrix distances;
    private ArrayList<Cluster> clusters;

    public UPGMA(DistanceMatrix initialDistances, ArrayList<Cluster> clusters) {
        this.distances = initialDistances;
        this.clusters = clusters;
    }


    public void run() {
        while (clusters.size() > 1) {
            Pair<Cluster, Cluster> smallest = distances.getNodesWithSmallestDistance();

            Cluster cluster1 = smallest.getKey();
            Cluster cluster2 = smallest.getValue();

            Cluster newCluster = updateClusters(distances, cluster1, cluster2);
            distances = updateDinstances(distances, newCluster, cluster1, cluster2);

        }

        Node root = clusters.get(0).getRoot();
        TreePrinter printer = new TreePrinter(root);
        System.out.println("--------------------------------------------------------------------");
        System.out.print(printer.toString());
    }

    private Cluster updateClusters(DistanceMatrix oldDistances, Cluster cluster1, Cluster cluster2){
        Node node1 = cluster1.getRoot();
        Node node2 = cluster2.getRoot();
        double[] distances = getDistancesToNode(oldDistances, cluster1, cluster2);
        node1.setDistanceToParent(distances[0]);
        node2.setDistanceToParent(distances[1]);


        Node newNode = new Node();
        newNode.addChild(node1);
        newNode.addChild(node2);
        Cluster newCluster = new Cluster(cluster1, cluster2, newNode);

        clusters.remove(cluster1);
        clusters.remove(cluster2);
        clusters.add(newCluster);

        return newCluster;
    }

    private DistanceMatrix updateDinstances(DistanceMatrix oldDistances, Cluster newCluster, Cluster cluster1, Cluster cluster2){
        Map<Pair<Cluster, Cluster>, Double> matrix = oldDistances.getMatrix();

        DistanceMatrix newDistances = new DistanceMatrix();

        for (Map.Entry<Pair<Cluster,Cluster>, Double> entry : matrix.entrySet()) {
            Cluster x = entry.getKey().getKey();
            Cluster y = entry.getKey().getValue();

            if(x.equals(cluster1) || x.equals(cluster2)) continue;
            if(y.equals(cluster1) || y.equals(cluster2)){
                // UPDATE
                System.out.println("distance update between: " + newCluster + " - " + x);
                Double dis1 = matrix.get(new Pair<>(cluster1, x));
                System.out.println(dis1 + " " + cluster1 + " - " + x);
                Double dis2 = matrix.get(new Pair<>(cluster2, x));
                System.out.println(dis2 + " " + cluster2 + " - " + x);

                double cluster1Size = cluster1.getMembers().size();
                double cluster2Size = cluster2.getMembers().size();

                double newDistance = (dis1 * cluster1Size + dis2 * cluster2Size) / (cluster1Size + cluster2Size);
                newDistances.add(newCluster, x, newDistance);
            }else {
                // REWRITE
                newDistances.add(entry.getKey().getKey(), entry.getKey().getValue(), entry.getValue());
                System.out.println(entry.getKey() + "  REWRITE " + entry.getValue());
            }
        }


        return newDistances;
    }


    private double[] getDistancesToNode(DistanceMatrix distances, Cluster cluster1, Cluster cluster2){
        double distance = distances.getMatrix().get(new Pair<>(cluster1, cluster2));
        Node root1 = cluster1.getRoot();
        Node root2 = cluster2.getRoot();


        return new double[]{
                distance / 2.0 - calculateTreeSum(root1),
                distance / 2.0 - calculateTreeSum(root2)
        };

    }


    private double calculateTreeSum(Node node){
        double sum = 0.0;
        Node child = node.getChild1();
        while (child != null) {
            sum += child.getDistanceToParent();
            child = child.getChild1();
        }
        return sum;
    }


}
