import { ThemeProvider } from "@emotion/react";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import React from "react";
import ReactDOM from "react-dom/client";
import { useNavigate } from "react-router";
import { BrowserRouter } from "react-router-dom";
import App from "./App";
import "./index.css";
import reportWebVitals from "./reportWebVitals";
import { useLoginStore } from "./store/useLoginStore";
import theme from "./theme/themeConfig";
import interceptorSetup from "./utils/axiosInterceptor";

interface InterceptorSetupProps {
  children: React.ReactNode;
}

// axios 인터셉터 설정 컴포넌트
const InterceptorSetup: React.FC<InterceptorSetupProps> = ({ children }) => {
  const store = useLoginStore();
  const navigate = useNavigate();
  React.useEffect(() => {
    interceptorSetup(store, navigate);
  }, [store, navigate]);

  return <>{children}</>;
};

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);

const queryClient = new QueryClient();

root.render(
  <QueryClientProvider client={queryClient}>
    <ThemeProvider theme={theme}>
      <BrowserRouter>
        <InterceptorSetup>
          <App />
        </InterceptorSetup>
      </BrowserRouter>
    </ThemeProvider>
  </QueryClientProvider>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
