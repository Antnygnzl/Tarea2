package backend;

import java.util.List;

public class Item {
    private int id;
    private String tipo; // "SeleccionMultiple" o "VF"
    private String nivelBloom; // Recordar, Entender, etc.
    private String enunciado;
    private List<String> opciones;
    private String respuestaCorrecta;
    private int tiempoEstimado; // En segundos
    private int anioUso;

    public Item(int id, String tipo, String nivelBloom, String enunciado, List<String> opciones, String respuestaCorrecta, int tiempoEstimado, int anioUso) {
        this.id = id;
        this.tipo = tipo;
        this.nivelBloom = nivelBloom;
        this.enunciado = enunciado;
        this.opciones = opciones;
        this.respuestaCorrecta = respuestaCorrecta;
        this.tiempoEstimado = tiempoEstimado;
        this.anioUso = anioUso;
    }

    // Getters
    public int getId() { return id; }
    public String getTipo() { return tipo; }
    public String getNivelBloom() { return nivelBloom; }
    public String getEnunciado() { return enunciado; }
    public List<String> getOpciones() { return opciones; }
    public String getRespuestaCorrecta() { return respuestaCorrecta; }
    public int getTiempoEstimado() { return tiempoEstimado; }
    public int getAnioUso() { return anioUso; }
}
