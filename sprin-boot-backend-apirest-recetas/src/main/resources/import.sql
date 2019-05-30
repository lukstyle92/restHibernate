INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES ('lucas','$2a$10$C3Uln5uqnzx/GswADURJGOIdBqYrly9731fnwKDaUdBkt/M3qvtLq',1, 'Lucas', 'Moreda','lucas_19_92@hotmail.com');
INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES ('admin','$2a$10$RmdEsvEfhI7Rcm9f/uZXPebZVCcPC7ZXZwV51efAvMAp1rIaRAfPK',1, 'John', 'Doe','jhon.doe@bolsadeideas.com');

INSERT INTO `roles` (nombre) VALUES ('ROLE_USER');
INSERT INTO `roles` (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 1);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 2);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 1);

INSERT INTO `categorias` (nombre) VALUES ('Mejicana');
INSERT INTO `categorias` (nombre) VALUES ('Mejicana');
INSERT INTO `categorias` (nombre) VALUES ('Española');
INSERT INTO `categorias` (nombre) VALUES ('Oriental');
INSERT INTO `categorias` (nombre) VALUES ('Mediterranea');
INSERT INTO `categorias` (nombre) VALUES ('Tradicional');
INSERT INTO `categorias` (nombre) VALUES ('Postres');
INSERT INTO `categorias` (nombre) VALUES ('Comidas');
INSERT INTO `categorias` (nombre) VALUES ('Desayunos');
INSERT INTO `categorias` (nombre) VALUES ('Italiana');
INSERT INTO `categorias` (nombre) VALUES ('Bajo en calorías');
INSERT INTO `categorias` (nombre) VALUES ('Dulce');
INSERT INTO `categorias` (nombre) VALUES ('Salado');
INSERT INTO `categorias` (nombre) VALUES ('Pescado');
INSERT INTO `categorias` (nombre) VALUES ('Carne');

INSERT INTO `recetas` (create_at, comensales, dificultad, ingredientes, nombre, path, preparacion, tipo) VALUES (12312432, 1, 'facil', 'patata', 'patata al horno', '', 'preparacion asi y asi', 'cena');

INSERT INTO `recetas_categorias` (receta_id, categoria_id) VALUES (1, 1);
INSERT INTO `recetas_categorias` (receta_id, categoria_id) VALUES (1, 2);
INSERT INTO `recetas_categorias` (receta_id, categoria_id) VALUES (1, 3);
INSERT INTO `recetas_categorias` (receta_id, categoria_id) VALUES (1, 4);
INSERT INTO `recetas_categorias` (receta_id, categoria_id) VALUES (1, 5);
INSERT INTO `recetas_categorias` (receta_id, categoria_id) VALUES (1, 6);
INSERT INTO `recetas_categorias` (receta_id, categoria_id) VALUES (1, 7);
INSERT INTO `recetas_categorias` (receta_id, categoria_id) VALUES (1, 8);
INSERT INTO `recetas_categorias` (receta_id, categoria_id) VALUES (1, 9);
INSERT INTO `recetas_categorias` (receta_id, categoria_id) VALUES (1, 10);