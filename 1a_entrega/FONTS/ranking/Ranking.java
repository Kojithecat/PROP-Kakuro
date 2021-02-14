package ranking;

import partida.*;

import java.util.*;

public class Ranking {
    Comparator<Partida> puntuacionAsc = Comparator.comparing(Partida::getPuntuacion);
	Comparator<Partida> puntuacionDesc = puntuacionAsc.reversed();
          
    SortedSet<Partida> ranking = new TreeSet<>(puntuacionDesc);  //No se si aqui hay que poner el Comparator.comparing
    
    public Ranking()
    {

    }

    public void anadirPartida(Partida p)
    {
        ranking.add(p);
    }

    public int getSize(){
        return ranking.size();
    }

    public Partida getBest()
    {
        return ranking.first();
    }
}
