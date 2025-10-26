const carrito = JSON.parse(localStorage.getItem("carrito")) || [];
const contenedor = document.getElementById("carrito");
const totalDiv = document.getElementById("total");

function mostrarCarrito() {
  contenedor.innerHTML = "";
  let total = 0;

  carrito.forEach((producto, index) => {
    const div = document.createElement("div");
    div.className = "carrito-item";
    div.innerHTML = `
      <h3>${producto.nombre}</h3>
      <p>Precio: Q${producto.precio}</p>
      <button onclick="eliminarProducto(${index})">Eliminar</button>
    `;
    contenedor.appendChild(div);
    total += producto.precio;
  });

  totalDiv.innerText = `Total: Q${total}`;
}

function eliminarProducto(index) {
  carrito.splice(index, 1);
  localStorage.setItem("carrito", JSON.stringify(carrito));
  mostrarCarrito();
}

function vaciarCarrito() {
  localStorage.removeItem("carrito");
  mostrarCarrito();
  totalDiv.innerText = "Total: Q0";
}

mostrarCarrito();
