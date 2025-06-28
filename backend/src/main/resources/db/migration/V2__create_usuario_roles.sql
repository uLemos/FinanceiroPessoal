CREATE TABLE usuario_roles (
    usuario_id BIGINT NOT NULL,
    role VARCHAR(50) NOT NULL,
    CONSTRAINT pk_usuario_roles PRIMARY KEY (usuario_id, role),
    CONSTRAINT fk_usuario_roles_usuario FOREIGN KEY (usuario_id)
        REFERENCES usuario(id) ON DELETE CASCADE
);