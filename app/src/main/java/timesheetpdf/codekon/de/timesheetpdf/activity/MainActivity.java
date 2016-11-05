package timesheetpdf.codekon.de.timesheetpdf.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import timesheetpdf.codekon.de.timesheetpdf.R;
import timesheetpdf.codekon.de.timesheetpdf.fragment.ViewPDFFragment;
import timesheetpdf.codekon.de.timesheetpdf.util.CreatePDF;
import timesheetpdf.codekon.de.timesheetpdf.util.DatePickerUtil;

public class MainActivity extends FragmentActivity {

    private static File mFile;
    private File mDir;
    private TextView mtv_date_output;

    private ArrayList<String[]> mResultList = new ArrayList<String[]>();


    /**
     * Set Date Listener
     */
    DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            mtv_date_output.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1)
                    + "-" + String.valueOf(year));

        }
    };

    /**
     * onCreate
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv_new_entry = (TextView) findViewById(R.id.tv_new_entry);
        final TextView tv_date = (TextView) findViewById(R.id.tv_date);
        final TextView tv_project = (TextView) findViewById(R.id.tv_project);
        final TextView tv_description = (TextView) findViewById(R.id.tv_description);
        final TextView tv_time = (TextView) findViewById(R.id.tv_time);

        mtv_date_output = (TextView) findViewById(R.id.tv_date_output);

        //TODO check if empty
        final EditText ed_project = (EditText) findViewById(R.id.ed_project);
        final EditText ed_description = (EditText) findViewById(R.id.ed_description);
        final EditText ed_time = (EditText) findViewById(R.id.ed_time);

        Button btn_changeDate = (Button) findViewById(R.id.btn_changeDate);
        Button btn_export = (Button) findViewById(R.id.btn_export);
        Button btn_view_pdf = (Button) findViewById(R.id.btn_view_pdf);

        // Set date on fragment
        DatePickerUtil.setDatePicker(mtv_date_output, btn_changeDate, getSupportFragmentManager(), onDate);

        //Create new directory and file
        mDir = new File(Environment.getExternalStorageDirectory().toString(), "timesheet");
        mDir.mkdirs();
        mFile = new File(mDir, "timesheet.pdf");

        btn_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] result = {mtv_date_output.getText().toString(), ed_project.getText().toString(), ed_description.getText().toString(), ed_time.getText().toString()};
                mResultList.add(result);

                try {

                    createCachedFilePdf(tv_new_entry.getText().toString(), tv_date.getText().toString(), tv_project.getText().toString(), tv_description.getText().toString(), tv_time.getText().toString(), mResultList);
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.saved), Toast.LENGTH_LONG).show();

                } catch (IOException e) {
                    e.getStackTrace();
                }


            }
        });

        btn_view_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFile.exists()) {

                    //Toast.makeText(MainActivity.this, getResources().getString(R.string.view), Toast.LENGTH_SHORT).show();

                    ViewPDFFragment dialogFragment = new ViewPDFFragment();
                    dialogFragment.show(getFragmentManager(), getResources().getString(R.string.view_pdf));

                }
                else{
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.file_not_found), Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    /**
     * Create a pdf file
     *
     * @param titlePage
     * @param titleDate
     * @param titleProject
     * @param titleDescription
     * @param titleTime
     * @param resultList
     * @throws IOException
     */
    private void createCachedFilePdf(String titlePage, String titleDate, String titleProject, String titleDescription, String titleTime, ArrayList<String[]> resultList) throws IOException {

        try {

            CreatePDF createPdf = new CreatePDF();

            Document document = new Document();
            FileOutputStream out = new FileOutputStream(mFile);

            PdfWriter.getInstance(document, out);

            document.open();

            createPdf.addMetaData(document);
            createPdf.addTitlePage(document, titlePage);
            createPdf.addContent(document, titleDate, titleProject, titleDescription, titleTime, resultList);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * TODO to implement if necessary
     * View by intent
     *
     * //it doesn`t work on the emulator if a pdf viewer application not installed
     *
     */
    private void viewPdfIntent() {
        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/timesheet/timesheet.pdf");

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        startActivity(intent);
    }


    /**
     * TODO to implement if necessary
     * View by Itext Library
     *
     */
    private static void viewPdfItextLibrary() {

        try {
            String INPUTFILE = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/timesheet/timesheet.pdf";
            String OUTPUTFILE = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/timesheet/timesheet.pdf";

            Document document = new Document();

            FileOutputStream outputStream = new FileOutputStream(OUTPUTFILE);

            PdfCopy writer = new PdfCopy(document, outputStream);
            PdfReader reader = new PdfReader(INPUTFILE);

            document.open();

            for (int i = 1; i <= reader.getNumberOfPages(); i++) {

                PdfImportedPage page = writer.getImportedPage(reader, i);
                writer.addPage(page);

            }

            outputStream.flush();
            writer.flush();

            reader.close();
            writer.close();
            outputStream.close();
            document.close();

        } catch (IOException | DocumentException e) {

            e.printStackTrace();

        }
    }

}
