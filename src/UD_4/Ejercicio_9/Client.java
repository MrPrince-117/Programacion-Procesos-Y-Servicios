package UD_4.Ejercicio_9;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client implements Serializable {
    public static void main(String[] args) {
        String nombre;
        int edad;
        Scanner sc = new Scanner(System.in);

        try (DatagramSocket clientSocket = new DatagramSocket()) {
            /*
            System.out.println("Escribe tu nombre:");
            nombre = sc.nextLine();
            System.out.println("Escribe tu edad:");
            edad = sc.nextInt();

            // Crear el objeto Persona
            Persona persona = new Persona(nombre, edad);
             */

            Persona persona = new Persona("German", 20);

            // Conversión del objeto a bytes
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(persona); // Aquí se convierte el objeto a bytes
            objectOutputStream.flush(); // Asegúrate de vaciar el flujo antes de cerrarlo
            objectOutputStream.close();

            // Obtener los bytes del objeto serializado
            byte[] bufferSend = byteArrayOutputStream.toByteArray();

            // Envío del datagrama
            InetAddress address = InetAddress.getByName("localhost");
            DatagramPacket datagramSend = new DatagramPacket(bufferSend, bufferSend.length, address, 12345);
            clientSocket.send(datagramSend);

            System.out.println("Cliente envió: " + persona);

            // Recepción del datagrama modificado
            byte[] bufferReceive = new byte[1024];
            DatagramPacket datagramReceive = new DatagramPacket(bufferReceive, bufferReceive.length);
            clientSocket.receive(datagramReceive);

            // Conversión de bytes al objeto modificado
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bufferReceive);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Persona personaModified = (Persona) objectInputStream.readObject();
            objectInputStream.close();

            System.out.println("Cliente recibió: " + personaModified);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
