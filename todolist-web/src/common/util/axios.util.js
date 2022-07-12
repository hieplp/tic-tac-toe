import axios from "axios";
import { getToken } from "../../services/jwt.service";

export const getWithToken = async (url, data) => {
  return await axios.get(url, {
    params: data,
    headers: {
      token: getToken(),
    },
  });
};
export const postWithToken = (url, data) => {
  return new Promise((resolve, reject) => {
    axios
      .post(url, data, {
        headers: {
          token: getToken(),
        },
      })
      .then((result) => {
        resolve(result);
      })
      .catch((e) => {
        reject(e);
      });
  });
};
export const putWithToken = async (url, data) => {
  return await axios.put(url, data, {
    headers: {
      token: getToken(),
    },
  });
};
