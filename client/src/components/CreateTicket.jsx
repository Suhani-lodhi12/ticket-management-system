import "../styles/dashboard.css";

export default function CreateTicket() {
  return (
    <div className="center-wrapper">
      <div className="card">
        <h2>Create Ticket</h2>

        <input placeholder="Title" />
        <textarea placeholder="Description" rows="4"></textarea>

        <select>
          <option>LOW</option>
          <option>MEDIUM</option>
          <option>HIGH</option>
        </select>

        <button>Submit Ticket</button>
      </div>
    </div>
  );
}

