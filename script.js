const productos = [
  { id: 1, nombre: "Laptop HP Pavilion", precio: 799.99 },
  { id: 2, nombre: "Mouse Logitech", precio: 199.99 },
  { id: 3, nombre: "Monitor Samsung", precio: 350 },
];

const contenedor = document.getElementById("productos");

productos.forEach(producto => {
  const div = document.createElement("div");
  div.className = "producto";
  div.innerHTML = `
    <h3>${producto.nombre}</h3>
    <p>Precio: Q${producto.precio}</p>
    <button onclick="agregarAlCarrito(${producto.id})">Agregar al carrito</button>
  `;
  contenedor.appendChild(div);
});

function agregarAlCarrito(id) {
  const producto = productos.find(p => p.id === id);
  let carrito = JSON.parse(localStorage.getItem("carrito")) || [];
  carrito.push(producto);
  localStorage.setItem("carrito", JSON.stringify(carrito));
  alert(`${producto.nombre} agregado al carrito`);
}
