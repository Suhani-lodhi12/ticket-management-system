import "../styles/dashboard.css";
import { useEffect, useState } from "react";
import api from "../api/axios";

export default function MyTickets() {

  const [tickets, setTickets] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    api.get("/tickets/my-created")
      .then(res => setTickets(res.data))
      .catch(() => setError("Failed to load tickets"))
      .finally(() => setLoading(false));
  }, []);

  return (
    <div className="center-wrapper">
      <div className="card wide">

        <h2>My Tickets</h2>

        {loading && <p>Loading tickets...</p>}

        {error && <p className="error-text">{error}</p>}

        {!loading && tickets.length === 0 && !error && (
          <p>No tickets created yet</p>
        )}

        {tickets.map(t => (
          <div className="ticket-card" key={t.ticketId}>
            <h4>{t.title}</h4>
            <p>
              Status: <span className={t.status}>{t.status}</span>
            </p>
            <p>Priority: {t.priority}</p>
            <p>Category: {t.category}</p>
          </div>
        ))}

      </div>
    </div>
  );
}
