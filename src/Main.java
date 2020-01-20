

public class Main {

    public static void main(String[] args) {
        MatrixReader matrixReader = new MatrixReader("distanceMatrix.txt");

        UPGMA upgma = new UPGMA(matrixReader.getDistanceMatrix(),matrixReader.getClusters());
        upgma.run();


        //        NJ nj = new NJ(distanceMatrix, clusters);
        //        nj.run();
    }


}
