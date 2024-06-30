import {
  Box,
  Button,
  Checkbox,
  FormControl,
  FormControlLabel,
  FormGroup,
  FormLabel,
  Input,
  Radio,
  RadioGroup,
} from "@mui/material";
import React, { useEffect, useState } from "react";
import { JwtPayload, jwtDecode } from "jwt-decode";
import useUpdatedServers from "../../hooks/UpdatedServer";
import { ServerType, CategoriesType, ThemeType } from "../../types/server";
import useFetchServers from "../../hooks/FetchServer";

interface UserIdJwtPayload extends JwtPayload {
  userId: string;
}

const CreateServerForm = () => {
  const token = localStorage.getItem("dicoTown_AccessToken");
  const { mutate } = useUpdatedServers(token);

  const [serverName, setServerName] = useState("");
  const [userId, setUserId] = useState("");
  const [theme, setTheme] = useState<ThemeType | null>(null);
  const [categories, setCategories] = useState<CategoriesType[]>([]);

  // 페이지가 로드 되었을 때, userId를 jwt에서 추출.
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
        : prevCategories.filter((category) => category.id !== value)
    );
  };

  const onChangeThemeChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTheme({ id: e.target.id, name: e.target.value });
  };

  // 서버 생성 onSubmit Handler
  const onSubmitHandler = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const newServer: ServerType = {
      serverName,
      userId,
      theme: theme as ThemeType, // 수정예정
      categories, // 수정예정
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

            <RadioGroup onChange={onChangeThemeChange} row>
              <FormControlLabel
                labelPlacement="top"
                id="SERVER_THEME_001"
                value="forest"
                control={<Radio />}
                label={
                  <img
                    src="https://images.unsplash.com/photo-1718889339117-d90a40b1250b?q=80&w=1287&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                    width={100}
                    alt="숲 테마"
                  />
                }></FormControlLabel>

              <FormControlLabel
                labelPlacement="top"
                id="SERVER_THEME_002"
                value="office"
                control={<Radio />}
                label={
                  <img
                    src="https://images.unsplash.com/photo-1718889339117-d90a40b1250b?q=80&w=1287&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                    width={100}
                    alt="숲 테마"
                  />
                }></FormControlLabel>
            </RadioGroup>
          </FormControl>

          <Button type="submit">서버생성</Button>
        </Box>
      </form>
    </div>
  );
};

export default CreateServerForm;
