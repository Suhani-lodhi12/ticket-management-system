import "../styles/dashboard.css";
import { useEffect, useState } from "react";
import api from "../api/axios";

export default function MyTickets() {

  const [tickets, setTickets] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {

    const fetchTickets = async () => {
      try {
        const res = await api.get("/tickets", {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        });

        setTickets(res.data);

      } catch (err) {
        console.error(err);
        setTickets([]);
      } finally {
        setLoading(false);
      }
    };

    fetchTickets();
  }, []);

  return (
    <div className="center-wrapper">
      <div className="card wide">

        <h2>My Tickets</h2>

        {loading && <p>Loading tickets...</p>}

        {!loading && tickets.length === 0 && (
          <p>No tickets created yet</p>
        )}

        {!loading && tickets.map(ticket => (
          <div className="ticket-card" key={ticket.ticketId}>
            <h4>{ticket.title}</h4>

            <p>
              Status:{" "}
              <span className={`status ${ticket.status}`}>
                {ticket.status}
              </span>
            </p>

            <p>
              Priority: <strong>{ticket.priority}</strong>
            </p>
          </div>
        ))}

      </div>
    </div>
  );
}
