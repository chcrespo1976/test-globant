-- This script was generated by a beta version of the ERD tool in pgAdmin 4.
-- Please log an issue at https://redmine.postgresql.org/projects/pgadmin4/issues/new if you find any bugs, including reproduction steps.
BEGIN;


CREATE TABLE IF NOT EXISTS microservicio.cliente
(
    "cliente_contraseña" character varying(255) COLLATE pg_catalog."default",
    cliente_estado boolean NOT NULL,
    persona_id bigint NOT NULL,
    CONSTRAINT cliente_pkey PRIMARY KEY (persona_id)
);

CREATE TABLE IF NOT EXISTS microservicio."cliente-cuenta"
(
    id bigint NOT NULL,
    cuenta_id bigint NOT NULL,
    persona_id bigint,
    CONSTRAINT "cliente-cuenta_pkey" PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS microservicio.persona
(
    persona_id bigint NOT NULL,
    persona_direccion character varying(255) COLLATE pg_catalog."default",
    persona_edad integer NOT NULL,
    persona_genero character varying(255) COLLATE pg_catalog."default",
    persona_identificacion character varying(255) COLLATE pg_catalog."default",
    persona_nombre character varying(255) COLLATE pg_catalog."default",
    persona_telefono character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT persona_pkey PRIMARY KEY (persona_id)
);

ALTER TABLE IF EXISTS microservicio.cliente
    ADD CONSTRAINT fkmc52upywn8gona2iiwwwphb92 FOREIGN KEY (persona_id)
    REFERENCES microservicio.persona (persona_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
CREATE INDEX IF NOT EXISTS cliente_pkey
    ON microservicio.cliente(persona_id);


ALTER TABLE IF EXISTS microservicio."cliente-cuenta"
    ADD CONSTRAINT fkbsyakyhfr01s51en2xc7i3lrl FOREIGN KEY (persona_id)
    REFERENCES microservicio.cliente (persona_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

END;