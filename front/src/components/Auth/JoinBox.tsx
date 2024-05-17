import LoginIcon from "@mui/icons-material/Login";
import PersonAddAlt1Icon from "@mui/icons-material/PersonAddAlt1";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
function JoinBox() {
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
        >
          <h2 className="text-center text-2xl font-bold text-primary mt-[5px] mb-[10px]">
            환영합니다!
          </h2>{" "}
          <TextField
            required
            id="user-name"
            label="닉네임"
            type="text"
            placeholder="닉네임을 입력해주세요."
            variant="standard"
            size="small"
          />
          <TextField
            required
            id="user-email"
            label="이메일"
            placeholder="이메일을 입력해주세요."
            variant="standard"
            size="small"
          />
          <TextField
            required
            id="user-password"
            label="비밀번호"
            type="password"
            placeholder="비밀번호를 입력해주세요."
            autoComplete="current-password"
            variant="standard"
            size="small"
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
          />
          <div className="mt-[20px] flex justify-center flex-wrap ">
            <Button
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
