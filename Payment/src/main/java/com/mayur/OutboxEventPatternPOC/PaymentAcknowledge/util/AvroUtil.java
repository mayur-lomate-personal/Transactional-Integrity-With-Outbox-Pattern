package com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.util;

import lombok.extern.log4j.Log4j2;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecord;
import org.apache.avro.specific.SpecificRecordBase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Log4j2
public class AvroUtil {
    public static <T extends SpecificRecordBase> String serializeToString(T record){
        DatumWriter<T> datumWriter = new SpecificDatumWriter<>(record.getSchema());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Encoder encoder = null;
        try {
            encoder = EncoderFactory.get().binaryEncoder(outputStream, null);
            datumWriter.write(record, encoder);
            encoder.flush();
            outputStream.close();
        } catch (IOException e) {
            log.error("Serialization error:" + e.getMessage());
            throw new RuntimeException("SpecificRecord to String Conversion Failed");
        }
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    public static <T extends SpecificRecordBase> T deserializeFromString(String data, Class<T> clazz) {
        byte[] bytes = Base64.getDecoder().decode(data);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        Decoder decoder = DecoderFactory.get().binaryDecoder(inputStream, null);
        DatumReader<T> datumReader = new SpecificDatumReader<>(clazz);
        T record = null;
        try {
            record = datumReader.read(null,decoder);
        } catch (IOException e) {
            log.error("Deserialization error:" + e.getMessage());
            throw new RuntimeException("String to SpecificRecord Conversion Failed");
        }
        return record;
    }
}
