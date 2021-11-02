CREATE TABLE IF NOT EXISTS account(
    user_id varchar primary key,
    username varchar(50) UNIQUE  NOT NULL,
    country varchar(128) NOT NULL
)