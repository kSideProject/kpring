import { LoginFormValues } from "../../types/user";
import useFormValidator from "../common/useFromValidator";

export const useLoginValidator = () => {
  const initialValues: LoginFormValues = {
    email: "",
    password: "",
  };

  const validators = {
    email: (value: string) => {
      if (!value) return "이메일을 입력해주세요.";
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      return emailRegex.test(value) ? "" : "올바른 이메일 형식이 아닙니다.";
    },

    password: (value: string) => {
      if (!value) return "비밀번호를 입력해주세요.";
      const passwordRegex =
        /^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9!@#$]{8,15}$/;
      return passwordRegex.test(value)
        ? ""
        : "비밀번호는 최소 8자에서 15자 사이, 대문자와 소문자, 숫자가 포함되어야 하며, 특수문자 ! @ # $도 사용할 수 있습니다.";
    },
  };

  return useFormValidator<LoginFormValues>(initialValues, validators);
};
