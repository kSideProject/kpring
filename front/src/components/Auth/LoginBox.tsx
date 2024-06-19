import LoginIcon from "@mui/icons-material/Login";
import PersonAddAlt1Icon from "@mui/icons-material/PersonAddAlt1";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import { useNavigate } from "react-router";
import { LoginValidation } from "../../hooks/LoginValidation";
import { useLoginStore } from "../../store/useLoginStore";

async function login(email: string, password: string) {
  try {
    const response = await fetch(
      "http://kpring.duckdns.org/user/api/v1/login",
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, password }),
      }
    );

    const data = await response.json();
    if (response.ok) {
      console.log("로그인 성공:", data);
      return data.data;
    } else {
      console.error("로그인 실패:", data);
      return null;
    }
  } catch (error) {
    console.error("API 호출 중 오류 발생:", error);
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
  const { setTokens } = useLoginStore();
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
      //navigate("/");
    } else {
      console.error("로그인 실패.");
      alert("로그인 실패. 이메일 혹은 비밀번호를 확인해 주세요.");
    }
  };
  const navigate = useNavigate();
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
      </div>
    </section>
  );
}

export default LoginBox;
