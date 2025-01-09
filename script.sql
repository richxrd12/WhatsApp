USE whatsapp;

CREATE TABLE usuarios (
	id int auto_increment primary key,
	nombre varchar(50),
	correo varchar(255),
	password varchar(255)
);

CREATE TABLE mensajes (
	id int auto_increment primary key,
	mensaje varchar(255),
	idDestinatario int,
	idRemitente int,
	fecha datetime,
	foreign key (idDestinatario) references usuarios(id),
	foreign key (idRemitente) references usuarios(id)
);

CREATE TABLE sesiones (
	idSesion int auto_increment primary key,
	idUsuario int,
	foreign key (idUsuario) references usuarios(id)
);

INSERT INTO usuarios (nombre, correo, password) VALUES
('Richard', 'richard@gmail.com', '1234'),
('Betsaida', 'betsaida@gmail.com', '1234'),
('Luca', 'luca@gmail.com', '1234'),
('Víctor', 'victor@gmail.com', '1234'),
('Saúl', 'saul@gmail.com', '1234'),
('Álvaro', 'alvaro@gmail.com', '1234'),
('Óscar', 'oscar@gmail.com', '1234'),
('Javier', 'javier@gmail.com', '1234'),
('Eliazar', 'eliazar@gmail.com', '1234'),
('Lin', 'lin@gmail.com', '1234'),
('Jazael', 'jazael@gmail.com', '1234'),
('Daniel', 'daniel@gmail.com', '1234'),
('Antonio', 'antonio@gmail.com', '1234'),
('Enrique', 'enrique@gmail.com', '1234'),
('Ángel', 'angel@gmail.com', '1234'),
('Flavio', 'flavio@gmail.com', '1234'),
('Rubén', 'ruben@gmail.com', '1234');
