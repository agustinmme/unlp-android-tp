package androidunlp.tpgrupo7.cuadrocromatico.Objects;

public class Nivel {
        public String nombre;
        public Jugador[] player;

    public Nivel(String nombre, Jugador[] player) {
        this.nombre = nombre;
        this.player = player;
    }

    public Nivel() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Jugador[] getPlayer() {
        return player;
    }

    public void setPlayer(Jugador[] player) {
        this.player = player;
    }


}
