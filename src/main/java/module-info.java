module com.example.tryout_kl1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.tryout_kl1 to javafx.fxml;
    opens com.example.tryout_kl1.Models to javafx.base;
    exports com.example.tryout_kl1;
    exports com.example.tryout_kl1.Controller;
    opens com.example.tryout_kl1.Controller to javafx.fxml;
}
