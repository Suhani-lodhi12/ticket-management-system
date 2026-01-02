import "../styles/dashboard.css";
import { useState } from "react";
import CreateTicket from "../components/CreateTicket";
import MyTickets from "../components/MyTickets";
export default function UserDashboard() {
  const userName = localStorage.getItem("userName");

  // ⭐ CONTROL WHICH SECTION IS OPEN
  const [activeSection, setActiveSection] = useState(null);

  return (
    <div className="dashboard">
      {/* NAVBAR */}
      <div className="navbar">
        <h3>USER</h3>
        <div>
          <button onClick={() => setActiveSection("create")}>
            Create Ticket
          </button>

          <button onClick={() => setActiveSection("tickets")}>
            My Tickets
          </button>

          <button
            onClick={() => {
              localStorage.clear();
              window.location.href = "/";
            }}
          >
            Logout
          </button>
        </div>
      </div>

      {/* WELCOME SECTION */}
      {activeSection === null && (
        <div className="welcome">
          <h1>Welcome, {userName}</h1>
          <p>Create and track your support tickets</p>
        </div>
      )}

      {/* ⭐ CONDITIONAL RENDERING */}
      {activeSection === "create" && <CreateTicket />}
      {activeSection === "tickets" && <MyTickets />}
    </div>
  );
}
