const { v4: uuid } = require("uuid");
const express = require("express");
const router = express.Router();
const SQL = require("sql-template-strings");
const dbPromise = require("../modules/database.js");

// The DAO that handles CRUD operations for users.
const userDao = require("../modules/user-dao.js");

// Whenever we navigate to /login, if we're already logged in, redirect to "/".
// Otherwise, render the "login" view.
router.get("/login", function (req, res) {

    if (res.locals.user) {
        res.redirect("/");
    }

    else {
        res.render("login");
    }

});

// Whenever we POST to /login, check the username and password submitted by the user.
// If they match a user in the database, give that user an authToken, save the authToken
// in a cookie, and redirect to "/". Otherwise, redirect to "/login", with a "login failed" message.
router.post("/login", async function (req, res) {

    // Get the username and password submitted in the form
    const username = req.body.username;
    const password = req.body.password;

    // Find a matching user in the database
    const user = await userDao.retrieveUserWithCredentials(username, password);



        if (user) {
            // Auth success - give that user an authToken, save the token in a cookie.
            const authToken = uuid();
            user.authToken = authToken;
            await userDao.updateUser(user);
            res.cookie("authToken", authToken);
            res.locals.user = user;
            req.session.userId = user.user_id;
            res.status(204).redirect("/");  // Return a 204 status code
            
        } else {
            // Auth fail - return a 401 status code.
            res.status(401);
            res.redirect("/login")
        }
    });
    
const bcrypt = require('bcrypt');  // Assuming you're using bcrypt library

router.post("/api/login", async function (req, res) {

    // Get the username and password submitted in the form
    const { username, password } = req.body;

    // Find the user in the database
    const user = await userDao.retrieveUserByUsername(username);

    // If the user does not exist, or the password is incorrect
    if (!user) {
        res.status(401).send('Cannot find user!');
        return;
    }

    // Use bcrypt.compare to verify the password
    const match = await bcrypt.compare(password, user.password);

    if (match) {
        // Passwords match, now check if user is an admin
        if (user.is_admin) {
            res.status(204).json({ message: 'Successfully logged in!'});
        } else {
            res.status(401).send('You are not an admin!');
        }
    } else {
        res.status(401).send('Username or password is incorrect!');
    }
});

router.delete('/api/delete/:user_id', async (req, res) => {
    const db = await dbPromise;

    // Begin a database transaction
    await db.run('BEGIN TRANSACTION;');

    try {
        const user_id = req.params.user_id;
        
        // Delete all related data from other tables
        await db.run(SQL`DELETE FROM notification WHERE from_id = ${user_id} OR to_id = ${user_id}`);
        await db.run(SQL`DELETE FROM user_status WHERE user_id = ${user_id}`);
        await db.run(SQL`DELETE FROM subscription WHERE from_id = ${user_id} OR to_id = ${user_id}`);
        await db.run(SQL`DELETE FROM like_article WHERE user_id = ${user_id}`);
        await db.run(SQL`DELETE FROM comments WHERE user_id = ${user_id}`);
        await db.run(SQL`DELETE FROM image WHERE article_id IN (SELECT id FROM articles WHERE user_id = ${user_id})`);
        await db.run(SQL`DELETE FROM articles WHERE user_id = ${user_id}`);
        await db.run(SQL`DELETE FROM users WHERE user_id = ${user_id}`);

        await db.run('COMMIT;');
        res.status(200).send('User deleted successfully');
    } catch (error) {
        await db.run('ROLLBACK;');
        res.status(500).send('Failed to delete user');
        throw error;
    }
});


// Whenever we navigate to /logout, delete the authToken cookie.
// redirect to "/login", supplying a "logged out successfully" message.
router.get("/logout", function (req, res) {
    res.clearCookie("authToken");

    res.status(204);
    res.setToastMessage("Logged out successfully!");
    res.redirect("/login");

});

// Account creation
router.get("/signup", function (req, res) {
    res.render("signup");
});


//check username availability
router.get("/checkUsername", async function (req, res) {
    const username = req.query.username;

    // If the request comes from our JavaScript code for username checking
    const existingUser = await userDao.retrieveUserByUsername(username);
    if (existingUser) {
        res.json({ isTaken: true, message: "The username is already taken." });
    } else {
        res.json({ isTaken: false, message: "The username is available." });
    }
});

router.post("/newAccount", async function (req, res) {

    const user = {
        username: req.body.username,
        password: req.body.password,
        name: req.body.fullName,
        date: req.body.date,
        gender: req.body.gender,
        country: req.body.country,
        description: req.body.description,
        avatarID: req.body.avatar
    };


        try {
            await userDao.createUser(user);
            res.setToastMessage("Account creation successful. Please login using your new credentials.");
            res.redirect("/login");
        }
        catch (err) {
            res.setToastMessage("That username was already taken!");
            res.redirect("/signup");
        }

    });



module.exports = router;