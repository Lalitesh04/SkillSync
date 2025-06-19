import {Link} from "react-router-dom";
import {ArrowRight, Github} from "lucide-react";

export default function Index(){
    return(
        <div className="min-h-screen bg-gradient-to-br from-slate-50 to-slate-100 ">
            <header className=" border-b bg-white/80 backdrop-blur-sm sticky top-0 z-50">
                <div className="flex items-center  p-4 justify-between">
                    <Link to="/" className="inline-flex  items-center space-x-2">
                        <div className="w-8 h-8 bg-gradient-to-r from-purple-600 to-blue-600 rounded-lg text-center flex   items-center justify-center">
                            <span className="text-white font-bold">SS</span>
                        </div>
                        <span className="text-xl font-bold text-slate-900">SkillSync</span>
                    </Link>
        
                    <div className="flex flex-col sm:flex-row gap-4 justify-center">
                        <Link to="/signin"    className="btn   bg-gradient-to-r text-black hover:from-gray-200 hover:to-gray-300" >Sign In</Link>
                        <Link to="/register" className="btn  bg-gradient-to-r from-purple-600 to-blue-600 hover:from-purple-700 hover:to-blue-700">Get Started</Link>
                    </div>
                </div>
            </header>

            <section className="container pt-20  mx-auto text-center">
                <div className="mb-6 badge bg-purple-100 text-purple-700 hover:bg-purple-100">
                    🎯 Full-Stack Portfolio Project
                </div>
                <h1 className="text-5xl md:text-6xl font-bold text-gray-900 mb-6 leading-tight">
                <span className="bg-gradient-to-r from-purple-600 to-blue-600 bg-clip-text text-transparent">
              SkillSync
            </span>
                </h1>
                <p className="text-xl text-gray-600 mb-4 max-w-3xl mx-auto">AI-Powered Freelance Collaboration Platform</p>
                <p className="text-lg text-gray-500 mb-8 max-w-2xl mx-auto">
                    A scalable, modular microservices system demonstrating enterprise-level architecture, ML integration, and
                    modern development practices
                </p>
                <div className="flex flex-col sm:flex-row gap-4 justify-center mb-12">

                    <Link to="https://github.com/Lalitesh04/SkillSync" target="_blank">
                        <button className="btn text-black bg-white hover:bg-gray-100 border border-gray-400">
                            <Github className="mr-2 h-4 w-4" />
                            View Source Code
                        </button>
                    </Link>
                </div>
                {/* Footer */}
                <div>
                    <p> </p>
                </div>
            </section>
        </div>
    )
}