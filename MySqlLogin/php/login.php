<?php
// Configuración de la conexión a la base de datos
$host = "localhost"; 
$dbname = "test"; 
$username = "root"; 
$password = ""; 

// Crear la conexión
$conn = new mysqli($host, $username, $password, $dbname);

// Verificar la conexión
if ($conn->connect_error) {
    die("Error en la conexión: " . $conn->connect_error);
}

// Obtener los datos enviados desde la aplicación
$user = $_POST['user'] ?? '';
$pwd = $_POST['pwd'] ?? '';

// Validar que los datos no estén vacíos
if (empty($user) || empty($pwd)) {
    echo json_encode(array("success" => false, "message" => "Por favor, completa todos los campos"));
    exit();
}

// Consultar la base de datos para verificar el usuario y la contraseña
$sql = "SELECT * FROM users WHERE user = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $user);
$stmt->execute();
$result = $stmt->get_result();

// Verificar si el usuario existe
if ($result->num_rows > 0) {
    $row = $result->fetch_assoc();

    // Comparar la contraseña ingresada con la almacenada en la base de datos
    if (password_verify($pwd, $row['pwd'])) {
        // Contraseña correcta, devolver éxito
        echo json_encode(array("success" => true, "message" => "Bienvenido, " . $row['user']));
    } else {
        // Contraseña incorrecta
        echo json_encode(array("success" => false, "message" => "Contraseña incorrecta"));
    }
} else {
    // Usuario no encontrado
    echo json_encode(array("success" => false, "message" => "Usuario no encontrado"));
}

// Cerrar la conexión
$conn->close();
?>
