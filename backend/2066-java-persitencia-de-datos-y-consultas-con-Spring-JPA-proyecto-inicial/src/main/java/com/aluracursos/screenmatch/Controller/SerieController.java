package com.aluracursos.screenmatch.Controller;

import com.aluracursos.screenmatch.Repository.SerieRepository;
import com.aluracursos.screenmatch.dto.episodioDto;
import com.aluracursos.screenmatch.dto.serieDTO;
import com.aluracursos.screenmatch.service.serieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/series")
public class SerieController {
    @Autowired
    private serieService servicio;

    @GetMapping()
    public List<serieDTO> obtenerSeries() {
        return servicio.obtenerSeries();
    }

    @GetMapping("/top5")
    public List<serieDTO> obtenertop5(){
        return servicio.obtenertop5();
    }

    @GetMapping("/lanzamientos")
    public List<serieDTO> lanzamientosRecientes() {
        return servicio.obtenerLanzamientosRecientes();
    }
    @GetMapping("/{id}")
    public serieDTO obtenerporId(@PathVariable Long id){
       return servicio.obtenerporId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<episodioDto> obtenerTemporadas(@PathVariable Long id){
        return servicio.obtenerTodasLasTemporadas(id);
    }

    @GetMapping("/{id}/temporadas/{numeroTemporada}")
    public List<episodioDto> obtenerTempPornumeros(@PathVariable Long id, @PathVariable Long numeroTemporada){
        return servicio.obtenerTemporadasPorNumero(id,numeroTemporada);
    }
    @GetMapping("/categoria/{nombreGenero}")
    public List<serieDTO> obtenerSeriesPorCategoria(@PathVariable String nombreGenero){
        return servicio.obtenerPorCategoria(nombreGenero);
    }


}
