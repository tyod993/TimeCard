package com.audiokontroller.timecard.data.room.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.audiokontroller.timecard.data.model.Break;
import com.audiokontroller.timecard.data.model.Task;
import com.audiokontroller.timecard.data.model.TimeEntry;

import java.util.List;

public class EntryWithTasksAndBreaks {
    @Embedded public TimeEntry timeEntry;
    @Relation(
            entity = Task.class,
            parentColumn = "entryID",
            entityColumn = "entryID"
    )
    public List<Task> tasks;

    @Relation(entity = Break.class, parentColumn = "entryID", entityColumn = "parentEntryID")
    public List<Break> breaks;
}
