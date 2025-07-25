import React, { useEffect, useState } from 'react';
import axios from 'axios';
import TaskReviewRespond from './TaskReviewRespond';

function MentorDashboard() {
  const [mentees, setMentees] = useState([]);
  const [requests, setRequests] = useState([]);
  const [selectedMentee, setSelectedMentee] = useState(null);
  const [task, setTask] = useState('');
  const [message, setMessage] = useState('');
  const [activeTab, setActiveTab] = useState('mentees');

  const token = localStorage.getItem("token");
  const mentorEmail = localStorage.getItem("email");

  useEffect(() => {
    if (!token) return;

    const fetchMentees = async () => {
      try {
        const response = await axios.get('http://localhost:8083/api/connection/accepted-mentees', {
            params:{mentorEmail},
          headers: { Authorization: `Bearer ${token}` }
        });
        setMentees(response.data);
      } catch (err) {
        console.error('Failed to fetch mentees', err);
      }
    };

    const fetchRequests = async () => {
      try {
        const response = await axios.get(`http://localhost:8083/api/connection/requests`, {
          params: { mentorEmail },
          headers: { Authorization: `Bearer ${token}` }
        });
            console.log("Requests fetched for mentor:", response.data);
        setRequests(response.data);
      } catch (err) {
        console.error("Error fetching requests", err);
      }
    };

    fetchMentees();
    fetchRequests();
  }, [token, mentorEmail]);

  const assignTask = async () => {
    if (!selectedMentee || !task.trim()) {
      setMessage('Please select a mentee and enter a task');
      return;
    }

    try {
      await axios.post(
        'http://localhost:8083/api/mentor/assign-task',
        {
          menteeEmail: selectedMentee.email,
          task: task.trim()
        },
        {
          headers: { Authorization: `Bearer ${token}` }
        }
      );
      setMessage(`Task assigned to ${selectedMentee.firstName}`);
      setTask('');
    } catch (err) {
      console.error('Failed to assign task', err);
      setMessage('Failed to assign task. Please try again.');
    }
  };

  const respondToRequest = async (requestId, action) => {
    try {
      await axios.post(
        `http://localhost:8083/api/connection/respond-request`,
        {
          requestId,
          action
        },
        {
          headers: { Authorization: `Bearer ${token}` }
        }
      );
      setMessage(`Request ${action === 'ACCEPTED' ? 'accepted' : 'rejected'}`);
      setRequests(prev => prev.filter(r => r.id !== requestId));
    } catch (err) {
      console.error("Failed to respond to request", err);
    }
  };

  if (!token) {
    return (
      <div className="container mt-5">
        <h2>Mentor Dashboard</h2>
        <div className="alert alert-danger">You are not logged in.</div>
      </div>
    );
  }

  return (
    <div className="d-flex" style={{ minHeight: '100vh' }}>
      {/* Sidebar */}
      <nav className="bg-dark text-white p-3" style={{ width: '220px', minHeight: '100vh' }}>
        <h4 className="mb-4">MentorConnect</h4>
        <ul className="nav flex-column">
          <li className="nav-item mb-2">
            <button className="btn btn-link text-white" onClick={() => setActiveTab('mentees')}>
              👥 Mentees
            </button>
          </li>
          <li className="nav-item mb-2">
            <button className="btn btn-link text-white" onClick={() => setActiveTab('requests')}>
              📩 Requests
            </button>
          </li>

        <li className="nav-item mb-2">
            <button className="btn btn-link text-white" onClick={()=>setActiveTab('review')}>
                Review Tasks
            </button>
        </li>
          <li className="nav-item mt-auto">
            <a href="/logout" className="nav-link text-white">🔒 Logout</a>
          </li>
        </ul>
      </nav>

      {/* Main Content */}
      <div className="flex-grow-1">
        {/* Navbar */}
        <nav className="navbar navbar-expand-lg navbar-light bg-light shadow-sm px-4">
          <span className="navbar-brand">Welcome Mentor</span>
        </nav>

        <div className="container mt-4">
          {message && <div className="alert alert-info">{message}</div>}

          {activeTab === 'mentees' && (
            <>
              <h4>👥 View Mentees</h4>
              <ul className="list-group mb-3">
                {mentees.map((mentee, index) => (
                  <li
                    key={index}
                    className={`list-group-item ${selectedMentee?.email === mentee.email ? 'active' : ''}`}
                    onClick={() => setSelectedMentee(mentee)}
                    style={{ cursor: 'pointer' }}
                  >
                    {mentee.firstName} {mentee.lastName} ({mentee.email})
                  </li>
                ))}
              </ul>

              <h4>Assign Task</h4>
              <textarea
                className="form-control mb-2"
                rows="3"
                placeholder="Enter task"
                value={task}
                onChange={(e) => setTask(e.target.value)}
              />
              <button className="btn btn-primary" onClick={assignTask}>Assign Task</button>
            </>
          )}

          {activeTab === 'requests' && (
            <>
              <h4>📩 Connection Requests</h4>
              {requests.length === 0 ? (
                <p>No new requests.</p>
              ) : (
                <ul className="list-group">
                  {requests.map((req) => (
                    <li key={req.id} className="list-group-item d-flex justify-content-between align-items-center">
                      <span>
                        {req.mentee.firstName} {req.mentee.lastName} ({req.mentee.email})
                      </span>
                      <div>
                        <button className="btn btn-sm btn-success me-2" onClick={() => respondToRequest(req.id, 'ACCEPTED')}>
                          Accept
                        </button>
                        <button className="btn btn-sm btn-danger" onClick={() => respondToRequest(req.id, 'REJECTED')}>
                          Reject
                        </button>
                      </div>
                    </li>
                  ))}
                </ul>
              )}
            </>
          )}

            {activeTab === 'review' &&(
                <>
                    <h4> Tasks Awaiting Review</h4>
                    <TaskReviewRespond/>
                </>
            )}




        </div>
      </div>
    </div>
  );
}

export default MentorDashboard;
