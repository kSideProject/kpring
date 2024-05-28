package kpring.user.service

import kpring.core.global.exception.ServiceException
import kpring.user.exception.UserErrorCode
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.text.SimpleDateFormat
import java.util.*

@Service
class UploadProfileImageService {
  public fun saveUploadedFile(
    multipartFile: MultipartFile,
    userId: Long,
    dirPath: Path,
  ): String {
    if (Files.notExists(dirPath)) {
      Files.createDirectories(dirPath)
    }
    val extension = multipartFile.originalFilename!!.substringAfterLast('.')
    isFileExtensionSupported(multipartFile)

    val uniqueFileName = generateUniqueFileName(userId, extension)
    val filePath = dirPath.resolve(uniqueFileName)
    multipartFile.transferTo(filePath.toFile())

    return uniqueFileName
  }

  private fun isFileExtensionSupported(multipartFile: MultipartFile) {
    val supportedExtensions = listOf("image/png", "image/jpeg")
    if (multipartFile.contentType !in supportedExtensions) {
      throw ServiceException(UserErrorCode.EXTENSION_NOT_SUPPORTED)
    }
  }

  private fun generateUniqueFileName(
    userId: Long,
    extension: String,
  ): String {
    val timeStamp = SimpleDateFormat("yyMMddHHmmss").format(Date())
    return "$timeStamp$userId.$extension"
  }
}
