-- linguist-language=SQL

CREATE DATABASE SalonFrizerie;
USE SalonFrizerie;

CREATE TABLE Clienti (
    id int AUTO_INCREMENT PRIMARY KEY,
    nume VARCHAR(255),
    numar_telefon VARCHAR(10),
    preferinte VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Frizeri (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nume VARCHAR(100) NOT NULL,
    numar_telefon VARCHAR(20),
    tarif DOUBLE,
    specializari TEXT
);

CREATE TABLE IF NOT EXISTS Programari (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    client_id BIGINT NOT NULL,
    frizer_id BIGINT NOT NULL,
    serviciu VARCHAR(100),
    data_ora TIMESTAMP NOT NULL

);
