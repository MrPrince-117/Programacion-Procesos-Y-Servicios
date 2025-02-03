package Ejercicio_10;

public class Numbers {
    private int numero;
    private long cuadrado;
    private long cubo;

    public Numbers() {}//Constructor vacio

    public Numbers(int numero, long cuadrado, long cubo) {
        this.numero = numero;
        this.cuadrado = cuadrado;
        this.cubo = cubo;
    }//Constructor con parametros

    //Getters & Setters
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public long getCuadrado() {
        return cuadrado;
    }

    public void setCuadrado(long cuadrado) {
        this.cuadrado = cuadrado;
    }

    public long getCubo() {
        return cubo;
    }

    public void setCubo(long cubo) {
        this.cubo = cubo;
    }
}
