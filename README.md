# GiftCards

## Tabla de contenido
1. [Información general](#información-general)
2. [Tecnologías](#tecnologías)
3. [Instalación](#instalación)
    * [Gradle](#gradle)
    * [Descargar proyecto "gift-card" del repositorio de código fuente](#descargar-proyecto-gift-card-del-repositorio-de-código-fuente)
    * [Entorno de desarrollo Eclipse IDE](#entorno-de-desarrollo-eclipse-ide)
    * [Entorno de desarrollo IntelliJ IDEA](#entorno-de-desarrollo-intellij-idea)
4. [Endpoints](#endpoints)
4. [Más información](#más-información)

## Información general
Este proyecto es una aplicaciòn para administar tarjetas de regalo (gift cards), permite autenticar usuarios y enviar notificaciones.
Este proyecto es una aplicación desarrollada con Java 17 y Spring Boot, que incluye seguridad con JWT y un sistema de autenticación basado en Spring Security.

El proyecto está basado en DDD (Domain Driven Design), es decir, el dominio es el centro del proyecto cumpliendo con el principio de [arquitectura hexagonal](https://refactorizando.com/ejemplo-de-arquitectura-hexagonal/).
El proyecto tiene la siguiente estructura:
1. **application:** este es el módulo principal y desde el cual se arranca la aplicación. No tiene lógica de negocio y solo se encarga de arrancar la aplicación como tal y definir beans necesarios para ello.
    * *use-case:* En este módulo encontraremos los casos de uso de nuestra aplicación.
2. **domain:**
    * *model:* En este módulo encontremos nuestras entidades de dominio (clases), junto con sus gateways o puertos (interfaces que representan por ejemplo, repositorios de dominio ). No se debe utilizar implementaciones o librerías que no tengan nada que ver con el dominio (se hace la excepción con LOMBOK pues nos ayuda a reducir la cantidad de código copiado para la creación de getters, setters, builders, etc. ).
    * *services:* En este módulo encontraremos los servicios de nuestra aplicación. Este módulo sigue siendo de dominio y cumple con la misma condición de solo usar notaciones de lombok. En este módulo, se usan los gateways de dominio para llevar a cabo la lógicaque se debe implementar para dar solución al problema planteado.
    * *exception:* Se crea excepciones de negocio o dominio que son las que se utilizan para responder a los errores de lógica.
3. **infraestructure:** 
    * *drivenadapters:* En este módulo está toda la lógica de JPA. Dentro de este módulo, se hace la implementación de un gateway de dominio. Este módulo es de infraestructura y por tal motivo, se pueden usar las librerías que se necesiten para realizar la implementación. Para este caso en específico, se usan librerías de JPA, ya que la implementación del gateway de dominio, está dado por accesos a bases de datos. La base de datos se configura con JPA y se define desde el archivo de propiedades application.yml la cual es una base de datos H2, es decir, los datos persisten en memoria, todo esto debido a que la información que persiste es poca.
    * *entrypoints.controllers*: Este módulo representa un entry point (o punto de entrada) de tipo web para nuestra aplicación, es decir, donde están las definiciones para que nuestra aplicación reciba peticiones a través de HTTP. Este módulo es de infraestructura  y por tal motivo, se usan librerías para poder crear Controllers, PostMappings, etc.
    * *security*: Este módulo incluye la lògica necesaria para configuración de Spring Security, filters y demás elementos necesarios para generar un token de autenticación.

Ver más sobre [arquitecturas limpias](https://www.youtube.com/watch?v=y3MWfPDmVqo).

Se hace uso de la libreria de swagger para documentar todo aquello que sea relevante dentro del proyecto.

## Tecnologías
Lista de tecnologías utilizadas:
* [Java](https://www.java.com/es/download/ie_manual.jsp)
* Spring Boot 3
* Spring Security con JWT
* [Gradle](https://gradle.org/releases/)
* [Eclipse IDE](https://www.eclipse.org/) o [IntelliJ IDEA](https://www.jetbrains.com/es-es/idea/)
* [Lombok](https://projectlombok.org/)
* [Swagger](https://swagger.io/)
* Spring Data JPA
* Base de datos H2
* [Git](https://git-scm.com/)
* [Jasypt](http://www.jasypt.org/)

## Instalación

### Prerrequisitos
Antes de ejecutar el proyecto, asegúrate de tener instalado lo siguiente:

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)  
- [Gradle](https://gradle.org/install/)  

Para verificar la instalación de Java y Maven, ejecuta:
    ```
java -version
gradle -version   
   
### Descargar proyecto "gift-card" del repositorio de código fuente
Ejecutar los siguientes comandos a través de la consola:
```
$ git clone [https://github.com/afavendanor/gift-card.git](https://github.com/afavendanor/gift-cards-api.git)
$ cd ../<<carpeta_del_proyecto>>
$ gradle clean build
```

### Entorno de desarrollo Eclipse IDE
Descargar e instalar [Eclipse IDE for Enterprise Java Developers](https://www.eclipse.org/downloads/download.php?file=/oomph/epp/2020-12/R/eclipse-inst-jre-win64.exe&mirror_id=576), realizar las siguientes configuraciones:
1. Abrir Eclipse IDE e instalar en plugin de Spring Tools 4, ```Help->Marketplace e instalar "spring tools"```
2. Instalar el plugin de [Lombok](https://projectlombok.org/downloads/lombok.jar) para Eclipse IDE,
   las instrucciones de instalación están [aquí](https://projectlombok.org/setup/eclipse).
3. Desde Eclipse IDE importar el proyecto. ```File->Import->Gradle->Existing Gradle Proyect```.
4. Ejecutar proyecto  ```Clic derecho en el proyecto y Run as->spring boot app``` desde Eclipse IDE

### Entorno de desarrollo Intellij IDEA
Descargar e instalar [IntelliJ IDEA](https://www.jetbrains.com/es-es/idea/download/#section=windows), puede ser la versión **Community Edition** y realizar las siguientes configuraciones:
1. Verificar si se tiene instalado el PLUGIN de Gradle para el Intellij y si no se tiene, instalarlo (Files -> Settings -> Plugins)
2. Verificar si se tiene instalado el PLUGIN de Lombok para el Intellij y si no se tiene, instalarlo (Files -> Settings -> Plugins)
3. Descargar el repositorio.
4. Abrir nuestro proyecto en Intellij Idea.
5. Dirigirse a derecha superior, donde se podrá ver una pequeña pestaña de Gradle. Abrirla y presionar en el símbolo de refrescar. Esto con la intención de que se descarguen todas las dependencias necesarias.
6. Una vez todo esto listo, en la pestaña del punto 5, nos dirigimos a Tasks -> application -> damos click derecho en bootRun y presionamos en RUN.

### Por consola de comandos
Una vez ubicado en el proyecto y tener instalado Graadle se puede ejecutar los siguientes comandos en la consola ubicados en la raiz del proyecto:
```
$ gradle clean build
$ gradle bootRun
```
Una vez inicia la aplicación como tiene una base de datos en memoria se precargan datos de usuario y de gift-cards configurado en el archivo ```./gift-cards-api/src/main/java/co/com/project/infraestructure/dataloader/DataLoader.java```, todo esto en una base de datos H2 que se configura en el archivo en el archivo ```./gift-cards-api/src/main/resources/application.yml```
```
  h2:
    console:
      enabled: true
      path: /h2-console
    datasource:
      driverClassName: org.h2.Driver
      url: jdbc:h2:mem:gift_cards;DB_CLOSE_ON_EXIT=FALSE
      username: sa
      password:
      initialize: true
    jpa:
      properties:
      database-platform: org.hibernate.dialect.H2Dialect
      show-sql: true
      hibernate:
        ddl-auto: create
```

## Endpoints

### Autenticación
La autenticación se realiza utilizando un token JWT. Primero debes autenticarse en el endpoint ```/login```, y luego usar el token JWT en las siguientes solicitudes.
```curl -X POST -d '{"username": "user", "password": "password"}' -H "Content-Type: application/json" http://localhost:8080/login```

Luego, usa el token en los encabezados de las siguientes solicitudes, por ejemplo:
```curl -X GET http://localhost:8080/api/gift-cards/list -H "Authorization: Bearer <your-jwt-token>"```

### Endpoints disponibles

* ```GET /health```: Verifica el estado de la API.
* User
   * ```GET /api/users/list```: Lista los usuarios registrados en el sistema.
   * ```POST /api/users/create```: Permite crear un usuario como administrador.
   * ```POST /api/users/register```: Permite crear un usuario como user, no requiere autorización.
   * ```POST /login```: Autenticación de usuarios con JWT.
* GiftCards
  * ```GET /api/gift-cards/:id```: Obtiene todas las tarjetas de regalo.
  * ```GET /api/gift-cards/list```: Obtiene todas las tarjetas de regalo
  * ```POST /api/gift-cards/create```: Crea una nueva tarjeta de regalo.
  * ```PUT /api/gift-cards/update```: Actualiza una tarjeta de regalo.
  * ```PUT /api/gift-cards/redeem```: Permite redimir una tarjeta de regalo.
  * ```DELETE /api/gift-cards/:id```: Elimina una tarjeta de regalo.

En el directorio doc/collection.json se encuentran los ejmplos de invocación a los servicios expuestos en el proyecto. Se encuentran los endpoint con los request. Para ello se debe importar en herramientas como Postman o Insomnia.

## Más información

Para mayor información Andres Avendaño (afavendanora@gmail.com)
