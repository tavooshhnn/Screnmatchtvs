package com.tavoos.screnmatch;

import com.tavoos.screnmatch.Principal.Principal;
import com.tavoos.screnmatch.model.DatosEpisodio;
import com.tavoos.screnmatch.model.DatosSerie;
import com.tavoos.screnmatch.model.DatosTemporadas;
import com.tavoos.screnmatch.service.ConsumoAPI;
import com.tavoos.screnmatch.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScrenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScrenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.muestraElMenu();
		/*var consumoApi = new ConsumoAPI();
		var json = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones&apikey=3c000dbe");
		//var json = consumoApi.obtenerDatos("https://coffee.alexflipnote.dev/random.json" );
		System.out.println(json);
		ConvierteDatos conversor = new ConvierteDatos();
		var datos = conversor.obtenerDaros(json, DatosSerie.class);
		System.out.println(datos);
		json= consumoApi.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones&Season=1&episode=1&apikey=3c000dbe");
		DatosEpisodio episodio = conversor.obtenerDaros(json, DatosEpisodio.class);
		System.out.println(episodio); */

		/*List<DatosTemporadas> temporadas = new ArrayList<>();
		for (int i = 1; i <=datos.totalDeTemporadas()  ; i++) {
			json= consumoApi.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones&Season="+i+"&apikey=3c000dbe");
			var datosTemporadas = conversor.obtenerDaros(json, DatosTemporadas.class);
			temporadas.add(datosTemporadas);*/


		//temporadas.forEach(System.out::println);
	}
}
