import 'package:flutter/services.dart';

class FilePickerNative {
  static const _channel = MethodChannel("file_picker_channel");

  static Future<String?> pickPdf() async {
    final path = await _channel.invokeMethod<String>("pickPdf");
    return path;
  }
}
