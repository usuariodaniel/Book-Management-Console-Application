package com.desafio.literalura2.service;

import com.desafio.literalura2.model.DadosAutor;
import com.desafio.literalura2.model.DadosLivro;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados implements IConverteDados{
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            JsonNode node = mapper.readTree(json);
            if (classe == DadosLivro.class) {
                var s = node.get("results").size() > 0 ? node.get("results").get(0) : null;
                return s != null ? mapper.treeToValue(s, classe) : null;
            } else if (classe == DadosAutor.class) {
                var authors = node.get("results").size() > 0 && node.get("results").get(0).has("authors") ?
                        node.get("results").get(0).get("authors") : null;
                return authors != null && authors.size() > 0 ? mapper.treeToValue(authors.get(0), classe) : null;
            } else {
                return mapper.readValue(json, classe);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
