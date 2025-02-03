package UD_4.Avivi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteTCP {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 1234;

        try (Socket socket = new Socket(host, port);
             PrintWriter outPrinter = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader inReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Introduce un número secreto: ");
            int miNumero = scanner.nextInt();

            boolean acierto = false;

            while (!acierto) {
                System.out.print("Introduce tu número para adivinar: ");
                String numeroCliente = scanner.next();
                outPrinter.println(numeroCliente);

                String respuesta = inReader.readLine();
                System.out.println("Respuesta del servidor: " + respuesta);

                if (respuesta.equals("GANASTE")) {
                    acierto = true;
                } else {
                    System.out.print("¿El número que el servidor pensó es mayor (1), menor (2) o igual (3)? ");
                    int estado = scanner.nextInt();
                    outPrinter.println(estado);

                    if (estado == 3) {
                        System.out.println("¡Has perdido! El servidor adivinó tu número.");
                        acierto = true;
                    }
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Error: Host desconocido.");
        } catch (IOException e) {
            System.err.println("Error de comunicación con el servidor.");
        }
    }
}

