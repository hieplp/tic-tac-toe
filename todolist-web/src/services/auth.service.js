import axios from "axios";
import { ApiConfig } from "../common/config";
import JwtService from "./jwt.service";
import { ErrorWrapper, ResponseWrapper } from "./util";
import jwtDecode from "jwt-decode";

export class AuthService {
  /**
   ******************************
   * @API
   ******************************
   */
  static async login({ email, password }) {
    try {
      const response = await axios.post(ApiConfig.LOGIN, { email, password });
      let data = response.data.data;
      if (data) {
        let token = response.data.data.token;
        _setAuthData({
          token: token.token,
          expiredAt: token.expiredAt,
          refreshToken: token.refreshToken,
        });
      }
      return new ResponseWrapper(response, data);
    } catch (error) {
      throw new ErrorWrapper(error);
    }
  }

  static async register(user) {
    try {
      const response = await axios.post(ApiConfig.REGISTER, user);
      const data = response.data.data;
      if (data) {
        let token = data.token;
        _setAuthData({
          token: token.token,
          expiredAt: token.expiredAt,
          refreshToken: token.refreshToken,
        });
      }
      return new ResponseWrapper(response, data);
    } catch (e) {
      throw new ErrorWrapper(e);
    }
  }

  static async doesEmailExist(email) {
    try {
      const response = await axios.get(`${ApiConfig.CHECK_EMAIL}/${email}`);
      return new ResponseWrapper(response, response.data.data);
    } catch (e) {
      throw new ErrorWrapper(e);
    }
  }

  static async sendForgotPasswordRequest(email) {
    try {
      const response = await axios.post(
        ApiConfig.SEND_FORGOT_PASSWORD_REQUEST,
        {
          email: email,
        }
      );
      return new ResponseWrapper(response, response.data.data);
    } catch (e) {
      throw new ErrorWrapper(e);
    }
  }

  static async isRequestValid(requestId) {
    try {
      const response = await axios.get(
        `${ApiConfig.CHECK_REQUEST}/${requestId}`
      );
      return new ResponseWrapper(response, response.data.data);
    } catch (e) {
      throw new ErrorWrapper(e);
    }
  }

  static async resetPassword(requestId, password) {
    try {
      const response = await axios.post(ApiConfig.RESET_PASSWORD, {
        requestId: requestId,
        password: password,
      });
      return new ResponseWrapper(response, response.data.data);
    } catch (e) {
      throw new ErrorWrapper(e);
    }
  }

  /**
   ******************************
   * @METHODS
   ******************************
   */
  static hasRefreshToken() {
    return Boolean(JwtService.getRefreshToken());
  }

  static isLoggedIn() {
    const token = JwtService.getToken();
    if (!token) {
      return false;
    }
    // Validate token expired time
    const decodedToken = jwtDecode(token);
    const expiredAt = decodedToken.exp;
    const nowTime = Math.floor(new Date().getTime() / 1000);
    return nowTime <= expiredAt;
  }
}

/**
 ******************************
 * @private_methods
 ******************************
 */
function _setAuthData({ token, expiredAt, refreshToken }) {
  JwtService.saveToken(token);
  JwtService.saveRefreshToken(refreshToken);
  console.log(expiredAt);
}
