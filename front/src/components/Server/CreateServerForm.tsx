import { Button, FormControl, Input, Select } from "@mui/material";

import React from "react";
import useModal from "../../hooks/Modal";
import { Label } from "@mui/icons-material";
import axios from "axios";

const CreateServerForm = () => {
  const { closeModal, openModal } = useModal();

  const onSubmitHandler = async () => {};

  return (
    <div>
      <h2>새로운 서버 생성</h2>
      <form onSubmit={onSubmitHandler}>
        <label>서버이름</label>
        <Input type="text" required />

        {/* <FormControl defaultValue="" required>
          <Label>서버이름</Label>
          <Input />
        </FormControl>

        <FormControl defaultValue="">
          <Label>카테고리</Label>
          <Select
            defaultValue={"personal"}
            id="named-select"
            name="demo-select">
            <option value="personal">개인</option>
            <option value="game">게임</option>
            <option value="study">학습</option>
          </Select>
        </FormControl> */}
        <button type="submit">서버생성</button>
      </form>
    </div>
  );
};

export default CreateServerForm;
