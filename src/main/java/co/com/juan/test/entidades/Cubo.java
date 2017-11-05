package co.com.juan.test.entidades;

public class Cubo {

    private int dimension;

    private int operacionesCubo;

    private int[][][] cubo;

    public Cubo() {
    }

    public Cubo(int dimension) {
        this.dimension = dimension;
        this.cubo = new int[dimension][dimension][dimension];
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public int getOperacionesCubo() {
        return operacionesCubo;
    }

    public void setOperacionesCubo(int operacionesCubo) {
        this.operacionesCubo = operacionesCubo;
    }

    public int[][][] getCubo() {
        return cubo;
    }

    public void setCubo(int[][][] cubo) {
        this.cubo = cubo;
    }

    public void setValorEnCubo(int x,int y,int z, int valor){
        cubo[x][y][z]=valor;
    }

    public int getValorEnCubo(int x,int y,int z){
        return cubo[x][y][z];
    }
}
