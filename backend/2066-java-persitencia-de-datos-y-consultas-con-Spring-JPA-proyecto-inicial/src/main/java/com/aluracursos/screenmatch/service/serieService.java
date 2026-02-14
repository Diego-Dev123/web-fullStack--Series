package com.aluracursos.screenmatch.service;

import com.aluracursos.screenmatch.Repository.SerieRepository;
import com.aluracursos.screenmatch.dto.episodioDto;
import com.aluracursos.screenmatch.dto.serieDTO;
import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.model.Serie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class serieService {
    @Autowired
    private SerieRepository repository;

    public List<serieDTO> obtenerSeries() {
        return convierteDatos(repository.findAll());
    }

    public List<serieDTO> obtenertop5() {
        return convierteDatos(repository.findTop5ByOrderByEvaluacionDesc());
    }

    public List<serieDTO> obtenerLanzamientosRecientes() {
        return convierteDatos(repository.lanzamientosRecientes());
    }

    public List<serieDTO> convierteDatos(List<Serie> series) {
        return series.stream()
                .map(s -> new serieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(),
                        s.getEvaluacion(), s.getPoster(), s.getGenero(),
                        s.getActores(), s.getSinopsis()
                ))
                .collect(Collectors.toList());

    }

    public serieDTO obtenerporId(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return new serieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(),
                    s.getEvaluacion(), s.getPoster(), s.getGenero(),
                    s.getActores(), s.getSinopsis());
        }
        return null;
    }

    public List<episodioDto> obtenerTodasLasTemporadas(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return s.getEpisodios().stream().map(e -> new episodioDto(e.getTemporada(), e.getTitulo(),
                    e.getNumeroEpisodio())).collect(Collectors.toList());
        }
        return null;
    }

    public List<episodioDto> obtenerTemporadasPorNumero(Long id, Long numeroTemporada) {
        return repository.obtenerTemporadasPorNumero(id, numeroTemporada).stream()
                .map(e -> new episodioDto(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio())).collect(Collectors.toList());
    }

    public List<serieDTO> obtenerPorCategoria(String nombreGenero) {
        Categoria categoria = Categoria.fromSpanol(nombreGenero);
        return convierteDatos(repository.findByGenero(categoria));
    }
}



