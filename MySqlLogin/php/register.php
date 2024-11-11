<?php
$hostname = "localhost";
$database = "test";
$username = "root";
$password = "";

$json = array();

// Leer los datos directamente desde $_POST
if (isset($_POST["user"]) && isset($_POST["pwd"])) {
    // Conexión a la base de datos
    $conexion = mysqli_connect($hostname, $username, $password, $database);

    if ($conexion) {
        $user = mysqli_real_escape_string($conexion, $_POST['user']);
        $pwd = mysqli_real_escape_string($conexion, $_POST['pwd']);

        // Cifrar la contraseña
        $hashedPwd = password_hash($pwd, PASSWORD_DEFAULT);

        // Consulta para insertar un nuevo usuario en la base de datos
        $insert = "INSERT INTO users (user, pwd) VALUES ('{$user}', '{$hashedPwd}')";

        if (mysqli_query($conexion, $insert)) {
            $json['success'] = true;
            $json['message'] = 'Usuario registrado correctamente';
        } else {
            $json['success'] = false;
            $json['message'] = 'Error al registrar el usuario: ' . mysqli_error($conexion);
        }

        mysqli_close($conexion);
    } else {
        $json['success'] = false;
        $json['message'] = 'Error al conectar a la base de datos: ' . mysqli_connect_error();
    }
} else {
    $json['success'] = false;
    $json['message'] = 'Faltan datos';
}

// Devuelve la respuesta en formato JSON
echo json_encode($json);
?>
