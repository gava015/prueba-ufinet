import { useState, useEffect } from 'react';

function CarsCRUD() {
  const [cars, setCars] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [editingCar, setEditingCar] = useState(null);
  const [formData, setFormData] = useState({
    brand: '',
    model: '',
    year: '',
    plate: '',
    color: ''
  });

  const userId = localStorage.getItem('userId'); // El ID del usuario logueado

  // Cargar autos al iniciar
  useEffect(() => {
    loadCars();
  }, []);

  // Obtener todos los autos
  const loadCars = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/cars');
      const data = await response.json();
      setCars(data);
    } catch (error) {
      console.error('Error al cargar autos:', error);
    }
  };

  // Manejar cambios en el formulario
  const handleInputChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  // Crear o actualizar auto
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const carData = {
        ...formData,
        year: parseInt(formData.year),
        user: { id: userId }
      };

      if (editingCar) {
        // Actualizar
        await fetch(`http://localhost:8080/api/cars/${editingCar.id}`, {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(carData)
        });
      } else {
        // Crear
        await fetch('http://localhost:8080/api/cars', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(carData)
        });
      }

      // Resetear y recargar
      setFormData({ brand: '', model: '', year: '', plate: '', color: '' });
      setShowForm(false);
      setEditingCar(null);
      loadCars();
    } catch (error) {
      console.error('Error al guardar auto:', error);
    }
  };

  // Editar auto
  const handleEdit = (car) => {
    setEditingCar(car);
    setFormData({
      brand: car.brand,
      model: car.model,
      year: car.year,
      plate: car.plate,
      color: car.color
    });
    setShowForm(true);
  };

  // Eliminar auto
  const handleDelete = async (id) => {
    if (window.confirm('¿Estás seguro de eliminar este auto?')) {
      try {
        await fetch(`http://localhost:8080/api/cars/${id}`, {
          method: 'DELETE'
        });
        loadCars();
      } catch (error) {
        console.error('Error al eliminar auto:', error);
      }
    }
  };

  // Cancelar edición
  const handleCancel = () => {
    setShowForm(false);
    setEditingCar(null);
    setFormData({ brand: '', model: '', year: '', plate: '', color: '' });
  };

  return (
    <div style={{ padding: '20px', maxWidth: '1200px', margin: '0 auto' }}>
      <h1>🚗 Gestión de Autos</h1>
      
      <button 
        onClick={() => setShowForm(!showForm)}
        style={{
          padding: '10px 20px',
          backgroundColor: '#007bff',
          color: 'white',
          border: 'none',
          borderRadius: '5px',
          cursor: 'pointer',
          marginBottom: '20px'
        }}
      >
        {showForm ? 'Cancelar' : '+ Agregar Auto'}
      </button>

      {/* Formulario */}
      {showForm && (
        <div style={{
          backgroundColor: '#f8f9fa',
          padding: '20px',
          borderRadius: '8px',
          marginBottom: '20px'
        }}>
          <h2>{editingCar ? 'Editar Auto' : 'Nuevo Auto'}</h2>
          <form onSubmit={handleSubmit}>
            <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '15px' }}>
              <input
                type="text"
                name="brand"
                placeholder="Marca"
                value={formData.brand}
                onChange={handleInputChange}
                required
                style={{ padding: '10px', borderRadius: '5px', border: '1px solid #ddd' }}
              />
              <input
                type="text"
                name="model"
                placeholder="Modelo"
                value={formData.model}
                onChange={handleInputChange}
                required
                style={{ padding: '10px', borderRadius: '5px', border: '1px solid #ddd' }}
              />
              <input
                type="number"
                name="year"
                placeholder="Año"
                value={formData.year}
                onChange={handleInputChange}
                required
                style={{ padding: '10px', borderRadius: '5px', border: '1px solid #ddd' }}
              />
              <input
                type="text"
                name="plate"
                placeholder="Placa"
                value={formData.plate}
                onChange={handleInputChange}
                required
                style={{ padding: '10px', borderRadius: '5px', border: '1px solid #ddd' }}
              />
              <input
                type="text"
                name="color"
                placeholder="Color"
                value={formData.color}
                onChange={handleInputChange}
                required
                style={{ padding: '10px', borderRadius: '5px', border: '1px solid #ddd' }}
              />
            </div>
            <div style={{ marginTop: '15px', display: 'flex', gap: '10px' }}>
              <button 
                type="submit"
                style={{
                  padding: '10px 20px',
                  backgroundColor: '#28a745',
                  color: 'white',
                  border: 'none',
                  borderRadius: '5px',
                  cursor: 'pointer'
                }}
              >
                {editingCar ? 'Actualizar' : 'Guardar'}
              </button>
              <button 
                type="button"
                onClick={handleCancel}
                style={{
                  padding: '10px 20px',
                  backgroundColor: '#6c757d',
                  color: 'white',
                  border: 'none',
                  borderRadius: '5px',
                  cursor: 'pointer'
                }}
              >
                Cancelar
              </button>
            </div>
          </form>
        </div>
      )}

      {/* Tabla de autos */}
      <table style={{
        width: '100%',
        borderCollapse: 'collapse',
        backgroundColor: 'white',
        boxShadow: '0 2px 4px rgba(0,0,0,0.1)'
      }}>
        <thead>
          <tr style={{ backgroundColor: '#343a40', color: 'white' }}>
            <th style={{ padding: '12px', textAlign: 'left' }}>Marca</th>
            <th style={{ padding: '12px', textAlign: 'left' }}>Modelo</th>
            <th style={{ padding: '12px', textAlign: 'left' }}>Año</th>
            <th style={{ padding: '12px', textAlign: 'left' }}>Placa</th>
            <th style={{ padding: '12px', textAlign: 'left' }}>Color</th>
            <th style={{ padding: '12px', textAlign: 'center' }}>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {cars.length === 0 ? (
            <tr>
              <td colSpan="6" style={{ textAlign: 'center', padding: '20px' }}>
                No hay autos registrados
              </td>
            </tr>
          ) : (
            cars.map(car => (
              <tr key={car.id} style={{ borderBottom: '1px solid #ddd' }}>
                <td style={{ padding: '12px' }}>{car.brand}</td>
                <td style={{ padding: '12px' }}>{car.model}</td>
                <td style={{ padding: '12px' }}>{car.year}</td>
                <td style={{ padding: '12px' }}>{car.plate}</td>
                <td style={{ padding: '12px' }}>{car.color}</td>
                <td style={{ padding: '12px', textAlign: 'center' }}>
                  <button
                    onClick={() => handleEdit(car)}
                    style={{
                      padding: '5px 10px',
                      backgroundColor: '#ffc107',
                      color: 'white',
                      border: 'none',
                      borderRadius: '3px',
                      cursor: 'pointer',
                      marginRight: '5px'
                    }}
                  >
                    ✏️ Editar
                  </button>
                  <button
                    onClick={() => handleDelete(car.id)}
                    style={{
                      padding: '5px 10px',
                      backgroundColor: '#dc3545',
                      color: 'white',
                      border: 'none',
                      borderRadius: '3px',
                      cursor: 'pointer'
                    }}
                  >
                    🗑️ Eliminar
                  </button>
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}

export default CarsCRUD;