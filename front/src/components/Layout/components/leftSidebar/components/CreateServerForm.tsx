import {
  Box,
  Checkbox,
  FormControl,
  FormControlLabel,
  FormGroup,
  FormLabel,
  Input,
} from "@mui/material";
import React, { useEffect, useState } from "react";
import { JwtPayload, jwtDecode } from "jwt-decode";
import ServerThemeSelector from "../../../../Server/ServerThemeSelector";
import { CategoriesType, ServerType } from "../../../../../types/server";
import { useThemeStore } from "../../../../../store/useThemeStore";
import useUpdatedServers from "../../../../../hooks/UpdatedServer";
import TextInput from "../../../../common/input/TextInput";

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
  const onSubmitHandler = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

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
    <div className="">
      <p className="text-lg text-center">새로운 서버 생성</p>

      <form onSubmit={onSubmitHandler}>
        <TextInput
          lable="서버이름"
          value={serverName}
          type="text"
          onChange={onChangeServerName}
          placeholder=""
        />

        <TextInput
          lable="서버카테고리"
          value={serverName}
          type=""
          onChange={onChangeServerName}
          placeholder=""
        />

        <Box sx={{ display: "flex", flexDirection: "column", gap: "10px" }}>
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

          <button color="bg-sky-600">서버생성</button>
        </Box>
      </form>
    </div>
  );
};

export default CreateServerForm;
