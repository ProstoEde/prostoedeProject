CREATE TABLE users (
	id serial NOT NULL,
	name text NULL,
	age int4 NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);