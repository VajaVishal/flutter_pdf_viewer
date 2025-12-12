# Pdf Viewer

A simple **Android-only Flutter PDF viewer plugin** with **zoom support** and native file picker integration.  
Use it to open PDF files directly in your Flutter app with smooth zooming and high-quality rendering.

## ✨Features
* ✅ Native PDF Picker - Pick PDF files from device storage
* ✅ PDF Renderer - Display PDF pages using Android's native PdfRenderer
* ✅ Platform Views - Seamless integration with Flutter UI
* ✅ Content URI Support - Handles both file paths and content URIs
* ✅ Automatic File Conversion - Converts content URIs to accessible file paths

## ✨ Preview
![screen-20251212-1228002](https://github.com/user-attachments/assets/e09bc70b-c037-413b-b83a-92cfec7d9722)


## Installation
Add this to your package's pubspec.yaml file:
```
dependencies:
  pdf_viewer:
    path: ../pdf_viewer  # For local development
```
from git:
```
dependencies:
  flutter_image_crop:
    git:
      url: https://github.com/yourusername/flutter_image_crop.git  # Your github path
```
Then run:
```
flutter pub get
```
## 📁 Project Structure
```
flutter_pdf_viewer/
├─ android/
│  └─ src/main/kotlin/com/example/flutter_pdf_viewer/
│       ├─ FlutterPdfViewerPlugin.kt
│       ├─ PdfViewFactory.kt
│       ├─ PdfViewController.kt
│       └─ PdfPicker.kt
├─ lib/
│  ├─ flutter_pdf_viewer.dart
│  ├─ file_picker_native.dart
│  ├─ native_pdf_viewer.dart
│  └─ pdf_viewer_page.dart
└─ pubspec.yaml
```
## How it Works
* File Picker: FlutterPdfViewer.pickPDF() opens native Android file picker to select PDFs.
* Platform View: Uses AndroidView to render PDF via PdfRenderer.
* Zoom: Pinch-to-zoom is enabled through ZoomageView.
* Navigation: Any page can be opened using PdfViewerPage.

## 🔑Permissions (Android)
Add to AndroidManifest.xml:
```
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>    
    <uses-permission android:name="android.permission.READ_MEDIA_IMAGES"/>
    <uses-permission android:name="android.permission.READ_MEDIA_VIDEO"/>
```
## 📱 Usage
### 1. Import the package
```
dart
import 'package:pdf_viewer/pdf_viewer.dart';
```
### 3. Pick a PDF file
```
dart
String? pdfPath = await PdfViewer.pickPdf();
if (pdfPath != null) {
  print('Selected PDF: $pdfPath');
}
```
### 4. Display PDF in Widget
```
dart
PdfView(
  filePath: '/storage/emulated/0/Download/document.pdf',
)
```
### 4. Complete Example
```
import 'package:flutter/material.dart';
import 'package:pdf_viewer/pdf_viewer.dart';

class PdfScreen extends StatefulWidget {
  @override
  _PdfScreenState createState() => _PdfScreenState();
}

class _PdfScreenState extends State<PdfScreen> {
  String? _pdfPath;

  Future<void> _selectPdf() async {
    final path = await PdfViewer.pickPdf();
    setState(() {
      _pdfPath = path;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('PDF Viewer'),
        actions: [
          IconButton(
            icon: Icon(Icons.folder_open),
            onPressed: _selectPdf,
          ),
        ],
      ),
      body: Column(
        children: [
          if (_pdfPath == null)
            Expanded(
              child: Center(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Icon(Icons.picture_as_pdf, size: 64, color: Colors.grey),
                    SizedBox(height: 16),
                    Text(
                      'No PDF Selected',
                      style: TextStyle(fontSize: 18, color: Colors.grey),
                    ),
                    SizedBox(height: 8),
                    ElevatedButton(
                      onPressed: _selectPdf,
                      child: Text('Select PDF File'),
                    ),
                  ],
                ),
              ),
            )
          else
            Expanded(
              child: PdfView(filePath: _pdfPath!),
            ),
        ],
      ),
    );
  }
}
```
## 📈 Performance Tips
* Cache rendered bitmaps for frequently viewed pages
* Limit PDF page count for initial load
* Use lower resolution for thumbnails (×1 instead of ×3)
* Implement page preloading for multi-page documents
* Add progress indicator for large PDFs

## 📜 License
MIT License
```
Copyright (c) 2025 Excelsior Technologies

Permission is hereby granted, free of charge, to any person obtaining a copy  
of this software and associated documentation files (the "Software"), to deal  
in the Software without restriction, including without limitation the rights  
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell  
copies of the Software, and to permit persons to whom the Software is  
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all  
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED **"AS IS"**, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR  
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,  
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.

