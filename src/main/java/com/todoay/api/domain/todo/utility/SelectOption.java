package com.todoay.api.domain.todo.utility;


import com.todoay.api.domain.todo.utility.inter.DateRepeator;
import com.todoay.api.domain.todo.utility.inter.Selector;
import com.todoay.api.domain.todo.utility.inter.select.CustomMonthSelector;
import com.todoay.api.domain.todo.utility.inter.select.CustomNumberSelector;
import com.todoay.api.domain.todo.utility.inter.select.OneMonthSelector;
import com.todoay.api.domain.todo.utility.inter.select.ThisMonthSelector;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor @Getter
public enum SelectOption {
    THIS_MONTH(new ThisMonthSelector()),
    ONE_MONTH(new OneMonthSelector()),
    CUSTOM_MONTH(new CustomMonthSelector()),
    CUSTOM_NUMBER(new CustomNumberSelector());

    private final Selector selector;

    public List<LocalDate> select(DateRepeator dateRepeator, int count, LocalDate repeatStandardDate) {
        return selector.select(dateRepeator, count,repeatStandardDate);
    }
}
