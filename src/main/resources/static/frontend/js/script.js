const API_URL = "http://localhost:8080/qrcode";

const form = document.querySelector("#qr-form");
const contentInput = document.querySelector("#content");
const characterCount = document.querySelector("#character-count");
const generateButton = document.querySelector("#generate-button");
const buttonLabel = document.querySelector(".button-label");
const statusArea = document.querySelector("#status-area");
const statusMessage = document.querySelector("#status-message");
const qrImage = document.querySelector("#qr-image");
const placeholderContent = document.querySelector("#placeholder-content");
const downloadButton = document.querySelector("#download-button");

let qrCodeUrl = null;

function setStatus(message, type = "") {
    statusMessage.textContent = message;
    statusArea.className = `status-area ${type}`.trim();
}

function updateCharacterCount() {
    characterCount.textContent = `${contentInput.value.length} / 500`;
}

function showQrCode(blob) {
    if (qrCodeUrl) {
        URL.revokeObjectURL(qrCodeUrl);
    }

    qrCodeUrl = URL.createObjectURL(blob);
    qrImage.src = qrCodeUrl;
    qrImage.hidden = false;
    placeholderContent.hidden = true;
    downloadButton.disabled = false;
}

function setLoading(isLoading) {
    generateButton.disabled = isLoading;
    contentInput.disabled = isLoading;
    buttonLabel.textContent = isLoading ? "Gerando..." : "Gerar QR Code";
    statusArea.classList.toggle("loading", isLoading);
}

form.addEventListener("submit", async (event) => {
    event.preventDefault();

    const content = contentInput.value.trim();

    if (!content) {
        setStatus("Digite um conteúdo antes de gerar o QR Code.", "error");
        contentInput.focus();
        return;
    }

    setLoading(true);
    setStatus("Gerando seu QR Code...", "loading");

    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ content })
        });

        if (!response.ok) {
            throw new Error("A API retornou um erro.");
        }

        const blob = await response.blob();
        showQrCode(blob);
        setStatus("QR Code pronto para baixar.");
    } catch (error) {
        if (error instanceof TypeError) {
            setStatus("Não foi possível conectar ao backend. Verifique se a aplicação está ativa.", "error");
        } else {
            setStatus("Não foi possível gerar o QR Code. Tente novamente.", "error");
        }
    } finally {
        setLoading(false);
    }
});

contentInput.addEventListener("input", updateCharacterCount);

downloadButton.addEventListener("click", () => {
    if (!qrCodeUrl) {
        return;
    }

    const link = document.createElement("a");
    link.href = qrCodeUrl;
    link.download = "qrcode.png";
    document.body.appendChild(link);
    link.click();
    link.remove();
});

window.addEventListener("beforeunload", () => {
    if (qrCodeUrl) {
        URL.revokeObjectURL(qrCodeUrl);
    }
});

updateCharacterCount();
