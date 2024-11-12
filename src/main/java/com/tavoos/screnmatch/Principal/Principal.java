package com.tavoos.screnmatch.Principal;

import com.tavoos.screnmatch.model.DatosEpisodio;
import com.tavoos.screnmatch.model.DatosSerie;
import com.tavoos.screnmatch.model.DatosTemporadas;
import com.tavoos.screnmatch.model.Episodio;
import com.tavoos.screnmatch.service.ConsumoAPI;
import com.tavoos.screnmatch.service.ConvierteDatos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=3c000dbe";
    private ConvierteDatos conversor = new ConvierteDatos();

    public void muestraElMenu() {
        System.out.println("Por favor escribe el nombre de la serie que deseas ver");
        // Busca los datos generales de la serie
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        var datos = conversor.obtenerDaros(json, DatosSerie.class);
        System.out.println(datos);

        // Busca los datos de todas las temporadas
        List<DatosTemporadas> temporadas = new ArrayList<>();
        for (int i = 1; i <= datos.totalDeTemporadas() ; i++) {
            json= consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + "&Season="+i+ API_KEY);
            var datosTemporadas = conversor.obtenerDaros(json, DatosTemporadas.class);
            temporadas.add(datosTemporadas);

        }
        //temporadas.forEach(System.out::println);
        // Mostrar solo el titulo de los episodios de las temporadas
        /*for (int i = 0; i < datos.totalDeTemporadas(); i++) {
            List<DatosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
            for (int j = 0; j < episodiosTemporada.size(); j++) {
                System.out.println(episodiosTemporada.get(j).titulo());
            }
        }*/
        //temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        //CONVERTIR LA INFORMACION A UNA LISTA DE DATOS EPISODIO
        List<DatosEpisodio> datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()).collect(Collectors.toList());


        //____________TOP 5 EPISODIO ________
//        System.out.println("El top 5 de tus episodios");
//        datosEpisodios.stream()
//                .filter(e ->!e.evaluacion().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Primer filtro (N/A)" + e))
//                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
//                .peek(e -> System.out.println("Segundo filtro (M>m)" + e))
//                .map( e -> e.titulo().toUpperCase())
//                .limit(5)
//                .peek(e -> System.out.println("Tercer filtro (m<M)" + e))
//                .forEach(System.out::println);





        //CONVIRTIENDO LOS DATOS A UNA LISTA TIPO EPISODIO

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t ->t.episodios().stream().map(d ->new Episodio(t.numero(),d))).collect(Collectors.toUnmodifiableList());
        //episodios.forEach(System.out::println);

        //BUSQUEDA DE EPISODIOS A PARTIR DE X AÑO

//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getFechaDeLanzamiento() != null && e.getFechaDeLanzamiento().isAfter(fechaBusqueda))
//                .forEach(e -> System.out.println(
//                        "Temporada" + e.getTemporada() + "Episodio" + e.getTitulo() + "Fecha de lanzamiento" + e.getFechaDeLanzamiento()
//                                .format(dtf)
//                ));

        //Busca episodio por un pedazo del titulo
//        System.out.println("Por favor escriba el titulo del episodio que deseas ver");
//        var pedazoTitulo = teclado.nextLine();
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))
//                .findFirst();
//        if (episodioBuscado.isPresent()){
//            System.out.println("Episodio encontrado");
//            System.out.println("los datos son: " + episodioBuscado.get());
//        }else {
//            System.out.println("Episodio no encontrado");





        Map<Integer, Double> evaluacionesPorTemporada = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada, Collectors.averagingDouble(Episodio::getEvaluacion)));
        System.out.println(evaluacionesPorTemporada);
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));
        System.out.println("Media de evaluaciones " + est.getAverage());
        System.out.println("Episodio mejor evaluado" + est.getMax());
        System.out.println("Episodio pero evaluado" + est.getMin());
    }

}
