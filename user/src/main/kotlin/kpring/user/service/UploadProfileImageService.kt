package kpring.user.service

import kpring.core.global.exception.ServiceException
import kpring.user.exception.UserErrorCode
import kpring.user.global.SupportedMediaType
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
    savedPath: Path,
  ): String {
    if (Files.notExists(savedPath)) {
      Files.createDirectories(savedPath)
    }
    val extension = multipartFile.originalFilename!!.substringAfterLast('.')
    isFileExtensionSupported(multipartFile)

    val uniqueFileName = generateUniqueFileName(userId, extension)
    val filePath = savedPath.resolve(uniqueFileName)
    multipartFile.transferTo(filePath.toFile())

    return uniqueFileName
  }

  private fun isFileExtensionSupported(multipartFile: MultipartFile) {
    val supportedExtensions = SupportedMediaType.entries.map { it.mediaType }
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
