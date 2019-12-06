package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapp.api.ApiGym;
import com.example.fitnessapp.modelo.Respuesta;
import com.example.fitnessapp.modelo.Usuario;
import com.google.gson.JsonSerializationContext;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    TextView btn_ingresar;
    EditText btn_DNI;
    EditText btn_contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_ingresar= (TextView) findViewById(R.id.btn_ingresar);
        btn_DNI = (EditText) findViewById(R.id.btn_DNI);
        btn_contrasena = (EditText) findViewById(R.id.btn_contraseña);

        //Eventos
        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dni=btn_DNI.getText().toString();
                String contrasena=btn_contrasena.getText().toString();


                Usuario usuario= new Usuario(dni,contrasena);
                List<Usuario> listausuario= new ArrayList<>();
                listausuario.add(usuario);
                ApiValidarUsuario(listausuario);
            }
        });
    }

    public void ApiValidarUsuario(List<Usuario> listausuario){
        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://192.168.43.80:8082/api/api-gym/")
                .baseUrl("http://190.117.72.184/API-GYM/api/api-gym/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiGym apigym = retrofit.create(ApiGym.class);
        Call<List<Respuesta>> peticion = apigym.ValidarUsuario(listausuario);

        peticion.enqueue(new Callback<List<Respuesta>>() {
            @Override
            public void onResponse(Call<List<Respuesta>> call, Response<List<Respuesta>> response) {
                if (!response.isSuccessful()){
                    return;
                }
                List<Respuesta> respuestas = response.body();
                String valor = respuestas.get(0).getValor();
                if (valor.equals("1")){
                    Toast.makeText(Login.this,"Bienvenido"+respuestas.get(0).getMensaje(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent (Login.this, MainActivity.class);
                    startActivityForResult(intent, 0);
                }else{
                    Toast.makeText(Login.this,"Nombre "+respuestas.get(0).getMensaje(),Toast.LENGTH_SHORT).show();
                }
                //Recorrer la lista
                /*for (Respuesta rsp : respuestas){
                    System.out.println("hola:"+rsp.getMensaje());
                }*/
            }

            @Override
            public void onFailure(Call<List<Respuesta>> call, Throwable t) {

            }
        });

    }



}
