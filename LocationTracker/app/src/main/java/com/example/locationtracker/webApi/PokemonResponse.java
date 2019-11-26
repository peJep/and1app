package com.example.locationtracker.webApi;

public class PokemonResponse {

    private int id;
    private String name;

    public PokemonResponse(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
