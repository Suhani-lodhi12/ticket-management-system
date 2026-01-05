import "../styles/dashboard.css";
import { useState } from "react";
import api from "../api/axios";

export default function CreateTicket({ onSuccess }) {

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [priority, setPriority] = useState("LOW");
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      await api.post(
        "/tickets",
        { title, description, priority },
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      );

      // ✅ FORM HIDE + SUCCESS SHOW
      setSuccess(true);

      // optional: auto back to dashboard
      setTimeout(() => {
        onSuccess();
      }, 2000);

    } catch (err) {
      alert("Failed to create ticket");
    } finally {
      setLoading(false);
    }
  };

  // ⭐ SUCCESS SCREEN
  if (success) {
    return (
      <div className="center-message">
        <div className="success-box">
          🎉 Ticket Created Successfully
          <p>You can track it in "My Tickets"</p>
        </div>
      </div>
    );
  }

  // ⭐ CREATE FORM
  return (
    <div className="center-wrapper">
      <form className="card" onSubmit={handleSubmit}>
        <h2>Create Ticket</h2>

        <input
          placeholder="Title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />

        <textarea
          placeholder="Description"
          rows="4"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          required
        />

        <select
          value={priority}
          onChange={(e) => setPriority(e.target.value)}
        >
          <option value="LOW">LOW</option>
          <option value="MEDIUM">MEDIUM</option>
          <option value="HIGH">HIGH</option>
        </select>

        <button type="submit" disabled={loading}>
          {loading ? "Submitting..." : "Submit Ticket"}
        </button>
      </form>
    </div>
  );
}
