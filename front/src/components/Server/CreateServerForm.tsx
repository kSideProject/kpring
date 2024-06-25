import {
  Button,
  InputLabel,
  MenuItem,
  Select,
  SelectChangeEvent,
} from "@mui/material";
import React, { useEffect, useState } from "react";
import { JwtPayload, jwtDecode } from "jwt-decode";
import axios from "axios";
import useFetchServers from "../../hooks/FetchServer";

interface UserIdJwtPayload extends JwtPayload {
  userId: string;
}

const CreateServerForm = () => {
  const token = localStorage.getItem("dicoTown_AccessToken");
  const [serverInfo, setServerInfo] = useState({
    serverName: "",
    userId: "",
    theme: "",
    categories: "",
  });

  useFetchServers(token);

  // 페이지가 로드 되었을 때, userId를 jwt-token에 추출해오기
  useEffect(() => {
    try {
      if (token) {
        const decoded = jwtDecode<UserIdJwtPayload>(token);
        setServerInfo((prevServerInfo) => ({
          ...prevServerInfo,
          userId: decoded.userId,
        }));
      }
    } catch (error) {
      console.log(error);
    }
  }, [token]);

  const handleChange = (e: SelectChangeEvent) => {
    setServerInfo((prevServerInfo) => ({
      ...prevServerInfo,
      categories: e.target.value,
    }));
  };

  const onSubmitHandler = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const SERVER_URL = "http://kpring.duckdns.org/server/api/v1/server";

    try {
      const res = await axios.post(
        SERVER_URL,
        {
          serverName: "yoyo",
          // userId: serverInfo.userId,
          theme: null,
          categories: null,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            // userId: serverInfo.userId,
          },
        }
      );
      console.log(res);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div>
      <h2>새로운 서버 생성</h2>
      <form onSubmit={onSubmitHandler}>
        <InputLabel>
          <Select
            labelId="demo-simple-select-label"
            label="서버 카테고리"
            value={serverInfo.categories}
            onChange={handleChange}>
            <MenuItem value={"personal"}>개인</MenuItem>
            <MenuItem value={"game"}>게임</MenuItem>
            <MenuItem value={"edu"}>학습</MenuItem>
          </Select>
        </InputLabel>
        <Button type="submit">서버생성</Button>
      </form>
    </div>
  );
};

export default CreateServerForm;
