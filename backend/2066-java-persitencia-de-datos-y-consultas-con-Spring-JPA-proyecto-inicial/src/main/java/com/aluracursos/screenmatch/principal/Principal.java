package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.Repository.SerieRepository;
import com.aluracursos.screenmatch.model.*;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = System.getenv("API_KEY");
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosSerie> datosSeries = new ArrayList<>();
    private List<Serie> series;
    private Optional<Serie> SerieBuscada;

    private SerieRepository repository;

    //constructor
    public Principal(SerieRepository repository) {
        this.repository = repository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar series 
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                    4 - Buscar series por titulo
                    5 - Top 5 series 
                    6 - Buscar series por categoria
                    7 - Filtrar por temporada y evaluacion
                    8 - Buscar episodios por nombre
                    9 - Top 5 episodios
                     
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    mostrarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriesPorTitulo();
                    break;
                case 5:
                    buscarTop5();
                    break;
                case 6:
                    buscarPorCategoria();
                    break;
                case 7:
                    filtrarPorTyE();
                    break;
                case 8:
                    buscarEpisodiosPortitulo();
                    break;
                case 9:
                    buscartop5Episodios();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }



    private DatosSerie getDatosSerie() {
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        System.out.println(json);
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
        return datos;
    }
    private void buscarEpisodioPorSerie() {
        mostrarSeriesBuscadas();
        System.out.println("escribe el nombre de la serie que quieres ver los episodios");
        var nombeSerie = teclado.nextLine();
        //busqueda que tiene o no un resultado
        Optional<Serie> serie = series.stream()
                .filter(s ->s.getTitulo().toLowerCase().contains(nombeSerie.toLowerCase()))
                .findFirst();


        if (serie.isPresent()) {
            var SerieEncontrada = serie.get();
            List<DatosTemporadas> tempoaradas = new ArrayList<>();


        List<DatosTemporadas> temporadas = new ArrayList<>();

        for (int i = 1; i <= SerieEncontrada.getTotalTemporadas(); i++) {
            var json = consumoApi.obtenerDatos(URL_BASE + SerieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
            DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporada);
        }

        temporadas.forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(d ->d.episodios().stream()
                        .map(e -> new Episodio(d.numero(), e)))
                .collect(Collectors.toList());

        SerieEncontrada.setEpisodios(episodios);
        repository.save(SerieEncontrada);
    }
    }

    private void buscarSerieWeb() {

        DatosSerie datos = getDatosSerie();
//        datosSeries.add(datos);

        Serie serie = new Serie(datos);
        repository.save(serie);
        System.out.println(datos);
    }

    private void mostrarSeriesBuscadas() {
        series= repository.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }
    private void buscarSeriesPorTitulo(){
        System.out.println("Escribe el nombre de la serie ");
        var nombreSerie = teclado.nextLine();

        SerieBuscada = repository.findByTituloContainsIgnoreCase(nombreSerie);

        if(SerieBuscada.isPresent()){
            System.out.println("la serie buscada es "+SerieBuscada.get());
        } else{
            System.out.println("serie no encontrada!");
        }


    }
    private void buscarTop5(){
        List<Serie> topSeries = repository.findTop5ByOrderByEvaluacionDesc();
        topSeries.forEach(s -> System.out.println("Serie: " + s.getTitulo() + "Evaluacion: "+s.getEvaluacion() ));
    }

    private void buscarPorCategoria() {
        System.out.println("Escribe el genero/categoria de la serie: ");
        var textoCategoria = teclado.nextLine();

        Categoria categoria = Categoria.fromSpanol(textoCategoria);

        List<Serie> seriesPorCategoria = repository.findByGenero(categoria);

        System.out.println("Las series en esa categoria son:");
        seriesPorCategoria.forEach(System.out::println);
    }

    private void filtrarPorTyE() {
        System.out.println("Escriba el numero de temporadas: ");
        int totalTemporadas = Integer.parseInt(teclado.nextLine());

        System.out.println("Escriba el valor de la evaluacion: ");
        Double evaluacion = Double.parseDouble(teclado.nextLine());

        List<Serie> filtrarSeries =
                repository.seriesPortemporadaEvaluacion(totalTemporadas, evaluacion);

        System.out.println("**** Series filtradas ****");
        filtrarSeries.forEach(s ->
                System.out.println(
                        s.getTitulo() + " -- Evaluacion: " + s.getEvaluacion()
                )
        );
    }
    private void buscarEpisodiosPortitulo() {
        System.out.println("escribe el nombre del espisodio a buscar");
        var nombreEpisodio = teclado.nextLine();
        List<Episodio> episodiosencontrados = repository.episodiosnombre(nombreEpisodio);
        episodiosencontrados.forEach(e ->
                System.out.printf(
                        "Serie %s | Episodio %s | Temporada %s | Evaluación %s%n",
                        e.getSerie().getTitulo(),
                        e.getNumeroEpisodio(),
                        e.getTemporada(),
                        e.getEvaluacion()
                )
            );
    }

    private void buscartop5Episodios(){
        buscarSeriesPorTitulo();
        if(SerieBuscada.isPresent()){
            Serie serie = SerieBuscada.get();
            List<Episodio> topEpisodios = repository.tpo5Episodios(serie);
            topEpisodios.forEach(e ->
                    System.out.printf(
                            "Serie %s | Episodio %s | Temporada %s | Evaluación %s%n",
                            e.getSerie().getTitulo(),
                            e.getNumeroEpisodio(),
                            e.getTemporada(),
                            e.getEvaluacion()
                    )
                );
        }

    }


}




