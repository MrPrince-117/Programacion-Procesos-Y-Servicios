package UD_4.Ejercicio_7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.nio.charset.StandardCharsets;



public class Client extends JFrame {
    JTextArea textArea;
    JButton exitButton;
    MulticastSocket multicastSocket;
    InetAddress group;
    static final int PORT = 12345;
    static final String GROUP_ADDRESS = "230.0.0.0";

    public Client() {
        // Configuración de la ventana principal
        setTitle("Cliente Multicast");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Área de texto para mostrar mensajes recibidos
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Botón para salir
        exitButton = new JButton("Salir");
        add(exitButton, BorderLayout.SOUTH);

        // Configuración del socket multicast
        try {
            multicastSocket = new MulticastSocket(PORT);
            group = InetAddress.getByName(GROUP_ADDRESS);
            multicastSocket.joinGroup(group);

            // Hilo para recibir mensajes
            Thread receiveThread = new Thread(this::receiveMessages);
            receiveThread.start();
        } catch (Exception e) {
            showError("Error al configurar el socket multicast: " + e.getMessage());
        }

        // Acción del botón "Salir"
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (multicastSocket != null) {
                        multicastSocket.leaveGroup(group);
                        multicastSocket.close();
                    }
                } catch (Exception ex) {
                    showError("Error al cerrar el socket: " + ex.getMessage());
                }
                System.exit(0);
            }
        });

        setVisible(true);
    }

    private void receiveMessages() {
        byte[] buffer = new byte[1024];
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                multicastSocket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);
                textArea.append("Recibido: " + message + "\n");
            } catch (Exception e) {
                showError("Error al recibir el mensaje: " + e.getMessage());
                break;
            }
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Client::new);
    }
}
