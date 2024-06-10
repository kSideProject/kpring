import { Input } from "@mui/material";

import React, { useState } from "react";
import useModal from "../../hooks/Modal";

import axios from "axios";

const CreateServerForm = () => {
  const { closeModal, openModal } = useModal();
  const [jwtToken, setJwtToken] = useState("");

  console.log(jwtToken);

  const onSubmitHandler = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const SERVER_URL = "http://localhost/server/api/v1/server";

    try {
      const res = await axios.post(
        SERVER_URL,
        {
          serverName: "TEST",
        },
        {
          headers: {
            Authorization: `Bearer ${jwtToken}`,
          },
        }
      );
      console.log(res);
    } catch (error) {
      console.error(error);
    }
  };

  const tempLogin = async () => {
    const SERVER_URL = "http://localhost/user/api/v1/login";

    try {
      const res = await axios.post(SERVER_URL, {
        email: "test@email.com",
        password: "tesT@1234",
      });
      setJwtToken(res.data.data.accessToken);
    } catch (error) {
      console.error(error);
    }
  };

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
      <button onClick={() => tempLogin()}>login</button>
    </div>
  );
};

export default CreateServerForm;
