USE whatsapp;

CREATE TABLE usuarios (
	id int auto_increment primary key,
	nombre varchar(50),
	estado varchar(255),
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

INSERT INTO usuarios (nombre, estado, correo, password) VALUES
('Richard', 'Im not God, but I have her on her knees', 'richard@gmail.com', '1234'),
('Betsaida', '¿No? ¿No?', 'betsaida@gmail.com', '1234'),
('Luca', 'Teta', 'luca@gmail.com', '1234'),
('Víctor', 'Me gusta el zumito', 'victor@gmail.com', '1234'),
('Saúl', ':D', 'saul@gmail.com', '1234'),
('Álvaro', 'Autobús', 'alvaro@gmail.com', '1234'),
('Óscar', 'Cogito ergo sum', 'oscar@gmail.com', '1234'),
('Javier', 'Disturbing the peaceeeee', 'javier@gmail.com', '1234'),
('Eliazar', 'Perdí la guagua profe', 'eliazar@gmail.com', '1234'),
('Lin', 'Me estoy sacando el B1 profe', 'lin@gmail.com', '1234'),
('Jazael', ':|', 'jazael@gmail.com', '1234'),
('Daniel', 'De autobuses no sé, de guaguas sí hay huelga', 'daniel@gmail.com', '1234'),
('Antonio', 'Antooooonio', 'antonio@gmail.com', '1234'),
('Enrique', ':|', 'enrique@gmail.com', '1234'),
('Ángel', 'Que fue jefa', 'angel@gmail.com', '1234'),
('Flavio', 'En verdad me llamo Fabio', 'flavio@gmail.com', '1234'),
('Edu', 'Delegado el que tengo aquí colgado', 'edu@gmail.com', '1234');
