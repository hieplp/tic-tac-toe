/**
 ******************************
 * @VALID
 ******************************
 */
export const isValidWithMinAndMaxLength = (str, minLength, maxLength) =>
  isValidWithMinLength(str, minLength) && isValidWithMaxLength(str, maxLength);
export const isNotValidWithMinAndMaxLength = (str, minLength, maxLength) =>
  !isValidWithMinAndMaxLength(str, minLength, maxLength);

export const isValidWithMinLength = (str, minLength) => {
  return str.length >= minLength;
};
export const isNotValidWithMinLength = (str, minLength) =>
  !isValidWithMinLength(str, minLength);

export const isValidWithMaxLength = (str, maxLength) => {
  return str.length <= maxLength;
};
export const isNotValidWithMaxLength = (str, maxLength) =>
  !isValidWithMaxLength(str, maxLength);

export const isValidEmail = (email) => {
  return String(email)
    .toLowerCase()
    .match(/^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/);
};
export const isNotValidEmail = (email) => !isValidEmail(email);
/**
 ******************************
 * @COMPARE
 ******************************
 */
export const equal = (str1, str2) => {
  return str1 === str2;
};
export const notEqual = (str1, str2) => {
  return !equal(str1, str2);
};
