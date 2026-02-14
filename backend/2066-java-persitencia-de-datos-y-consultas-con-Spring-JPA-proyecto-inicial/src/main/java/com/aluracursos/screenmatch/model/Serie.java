package com.aluracursos.screenmatch.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "series")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    private Integer totalTemporadas;
    private Double evaluacion;
    private String poster;

    @Enumerated(EnumType.STRING)
    private Categoria genero;

    private String actores;

    @Column(length = 1000)
    private String sinopsis;

    @OneToMany(
            mappedBy = "serie",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private List<Episodio> episodios;

    //  Constructor vacío (obligatorio para JPA)
    public Serie() {}

    //  Constructor con datos de la API
    public Serie(DatosSerie datosSerie) {
        this.titulo = datosSerie.titulo();
        this.totalTemporadas = datosSerie.totalTemporadas();
        this.evaluacion = datosSerie.evaluacion() != null
                ? Double.valueOf(datosSerie.evaluacion())
                : 0.0;
        this.poster = datosSerie.poster();
        this.genero = Categoria.fromString(
                datosSerie.genero().split(",")[0].trim()
        );
        this.actores = datosSerie.actores();
        this.sinopsis = datosSerie.sinopsis();
    }

    //  Getter y Setter clave para evitar errores
    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(e -> e.setSerie(this)); // relación bidireccional para el id
        this.episodios = episodios;
    }

    //  Getters y setters normales

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public String getPoster() {
        return poster;
    }

    public Categoria getGenero() {
        return genero;
    }

    public String getActores() {
        return actores;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    @Override
    public String toString() {
        return "Serie{" +
                "titulo='" + titulo + '\'' +
                ", genero=" + genero +
                ", temporadas=" + totalTemporadas +
                ", evaluacion=" + evaluacion +
                ", episodios="+ episodios +
                '}';
    }
}
