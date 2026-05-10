
// async function :-
document.querySelector("form").addEventListener("submit", async function(e) {
    e.preventDefault();  // stop page reload

    const username = document.getElementById("text").value;
    const password = document.getElementById("pass").value;

    const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
    });

    if (response.ok) {
        const data = await response.json();
        localStorage.setItem("jwtToken", data.token);
        // alert("Login successful ✅");
        window.location.href = "dashboard.html";  // redirect after login
    } else {
        alert("Invalid username or password");
    }
});