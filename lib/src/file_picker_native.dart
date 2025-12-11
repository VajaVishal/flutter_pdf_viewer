import 'package:flutter/services.dart';

class FilePickerNative {
  static const _channel = MethodChannel("file_picker_native");

  static Future<String?> pickPdf() async {
    return await _channel.invokeMethod("pickPdf");
  }
}
