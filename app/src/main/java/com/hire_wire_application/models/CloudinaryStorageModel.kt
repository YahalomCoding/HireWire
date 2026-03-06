package com.hire_wire_application.models

import android.content.Context
import android.graphics.Bitmap
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.policy.GlobalUploadPolicy
import com.cloudinary.android.policy.UploadPolicy
import com.hire_wire_application.MyApplication
import com.hire_wire_application.StringCompletion
import java.io.File

class CloudinaryStorageModel {
  init {
    val config =
        mapOf(
            "cloud_name" to "dunh6l62o",
            "api_key" to "254264749826441",
            "api_secret" to "6LVr28U5AKHUTcYE1yB4GlaPN5k",
        )

    MyApplication.appContext?.let {
      MediaManager.init(it, config)
      MediaManager.get().globalUploadPolicy =
          GlobalUploadPolicy.Builder()
              .maxConcurrentRequests(3)
              .networkPolicy(UploadPolicy.NetworkType.UNMETERED)
              .build()
    }
  }

  fun uploadServiceImage(image: Bitmap, service: Service, completion: StringCompletion) {
    val context = MyApplication.appContext ?: return
    val file = convertBitmapToFile(image, context)

    MediaManager.get()
        .upload(file.path)
        .option("images", "services/${service.id}/image")
        .callback(
            object : com.cloudinary.android.callback.UploadCallback {
              override fun onSuccess(requestId: String, resultData: Map<*, *>) {
                completion(resultData["secure_url"] as String)
              }

              override fun onError(requestId: String, error: ErrorInfo) {}

              override fun onStart(requestId: String) {}

              override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {}

              override fun onReschedule(requestId: String, error: ErrorInfo) {}
            }
        )
        .dispatch()
  }

  private fun convertBitmapToFile(image: Bitmap, context: Context): File {
    val file = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")

    file.outputStream().use {
      image.compress(Bitmap.CompressFormat.JPEG, 100, it)
      it.flush()
    }

    return file
  }
}
