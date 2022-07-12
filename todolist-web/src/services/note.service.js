import { ApiConfig } from "../common/config";
import { ErrorWrapper, ResponseWrapper } from "./util";
import {
  getWithToken,
  postWithToken,
  putWithToken,
} from "../common/util/axios.util";

export class NoteService {
  /**
   ******************************
   * @API
   ******************************
   */
  static async getNoteList(request) {
    try {
      const response = await getWithToken(ApiConfig.GET_NOTE_LIST, request);
      return new ResponseWrapper(response, response.data.data);
    } catch (e) {
      throw new ErrorWrapper(e);
    }
  }

  static async getNoteDetail(noteId) {
    try {
      const response = await getWithToken(
        ApiConfig.GET_NOTE_DETAIL + noteId,
        null
      );
      return new ResponseWrapper(response, response.data.data);
    } catch (e) {
      throw new ErrorWrapper(e);
    }
  }

  static async updateNote(note) {
    try {
      const response = await putWithToken(
        ApiConfig.UPDATE_NOTE + note.noteId,
        note
      );
      return new ResponseWrapper(response, response.data.data);
    } catch (e) {
      throw ErrorWrapper(e);
    }
  }

  static async createNote(note) {
    try {
      const response = await postWithToken(ApiConfig.CREATE_NOTE, note);
      return new ResponseWrapper(response, response.data.data);
    } catch (e) {
      throw ErrorWrapper(e);
    }
  }

  static async updateNoteIsPinned(noteId, isPinned) {
    try {
      const response = await putWithToken(
        `${ApiConfig.NOTE}/${noteId}/${ApiConfig.UPDATE_NOTE_IS_PINNED}`,
        {
          isPinned: isPinned,
        }
      );
      return new ResponseWrapper(response, response.data.data);
    } catch (e) {
      throw ErrorWrapper(e);
    }
  }

  static async updateNoteStatus(noteId, noteStatus) {
    try {
      const response = await putWithToken(
        `${ApiConfig.NOTE}/${noteId}/${ApiConfig.UPDATE_NOTE_STATUS}`,
        {
          noteStatus: noteStatus,
        }
      );
      return new ResponseWrapper(response, response.data.data);
    } catch (e) {
      throw ErrorWrapper(e);
    }
  }
}
