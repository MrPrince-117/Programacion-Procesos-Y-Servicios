package UD_3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;

public class ClienteApp {
    private JFrame frame;
    private JTextArea textArea;
    private JButton botonConectar, botonEnviar, botonJugar, botonEnviarArchivo, botonSalir;
    private Socket clientSocket;
    private boolean conectado = false;
    private boolean jugando = false;
    private Thread threadRecibir;

    public ClienteApp() {
        frame = new JFrame("Cliente TCP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Crear el área de texto con scroll
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(5, 1, 10, 10));

        botonConectar = new JButton("1. Conectar al servidor");
        botonConectar.addActionListener(e -> conectar());
        panelBotones.add(botonConectar);

        botonEnviar = new JButton("2. Enviar mensaje");
        botonEnviar.addActionListener(e -> enviarMensaje());
        botonEnviar.setEnabled(false);
        panelBotones.add(botonEnviar);

        botonJugar = new JButton("3. Jugar al 3 en raya");
        botonJugar.addActionListener(e -> jugarTresEnRaya());
        botonJugar.setEnabled(false);
        panelBotones.add(botonJugar);

        botonEnviarArchivo = new JButton("4. Enviar archivo");
        botonEnviarArchivo.addActionListener(e -> enviarArchivo());
        botonEnviarArchivo.setEnabled(false);
        panelBotones.add(botonEnviarArchivo);

        botonSalir = new JButton("5. Salir");
        botonSalir.addActionListener(e -> salir());
        panelBotones.add(botonSalir);

        frame.add(panelBotones, BorderLayout.EAST);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClienteApp());
    }

    private void conectar() {
        if (conectado) {
            mostrarMensaje("Ya estás conectado.");
            return;
        }

        String host = JOptionPane.showInputDialog("Ingrese la IP del servidor:");
        String portString = JOptionPane.showInputDialog("Ingrese el puerto del servidor:");
        int port = Integer.parseInt(portString);

        try {
            clientSocket = new Socket(host, port);
            conectado = true;
            agregarMensaje("Conectado al servidor.");

            // Habilitar botones
            botonEnviar.setEnabled(true);
            botonJugar.setEnabled(true);
            botonEnviarArchivo.setEnabled(true);

            // Iniciar hilo para recibir mensajes
            threadRecibir = new Thread(this::recibirMensajes);
            threadRecibir.start();
        } catch (IOException e) {
            agregarMensaje("Error al conectar: " + e.getMessage());
            conectado = false;
        }
    }

    private void recibirMensajes() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String mensaje;
            while (conectado) {
                mensaje = reader.readLine();
                if (mensaje != null) {
                    agregarMensaje("Servidor: " + mensaje);
                } else {
                    agregarMensaje("El servidor cerró la conexión.");
                    desconectar();
                    break;
                }
            }
        } catch (IOException e) {
            agregarMensaje("Error recibiendo mensajes: " + e.getMessage());
            desconectar();
        }
    }

    private void enviarMensaje() {
        if (!conectado) {
            JOptionPane.showMessageDialog(frame, "No estás conectado al servidor.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String mensaje = JOptionPane.showInputDialog("Escribe tu mensaje:");
        if (mensaje != null && !mensaje.isEmpty()) {
            try {
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                writer.println(mensaje);
                agregarMensaje("Tú: " + mensaje);
            } catch (IOException e) {
                agregarMensaje("Error al enviar mensaje: " + e.getMessage());
                desconectar();
            }
        }
    }

    private void enviarArchivo() {
        if (!conectado) {
            JOptionPane.showMessageDialog(frame, "No estás conectado al servidor.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(frame);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            try {
                byte[] contenido = Files.readAllBytes(archivo.toPath());

                // Enviar encabezado con el nombre del archivo
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                writer.println("ARCHIVO " + archivo.getName());

                OutputStream outputStream = clientSocket.getOutputStream();
                outputStream.write(contenido);
                outputStream.flush();

                agregarMensaje("Archivo '" + archivo.getName() + "' enviado.");
            } catch (IOException e) {
                agregarMensaje("Error al enviar archivo: " + e.getMessage());
            }
        }
    }

    private void agregarMensaje(String mensaje) {
        textArea.append(mensaje + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    private void jugarTresEnRaya() {
        // Implementación del juego tres en raya (anteriormente discutida)
        JOptionPane.showMessageDialog(frame, "Funcionalidad del juego tres en raya aún no implementada.");
    }

    private void desconectar() {
        conectado = false;
        try {
            if (clientSocket != null) {
                clientSocket.close();
            }
        } catch (IOException e) {
            agregarMensaje("Error al desconectar: " + e.getMessage());
        }
        clientSocket = null;
        botonEnviar.setEnabled(false);
        botonJugar.setEnabled(false);
        botonEnviarArchivo.setEnabled(false);
    }

    private void salir() {
        if (conectado) {
            desconectar();
        }
        frame.dispose();
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje);
    }
}
