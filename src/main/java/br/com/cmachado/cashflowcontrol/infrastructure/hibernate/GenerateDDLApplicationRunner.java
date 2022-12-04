package br.com.cmachado.cashflowcontrol.infrastructure.hibernate;

import org.hibernate.boot.Metadata;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaExport.Action;
import org.hibernate.tool.schema.TargetType;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.MessageFormat;
import java.util.EnumSet;

@Component
public class GenerateDDLApplicationRunner implements ApplicationRunner {
    private final Metadata metadata;

    public GenerateDDLApplicationRunner(Metadata metadata) {
        this.metadata = metadata;
    }

    public void run(ApplicationArguments args) {
        File dropAndCreateDdlFile = new File("drop-and-create.ddl");
        deleteFileIfExists(dropAndCreateDdlFile);

        SchemaExport schemaExport = new SchemaExport();
        schemaExport.setDelimiter(";");
        schemaExport.setFormat(false);
        schemaExport.setOutputFile(dropAndCreateDdlFile.getAbsolutePath());

        schemaExport.execute(EnumSet.of(TargetType.SCRIPT), Action.CREATE, metadata);
    }

    private void deleteFileIfExists(File dropAndCreateDdlFile) {
        if (dropAndCreateDdlFile.exists()) {
            if (!dropAndCreateDdlFile.isFile()) {
                String msg = MessageFormat.format("File is not a normal file {0}", dropAndCreateDdlFile);
                throw new IllegalStateException(msg);
            }

            if (!dropAndCreateDdlFile.delete()) {
                String msg = MessageFormat.format("Unable to delete file {0}", dropAndCreateDdlFile);
                throw new IllegalStateException(msg);
            }
        }
    }
}
