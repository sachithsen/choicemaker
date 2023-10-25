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
import Chip from "@mui/material/Chip";
import FaceIcon from '@mui/icons-material/Face';
import { AuthProvider } from "./components/AuthContext";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import FastfoodIcon from "@mui/icons-material/Fastfood";
import IconButton from "@mui/material/IconButton";

function App() {
  const user = JSON.parse(localStorage.getItem("currentUser"));
  const chipStyle = {
    fontSize: '20px', 
    color: 'white',
    position: "fixed",
    top: "15px",
    right: "30px",
  };

  return (
    <div>
      <AppBar position="static">
        <Container maxWidth="xl">
          <Toolbar disableGutters>
            <FastfoodIcon sx={{ display: { xs: "none", md: "flex" }, mr: 1 }} />
            <Typography
              variant="h6"
              noWrap
              component="a"
              href="#"
              sx={{
                mr: 2,
                display: { xs: "none", md: "flex" },
                fontFamily: "monospace",
                fontWeight: 700,
                letterSpacing: ".3rem",
                color: "inherit",
                textDecoration: "none",
              }}
            >
              CHOICEMAKER
            </Typography>

            <Box sx={{ flexGrow: 1, display: { xs: "flex", md: "none" } }}>
              <IconButton
                size="large"
                aria-label="account of current user"
                aria-controls="menu-appbar"
                aria-haspopup="true"
                color="warning"
              ></IconButton>
            </Box>
            <Typography
              variant="h5"
              noWrap
              component="a"
              href="#"
              sx={{
                mr: 2,
                display: { xs: "flex", md: "none" },
                flexGrow: 1,
                fontFamily: "monospace",
                fontWeight: 700,
                letterSpacing: ".3rem",
                color: "inherit",
                textDecoration: "none",
              }}
            >
              CHOICEMAKER
            </Typography>
            <Chip icon={<FaceIcon color="warning"/>} label={user.username} variant="outlined" color="default" style={chipStyle}/>
          </Toolbar>
        </Container>
      </AppBar>
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
    </div>
  );
}

export default App;
