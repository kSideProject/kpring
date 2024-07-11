import LoginIcon from "@mui/icons-material/Login";
import PersonAddAlt1Icon from "@mui/icons-material/PersonAddAlt1";
import Alert from "@mui/material/Alert";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Snackbar, { SnackbarCloseReason } from "@mui/material/Snackbar";
import TextField from "@mui/material/TextField";
import axios from "axios";
import React, { SyntheticEvent, useState } from "react";
import { useNavigate } from "react-router";
import { LoginValidation } from "../../hooks/LoginValidation";
import { useLoginStore } from "../../store/useLoginStore";
import type { AlertInfo } from "../../types/join";
async function login(email: string, password: string) {
  console.log(process.env.REACT_APP_BASE_URL);
  try {
    const response = await axios.post(
      `${process.env.REACT_APP_BASE_URL}/user/api/v1/login`,
      { email, password },
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );

    const data = response.data;
    console.log("로그인 성공:", data);
    return data.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      if (error.response) {
        // 서버 응답이 있지만, 응답 코드가 2xx가 아님
        console.error("로그인 실패:", error.response.data);
      } else if (error.request) {
        // 요청이 이루어졌으나 응답을 받지 못함
        console.error("응답 없음:", error.request);
      } else {
        // 요청 설정 중에 문제 발생
        console.error("API 호출 중 오류 발생:", error.message);
      }
    } else {
      // axios 에러가 아닌 경우
      console.error("예상치 못한 오류 발생:", error);
    }
    return null;
  }
}

function LoginBox() {
  const {
    values,
    setValues,
    errors,
    setErrors,
    validateEmail,
    validatePassword,
    validators,
  } = LoginValidation();
  const [open, setOpen] = useState(false);
  const [alertInfo, setAlertInfo] = useState<AlertInfo>({
    severity: "info",
    message: "",
  });
  const { setTokens } = useLoginStore();

  const navigate = useNavigate();

  const onChangeHandler = (
    field: string,
    event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const value = event.target.value;
    const error = validators[field](value);
    setValues((prevValues) => ({ ...prevValues, [field]: value }));
    setErrors((prevErrors) => ({ ...prevErrors, [`${field}Error`]: error }));
  };

  const clickSubmitHandler = async (e: React.FormEvent) => {
    e.preventDefault();
    //console.log("폼 제출 시도:", values);
    const result = await login(values.email, values.password);
    if (result) {
      //console.log("토큰 설정:", result);
      setTokens(result.accessToken, result.refreshToken);

      setAlertInfo({
        severity: "success",
        message: "로그인 성공! 3초 후 메인 페이지로 이동합니다.",
      });
      setOpen(true);

      setTimeout(() => {
        navigate("/");
      }, 3000);
    } else {
      console.error("로그인 실패.");
      setAlertInfo({
        severity: "error",
        message: "로그인 실패. 이메일 혹은 비밀번호를 확인해 주세요.",
      });
      setOpen(true);
    }
  };

  const clickCloseHandler = (
    event: Event | SyntheticEvent<any, Event>,
    reason?: SnackbarCloseReason
  ) => {
    if (reason === "clickaway") {
      return;
    }
    setOpen(false);
  };

  return (
    <section className="flex justify-center mt-[200px]">
      <div className="mt-[30px] w-[400px] text-center">
        <Box
          component="form"
          sx={{
            "& .MuiTextField-root": { mb: 3, width: "90%" },
          }}
          noValidate
          autoComplete="off"
          bgcolor="#fde2f34d
          "
          border="1px solid #e4d4e7"
          padding="20px"
          onSubmit={clickSubmitHandler}
        >
          <h2 className="text-center text-2xl font-bold text-primary mt-[5px] mb-[10px]">
            디코타운에 어서오세요!
          </h2>
          <TextField
            required
            id="user-email"
            label="이메일"
            placeholder="이메일을 입력해주세요."
            variant="standard"
            size="small"
            autoComplete="email"
            value={values.email}
            onChange={(e) => onChangeHandler("email", e)}
            error={!!errors.emailError}
            helperText={errors.emailError}
          />
          <TextField
            required
            id="user-password"
            label="비밀번호"
            type="password"
            placeholder="비밀번호를 입력해주세요."
            autoComplete="password"
            variant="standard"
            size="small"
            value={values.password}
            onChange={(e) => onChangeHandler("password", e)}
            error={!!errors.passwordError}
            helperText={errors.passwordError}
          />
          <div className="mt-[20px] flex justify-center flex-wrap ">
            <Button
              type="submit"
              variant="contained"
              startIcon={<LoginIcon />}
              sx={{ width: "90%" }}
            >
              로그인
            </Button>

            <Button
              variant="contained"
              color="secondary"
              startIcon={<PersonAddAlt1Icon />}
              sx={{ mt: "20px", width: "90%", mb: "20px" }}
              onClick={() => navigate("/join")}
            >
              회원가입
            </Button>
          </div>
        </Box>
        <Snackbar
          open={open}
          autoHideDuration={6000}
          onClose={clickCloseHandler}
        >
          <Alert
            onClose={clickCloseHandler}
            severity={alertInfo.severity}
            sx={{ width: "100%" }}
          >
            {alertInfo.message}
          </Alert>
        </Snackbar>
      </div>
    </section>
  );
}

export default LoginBox;
