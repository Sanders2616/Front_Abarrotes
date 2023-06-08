package com.example.demoapi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private Button btnGuardar;
    private Button btnActualizar;
    private Button btnBuscar;
    private Button btnEliminar;
    private EditText etCodigoBarras;
    private EditText etDescripcion;
    private EditText etMarca;
    private EditText etPrecioCompra;

    private EditText etPrecioVenta;

    private EditText etExistencias;
    private ListView lvProductos;
    private RequestQueue colaPeticiones;
    private JsonArrayRequest jsonArrayRequest;
    private ArrayList<String> origenDatos=new ArrayList<String>();
    private ArrayAdapter<String>adapter;

    private String url="http://10.10.62.6:3000/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGuardar= findViewById(R.id.btnGuardar);
        btnActualizar= findViewById(R.id.btnActualizar);
        btnBuscar= findViewById(R.id.btnBuscar);
        btnEliminar= findViewById(R.id.btnEliminar);
        etCodigoBarras= findViewById(R.id.etCodigoBarras);
        etDescripcion= findViewById(R.id.etDescripcion);
        etMarca= findViewById(R.id.etMarca);
        etPrecioCompra= findViewById(R.id.etPrecioCompra);
        etPrecioVenta= findViewById(R.id.etPrecioVenta);
        etExistencias= findViewById(R.id.etExistencias);
        lvProductos= findViewById(R.id.lvProductos);
        colaPeticiones= Volley.newRequestQueue(this);
        listarProductos();
        //Delete button
        /* 
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.DELETE,
                        url + "borrar/" + etCodigoBarras.getText().toString(),
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getString("status").equals("Producto eliminado")) {
                                        Toast.makeText(MainActivity.this, "Producto Eliminado con EXITO!", Toast.LENGTH_SHORT).show();
                                        etCodigoBarras.setText("");
                                        adapter.clear();
                                        lvProductos.setAdapter(adapter);
                                        listarProductos();
                                    }else if (response.getString("status").equals("Not Found")){
                                        Toast.makeText(MainActivity.this, "Producto no encontrado", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                colaPeticiones.add(jsonObjectRequest);
            }
        });*/
         btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etCodigoBaras.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Ingrese el código de barras", Toast.LENGTH_SHORT).show();
                } else {
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                            Request.Method.DELETE,
                            url + "borrar/" + etCodigoBaras.getText().toString(),
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        if (response.getString("status").equals("Producto eliminado")) {
                                            Toast.makeText(MainActivity.this, "Producto Eliminado con EXITO!", Toast.LENGTH_SHORT).show();
                                            etCodigoBaras.setText("");
                                            etDescripcion.setText("");
                                            etMarca.setText("");
                                            etPrecioCompra.setText("");
                                            etPrecioVenta.setText("");
                                            etExistencia.setText("");
                                            adapter.clear();
                                            lvProductos.setAdapter(adapter);
                                            listarProductos();
                                        } else if (response.getString("status").equals("Not Found")) {
                                            Toast.makeText(MainActivity.this, "Producto no encontrado", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {
                                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    );
                    colaPeticiones.add(jsonObjectRequest);
                }
            }
        });



        /*Button update
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject producto  = new JSONObject();
                try {
                    producto .put("codigobarras",etCodigoBarras.getText().toString());
                    producto .put("descripcion",etDescripcion.getText().toString());
                    producto .put("marca",etMarca.getText().toString());
                    producto .put("preciocompra",Float.parseFloat(etPrecioCompra.getText().toString()));
                    producto .put("precioventa",Float.parseFloat(etPrecioVenta.getText().toString()));
                    producto .put("existencias",Float.parseFloat(etExistencias.getText().toString()));
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.PUT,
                        url + "actualizar/" + etCodigoBarras.getText().toString(),
                        producto,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getString("status").equals("Producto actualizado")){
                                        Toast.makeText(MainActivity.this, "Producto actualizado con EXITO!", Toast.LENGTH_SHORT).show();
                                        etCodigoBarras.setText("");
                                        etDescripcion.setText("");
                                        etMarca.setText("");
                                        etPrecioCompra.setText("");
                                        etPrecioVenta.setText("");
                                        etExistencias.setText("");
                                        adapter.clear();
                                        lvProductos.setAdapter(adapter);
                                        listarProductos();
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, error.getMessage() , Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                colaPeticiones.add(jsonObjectRequest);
            }
        });

        */
        //Button update
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCodigoBaras.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Primero use el BOTÓN BUSCAR!", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject productos = new JSONObject();
                    try {
                        productos.put("codigobarras", etCodigoBaras.getText().toString());
                        if (!etDescripcion.getText().toString().isEmpty()) {
                            productos.put("descripcion", etDescripcion.getText().toString());
                        }

                        if (!etMarca.getText().toString().isEmpty()) {
                            productos.put("marca", etMarca.getText().toString());
                        }

                        if (!etPrecioCompra.getText().toString().isEmpty()) {
                            productos.put("preciocompra", Float.parseFloat(etPrecioCompra.getText().toString()));
                        }

                        if (!etPrecioVenta.getText().toString().isEmpty()) {
                            productos.put("precioventa", Float.parseFloat(etPrecioVenta.getText().toString()));
                        }

                        if (!etExistencia.getText().toString().isEmpty()) {
                            productos.put("existencias", Float.parseFloat(etExistencia.getText().toString()));
                        }

                    } catch (JSONException e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    JsonObjectRequest actualizar = new JsonObjectRequest(
                            Request.Method.PUT,
                            url + "actualizar/" + etCodigoBaras.getText().toString(),
                            productos,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        if (response.getString("status").equals("Producto actualizado")) {
                                            Toast.makeText(MainActivity.this, "Producto actualizado con EXITO!", Toast.LENGTH_SHORT).show();
                                            etCodigoBaras.setText("");
                                            etDescripcion.setText("");
                                            etMarca.setText("");
                                            etPrecioCompra.setText("");
                                            etPrecioVenta.setText("");
                                            etExistencia.setText("");
                                            adapter.clear();
                                            lvProductos.setAdapter(adapter);
                                            listarProductos();
                                        } else if (response.getString("status").equals("No encontrado")) {
                                            Toast.makeText(MainActivity.this, "Producto no encontrado", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {
                                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    );
                    colaPeticiones.add(actualizar);
                }
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObjectRequest peticion=new JsonObjectRequest(
                        Request.Method.GET,
                        url + etCodigoBarras.getText().toString(),
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                if(response.has("status"))
                                    Toast.makeText(MainActivity.this, "producto no encontrado", Toast.LENGTH_SHORT).show();
                                else {
                                    try {
                                        etDescripcion.setText(response.getString("descripcion"));
                                        etMarca.setText(response.getString("marca"));
                                        etPrecioCompra.setText(String.valueOf(response.getInt("preciocompra")));
                                        etPrecioVenta.setText(String.valueOf(response.getInt("precioventa")));
                                        etExistencias.setText(String.valueOf(response.getInt("existencias")));
                                    } catch (JSONException e) {
                                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                colaPeticiones.add(peticion);
            }
        });





        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="http://10.10.62.6:3000/";
                    JSONObject producto = new JSONObject();
                try {
                    producto.put("codigobarras",etCodigoBarras.getText().toString());
                    producto.put("descripcion",etDescripcion.getText().toString());
                    producto.put("marca",etMarca.getText().toString());
                    producto.put("precio compra",Float.parseFloat(etPrecioCompra.getText().toString()));
                    producto.put("precio venta",Float.parseFloat(etPrecioVenta.getText().toString()));
                    producto.put("existencias",Integer.parseInt(etExistencias.getText().toString()));

                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                        Request.Method.POST,
                        url + "insert/",
                        producto,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getString("status").equals("Producto insertado"))
                                        Toast.makeText(MainActivity.this, "Producto insertado con exito", Toast.LENGTH_SHORT).show();
                                    adapter.clear();
                                    lvProductos.setAdapter(adapter);
                                    listarProductos();
                                } catch (JSONException e) {
                                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                colaPeticiones.add(jsonObjectRequest);

            }
        });
    }
    protected void listarProductos(){
            String url="http://10.10.62.6:3000/";
           jsonArrayRequest= new JsonArrayRequest(
                   Request.Method.GET,
                   url,
                   null,
                   new Response.Listener<JSONArray>() {
                       @Override
                       public void onResponse(JSONArray response) {
                           for(int i=0;i<response.length();i++){
                               try {
                                String codigobarras=response.getJSONObject(i).getString("codigobarras");
                                String descripcion=response.getJSONObject(i).getString("descripcion");
                                String marca=response.getJSONObject(i).getString("marca");
                                origenDatos.add(codigobarras + ": " +descripcion+ ": " +marca);
                               } catch (JSONException e) {

                               }
                           }
                           adapter=new ArrayAdapter<String>(MainActivity.this,
                                   androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                                   origenDatos);
                           lvProductos.setAdapter(adapter);
                       }
                   },
                   new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {
                           Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   }
           );
           colaPeticiones.add(jsonArrayRequest);
    }
}
