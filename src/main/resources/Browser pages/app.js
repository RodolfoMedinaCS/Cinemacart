// Listens for page load for curtain animation
document.addEventListener("DOMContentLoaded", () => {
  const curtain = document.getElementById("curtain");

  const movieDescriptions = {
    1: "Set against the backdrop of the tragic 1912 RMS Titanic disaster, this epic romance follows Jack Dawson, a charming drifter who wins a third-class ticket in a poker game, and Rose DeWitt Bukater, a first-class passenger suffocating under the weight of her privileged but loveless engagement. When the two meet aboard the ship, they fall into a passionate and forbidden love that transcends their social divide. As the unsinkable ship meets its fate in the icy North Atlantic, Jack and Rose must fight not only for their survival but for the love they found in the most unlikely of places.",

    2: "When aging Mafia patriarch Vito Corleone survives an assassination attempt, his youngest and most reluctant son Michael — a decorated war hero who wanted nothing to do with the family business — is slowly drawn into the violent and treacherous world of organized crime. What begins as a desire to protect his father becomes a complete transformation as Michael outmaneuvers rival families, corrupt politicians, and enemies from within. A sweeping portrait of power, loyalty, and moral decay, this film redefined what American cinema could be.",

    3: "Told across two timelines, this epic continuation follows the Corleone crime family as Michael consolidates power in the 1950s, while flashbacks reveal the early life of young Vito Corleone — a Sicilian immigrant who arrives in America with nothing and quietly builds an empire through street-level cunning and ruthless ambition. As Michael eliminates his enemies and expands the family's reach, he loses everything that once made him human. Widely regarded as one of the greatest sequels ever made, this film is a haunting meditation on ambition, family, and the corruption of the American Dream.",

    4: "In the height of the Roman Empire, General Maximus Decimus Meridius is a beloved commander on the verge of retirement when Emperor Marcus Aurelius secretly names him his successor — infuriating the emperor's power-hungry son Commodus, who murders his own father, executes Maximus's family, and sells Maximus into slavery. Stripped of his name and his identity, Maximus rises through the brutal gladiatorial arena, winning the hearts of the Roman crowds and slowly engineering a path toward the vengeance he has sworn. A thundering story of honor, loss, and the indomitable will to survive.",

    5: "Rancho, Farhan, and Raju are three inseparable friends navigating the cutthroat pressure of India's most elite engineering college, where rote memorization and family expectations crush the joy out of learning. Rancho, a free-spirited genius who challenges every convention, inspires his friends to question what they truly want from life — but then mysteriously disappears after graduation. A decade later, Farhan and Raju race across India following cryptic clues to find their lost friend. Equal parts hilarious and deeply emotional, this film is a love letter to curiosity, friendship, and following your own path.",

    6: "Two years into his war on Gotham's criminals, Batman operates in the shadows as a vigilante feared by the public and hunted by the police. When a sadistic, intellectually twisted killer called the Riddler begins systematically murdering the city's most powerful figures and leaving coded messages addressed to Batman, the Dark Knight is forced to confront a conspiracy that runs deeper than he ever imagined — and that strikes uncomfortably close to his own family's legacy. A dark, rain-soaked neo-noir that reimagines Batman as a brooding detective in a city rotting from the inside.",

    7: "Young lion cub Simba idolizes his father, King Mufasa, and dreams of one day ruling the Pride Lands. But when Simba's scheming uncle Scar orchestrates Mufasa's death and convinces the devastated cub that he was responsible, Simba flees into exile and spends years hiding from his past in a carefree jungle life with unlikely companions Timon and Pumbaa. When the spirit of his father calls him home and a childhood friend appears with urgent news, Simba must finally confront his guilt, face his murderous uncle, and claim the throne that was always his destiny.",

    8: "November 1945, Nazi Germany's final days. Sergeant Don 'Wardaddy' Collier commands a battle-hardened crew aboard a Sherman tank nicknamed Fury, pushing deep into the heart of a crumbling but still-deadly enemy. When a young and inexperienced Army typist is assigned to their crew, the veterans must transform him into a soldier while facing overwhelming German forces, including the terrifying Tiger I tank. Raw, brutal, and relentlessly tense, this film strips war of its glory and captures the desperate brotherhood that forms between men who know every mission might be their last.",

    9: "In 1970s Kabul, wealthy Pashtun boy Amir and his loyal Hazara servant Hassan share an inseparable childhood bond — until a devastating act of violence and Amir's cowardly failure to intervene tears them apart forever. When the Soviet invasion forces Amir and his father to flee to America, Amir spends decades haunted by guilt and the memory of his betrayal. Years later, a phone call from a dying old friend offers him one last chance at redemption — but it will require him to return to a Taliban-controlled Afghanistan and face the darkest chapter of his past.",

    10: "In the mid-22nd century, paraplegic former Marine Jake Sully is sent to the moon Pandora, where a corporation is mining a precious mineral and a militarized security force is preparing to displace the indigenous Na'vi people. Jake is given control of a Na'vi-human hybrid body — an Avatar — to infiltrate the tribe and gather intelligence. But as Jake immerses himself in Na'vi culture and falls for a warrior named Neytiri, he finds himself torn between the world he came from and the breathtaking world he has come to love, ultimately choosing a side in a conflict that will decide Pandora's fate.",

    11: "Following the events that shook Gotham, Batman forges an uneasy alliance with Lieutenant James Gordon and newly appointed District Attorney Harvey Dent to systematically dismantle what remains of the city's organized crime networks. But when the anarchic and philosophically terrifying Joker emerges — not to gain power or money, but simply to watch the world burn — their alliance is pushed to its breaking point. The Joker forces Batman into an impossible moral dilemma: to save Gotham, he may have to become the very thing the city fears. A landmark in superhero cinema that is as much a crime thriller as it is a blockbuster.",

    12: "When a kind-hearted young woman named Belle offers herself as a prisoner in exchange for her father's freedom, she finds herself trapped in an enchanted castle ruled by a fearsome Beast — a prince cursed long ago for his arrogance and cruelty. As the seasons change within the castle walls, Belle slowly looks past the Beast's terrifying exterior and discovers a wounded, lonely soul beneath. With a cast of enchanted household objects cheering them on and a villain from the village plotting their downfall, Belle and the Beast must learn that love sees what is invisible to the eye — before the last petal falls."
  };

  if (!curtain) return;

  // Login state check
  const navAuthLink = document.querySelector("#navAuthLink");
  const isLoggedIn = sessionStorage.getItem("isLoggedIn") === "true";

  if (navAuthLink) {
    // Account dropdown menu for logged in users
    if (isLoggedIn) {
      navAuthLink.innerHTML = `
        <div class="account-dropdown">
          <button id="accountBtn" class="btn">My Account ▾</button>
          <div class="dropdown-menu" id="dropdownMenu">
            <button id="accountPageBtn">Account Page</button>
            <button id="logoutBtn">Logout</button>
          </div>
        </div>
      `;

      // Toggle dropdown
      const accountBtn = document.getElementById("accountBtn");
      const dropdownMenu = document.getElementById("dropdownMenu");

      accountBtn.addEventListener("click", () => {
        dropdownMenu.classList.toggle("show");
      });

      // Logout logic
      const logoutBtn = document.getElementById("logoutBtn");
      logoutBtn.addEventListener("click", () => {
        sessionStorage.clear();
        localStorage.clear();

        window.location.href = "login.html";
      });

      const accountPageBtn = document.getElementById("accountPageBtn");

      accountPageBtn.addEventListener("click", () => {
        window.location.href = "account.html";
      });

      // Close dropdown if clicked outside
      document.addEventListener("click", (e) => {
        if (!accountBtn.contains(e.target) && !dropdownMenu.contains(e.target)) {
          dropdownMenu.classList.remove("show");
        }
      });

      // Login button if not logged in 
    } else {
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

          data.forEach(movie => {
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
              <div style="position:relative;">                                                                                                   
                <img src="${image}" alt="${title}" style="width:100%; height:320px; object-fit:cover; display:block;">
                <span class="rating-overlay">⭐ ${rating}</span>                                                                                 ─
              </div>
              <div class="movie-info">                                                                                                           
                <h3>${title}</h3>
                <div class="movie-meta-row">                                                                                                     
                  <span class="genre-tag">${genre}</span>
                  <span class="duration-text">${duration} min</span>                                                                             
                </div>      
                <button class="btn view-btn" style="width:100%;">View</button>
              </div>                                                                                                                             
            `;


            resultsContainer.appendChild(card);
            card.querySelector('.view-btn').addEventListener('click', () => {
              localStorage.setItem('selectedMovieId', movieId);
              localStorage.setItem('selectedMovieTitle', title);
              localStorage.setItem('selectedMovieGenre', genre);
              localStorage.setItem('selectedMovieDuration', duration);
              localStorage.setItem('selectedMovieRating', rating);
              localStorage.setItem('selectedMovieDescription', movie.description || movieDescriptions[movieId] || 'No description available.');
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
      // Shuffle movies array and pick the first 6
      allMovies.sort(() => Math.random() - 0.5);
      const nowShowing = allMovies;

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
          <div style="position:relative;">                                                                                                   
            <img src="${image}" alt="${movie.title}" style="width:100%; height:320px; object-fit:cover; display:block;">                     
            <span class="rating-overlay">⭐ ${movie.rating}</span>                                                                           ─
          </div>        
          <div class="movie-info">                                                                                                           
            <h3>${movie.title}</h3>
            <div class="movie-meta-row">                                                                                                     
              <span class="genre-tag">${movie.genre}</span>
              <span class="duration-text">${movie.duration} min</span>                                                                       
            </div>      
            <button class="btn view-btn" style="width:100%;">View Details</button>                                                           
          </div>
        `;
        nowShowingContainer.appendChild(card);
        card.querySelector('.view-btn').addEventListener('click', () => {
          localStorage.setItem('selectedMovieId', movie.movieId);
          localStorage.setItem('selectedMovieTitle', movie.title);
          localStorage.setItem('selectedMovieGenre', movie.genre);
          localStorage.setItem('selectedMovieDuration', movie.duration);
          localStorage.setItem('selectedMovieRating', movie.rating);
          localStorage.setItem('selectedMovieDescription', movie.description || '');
          localStorage.setItem('selectedMovieDescription', movie.description || movieDescriptions[movie.movieId] || 'No description available.');
          curtain.classList.add('active');
          setTimeout(() => { window.location.href = 'movieDetails.html'; }, 1500);
        });
      });

      const track = document.getElementById('nowShowing');
      const prevBtn = document.querySelector('.prev-btn');
      const nextBtn = document.querySelector('.next-btn');
      const total = allMovies.length;
      const visibleCount = Math.floor(track.parentElement.offsetWidth / 240);
      const maxIndex = total - visibleCount - 1;
      let index = Math.floor(maxIndex / 2);

      function updateCarousel() {
        track.style.transform = `translateX(-${index * 240}px)`;
      }

      updateCarousel();

      nextBtn.addEventListener('click', () => {
        if (index < maxIndex) { index++; updateCarousel(); }
      });

      prevBtn.addEventListener('click', () => {
        if (index > 0) { index--; updateCarousel(); }
      });

    })
    .catch(err => {
      // Show error if movies failed to load
      console.error("Failed to load now showing movies!", err);
    });
  }
});