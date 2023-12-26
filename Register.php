<?php
$con = mysqli_connect("localhost", "root", "", "usuariosapp");

// Verificar la conexión a la base de datos
if ($con === false) {
    die('Error de conexión: ' . mysqli_connect_error());
}

// Verificar la presencia de los parámetros POST
if (!isset($_POST["name"], $_POST["edad"], $_POST["username"], $_POST["password"])) {
    $response = array();
    $response["success"] = false;
    $response["message"] = "Algunos parámetros están ausentes.";
    echo json_encode($response);
    exit;
}

// Escapar y validar los datos POST
$name = mysqli_real_escape_string($con, $_POST["name"]);
$edad = mysqli_real_escape_string($con, $_POST["edad"]);
$username = mysqli_real_escape_string($con, $_POST["username"]);
$password = mysqli_real_escape_string($con, $_POST["password"]);

// Preparar la declaración SQL
$statement = mysqli_prepare($con, "INSERT INTO user (name, username, edad, password) VALUES (?,?,?,?)");

// Verificar la preparación de la declaración
if ($statement === false) {
    $response = array();
    $response["success"] = false;
    $response["message"] = "Error en la preparación de la declaración: " . mysqli_error($con);
    echo json_encode($response);
    exit;
}

// Vincular parámetros y ejecutar la declaración
mysqli_stmt_bind_param($statement, "ssis", $name, $username, $edad, $password);

// Verificar la ejecución de la declaración
if (!mysqli_stmt_execute($statement)) {
    $response = array();
    $response["success"] = false;
    $response["message"] = "Error al ejecutar la declaración: " . mysqli_error($con);
    echo json_encode($response);
    exit;
}

// Éxito en la inserción
$response = array();
$response["success"] = true;
$response["message"] = "Usuario registrado exitosamente.";
echo json_encode($response);

// Cerrar la conexión
mysqli_close($con);
?>