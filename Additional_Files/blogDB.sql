DROP DATABASE IF EXISTS blog;
CREATE DATABASE blog;

USE blog;

CREATE TABLE user(
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL
);

CREATE TABLE role(
    role_id INT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(25) NOT NULL
);

CREATE TABLE user_role(
    user_role_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (role_id) REFERENCES role(role_id)
);

CREATE TABLE post_status(
    status_id INT PRIMARY KEY AUTO_INCREMENT,
    status_name VARCHAR(25) NOT NULL
);

CREATE TABLE post(
    post_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    title VARCHAR(250) NOT NULL,
    content LONGTEXT NOT NULL,
    created_date TIMESTAMP NOT NULL,
    expiration_date TIMESTAMP,
    status_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (status_id) REFERENCES post_status(status_id)
);

CREATE TABLE tag(
    tag_id INT PRIMARY KEY AUTO_INCREMENT,
    tag_name VARCHAR(100) NOT NULL
);

CREATE TABLE post_tag(
    post_tag_id INT PRIMARY KEY AUTO_INCREMENT,
    post_id INT NOT NULL,
    tag_id INT NOT NULL,
    FOREIGN KEY (post_id) REFERENCES post(post_id),
    FOREIGN KEY (tag_id) REFERENCES tag(tag_id)
);
