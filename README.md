# sudoku

https://github.com/swapnilbw/sudoku

Instructions to run the project
1) Download or clone the project using https://github.com/swapnilbw/sudoku.git
2) Build - This is a maven project, run mvn clean install at sudoku project folder to build the project
3) Start - Start the application using java -jar target/demo-0.0.1-SNAPSHOT.jar

Below are sample test cases to run using Postman
1) GET /setupBoard - http://localhost:8080/setupBoard 
# -1 number in board indicate empty  field

2) GET /restart - http://localhost:8080/restart

3) GET - /solve - http://localhost:8080/solve

4) POST - /check  - http://localhost:8080/check ( check if partial board is valid at any point)
Sample Request Body - ( -1 indicates empty field)
{"board":[[4,-1,5,2,-1,-1,7,8,-1],[-1,8,-1,-1,-1,1,4,9,3],[1,9,-1,8,3,-1,-1,6,2],[8,-1,-1,1,9,-1,3,-1,7],[3,7,-1,-1,-1,-1,9,1,5],[-1,-1,1,7,4,-1,6,2,-1],[-1,-1,-1,3,2,6,8,-1,4],[2,-1,8,-1,-1,7,1,3,6],[-1,-1,-1,4,-1,8,2,5,9]]}

5) POST - /checkBoard - http://localhost:8080/checkBoard
Sample Request Body - ( -1 indicates empty field)
{"board":[[4,-1,5,2,-1,-1,7,8,-1],[-1,8,-1,-1,-1,1,4,9,3],[1,9,-1,8,3,-1,-1,6,2],[8,-1,-1,1,9,-1,3,-1,7],[3,7,-1,-1,-1,-1,9,1,5],[-1,-1,1,7,4,-1,6,2,-1],[-1,-1,-1,3,2,6,8,-1,4],[2,-1,8,-1,-1,7,1,3,6],[-1,-1,-1,4,-1,8,2,5,9]]}

