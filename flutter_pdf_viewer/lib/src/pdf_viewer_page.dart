import 'package:flutter/material.dart';
import 'native_pdf_viewer.dart';

class PdfViewerPage extends StatelessWidget {
  final String pdfPath;
  const PdfViewerPage({super.key, required this.pdfPath});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("PDF Viewer")),
      body: NativePdfViewer(path: pdfPath),
    );
  }
}
