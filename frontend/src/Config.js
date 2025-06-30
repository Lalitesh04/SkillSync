// config.js
const API = "http://localhost:5858/api";

const Config = {
    API,
    SIGNIN: `${API}/auth/signIn`,
    SIGNUP: `${API}/auth/signUp`,
    IS_EMAIL_EXIST: `${API}/auth/isEmailExist`,
    IS_LOGIN :  `${API}/auth/isLogin` ,
    VERIFICATION_LINK: `${API}/auth/verification-link`,
    CHANGE_PASSWORD: `${API}/auth/change-password`,
    CREATE_PROFILE: `${API}/v1/users/create-profile`,
};

export default Config;