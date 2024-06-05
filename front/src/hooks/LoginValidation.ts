import { useState } from "react";

import {
  LoginBoxValidateErrors,
  LoginBoxValues,
  Validators,
} from "../types/login";
export const LoginValidation = () => {
  const [values, setValues] = useState<LoginBoxValues>({
    email: "",
    password: "",
  });

  const [errors, setErrors] = useState<LoginBoxValidateErrors>({
    emailError: "",
    passwordError: "",
  });

  const validateEmail = (email: string) => {
    if (!email) return "이메일을 입력해주세요.";
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) return "올바른 이메일 형식이 아닙니다.";
    return "";
  };

  const validatePassword = (password: string) => {
    if (!password) return "비밀번호를 입력해주세요.";
    const passwordRegex =
      /^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9!@#$]{8,15}$/;
    if (!passwordRegex.test(password))
      return "비밀번호는 최소 8자에서 15자 사이, 대문자와 소문자, 숫자가 포함되어야 하며, 특수문자 ! @ # $도 사용할 수 있습니다.";
    return "";
  };

  const validators: Validators = {
    email: validateEmail,
    password: validatePassword,
  };

  return {
    values,
    setValues,
    errors,
    setErrors,

    validateEmail,
    validatePassword,

    validators,
  };
};
