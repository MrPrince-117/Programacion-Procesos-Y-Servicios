package UD_3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteConGUI {

    private static String nombreCliente;
    private static PrintWriter output;
    private static JTextArea textAreaChat;
    private static JTextField textFieldMensaje;

    public static void main(String[] args) {
        String host = JOptionPane.showInputDialog(null, "Ingresa la IP del servidor:", "Conexión al Servidor", JOptionPane.QUESTION_MESSAGE);
        if (host == null || host.trim().isEmpty()) {
            host = "localhost";
        }

        int puerto = 1234;
        nombreCliente = JOptionPane.showInputDialog(null, "Ingresa tu nombre:", "Nombre de Usuario", JOptionPane.QUESTION_MESSAGE);

        try {
            Socket socket = new Socket(host, puerto);
            output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Crear interfaz gráfica
            crearInterfazGrafica();

            // Hilo para escuchar mensajes del servidor
            new Thread(() -> {
                try {
                    String mensaje;
                    while ((mensaje = input.readLine()) != null) {
                        mostrarMensaje(mensaje);
                    }
                } catch (IOException e) {
                    mostrarMensaje("Error al recibir mensaje: " + e.getMessage());
                }
            }).start();

            // Enviar nombre al servidor
            output.println(nombreCliente);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse al servidor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private static void crearInterfazGrafica() {
        JFrame frame = new JFrame("Cliente de Chat - " + nombreCliente);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textAreaChat = new JTextArea();
        textAreaChat.setEditable(false);
        JScrollPane scrollPaneChat = new JScrollPane(textAreaChat);

        textFieldMensaje = new JTextField();
        JButton botonEnviar = new JButton("Enviar");

        botonEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarMensaje();
            }
        });

        textFieldMensaje.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarMensaje();
            }
        });

        JPanel panelEnvio = new JPanel(new BorderLayout());
        panelEnvio.add(textFieldMensaje, BorderLayout.CENTER);
        panelEnvio.add(botonEnviar, BorderLayout.EAST);

        frame.setLayout(new BorderLayout());
        frame.add(scrollPaneChat, BorderLayout.CENTER);
        frame.add(panelEnvio, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static void mostrarMensaje(String mensaje) {
        SwingUtilities.invokeLater(() -> textAreaChat.append(mensaje + "\n"));
    }

    private static void enviarMensaje() {
        String mensaje = textFieldMensaje.getText().trim();
        if (!mensaje.isEmpty()) {
            output.println(mensaje);
            textFieldMensaje.setText("");
        }
    }
}

