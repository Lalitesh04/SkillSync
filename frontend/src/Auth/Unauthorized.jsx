"use client"

import { Shield, Home, ArrowLeft } from "lucide-react"

export default function UnauthorizedPage() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-red-50 to-orange-50 flex items-center justify-center p-4">
      <div className="max-w-md w-full text-center">
        <div className="mb-8">
          <div className="mx-auto w-24 h-24 bg-red-100 rounded-full flex items-center justify-center mb-4">
            <Shield className="w-12 h-12 text-red-600" />
          </div>
          <div className="text-6xl font-bold text-red-600 mb-2">403</div>
          <h1 className="text-2xl font-semibold text-gray-900 mb-2">Access Forbidden</h1>
          <p className="text-gray-600 mb-8">
            {
              "You don't have permission to access this resource. Please contact your administrator if you believe this is an error."
            }
          </p>
        </div>

        <div className="space-y-3">
          <button
            onClick={() => window.history.back()}
            className="w-full bg-red-600 hover:bg-red-700 text-white font-medium py-3 px-4 rounded-lg transition-colors duration-200 flex items-center justify-center"
          >
            <ArrowLeft className="w-4 h-4 mr-2" />
            Go Back
          </button>

        </div>
        <div className="mt-8 pt-8 border-t border-gray-200">
          <p className="text-sm text-gray-500">Error Code: 403 | Unauthorized Access</p>
          <p className="text-xs text-gray-400 mt-2">If you need access to this resource, please contact support.</p>
        </div>
      </div>
    </div>
  )
}
