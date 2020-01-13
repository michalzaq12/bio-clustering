import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Map;

public class NJ {

    private final DistanceMatrix distances;
    private final int size;
    private ArrayList<Cluster> clusters;

    public NJ(DistanceMatrix initialDistances, ArrayList<Cluster> clusters) {
        this.size = initialDistances.size();
        this.distances = initialDistances;
        this.clusters = clusters;
    }

    public void run() {

        int n = 3;
        while (n > 2) {
            DistanceMatrix oldDistances = distances;

            DistanceMatrix qValues = this.calculateQValues(distances);

            Pair<Cluster, Cluster> smallestDistance = qValues.getNodesWithSmallestDistance(true);

            //TODO
            //clusters = updateClusters(oldDistances, clusters, f, g);

            n--;
            //TODO
            //updateDistances(n, oldDistances, f, g);
        }
        
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
            System.out.println(clusters.getKey() + " " + clusters.getValue() + " distance:" + distance + " sum1:" + sum1 + " sum2:" + sum2 + "  q=" + q);
            qValues.add(clusters.getKey(), clusters.getValue(), q);
        }

        return qValues;
    }

}
