CREATE USER hotels_admin PASSWORD 'hotels_admin';
CREATE DATABASE hotels_db OWNER hotels_admin ENCODING 'UTF8' LC_COLLATE ='ru_RU.utf8' LC_CTYPE ='ru_RU.utf8' TEMPLATE ='template0' CONNECTION LIMIT 10;
GRANT ALL PRIVILEGES ON DATABASE hotels_db TO hotels_admin;