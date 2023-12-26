package com.upm.es.grupo9;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import com.upm.es.grupo9.OnRegistrationListener;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterRequest extends AsyncTask<String, Void, String> implements OnRegistrationListener {
    private OnRegistrationListener registrationListener;

    public RegisterRequest(OnRegistrationListener listener) {
        this.registrationListener = listener;
    }



    @Override
    protected String doInBackground(String... params) {
        String urlString = params[0];
        String name = params[1];
        String username = params[2];
        String edadString = params[3];
        int edad;

        try {
            Log.d("RegisterRequest", "Enviando solicitud de registro...");
            OkHttpClient client = new OkHttpClient();

            // Convertir la edad a entero
            edad = Integer.parseInt(edadString);

            // Construir el cuerpo de la solicitud
            RequestBody requestBody = new FormBody.Builder()
                    .add("name", name)
                    .add("username", username)
                    .add("edad", String.valueOf(edad))
                    .add("password", params[4])
                    .build();

            // Construir la solicitud POST
            Request request = new Request.Builder()
                    .url(urlString)
                    .post(requestBody)
                    .build();

            // Ejecutar la solicitud y obtener la respuesta
            Response response = client.newCall(request).execute();

            // Verificar si la respuesta fue exitosa
            if (response.isSuccessful()) {
                // Leer la respuesta del servidor
                String responseBody = response.body().string();
                Log.d("RegisterRequest", "Response: " + responseBody);

                // Devolver la respuesta para que onPostExecute la maneje
                return responseBody;
            } else {
                // Manejar errores de conexión
                Log.e("RegisterRequest", "Error en la conexión: " + response.code());
            }

        } catch (Exception e) {
            // Manejar errores generales
            Log.e("RegisterRequest", "Error general: " + e.getMessage());
            e.printStackTrace();
        }

        // Devolver null si hay algún error
        return null;
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        // Verificar si la respuesta no es nula y contiene "success"
        if (result != null && result.contains("success")) {
            // Informar a la actividad que la registración fue exitosa
            registrationListener.onRegistrationSuccess();
        } else {
            // Informar a la actividad sobre el error
            registrationListener.onRegistrationError("Error en la conexión o datos incorrectos");
        }
    }
    @Override
    public void onRegistrationSuccess() {

    }

    @Override
    public void onRegistrationError(String errorMessage) {

    }
}
