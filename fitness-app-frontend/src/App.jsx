
import './App.css'
import { Button, Box } from "@mui/material"
import { useContext, useEffect, useState } from 'react';
import { BrowserRouter as Router, Navigate, Route, Routes, useLocation } from "react-router";
import { useDispatch } from 'react-redux';
import { AuthContext } from 'react-oauth2-code-pkce';
import { setCredentials, logout } from './store/authSlice';
import ActivityForm from './componenets/ActivityForm';
import ActivityList from './componenets/ActivityList';
import ActivityDetail from './componenets/ActivityDetail';
const ActivitiesPage = () =>{
  return(
  <Box component="section" sx={{ p: 2, border: '1px dashed grey' }}>
      <p>Hello </p>
      <ActivityForm onActivitiesAdded = {()=> window.location.reload()} />
      <ActivityList />
  </Box>
  );
}


function App() {
  const {token , tokenData , logIn,
    logOut, isAuthenticated
  } = useContext(AuthContext);

  const dispatch = useDispatch();
  const [authReady , setAuthReady]  = useState(false);
  
  useEffect(() => {
    if (token) {
      dispatch(setCredentials({
        token,
        user: tokenData,
        
        
      }));
      setAuthReady(true) ; 
    } else {
      dispatch(logout())
    }
  } ,
   [token , tokenData , dispatch])

  return (
    <Router>
      { !token ? 
      (<Button variant="contained" color="primary"
      onClick={()=>{
        logIn();
      } }>login</Button> ) : 
      ( 
        <Box component="section" sx={{ p: 2, border: '1px dashed grey' }}>

          <Routes>
            <Route path="/activities" element={<ActivitiesPage /> } />
            <Route path="/activities/:id" element={<ActivityDetail /> } />
            <Route path="/" element={token ? <Navigate to="/activities"  replace /> : <div>welcome, login</div> } />
          </Routes>
    </Box>
      ) }

      
    </Router>
  )
}

export default App
