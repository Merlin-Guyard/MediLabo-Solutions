package com.oc.medilabosolutionsreport.TU;

import com.oc.medilabosolutionsreport.Exceptions.MicroserviceDownException;
import com.oc.medilabosolutionsreport.dao.KeywordFile;
import com.oc.medilabosolutionsreport.dao.NoteProxy;
import com.oc.medilabosolutionsreport.dao.PatientProxy;
import com.oc.medilabosolutionsreport.model.Note;
import com.oc.medilabosolutionsreport.model.Patient;
import com.oc.medilabosolutionsreport.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class ServiceTest {

    @Mock
    private NoteProxy noteProxy;

    @Mock
    private PatientProxy patientProxy;

    @Mock
    private KeywordFile keywordFile;

    @InjectMocks
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void makeReportTest() throws MicroserviceDownException {

        when(patientProxy.getPatient(anyInt()))
                .thenReturn(new Patient("John", "Doe", LocalDate.parse("1980-01-01"), "M", "123 Main St", "123-456-7890"));

        List<Note> notes = Arrays.asList(
                new Note("1", "1", "danger"),
                new Note("2", "1", "test"),
                new Note("3", "1", "caution")
        );
        when(noteProxy.getNotes(anyInt())).thenReturn(notes);

        when(keywordFile.getKeywords()).thenReturn(Arrays.asList("test", "danger", "caution"));

        String result = reportService.makeReport(1);

        verify(patientProxy, times(1)).getPatient(anyInt());
        verify(noteProxy, times(1)).getNotes(anyInt());

        assertEquals("Early onset", result);
    }


}
