import "../styles/dashboard.css";

export default function MyTickets() {

  const tickets = [
    { id: 1, title: "Laptop Issue", status: "NEW" },
    { id: 2, title: "WiFi Problem", status: "RESOLVED" }
  ];

  return (
    <div className="ticket-list">
      {tickets.map(t => (
        <div className="ticket-card" key={t.id}>
          <h4>{t.title}</h4>
          <p className={t.status}>{t.status}</p>
        </div>
      ))}
    </div>
  );
}
