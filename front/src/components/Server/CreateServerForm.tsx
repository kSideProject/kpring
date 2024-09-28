import {
  Box,
  Button,
  Checkbox,
  FormControl,
  FormControlLabel,
  FormGroup,
  FormLabel,
  Input,
} from "@mui/material";
import React, { useEffect, useState } from "react";
import { JwtPayload, jwtDecode } from "jwt-decode";
import useUpdatedServers from "../../hooks/UpdatedServer";
import { ServerType, CategoriesType } from "../../types/server";
import ServerThemeSelector from "./ServerThemeSelector";
import { useThemeStore } from "../../store/useThemeStore";

interface UserIdJwtPayload extends JwtPayload {
  userId: string;
}

const CreateServerForm = () => {
  const token = localStorage.getItem("dicoTown_AccessToken");
  const { mutate } = useUpdatedServers(token);
  const [serverName, setServerName] = useState("");
  const [userId, setUserId] = useState("");
  const [categories, setCategories] = useState<CategoriesType[]>([]);
  const [hostName, setHostName] = useState("");

  const { selectedTheme } = useThemeStore();
  console.log(selectedTheme);

  useEffect(() => {
    if (token) {
      try {
        const decoded = jwtDecode<UserIdJwtPayload>(token);
        setUserId(decoded.userId);
      } catch (error) {
        console.log(error);
      }
    }
  }, [token]);

  // 각 Input의 onChange Handlers
  const onChangeServerName = (e: React.ChangeEvent<HTMLInputElement>) => {
    setServerName(e.target.value);
  };

  const onChangeCategories = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { id, value, checked } = e.target;
    const category = { id, name: value };
    setCategories((prevCategories) =>
      checked
        ? [...prevCategories, category]
        : prevCategories.filter((category) => category.id !== id)
    );
  };

  // 서버 생성 onSubmit Handler
  const onSubmitHandler = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const newServer: ServerType = {
      name: serverName,
      hostName,
      userId,
      theme: selectedTheme,
      categories,
    };

    mutate(newServer);
  };

  return (
    <div>
      <h2>새로운 서버 생성</h2>

      <form onSubmit={onSubmitHandler}>
        <Box sx={{ display: "flex", flexDirection: "column", gap: "10px" }}>
          <FormControl sx={{ m: 2 }}>
            <FormLabel>서버이름</FormLabel>
            <Input value={serverName} onChange={onChangeServerName}></Input>
          </FormControl>

          <FormControl sx={{ m: 2 }}>
            <FormLabel>서버 카테고리</FormLabel>
            <FormGroup onChange={onChangeCategories}>
              <FormControlLabel
                label="개인"
                control={<Checkbox value="personal" id="SERVER_CATEGORY1" />}
              />
              <FormControlLabel
                label="교육/학습"
                control={<Checkbox value="education" id="SERVER_CATEGORY2" />}
              />
              <FormControlLabel
                label="게임"
                control={<Checkbox value="game" id="SERVER_CATEGORY3" />}
              />
            </FormGroup>
          </FormControl>

          <FormControl sx={{ m: 2 }}>
            <FormLabel>서버 테마</FormLabel>
            <ServerThemeSelector />
          </FormControl>

          <Button type="submit">서버생성</Button>
        </Box>
      </form>
    </div>
  );
};

export default CreateServerForm;
