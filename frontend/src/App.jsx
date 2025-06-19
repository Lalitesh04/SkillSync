import Index from "./Auth/Index.jsx";
import { BrowserRouter, Routes, Route, useNavigate } from "react-router-dom";
import { SignIn } from "./Auth/SignIn.jsx";
import Register from "./Auth/Register.jsx";
import { Toaster } from "./components/ui/toaster.js";
import ProtectedRoute from "./Auth/ProtectedRoute.jsx";
import ClientHome from "./Client/ClientHome.jsx";
import Unauthorized from "./Auth/Unauthorized.jsx";
import Dashboard from "./User/Dashboard.jsx";
import AdminHome from "./Admin/AdminHome.jsx";
import { useEffect } from "react";
import { jwtDecode } from "jwt-decode";

export default function App() {
    const navigate = useNavigate(); 

    useEffect(() => {
        const token = localStorage.getItem("token");
        if (token) {
            try {
                const userInfo = jwtDecode(token);

                if (userInfo && userInfo.role) {
                    switch (userInfo.role) {
                        case "ROLE_ADMIN":
                            navigate("/adminhome");
                            break;
                        case "ROLE_USER":
                            navigate("/dashboard");
                            break;
                        case "ROLE_CLIENT":
                            navigate("/clienthome");
                            break;
                        default:
                            navigate("/unauthorized");
                    }
                }
            } catch (error) {
                localStorage.removeItem("token");
                navigate("/signin");
            }
        }
    }, [navigate]);

    return (
    <>
        
            <Routes>
                <Route path="/" element={<Index />} />
                <Route path="/signin" element={<SignIn />} />
                <Route path="/register" element={<Register />} />

                {/* Protected for ROLE_USER */}
                <Route element={<ProtectedRoute allowedRoles={["ROLE_USER"]} />}>
                    <Route path="/dashboard" element={<Dashboard />} />
                </Route>

                {/* Protected for ROLE_ADMIN */}
                <Route element={<ProtectedRoute allowedRoles={["ROLE_ADMIN"]} />}>
                    <Route path="/adminhome" element={<AdminHome />} />
                </Route>

                {/* Protected for ROLE_CLIENT */}
                <Route element={<ProtectedRoute allowedRoles={["ROLE_CLIENT"]} />}>
                    <Route path="/clienthome" element={<ClientHome />} />
                </Route>

                <Route path="/unauthorized" element={<Unauthorized />} />

                <Route path="*" element={<h1 className="text-3xl text-red-500">404 Not Found</h1>} />
            </Routes>

            <Toaster />

    </>
    );
}
