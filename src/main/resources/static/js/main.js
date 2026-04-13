// ===== VALIDAÇÕES GLOBAIS =====

// Validação de Login
function validarLogin(login) {
  if (!login || login.length < 3) {
    return { valido: false, mensagem: "Login deve ter no mínimo 3 caracteres" };
  }
  if (login.includes(" ")) {
    return { valido: false, mensagem: "Login não pode conter espaços" };
  }
  return { valido: true, mensagem: "" };
}

// Validação de Email
function validarEmail(email) {
  const regex = /^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$/;
  if (!email) {
    return { valido: false, mensagem: "Email é obrigatório" };
  }
  if (!regex.test(email)) {
    return { valido: false, mensagem: "Email inválido" };
  }
  return { valido: true, mensagem: "" };
}

// Validação de Senha (força)
function validarSenha(senha) {
  if (!senha || senha.length < 6) {
    return { valido: false, mensagem: "Senha deve ter no mínimo 6 caracteres" };
  }
  if (!senha.match(/.*\d.*/)) {
    return { valido: false, mensagem: "Senha deve conter pelo menos 1 número" };
  }
  if (!senha.match(/.*[A-Z].*/)) {
    return {
      valido: false,
      mensagem: "Senha deve conter pelo menos 1 letra maiúscula",
    };
  }
  if (!senha.match(/.*[!@#$%&*].*/)) {
    return {
      valido: false,
      mensagem: "Senha deve conter pelo menos 1 caractere especial (!@#$%&*)",
    };
  }
  return { valido: true, mensagem: "" };
}

// Mostrar mensagem de erro
function mostrarErro(elementId, mensagem) {
  const errorElement = document.getElementById(elementId);
  if (errorElement) {
    errorElement.textContent = mensagem;
    errorElement.style.display = "block";
  }
}

function limparErro(elementId) {
  const errorElement = document.getElementById(elementId);
  if (errorElement) {
    errorElement.textContent = "";
    errorElement.style.display = "none";
  }
}

// Máscara para telefone
function aplicarMascaraTelefone(input) {
  let valor = input.value.replace(/\D/g, "");
  if (valor.length <= 11) {
    if (valor.length > 2) {
      valor = `(${valor.substring(0, 2)}) ${valor.substring(2)}`;
    }
    if (valor.length > 10) {
      valor = `${valor.substring(0, 10)}-${valor.substring(10)}`;
    }
    input.value = valor;
  }
}

// Máscara para CNPJ
function aplicarMascaraCNPJ(input) {
  let valor = input.value.replace(/\D/g, "");
  if (valor.length <= 14) {
    if (valor.length > 2) {
      valor = `${valor.substring(0, 2)}.${valor.substring(2)}`;
    }
    if (valor.length > 6) {
      valor = `${valor.substring(0, 6)}.${valor.substring(6)}`;
    }
    if (valor.length > 10) {
      valor = `${valor.substring(0, 10)}/${valor.substring(10)}`;
    }
    if (valor.length > 15) {
      valor = `${valor.substring(0, 15)}-${valor.substring(15, 17)}`;
    }
    input.value = valor;
  }
}

// ===== MODAL DE ALERTA GLOBAL =====
function mostrarAlerta(mensagem, tipo = "info") {
  // Remove modal existente se houver
  const modalExistente = document.querySelector(".modal-alert");
  if (modalExistente) {
    modalExistente.remove();
  }

  const icons = {
    success: "✅",
    error: "❌",
    warning: "⚠️",
    info: "ℹ️",
  };

  const modal = document.createElement("div");
  modal.className = "modal-alert";
  modal.innerHTML = `
        <div class="modal-alert-content">
            <div class="modal-alert-header ${tipo}">
                ${icons[tipo] || "ℹ️"}
            </div>
            <div class="modal-alert-body">
                ${mensagem}
            </div>
            <div class="modal-alert-footer">
                <button onclick="fecharAlerta()">OK</button>
            </div>
        </div>
    `;

  document.body.appendChild(modal);

  // Fechar ao clicar fora
  modal.addEventListener("click", (e) => {
    if (e.target === modal) {
      fecharAlerta();
    }
  });
}

function fecharAlerta() {
  const modal = document.querySelector(".modal-alert");
  if (modal) {
    modal.remove();
  }
}

// Substituir os alerts do sistema
window.alert = function (mensagem) {
  mostrarAlerta(mensagem, "info");
};
