import { Box } from '@mui/material'
import React, { useMemo } from 'react'

interface DropDownProps{
    dropDownItems : string[];
}

const DropDown: React.FC<DropDownProps> = ({dropDownItems}) => {

    const dropDownList = useMemo(()=>{
        return dropDownItems.map((item, index)=>{
            return <li className='h-10 leading-10'>{item}</li>
        })
    },[dropDownItems])

    
return (
    <Box className='absolute top-11 right-0 w-32 h-auto z-50 bg-dark text-pink shadow-md'>
        <ul className='flex flex-col items-center'>
            {
                dropDownList
            }
        </ul>
    </Box>
  )
}

export default DropDown