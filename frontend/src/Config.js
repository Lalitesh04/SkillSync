// config.js
const API = "http://localhost:8080/api";

const Config = {
    API,
    SIGNIN: `${API}/auth/signIn`,
    SIGNUP: `${API}/auth/signUp`,
    IS_EMAIL_EXIST: `${API}/auth/isEmailExist`,
    FORGOT_PASSWORD: `${API}/auth/forgot-password`,
};

export default Config;