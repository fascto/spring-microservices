# 🚀 Proyecto Microservicios con Spring Boot

Sistema de microservicios desarrollado con **Spring Boot**, con dos microservicios principales: **usuarios** y **cursos**.  
Este proyecto fue diseñado como una demostración de arquitectura distribuida y despliegue en la nube utilizando herramientas modernas del ecosistema Java y DevOps.

---

## 📌 Descripción General

La arquitectura se compone de **dos microservicios independientes** que se comunican mediante **API REST** utilizando **Spring Cloud OpenFeign**, cada uno corriendo en un puerto específico:

- 🧑‍💼 `msvc-usuarios` → Puerto `8001`
- 📚 `msvc-cursos` → Puerto `8002`

---

## 🔄 Comunicación entre Servicios

Los servicios se integran mediante **Feign Clients**, lo que permite realizar operaciones como:

* 🔗 Asignar usuarios a cursos desde el servicio de cursos  
* 🧾 Crear usuarios desde el servicio de cursos de forma transparente

---

## 🛠️ Tecnologías Utilizadas

### 👨‍💻 Backend

* ☕ Java 17  
* ⚙️ Spring Boot `3.5.3`  
* ☁️ Spring Cloud `2025.0.0`  
* 🛢️ Spring Data JPA  
* 🔗 Spring Cloud OpenFeign  
* 📦 Maven

---

### 💾 Bases de Datos

* 🐬 **MySQL 8.0** — para el microservicio de usuarios  
* 🐘 **PostgreSQL 15** — para el microservicio de cursos

---

### ☁️ DevOps & Cloud

* 🐳 **Docker** — Contenedorización de los servicios  
* ☸️ **Kubernetes** — Orquestación de contenedores  
* 🌩️ **AWS ECS** — Despliegue en Amazon Web Services  
* 🔶 **AWS EKS** — Despliegue en Amazon Web Services

---

## ✅ Objetivos del Proyecto

* Diseñar una arquitectura de microservicios basada en Spring Boot  
* Integrar múltiples bases de datos en una solución distribuida  
* Desplegar servicios en plataformas cloud con herramientas modernas de contenedorización y orquestación

---

## 📁 Estructura del Proyecto

```bash
/project-root
│
├── msvc-usuarios      # Microservicio de gestión de usuarios
