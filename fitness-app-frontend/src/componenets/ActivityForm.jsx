import React from 'react'
import { Box, FormControl } from '@mui/material';
import { useState } from 'react';
import {InputLabel, Select, MenuItem,TextField,Button} from '@mui/material' ;
import { addActivity } from '../services/api';
const ActivityForm = ({onActivityAdded}) => {

  const [activity , setActivity] = useState({
    type: "RUNNING",
    duration: '', 
    caloriesBurned: '' , 
    additionalMetrics: {}
  })
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await addActivity(activity) ; 
      onActivityAdded() ;
      setActivity({
        type: "RUNNING",
        duration: '',
        caloriesBurned: ''
      }) ;
    } catch (error) {
      console.error('Error adding activity:', error);
    }
  }

  return (

    <Box component="section" onSubmit={handleSubmit} sx={{mb: 4 }}>
      <FormControl  fullWidth sx={{mb: 2}} >
        <InputLabel> Activity Type </InputLabel>
        <Select value={activity.type}
        onChange={(e) => setActivity({...activity, type: e.target.value}) } >

          <MenuItem  value="RUNNING"> Running </MenuItem>
          <MenuItem  value="CYCLING"> Cycling </MenuItem>
          <MenuItem  value="WALKING"> Walking </MenuItem>

        </Select>

      </FormControl>
      <TextField fullWidth label=" duration (minutes) "
       type="number" 
       sx={{mb: 4 }} 
       value={activity.duration}
       onChange={(e) => setActivity({...activity, duration: e.target.value}) }
       
       />
      <TextField fullWidth label="calories burned "
       type="number" 
       sx={{mb: 4 }} 
       value={activity.caloriesBurned}
       onChange={(e) => setActivity({...activity, caloriesBurned: e.target.value}) }
       
       />

       <Button type='submit' onSubmit={handleSubmit} variant='contained' >  Add activity </Button>


    </Box>
  )
}

export default ActivityForm