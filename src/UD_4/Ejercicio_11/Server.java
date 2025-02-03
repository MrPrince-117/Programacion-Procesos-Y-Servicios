package UD_4.Ejercicio_11;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class Server {
    static Curso curso1 = new Curso("1234", "primero");
    static Curso curso2 = new Curso("5678", "segundo");

    static Alumno alumno1 = new Alumno("1111", "German", curso2, 8);
    static Alumno alumno2 = new Alumno("2222", "Daniel", curso1, 9);
    static Alumno alumno3 = new Alumno("3333", "Francesco", curso2, 10);
    static Alumno alumno4 = new Alumno("4444", "Pepe", curso1, 6);
    static Alumno alumno5 = new Alumno("5555", "Manuel", curso2, 7);

    static Alumno[] alumnos = {alumno1,alumno2,alumno3,alumno4, alumno5};

    public static Alumno searchAlumno(String idAlumno) {
        for (Alumno alumno : alumnos) {
            if (alumno.getIdAlumno().equals(idAlumno)) {
                return alumno;
            }
        }
        System.out.println("No existe el alumno con id: " + idAlumno);
        //En caso de no coicidir
        return null;
    }

    public static void main(String[] args) {
        String idAlumno;
        try (DatagramSocket socket = new DatagramSocket()) {
            System.out.println("Servidor iniciado. Esperando conexiones...");
            byte[] bufferIDalumno = new byte[1024];
            while (true) {
                //Recivir el id del alumno que busca el cliente
                DatagramPacket datagramReceived;
                try {
                    datagramReceived = new DatagramPacket(bufferIDalumno, bufferIDalumno.length);
                    socket.receive(datagramReceived);
                    idAlumno = new String(datagramReceived.getData(), 0, datagramReceived.getLength(), StandardCharsets.UTF_8);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                //Se cierra el programa en caso de *
                if (idAlumno == "*") {
                    System.out.println("Terminando conexiones...");
                    break;
                }

                System.out.println("ID recibido: " + idAlumno);
                Alumno searchedAlumno = searchAlumno(idAlumno);

                //Convertimos el objeto a bytes
                byte[] bufferSend;
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream ous = new ObjectOutputStream(baos);
                    ous.writeObject(searchedAlumno);
                    ous.close();
                    bufferSend = baos.toByteArray();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                //Respuesta de la busqueda del alumno
                try {

                    InetAddress address = datagramReceived.getAddress();
                    int portClient = datagramReceived.getPort();
                    DatagramPacket datagramSend = new DatagramPacket(bufferSend, bufferSend.length, address, portClient);
                    socket.send(datagramSend);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Servidor envio la repuesta");
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }
}
