import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom'; 
import CodeCompiler from './CodeCompiler';

const TaskReview = ({ task, userType, token, onTaskUpdated }) => {
  const [message, setMessage] = useState('');
  const [isSending, setIsSending] = useState(false);

    const navigate = useNavigate(); // 👈 Initialize navigate

     const handleCodeNow = () => {
        navigate('/code-compiler',{ state: { taskDescription: task.description, taskTitle: task.task } }); // 👈 Navigate to compiler
    };

  const handleSendForReview = async () => {
    setIsSending(true);
    try {
      const response = await axios.put(
        `http://localhost:8083/api/tasks/send-review/${task.id}`,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setMessage('Task sent for review ✅');
      onTaskUpdated(task.id, 'REVIEW_PENDING');
    } catch (error) {
      console.error('Failed to send task for review:', error);
      setMessage('Error sending task');
    } finally {
      setIsSending(false);
    }
  };

  // Only render for mentee
  if (userType !== 'mentee') return null;

  return (
    <div className="col-md-6 col-lg-4 mb-4">
      <div className={`card shadow-sm border-${task.status === 'COMPLETED' ? 'success' : 'primary'}`}>
        <div className="card-body">
          <h5 className="card-title text-dark">{task.task}</h5>
          <p className="card-text text-secondary" style={{ whiteSpace: 'pre-wrap' }}>
            {task.description}
          </p>

          {/* Show button only if task is not sent */}
          {task.status === 'PENDING' && (
            <button
              className="btn btn-sm btn-outline-primary"
              onClick={handleSendForReview}
              disabled={isSending}
            >
              🚀 {isSending ? 'Sending...' : 'Send for Review'}
            </button>
          )}


             {/* 👉 Code Now Button */}
            <button className="btn btn-outline-success mt-2" onClick={handleCodeNow}>
                 💻 Code Now
            </button>

          {task.status === 'REVIEW_PENDING' && (
            <span className="badge bg-warning text-dark">⏳ Review Pending</span>
          )}

          {task.status === 'COMPLETED' && (
            <div className="mt-2">
              <span className="badge bg-success">✅ Reviewed</span>
              {task.rating && (
                <div className="mt-1 text-muted">⭐ Rating: {task.rating}/5</div>
              )}
              {task.feedback && (
                <div className="text-muted mt-1">📝 Feedback: {task.feedback}</div>
              )}
            </div>
          )}
          {message && <p className="mt-2 text-success small">{message}</p>}
        </div>
      </div>
    </div>
  );
};

export default TaskReview;
