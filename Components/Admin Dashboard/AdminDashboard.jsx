
import React from 'react';
import Sidebar from './Sidebar';
import Navbar from './Navbar';

function AdminDashboard() {
  return (
    <div className="d-flex">
      <Sidebar role="ADMIN" />
      <div className="flex-grow-1 p-3">
        <Navbar />
        <h3>Welcome, Admin</h3>
        <p>You can manage users, assign mentors, and view platform statistics here.</p>
      </div>
    </div>
  );
}

export default AdminDashboard;