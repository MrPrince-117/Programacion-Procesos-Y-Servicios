package UD_4.Ejercicio_9;

/*
Usando sockets UDP realiza un programa servidor que espere un datagrama de un
cliente. El cliente le envía un objeto Persona que previamente había inicializado. El
servidor modifica los datos del objeto Persona y lo envía de vuelta al cliente.
Visualiza los datos del objeto Persona tanto en el programa cliente cuando los
envía y los recibe como en el programa servidor cuando los recibe y los envía
modificados
 */

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {

        try (DatagramSocket socket = new DatagramSocket(9876)) {
            System.out.println("Servidor iniciado, esperando conexiones...");

            // Recepción del datagrama
            byte[] bufferReceive = new byte[1024];
            DatagramPacket datagramReceived = new DatagramPacket(bufferReceive, bufferReceive.length);
            socket.receive(datagramReceived);

            // Conversión de bytes al objeto Persona
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(datagramReceived.getData());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Persona persona = (Persona) objectInputStream.readObject();
            objectInputStream.close();

            System.out.println("Servidor recibió: " + persona);

            // Modificación del objeto
            persona.setNombre(persona.getNombre().toUpperCase());
            persona.setEdad(persona.getEdad() + 1);
            persona.setModificado(true);

            System.out.println("Servidor modificó: " + persona);

            // Conversión del objeto modificado a bytes
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(persona);
            objectOutputStream.close();
            byte[] bufferSend = byteArrayOutputStream.toByteArray();

            // Envío del datagrama modificado
            InetAddress address = datagramReceived.getAddress();
            int portClient = datagramReceived.getPort();
            DatagramPacket datagramSend = new DatagramPacket(bufferSend, bufferSend.length, address, portClient);
            socket.send(datagramSend);

            System.out.println("Servidor envió: " + persona);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
