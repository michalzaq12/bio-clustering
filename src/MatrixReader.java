import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;

public class MatrixReader {

    private String path;
    private DistanceMatrix distanceMatrix;
    private ArrayList<Cluster> clusters;

    public MatrixReader(String path){
        this.path = path;
        distanceMatrix = new DistanceMatrix();
        clusters = new ArrayList<>();
        this.read();
    }


    private void read(){
        try{
            File file = new File(this.path);
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
    }

    public DistanceMatrix getDistanceMatrix(){
        return distanceMatrix;
    }

    public ArrayList<Cluster> getClusters(){
        return clusters;
    }

}
