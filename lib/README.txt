# SQLite JDBC Driver Setup

This project uses **SQLite** with **JDBC**. You must download and add the JDBC driver JAR manually (no Maven/Gradle).

## Step 1: Download the Driver

1. Visit: https://github.com/xerial/sqlite-jdbc/releases
2. Download the latest `sqlite-jdbc-x.x.x.jar` file (e.g., `sqlite-jdbc-3.45.3.0.jar`)
3. Save it in this folder: `VehicleRentalPortal/lib/`

## Step 2: Add JAR to Your IDE

### IntelliJ IDEA

1. Open the project folder in IntelliJ
2. Go to **File → Project Structure** (Ctrl+Alt+Shift+S)
3. Select **Modules → Dependencies**
4. Click **+** → **JARs or directories**
5. Select `lib/sqlite-jdbc-x.x.x.jar`
6. Click **Apply → OK**
7. Mark `src` as **Sources Root** (right-click src → Mark Directory as → Sources Root)

### Eclipse

1. Import project: **File → Import → General → Existing Projects into Workspace**
2. Right-click project → **Build Path → Configure Build Path**
3. Go to **Libraries** tab → **Add External JARs**
4. Select `lib/sqlite-jdbc-x.x.x.jar`
5. Click **Apply and Close**

### NetBeans

1. Open project: **File → Open Project**
2. Right-click project → **Properties**
3. Select **Libraries** → **Classpath** → **Add JAR/Folder**
4. Browse to `lib/sqlite-jdbc-x.x.x.jar`
5. Click **OK**

## Step 3: Command Line Compilation (Optional)

From the `VehicleRentalPortal` folder:

```bat
javac -cp "lib\sqlite-jdbc-3.45.3.0.jar;src" -d out src\*.java
java -cp "out;lib\sqlite-jdbc-3.45.3.0.jar" Main
```

Replace the JAR filename with your downloaded version.

## Troubleshooting

| Error | Solution |
|-------|----------|
| `No suitable driver found` | JAR not in classpath – re-add the driver |
| `ClassNotFoundException: org.sqlite.JDBC` | Same as above |
| Database file not found | Normal – `vehicle_rental.db` is auto-created on first run |
