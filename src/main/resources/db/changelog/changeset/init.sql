--liquibase formatted sql

-- changeset YuriiD:1
-- comment: Changeset for creating init structure


-- Create the Currency table
CREATE TABLE currency
(
    id   SERIAL PRIMARY KEY,
    code VARCHAR(3) NOT NULL UNIQUE,
    name VARCHAR(255)
);

INSERT INTO currency (code, name)
VALUES ('USD', 'United States Dollar');
INSERT INTO currency (code, name)
VALUES ('EUR', 'Euro');

CREATE TABLE currency_rate_history
(
    id          SERIAL PRIMARY KEY,
    time        timestamp NOT NULL,
    rate        decimal   NOT NULL,
    currency_id int       NOT NULL
);

ALTER TABLE currency_rate_history
    ADD CONSTRAINT fk_currency
        FOREIGN KEY (currency_id)
            REFERENCES currency (id);

