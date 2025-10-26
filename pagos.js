document.getElementById("formPago").addEventListener("submit", function (e) {
  e.preventDefault();

  const codUsuario = document.getElementById("codUsuario").value;
  const metodo = document.getElementById("metodo").value;
  const carrito = JSON.parse(localStorage.getItem("carrito")) || [];

  const monto = carrito.reduce((total, producto) => total + producto.precio, 0);

  fetch("https://TU_BACKEND_URL/api/pago", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ codUsuario, metodo, monto })
  })
    .then(response => {
      if (!response.ok) throw new Error("Error en el pago");
      return response.json();
    })
    .then(data => {
      document.getElementById("resultadoPago").innerText =
        `Pago exitoso: ${data.estado} por Q${data.monto}`;
      localStorage.removeItem("carrito");
    })
    .catch(error => {
      document.getElementById("resultadoPago").innerText =
        "Hubo un error al procesar el pago.";
    });
});
