
import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function RegisterLogin({ setRole }) {
  const navigate = useNavigate();
  const [message, setMessage] = useState('');
  const [showLogin, setShowLogin] = useState(false);

  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    role: 'MENTEE'
  });

  const [loginData, setLoginData] = useState({
    email: '',
    password: ''
  });

  const handleRegisterChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleLoginChange = (e) => {
    const { name, value } = e.target;
    setLoginData(prev => ({ ...prev, [name]: value }));
  };

  const handleRegisterSubmit = async () => {
    try {
      const response = await axios.post('http://localhost:8083/api/auth/register', formData);
      setMessage(response.data.message || "Registration successful! Please login.");
      setShowLogin(true);
    } catch (err) {
      setMessage('Registration failed. ' + err?.response?.data?.message);
    }
  };

  const handleLoginSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8083/api/auth/login', loginData);
      const { token, role, email } = response.data;

      // Store in localStorage
      localStorage.setItem('token', token);
      localStorage.setItem('role', role);
      localStorage.setItem('email', email);

      // Update App state
      if (setRole) setRole(role);

      // Redirect based on role
      if (role === 'ADMIN') navigate('/admin/dashboard');
      else if (role === 'MENTOR') navigate('/mentor/dashboard');
      else if (role === 'MENTEE') navigate('/mentee/dashboard');
      else setMessage('Unknown role');
    } catch (error) {
      setMessage('Invalid credentials');
    }
  };

  return (
    <section className="h-100 bg-dark">
      <div className="container py-5 h-100">
        <div className="row d-flex justify-content-center align-items-center h-100">
          <div className="col">
            <div className="card card-registration my-4">
              <div className="row g-0">
                <div className="col-xl-6 d-none d-xl-block">
                  <img
                    src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-registration/img4.webp"
                    alt="Sample"
                    className="img-fluid"
                    style={{ borderTopLeftRadius: ".25rem", borderBottomLeftRadius: ".25rem" }}
                  />
                </div>
                <div className="col-xl-6">
                  <div className="card-body p-md-5 text-black">
                    <h3 className="mb-4 text-uppercase">{showLogin ? 'SkillHub Login' : 'SkillHub Registration'}</h3>

                    {message && <div className="alert alert-info">{message}</div>}

                    {showLogin ? (
                      <form onSubmit={handleLoginSubmit}>
                        <div className="mb-4">
                          <input
                            type="email"
                            name="email"
                            className="form-control form-control-lg"
                            placeholder="Email"
                            value={loginData.email}
                            onChange={handleLoginChange}
                            required
                          />
                        </div>
                        <div className="mb-4">
                          <input
                            type="password"
                            name="password"
                            className="form-control form-control-lg"
                            placeholder="Password"
                            value={loginData.password}
                            onChange={handleLoginChange}
                            required
                          />
                        </div>
                        <div className="d-flex justify-content-between pt-3">
                          <button type="button" className="btn btn-light btn-lg" onClick={() => setShowLogin(false)}>
                            Back to Register
                          </button>
                          <button type="submit" className="btn btn-primary btn-lg">Login</button>
                        </div>
                      </form>
                    ) : (
                      <>
                        <div className="row">
                          <div className="col-md-6 mb-4">
                            <input
                              type="text"
                              name="firstName"
                              className="form-control form-control-lg"
                              placeholder="First name"
                              value={formData.firstName}
                              onChange={handleRegisterChange}
                              required
                            />
                          </div>
                          <div className="col-md-6 mb-4">
                            <input
                              type="text"
                              name="lastName"
                              className="form-control form-control-lg"
                              placeholder="Last name"
                              value={formData.lastName}
                              onChange={handleRegisterChange}
                              required
                            />
                          </div>
                        </div>

                        <div className="mb-4">
                          <input
                            type="email"
                            name="email"
                            className="form-control form-control-lg"
                            placeholder="Email"
                            value={formData.email}
                            onChange={handleRegisterChange}
                            required
                          />
                        </div>

                        <div className="mb-4">
                          <input
                            type="password"
                            name="password"
                            className="form-control form-control-lg"
                            placeholder="Password"
                            value={formData.password}
                            onChange={handleRegisterChange}
                            required
                          />
                        </div>

                        <div className="mb-4">
                          <label className="form-label">Select Role</label>
                          <select
                            name="role"
                            className="form-select form-select-lg"
                            value={formData.role}
                            onChange={handleRegisterChange}
                          >
                            <option value="MENTEE">Mentee</option>
                            <option value="MENTOR">Mentor</option>
                            <option value="ADMIN">Admin</option>
                          </select>
                        </div>

                        <div className="d-flex justify-content-between pt-3">
                          <button type="button" className="btn btn-light btn-lg"
                            onClick={() => setFormData({ firstName: '', lastName: '', email: '', password: '', role: 'MENTEE' })}>
                            Reset
                          </button>
                          <button type="button" className="btn btn-success btn-lg" onClick={handleRegisterSubmit}>
                            Register
                          </button>
                          <button type="button" className="btn btn-outline-primary btn-lg" onClick={() => setShowLogin(true)}>
                            Login
                          </button>
                        </div>
                      </>
                    )}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}

export default RegisterLogin;
