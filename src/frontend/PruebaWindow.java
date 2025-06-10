package frontend;

import backend.PruebaController;
import backend.Item;

import java.util.Enumeration;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class PruebaWindow extends JFrame {

    private PruebaController controller;
    private JLabel lblEnunciado;
    private ButtonGroup opcionesGroup;
    private JPanel opcionesPanel;
    private JButton btnAnterior;
    private JButton btnSiguiente;

    public PruebaWindow(PruebaController controller) {
        super("Aplicación de la Prueba");
        this.controller = controller;
        initComponents();
        mostrarItemActual();
    }

    private void initComponents() {
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        lblEnunciado = new JLabel("Enunciado", SwingConstants.CENTER);
        lblEnunciado.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblEnunciado, BorderLayout.NORTH);

        opcionesPanel = new JPanel();
        opcionesPanel.setLayout(new BoxLayout(opcionesPanel, BoxLayout.Y_AXIS));
        add(opcionesPanel, BorderLayout.CENTER);

        JPanel botonesPanel = new JPanel();
        btnAnterior = new JButton("Anterior");
        btnSiguiente = new JButton("Siguiente");

        btnAnterior.addActionListener(e -> {
            guardarRespuestaSeleccionada();
            controller.anteriorItem();
            mostrarItemActual();
        });

        btnSiguiente.addActionListener(e -> {
            guardarRespuestaSeleccionada();
            if (controller.estaEnUltimaPregunta()) {
                this.dispose();
                new RevisionWindow(controller);
            } else {
                controller.siguienteItem();
                mostrarItemActual();
            }
        });

        botonesPanel.add(btnAnterior);
        botonesPanel.add(btnSiguiente);
        add(botonesPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void mostrarItemActual() {
        Item item = controller.getItemActual();
        lblEnunciado.setText("<html><body style='text-align: center;'>" + item.getEnunciado() + "</body></html>");
        opcionesPanel.removeAll();
        opcionesGroup = new ButtonGroup();

        List<String> opciones = item.getOpciones();
        String respuestaUsuario = controller.getRespuesta(controller.getIndiceActual());

        for (String opcion : opciones) {
            if (opcion == null || opcion.isEmpty()) continue;
            JRadioButton radio = new JRadioButton(opcion);
            if (opcion.equals(respuestaUsuario)) {
                radio.setSelected(true);
            }
            opcionesGroup.add(radio);
            opcionesPanel.add(radio);
        }

        opcionesPanel.revalidate();
        opcionesPanel.repaint();

        // Botón "Anterior" deshabilitado en la primera pregunta
        btnAnterior.setEnabled(!controller.estaEnPrimeraPregunta());

        // Cambiar el texto de "Siguiente" a "Enviar" si es la última pregunta
        if (controller.estaEnUltimaPregunta()) {
            btnSiguiente.setText("Enviar respuestas");
        } else {
            btnSiguiente.setText("Siguiente");
        }
    }

    private void guardarRespuestaSeleccionada() {
        for (Enumeration<AbstractButton> buttons = opcionesGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                controller.guardarRespuesta(button.getText());
                return;
            }
        }
        controller.guardarRespuesta(""); // Si no hay selección
    }
}
