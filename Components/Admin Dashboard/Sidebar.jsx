
import React from 'react';
import { Link } from 'react-router-dom';

function Sidebar({ role }) {
  return (
    <div className="bg-dark text-white p-3" style={{ minHeight: '100vh', width: '220px' }}>
      <h5 className="text-center">SkillHub</h5>
      <ul className="nav flex-column mt-4">
        <li className="nav-item"><Link className="nav-link text-white" to={`/${role.toLowerCase()}/dashboard`}>Dashboard</Link></li>
        {role === 'ADMIN' && (
          <>
            <li className="nav-item"><Link className="nav-link text-white" to="#">Manage Users</Link></li>
            <li className="nav-item"><Link className="nav-link text-white" to="#">Assign Mentors</Link></li>
          </>
        )}
        {role === 'MENTOR' && (
          <>
            <li className="nav-item"><Link className="nav-link text-white" to="#">My Mentees</Link></li>
            <li className="nav-item"><Link className="nav-link text-white" to="#">Assign Tasks</Link></li>
          </>
        )}
        {role === 'MENTEE' && (
          <>
            <li className="nav-item"><Link className="nav-link text-white" to="#">My Tasks</Link></li>
            <li className="nav-item"><Link className="nav-link text-white" to="#">Submit Work</Link></li>
          </>
        )}
      </ul>
    </div>
  );
}

export default Sidebar;
