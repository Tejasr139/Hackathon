import React, { useState } from "react";
import axios from "axios";
import { useLocation, useNavigate } from "react-router-dom";
import "./CodeCompiler.css";

const CodeCompiler = () => {
  const [code, setCode] = useState("");
  const [output, setOutput] = useState("");
  const [selectedLanguage, setSelectedLanguage] = useState(54); 

  const navigate = useNavigate();
  const userEmail = localStorage.getItem("email");
  const token = localStorage.getItem("token");

  const { state } = useLocation();
  const taskDescription = state?.taskDescription || "No task description available.";
  const taskTitle = state?.taskTitle || "Untitled Task";

  const languages = [
    { id: 54, name: "C++ (GCC 9.2.0)" },
    { id: 62, name: "Java (OpenJDK 13.0.1)" },
    { id: 71, name: "Python (3.8.1)" },
    { id: 63, name: "JavaScript (Node.js 12.14.0)" },
  ];

  const handleRun = async () => {
    setOutput("⏳ Compiling...");
    const encodedCode = btoa(code);

    const payload = {
      source_code: encodedCode,
      language_id: selectedLanguage,
    };

    try {
      const { data } = await axios.post(
        "https://judge0-ce.p.rapidapi.com/submissions?base64_encoded=true&wait=true",
        payload,
        {
          headers: {
            "Content-Type": "application/json",
            "X-RapidAPI-Key": "54e8ef72d0msh3045a8ed2f55454p137012jsn7a87f1167595",
            "X-RapidAPI-Host": "judge0-ce.p.rapidapi.com",
          },
        }
      );

      const result =
        data.stdout || data.stderr || data.compile_output || "No output";
      setOutput(atob(result));
    } catch (error) {
      console.error(error);
      setOutput("❌ Error compiling code.");
    }
  };

  const handleSubmit = async () => {
    const submission = {
      menteeEmail: userEmail,
      taskDescription: taskDescription,
      language: languages.find((l) => l.id === selectedLanguage)?.name,
      code: code,
      output: output,
    };

    console.log("📤 Submitting the following data to backend:");
    console.log(submission);

    try {
      await axios.post("http://localhost:8083/api/code/submit", submission, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });

      alert("✅ Code Submitted!");
      navigate("/mentee/dashboard");
    } catch (error) {
      console.error("❌ Error submitting code:", error);
      alert("❌ Error submitting code.");
    }
  };

  return (
    <div className="container py-5">
      <div className="card shadow-lg border-0 rounded-4">
        <div className="card-body">
          <h2 className="mb-4 text-center text-primary fw-bold">
            💻 Online Code Compiler
          </h2>

          <div className="alert alert-info">
            <h5 className="mb-2"><strong>📝 {taskTitle}</strong></h5>
            <p className="mb-0">{taskDescription}</p>
          </div>

          <div className="mb-3">
            <label className="form-label fw-bold">Choose Language:</label>
            <select
              className="form-select"
              value={selectedLanguage}
              onChange={(e) => setSelectedLanguage(Number(e.target.value))}
            >
              {languages.map((lang) => (
                <option key={lang.id} value={lang.id}>
                  {lang.name}
                </option>
              ))}
            </select>
          </div>

          <div className="mb-3">
            <label className="form-label fw-bold">Write Your Code:</label>
            <textarea
              className="form-control code-box"
              rows="10"
              value={code}
              onChange={(e) => setCode(e.target.value)}
              placeholder="// Type your code here"
            ></textarea>
          </div>

          <div className="d-grid mb-4">
            <button className="btn btn-success btn-lg run-btn" onClick={handleRun}>
              ▶️ Run Code
            </button>
          </div>

          <h5 className="fw-bold text-dark">🖨 Output:</h5>
          <pre className="output-box p-3 rounded-3 bg-light border">{output}</pre>

          <div className="d-grid mt-3">
            <button className="btn btn-primary btn-lg" onClick={handleSubmit}>
              📤 Submit Code
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CodeCompiler;
