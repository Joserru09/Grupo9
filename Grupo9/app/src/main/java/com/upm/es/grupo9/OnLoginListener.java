package com.upm.es.grupo9;

public interface OnLoginListener {
    void onLoginSuccess(String username, String name, int edad);
    void onLoginError(String errorMessage);
}
