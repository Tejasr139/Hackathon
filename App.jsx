// App.jsx
import { BrowserRouter as Router, Routes, Route, Navigate, useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import RegisterLogin from './Components/RegisterLogin';
import AdminDashboard from './Components/Admin Dashboard/AdminDashboard';
import MentorDashboard from './Components/Mentor Dashboard/MentorDashboard';
import MenteeDashboard from './Components/Mentee Dashboard/MenteeDashboard';
import Logout from './Components/Logout';

import TaskReview from './Components/Mentee Dashboard/TaskReview';
import CodeCompiler from './Components/Mentee Dashboard/CodeCompiler';

function App() {
  const [role, setRole] = useState(localStorage.getItem('role'));
  const [token, setToken] = useState(localStorage.getItem('token'));

  useEffect(() => {
    const checkAuth = () => {
      setRole(localStorage.getItem('role'));
      setToken(localStorage.getItem('token'));
    };

    // Listen for changes in localStorage (when login sets them)
    window.addEventListener('storage', checkAuth);
    return () => window.removeEventListener('storage', checkAuth);
  }, []);

  return (
    <Router>
      <Routes>
        <Route path="/registerlogin" element={<RegisterLogin setRole={setRole} />} />
        <Route path="/logout" element={<Logout />} />

        <Route
          path="/admin/dashboard"
          element={token && role === 'ADMIN' ? <AdminDashboard /> : <Navigate to="/registerlogin" />}
        />
        <Route
          path="/mentor/dashboard"
          element={token && role === 'MENTOR' ? <MentorDashboard /> : <Navigate to="/registerlogin" />}
        />
        <Route
          path="/mentee/dashboard"
          element={token && role === 'MENTEE' ? <MenteeDashboard /> : <Navigate to="/registerlogin" />}
        />

        <Route path="*" element={<Navigate to="/registerlogin" />} />

        {/* <Route path="/" element={<TaskReview />} /> */}
         <Route path="/code-compiler" element={<CodeCompiler />} />

        
      </Routes>
    </Router>
  );
}

export default App;
