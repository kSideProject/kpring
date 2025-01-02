import { useNavigate } from "react-router";
import Button from "../atoms/Button";
import { useJoinValidation } from "@/hooks/user/useJoinValidator";
import FormField from "../molecules/FormField";
import { join } from "@/api/user";

const JoinForm = () => {
  const navigate = useNavigate();

  const { values, setValues, errors, validateFieldAndSetError, isFormValid } =
    useJoinValidation();

  const onChangeHandler =
    (field: keyof typeof values) =>
    (event: React.ChangeEvent<HTMLInputElement>) => {
      const value = event.target.value;
      setValues((prevValues) => ({ ...prevValues, [field]: value }));
      validateFieldAndSetError(field, value);
    };

  const clickSubmitHandler = async (e: React.FormEvent) => {
    e.preventDefault();

    if (isFormValid()) {
      const result = await join(values.email, values.password, values.nickname);

      console.log("가입성공 ", result);
      setTimeout(() => {
        navigate("/");
      }, 3000);
    } else {
      console.log("");
    }
  };
  return (
    <form className="flex flex-col gap-3">
      <FormField
        value={values.email}
        label="Email"
        name="email"
        type="email"
        onChange={onChangeHandler("email")}
        message={errors.email}
      />
      <FormField
        value={values.nickname}
        name="nickname"
        label="Nickname"
        type="text"
        onChange={onChangeHandler("nickname")}
        message={errors.nickname}
      />
      <FormField
        value={values.password}
        name="password"
        label="Password"
        type="password"
        onChange={onChangeHandler("password")}
        message={errors.password}
      />
      <FormField
        value={values.passwordConfirm}
        name="passwordConfirm"
        label="Confirm Password"
        type="password"
        onChange={onChangeHandler("passwordConfirm")}
        message={errors.passwordConfirm}
      />

      <Button color="" onClick={clickSubmitHandler}>
        회원가입
      </Button>
    </form>
  );
};

export default JoinForm;
