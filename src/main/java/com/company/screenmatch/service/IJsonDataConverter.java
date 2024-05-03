package com.company.screenmatch.service;

public interface IJsonDataConverter {

    //este metodo trabaja con tipos de datos genericos
    <T> T dataJsonToClass(String json, Class<T> parameterClass);

}
