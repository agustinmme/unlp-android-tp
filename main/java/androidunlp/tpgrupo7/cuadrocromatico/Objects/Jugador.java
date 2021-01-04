package androidunlp.tpgrupo7.cuadrocromatico.Objects;

public class Jugador {
    public String Name;
    public int Score;

    public Jugador(String name, int score) {
        Name = name;
        Score = score;
    }

    public Jugador() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }
}
