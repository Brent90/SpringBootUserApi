
# docker run -d --name mysql-rest-container -e MYSQL_ROOT_PASSWORD=password -p 3306:3306 -v /home/slinger/Programming/SQL_Backup/SpringBootUserAPI_SQL_Backup:/var/lib/mysql mysql:5.7.22

# connect to mysql and run as root user
#Create Databases
CREATE DATABASE dev;
CREATE DATABASE prod;

#Create database service accounts
CREATE USER 'dev_user'@'localhost' IDENTIFIED BY 'password';
CREATE USER 'prod_user'@'localhost' IDENTIFIED BY 'password';
CREATE USER 'dev_user'@'%' IDENTIFIED BY 'password';
CREATE USER 'prod_user'@'%' IDENTIFIED BY 'password';

#Database grants
GRANT SELECT ON dev.* to 'dev_user'@'localhost';
GRANT INSERT ON dev.* to 'dev_user'@'localhost';
GRANT DELETE ON dev.* to 'dev_user'@'localhost';
GRANT UPDATE ON dev.* to 'dev_user'@'localhost';

GRANT SELECT ON prod.* to 'prod_user'@'localhost';
GRANT INSERT ON prod.* to 'prod_user'@'localhost';
GRANT DELETE ON prod.* to 'prod_user'@'localhost';
GRANT UPDATE ON prod.* to 'prod_user'@'localhost';

GRANT SELECT ON dev.* to 'dev_user'@'%';
GRANT INSERT ON dev.* to 'dev_user'@'%';
GRANT DELETE ON dev.* to 'dev_user'@'%';
GRANT UPDATE ON dev.* to 'dev_user'@'%';

GRANT SELECT ON prod.* to 'prod_user'@'%';
GRANT INSERT ON prod.* to 'prod_user'@'%';
GRANT DELETE ON prod.* to 'prod_user'@'%';
GRANT UPDATE ON prod.* to 'prod_user'@'%';
