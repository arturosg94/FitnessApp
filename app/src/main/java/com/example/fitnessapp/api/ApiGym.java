package com.example.fitnessapp.api;


import com.example.fitnessapp.modelo.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiGym {

    @POST("ValidarUsuario")
    Call<List<Respuesta>> ValidarUsuario(@Body List<Usuario> usuario);
}
