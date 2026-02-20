// Listens for page load for curtain animation
document.addEventListener("DOMContentLoaded", () => {
  const curtain = document.getElementById("curtain");
  if (!curtain) return;

  // Makes sure buttons/links are hooked after page load
  document.querySelectorAll("a, button[data-link]").forEach(el => {
    el.addEventListener("click", e => {
      const url = el.getAttribute("href") || el.dataset.link;
      if (!url || url.startsWith("#")) return;

      e.preventDefault();

      // Slides curtains in
      curtain.classList.add("active");

      // Stores the next page
      sessionStorage.setItem("nextPage", url);

      // Waits for curtains to fully close
      setTimeout(() => {
        window.location.href = url;
      }, 1500);
    });
  });

  // Starts curtains closed
  curtain.style.transition = "none";
  curtain.classList.add("active");

  curtain.offsetHeight;

  curtain.style.transition = "";

  // Trigger open animation
  requestAnimationFrame(() => {
    setTimeout(() => {
      curtain.classList.remove("active");
      sessionStorage.removeItem("nextPage");
    }, 50);
  });

  // Button to ping java server
  const pingButton = document.getElementById("pingServer");

  if (pingButton) {
    pingButton.addEventListener("click", () => {
      // Sends "Hi!" to Java server
      fetch("http://localhost:8000/", {
        method: "POST",
        headers: {
          "Content-Type": "text/plain"
        },
        body: "Hi!"
      })
        .then(response => response.text())
        .then(data => {
          // Shows the server response in an alert (Should be Hello)
          alert("Received from backend: " + data);
        })
        .catch(error => {
          alert("Error connecting to server");
          console.error(error);
        });
    });
  }
});