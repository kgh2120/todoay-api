package com.todoay.api.domain.todo.utility.inter.select;



import com.todoay.api.domain.todo.utility.inter.DateRepeator;
import com.todoay.api.domain.todo.utility.inter.Selector;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class ThisMonthSelector implements Selector {
    @Override
    public List<LocalDate> select(DateRepeator dateRepeator, int count, LocalDate repeatStandardDate) {
        List<LocalDate> selectedDates = new ArrayList<>();
        repeatStandardDate = LocalDate.now();
        Month 이번달 = repeatStandardDate.getMonth();
        int i = 이번달.maxLength();
        LocalDate 이번달마지막날 = LocalDate.of(2022, 이번달, i);
        while (true) {
            LocalDate weeks = dateRepeator.plus(repeatStandardDate);
            if(weeks.isAfter(이번달마지막날))
                break;
            selectedDates.add(weeks);
            repeatStandardDate = weeks;
        }
        return selectedDates;
    }
}
