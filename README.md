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
`int authTimeOffSeconds = 10;`

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
\
\
-============================================================  
    **Подключение JavaFX**  
-============================================================

Getting Started with JavaFX  
https://openjfx.io/openjfx-docs

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




