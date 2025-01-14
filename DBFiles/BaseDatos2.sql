-- This script was generated by a beta version of the ERD tool in pgAdmin 4.
-- Please log an issue at https://redmine.postgresql.org/projects/pgadmin4/issues/new if you find any bugs, including reproduction steps.
BEGIN;


CREATE TABLE IF NOT EXISTS microservicio.cuenta
(
    cuenta_id bigint NOT NULL,
    cuenta_estado boolean NOT NULL,
    cuenta_numero character varying(255) COLLATE pg_catalog."default",
    cuenta_saldo_inicial double precision NOT NULL,
    cuenta_tipo character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT cuenta_pkey PRIMARY KEY (cuenta_id)
);

CREATE TABLE IF NOT EXISTS microservicio.movimiento
(
    movimiento_id bigint NOT NULL,
    movimiento_fecha date,
    movimiento_saldo double precision NOT NULL,
    movimiento_tipo character varying(255) COLLATE pg_catalog."default",
    movimiento_valor double precision NOT NULL,
    cuenta_id bigint,
    CONSTRAINT movimiento_pkey PRIMARY KEY (movimiento_id)
);

ALTER TABLE IF EXISTS microservicio.movimiento
    ADD CONSTRAINT fk4ea11fe7p3xa1kwwmdgi9f2fi FOREIGN KEY (cuenta_id)
    REFERENCES microservicio.cuenta (cuenta_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

END;