# Chicken Invaders Java Game

## Project Description

This project is a Java Swing arcade game inspired by Chicken Invaders.  
The player controls a spaceship and must shoot enemy chickens, avoid eggs and enemy bullets, collect power-ups, defeat bosses, and complete all levels.

## Technologies Used

- Java
- Java Swing
- SQLite
- JDBC
- IntelliJ IDEA
- Git / GitHub

## Main Features

- Main menu system
- Login and register system
- SQLite database for users and game records
- High score system
- Multiple enemy types:
    - Normal Chicken
    - Fast Chicken
    - Zigzag Chicken
    - Shooter Chicken
- Boss levels
- Enemy egg dropping
- Enemy bullets
- Player bullets
- Power-ups:
    - Extra Life
    - Add Fire
    - Rapid Fire
    - Shield
    - Freeze Bomb
- Cell counter system
- Enemy respawn system
- Pause system
- Sound settings
- Game Over and Win states

## Controls

| Key | Action |
|---|---|
| Arrow Keys / WASD | Move player |
| Space | Shoot |
| P | Pause / Resume |
| ESC | Return to main menu |

## Database

The project uses SQLite database file:

```text
identifier.db

The database contains two main tables:

users

Stores user account information and sound settings.

Important columns:

username
password
highest_score
last_level
background_music_on
shot_sound_on
explosion_sound_on
game_result_sound_on
game_records

Stores each finished game record.

Important columns:

username
score
level_reached
played_at
sound settings at the time of playing
Game Levels

The game has 8 levels.

Levels 1, 2, 3, 5, 6, and 7 contain normal enemy grids.
Levels 4 and 8 are boss levels.

As the level increases, enemies become faster and attacks become more difficult.

Enemy Respawn System

Each enemy cell has a counter.
When an enemy is killed, the counter of that cell decreases by one.
If the counter is still greater than zero, a new enemy appears from the top-left or top-right side of the screen and moves toward its own cell.

The respawn enemy is vulnerable while moving toward the cell.

How to Run
Open the project in IntelliJ IDEA.
Make sure the SQLite JDBC jar file exists in the lib folder.
Make sure game.db exists in the project root folder.
Run GameMain.java.
Author

Student Name: Karen Salek
Project: Chicken Invaders Java Final Project