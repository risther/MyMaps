package com.desarrollo.mymaps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Seleccion extends AppCompatActivity {


    Spinner spinnerT;
    String tipo;
    Button btn;
    MapsActivity n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion);
        MapsActivity n= new MapsActivity();
        spinnerT=(Spinner) findViewById(R.id.spinner);
        Spinner spinner=findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.tipoMapa,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipo=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn=(Button) findViewById(R.id.btnAceptar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nn= new Intent(Seleccion.this,MapsActivity.class);
                if (tipo.equals("Normal")){

                    nn.putExtra("dato","1");
                    Toast.makeText(Seleccion.this,"De Tacna a japon 16362.56 km aprox",Toast.LENGTH_SHORT).show();
                    startActivity(nn);
                }
                if (tipo.equals("Satelite")){
                    nn.putExtra("dato","2");
                    Toast.makeText(Seleccion.this,"De Tacna a Alemania 10157.04 km aprox",Toast.LENGTH_SHORT).show();
                    startActivity(nn);
                }
                if (tipo.equals("Hibrido")){
                    nn.putExtra("dato","3");
                    Toast.makeText(Seleccion.this,"De Tacna a Italia 10898.21 km aprox",Toast.LENGTH_SHORT).show();
                    startActivity(nn);
                }
                if (tipo.equals("Terrarian")){
                    nn.putExtra("dato","4");
                    Toast.makeText(Seleccion.this,"De Tacna a Francia 11251.76 km aprox",Toast.LENGTH_SHORT).show();
                    startActivity(nn);
                }
            }
        });

    }
}