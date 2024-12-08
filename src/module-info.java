module library {
    requires java.desktop;     // For Swing UI
    requires java.sql;         // For JDBC and SQLite

    exports library;           // Exports your main package for use
    exports library.models;    // Exports models package if needed externally
}
