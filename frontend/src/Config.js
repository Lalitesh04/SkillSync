// config.js
const API = "http://localhost:8080/api";

const Config = {
    API,
    SIGNIN: `${API}/auth/signIn`,
    SIGNUP: `${API}/auth/signUp`,
    IS_EMAIL_EXIST: `${API}/auth/isEmailExist`,
    VERIFICATION_LINK: `${API}/auth/verification-link`,
    CHANGE_PASSWORD: `${API}/auth/change-password`,
};

export default Config;