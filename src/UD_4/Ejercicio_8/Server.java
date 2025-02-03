package UD_4.Ejercicio_8;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void reciveObject() {

    }

    public static void main(String[] args) {
        int numero;
        long cuadrado;
        long cubo;
        int port = 12345;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor iniciado. Esperando conexiones...");
            Socket clientSocket = serverSocket.accept();

            while (true) {
                //Recibir objeto del cliente
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

                Numbers clientNumber = (Numbers) objectInputStream.readObject();
                System.out.println("Número recibido: " + clientNumber.getNumero());

                //Enviar objeto al cliente
                //Hacemos los calculos para obtener los datos a enviar
                numero = clientNumber.getNumero();
                cuadrado = numero*numero;
                cubo = cuadrado*numero;

                Numbers serverNumber = (Numbers) objectInputStream.readObject();

                //Introducimos los valores al objeto
                serverNumber.setNumero(numero);
                serverNumber.setCuadrado(cuadrado);
                serverNumber.setCubo(cubo);

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(serverSocket.accept().getOutputStream());

                objectOutputStream.writeObject(serverNumber);

            }

        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }//Fin try-catch
    }
}
