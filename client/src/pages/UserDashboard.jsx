// import "../styles/dashboard.css";
import { useNavigate } from "react-router-dom";

export default function UserDashboard() {

  const navigate = useNavigate();
  const userName = localStorage.getItem("userName");

  const logout = () => {
    localStorage.clear();
    navigate("/");
  };

  return (
    <div className="dashboard">
      <aside className="sidebar">
        <h2>USER</h2>

        <button>Create Ticket</button>
        <button>My Tickets</button>

        <button className="logout" onClick={logout}>Logout</button>
      </aside>

      <main className="content">
        <h1>Welcome, {userName}</h1>
        <p>Create and track your support tickets</p>
      </main>
    </div>
  );
}
