# Library
Implementation of a library in JAVA includes identification and division into administrator and regular user.

![image](https://github.com/user-attachments/assets/52024049-9a14-403c-bd55-9ec74db95c57)


## What did I include in this project:
1. Defined options for three types of users: Student, Teacher, and library worker.

2. Student and Teacher users have options to get a book: Students for a period of 2 weeks, Teacher for a period of 1 month
to return a book and to extend a book in the library: Student for a period of 1 week, Teacher for a period of 2 weeks
A person can extend a book no more than 2 times.
To see a list of all books that are still in use with their return day. To see the history of all books for each person option to send a message to the library worker.
Users can find a book in the library and read the book's description.

3. Library worker user has options: 
    - To add a new book to the library.
    - To remove some books from the library.
    - To see a list of the users that have books at home.
    - To see all messages that were sent to the library worker.
    - To add a new person to the library system.
    - To remove a person from the library system.

4. User login to the library system with user name and passport data.

5. Only exist user in the library can enter the system, user can try 3 times and if the input data is not correct, will see a message that he doesn't have a permission to enter the library.

In this project, I used the `Log4J` library to log errors.
