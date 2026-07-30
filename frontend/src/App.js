import { useState } from "react";
import RoadmapApp from "./roadmapApp.js";
import FinalsApp from "./finalsApp.js";
import "./App.css";

function App() {
  const [selectedApp, setSelectedApp] = useState(null);

  if (selectedApp === "roadmap") {
    return (
      <>
        <button className="btn-back" onClick={() => setSelectedApp(null)}>← Back</button>
        <RoadmapApp />
      </>
    );
  }

  if (selectedApp === "finals") {
    return (
      <>
        <button className="btn-back" onClick={() => setSelectedApp(null)}>← Back</button>
        <FinalsApp />
      </>
    );
  }

  return (
    <div className="home-page">
      <h1>McGill Tools</h1>

      <button onClick={() => setSelectedApp("roadmap")}>
        Roadmap Generator
      </button>

      <button onClick={() => setSelectedApp("finals")}>
        Finals Information
      </button>
    </div>
  );
}

export default App;