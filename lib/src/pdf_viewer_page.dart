import 'package:flutter/material.dart';
import 'package:share_plus/share_plus.dart';
import 'native_pdf_viewer.dart';

class PdfViewerPage extends StatelessWidget {
  final String pdfPath;
  const PdfViewerPage({super.key, required this.pdfPath});

  void _sharePdf() {
    Share.shareXFiles([XFile(pdfPath)], text: "Sharing PDF");
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("PDF Viewer"),
        actions: [
          IconButton(
            icon: const Icon(Icons.share),
            onPressed: _sharePdf,
          ),
          PopupMenuButton<String>(
            icon: const Icon(Icons.more_vert),
            onSelected: (value) {
              if (value == "details") {
                showDialog(
                  context: context,
                  builder: (_) => AlertDialog(
                    title: const Text("PDF Info"),
                    content: Text("Path:\n$pdfPath"),
                    actions: [
                      TextButton(
                        onPressed: () => Navigator.pop(context),
                        child: const Text("OK"),
                      ),
                    ],
                  ),
                );
              }
            },
            itemBuilder: (context) => [
              const PopupMenuItem(
                value: "details",
                child: Text("Details"),
              ),
            ],
          ),
        ],
      ),
      body: NativePdfViewer(path: pdfPath),
    );
  }
}
