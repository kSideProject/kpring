import LoginIcon from "@mui/icons-material/Login";
import PersonAddAlt1Icon from "@mui/icons-material/PersonAddAlt1";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
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
  } = JoinValidation();

  //사용자가 입력필드의 값을 변경할때 마다 호출되서 그 필드 값과 관련되어 있는 유효성 검사 상태를 업데이트 하는 역할을 함
  const onChangeHandler = (field: string, value: string) => {
    // 두개의 인자를 받음 field : 입력 필드의 이름을 나타내는 문자열(예시: nickname, email 등), value : 사용자가 입력한 새로운 값
    setValues((prevValues) => ({ ...prevValues, [field]: value }));
    // 함수 내부에서 setValues를 호출해서 상태를 업데이트 함
    //=> 함수형 업데이트를 사용해서 이전 값을 가져와서 입력된 필드의 이름(field)에 해당하는 값을 새로운 값(value)으로 설정함
    // 결론 : 해당 필드의 값만 변경 되고, 다른 필드의 값은 그대로 유지됨
    let error = "";
    //아래부터는 입력 필드의 종류에 따라 적절하게 유효성 검사를 실행함, switch 문을 통해 field 값에 따라 처리함
    //유효성 검사 함수는 해당 값에 대해 유효성 검사를 하고 해당 상황에 따라 에러 메시지를 나타냄
    switch (field) {
      case "nickname":
        error = validateNickname(value);
        break;
      case "email":
        error = validateEmail(value);
        break;
      case "password":
        error = validatePassword(value);
        break;
      case "passwordConfirm":
        error = validatePasswordConfirm(values.password, value);
        break;
    }
    setErrors((prevErrors) => ({ ...prevErrors, [`${field}Error`]: error }));
    //에러메세지가 있을 경우 이 메세지를 상태에 반영하기 위해 setErrors를 호출함
    //에러 상태도 이전 상태를 기반으로 업데이트 함. 위의 주석 처럼 해당 필드의 메세지만 변경 되고, 다른 필드의 에러메세지는 변경하지 않음
  };

  const clickSubmitHandler = (e: React.FormEvent) => {
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
        alert("회원가입 성공!");
        setValues({
          nickname: "",
          email: "",
          password: "",
          passwordConfirm: "",
        });
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
          bgcolor="#fde2f34d
          "
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
            onChange={(e) => onChangeHandler("nickname", e.target.value)}
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
            onChange={(e) => onChangeHandler("email", e.target.value)}
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
            onChange={(e) => onChangeHandler("password", e.target.value)}
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
            onChange={(e) => onChangeHandler("passwordConfirm", e.target.value)}
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
