# OnGoing-Blog-System

# Java Swing User Management Application

This application is a user management system implemented with Java Swing. It allows users to view and manage a list of users.

## Features

1. **User Login:** A user must first log in to the application with their username and password. The application has an administrator account with username 'user1' and password 'password1'. Please note that other users can be added as per the system requirements.
2. **User Management:** Once logged in, the application displays a table of users, including their details such as username, real name, gender, country, and whether they are administrators.
3. **Data Refresh:** The application automatically refreshes the user table every seven seconds to ensure the data is up-to-date.
4. **Delete User:** Administrators can select a user from the table and delete them from the system by clicking on the 'Delete User' button.
5. **Logout:** Users can log out from the system using the 'Logout' button. After logging out, users will be redirected to the login page.

## Admin Credentials

In order to access the application, use the following credentials:

- **Username:** user1
- **Password:** password1

Please note that these is admin credential that allow you to manage user data.

## How to Run

Before running the application, ensure that you have Java 11 or above installed on your system. Additionally, you must have Node.js installed to start the server.

Here's how you can run the application:

1. **Start the Server:** Navigate to the server directory on your terminal or command line and run `npm start`. This will start your server.
2. **Run the Java Application:** Once the server is running, you can execute the main class (`main.java`) of the application from your preferred IDE or from the command line.

Please ensure that both the server and the Java application are running simultaneously for the application to function correctly.

# Personal Blog System

## Functionality User Accounts

Users can open new accounts on the system, each with a different username and password. The system keeps track of the user's real name, date of birth, and a brief biographical statement in addition to these fundamental facts. Usernames and other account details are always editable by users. In addition, they have the choice to cancel their account, which also deletes all of their articles and comments from the database.

## Blogging

Users have control over the creation, editing, and deletion of their own blog posts. They have total control over their content thanks to this, and they may openly express their beliefs. While reading articles, users can choose to show or hide comments.

## Comments

The technology gives users a forum to interact with one another through comments. Users have the option to post comments on articles and reply to others' comments, which promotes community and discussion.

## Notifications 

Users receive notifications for a variety of platform actions. These events include the creation of a new article, the posting or replying to a remark, and the beginning of a new user's following. The navigation bar's notification badges provide users with up-to-the-minute information.

## Dashboard for Analytics

The analytics dashboard is a useful tool for authors to track the readership of their articles and other engagement-related information. The top three most popular posts, key performance indicators, and a histogram graphic displaying the average number of comments per day over time are all displayed.


---

# Before you start

## Initialize Database

1. Start the server

```
npm i
npm start
```

2. Then the database will be initialized. Use sql script in ```/sql/project-database-init-script.sql``` to setup the database.

## Default settings 

We have 2 default accounts.

## user1 (admin user)

```
username: user1

password: password1
```

## user2

```
username: user2

password: password2
```

You can log in and check their information. The user1 account has an extra link in the right nav bar which called "All users", when you click this link, you can view all user’s information including their usernames, real names, countries, avatars and descriptions. Also, user1 has the delete other users’ articles, comments even account function. When user1 delete other user’s account, it will automatically delete their notifications, comments, likes and articles. Finally, you can log in the default account to start exploring our blog website.



## Contact

For any queries, please feel free to reach out at [liudongyu12138@gmail.com]
