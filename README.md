# 🚀 API de Gastos de Viaje - Jose Franco Arroyave Cardona

Esta API permite consultar y procesar la lista de gastos de viaje de empleados, aplicando las reglas de negocio definidas por Sura.

- Calcula el total de gastos globales.
- Agrupa los gastos por empleado y mes ordenada alfabéticamente por nombre.
- Determina qué gastos asume la compañía y cuáles el empleado.
- Listar los gastos por empleado

---

## 🛠️ Tecnologías usadas

- ☕ **Java 17**
- ⚡ **Spring Boot**
- 🌱 **Spring WebFlux** 
- 🧪 **JUnit 5 y Mockito**
- 📦 **Maven**

---

## 🧑‍💻 Diseño del proyecto

El proyecto fue desarrollado siguiendo un **diseño por capas** para una mejor separación de responsabilidades:

- **Router:** Define las rutas de la API y las asocia a sus handlers.
- **Handler:** Contiene la lógica de negocio reactiva para cada endpoint.
- **Service:** Provee los datos y encapsula la lógica de acceso a ellos (en este caso, datos en memoria).

Este enfoque permite un código más modular, mantenible y fácil de probar.

---

## 📂 Endpoints disponibles

### ✅ Obtener el total global de gastos
Devuelve la suma total de todos los gastos registrados.

`GET /gastosTotal`

### ✅ Obtener el total global de gastos
Devuelve la lista de empleados ordenada alfabéticamente por nombre, sus gastos mensuales y si la compañía los asume.

`GET /empleados/`

📖 **Descripción:**  

### ✅ Obtener los gastos de un empleado específico
Devuelve los gastos registrados para un empleado específico, identificándolo por su id.

`GET /empleados/{id}`

# 🚀 Ejecutar el proyecto
git clone https://github.com/franco-arroyave/inteview.gastos_api.git
cd gastos-api

# Construir el proyecto
mvn clean install

# Levantar la API en localhost:8080
mvn spring-boot:run

# 🧪 Ejecutar las pruebas unitarias
mvn test