import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Map;

public class NJ {

    private DistanceMatrix distances;
    private final int size;
    private ArrayList<Cluster> clusters;

    public NJ(DistanceMatrix initialDistances, ArrayList<Cluster> clusters) {
        this.size = initialDistances.size();
        this.distances = initialDistances;
        this.clusters = clusters;
    }

    public void run() {

        int n = this.size;
        while (n > 2) {
            DistanceMatrix oldDistances = distances;

            DistanceMatrix qValues = this.calculateQValues(distances);

            Pair<Cluster, Cluster> smallestDistance = qValues.getNodesWithSmallestDistance(true);

            Cluster cluster1 = smallestDistance.getKey();
            Cluster cluster2 = smallestDistance.getValue();

            Cluster newCluster = updateClusters(oldDistances, cluster1, cluster2);
            updateDistances(oldDistances, newCluster, cluster1, cluster2);

            n--;
            System.out.println("#-------------------------------------------------------------------------------------------");
        }

        System.out.println(this.clusters);
        Node subTree = clusters.get(1).getRoot();
        double lastNodeDistance = distances.getMatrix().get(new Pair<>(clusters.get(0), clusters.get(1)));
        Node root = clusters.get(0).getRoot();
        root.setDistanceToParent(lastNodeDistance);
        root.addChild(subTree);


        TreePrinter printer = new TreePrinter(root);
        System.out.print(printer.toString());

    }


    private DistanceMatrix calculateQValues(DistanceMatrix initialDistance) {
        DistanceMatrix qValues = new DistanceMatrix();

        for (Map.Entry<Pair<Cluster,Cluster>, Double> entry : initialDistance.getMatrix().entrySet()) {
            Pair<Cluster,Cluster> clusters = entry.getKey();
            if(clusters.getKey().equals(clusters.getValue())) continue;

            double distance = entry.getValue();
            double sum1 = 0.0;
            double sum2 = 0.0;
            for (Map.Entry<Pair<Cluster,Cluster>, Double> entry2 : initialDistance.getMatrix().entrySet()) {
                Pair<Cluster,Cluster> clusters2 = entry2.getKey();

                if(clusters.getKey().equals(clusters2.getKey())) sum1+= entry2.getValue();
                if(clusters.getValue().equals(clusters2.getKey())) sum2+= entry2.getValue();
            }
            double q = (initialDistance.size() - 2) * distance - sum1 - sum2;
            qValues.add(clusters.getKey(), clusters.getValue(), q);
        }

        return qValues;
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


    private void updateDistances(DistanceMatrix oldDistances, Cluster newCluster, Cluster cluster1, Cluster cluster2){
        Map<Pair<Cluster, Cluster>, Double> matrix = oldDistances.getMatrix();

        DistanceMatrix newDistances = new DistanceMatrix();

        for (Map.Entry<Pair<Cluster,Cluster>, Double> entry : matrix.entrySet()) {
            Pair<Cluster,Cluster> clusters = entry.getKey();
            Cluster x = clusters.getKey();
            Cluster y = clusters.getValue();

            if(!x.equals(cluster1) && !x.equals(cluster2) &&
                    !y.equals(cluster1) && !y.equals(cluster2) ){
                // REWRITE
                Double distance = matrix.get(new Pair<>(x, y));
                newDistances.add(x, y, distance);
            }else if ((!x.equals(cluster1) && !x.equals(cluster2)) ||
                    (!y.equals(cluster1) && !y.equals(cluster2))) {
                Cluster v = (x.equals(cluster1) || x.equals(cluster2)) ? y : x;
                // UPDATE
                Double dis1 = matrix.get(new Pair<>(cluster1, v));
                Double dis2 = matrix.get(new Pair<>(cluster2, v));
                Double pairDis = matrix.get(new Pair<>(cluster1, cluster2));
                Double newDistance = (dis1 + dis2 - pairDis) / 2.0;
                newDistances.add(newCluster, v, newDistance);
            }


        }
        newDistances.add(newCluster, newCluster, 0.0);
        this.distances = newDistances;
    }



    private double[] getDistancesToNode(DistanceMatrix distances, Cluster cluster1, Cluster cluster2){
        double distance = distances.getMatrix().get(new Pair<>(cluster1, cluster2));
        double sum1 = 0.0;
        double sum2 = 0.0;
        for (Map.Entry<Pair<Cluster,Cluster>, Double> entry : distances.getMatrix().entrySet()) {
            Cluster x = entry.getKey().getKey();
            if(x.equals(cluster1)) sum1+= entry.getValue();
            if(x.equals(cluster2)) sum2+= entry.getValue();
        }
        double node1Dis = ((1.0d / 2) * distance) + (1.0d / (2.0d * (distances.size() - 2))) * (sum1 - sum2);
        double node2Dis = distance - node1Dis;

        return new double[]{node1Dis, node2Dis};
    }


}
