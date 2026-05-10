
document.querySelector("form").addEventListener("submit", async function (e) {
    e.preventDefault(); // to prevent page from reloading
    const username = document.getElementById("name").value;
    const password = document.getElementById("pass").value;
    const email = document.getElementById("email").value;
    const roll_no = document.getElementById("roll_no").value;
    const password_confirmation = document.getElementById("reenter").value;
    // check passwords match before even hitting the backend
    if (password !== password_confirmation) {
        alert("Passwords do not match");
        return;
    }
    const response = await fetch("http://localhost:8080/auth/register",{
        method: "POST",
        headers: {"Content-Type": "application/json"},
        // to send the body to the backend since password_confirmation doesn't exist in backend no need to add it here
        body: JSON.stringify({username, password, email, roll_no })
    });
    if (response.ok) {
        const data = await response.json();
        localStorage.setItem("jwtToken", data.token);
        alert("Successfully registered!");
        window.location.href = "dashboard.html"; // directly redirecting to dashboard instead of logging in again
    }
    else{
        alert(response.message);
    }
});