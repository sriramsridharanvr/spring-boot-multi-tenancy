# Multi Tenancy - Configurable strategy

This project illustrates the implementation of multi-tenancy using Spring Boot, JPA (Hibernate) with PostgreSQL, which can be configured to use the ``discriminator`` strategy, or the ``schema-per-tenant`` strategy.

## How it works
This project allows you to choose which multi tenant strategy you want to implement in the property file, at the time of App startup.

Just change the ``multi-tenant-strategy`` property value in the ``application.properties`` file, as follows

    #For Schema per Tenant
    multi-tenant-strategy=schema

    #For Discriminator strategy
    multi-tenant-strategy=discriminator

This value defaults to ``discriminator``

If you choose the schema strategy, then hibernate will connect to the specific schema in hibernate, identified by the tenant ID (passed in ``X-TENANT-ID`` header).

For discriminator strategy, the ``public`` schema will be used.

Please note, that ``ddl-auto=create`` has no effect in this project. All DDLs and factory shipped data have to be pumped in manually


## Running this project

Clone this repository, then

    cd mt-schema-per-tenant-strategy
    .\mvnw spring-boot:run

## Setting up the database (for discriminator strategy)

As mentioned above, this example uses PostgreSQL. The Database name is `mt_todos` (change it to something that makes sense for you).

Now, create tables and sequences in the ``public`` schema as follows:

    CREATE SEQUENCE public.hibernate_sequence
        INCREMENT BY 1
        MINVALUE 1
        MAXVALUE 9223372036854775807
        CACHE 1
        NO CYCLE;

    CREATE TABLE public.todos (
        id int8 NOT NULL,
        tenant_id varchar(255) NULL,
        todo_id varchar(255) NULL,
        "text" varchar(500) NULL,
        completed bool NULL,
        CONSTRAINT todos_pk PRIMARY KEY (id),
        CONSTRAINT todos_un UNIQUE (todo_id)
    );

## Setting up the database (for Schema per Tenant strategy)

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
