## DESCRIPCIÓN
Esta es una aplicación de inicio de sesión desarrollada en Android que utiliza la biblioteca Room para la gestión de una base de datos local. La aplicación permite a los usuarios registrarse y autenticarse mediante un nombre de usuario y contraseña.

## CARACTERÍSTICAS
  - **Registro de Usuario**: Los nuevos usuarios pueden registrarse y almacenar sus credenciales de forma segura en la base de datos local.
  - **Inicio de Sesión**: Los usuarios existentes pueden iniciar sesión utilizando su nombre de usuario y contraseña.
  - **Base de Datos Local**: Utiliza Room para gestionar la base de datos, lo que facilita el almacenamiento y recuperación de datos.
  - **Interfaz de Usuario Intuitiva**: Diseño sencillo y fácil de usar para mejorar la experiencia del usuario.
  - **Seguridad**: No admite contraseñas menores o igual a 8 caracteres.
    
## TECNOLOGÍAS UTILIZADAS
  - **Kotlin**: Lenguaje de programación utilizado para el desarrollo de la aplicación.
  - **Jetpack Compose**: Conjunto de bibliotecas que incluye Room para la gestión de bases de datos.
  - **Corutinas**: Para realizar operaciones de base de datos de manera asíncrona sin bloquear la interfaz de usuario.

## CÓMO IMPLEMENTARLO
1. Abre tu archivo build.gradle (nivel de módulo) y agrega las siguientes dependencias dentro del bloque dependencies:
```
dependencies {
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
}
```
2. Si estás usando Kotlin, asegúrate de aplicar el plugin de KAPT en la parte superior de tu archivo build.gradle:
```
plugins {
    kotlin("kapt")
}
```
3. Después de agregar las dependencias, sincroniza tu proyecto para que Gradle descargue las bibliotecas necesarias.
