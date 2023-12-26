<?php
    $con = mysqli_connect("localhost", "root", "", "usuariosapp");

    // Verificar si los índices están presentes en $_POST
    $username = isset($_POST["username"]) ? $_POST["username"] : null;
    $password = isset($_POST["password"]) ? $_POST["password"] : null;

    // Validación básica
    if (empty($username) || empty($password)) {
        $response = array();
        $response["success"] = false;
        $response["message"] = "Nombre de usuario y contraseña son obligatorios.";
        echo json_encode($response);
        exit(); // Termina la ejecución del script
    }

    $statement = mysqli_prepare($con, "SELECT name, edad, username, password FROM user WHERE username = ?");
    mysqli_stmt_bind_param($statement, "s", $username);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);

    $response = array();
    $response["success"] = false;

    // Obtener resultados de la consulta
    mysqli_stmt_bind_result($statement, $name, $edad, $dbUsername, $dbPassword);

    if (mysqli_stmt_fetch($statement)) {
        // Verificar la contraseña
        if ($password === $dbPassword) {
            $response["success"] = true;
            $response["name"] = $name;
            $response["edad"] = $edad;
            $response["username"] = $dbUsername;
            // Puedes agregar más datos del usuario si es necesario
        } else {
            $response["message"] = "Contraseña incorrecta.";
    $response["storedPassword"] = "Contraseña almacenada: " . $dbPassword;
    $response["providedPassword"] = "Contraseña proporcionada: " . $password;
    
    error_log("Contraseña proporcionada: " . $password);
    error_log("Contraseña almacenada: " . $dbPassword);
        }
    } else {
        $response["message"] = "Usuario no encontrado.";
    }

    echo json_encode($response);

    mysqli_close($con);
?>