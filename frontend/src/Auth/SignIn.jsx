import {Link, redirect, useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import Config from "../Config";
import { toast } from "sonner"
import { jwtDecode } from "jwt-decode";


export function SignIn() {
        const navigate = useNavigate();


    const [formData, setFormData] = useState({
        "email": "",
        "password": ""
    })

    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);

    const handleChange = (e) => {
        setFormData({...formData, [e.target.id]: e.target.value})
    }
   
    const handleSubmit = async (e) => {
        e.preventDefault();
        
        if (!formData.email || !formData.password) {
           toast.error("Email and password are required.");
            return;
        }

        setLoading(true);

        try {
            const response = await axios.post(Config.SIGNIN, formData);
        
            toast.success("Login successful!");
            try {
                localStorage.setItem("token", response.data);
                const userInfo = jwtDecode(response.data);
                console.log("User Info:", userInfo);

                if (!userInfo || !userInfo.sub) {
                    toast.error("Invalid token received.");
                    return;
                }

            // Role-based redirect
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
                    toast.error("Unauthorized role.");
                    localStorage.removeItem("token");
                    break;
            }
        } catch (err) {
            toast.error("Error decoding token.");
        }
    } catch (err) {

        toast.error("Invalid credentials, please try again.");
    } finally {
        setLoading(false);
        setFormData({ email: "", password: "" });
    }
    };

    return (
        <div className="min-h-screen bg-gradient-to-br from-slate-50 to-slate-100 flex pt-12 justify-center p-4">
            <div className="w-full max-w-md">
                <div className="text-center mb-8">
                    <Link to="/" className="inline-flex items-center space-x-2">
                        <div
                            className="w-10 h-10 bg-gradient-to-r from-purple-600 to-blue-600 rounded-lg flex items-center justify-center">
                            <span className="text-white font-bold">SS</span>
                        </div>
                        <span className="text-2xl font-bold text-gray-900">SkillSync</span>
                    </Link>
                </div>

                <div className=" border-gray-200 mt-8 rounded-lg border bg-white p-4 shadow-lg ">
                    <div className="text-center">
                        <h1 className="text-2xl p-2 font-semibold">Welcome Back</h1>
                        <p className="text-gray-400 p-2 text-sm">Sign in to your account to continue</p>
                    </div>



                    <form className="space-y-4 mt-4" onSubmit={handleSubmit}>
                        <div className="space-y-2">
                            <label className="label">Email</label>
                            <input className="input" id="email" type="email" placeholder="Enter your email"
                                   value={formData.email} onChange={handleChange} />

                        </div>
                        <div className="space-y-2">
                            <label className="label"> password</label>
                            <input className="input" id="password" type="password" placeholder="Enter your password"
                                   value={formData.password} onChange={handleChange} required/>
                        </div>
                        <div className="flex items-center justify-between">
                            <Link to="/forgot-password" className="text-sm text-purple-600 hover:text-purple-700">
                                Forgot password?
                            </Link>
                        </div>
                         <button className="btn w-full bg-purple-600 text-white hover:bg-purple-700">
                                {loading ? "Signing in..." : "Sign In"}
                            </button>         
                    </form>

                    <div className="text-center text-sm pt-5 text-gray-600">
                        Don't have an account?{" "}
                        <Link to="/register" className="text-purple-600 hover:text-purple-700 font-medium">
                            Sign up
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    )
}