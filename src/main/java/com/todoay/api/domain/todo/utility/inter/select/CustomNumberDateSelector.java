package com.todoay.api.domain.todo.utility.inter.select;


import com.todoay.api.domain.todo.utility.inter.DateRepeator;
import com.todoay.api.domain.todo.utility.inter.DateSelector;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomNumberDateSelector implements DateSelector {
    @Override
    public List<LocalDate> select(DateRepeator dateRepeator, int count, LocalDate repeatStandardDate) {
        List<LocalDate> selectedDates = new ArrayList<>();
        for (int i = 0; i < count ; i++) {
            repeatStandardDate = dateRepeator.plus(repeatStandardDate);
            selectedDates.add(repeatStandardDate);
        }
        return selectedDates;
    }

}
