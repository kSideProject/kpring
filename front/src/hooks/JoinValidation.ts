import { useState } from "react";
export const JoinValidation = () => {
  const [nickname, setNickname] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [passwordConfirm, setPasswordConfirm] = useState("");

  const [nicknameError, setNicknameError] = useState("");
  const [emailError, setEmailError] = useState("");
  const [passwordError, setPasswordError] = useState("");
  const [passwordConfirmError, setPasswordConfirmError] = useState("");

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
    const passwordRegex = /^(?=.*[a-z])(?=.*\d)(?=.*[!@#$%^&*]).{8,}$/;
    if (!passwordRegex.test(password))
      return "비밀번호는 소문자, 숫자, 특수문자를 포함한 8자 이상이어야 합니다.";
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
    nickname,
    setNickname,
    email,
    setEmail,
    password,
    setPassword,
    passwordConfirm,
    setPasswordConfirm,
    nicknameError,
    setNicknameError,
    emailError,
    setEmailError,
    passwordError,
    setPasswordError,
    passwordConfirmError,
    setPasswordConfirmError,
    validateNickname,
    validateEmail,
    validatePassword,
    validatePasswordConfirm,
  };
};
