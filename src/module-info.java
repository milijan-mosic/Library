module library {
    requires java.desktop;     		// For Swing UI
    requires transitive java.sql;	// For SQLite
    requires jdatepicker;          	// For JDBC

    exports library;
    exports library.models;
}
