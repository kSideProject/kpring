import { Box, List, ListItem } from '@mui/material'
import React, { useMemo } from 'react'

interface DropDownProps{
    dropDownItems : string[];
}

const DropDown: React.FC<DropDownProps> = ({dropDownItems}) => {

    const dropDownList = useMemo(()=>{
        return dropDownItems.map((item, index)=>{
            return <ListItem sx={{height:'40px', lineHeight: '40px'}} key={index}>{item}</ListItem>
        })
    },[dropDownItems])

    
return (
    <Box sx={{
      position: 'absolute',
      top: '44px',
      right: '0px',
      width: '128px',
      height: 'auto',
      zIndex: '50',
      backgroundColor: '#2A2F4F',
      color: '#FDE2F3',
      boxShadow: '5px',
    }}>
        <List>{dropDownList}</List>
    </Box>
  )
}

export default DropDown