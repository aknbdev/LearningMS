package uz.aknb.app.config;

public interface ProjectConfig {

    String BASE_PACKAGE = "uz.aknb";
    String ENTITY_PACKAGE = BASE_PACKAGE + ".*.entity";
    String REPOSITORY_PACKAGE = BASE_PACKAGE + ".*.repository";
    String LOCAL_DATE_PATTERN = "dd-MM-yyyy";
}
