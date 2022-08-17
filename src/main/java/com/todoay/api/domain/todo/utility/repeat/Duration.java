package com.todoay.api.domain.todo.utility.repeat;


import com.todoay.api.domain.todo.utility.repeat.impl.selector.CustomMonthDateSelector;
import com.todoay.api.domain.todo.utility.repeat.impl.selector.CustomNumberDateSelector;
import com.todoay.api.domain.todo.utility.repeat.impl.selector.OneMonthDateSelector;
import com.todoay.api.domain.todo.utility.repeat.impl.selector.CustomWeeksDateSelector;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor @Getter
public enum Duration {
    ONE_MONTH(new OneMonthDateSelector()),
    CUSTOM_WEEKS(new CustomWeeksDateSelector()),
    CUSTOM_MONTH(new CustomMonthDateSelector()),
    CUSTOM_NUMBER(new CustomNumberDateSelector());

    private final DateSelector dateSelector;

    public List<LocalDate> select(DateRepeator dateRepeator, int count, LocalDate repeatStandardDate) {
        return dateSelector.select(dateRepeator, count,repeatStandardDate);
    }
}
