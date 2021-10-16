

function actualizarTarefas() {
	var request = new XMLHttpRequest();
	var vBoard = document.getElementById("tarefas");

	request.onload = function() {
		vBoard.innerHTML = this.responseText;
		vBoard.style.color="black";
		setTimeout(actualizarTarefas, 2000);
	};

	request.ontimeout = function() {
		vBoard.innerHTML = "Server timeout, a tentar reconectar ...";
		vBoard.style.color="red";
		setTimeout(actualizarTarefas, 3000);
	};

	request.onerror = function() {
		vBoard.innerHTML = "Sem resposta do Servidor, a tentar reconectar ...";
		vBoard.style.color="red";
		setTimeout(actualizarTarefas, 5000);
	};

	request.open("GET", "/tarefas", true);
	request.timeout = 5000;
	request.send();
}


