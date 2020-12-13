package vn.vnpt.tracebility_custom.model.jsonparse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomLocalDateSerilizable extends StdSerializer<LocalDate> {


    public CustomLocalDateSerilizable() {
        super(LocalDate.class);
    }

    public CustomLocalDateSerilizable(Class<LocalDate> t) {
        super(t);
    }

    @Override
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (localDate != null) {
            final DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            jsonGenerator.writeString(localDate.format(df));
        }
    }
}
