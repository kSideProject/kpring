import { useState } from "react";
import { FormValidator } from "../../types/common";

const useFormValidator = <T extends object>(
  initialValues: T,
  validators: FormValidator<T>
) => {
  // 폼의 값
  const [values, setValues] = useState<T>(initialValues);

  // 에러메세지
  const [errors, setErrors] = useState<Record<keyof T, string>>(
    Object.keys(initialValues).reduce(
      (acc, key) => ({ ...acc, [key]: "" }),
      {} as Record<keyof T, string>
    )
  );

  // 개별 필드 유효성 검사
  const validateFieldAndSetError = (field: keyof T, value: string) => {
    const errorMessage = validators[field](value, values);
    setErrors((prevError) => ({
      ...prevError,
      [field]: errorMessage,
    }));

    return errorMessage;
  };

  // 폼 전체 유효성 검사
  const isFormValid = () => {
    const newErrors: Record<keyof T, string> = {} as Record<keyof T, string>;

    (Object.keys(validators) as (keyof T)[]).forEach((field) => {
      newErrors[field] = validators[field](values[field] as string, values);
    });

    setErrors(newErrors);

    return !Object.values(newErrors).some((error) => error);
  };

  return {
    values,
    setValues,
    errors,
    setErrors,
    validateFieldAndSetError,
    isFormValid,
  };
};

export default useFormValidator;
