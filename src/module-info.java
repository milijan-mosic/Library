module library {
    requires java.desktop;     // For Swing UI
    requires java.sql;         // For SQLite
    requires jdatepicker;      // For JDBC

    exports library;
    exports library.models;
}
