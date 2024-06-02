import LoginIcon from "@mui/icons-material/Login";
import PersonAddAlt1Icon from "@mui/icons-material/PersonAddAlt1";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import axios from "axios";
import { useNavigate } from "react-router";
import { JoinValidation } from "../../hooks/JoinValidation";
function JoinBox() {
  const {
    values,
    setValues,
    errors,
    setErrors,
    validateNickname,
    validateEmail,
    validatePassword,
    validatePasswordConfirm,
    validators,
  } = JoinValidation();

  const onChangeHandler = (
    field: string,
    event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const value = event.target.value;
    const error = validators[field](value);
    setValues((prevValues) => ({ ...prevValues, [field]: value }));
    setErrors((prevErrors) => ({ ...prevErrors, [`${field}Error`]: error }));
  };
  const submitJoin = async () => {
    try {
      const response = await axios.post(
        "http://localhost:30002/api/v1/user",
        {
          email: values.email,
          password: values.password,
          username: values.nickname,
        },
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      // 회원가입 성공
      alert("회원가입 성공!");
      console.log("회원가입 성공:", response.data);

      // 입력 폼 초기화
      setValues({
        nickname: "",
        email: "",
        password: "",
        passwordConfirm: "",
      });
    } catch (error) {
      if (axios.isAxiosError(error) && error.response) {
        alert(`Error: ${error.response.data.message}`);
        console.log("회원가입 오류:", error.response.data);
      } else {
        alert("회원가입 과정에서 문제가 발생했습니다.");
        console.log("회원가입 중 예상치 못한 오류 발생", error);
      }
    }
  };

  const clickSubmitHandler = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const nicknameError = validateNickname(values.nickname);
    const emailError = validateEmail(values.email);
    const passwordError = validatePassword(values.password);
    const passwordConfirmError = validatePasswordConfirm(
      values.password,
      values.passwordConfirm
    );

    setErrors({
      nicknameError,
      emailError,
      passwordError,
      passwordConfirmError,
    });

    // setState가 비동기적으로 업데이트되어서 업데이트 완료 후 검사하도록 처리
    setTimeout(() => {
      // 유효성 검사를 해서 모든 에러가 없을때만 실행이 되고 alert를 통해 사용자에게 성공 메세지를 보여줌
      if (
        !nicknameError &&
        !emailError &&
        !passwordError &&
        !passwordConfirmError
      ) {
        submitJoin();
      }
    }, 0);
  };

  const navigation = useNavigate();

  return (
    <section className="flex justify-center mt-[200px]">
      <div className="mt-[10px] w-[400px] text-center">
        <Box
          component="form"
          sx={{
            "& .MuiTextField-root": { mb: 3, width: "90%" },
          }}
          noValidate
          autoComplete="off"
          bgcolor="#fde2f34d"
          border="1px solid #e4d4e7"
          padding="20px"
          onSubmit={clickSubmitHandler}
        >
          <h2 className="text-center text-2xl font-bold text-primary mt-[5px] mb-[10px]">
            환영합니다!
          </h2>
          <TextField
            required
            id="user-name"
            label="닉네임"
            type="text"
            placeholder="닉네임을 입력해주세요."
            variant="standard"
            autoComplete="username"
            size="small"
            value={values.nickname}
            onChange={(e) => onChangeHandler("nickname", e)}
            error={!!errors.nicknameError}
            helperText={errors.nicknameError}
          />
          <TextField
            required
            id="user-email"
            label="이메일"
            placeholder="이메일을 입력해주세요."
            variant="standard"
            autoComplete="email"
            size="small"
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
            placeholder="대문자와 소문자, 숫자, 특수문자 포함 8~15자"
            autoComplete="current-password"
            variant="standard"
            size="small"
            value={values.password}
            onChange={(e) => onChangeHandler("password", e)}
            error={!!errors.passwordError}
            helperText={errors.passwordError}
          />
          <TextField
            required
            id="user-password-confirm"
            label="비밀번호 확인"
            type="password"
            placeholder="동일한 비밀번호를 입력해주세요."
            autoComplete="current-password"
            variant="standard"
            size="small"
            value={values.passwordConfirm}
            onChange={(e) => onChangeHandler("passwordConfirm", e)}
            error={!!errors.passwordConfirmError}
            helperText={errors.passwordConfirmError}
          />
          <div className="mt-[20px] flex justify-center flex-wrap ">
            <Button
              type="submit"
              variant="contained"
              color="secondary"
              startIcon={<PersonAddAlt1Icon />}
              sx={{ width: "90%", mb: "20px" }}
            >
              회원가입
            </Button>
            <Button
              variant="text"
              startIcon={<LoginIcon />}
              sx={{ width: "50%" }}
              onClick={() => navigation("/login")}
            >
              로그인 하러가기
            </Button>
          </div>
        </Box>
      </div>
    </section>
  );
}

export default JoinBox;
