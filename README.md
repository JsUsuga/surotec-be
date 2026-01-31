📘 Documentación de la API
Esta API expone un conjunto de endpoints REST para la gestión de usuarios, estudiantes, cohortes académicas, empleados, roles, proyectos académicos, noticias y donaciones. Está documentada mediante OpenAPI 3.0 y puede explorarse usando Swagger.
________________________________________
🚀 Información General
•	Versión OpenAPI: 3.0
•	Base URL: http://localhost:8086
•	Formato: JSON
•	Arquitectura: REST
La documentación interactiva se encuentra disponible en:
/v3/api-docs
________________________________________
📂 Módulos y Endpoints
🧑‍🏫 Cohort Controller
Operaciones relacionadas con las cohortes académicas.
Método	Endpoint	Descripción
GET	/api/cohorts	Obtener todas las cohortes
GET	/api/cohorts/{id}	Obtener una cohorte por ID
POST	/api/cohorts	Crear una nueva cohorte
PUT	/api/cohorts/{id}	Actualizar una cohorte existente
DELETE	/api/cohorts/{id}	Eliminar una cohorte por ID
________________________________________
👤 User Controller
Gestión de usuarios del sistema.
Método	Endpoint	Descripción
GET	/users	Obtener todos los usuarios
GET	/users/{idUser}	Obtener un usuario por ID
POST	/users	Crear un nuevo usuario
PUT	/users/{idUser}	Actualizar un usuario existente
DELETE	/users/{idUser}	Eliminar un usuario por ID
POST	/users/login	Autenticación de usuario
GET	/users/status	Obtener usuarios por estado
________________________________________
🎓 Student Controller
Gestión de estudiantes.
Método	Endpoint	Descripción
GET	/students	Obtener todos los estudiantes
GET	/students/{idStudent}	Obtener un estudiante por ID
POST	/students	Crear un nuevo estudiante
PUT	/students/{idStudent}	Actualizar un estudiante
DELETE	/students/{idStudent}	Eliminar un estudiante
GET	/students/status	Obtener estudiantes por estado
________________________________________
🛡️ Roles Controller
Gestión de roles del sistema.
Método	Endpoint	Descripción
GET	/api/v1/roles	Obtener todos los roles
GET	/api/v1/roles/{id}	Obtener un rol por ID
POST	/api/v1/roles	Crear un rol
PUT	/api/v1/roles/{id}	Actualizar un rol
DELETE	/api/v1/roles/{id}	Eliminar un rol
________________________________________
📚 Academy Project Controller
Gestión de proyectos académicos.
Método	Endpoint	Descripción
GET	/api/projects	Obtener todos los proyectos
GET	/api/projects/{id}	Obtener un proyecto por ID
POST	/api/projects	Crear un proyecto académico
PUT	/api/projects/{id}	Actualizar un proyecto
DELETE	/api/projects/{id}	Eliminar un proyecto
________________________________________
📰 News Controller
Gestión de noticias.
Método	Endpoint	Descripción
POST	/api/news	Crear una noticia
GET	/api/news/{id}	Obtener una noticia por ID
PUT	/api/news/{id}	Actualizar una noticia
DELETE	/api/news/{id}	Eliminar una noticia
GET	/api/news/status/{status}	Obtener noticias por estado
GET	/api/news/employee/{employeeId}	Obtener noticias por empleado
________________________________________
🧑‍💼 Employee Controller
Gestión de empleados.
Método	Endpoint	Descripción
GET	/api/employees	Obtener todos los empleados
GET	/api/employees/{id}	Obtener un empleado por ID
POST	/api/employees	Crear un empleado
PUT	/api/employees/{id}	Actualizar un empleado
DELETE	/api/employees/{id}	Eliminar un empleado
________________________________________
💰 Donation Controller
Gestión de donaciones.
Método	Endpoint	Descripción
GET	/donations	Obtener todas las donaciones
GET	/donations/{idDonation}	Obtener una donación por ID
POST	/donations	Crear una donación
DELETE	/donations/{idDonation}	Eliminar una donación
________________________________________
🔗 Employee Role Controller
Asignación de roles a empleados.
Método	Endpoint	Descripción
POST	/api/v1/employees/{employeeId}/roles/{roleId}	Asignar rol a empleado
DELETE	/api/v1/employees/{employeeId}/roles/{roleId}	Remover rol de empleado
________________________________________
📦 Schemas (Modelos de Datos)
Los siguientes DTOs y entidades son utilizados en la API:
•	UserDto
•	UserCreatedDto
•	UserEntity
•	StudentDto
•	RolesDto
•	EmployeeDto
•	CohortDto
•	AcademyProjectDto
•	AcademyProjectCreatedDto
•	NewsDto
•	NewsCreatedDto
•	DonationDto
•	DonationCreateDto
•	DonationCreatedDto
________________________________________
✅ Notas Finales
•	Todos los endpoints trabajan con JSON.
•	Los IDs se envían como path variables.
•	Se recomienda usar Swagger para probar los endpoints.
•	La API puede extenderse fácilmente siguiendo la estructura actual de controladores y DTOs.
________________________________________
📌 Documento pensado para ser usado como README.md del proyecto.
