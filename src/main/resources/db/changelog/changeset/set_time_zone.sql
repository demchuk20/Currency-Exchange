--liquibase formatted sql

-- changeset YuriiD:2
-- comment: set timezone as Kyiv

ALTER DATABASE postgres SET timezone TO 'Europe/Kiev';
