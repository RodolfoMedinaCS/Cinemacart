// Listens for page load for curtain animation
document.addEventListener("DOMContentLoaded", () => {
  const curtain = document.getElementById("curtain");
  if (!curtain) return;

  // Login state check
  const navAuthLink = document.querySelector("#navAuthLink");
  const isLoggedIn = localStorage.getItem("isLoggedIn") === "true";

  if (navAuthLink) {
    if (isLoggedIn) {
      // Show Account button instead of Login
      navAuthLink.innerHTML = '<a href="account.html" id="accountBtn">Account</a>';
    } else {
      // Show login by default
      navAuthLink.innerHTML = '<a href="login.html">Login</a>';
    }
  }

  // Curtains
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

  // Ping server button
  const pingButton = document.getElementById("pingServer");

  if (pingButton) {
    pingButton.addEventListener("click", () => {
      fetch("http://localhost:8000/", {
        method: "POST",
        headers: { "Content-Type": "text/plain" },
        body: "Hi!"
      })
        .then(response => response.text())
        .then(data => alert("Received from backend: " + data))
        .catch(error => {
          alert("Error connecting to server");
          console.error(error);
        });
    });
  }

  // Search elements
  const searchBtn = document.getElementById("searchBtn");
  const searchInput = document.getElementById("searchInput");
  const resultsContainer = document.getElementById("searchResults");

  // Enter triggers search button
  if (searchInput) {
    searchInput.addEventListener("keypress", (e) => {
      if (e.key === "Enter") {
        searchBtn.click();
      }
    });
  }

  // Hide results by default
  if (resultsContainer) {
    resultsContainer.style.display = "none";
  }

  // Create "No results" message
  let noResultsMsg = document.createElement("p");
  noResultsMsg.textContent = "No results found";
  noResultsMsg.style.color = "#ccc";
  noResultsMsg.style.marginTop = "20px";
  noResultsMsg.style.display = "none";

  // Insert message under search bar
  if (searchInput && searchInput.parentElement) {
    searchInput.parentElement.parentElement.appendChild(noResultsMsg);
  }

  if (searchBtn) {
    searchBtn.addEventListener("click", () => {
      const query = searchInput.value.trim();

      // Empty input check
      if (!query) {
        alert("Please enter a search term");
        return;
      }

      // Make request to backend
      fetch("http://localhost:8000/search", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          action: "search",
          query: query
        })
      })
        .then(res => res.json())
        .then(data => {
          console.log("Search results:", data); // Inspect this in console

          // Clear previous results
          resultsContainer.innerHTML = "";

          if (!data || data.length === 0) {
            resultsContainer.style.display = "none";
            noResultsMsg.style.display = "block";
            return;
          }

          noResultsMsg.style.display = "none";
          resultsContainer.style.display = "";

          // Limit to top 3 results (can be increased later)
          const resultsToShow = data.slice(0, 3);

          resultsToShow.forEach(movie => {
            const card = document.createElement("div");
            card.className = "movie-card";

            const title = movie.title;
            const genre = movie.genre;
            const duration = movie.duration;
            const rating = movie.rating;
            const movieId = movie.movieId;

            // Map movie IDs to images
            function getMovieImage(id) {
              const images = {
                1: "images/titanic.webp",
                2: "images/godfather1.jpg",
                3: "images/godfather2.webp",
                4: "images/gladiator.webp",
                5: "images/3idiots.jpg",
                6: "images/batman.jpg",
                7: "images/lionking.jpg",
                8: "images/fury.jpg",
                9: "images/kiterunner.jpg",
                10: "images/avatar.webp",
                11: "images/darkknight.avif",
                12: "images/beautybeast.jpg"
              };
              return images[id] || "images/placeholder.jpg";
            }

            const image = getMovieImage(movieId);

            card.innerHTML = `
              <img src="${image}" alt="${title}">
              <div class="movie-info">
                <h3>${title}</h3>
                <p>${genre}</p>
                <p>⭐ ${rating} | ${duration} min</p>
                <button class="btn view-btn">View</button>
              </div>
            `;

            resultsContainer.appendChild(card);
            card.querySelector('.view-btn').addEventListener('click', () => {
              localStorage.setItem('selectedMovieId', movieId);
              localStorage.setItem('selectedMovieTitle', title);
              localStorage.setItem('selectedMovieGenre', genre);
              localStorage.setItem('selectedMovieDuration', duration);
              localStorage.setItem('selectedMovieRating', rating);
              curtain.classList.add('active');
              setTimeout(() => { window.location.href = 'movieDetails.html'; }, 1500);
            });
          });
        })
        .catch(err => {
          console.error(err);
          alert("Error connecting to server");
        });
    });
  }
  // Show 4 random movies in "Now Showing" on the homepage
  if (
    window.location.pathname.endsWith('index.html') ||
    window.location.pathname === "/" ||
    window.location.pathname === "/index.html"
  ) {
    fetch("http://localhost:8000/search", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ action: "search", query: "" }) // Empty query fetches all movies
    })
    .then(res => res.json())
    .then(allMovies => {
      // Shuffle movies array and pick the first 4
      allMovies.sort(() => Math.random() - 0.5);
      const nowShowing = allMovies.slice(0, 4);

      // Get Now Showing container and clear any previous cards
      const nowShowingContainer = document.getElementById("nowShowing");
      nowShowingContainer.innerHTML = "";

      // Map movie IDs to images
      function getMovieImage(id) {
        const images = {
          1: "images/titanic.webp",
          2: "images/godfather1.jpg",
          3: "images/godfather2.webp",
          4: "images/gladiator.webp",
          5: "images/3idiots.jpg",
          6: "images/batman.jpg",
          7: "images/lionking.jpg",
          8: "images/fury.jpg",
          9: "images/kiterunner.jpg",
          10: "images/avatar.webp",
          11: "images/darkknight.avif",
          12: "images/beautybeast.jpg"
        };
        return images[id] || "images/placeholder.jpg";
      }

      // Create a card for each movie and add to the page
      nowShowing.forEach(movie => {
        const card = document.createElement("div");
        card.className = "movie-card";
        const image = getMovieImage(movie.movieId);
        card.innerHTML = `
          <img src="${image}" alt="${movie.title}">
          <div class="movie-info">
            <div class="movie-top">
              <h3>${movie.title}</h3>
              <span class="movie-rating">⭐ ${movie.rating}</span>
            </div>
            <div class="movie-bottom">
              <p class="movie-genre">${movie.genre}</p>
              <span class="movie-duration">${movie.duration} min</span>
            </div>
            <button class="btn view-btn">View Details</button>
          </div>
        `;
        nowShowingContainer.appendChild(card);
        card.querySelector('.view-btn').addEventListener('click', () => {
          localStorage.setItem('selectedMovieId', movie.movieId);
          localStorage.setItem('selectedMovieTitle', movie.title);
          localStorage.setItem('selectedMovieGenre', movie.genre);
          localStorage.setItem('selectedMovieDuration', movie.duration);
          localStorage.setItem('selectedMovieRating', movie.rating);
          curtain.classList.add('active');
          setTimeout(() => { window.location.href = 'movieDetails.html'; }, 1500);
        });
      });
    })
    .catch(err => {
      // Show error if movies failed to load
      console.error("Failed to load now showing movies!", err);
    });
  }
});