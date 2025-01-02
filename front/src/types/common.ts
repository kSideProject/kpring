export type FormValidator<T> = {
  [K in keyof T]: (value: string, formValues?: T) => string;
};
