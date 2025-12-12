import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class NativePdfViewer extends StatelessWidget {
  final String path;
  const NativePdfViewer({super.key, required this.path});

  @override
  Widget build(BuildContext context) {
    return AndroidView(
      viewType: "pdf_view",
      creationParams: {"filePath": path},
      creationParamsCodec: const StandardMessageCodec(),
    );
  }
}
