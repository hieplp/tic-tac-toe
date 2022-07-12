import { API_URL } from "../dev.env";

export const ApiConfig = {
  // Auth
  LOGIN: API_URL + "/auth/login",
  REGISTER: API_URL + "/auth/register",
  CHECK_EMAIL: API_URL + "/auth/check-email",
  CHECK_REQUEST: API_URL + "/auth/check-request",
  RESET_PASSWORD: API_URL + "/auth/reset-password",
  SEND_FORGOT_PASSWORD_REQUEST: API_URL + "/auth/forgot-password",
  // Note
  NOTE: API_URL + "/notes",
  GET_NOTE_LIST: API_URL + "/notes",
  GET_NOTE_DETAIL: API_URL + "/notes/",
  UPDATE_NOTE: API_URL + "/notes/",
  CREATE_NOTE: API_URL + "/notes",
  UPDATE_NOTE_IS_PINNED: "pin",
  UPDATE_NOTE_STATUS: "status",
  // TASK
  TASK: API_URL + "/tasks",
  // UPLOAD
  UPLOADS_IMAGES: API_URL + "/uploads/images",
};

export const ImageHost = API_URL + "/uploads/images/";

export const PasswordRSA = {
  PUBLIC_KEY:
    "-----BEGIN PUBLIC KEY-----\n" +
    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxrhiHoJj4Rr4WqbKc7TB\n" +
    "S5/gsx08PTnBZSD320lVtQeuakPXyojp1OVSWrxz/2k8Scn7DjGZlgPfHgh8/Rmr\n" +
    "rHuo42L283yZTXfiEnua0py9G2zOunGPCKWABDJl67JUJAmjnjRLobH5iexFGqrw\n" +
    "lFQ+WZf3gnTbPaFjCmfpypIU25ixXfaV/j9G2Z7lC0q5FnqX6QqbJ4AJGTHZysgE\n" +
    "1ueH5sw7e3gDrDCjngUjT67aAzRaT0ZkoF6qS3j9M+mV8P/eavxyM+FFRRvaxYDa\n" +
    "szXp6OvYctoTHwvALaCux7m8gkSeslIMgD4T745Yfjo/Rn8aRfRK96WAfsjANc21\n" +
    "TwIDAQAB\n" +
    "-----END PUBLIC KEY-----",
};

export const TokenRSA = {
  PUBLIC_KEY:
    "-----BEGIN PUBLIC KEY-----\n" +
    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqssGZwp4q9grDkztlx9P\n" +
    "pMz6A/WwVqblFihNnMH6MsZ+4f1E4D4xbZLFFWPRPdpJCacAGP13le9/oPzsHY9j\n" +
    "yL04U0ke/4HpxN45bzHJf/j3QoJYUqdzSMjiU7lxEWlbkQgqsab5tGLcnQUExQ+A\n" +
    "JIRZMoQzABZSbO2CApxdvu1uxiK6CtCO4XePASOtIWhpbCFhuA2xhTjuZdoDd2x4\n" +
    "4zVtoxwSU0TMrvUpZzonG3mvMF43hItvcxW1RiZD2NsnlpZvSzKAWqQR7jH92azH\n" +
    "5askEsF5a4mFQuwpKO4vUVac8kDzN078RjXeR8dBL0sCcfgyFg8qWOE91vIThRS8\n" +
    "6wIDAQAB\n" +
    "-----END PUBLIC KEY-----",
};
