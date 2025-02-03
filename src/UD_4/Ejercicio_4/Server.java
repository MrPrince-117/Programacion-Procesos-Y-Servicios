package UD_4.Ejercicio_4;

/*
Escribe un programa servidor que pueda atender hasta 3 clientes. Debe enviar a
cada cliente un mensaje indicando el número de cliente que es. Este número será 1,
2 o 3. El cliente mostrará el mensaje recibido. Cambia el programa para que lo haga
con N clientes, siendo N un parámetro que tendrás que definir en el programa
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    public static void main(String[] args) {
        int port = 12345;
        int numClientes;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor iniciado. Esperando conexiones...");

            while (true) {
                try{
                    //Elige el numero de clientes que tendra el servidor
                    System.out.println("Indique el numero de clientes");
                    Scanner sc = new Scanner(System.in);
                    numClientes = sc.nextInt();
                    System.out.println("Se ha fijado un limite de " + numClientes + " clientes en el servidor");
                    break;
                }catch (InputMismatchException e){
                    System.err.println("Escriba un numero entero, evite los decimales y los caracteres de texto");;
                }//Fin try-catch
            }//Fin while

            //Aceptar n clientes
            for (int i = 1; i <= numClientes; i++) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente " + i + " conectado");

                //Envia el mensaje de conexion al cliente
                String message = "Bienvenido cliente " + i;
                try {
                    OutputStream outputStream = clientSocket.getOutputStream();
                    PrintWriter writer = new PrintWriter(outputStream, true);
                    writer.println(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }//Fin try-catch
            }//Fin for
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }//Fin try-catch
    }//Fin main
}//Fin clase Server
