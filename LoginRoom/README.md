## INTERFAZ
<img src="https://github.com/user-attachments/assets/b437af99-614b-4657-ae55-77284e19f466" width="170"/>
<img src="https://github.com/user-attachments/assets/f604dc28-f862-467d-a51e-ed54fd7c4874" width="170"/>
<img src="https://github.com/user-attachments/assets/f83c1f1b-ab1a-4507-ad49-0304917fc866" width="170"/>
<img src="https://github.com/user-attachments/assets/2367925a-05e8-4089-8f87-29d1f314001e"/>


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
