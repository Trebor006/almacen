create table if not exists solicitud
(
    id                  SERIAL primary key,
    titulo              varchar(256) not null,
    descripcion         varchar(256) not null,
    estado              varchar(256) not null,
    fecha               date         not null,
    id_registro_externo int          not null,
    proceso_origen      varchar(256) not null
);

