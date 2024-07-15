CREATE TABLE livros(
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       nome VARCHAR(255),
                       idioma VARCHAR(255),
                       quantidade_de_downloads INTEGER,
                       autor_id BIGINT,
                       FOREIGN KEY (autor_id) REFERENCES autores(id)
);