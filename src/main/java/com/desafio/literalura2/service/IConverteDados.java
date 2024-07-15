package com.desafio.literalura2.service;

public interface IConverteDados {
    <T> T  obterDados(String json, Class<T> classe);
}
