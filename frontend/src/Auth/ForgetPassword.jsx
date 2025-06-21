import Config from '@/Config';
import { toast } from 'sonner';
import axios from 'axios';
import { useState } from 'react';
import { Link } from 'react-router-dom';

export default function ForgetPassword() {
    const [email, setEmail] = useState('');
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            const response = await axios.post(
                `${Config.VERIFICATION_LINK}?email=${encodeURIComponent(email)}`
            );
            if (response.status === 200) {
                toast.success(response.data || 'Reset link sent successfully');
        
            } else {
                toast.error(response.data || 'Failed to send reset link');
            }
        } catch (error) {
            toast.error(error.response?.data || 'Something went wrong');
        } finally {
            setLoading(false);
            setEmail('');
        }
    };

    return (
        <div className="min-h-screen bg-gradient-to-br from-slate-50 to-slate-100 flex justify-center pt-12 px-4">
            <div className="w-full max-w-md">
                <div className="text-center mb-8">
                    <Link to="/" className="inline-flex items-center space-x-2">
                        <div className="w-10 h-10 bg-gradient-to-r from-purple-600 to-blue-600 rounded-lg flex items-center justify-center">
                            <span className="text-white font-bold">SS</span>
                        </div>
                        <span className="text-2xl font-bold text-gray-900">SkillSync</span>
                    </Link>
                </div>

                <div className="bg-white border border-gray-200 shadow-lg rounded-lg p-6">
                    <div className="text-center">
                        <h1 className="text-2xl font-semibold">Forgot Password</h1>
                        <p className="text-gray-500 text-sm mt-2">Enter your email to reset your password</p>
                    </div>

                    <form onSubmit={handleSubmit} className="space-y-5 mt-6">
                        <div>
                            <label htmlFor="email" className="block text-sm font-medium text-gray-700">
                                Email
                            </label>
                            <input
                                id="email"
                                type="email"
                                className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm px-3 py-2 focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent"
                                placeholder="Enter your email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                required
                            />
                        </div>
                        <button
                            type="submit"
                            className="btn w-full bg-purple-600 text-white hover:bg-purple-700 transition"
                            disabled={loading}
                        >
                            {loading ? 'Sending...' : 'Send Reset Link'}
                        </button>
                    </form>

                    <p className="text-center text-sm text-gray-600 mt-6">
                        Already have an account?{' '}
                        <Link to="/signin" className="text-purple-600 hover:underline font-medium">
                            Sign In
                        </Link>
                    </p>
                </div>
            </div>
        </div>
    );
}
