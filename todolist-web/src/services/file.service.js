import axios from "axios";
import { ApiConfig } from "../common/config";
import { ErrorWrapper, ResponseWrapper } from "./util";

export class FileService {
  /**
   ******************************
   * @API
   ******************************
   */
  static async uploadImage(image) {
    try {
      const bodyFormData = new FormData();
      bodyFormData.append("image", image);
      const response = await axios.post(
        ApiConfig.UPLOADS_IMAGES,
        bodyFormData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );

      return new ResponseWrapper(response, response.data);
    } catch (e) {
      throw new ErrorWrapper(e);
    }
  }
}
