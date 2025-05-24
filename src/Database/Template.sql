CREATE TABLE IF NOT EXISTS users (
    id       INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    username TEXT    NOT NULL UNIQUE,
    password TEXT    NOT NULL,
    email    TEXT    NOT NULL UNIQUE,
    status   TEXT    NOT NULL
);

CREATE TABLE IF NOT EXISTS inventory (
    id       INTEGER      PRIMARY KEY AUTOINCREMENT UNIQUE,
    item     TEXT         UNIQUE NOT NULL,
    price    REAL (32, 2) NOT NULL DEFAULT (0),
    quantity INTEGER      NOT NULL DEFAULT (0),
    photo    BLOB         NOT NULL
);

CREATE TABLE IF NOT EXISTS trans_history (
    id       INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,
    item_id  INTEGER NOT NULL REFERENCES inventory (id),
    user_id  INTEGER NOT NULL REFERENCES users (id),
    quantity INTEGER NOT NULL,
    date     TEXT    NOT NULL
);

CREATE TABLE IF NOT EXISTS inventory_log (
    id      INTEGER PRIMARY KEY AUTOINCREMENT,
    item_id INTEGER NOT NULL REFERENCES inventory (id),
    date    TEXT    NOT NULL,
    log     TEXT    NOT NULL
);
