
Requisitos tener instalado docker

Ejecutar este comando en la terminal

* docker run --name almacen -p 5436:5432 -e POSTGRES_PASSWORD=password -d postgres:13.5


primero ejecutar

* resources/db/migration/V1.001__init.sql


luego en la terminal dentro del folder del proyecto ejecutar.
* mvn install

y luego se podrá ejecutar