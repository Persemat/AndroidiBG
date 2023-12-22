package it.unimib.sportq.model;

public class ListData {
    public String sport, player;
    public int image;

    public ListData(String name, String player, int image) {
        this.sport = sport;
        this.player = player;
        this.image = image;
    }

    public String getSport() {
        return sport;
    }

    public String getPlayer() {
        return player;
    }

    public int getImage() {
        return image;
    }
}
