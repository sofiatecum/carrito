document.getElementById("formUsuario").addEventListener("submit", function (e) {
  e.preventDefault();

  const codUsuario = document.getElementById("codUsuario").value;
  const tipoPermiso = document.getElementById("permiso").value;

  fetch("https://TU_BACKEND_URL/api/usuario/permiso", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ codUsuario, tipoPermiso })
  })
    .then(response => {
      if (!response.ok) throw new Error("Error al asignar permiso");
      return response.json();
    })
    .then(data => {
      document.getElementById("resultadoUsuario").innerText =
        `Permiso asignado: ${data.permiso} al usuario ${data.codUsuario}`;
    })
    .catch(error => {
      document.getElementById("resultadoUsuario").innerText =
        "Hubo un error al asignar el permiso.";
    });
});
