import { Link } from "react-router-dom";
import { useState, useCallback } from "react";
import { Briefcase, Eye, EyeOff, User } from "lucide-react";
import axios from "axios";
import { debounce } from "lodash";
import Config from "../Config";
import { toast } from "../hooks/use-toast";
import { useNavigate } from "react-router-dom";

export default function Register() {
    const [showPassword, setShowPassword] = useState(false);
    const [userType, setUserType] = useState("freelancer");
    const [errors, setErrors] = useState({});
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);

    const navigate = useNavigate();
    const [user, setUser] = useState({
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        confirmPassword: ""
    });

    const checkEmailExists = useCallback(
        debounce(async (email) => {
            try {
                const res = await axios.post(`${Config.IS_EMAIL_EXIST}?email=${encodeURIComponent(email)}`);
                if (res.data === true) {
                    setErrors((prev) => ({ ...prev, email: "Email already in use" }));
                } else {
                    setErrors((prev) => {
                        const updated = { ...prev };
                        delete updated.email;
                        return updated;
                    });
                }
            } catch (err) {
                console.error("Email check failed", err);
            }
        }, 500),
        []
    );

    const validateForm = () => {
        const newErrors = {};
        if (!user.firstName.trim()) newErrors.firstName = "First name is required";
        if (!user.lastName.trim()) newErrors.lastName = "Last name is required";

        if (!user.email.trim()) newErrors.email = "Email is required";
        else if (!/\S+@\S+\.\S+/.test(user.email)) newErrors.email = "Invalid email format";

        if (!user.password) newErrors.password = "Password is required";
        else if (user.password.length < 6) newErrors.password = "Password must be at least 6 characters";

        if (!user.confirmPassword) newErrors.confirmPassword = "Please confirm your password";
        else if (user.password !== user.confirmPassword) newErrors.confirmPassword = "Passwords do not match";

        setErrors(newErrors);
        if (Object.keys(newErrors).length > 0) {
            setErrors(newErrors);
            return false;
        }
       
        return true;
    };

    const handleChange = (e) => {
        const { id, value } = e.target;
        setUser((prev) => ({ ...prev, [id]: value }));

        if (errors[id]) {
            setErrors((prev) => {
                const updated = { ...prev };
                delete updated[id];
                return updated;
            });
        }

        if (id === "email" && /\S+@\S+\.\S+/.test(value)) {
            checkEmailExists(value);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);

        if (!validateForm()) return;

        
        const formData = {
            ...user,
            userType
        };
        delete formData.confirmPassword;

        try {
            setLoading(true);
            const response = await axios.post(
                Config.SIGNUP,
                JSON.stringify(formData),
                {
                    headers: { "Content-Type": "application/json" }
                }
            );

            if (response.status === 201) {
                toast({
                    title: "Registration successful! Please check your email to verify your account.",
                    variant: "success",
                    duration: 3000
                });
                resetForm();
                navigate("/login"); 
            }
        } catch (err) {
            toast({ title: "Registration failed. Please try again.", variant: "destructive", duration: 3000 });
        } finally {
            setLoading(false);
        }
    };

    const resetForm = () => {
        setUser({
            firstName: "",
            lastName: "",
            email: "",
            password: "",
            confirmPassword: ""
        });
        setErrors({});
        setError(null);
    };

    return (
        <div className="min-h-screen bg-gradient-to-br from-slate-50 to-slate-100 flex pt-12 justify-center p-4">
            <div className="w-full max-w-md">
                <div className="text-center mb-4">
                    <Link to="/" className="inline-flex items-center space-x-2">
                        <div className="w-10 h-10 bg-gradient-to-r from-purple-600 to-blue-600 rounded-lg flex items-center justify-center">
                            <span className="text-white font-bold">SS</span>
                        </div>
                        <span className="text-2xl font-bold text-gray-900">SkillSync</span>
                    </Link>
                </div>

                <div className="border-gray-200 rounded-lg border bg-white p-4 shadow-lg">
                    <div className="text-center">
                        <h1 className="text-2xl p-2 font-semibold">Join SkillSync</h1>
                        <p className="text-gray-400 p-2 text-sm">Create your account and start your journey</p>
                    </div>

                    <div className="grid w-full grid-cols-2 bg-gray-100 rounded-md p-1 text-gray-500">
                        <button
                            type="button"
                            onClick={() => setUserType("freelancer")}
                            className={`flex items-center justify-center gap-2 py-2 px-4 text-sm font-medium rounded-md transition-all ${userType === "freelancer" ? "bg-white text-black shadow-sm" : ""}`}
                        >
                            <User className="h-4 w-4" />
                            Freelancer
                        </button>
                        <button
                            type="button"
                            onClick={() => setUserType("client")}
                            className={`flex items-center justify-center gap-2 py-2 px-4 text-sm font-medium rounded-md transition-all ${userType === "client" ? "bg-white text-black shadow-sm" : ""}`}
                        >
                            <Briefcase className="h-4 w-4" />
                            Client
                        </button>
                    </div>

                    {error && <div className="text-red-500 text-sm text-center mt-2">{error}</div>}

                    <form className="space-y-4 mt-2 p-2" onSubmit={handleSubmit}>
                        <div className="grid grid-cols-2 gap-4">
                            <div className="space-y-2">
                                <label className="label">First Name</label>
                                <input id="firstName" className={`input ${errors.firstName ? "border-red-500 focus:ring-red-500" : ""}`}  type="text" placeholder="John" value={user.firstName} onChange={handleChange} />
                                {errors.firstName && <p className="text-red-500 text-sm">{errors.firstName}</p>}
                            </div>
                            <div className="space-y-2">
                                <label className="label">Last Name</label>
                                <input id="lastName" className={`input ${errors.lastName ? "border-red-500 focus:ring-red-500" : ""}`} type="text" placeholder="Doe" value={user.lastName} onChange={handleChange} />
                                {errors.lastName && <p className="text-red-500 text-sm">{errors.lastName}</p>}
                            </div>
                        </div>

                        <div className="space-y-2">
                            <label className="label">Email</label>
                            <input id="email" className={`input ${errors.email ? "border-red-500 focus:ring-red-500" : ""}`} type="email" placeholder="john@example.com" value={user.email} onChange={handleChange} />
                            {errors.email && <p className="text-red-500 text-sm">{errors.email}</p>}
                        </div>

                        <div className="space-y-2">
                            <label className="label">Password</label>
                            <input id="password" className={`input ${errors.password ? "border-red-500 focus:ring-red-500" : ""}`} type="password" placeholder="Create a strong password" value={user.password} onChange={handleChange} />
                            {errors.password && <p className="text-red-500 text-sm">{errors.password}</p>}
                        </div>

                        <div className="space-y-2">
                            <label className="label">Confirm Password</label>
                            <div className="relative">
                                <input id="confirmPassword" className={`input ${errors.confirmPassword ? "border-red-500 focus:ring-red-500" : ""}`} type={showPassword ? "text" : "password"} placeholder="Confirm your password" value={user.confirmPassword} onChange={handleChange} />
                                <button type="button" onClick={() => setShowPassword(!showPassword)} className="absolute right-0 top-0 h-full px-3 py-2 hover:bg-transparent">
                                    {showPassword ? <EyeOff className="h-4 w-4 text-gray-400" /> : <Eye className="h-4 w-4 text-gray-400" />}
                                </button>
                            </div>
                            {errors.confirmPassword && <p className="text-red-500 text-sm">{errors.confirmPassword}</p>}
                        </div>

                        <button
                            type="submit"
                            className="btn w-full bg-gradient-to-r from-purple-600 to-blue-600 hover:from-purple-700 hover:to-blue-700"
                            disabled={loading}
                        >
                            {loading ? "Creating..." : "Create Account"}
                        </button>
                    </form>

                    <div className="text-center text-sm pt-5 text-gray-600">
                        Already have an account?{" "}
                        <Link to="/signin" className="text-purple-600 hover:text-purple-700 font-medium">
                            Sign In
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    );
}
