# Introduction
In JDBC app, I've learned the concepts RDBMS, JDBC, and the Postgre database. I also worked on a DAO that spanned multiple tables to implement basic CRUD operations. 

# Design Patterns
DAO (Data Access Objects) Patterns:
It provides an abstraction layer between the raw JDBC code and the rest of my code, specifically the business logic. Most developers use DTOs (Data Transfer Object) with DAOs as a pure abstraction layer. DAOs can assist developer with solid encapsulation of complex join, pass and aggregations. However, if we only have CRUD operations, Repository Pattern might be a better choice. 

Repository Patterns: 
It only focuses on single table access per class. Insteading of joining in the database, in repository pattern you should join in the code. Therefore, it fits horizontally scalable application and it can help when our application is database constrained. 
