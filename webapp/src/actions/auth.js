import cFetch from './../utils/cFetch';
import cookie from 'js-cookie';
import { API_CONFIG } from './../config/api';

export const loginUser = (creds, cbk) => {
  return cFetch(API_CONFIG.auth, { method: "POST", body: JSON.stringify(creds) }).then(response => {
      cookie.set('access_token', response.access_token);
  })
};
