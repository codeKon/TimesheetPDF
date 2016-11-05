package timesheetpdf.codekon.de.timesheetpdf.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import timesheetpdf.codekon.de.timesheetpdf.R;


public class DatePickerFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener mOndateSet;
    private int mYear, mMonth, mDay;

    /**
     * Constructor
     */
    public DatePickerFragment() {

    }

    /**
     * Set call back
     *
     * @param ondate
     */
    public void setCallBack(DatePickerDialog.OnDateSetListener ondate) {
        mOndateSet = ondate;
    }

    /**
     * Set args
     *
     * @param args
     */
    @Override
    public void setArguments(Bundle args) {

        super.setArguments(args);
        mYear = args.getInt("year");
        mMonth = args.getInt("month");
        mDay = args.getInt("day");
    }

    /**
     * onCreateDialog()
     *
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new DatePickerDialog(getActivity(), R.style.CustomDatePicker, mOndateSet, mYear, mMonth, mDay/**/);
    }
}
