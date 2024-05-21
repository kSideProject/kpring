import { ThemeProvider } from "@mui/material/styles";
import LoginBox from "../components/Auth/LoginBox";
import theme from "../theme/themeConfig";
const Login = () => {
  return (
    <div>
      <ThemeProvider theme={theme}>
        <LoginBox />
      </ThemeProvider>
    </div>
  );
};

export default Login;
