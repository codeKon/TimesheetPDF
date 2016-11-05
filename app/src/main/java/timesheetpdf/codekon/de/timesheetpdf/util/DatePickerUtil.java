package timesheetpdf.codekon.de.timesheetpdf.util;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Calendar;
import timesheetpdf.codekon.de.timesheetpdf.fragment.DatePickerFragment;

public class DatePickerUtil {

    /**
     * Set Date Picker
     *
     * @param dateOutput
     * @param changeDate
     * @param fragmentManager
     * @param ondate
     */
    public static void setDatePicker(TextView dateOutput, Button changeDate, final FragmentManager fragmentManager, final DatePickerDialog.OnDateSetListener ondate) {

        // Get current date by calender
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Show current date
        dateOutput.setText(new StringBuilder()

                // Month is 0 based, just add 1
                .append(day).append("-").append(month + 1).append("-")
                .append(year).append(" "));

        // Button listener to show date picker dialog
        changeDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // On button click show datepicker dialog
                showDatePicker(fragmentManager, ondate);
            }

        });
    }

    /**
     * Show Date Picker
     *
     * @param fragmentManager
     * @param ondate
     *
     */

    private static void showDatePicker(FragmentManager fragmentManager, DatePickerDialog.OnDateSetListener ondate) {

        DatePickerFragment date = new DatePickerFragment();

        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));

        date.setArguments(args);

        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(fragmentManager, "Date Picker");
    }
}
