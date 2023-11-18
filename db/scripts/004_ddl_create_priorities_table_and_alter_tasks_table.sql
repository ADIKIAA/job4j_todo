CREATE TABLE priorities
(
   id       SERIAL PRIMARY KEY,
   name     TEXT UNIQUE NOT NULL,
   position int
);

INSERT INTO priorities (name, position) VALUES ('Срочно', 1);
INSERT INTO priorities (name, position) VALUES ('Нормально', 2);

ALTER TABLE tasks ADD COLUMN priority_id int REFERENCES priorities(id);