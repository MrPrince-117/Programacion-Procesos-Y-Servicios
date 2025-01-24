package UD_2;

public class ControlHilos extends Thread {
    private boolean ejecutar = true;
    private int iteraciones = 0;

    @Override
    public void run() {
        while (ejecutar) {
            System.out.println(Thread.currentThread().getName() + " - Iteración: " + iteraciones);
            iteraciones++;

            // Cambiar ejecutar a false después de 5 iteraciones
            if (iteraciones >= 5) {
                ejecutar = false;
            }

            // Pausar el hilo para simular trabajo
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.err.println(Thread.currentThread().getName() + " fue interrumpido.");
            }
        }
        System.out.println(Thread.currentThread().getName() + " ha terminado.");
    }

    public static void main(String[] args) {
        // Crear dos hilos de la clase ControlHilos
        ControlHilos hilo1 = new ControlHilos();
        ControlHilos hilo2 = new ControlHilos();

        // Iniciar los hilos
        System.out.println("Iniciando los hilos...");
        hilo1.start();
        hilo2.start();

        // Verificar que los hilos se han iniciado
        System.out.println("Hilo 1 está vivo: " + hilo1.isAlive());
        System.out.println("Hilo 2 está vivo: " + hilo2.isAlive());

        // Esperar a que ambos hilos terminen
        try {
            hilo1.join();
            hilo2.join();
        } catch (InterruptedException e) {
            System.err.println("El hilo principal fue interrumpido.");
        }

        // Verificar que ambos hilos han terminado
        System.out.println("Hilo 1 está vivo después de terminar: " + hilo1.isAlive());
        System.out.println("Hilo 2 está vivo después de terminar: " + hilo2.isAlive());
        System.out.println("Todos los hilos han finalizado.");
    }
}

