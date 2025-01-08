import { useNavigate } from "react-router";
import { useLoginStore } from "@/store/useLoginStore";
import { useLoginValidator } from "@/hooks/user/useLoginValidator";
import { login } from "@/api/user";
import FormField from "../molecules/FormField";
import Button from "../atoms/Button";

const LoginForm = () => {
  const navigate = useNavigate();
  const { setTokens } = useLoginStore();

  const { values, setValues, errors, validateFieldAndSetError, isFormValid } =
    useLoginValidator();

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
      const result = await login(values.email, values.password);

      if (result.refreshToken) {
        setTokens(result.accessToken, result.refreshToken);
      }

      setTimeout(() => {
        navigate("/");
      }, 3000);
    } else {
      console.log("");
    }
  };
  return (
    <form className="w-96 flex flex-col gap-5" onSubmit={clickSubmitHandler}>
      <FormField
        value={values.email}
        name="email"
        label="Email"
        type="email"
        style={`text-white font-bold`}
        onChange={onChangeHandler("email")}
        message={errors.email}
      />
      <FormField
        value={values.password}
        name="password"
        label="Password"
        type="password"
        style={`text-white font-bold`}
        onChange={onChangeHandler("password")}
        message={errors.password}
      />

      <Button
        style={`bg-primary text-white transition duration-300 hover:bg-secondary hover:text-primary`}
        type="submit">
        로그인
      </Button>
    </form>
  );
};

export default LoginForm;
