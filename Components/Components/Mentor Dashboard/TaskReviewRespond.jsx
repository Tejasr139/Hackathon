
import React, { useEffect, useState } from 'react';
import axios from 'axios';

const TaskReviewRespond = () => {
  const [tasks, setTasks] = useState([]);
  const [message, setMessage] = useState('');
  const [ratings, setRatings] = useState({});
  const [feedbacks, setFeedbacks] = useState({});

  useEffect(() => {
   fetchTasksForReview();
  }, []);

 const fetchTasksForReview = async () => {
  const token = localStorage.getItem("token"); // ✅ get token from localStorage

  try {
    const response = await axios.get("http://localhost:8083/api/tasks/mentor/pending", {
      headers: {
        Authorization: `Bearer ${token}` // ✅ attach token to request
      }
    });
    setTasks(response.data);
  } catch (error) {
    console.error("Error fetching review tasks:", error);
    setMessage("Failed to load tasks.");
  }
};

  const handleInputChange = (taskId, type, value) => {
     if (type === "rating") {
       setRatings({ ...ratings, [taskId]: value });
     } else {
     setFeedbacks({ ...feedbacks, [taskId]: value });
     }
  };

  const submitReview = async (taskId) => {
     const rating = ratings[taskId];
     const feedback = feedbacks[taskId];

    if (!rating || !feedback) {
      setMessage("Please provide both rating and feedback.");
      return;
    }

    try {
      await axios.post(`http://localhost:8083/api/tasks/review`, {
        taskId,
        rating,
        feedback
    }, {
    headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`
        }
    });
      setMessage("Review submitted successfully!");
      fetchTasksForReview(); // refresh
    } catch (error) {
      console.error("Review submission failed:", error);
      setMessage("Failed to submit review.");
    }
  };

  return (
    <div className="container mt-4">
      <h2>Review Tasks from Mentees</h2>
      {message && <p className="alert alert-info">{message}</p>}
      {tasks.length === 0 ? (
        <p>No tasks pending review.</p>
      ) : (
        tasks.map((task) => (
          <div key={task.id} className="card mb-3">
            <div className="card-body">
              <h5 className="card-title">{task.title}</h5>
              <p className="card-text"><strong>Description:</strong> {task.description}</p>

              <div className="mb-2">
                <label><strong>Rating (1-5):</strong></label>
                <input
                  type="number"
                  min="1"
                  max="5"
                  className="form-control"
                  value={ratings[task.id] || ""}
                  onChange={(e) => handleInputChange(task.id, "rating", e.target.value)}
                />
              </div>

              <div className="mb-2">
                <label><strong>Feedback:</strong></label>
                <textarea
                  className="form-control"
                  value={feedbacks[task.id] || ""}
                  onChange={(e) => handleInputChange(task.id, "feedback", e.target.value)}
                />
              </div>

              <button
                className="btn btn-success"
                onClick={() => submitReview(task.id)}
              >
                Mark as Reviewed
              </button>
            </div>
          </div>
        ))
      )}
    </div>
  );
};

export default TaskReviewRespond;
