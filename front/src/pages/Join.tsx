import { ThemeProvider } from "@mui/material/styles";
import JoinBox from "../components/auth/JoinBox";
import theme from "../theme/themeConfig";
const Login = () => {
  return (
    <div>
      <ThemeProvider theme={theme}>
        <JoinBox />
      </ThemeProvider>
    </div>
  );
};

export default Login;
