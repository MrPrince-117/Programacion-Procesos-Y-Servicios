package UD_2;

class MiHilo extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getName() + " - Iteración " + i);
            try {
                Thread.sleep(500); // Pausa de 500ms para simular trabajo
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " fue interrumpido.");
            }
        }
        System.out.println(Thread.currentThread().getName() + " ha terminado.");
    }
}
