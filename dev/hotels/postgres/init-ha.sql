CREATE USER ha_admin PASSWORD 'ha_admin_pass';
CREATE DATABASE hotels_aggregator_db OWNER ha_admin ENCODING 'UTF8' LC_COLLATE ='ru_RU.utf8' LC_CTYPE ='ru_RU.utf8' TEMPLATE ='template0' CONNECTION LIMIT 10;
GRANT ALL PRIVILEGES ON DATABASE hotels_aggregator_db TO ha_admin;