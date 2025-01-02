import { JoinFormValues } from "@/types/user";
import useFormValidator from "../common/useFromValidator";

export const useJoinValidation = () => {
  const initialValues: JoinFormValues = {
    email: "",
    nickname: "",
    password: "",
    passwordConfirm: "",
  };

  const validators = {
    email: (value: string) => {
      if (!value) return "이메일을 입력해주세요.";
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      return emailRegex.test(value) ? "" : "올바른 이메일 형식이 아닙니다.";
    },

    nickname: (value: string) => {
      if (!value) return "닉네임을 입력해주세요.";
      return value.length >= 2 ? "" : "닉네임은 최소 2글자 이상이어야 합니다.";
    },

    password: (value: string) => {
      if (!value) return "비밀번호를 입력해주세요.";
      const passwordRegex =
        /^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9!@#$]{8,15}$/;

      return passwordRegex.test(value)
        ? ""
        : "비밀번호는 최소 8자에서 15자 사이, 대문자와 소문자, 숫자가 포함되어야 하며, 특수문자 ! @ # $도 사용할 수 있습니다.";
    },

    passwordConfirm: (value: string, formValues?: JoinFormValues) => {
      if (!value) return "비밀번호 확인을 입력해주세요.";
      if (formValues && value !== formValues.password)
        return "비밀번호가 일치하지 않습니다.";
      return "";
    },
  };

  return useFormValidator<JoinFormValues>(initialValues, validators);
};
