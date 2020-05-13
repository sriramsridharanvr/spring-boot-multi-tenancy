# Multi Tenancy - Schema Per Tenant Strategy

This project illustrates the implementation of the Schema-Per-Tenant multi tenancy strategy using Spring Boot, JPA with PostgreSQL.

## Running this project

Clone this repository, then

    cd mt-schema-per-tenant-strategy
    .\mvnw spring-boot:run

## Setting up the database

As mentioned above, this example uses PostgreSQL. The Database name is `mt_todos` (change it to something that makes sense for you).

For each tenant, you need to create a schema in your database

    CREATE SCHEMA <tenant_name> AUTHORIZATION <db_username>;

For example:

    CREATE SCHEMA t1 AUTHORIZATION postgres;

Now, create tables and sequences in the schema as follows:

    CREATE SEQUENCE T1.hibernate_sequence
        INCREMENT BY 1
        MINVALUE 1
        MAXVALUE 9223372036854775807
        CACHE 1
        NO CYCLE;

    CREATE TABLE T2.todos (
        id int8 NOT NULL,
        tenant_id varchar(255) NULL,
        todo_id varchar(255) NULL,
        "text" varchar(500) NULL,
        completed bool NULL,
        CONSTRAINT todos_pk PRIMARY KEY (id),
        CONSTRAINT todos_un UNIQUE (todo_id)
    );

## APIs

Just pass the tenant ID in the `X-TENANT-ID` header.

    POST /api/todos
    X-TENANT-ID: t1

    {
        "text":"Implement a multi tenancy strategy with Spring Boot and JPA"
    }

## Postman Collection

Please refer to the `multi-tenant-app-postman-tests` for the Postman collection to test this example.
