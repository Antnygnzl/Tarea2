package backend;

import java.io.*;
import java.util.*;

public class PruebaController {

    private List<Item> items = new ArrayList<>();
    private List<String> respuestasUsuario = new ArrayList<>();
    private int currentIndex = 0;

    public boolean cargarItemsDesdeArchivo(File archivo) {
        items.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean primera = true;
            int id = 1;

            while ((linea = br.readLine()) != null) {
                if (primera) {
                    primera = false; // Saltar encabezado
                    continue;
                }

                String[] partes = linea.split(",", -1);
                if (partes.length < 6) {
                    System.out.println("Línea inválida (faltan columnas): " + linea);
                    continue;
                }

                String tipo = partes[0].trim().toLowerCase();
                String enunciado = partes[1].trim();
                String opcionesRaw = partes[2].trim();
                String nivel = partes[3].trim();
                String tiempoStr = partes[4].trim();
                String respuesta = partes[5].trim();
                int anio = Calendar.getInstance().get(Calendar.YEAR);

                int tiempo;
                try {
                    tiempo = Integer.parseInt(tiempoStr);
                } catch (NumberFormatException e) {
                    System.out.println("Tiempo inválido en línea: " + linea);
                    continue;
                }

                List<String> opciones = new ArrayList<>();
                if (tipo.equals("opcion_multiple")) {
                    if (!opcionesRaw.isEmpty()) {
                        String[] ops = opcionesRaw.split(";");
                        opciones.addAll(Arrays.asList(ops));
                    }
                } else if (tipo.equals("verdadero_falso")) {
                    opciones.add("Verdadero");
                    opciones.add("Falso");
                } else {
                    System.out.println("Tipo desconocido en línea: " + linea);
                    continue;
                }

                items.add(new Item(id++, tipo, nivel, enunciado, opciones, respuesta, tiempo, anio));
            }

            return !items.isEmpty();
        } catch (Exception e) {
            System.out.println("Error general al leer archivo: " + e.getMessage());
            return false;
        }
    }

    public List<Item> getItems() {
        return items;
    }

    public int getTotalTiempoEstimado() {
        return items.stream().mapToInt(Item::getTiempoEstimado).sum();
    }

    public int getCantidadItems() {
        return items.size();
    }

    public void guardarRespuesta(String respuesta) {
        while (respuestasUsuario.size() <= currentIndex) {
            respuestasUsuario.add("");
        }
        respuestasUsuario.set(currentIndex, respuesta);
    }

    public String getRespuesta(int index) {
        if (index < respuestasUsuario.size()) {
            return respuestasUsuario.get(index);
        }
        return "";
    }

    public Item getItemActual() {
        return items.get(currentIndex);
    }

    public void siguienteItem() {
        if (currentIndex < items.size() - 1) currentIndex++;
    }

    public void anteriorItem() {
        if (currentIndex > 0) currentIndex--;
    }

    public int getIndiceActual() {
        return currentIndex;
    }

    public boolean estaEnPrimeraPregunta() {
        return currentIndex == 0;
    }

    public boolean estaEnUltimaPregunta() {
        return currentIndex == items.size() - 1;
    }

    public List<String> getRespuestasUsuario() {
        return respuestasUsuario;
    }
}
