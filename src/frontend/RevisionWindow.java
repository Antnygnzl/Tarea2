package frontend;

import backend.Item;
import backend.PruebaController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RevisionWindow extends JFrame {

    private PruebaController controller;
    private JLabel lblEnunciado;
    private JLabel lblResultado;
    private JPanel opcionesPanel;
    private JButton btnAnterior, btnSiguiente, btnResumen;

    public RevisionWindow(PruebaController controller) {
        super("Revisión de Respuestas");
        this.controller = controller;
        initComponents();
        mostrarItemActual();
    }

    private void initComponents() {
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        lblEnunciado = new JLabel("", SwingConstants.CENTER);
        lblEnunciado.setFont(new Font("Arial", Font.BOLD, 16));

        lblResultado = new JLabel("", SwingConstants.CENTER);
        lblResultado.setFont(new Font("Arial", Font.PLAIN, 14));

        opcionesPanel = new JPanel();
        opcionesPanel.setLayout(new BoxLayout(opcionesPanel, BoxLayout.Y_AXIS));

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.add(lblResultado, BorderLayout.NORTH);
        panelCentro.add(opcionesPanel, BorderLayout.CENTER);

        btnAnterior = new JButton("Anterior");
        btnSiguiente = new JButton("Siguiente");
        btnResumen = new JButton("Ver resumen");

        btnAnterior.addActionListener(e -> {
            controller.anteriorItem();
            mostrarItemActual();
        });

        btnSiguiente.addActionListener(e -> {
            controller.siguienteItem();
            mostrarItemActual();
        });

        btnResumen.addActionListener(e -> {
            this.dispose();
            new ResumenWindow(controller);
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAnterior);
        panelBotones.add(btnSiguiente);
        panelBotones.add(btnResumen);

        add(lblEnunciado, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void mostrarItemActual() {
        Item item = controller.getItemActual();
        int index = controller.getIndiceActual();
        String respuestaUsuario = controller.getRespuesta(index);
        String respuestaCorrecta = item.getRespuestaCorrecta();

        lblEnunciado.setText("<html><body style='text-align: center;'>" + item.getEnunciado() + "</body></html>");
        opcionesPanel.removeAll();

        for (String opcion : item.getOpciones()) {
            if (opcion == null || opcion.isEmpty()) continue;
            JLabel lbl = new JLabel(opcion);
            if (opcion.equals(respuestaCorrecta)) {
                lbl.setForeground(Color.GREEN.darker());
                lbl.setText("✔ " + lbl.getText() + " (Correcta)");
            } else if (opcion.equals(respuestaUsuario)) {
                lbl.setForeground(Color.RED);
                lbl.setText("✘ " + lbl.getText() + " (Tu respuesta)");
            }
            opcionesPanel.add(lbl);
        }

        boolean correcta = respuestaUsuario != null && respuestaUsuario.equals(respuestaCorrecta);
        lblResultado.setText("Resultado: " + (correcta ? "✅ Correcto" : "❌ Incorrecto"));

        btnAnterior.setEnabled(!controller.estaEnPrimeraPregunta());
        btnSiguiente.setEnabled(!controller.estaEnUltimaPregunta());
    }
}
