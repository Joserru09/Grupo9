package com.upm.es.grupo9;
import android.os.AsyncTask;
import android.util.Log;
import com.upm.es.grupo9.OnRegistrationListener;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginRequest extends AsyncTask<String, Void, String> {
    private OnLoginListener loginListener;

    public LoginRequest(OnLoginListener listener) {
        this.loginListener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String urlString = params[0];
        String username = params[1];
        String password = params[2];

        try {
            Log.d("LoginRequest", "Enviando solicitud de inicio de sesión...");
            OkHttpClient client = new OkHttpClient();

            // Construir el cuerpo de la solicitud
            RequestBody requestBody = new FormBody.Builder()
                    .add("username", username)
                    .add("password", password)
                    .build();

            // Construir la solicitud POST
            Request request = new Request.Builder()
                    .url(urlString)
                    .post(requestBody)
                    .build();

            // Ejecutar la solicitud y obtener la respuesta
            Response response = client.newCall(request).execute();
            Log.d("LoginRequest", "Contraseña enviada: " + password);

            // Verificar si la respuesta fue exitosa
            if (response.isSuccessful()) {
                // Leer la respuesta del servidor
                String responseBody = response.body().string();
                Log.d("LoginRequest", "Response: " + responseBody);

                // Devolver la respuesta para que onPostExecute la maneje
                return responseBody;
            } else {
                // Manejar errores de conexión
                Log.e("LoginRequest", "Error en la conexión: " + response.code());
            }

        } catch (Exception e) {
            // Manejar errores generales
            Log.e("LoginRequest", "Error general: " + e.getMessage());
            e.printStackTrace();
        }

        // Devolver null si hay algún error
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        // Verificar si la respuesta no es nula
        if (result != null) {
            try {
                JSONObject jsonResult = new JSONObject(result);

                // Verificar si el inicio de sesión fue exitoso
                if (jsonResult.getBoolean("success")) {
                    String username = jsonResult.getString("username");
                    String name = jsonResult.getString("name");
                    int edad = jsonResult.getInt("edad");

                    // Informar a la actividad que el inicio de sesión fue exitoso
                    loginListener.onLoginSuccess(username, name, edad);
                } else {
                    // Informar a la actividad sobre el error en el inicio de sesión
                    String errorMessage = jsonResult.getString("message");
                    loginListener.onLoginError(errorMessage);
                }
            } catch (JSONException e) {
                Log.e("LoginRequest", "Error al procesar la respuesta JSON: " + e.getMessage());
                e.printStackTrace();
                loginListener.onLoginError("Error en el formato de la respuesta del servidor");
            }
        } else {
            // Informar a la actividad sobre el error en el inicio de sesión
            loginListener.onLoginError("Error en la conexión o datos incorrectos");

        }
    }
}
