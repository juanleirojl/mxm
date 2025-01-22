package com.mxm.helpers;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

public class WordTemplateProcessor {

    public static void generateWord(String templatePath, String outputPath, Map<String, String> placeholders) throws Exception {
        // Abrir o template
        try (FileInputStream fis = new FileInputStream(templatePath);
             XWPFDocument document = new XWPFDocument(fis)) {

            // Substituir os placeholders no documento
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    if (text != null) {
                        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                            text = text.replace(entry.getKey(), entry.getValue());
                        }
                        run.setText(text, 0);
                    }
                }
            }

            // Salvar o novo documento
            try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                document.write(fos);
            }
        }
    }
}
