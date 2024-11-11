<?php
// Conexión a la base de datos
$servername = "localhost";  // Cambiar si tu servidor de base de datos está en otro lugar
$username = "root";         // Cambia este usuario si no usas el predeterminado
$password = "";             // La contraseña de tu servidor MySQL (en XAMPP es generalmente vacía por defecto)
$dbname = "test";  // Cambia por el nombre de tu base de datos

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);

// Verificar la conexión
if ($conn->connect_error) {
    die(json_encode(array("success" => false, "message" => "Conexión fallida: " . $conn->connect_error)));
}

// Obtener los datos del cuerpo de la solicitud (JSON)
$data = json_decode(file_get_contents("php://input"), true);
$new_password = $data['new_pwd'];
$user = $data['user'];

// Validar que los datos no estén vacíos
if (empty($new_password) || empty($user)) {
    echo json_encode(array("success" => false, "message" => "Faltan datos"));
    exit();
}

// Hashear la nueva contraseña para mayor seguridad (opcional pero recomendado)
$hashed_password = password_hash($new_password, PASSWORD_DEFAULT);

// Preparar la consulta para actualizar la contraseña
$sql = "UPDATE users SET pwd = ? WHERE user = ?";

// Usar prepared statements para evitar inyecciones SQL
$stmt = $conn->prepare($sql);
if ($stmt) {
    $stmt->bind_param("ss", $hashed_password, $user);
    
    // Ejecutar la consulta
    if ($stmt->execute()) {
        echo json_encode(array("success" => true, "message" => "Contraseña actualizada exitosamente"));
    } else {
        echo json_encode(array("success" => false, "message" => "Error al actualizar la contraseña"));
    }

    $stmt->close();
} else {
    echo json_encode(array("success" => false, "message" => "Error en la preparación de la consulta"));
}

// Cerrar la conexión
$conn->close();
?>
