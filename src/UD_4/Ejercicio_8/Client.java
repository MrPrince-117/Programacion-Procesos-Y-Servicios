package UD_4.Ejercicio_8;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        String serverIP = "127.0.0.1"; // Dirección IP del servidor
        int serverPORT = 12345; // Puerto del servidor
        int number = 0;
        Scanner sc = new Scanner(System.in);

        try (Socket socket = new Socket(serverIP, serverPORT)) {
            System.out.println("Conectado al servidor: " + socket.getRemoteSocketAddress());
            /*
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Numbers clientNumber = (Numbers) objectInputStream.readObject();

             */
            while (true) {
                try  {
                    System.out.println("Introduce un numero entero");
                    number = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.err.println("El numero debe ser entero, no se permiten ni decimales ni caracteres");
                }
                if (number == 0) {
                    break;
                }

                //Enviar objeto al servidor
                Numbers clientNumber = new Numbers();
                clientNumber.setNumero(number);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

                objectOutputStream.writeObject(clientNumber);

                System.out.println("Número enviado: " + clientNumber.getNumero());

                //Recibir el objeto del servidor
                ObjectInputStream objectInputStream1 = new ObjectInputStream(socket.getInputStream());
                Numbers serverNumber = (Numbers) objectInputStream1.readObject();

                System.out.println("Numero: " + serverNumber.getNumero() +
                        "\nCuadrado: " + serverNumber.getCuadrado() +
                        "\nCubo: " + serverNumber.getCubo());

            }

        } catch (IOException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }//Fin try-catch
    }
}
