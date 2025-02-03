package UD_4.Ejercicio_11;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String idAlumno;

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName("localhost");
            //int serverPort = socket.getLocalPort();
            int serverPort = 12345; //segunda opcion en caso de error

            System.out.println("Conexion al servidor");

            while (true) {
                System.out.println("Ingrese el ID del alumno que desea buscar");
                idAlumno = sc.nextLine();

                //Enviar id del alumno que se desea buscar
                byte[] bufferSend = idAlumno.getBytes();
                DatagramPacket datagramSend = new DatagramPacket(bufferSend, bufferSend.length, serverAddress, serverPort);
                socket.send(datagramSend);

                if (idAlumno.equals("*")) {
                    System.out.println("Cerrando el programa");
                    break;
                }
                
                //Recibir respuesta del servidor
                byte[] bufferReceive = new byte[4096];
                DatagramPacket datagramReceive = new DatagramPacket(bufferReceive, bufferReceive.length);
                socket.receive(datagramReceive);
                
                //Convertir los bytes a objeto
                Alumno alumno = null;
                try (ByteArrayInputStream bais = new ByteArrayInputStream(bufferReceive)) {
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    alumno = (Alumno) ois.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                System.out.println("Datos del alumno " +
                        "\nID: " + alumno.getIdAlumno() +
                        "\nNombre: " + alumno.getNombre() +
                        "\n" + alumno.getCurso() +
                        "\nNota: " + alumno.getNota());
            }

        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
