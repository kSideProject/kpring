import { ThemeProvider, createTheme } from "@mui/material/styles";
import JoinBox from "../components/Auth/JoinBox";
const Login = () => {
  const theme = createTheme({
    palette: {
      primary: {
        light: "#FDE2F3",
        main: "#917FB3",
        dark: "#E5BEEC",
        contrastText: "#fff",
      },
      secondary: {
        light: "#2A2F4F",
        main: "#2A2F4F",
        dark: "#E5BEEC",
        contrastText: "#fff",
      },
    },
  });
  return (
    <div>
      <ThemeProvider theme={theme}>
        <JoinBox />
      </ThemeProvider>
    </div>
  );
};

export default Login;
