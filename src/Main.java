

public class Main {

    public static void main(String[] args) {
        MatrixReader matrixReader = new MatrixReader("nj.txt");

        UPGMA upgma = new UPGMA(matrixReader.getDistanceMatrix(),matrixReader.getClusters());
        upgma.run();

        MatrixReader matrixReader2 = new MatrixReader("nj.txt");
        NJ nj = new NJ(matrixReader2.getDistanceMatrix(),matrixReader2.getClusters());
        nj.run();
    }


}
