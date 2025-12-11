import 'package:flutter/material.dart';
import 'dart:io';
import 'file_picker_native.dart';
import 'package:flutter_pdf_viewer/src/native_pdf_viewer.dart';

class PdfScreen extends StatefulWidget {
  @override
  _PdfScreenState createState() => _PdfScreenState();
}

class _PdfScreenState extends State<PdfScreen> {
  String? pdfPath;

  pickFile() async {
    final path = await FilePickerNative.pickPdf();
    setState(() => pdfPath = path);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("PDF Viewer")),
      body: pdfPath == null
          ? Center(
          child: ElevatedButton(
              onPressed: pickFile, child: Text("Pick PDF")))
          : NativePdfViewer(path: pdfPath!),
    );
  }
}
