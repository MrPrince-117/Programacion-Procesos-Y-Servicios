package UD_4.Ejercicio_7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.nio.charset.StandardCharsets;


public class Server extends JFrame {
    JTextField textField;
    JTextArea textArea;
    JButton sendButton, exitButton;
    MulticastSocket multicastSocket;
    InetAddress group;
    static final int PORT = 12345;
    static final String GROUP_ADDRESS = "230.0.0.0";

    public Server() {
        // Configuración de la ventana principal
        setTitle("Servidor Multicast");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Campo de texto para ingresar el mensaje
        textField = new JTextField();
        add(textField, BorderLayout.NORTH);

        // Área de texto para mostrar mensajes enviados
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Panel con botones
        JPanel buttonPanel = new JPanel();
        sendButton = new JButton("Enviar");
        exitButton = new JButton("Salir");
        buttonPanel.add(sendButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Configuración del socket multicast
        try {
            multicastSocket = new MulticastSocket();
            group = InetAddress.getByName(GROUP_ADDRESS);
        } catch (Exception e) {
            showError("Error al configurar el socket multicast: " + e.getMessage());
        }

        // Acción del botón "Enviar"
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = textField.getText();
                if (!message.isEmpty()) {
                    sendMessage(message);
                    textArea.append("Enviado: " + message + "\n");
                    textField.setText("");
                }
            }
        });

        // Acción del botón "Salir"
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (multicastSocket != null) multicastSocket.close();
                } catch (Exception ex) {
                    showError("Error al cerrar el socket: " + ex.getMessage());
                }
                System.exit(0);
            }
        });

        setVisible(true);
    }

    private void sendMessage(String message) {
        try {
            byte[] buffer = message.getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);
            multicastSocket.send(packet);
        } catch (Exception e) {
            showError("Error al enviar el mensaje: " + e.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Server::new);
    }

}
