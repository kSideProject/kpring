import { useState } from "react";
import type { JoinBoxValidateErrors, JoinBoxValues } from "../types/join";
export const JoinValidation = () => {
  const [values, setValues] = useState<JoinBoxValues>({
    nickname: "",
    email: "",
    password: "",
    passwordConfirm: "",
  });

  const [errors, setErrors] = useState<JoinBoxValidateErrors>({
    nicknameError: "",
    emailError: "",
    passwordError: "",
    passwordConfirmError: "",
  });

  const validateNickname = (nickname: string) => {
    if (!nickname) return "닉네임을 입력해주세요.";
    if (nickname.length < 2) return "닉네임은 최소 2글자 이상이어야 합니다.";
    return "";
  };

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

  const validatePasswordConfirm = (
    password: string,
    passwordConfirm: string
  ) => {
    if (!passwordConfirm) return "비밀번호 확인을 입력해주세요.";
    if (password !== passwordConfirm) return "비밀번호가 일치하지 않습니다.";
    return "";
  };

  return {
    values,
    setValues,
    errors,
    setErrors,
    validateNickname,
    validateEmail,
    validatePassword,
    validatePasswordConfirm,
  };
};
