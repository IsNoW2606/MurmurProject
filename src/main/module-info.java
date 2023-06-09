module MurmurProject.main {
    requires com.google.gson;
    opens repository.dto to com.google.gson;
    opens repository.dto.implementation to com.google.gson;
}