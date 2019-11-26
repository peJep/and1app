package com.example.locationtracker.webApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl("https://pokeapi.co")
            .addConverterFactory(GsonConverterFactory.create());
    private static Retrofit retrofit = retrofitBuilder.build();
    private static PokemonApi pokemonApi = retrofit.create(PokemonApi.class);

    public static PokemonApi getPokemonApi() {
        return pokemonApi;
    }

}
