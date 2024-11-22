CREATE TABLE usuarios(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    email VARCHAR(255),
    login VARCHAR(255),
    senha VARCHAR(255),
    ultima_alteracao DATE,
    endereco VARCHAR(255),
    tipo VARCHAR(20) -- DONO, CLIENTE ou ADMIN
);