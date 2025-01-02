import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import reportWebVitals from "./reportWebVitals";
import { useLoginStore } from "./store/useLoginStore";
import interceptorSetup from "./api/axios/axiosInterceptor";
import { RouterProvider } from "react-router-dom";
import router from "./router";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);

const queryClient = new QueryClient();

// axios 인터셉터 설정 컴포넌트
const InterceptorSetup = () => {
  const store = useLoginStore();

  React.useEffect(() => {
    interceptorSetup(store);
  }, [store]);

  return null;
};

root.render(
  <QueryClientProvider client={queryClient}>
    <RouterProvider router={router} />
    <InterceptorSetup />
  </QueryClientProvider>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
