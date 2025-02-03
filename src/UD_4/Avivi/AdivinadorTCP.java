package UD_4.Avivi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class AdivinadorTCP extends Thread {
    private final Socket socketServicio;
    private final Random random;
    private PrintWriter outPrinter;
    private BufferedReader inReader;
    private int numeroOculto, numeroAnterior;
    private int estado;
    private boolean acierto = false;
    private final int rango = 10;

    private int mid(int a, int b) {
        return (a + b) / 2;
    }

    public AdivinadorTCP(Socket socketServicio) {
        this.socketServicio = socketServicio;
        random = new Random();
        numeroOculto = random.nextInt(rango) + 1;
        try {
            inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
            outPrinter = new PrintWriter(socketServicio.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("Error al obtener los flujos de entrada/salida.");
        }
    }

    @Override
    public void run() {
        try {
            while (!acierto) {
                // Recibimos el número del cliente
                String mensajeRecibido = inReader.readLine();
                int numeroCliente = Integer.parseInt(mensajeRecibido);

                if (numeroCliente < numeroOculto) {
                    outPrinter.println("Fallo. El número es mayor que " + numeroCliente);
                } else if (numeroCliente > numeroOculto) {
                    outPrinter.println("Fallo. El número es menor que " + numeroCliente);
                } else {
                    outPrinter.println("GANASTE");
                    acierto = true;
                }

                if (!acierto) {
                    preguntaCliente();
                }
            }
        } catch (IOException e) {
            System.err.println("Error durante la comunicación con el cliente.");
        } finally {
            try {
                socketServicio.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar el socket.");
            }
        }
    }

    private void preguntaCliente() {
        int numeroPensado = (int) Math.round(random.nextGaussian() * 2 + 5);
        if (estado == 1) {
            while (numeroPensado <= numeroAnterior) {
                numeroPensado = (int) Math.round(random.nextGaussian() * 1.23 + mid(numeroAnterior, rango));
            }
        } else if (estado == 2) {
            while (numeroPensado >= numeroAnterior) {
                numeroPensado = (int) Math.round(random.nextGaussian() * 1.23 + mid(numeroAnterior, 0));
            }
        }

        outPrinter.println(numeroPensado);

        try {
            String respuesta = inReader.readLine();
            estado = Integer.parseInt(respuesta);
            if (estado != 3) {
                numeroAnterior = numeroPensado;
            } else {
                acierto = true;
            }
        } catch (IOException e) {
            System.err.println("Error al leer la respuesta del cliente.");
        }
    }
}

