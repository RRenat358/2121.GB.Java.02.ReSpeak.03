ReSpeak
-
Проект сетевого чата

\
\
\
-============================================================  
    **Аргументы**  
-============================================================  

− − − − − − − − − − − − − − −  
`public User( login,  password,  userName) {}  `  

`new User("1", "1", "userName1"),  `  
`new User("2", "2", "userName2"),  `  
`new User("3", "3", "userName3");  `  

\
− − − − − − − − − − − − − − −  
`int authTimeOffSeconds = 100;`

\
− − − − − − − − − − − − − − −  
`alternativePrivateMessage() {}`  
trigger == `/*`  
format == `"/* userName message"`  


\
\
\
-============================================================  
    **Импорт проекта**  
-============================================================  
Подключаем к проекту lib  

`jdk-17.0.3`  language version: 17  

`javafx-sdk-18.0.1`  

\
−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−  
**Директории**  

Sources Root  
`Commands/src`  
`Server/src`  
`Chat/src/java`  

\
Resources Root  
`Chat/src/resources`  


\
−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−  
или  
**Mодули**  
 `Commands`  
 `Chat`  
 `Server`


\
−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−  
Зависимости  
Все модули от  `Commands`  

\
−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−  
Maven  
в импорте присутствует `pom.xml`

\
\
\
-============================================================  
    **Подключение JavaFX**  
-============================================================  
В настройках проекта в разделе `lib` путь к JavaFX.lib  
пример  
`C:\MyWorkSpace.Lib\javafx-sdk\lib`  


\
−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−  
В настройках IDEA глобальный путь к JavaFX.lib  
`PATH_TO_FX`  

\
Для запуска "ChatApp" для VM указать путь  

Getting Started with JavaFX  →  https://openjfx.io/openjfx-docs  
`
--module-path ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml
`

\
\
\
\
-============================================================
\
\
\
\
\
\




