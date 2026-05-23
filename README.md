<H1>CinemaCart </H1>
<H2>A full-stack movie ticket booking web application, browse movies, select showtimes, pick seats, and checkout.</H2>

<img width="3024" height="1720" alt="image" src="https://github.com/user-attachments/assets/506836bd-9f3d-499b-9150-d149be9aaf18" />

<img width="3024" height="1722" alt="image" src="https://github.com/user-attachments/assets/8869a4a6-e9b7-41a4-9674-6c582cc5557e" />

<img width="3024" height="1722" alt="image" src="https://github.com/user-attachments/assets/5f02fd8c-0d38-4372-a054-f5e1c23a1060" />

<img width="3024" height="1720" alt="image" src="https://github.com/user-attachments/assets/2af63e99-5e99-4e09-a71a-ed1840fb4f08" />

<H1>Live Demo</H1>
<p>📹 Watch Demo Walkthrough — see the full booking flow in under 3 minutes</p>
<hr>
<a href="https://youtu.be/N7dFTu-qBuw">
  <img src="https://img.youtube.com/vi/N7dFTu-qBuw/maxresdefault.jpg" alt="CinemaCart Demo" width="800"/>
</a>
<hr>
<h1>What is this?</h1>
<p>CinemaCart is a full-stack cinema booking platform built as a team project for COMP 380 (Software Engineering) at CSUN. Users can:</p>
<ul>
  <li>Browse and search movies by title or genre</li>
  <li>View movie details and trailers</li>
  <li>Select a date, showtime, and seats</li>
  <li>Choose ticket types (Adult / Child / Senior)</li>
  <li>Review their order in a cart and checkout</li>
  <li>View their booking history and cancel/delete bookings</li>
  <li>(Manager only) View a revenue dashboard with monthly reports</li>
</ul>
<hr>
<h1>Tech Stack</h1>
<table>
  <tr>
    <th>Layer</th>
    <th>Technology</th>
  </tr>
  <tr>
    <td>Frontend</td>
    <td>HTML, CSS, JavaScript</td>
  </tr>
  <tr>
    <td>Backend</td>
    <td>Java </td>
  </tr>
  <tr>
    <td>Database</td>
    <td>Firebase Firestore</td>
  </tr>
  <tr>
    <td>Auth</td>
    <td>BCrypt password hashing + session token management</td>
  </tr>
  <tr>
    <td>Build Tool</td>
    <td>Maven</td>
  </tr>
</table>

<hr>
<h1>My Contributions</h1>
<p>This was a 4-person team project. Below is what I personally built.</p>

<h2> Complete 7-Page Booking Pipeline</h2>
<p>I designed and implemented the entire user-facing booking flow from start to finish, managing state across pages using localStorage:</p>

<table>
  <tr>
    <th>Page</th>
    <th>Description</th>
  </tr>
  <tr>
    <td>index.html</td>
    <td>Homepage with dynamic movie carousel</td>
  </tr>
  <tr>
    <td>movieDetails.html</td>
    <td>Movie detail view with poster, rating, genre, description, and embedded YouTube trailer</td>
  </tr>
  <tr>
    <td>showtimes.html</td>
    <td>Date picker (7-day rolling window) + showtime selection</td>
  </tr>
  <tr>
    <td>tickets.html</td>
    <td>Interactive 6x8 seat map with real time seat selection</td>
  </tr>
  <tr>
    <td>ticketTypes.html</td>
    <td>Adult / Child / Senior ticket counter with live price calculation</td>
  </tr>
  <tr>
    <td>cart.html</td>
    <td>Order review with edit functionality and subtotal/tax breakdown</td>
  </tr>
  <tr>
    <td>checkout.html</td>
    <td>Payment form with full client side validation (card number, expiry, CVV)</td>
  </tr>
  <tr>
    <td>bookingConfirmed.html</td>
    <td>Confirmation page showing full booking summary</td>
  </tr>
</table>
<hr>
<h2> Homepage Carousel</h2>
<p>Built the "Now Showing" carousel on the homepage, dynamically fetches all movies from the backend and renders them as cards with scroll navigation.</p>
<hr>
<h2> localStorage State Architecture</h2>
<p>Designed the state flow that carries movie selection, seat choices, and ticket data across all 7 pages without a page refresh, seamlessly syncs to the backend cart when the user is logged in.</p>
