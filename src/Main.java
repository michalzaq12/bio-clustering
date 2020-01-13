import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        upgma();
    }



    public static void printTree(Node node){
        if(node == null) return;
        TreePrinter printer = new TreePrinter(node);
        System.out.print(printer);
    }

    public static void upgma(){
        DistanceMatrix distanceMatrix = new DistanceMatrix();
        ArrayList<Cluster> clusters = new ArrayList<>();


        File file = new File("nj.txt");

        try{
            BufferedReader br = new BufferedReader(new FileReader(file));


            ArrayList<Cluster> clustersTemp = new ArrayList<>();
            String speciesLine = br.readLine();

            int v = 0;
            for(String specie: speciesLine.split(",")){
                Node node = new Node(specie);
                Cluster cluster = new Cluster(node);
                clustersTemp.add(v, cluster);
                clusters.add(cluster);
                v++;
            }

            String st;
            int y = 0;
            while ((st = br.readLine()) != null){
                int x = 0;
                System.out.println(st);
                for(String distance: st.split(",")){
                    distanceMatrix.add(clustersTemp.get(x), clustersTemp.get(y), Double.valueOf(distance));
                    x++;
                }
                y++;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


        NJ nj = new NJ(distanceMatrix, clusters);
        nj.run();

//        while (clusters.size() > 1){
//            Pair<Cluster, Cluster> smallest = distanceMatrix.getNodesWithSmallestDistance();
//
//            Cluster cluster1 = smallest.getKey();
//            Cluster cluster2 = smallest.getValue();
//
//            System.out.println(cluster1 + " " + cluster2);
//
//            Node branch1 = new Node();
//            branch1.addChild(cluster1.getRoot());
//            branch1.addChild(cluster2.getRoot());
//            Cluster newCluster = new Cluster(cluster1, cluster2, branch1);
//            distanceMatrix.update(cluster1, cluster2, newCluster);
//            clusters.remove(cluster1);
//            clusters.remove(cluster2);
//            clusters.add(newCluster);
//        }
//
//        System.out.println("end");
//
//        printTree(clusters.get(0).getRoot());
    }
}
