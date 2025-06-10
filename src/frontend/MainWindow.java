package frontend;

import backend.PruebaController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class MainWindow extends JFrame {
    private PruebaController controller;

    private JLabel lblCantidadItems;
    private JLabel lblTiempoTotal;
    private JButton btnCargarArchivo;
    private JButton btnIniciarPrueba;

    public MainWindow() {
        super("Sistema de Evaluación - Taxonomía de Bloom");
        this.controller = new PruebaController();
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1));

        lblCantidadItems = new JLabel("Cantidad de ítems: 0", SwingConstants.CENTER);
        lblTiempoTotal = new JLabel("Tiempo total estimado: 0 segundos", SwingConstants.CENTER);

        btnCargarArchivo = new JButton("Cargar archivo de ítems");
        btnIniciarPrueba = new JButton("Iniciar prueba");
        btnIniciarPrueba.setEnabled(false);

        btnCargarArchivo.addActionListener(e -> cargarArchivo());
        btnIniciarPrueba.addActionListener(e -> iniciarPrueba());

        add(new JLabel("Sistema de Evaluación por Taxonomía de Bloom", SwingConstants.CENTER));
        add(btnCargarArchivo);
        add(lblCantidadItems);
        add(lblTiempoTotal);
        add(btnIniciarPrueba);

        setVisible(true);
    }

    private void cargarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        int opcion = fileChooser.showOpenDialog(this);

        if (opcion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            boolean cargado = controller.cargarItemsDesdeArchivo(archivo);

            if (cargado) {
                int cantidad = controller.getCantidadItems();
                int tiempo = controller.getTotalTiempoEstimado();

                lblCantidadItems.setText("Cantidad de ítems: " + cantidad);
                lblTiempoTotal.setText("Tiempo total estimado: " + tiempo + " segundos");
                btnIniciarPrueba.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Error al cargar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void iniciarPrueba() {
        this.dispose(); // Cierra la ventana principal
        new PruebaWindow(controller); // Asegúrate de tener implementada esta clase
    }

    // ESTE ES EL MÉTODO PRINCIPAL CORRECTO PARA SWING
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow());
    }
}
