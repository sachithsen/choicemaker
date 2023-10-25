import React, { createContext, useContext, useState, useEffect } from 'react';

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);

  useEffect(() => {
    // Check if the user is authenticated, e.g., by checking a token in localStorage
    const token = localStorage.getItem('accessToken');
    if (token) {
      // Fetch user data or decode token as needed and set the user
      // Example: setUser(decodedUser);
    }
  }, []);

  const login = (userData) => {
    // Perform your login logic here and set the user
    // add to localstorage
    setUser(userData);
  };

  const logout = () => {
    // Clear the user data
    setUser(null);
    // Clear the token or do any necessary cleanup
  };

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
