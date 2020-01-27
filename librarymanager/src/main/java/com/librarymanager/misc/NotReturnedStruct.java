package com.librarymanager.misc;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class NotReturnedStruct {
    private LocalDateTime deadline;
    private boolean deadlineExceeded;
    private long fineValue;

    public NotReturnedStruct()
    {
    }

    public boolean isDeadlineExceeded() {
        return deadlineExceeded;
    }

    public long getFineValue() {
        return fineValue;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadlineExceeded(boolean deadlineExceeded) {
        this.deadlineExceeded = deadlineExceeded;
    }

    public void setFineValue(long fineValue) {
        this.fineValue = fineValue;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
