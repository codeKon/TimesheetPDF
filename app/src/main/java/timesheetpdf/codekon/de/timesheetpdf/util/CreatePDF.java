package timesheetpdf.codekon.de.timesheetpdf.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.IOException;
import java.util.ArrayList;


public class CreatePDF {

    private static Font mCatFont = new Font(Font.FontFamily.TIMES_ROMAN, 24,
            Font.BOLD);

    /**
     * Add meta data
     *
     * @param document
     */

    public static void addMetaData(Document document) {
        document.addTitle("");
        document.addSubject("");
    }

    /**
     * Add page title
     *
     * @param document
     * @param titlePage
     * @throws DocumentException
     */
    public static void addTitlePage(Document document, String titlePage)
            throws DocumentException {

        Paragraph preface = new Paragraph();
        addEmptyLine(preface, 5);

        preface.add(new Paragraph(titlePage, mCatFont));
        addEmptyLine(preface, 3);

        document.add(preface);
    }

    /**
     * Add empty line
     *
     * @param paragraph
     * @param number
     */
    public static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    /**
     * Add content
     *
     * @param document
     * @param titleDate
     * @param titleProject
     * @param titleDescription
     * @param titleTime
     * @param resultList
     * @throws DocumentException
     * @throws IOException
     */
    public static void addContent(Document document, String titleDate, String titleProject, String titleDescription, String titleTime,
                                  ArrayList<String[]> resultList) throws DocumentException, IOException {

        createTable(document, titleDate, titleProject, titleDescription, titleTime, resultList);
    }

    /**
     * Create a table with header and rows
     *
     * @param document
     * @param titleDate
     * @param titleProject
     * @param titleDescription
     * @param titleTime
     * @param resultList
     * @throws DocumentException
     * @throws IOException
     */
    public static void createTable(Document document, String titleDate, String titleProject, String titleDescription, String titleTime,
                                   ArrayList<String[]> resultList)
            throws DocumentException, IOException {

        float[] cellWidths = new float[]{25f, 15f, 40f, 20f};

        PdfPTable table = new PdfPTable(4);

        table.setWidths(cellWidths);

        BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
        Font font = new Font(bf, 22);

        PdfPCell c1 = new PdfPCell(new Phrase(titleDate, font));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setPadding(2);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(titleProject, font));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setPadding(2);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(titleDescription, font));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setPadding(2);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(titleTime, font));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setPadding(2);
        table.addCell(c1);

        for (int i = 0; i < resultList.size(); i++) {

            String[] dataRow = resultList.get(i);

            table.setFooterRows(i);

            PdfPCell rowCell0 = new PdfPCell(new Phrase(dataRow[0], font));
            rowCell0.setPadding(3);
            table.addCell(rowCell0);
            PdfPCell rowCell1 = new PdfPCell(new Phrase(dataRow[1], font));
            rowCell1.setPadding(3);
            table.addCell(rowCell1);
            PdfPCell rowCell2 = new PdfPCell(new Phrase(dataRow[2], font));
            rowCell2.setPadding(3);
            table.addCell(rowCell2);
            PdfPCell rowCell3 = new PdfPCell(new Phrase(dataRow[3], font));
            rowCell3.setPadding(3);
            table.addCell(rowCell3);

        }
        document.add(table);
    }
}
