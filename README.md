# Barber API

Sistema de gestão de agendamentos para barbearia desenvolvido com **Java** e **Spring Boot**.

## Sobre o projeto

A aplicação permite gerenciar agendamentos entre **clientes** e **barbeiros**, com um sistema de autenticação seguro utilizando **Spring Security** e **JWT**.

Foi implementado um **filtro personalizado** que intercepta as requisições para validar o token e verificar as permissões do usuário, permitindo que o sistema diferencie automaticamente barbeiros e clientes.

A lógica da aplicação foi organizada em **Use Cases**, o que ajuda a manter os **Controllers mais simples** e melhora a organização do código.

## Tecnologias utilizadas

- Java
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- PostgreSQL
