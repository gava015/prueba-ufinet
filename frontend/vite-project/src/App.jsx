import { useState } from "react";
import "./App.css";

function App() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [mensaje, setMensaje] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMensaje("Conectando...");

    try {
      console.log("Enviando request a:", "http://localhost:8080/login");
      console.log("Datos:", { username, password });

      const response = await fetch("http://localhost:8080/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }),
      });

      console.log("Response status:", response.status);
      console.log("Response ok:", response.ok);

      if (response.ok) {
        // ✅ Obtener los datos de la respuesta
        const data = await response.json();
        console.log("Datos recibidos:", data);
        
        // Guardar datos en localStorage
        localStorage.setItem('userId', data.userId);
        localStorage.setItem('username', data.username);
        
        setMensaje("✅ Login exitoso, redirigiendo...");
        
        // Redirigir a la página de gestión de autos
        setTimeout(() => {
          window.location.href = "/cars";
        }, 500);
        
      } else {
        const data = await response.json();
        console.log("Error del servidor:", data);
        setMensaje(data.message || "❌ Usuario o contraseña incorrectos");
      }
    } catch (error) {
      console.error("Error completo:", error);
      setMensaje(`⚠️ Error: ${error.message}. Verifica que el backend esté corriendo en http://localhost:8080`);
    }
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <h1 className="title">Iniciar Sesión</h1>
        <form onSubmit={handleSubmit} className="login-form">
          <input
            type="text"
            placeholder="Nombre de usuario"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
          <input
            type="password"
            placeholder="Contraseña"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <button type="submit">Ingresar</button>
        </form>

        {/* Mensaje de respuesta */}
        {mensaje && <p className="respuesta">{mensaje}</p>}

        <p className="register-text">
          ¿No tienes cuenta? <a href="#">Regístrate</a>
        </p>
      </div>
    </div>
  );
}

export default App;