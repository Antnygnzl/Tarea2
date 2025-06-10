package frontend;

import backend.Item;
import backend.PruebaController;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ResumenWindow extends JFrame {

    private PruebaController controller;

    public ResumenWindow(PruebaController controller) {
        super("Resumen de Resultados");
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextArea resumenArea = new JTextArea();
        resumenArea.setEditable(false);
        resumenArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resumenArea);

        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(e -> System.exit(0));

        add(new JLabel("Resumen de Respuestas Correctas", SwingConstants.CENTER), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(btnSalir, BorderLayout.SOUTH);

        StringBuilder resumen = new StringBuilder();

        Map<String, Integer> totalPorNivel = new HashMap<>();
        Map<String, Integer> correctasPorNivel = new HashMap<>();

        Map<String, Integer> totalPorTipo = new HashMap<>();
        Map<String, Integer> correctasPorTipo = new HashMap<>();

        List<Item> items = controller.getItems();
        List<String> respuestas = controller.getRespuestasUsuario();

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            String tipo = item.getTipo();
            String nivel = item.getNivelBloom();
            String correcta = item.getRespuestaCorrecta();
            String usuario = respuestas.get(i);

            // Por nivel
            totalPorNivel.put(nivel, totalPorNivel.getOrDefault(nivel, 0) + 1);
            if (usuario != null && usuario.equals(correcta)) {
                correctasPorNivel.put(nivel, correctasPorNivel.getOrDefault(nivel, 0) + 1);
            }

            // Por tipo
            totalPorTipo.put(tipo, totalPorTipo.getOrDefault(tipo, 0) + 1);
            if (usuario != null && usuario.equals(correcta)) {
                correctasPorTipo.put(tipo, correctasPorTipo.getOrDefault(tipo, 0) + 1);
            }
        }

        resumen.append("=== Porcentaje de respuestas correctas por Nivel Bloom ===\n");
        for (String nivel : totalPorNivel.keySet()) {
            int total = totalPorNivel.get(nivel);
            int correctas = correctasPorNivel.getOrDefault(nivel, 0);
            double porcentaje = (total > 0) ? (correctas * 100.0 / total) : 0;
            resumen.append(String.format("%-15s: %5.1f%% (%d de %d)\n", nivel, porcentaje, correctas, total));
        }

        resumen.append("\n=== Porcentaje de respuestas correctas por Tipo de Ítem ===\n");
        for (String tipo : totalPorTipo.keySet()) {
            int total = totalPorTipo.get(tipo);
            int correctas = correctasPorTipo.getOrDefault(tipo, 0);
            double porcentaje = (total > 0) ? (correctas * 100.0 / total) : 0;
            resumen.append(String.format("%-20s: %5.1f%% (%d de %d)\n", tipo, porcentaje, correctas, total));
        }

        resumenArea.setText(resumen.toString());
    }
}
