const PREFIX_TOKEN = "token";
const ACCESS_TOKEN = "access_token";
const REFRESH_TOKEN = "refresh_token";

export const getPrefixToken = () => {
  return PREFIX_TOKEN;
};

/**
 ******************************
 * ACCESS TOKEN
 ******************************
 */
export const getToken = () => {
  return localStorage.getItem(ACCESS_TOKEN);
};

export const saveToken = (token) => {
  localStorage.setItem(ACCESS_TOKEN, token);
};

export const destroyToken = () => {
  localStorage.removeItem(ACCESS_TOKEN);
};

export const isTokenExpired = (expDate) => {
  const nowTime = Math.floor(new Date().getTime() / 1000);
  return expDate > nowTime;
};

/**
 ******************************
 * REFRESH TOKEN
 ******************************
 */
export const getRefreshToken = () => {
  return localStorage.getItem(REFRESH_TOKEN);
};

export const saveRefreshToken = (token) => {
  localStorage.setItem(REFRESH_TOKEN, token);
};

export const destroyRefreshToken = () => {
  localStorage.removeItem(REFRESH_TOKEN);
};

export default {
  getToken,
  saveToken,
  destroyToken,
  getRefreshToken,
  saveRefreshToken,
  destroyRefreshToken,
};
