package com.example.pruebaclientesocket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Socket socket;
    private PrintWriter printWriter;
    private EditText Nombre, Edad;
    private TextView tv;
    private Button btnEnviar;
    private int puerto = 8000;
    private String mensaje, nombre, edad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Nombre = findViewById(R.id.editNombre);
        Edad = findViewById(R.id.editApe);
        tv = findViewById(R.id.tv);
        btnEnviar = findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = Nombre.getText().toString();
                edad = Edad.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            socket = new Socket("192.168.1.127", puerto);
                            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                            ArrayList<Anuncio> listaFavoritos = new ArrayList();
                            listaFavoritos = ( ArrayList<Anuncio>) ois.readObject();
                            ois.close();
                            tv.setText(listaFavoritos.get(0).getTitulo());
                            /*FUNCIONA
                            OutputStream os = socket.getOutputStream();
                            DataOutputStream dos = new DataOutputStream(os);
                            dos.writeUTF("SOY " + nombre + " ... HOLA SERVIDOR");
                            InputStream is = socket.getInputStream();
                            DataInputStream dis = new DataInputStream(is);
                            tv.setText(dis.readUTF());
                            is.close();
                            dis.close();
                            os.close();
                            dos.close();*/
                            socket.close();
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
                Nombre.setText(null);
                Edad.setText(null);

            }
        });
    }
}