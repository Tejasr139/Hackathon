import React, { useEffect, useState } from 'react';
import axios from 'axios';
import CodeCompiler from './CodeCompiler';
import TaskReview from './TaskReview';

function MenteeDashboard() {
  const [tasks, setTasks] = useState([]);
  const [message, setMessage] = useState('');
  const [searchTerm, setSearchTerm] = useState('');
  const [filteredTasks, setFilteredTasks] = useState([]);

  const [mentors, setMentors] = useState([]);
  const [connectionRequestsSent, setConnectionRequestsSent] = useState(new Set());
  const [acceptedMentors, setAcceptedMentors] = useState(new Set());

  const [activeTab, setActiveTab] = useState('dashboard');

  const token = localStorage.getItem('token');
  const menteeEmail = localStorage.getItem('email');

  const profile = {
    name: 'John Doe',
    email: menteeEmail,
  };

  const [userProfile, setUserProfile] = useState({ name: '', email: menteeEmail });

const fetchUserProfile = async () => {
  try {
    const response = await axios.get(`http://localhost:8083/api/auth/users/${menteeEmail}`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    const { firstName, lastName, email } = response.data;
    setUserProfile({ name: `${firstName} ${lastName}`, email });
  } catch (err) {
    console.error("Failed to fetch user profile", err);
  }
};

  const fetchTasks = async () => {
    if (!menteeEmail) {
      setMessage("No mentee email found");
      return;
    }
    try {
      const response = await axios.get(`http://localhost:8083/api/tasks/mentee/${menteeEmail}`, {
        headers: { Authorization: `Bearer ${token}` },
      });

       // Add 'status' field manually
    const enrichedTasks = response.data.map(task => ({
      ...task,
      status: task.completed
        ? 'COMPLETED'
        : task.reviewPending
        ? 'REVIEW_PENDING'
        : 'PENDING',
    }));


      setTasks(enrichedTasks);
      setFilteredTasks(enrichedTasks);
    } catch (err) {
      console.error('Failed to fetch tasks', err);
      setMessage('Failed to load tasks');
    }
  };

  const sendForReview = async (taskId) => {
    try {
      await axios.post(
        `http://localhost:8083/api/mentee/tasks/${taskId}/complete`,
        {},
        { headers: { Authorization: `Bearer ${token}` } }
      );
      const updatedTasks = tasks.map((task) =>
        task.id === taskId ? { ...task, completed: true } : task
      );
      setTasks(updatedTasks);
      setFilteredTasks(updatedTasks);
      setMessage("Task sent for review");
    } catch (err) {
      console.error("Error sending task for review:", err);
      setMessage("Failed to send for review");
    }
  };

  const fetchMentors = async () => {
    try {
      const response = await axios.get('http://localhost:8083/api/mentor/mentors', {
        headers: { Authorization: `Bearer ${token}` },
      });
      setMentors(response.data);
    } catch (err) {
      console.error('Failed to fetch mentors', err);
    }
  };

  const fetchSentRequests = async () => {
    try {
      const response = await axios.get(`http://localhost:8083/api/connection/sent-requests`, {
        params: { menteeEmail },
        headers: { Authorization: `Bearer ${token}` },
      });

      const sentMentorEmails = response.data.map(req => req.mentor.email);
      setConnectionRequestsSent(new Set(sentMentorEmails));
    } catch (err) {
      console.error("Failed to load sent requests", err);
    }
  };

  const fetchAcceptedMentors = async () => {
    try {
      const response = await axios.get(`http://localhost:8083/api/connection/accepted-mentors`, {
        params: { menteeEmail },
        headers: { Authorization: `Bearer ${token}` },
      });

      const mentorEmails = response.data.map(mentor => mentor.email);
      setAcceptedMentors(new Set(mentorEmails));
    } catch (err) {
      console.error("Failed to fetch accepted mentors", err);
    }
  };

  useEffect(() => {
    if (!token) return;
    fetchTasks();
    fetchMentors();
    fetchSentRequests();
    fetchAcceptedMentors();
     fetchUserProfile(); 
  }, [token]);

  useEffect(() => {
    if (!searchTerm) {
      setFilteredTasks(tasks);
    } else {
      const filtered = tasks.filter((task) =>
        task.task.toLowerCase().includes(searchTerm.toLowerCase())
      );
      setFilteredTasks(filtered);
    }
  }, [searchTerm, tasks]);

  const sendConnectionRequest = async (mentorEmail) => {
    try {
      await axios.post('http://localhost:8083/api/connection/send-request', null, {
        params: {
          menteeEmail,
          mentorEmail,
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setConnectionRequestsSent((prev) => new Set(prev).add(mentorEmail));
      alert("Request sent successfully!");
    } catch (err) {
      console.error("Failed to send connection request", err);
      alert("Failed to send request");
    }
  };

  return (
    <div className="d-flex" style={{ minHeight: '100vh' }}>
      {/* Sidebar */}
      <nav className="bg-primary text-white p-3" style={{ width: '220px', minHeight: '100vh' }}>
        <h4 className="mb-4">MentorConnect</h4>
        {/* <div className="mb-4">
          <strong>{profile.name}</strong><br />
          <small>{profile.email}</small>
        </div> */}
        <div className="mb-4">
  <strong>{userProfile.name || 'Loading...'}</strong><br />
  <small>{userProfile.email}</small>
</div>

        <ul className="nav flex-column">
          <li className="nav-item mb-2">
            <button className="btn btn-link text-white" onClick={() => setActiveTab('dashboard')}>
              🏠 Dashboard
            </button>
          </li>
          <li className="nav-item mb-2">
            <button className="btn btn-link text-white" onClick={() => setActiveTab('profile')}>
              👤 Profile
            </button>
          </li>
          <li className="nav-item mb-2">
            <button className="btn btn-link text-white" onClick={() => setActiveTab('compiler')}>
              💻 Code Compiler
            </button>
          </li>
          <li className="nav-item mt-auto">
            <a href="/logout" className="nav-link text-white">🔒 Logout</a>
          </li>
        </ul>
      </nav>

      {/* Main Content */}
      <div className="flex-grow-1">
        <nav className="navbar navbar-expand-lg navbar-light bg-light shadow-sm">
          <div className="container-fluid">
            <form className="d-flex ms-auto" role="search" onSubmit={(e) => e.preventDefault()}>
              <input
                type="search"
                className="form-control form-control-sm me-2"
                placeholder="Search tasks..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                style={{ maxWidth: '200px' }}
              />
              <button
                className="btn btn-outline-secondary btn-sm"
                onClick={() => setSearchTerm('')}
                type="button"
              >
                Clear
              </button>
            </form>
          </div>
        </nav>

        <header className="bg-white py-3 shadow-sm">
          <div className="container">
            <h2 className="mb-0">👩‍🎓 Mentee Dashboard</h2>
          </div>
        </header>

        <div className="container mt-4">
          {message && <div className="alert alert-info">{message}</div>}

          {activeTab === 'dashboard' && (
            <>
              <section className="mb-5">
              <h4 className="mb-3">📋 Your Tasks</h4>
                  {filteredTasks.length === 0 ? (
                    <div className="alert alert-warning">No tasks assigned or no tasks found matching your search.</div>
                  ) : (
                  <div className="row">
                     {filteredTasks.map((task) => (
                        <TaskReview
                        key={task.id}
                        task={task}
                        userType="mentee"
                        token={token}
                        onTaskUpdated={(taskId, newStatus) => {
                        const updatedTasks = tasks.map((t) =>
                        t.id === taskId ? { ...t, status: newStatus } : t
                      );
                    setTasks(updatedTasks);
                    setFilteredTasks(updatedTasks);
                  }}
                />
              ))}
        </div>

      )}
    </section>


              <section>
                <h4>Available Mentors</h4>
                {mentors.length === 0 ? (
                  <p>No mentors available at the moment.</p>
                ) : (
                  <table className="table table-hover">
                    <thead>
                      <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Action</th>
                      </tr>
                    </thead>
                    <tbody>
                      {mentors.map((mentor) => (
                        <tr key={mentor.email}>
                          <td>{mentor.firstName} {mentor.lastName}</td>
                          <td>{mentor.email}</td>
                          <td>{mentor.role}</td>
                          <td>
                            <button
                              className="btn btn-sm btn-primary"
                              disabled={connectionRequestsSent.has(mentor.email) || acceptedMentors.has(mentor.email)}
                              onClick={() => sendConnectionRequest(mentor.email)}
                            >
                              {acceptedMentors.has(mentor.email)
                                ? 'Connected ✅'
                                : connectionRequestsSent.has(mentor.email)
                                ? 'Request Sent'
                                : 'Send Connection Request'}
                            </button>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                )}
              </section>
            </>
          )}

          {activeTab === 'profile' && (
            <div>
              <h4>👤 Profile</h4>
              <p><strong>Name:</strong> {profile.name}</p>
              <p><strong>Email:</strong> {profile.email}</p>
            </div>
          )}

          {activeTab === 'compiler' && (
            <CodeCompiler />
          )}
        </div>
      </div>
    </div>
  );
}

export default MenteeDashboard;
