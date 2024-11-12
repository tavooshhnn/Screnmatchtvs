package com.tavoos.screnmatch.service;

public interface IConvierteDatos {
    <T> T obtenerDaros(String json, Class<T> clase);
}
