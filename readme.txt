В Java выше 8-ой JavaFX был удалён: подключать локально, ссылаясь на javafx-sdk или с использованием maven/gradle

Надо, чтобы версия Java, на которой выполняется Idea была >= версии классов JavaFX!!

Для запуска аппа (configuration "Application") из Idea в параметрах надо указывать локальную SDK.

========

There are similar questions like this or this other one.

Before JavaFX 11, whenever you were calling something JavaFX related, you had all the javafx modules available within the SDK.

But now you have to include the modules/dependencies you need.

Your error says that you are using FXML but it can't be resolved, but you have just added the javafx.controls module:

--add-modules=javafx.controls

As you can see in the JavaDoc the javafx.controls module depends on javafx.graphics and java.base, but none of those modules includes the FXML classes.

If you need FXML classes like the FXMLLoader, you need to include javafx.fxml module:

 --module-path="C:\Program Files\Java\javafx-sdk-11\lib" \
    --add-modules=javafx.controls,javafx.fxml

========

mvn archetype:generate -DarchetypeGroupId=com.zenjava -DarchetypeArtifactId=javafx-basic-archetype
https://github.com/javafx-maven-plugin/javafx-basic-archetype

OR

org.openjfx plugin
https://github.com/openjfx/javafx-maven-plugin

To compile the project (optional):

mvn javafx:compile

Alternatively, the maven-compiler-plugin can be used:

mvn compile

Note that including this plugin is convenient for a better project integration within your IDE.

To run the project:

mvn javafx:run

For modular projects, to create and run a custom image:

mvn javafx:jlink

target/image/bin/java -m hellofx/org.openjfx.App

======

mvn clean javafx:run

=====

Using the JavafX JAR export option doesn't work anymore in Intelij. You can export it as a regular jar with "Jar-From module with dependencies". This will export a valid Jar, but in order to run it, you need to add your javaFx path and modules to your command.

After you have the jar, the run command should look something like this:

java --module-path PATH_TO_YOUR_JAVAFX_LIB_FOLDER --add-modules javafx.controls,javafx.fxml,java