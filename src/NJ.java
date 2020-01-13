import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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

        int n = 5;
        while (n > 2) {
            DistanceMatrix oldDistances = distances;

            DistanceMatrix qValues = this.calculateQValues(distances);

            Pair<Cluster, Cluster> smallestDistance = qValues.getNodesWithSmallestDistance(true);

            System.out.println("SMALLEST: " + smallestDistance.getKey() + " " + smallestDistance.getValue());
            //TODO
            Cluster newCluster = updateClusters(oldDistances, smallestDistance.getKey(), smallestDistance.getValue());

            n--;
            //TODO
            updateDistances(oldDistances, newCluster, smallestDistance.getKey(), smallestDistance.getValue() );

            System.out.println("#-------------------------------------------------------------------------------------------");
        }


        Node node = clusters.get(1).getRoot();
        //node.setDistanceToParent(distances.getData()[0][1]);//only one distance is left
        clusters.get(0).getRoot().addChild(node);


        TreePrinter printer = new TreePrinter(clusters.get(0).getRoot());
        System.out.print(printer.toString());

    }


    private DistanceMatrix calculateQValues(DistanceMatrix initialDistance) {
        DistanceMatrix qValues = new DistanceMatrix();
        System.out.println("size: " + initialDistance.size());

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
            System.out.println(clusters.getKey() + " " + clusters.getValue() + " distance:" + distance + " sum1:" + sum1 + " sum2:" + sum2 + "  q=" + q);
            qValues.add(clusters.getKey(), clusters.getValue(), q);
        }

        return qValues;
    }


    private Cluster updateClusters(DistanceMatrix oldDistances, Cluster cluster1, Cluster cluster2){
        //TODO set distance
//        Node fNode = clusters[f].getRootNode();
//        Node gNode = clusters[g].getRootNode();
//        double[] distances = distancesToNewNode(oldDistances, f, g);
//        fNode.setDistanceToParent(distances[0]);
//        gNode.setDistanceToParent(distances[1]);

        Node node = new Node();
        node.addChild(cluster1.getRoot());
        node.addChild(cluster2.getRoot());

        Cluster newCluster = new Cluster(cluster1, cluster2, node);

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
                System.out.println("add without change " + x + " = " + y);
                Double distance = matrix.get(new Pair<>(x, y));
                newDistances.add(x, y, distance);
            }else if ((!x.equals(cluster1) && !x.equals(cluster2)) ||
                    (!y.equals(cluster1) && !y.equals(cluster2))) {
                Cluster v = (x.equals(cluster1) || x.equals(cluster2)) ? y : x;
                System.out.println("ELSE (override)" + entry.getKey() + " = " + entry.getValue() + " = " + v);
                Double dis1 = matrix.get(new Pair<>(cluster1, v));
                System.out.println(dis1);
                Double dis2 = matrix.get(new Pair<>(cluster2, v));
                System.out.println(dis2);
                Double pairDis = matrix.get(new Pair<>(cluster1, cluster2));
                System.out.println("PairDis: " + pairDis);
                Double newDistance = (dis1 + dis2 - pairDis) / 2.0;
                System.out.println("newDistance " + newDistance);
                newDistances.add(newCluster, v, newDistance);
            }


        }

        newDistances.add(newCluster, newCluster, 0.0);

//        System.out.println(newDistances.getMatrix());
//        System.out.println(newDistances.size());
//
//        for (Map.Entry<Pair<Cluster,Cluster>, Double> entry : newDistances.getMatrix().entrySet()) {
//            Cluster x = entry.getKey().getKey();
//            Cluster y = entry.getKey().getValue();
//            System.out.println(x + " " + y + " = " + entry.getValue());
//        }


        this.distances = newDistances;
    }


}
