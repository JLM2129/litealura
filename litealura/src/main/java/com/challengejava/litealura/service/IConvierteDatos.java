package com.challengejava.litealura.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json,Class<T> clase);
}
