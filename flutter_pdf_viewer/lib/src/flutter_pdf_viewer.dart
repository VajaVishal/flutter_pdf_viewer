import 'package:flutter/material.dart';
import 'file_picker_native.dart';
import 'pdf_viewer_page.dart';

class PdfScreen extends StatefulWidget {
  @override
  State<PdfScreen> createState() => _PdfScreenState();
}

class _PdfScreenState extends State<PdfScreen> {
  pickFile() async {
    final path = await FilePickerNative.pickPdf();
    if (path != null) {
      Navigator.push(
        context,
        MaterialPageRoute(builder: (_) => PdfViewerPage(pdfPath: path)),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Pick PDF")),
      body: Center(
        child: ElevatedButton(
          onPressed: pickFile,
          child: const Text("Pick PDF"),
        ),
      ),
    );
  }
}
