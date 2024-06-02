export type LoginBoxValues = {
  email: string;
  password: string;
};

export type LoginBoxValidateErrors = {
  emailError: string;
  passwordError: string;
};

export interface Validators {
  [key: string]: (input: string) => string;
}
