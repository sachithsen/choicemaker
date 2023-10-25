import React from "react";
import {
  BrowserRouter as Router,
  Route,
  Routes,
  Navigate,
} from "react-router-dom";
import Login from "./components/Login";
import Home from "./components/Home/Home";
import Room from "./components/Room";
import PrivateRoute from "./components/PrivateRoute";
import { AuthProvider } from "./components/AuthContext";

function App() {
  const isAuthenticated = localStorage.getItem("accessToken");

  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/" element={<Home />} />
          {/* <PrivateRoute
          path="/"
          component={Home}
          isAuthenticated={isAuthenticated}
        /> */}
          {/* <Navigate from="/" to="/login" /> */}
          <Route path="/session" element={<Room />} />
          {/* <PrivateRoute
          path="/room"
          component={Room}
          isAuthenticated={isAuthenticated}
        /> */}
          {/* <Navigate from="/" to="/login" /> */}
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
