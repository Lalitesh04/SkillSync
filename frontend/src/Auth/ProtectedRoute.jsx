import { jwtDecode } from "jwt-decode"
import { Outlet,Navigate } from "react-router-dom"


const ProtectedRoute = ({allowedRoles}) => {

    const token = localStorage.getItem("token");
    if (!token) {
        return <Navigate to="/signin" replace />;
    }
    try {

        const userInfo = jwtDecode(token);
        if(!userInfo){
            return <Navigate to="/signin" replace />;
        }
        if(!allowedRoles.includes(userInfo.role)){
            return <Navigate to="/unauthorized" replace />;
        }
        <Outlet/>

    }catch (error) {
        localStorage.removeItem("token");
        return <Navigate to="/signin" replace />;
    }
}


export default ProtectedRoute;
