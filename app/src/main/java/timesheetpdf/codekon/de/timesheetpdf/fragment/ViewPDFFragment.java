package timesheetpdf.codekon.de.timesheetpdf.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.joanzapata.pdfview.PDFView;
import java.io.File;
import timesheetpdf.codekon.de.timesheetpdf.R;

public class ViewPDFFragment extends DialogFragment {

    private PDFView mPdfView;

    /**
     * newInstance()
     *
     * @param content
     * @return
     */
    public static ViewPDFFragment newInstance(String content) {

        ViewPDFFragment fragment = new ViewPDFFragment();

        fragment.setRetainInstance(true);
        return fragment;
    }

    /**
     * onCreate()
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    /**
     * onCreateView()
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_view_pdf, null);
        mPdfView = (PDFView) v.findViewById(R.id.pdfview);

        viewPdfViewLibrary(mPdfView);

        return v;

    }

    /**
     * View PDF by Joanzapata PDF Viewer Library
     *
     * @param pdfView
     */

    public void viewPdfViewLibrary(PDFView pdfView){

        String INPUTFILE = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/timesheet/timesheet.pdf";

        File file = new File(INPUTFILE);

        pdfView.fromFile(file)
                .pages(0, 2, 1, 3, 3, 3)
                .defaultPage(1)
                .showMinimap(false)
                .enableSwipe(true)
                .load();

    }


}
