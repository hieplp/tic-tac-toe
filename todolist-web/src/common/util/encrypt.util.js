import MD5 from "crypto-js/md5";
import { JSEncrypt } from "jsencrypt";
import { PasswordRSA } from "../config";

export const encryptPassword = (password) => {
  const hashedPassword = MD5(password).toString();
  const sign = new JSEncrypt();
  sign.setPublicKey(PasswordRSA.PUBLIC_KEY);
  return sign.encrypt(hashedPassword);
};
