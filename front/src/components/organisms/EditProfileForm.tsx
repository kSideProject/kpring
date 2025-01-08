import { useEditProfileValidator } from "@/hooks/user/useEditProfileValidator";
import React, { useEffect } from "react";
import FormField from "../molecules/FormField";
import Button from "../atoms/Button";
import { useNavigate } from "react-router";
import Cookies from "js-cookie";
import { editUserProfile } from "@/api/user";
import { useLoginStore } from "@/store/useLoginStore";

const EditProfileForm: React.FC<{
  userData: { email: string; username: string };
}> = ({ userData }) => {
  const navigate = useNavigate();
  const userId = Cookies.get("userId");
  const { accessToken } = useLoginStore();
  const { values, setValues, errors, validateFieldAndSetError, isFormValid } =
    useEditProfileValidator(userData);

  useEffect(() => {
    setValues({
      email: userData.email,
      nickname: userData.username,
      password: "",
      newPassword: "",
    });
  }, [userData, setValues]);

  const onChangeHandler =
    (field: keyof typeof values) =>
    (event: React.ChangeEvent<HTMLInputElement>) => {
      const value = event.target.value;
      setValues((prevValues) => ({ ...prevValues, [field]: value }));
      validateFieldAndSetError(field, value);
    };

  const handleEditProfile = async (e: React.FormEvent) => {
    e.preventDefault();

    if (isFormValid()) {
      try {
        const response = await editUserProfile(
          userId || "",
          accessToken,
          values
        );

        console.log(response);
        // TODO: CORS 문제 해결 필요
        // if (response) {
        //   console.log(response);
        //   navigate("/profile");
        // }
      } catch (error) {
        throw new Error("프로필 수정 실패");
      }
    } else {
      console.log("");
    }
  };

  return (
    <form className="w-96 flex flex-col gap-5" onSubmit={handleEditProfile}>
      <FormField
        value={values.email}
        label="Email"
        name="email"
        type="email"
        onChange={onChangeHandler("email")}
        style={`text-black font-bold`}
        message={errors.email}
      />
      <FormField
        value={values.nickname}
        name="nickname"
        label="Nickname"
        type="text"
        onChange={onChangeHandler("nickname")}
        style={`text-black font-bold`}
        message={errors.nickname}
      />
      <FormField
        value={values.password}
        name="password"
        label="Password"
        type="password"
        onChange={onChangeHandler("password")}
        style={`text-black font-bold`}
        message={errors.password}
      />
      <FormField
        value={values.newPassword}
        name="newPassword"
        label="Confirm Password"
        type="password"
        onChange={onChangeHandler("newPassword")}
        style={`text-black font-bold`}
        message={errors.newPassword}
      />

      <Button
        style={`bg-primary text-white transition duration-300 hover:bg-secondary hover:text-primary`}
        type="submit">
        수정하기
      </Button>
      <Button
        style={`bg-quaternary text-primary transition duration-300 hover:bg-secondary hover:text-primary`}
        type="button"
        onClick={() => navigate("/")}>
        취소
      </Button>
    </form>
  );
};

export default EditProfileForm;
