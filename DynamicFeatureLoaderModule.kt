package com.citpl_dynamic_module

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest






class DynamicFeatureLoaderModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    private val splitInstallManager: SplitInstallManager = SplitInstallManagerFactory.create(reactContext)

    override fun getName(): String {
        return "DynamicFeatureLoader"
    }

    @ReactMethod
    fun loadDynamicFeature(moduleName: String, promise: Promise) {
        val request = SplitInstallRequest.newBuilder()
            .addModule(moduleName)
            .build()

        splitInstallManager.startInstall(request)
            .addOnSuccessListener {
                promise.resolve("Dynamic Feature $moduleName loaded successfully")
                // Optionally, you can trigger navigation to the activity here
            }
            .addOnFailureListener { exception ->
                promise.reject("Error", exception.message)
            }
    }
}
